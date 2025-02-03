package com.sbs.qna_service.boundedContext.home.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 폼 클래스는 입력값 검증 & 입력 항목 바인딩할 때도 사용
// question_form.html 템플릿의 입력 항목인 subject와 content가
// 폼 클래스의 subject, content 속성과 바인딩
// 바인딩이란 템플릿의 항목과 form 클래스의 속성이 매핑되는 과정
public class QuestionForm {
  // Spring Boot Validation 라이브러리 @NotEmpty, @Size
  // @NotEmpty: Null 또는 빈 문자열("")을 허용하지 않는다.
  @NotEmpty(message = "제목은 필수항목입니다.")
  // @Size: 문자 길이를 제한한다.
  @Size(max=200)
  private String subject;

  @NotEmpty(message = "내용은 필수항목입니다.")
  private String content;
}
