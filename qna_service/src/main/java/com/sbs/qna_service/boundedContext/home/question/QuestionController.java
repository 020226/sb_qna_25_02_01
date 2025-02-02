package com.sbs.qna_service.boundedContext.home.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor // final이 붙은 속성을 포함하는 생성자를 자동으로 만들어 주는 역할
// QuestionController를 생성하며 questionRepository 객체가 자동으로 주입됨
@Controller
public class QuestionController {
  // 질문 목록 데이터 조회를 위해 QuestionRepository 사용
  // 조회한 데이터 Model 로 템플릿에 전달
  private final QuestionRepository questionRepository;

  // Model 객체는 자바 클래스와 템플릿 간의 연결고리
  // Model 객체에 값을 담아두면 템플릿에서 그 값을 사용할 수 있음
  @GetMapping("/question/list")
  public String list(Model model) { // 매개변수로 Model을 지정하면 객체가 자동으로 생성
    List<Question> questionList = questionRepository.findAll(); // 질문 목록 questionList를 생성
    model.addAttribute("questionList", questionList); // Model 객체에 questionList 이름으로 저장
    return "question_list";
  }
}
