package com.sbs.qna_service.boundedContext.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
  @GetMapping("/home/main")
  @ResponseBody
  public String showHome() {
    return "안녕하세요 sbb에 오신 것을 환영합니다.";
  }

  @GetMapping("/")
  public String root() {
    // 리다이렉트란 클라이언트가 요청하면 새로운 URL로 전송하는 것을 의미
    // localhost:8080로 접속하면 localhost:8080/question/list로 주소가 바뀌면서
    // 질문 목록이 있는 웹 페이지로 연결됨
    return "redirect:/question/list"; // `/question/list` URL로 페이지를 리다이렉트하라
  }
}
