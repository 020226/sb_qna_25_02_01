package com.sbs.qna_service.boundedContext.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  @GetMapping("/signup") // GET 요청 시 회원가입을 위한 템플릿을 렌더링
  public String signup(UserCreateForm userCreateForm) {
    return "signup_form";
  }

  @PostMapping("/signup") // POST 요청 시 회원 가입 진행
  public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult){
    if(bindingResult.hasErrors()) {
      return "signup_form";
    }

    // 2개의 값이 서로 일치하지 않을 경우
    if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
      // bindingResult.rejectValue를 사용하여 입력 받은 2개의 비밀번호가 일치하지 않는다는 오류가 발생하게
      // bindingResult.rejectValue(필드명, 오류 코드, 오류 메시지)
      // 오류 코드는 임의로 passwordInCorrect로 정의
      bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
      return "signup_form";
    }
    try {
      userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
    } catch (DataIntegrityViolationException e) { // 사용자 ID 또는 이메일 주소가 이미 존재할 경우에는 DataIntegrityViolationException이라는 예외 발생
      e.printStackTrace();
      bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
      return "signup_form";
    }catch(Exception e) {
      e.printStackTrace();
      bindingResult.reject("signupFailed", e.getMessage());
      return "signup_form";
    }
      return "redirect:/";
  }

  // 스프링 시큐리티에 로그인 URL을 `/usr/login`으로 설정했리 때문에 URL 매핑
  // 실제 로그인을 진행하는 @PostMapping 방식의 메서드는 스프링 시큐리티가 대신 처리함(직접 구현X)
  @GetMapping("/login")
  public String login() {
    return "login_form";
  }
}
