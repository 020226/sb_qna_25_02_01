package com.sbs.qna_service.boundedContext.home.question;

import com.sbs.qna_service.boundedContext.home.answer.AnswerForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
  public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
    Question question = questionService.getQuestion(id);
    model.addAttribute("question", question);
    return "question_detail";
  }

  // question_form.html의 form 태그에 th:object 속성을 추가했기 때문에
  // question_form.html은 [질문 등록하기] 버튼을 통해 GET 방식으로 URL이 요청되더라도
  // th:object에 의해 QuestionForm 객체가 필요하여 매개변수로 추가함
  // QuestionForm과 같이 매개변수로 바인딩한 객체는 Model 객체로 전달하지 않아도 템플릿에서 사용할 수 있다
  @GetMapping("/create")
  public String questionCreate(QuestionForm questionForm) {
    return "question_form";
  }

  // questionCreate 오버로딩
  // 화면에서 입력한 제목(subject)과 내용(content)을 매개변수로 받는다
  // question_form.html에서 입력 항목으로 사용한 subject, content의 이름과
  // RequestParam의 value 값이 동일해야 함
  @PostMapping("/create")
  // 매개변수 subject, content -> QuestionForm 객체
  // subject, content 항목을 지닌 폼이 전송되면 QuestionForm의 subject, content 속성이 자동으로 바인딩
  // 이름이 동일하면 함께 연결되어 묶이는 것이 폼의 바인딩 기능
  // @Valid: QuestionForm의 @NotEmpty, @Size 등으로 설정한 검증 기능이 동작
  // BindingResult 매개변수는 @Valid 애너테이션으로 검증이 수행된 결과를 의미하는 객체
  // BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치해야
  public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "question_form"; // 오류가 있는 경우에는 다시 제목과 내용을 작성하는 화면으로 돌아가도록
    }
    questionService.create(questionForm.getSubject(), questionForm.getContent()); // QuestionService의 create 메서드를 호출하여 질문 데이터(subject, content)를 저장
    return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
  }
}
