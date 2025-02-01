package com.sbs.qna_service;

import com.sbs.qna_service.boundedContext.home.question.Question;
import com.sbs.qna_service.boundedContext.home.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class QnaServiceApplicationTests {

	// 의존성 주입: 스프링이 객체를 대신 생성하여 주입하는 기법
	// questionRepository 객체를 자동으로 만들어 주입함
	// Autowired 혹은 Setter 메서드, 생성자 사용하는 방식으로 객체를 주입할 수 있다
	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void t1() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

}
