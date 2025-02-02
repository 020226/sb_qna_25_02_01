package com.sbs.qna_service.boundedContext.home.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class QuestionController {
  private final QuestionService questionService;

  // Model 객체는 자바 클래스와 템플릿 간의 연결고리
  // Model 객체에 값을 담아두면 템플릿에서 그 값을 사용할 수 있음
  @GetMapping("/question/list")
  public String list(Model model) { // 매개변수로 Model을 지정하면 객체가 자동으로 생성
    List<Question> questionList = questionService.getList(); // 질문 목록 questionList를 생성
    model.addAttribute("questionList", questionList); // Model 객체에 questionList 이름으로 저장
    return "question_list";
  }

  // http://localhost:8080/question/detail/2의 숫자 2처럼 변하는 id값을 얻을 때에는
  // @PathVariable 애너테이션을 사용
  @GetMapping(value = "/question/detail/{id}")
  public String detail(Model model, @PathVariable("id") Integer id){
    Question question = questionService.getQuestion(id);
    model.addAttribute("question", question);
    return "question_detail";
  }
}
