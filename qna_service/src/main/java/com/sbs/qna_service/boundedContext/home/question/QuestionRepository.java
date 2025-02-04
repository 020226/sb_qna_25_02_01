package com.sbs.qna_service.boundedContext.home.question;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// 리포지터리로 만들기 위해 JpaRepository 인터페이스를 상속
// JpaRepository는 CRUD 메서드를 내장하고 있음
// <Question, Integer> : Question 엔티티로 리포지터리를 생성하고 Question 엔티티 기본키가 Integer임을 나타냄
// interface로 만들면 @Repository가 생략되어 있다
public interface QuestionRepository extends JpaRepository<Question, Integer> {
  // JPA는 리포지터리의 메서드명을 분석하여 쿼리를 만들고 실행한다
  // findBy+엔티티 속성명 -> 입력한 속성의 값으로 데이터를 조회
  Question findBySubject(String subject);

  // AND 연산잘ㄹ 사용하여 두 개의 열을 조회
  Question findBySubjectAndContent(String subject, String content);

  List<Question> findBySubjectLike(String subject);
  // Pageable 객체를 입력받아 Page<Question> 타입 객체를 리턴하는 findAll 메서드 생성
  Page<Question> findAll(Pageable pageable);

  // 테스트 케이스 독립성 확보
  @Modifying // INSERT, UPDATE, DELETE와 같은 데이터가 변경 작업에서만 사용
  // nativeQuery = ture 여야 MySQL 쿼리 사용이 가능
  @Transactional
  @Query(value = "ALTER TABLE question AUTO_INCREMENT = 1", nativeQuery = true)
  void clearAutoIncrement();
}
