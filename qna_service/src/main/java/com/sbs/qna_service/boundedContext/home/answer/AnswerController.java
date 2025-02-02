package com.sbs.qna_service.boundedContext.home.answer;

import com.sbs.qna_service.boundedContext.home.question.Question;
import com.sbs.qna_service.boundedContext.home.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
  private final QuestionService questionService;
  private final AnswerService answerService;

  // POST 요청을 처리하는 경우 @GetMapping이 아니라 @PostMapping
  @PostMapping("/create/{id}") // `/answer/create/{id}` 요청 시 createAnswer 메서드가 호출
  // question_detail.html 에서 답변으로 입력한 content 얻기 위해
  // <textarea>의 name 속성명이 content이므로 여기서도 변수명을 content로 사용
  public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value = "content") String content) {
    Question question = questionService.getQuestion(id);
    answerService.create(question, content); // AnswerService의 create 메서드를 호출하여 답변을 저장
    return String.format("redirect:/question/detail/%s", id);
  }
}
