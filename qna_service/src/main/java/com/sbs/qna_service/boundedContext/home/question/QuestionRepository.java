package com.sbs.qna_service.boundedContext.home.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 리포지터리로 만들기 위해 JpaRepository 인터페이스를 상속
// JpaRepository는 CRUD 메서드를 내장하고 있음
// <Question, Integer> : Question 엔티티로 리포지터리를 생성하고 Question 엔티티 기본키가 Integer임을 나타냄
public interface QuestionRepository extends JpaRepository<Question, Integer> {
  // JPA는 리포지터리의 메서드명을 분석하여 쿼리를 만들고 실행한다
  // findBy+엔티티 속성명 -> 입력한 속성의 값으로 데이터를 조회
  Question findBySubject(String subject);

  // AND 연산잘ㄹ 사용하여 두 개의 열을 조회
  Question findBySubjectAndContent(String subject, String content);

  List<Question> findBySubjectLike(String subject);
}
