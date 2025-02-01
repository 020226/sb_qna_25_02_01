package com.sbs.qna_service.boundedContext.home.question;

import org.springframework.data.jpa.repository.JpaRepository;

// 리포지터리로 만들기 위해 JpaRepository 인터페이스를 상속
// JpaRepository는 CRUD 메서드를 내장하고 있음
// <Question, Integer> : Question 엔티티로 리포지터리를 생성하고 Question 엔티티 기본키가 Integer임을 나타냄
public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
