package com.sbs.qna_service.boundedContext.home.question;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
}
