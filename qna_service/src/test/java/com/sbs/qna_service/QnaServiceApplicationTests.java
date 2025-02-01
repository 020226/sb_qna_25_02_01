package com.sbs.qna_service;

import com.sbs.qna_service.boundedContext.home.question.Question;
import com.sbs.qna_service.boundedContext.home.question.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class QnaServiceApplicationTests {

	// 의존성 주입: 스프링이 객체를 대신 생성하여 주입하는 기법
	// questionRepository 객체를 자동으로 만들어 주입함
	// Autowired 혹은 Setter 메서드, 생성자 사용하는 방식으로 객체를 주입할 수 있다
	@Autowired
	private QuestionRepository questionRepository;

	@Test
	@DisplayName("데이터 저장")
	void t1() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1); // insert 쿼리

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	@Test
	@DisplayName("findAll")
	void t2() {
		List<Question> all = questionRepository.findAll();
		// assertEquals(기댓값, 실젯값): 테스트에서 예상한 결과와 실제 결과가 동일한지 확인
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	@DisplayName("findById")
	void t3() {
		// 리턴타입 Optional인 이유: findById로 호출한 값이 존재할 수도 있고 아닐 수도 있음
		// Optional의 isPresent()로 값이 존재하는지 확인할 수 있다
		Optional<Question> oq = questionRepository.findById(1);
		if(oq.isPresent()) {
			Question q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());
		}
	}

}
