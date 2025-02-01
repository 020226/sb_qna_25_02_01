package com.sbs.qna_service.boundedContext.home.question;

import com.sbs.qna_service.boundedContext.home.answer.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 200)
  private String subject;

  @Column(columnDefinition = "TEXT") // columnDefinition : 열 데이터의 유형이나 성격을 정의
  private String content;

  private LocalDateTime createDate;

  // 질문에서 답변을 참조할 수 있도록. 질문과 답변 = 1 : N 관계
  // 질문에서 답변을 참조하기 위해 question.getAnswerList()를 호출할 수 있음
  // mappedBy - 참조 엔티티의 속성명을 정의함
  // Answer 엔티티에서 Question 엔티티를 참조한 속성인 question을 mappedBy에 전달해야 함
  // 질문을 삭제하면 그에 달린 답변들도 함께 삭제될 수 있도록: cascade = CascadeType.REMOVE
  @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
  private List<Answer> answerList = new ArrayList<>();

  // 외부에서 answerList 필드에 접근하는 것을 차단
  public void addAnswer(Answer a) {
    a.setQuestion(this); // Question 객체에 Answer 추가
    answerList.add(a); // Answer 객체에 Question 설정
  }
}
