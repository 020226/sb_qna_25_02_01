package com.sbs.qna_service.boundedContext.home.answer;

import org.springframework.data.jpa.repository.JpaRepository;

// 리포지터리를 이용하여 question, answer 테이블에 데이터를 저장, 조회, 수정, 삭제 가능
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
