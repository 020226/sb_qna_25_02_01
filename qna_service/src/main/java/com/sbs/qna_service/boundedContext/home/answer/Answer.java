package com.sbs.qna_service.boundedContext.home.answer;

import com.sbs.qna_service.boundedContext.home.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(columnDefinition = "TEXT")
  private String content;

  private LocalDateTime createDate;

  // 답변을 통해 질문의 제목을 알고 싶을 수 있다
  // answer.getQuestion().getSubject()로 접근할 수 있도록 question 속성 추가
  // 질문 엔티티와 연결된 속성임을 답변 엔티티에 표시하기 위해  `@ManyToOne` 애너테이션
  @ManyToOne // 답변 엔티티의 question 속성과 질문 엔티티가 서로 연결 -> 외래키 관계
  // 하나의 질문에 답변이 여러 개 달릴 수 있음 = ManyToOne
  // @ManyToOne - 부모 자식 관계(부모: Question, 자식: Answer)
  private Question question;
}
