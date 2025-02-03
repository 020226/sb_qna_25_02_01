package com.sbs.qna_service.boundedContext.home.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 프리픽스(prefix)란 URL의 접두사 또는 시작 부분을 가리키는 말
// 메서드 단위에서는 /question을 생략하고 그 뒷부분만을 적을 수 있도록 수정
@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
  private final QuestionService questionService;

  // Model 객체는 자바 클래스와 템플릿 간의 연결고리
  // Model 객체에 값을 담아두면 템플릿에서 그 값을 사용할 수 있음
  @GetMapping("/list") // `/question` + `/list`가 되어 최종 URL 매핑은 `/question/list`가 된다
  public String list(Model model) { // 매개변수로 Model을 지정하면 객체가 자동으로 생성
    List<Question> questionList = questionService.getList(); // 질문 목록 questionList를 생성
    model.addAttribute("questionList", questionList); // Model 객체에 questionList 이름으로 저장
    return "question_list";
  }

  // http://localhost:8080/question/detail/2의 숫자 2처럼 변하는 id값을 얻을 때에는
  // @PathVariable 애너테이션을 사용
  @GetMapping(value = "/detail/{id}")
  public String detail(Model model, @PathVariable("id") Integer id) {
    Question question = questionService.getQuestion(id);
    model.addAttribute("question", question);
    return "question_detail";
  }

  @GetMapping("/create")
  public String questionCreate() {
    return "question_form";
  }

  // questionCreate 오버로딩
  // 화면에서 입력한 제목(subject)과 내용(content)을 매개변수로 받는다
  // question_form.html에서 입력 항목으로 사용한 subject, content의 이름과
  // RequestParam의 value 값이 동일해야 함
  @PostMapping("/create")
  public String questionCreate(@RequestParam(value = "subject") String subject, @RequestParam(value = "content") String content) {
    questionService.create(subject, content); // QuestionService의 create 메서드를 호출하여 질문 데이터(subject, content)를 저장
    return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
  }
}
