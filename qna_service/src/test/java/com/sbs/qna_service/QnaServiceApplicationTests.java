package com.sbs.qna_service;

import com.sbs.qna_service.boundedContext.home.answer.Answer;
import com.sbs.qna_service.boundedContext.home.answer.AnswerRepository;
import com.sbs.qna_service.boundedContext.home.question.Question;
import com.sbs.qna_service.boundedContext.home.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class QnaServiceApplicationTests {

	// 의존성 주입: 스프링이 객체를 대신 생성하여 주입하는 기법
	// questionRepository 객체를 자동으로 만들어 주입함
	// Autowired 혹은 Setter 메서드, 생성자 사용하는 방식으로 객체를 주입할 수 있다
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@BeforeEach // 테스트 케이스 실행 전에 딱 한 번 실행
	void beforeEach() {
		// 모든 데이터 삭제
		questionRepository.deleteAll();

		// 흔적 삭제(다음 INSERT 때 id가 1번으로 설정되도록)
		questionRepository.clearAutoIncrement();

		// 모든 데이터 삭제
		answerRepository.deleteAll();

		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);
	}

	@Test
	@DisplayName("데이터 저장")
	void t1() {
		Question q = new Question();
		q.setSubject("겨울 제철 음식으로는 무엇을 먹어야 하나요?");
		q.setContent("겨울 제철 음식을 알려주세요.");
		q.setCreateDate(LocalDateTime.now());
		questionRepository.save(q); // 세번째 질문 저장

		assertEquals("겨울 제철 음식으로는 무엇을 먹어야 하나요?",
				questionRepository.findById(3).get().getSubject());
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

	@Test
	@DisplayName("findBySubject")
	void t4() {
		Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, q.getId());
		}

	@Test
	@DisplayName("findBySubjectAndContent")
	void t5() {
		Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(1, q.getId());
	}

	@Test
	@DisplayName("findBySubjectLike") // subject 값 중 특정 문자열을 포함하는 데이터 조회
	void t6() {
		List<Question> qList = questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	@DisplayName("질문 데이터 수정하기")
	void t7() {
		Optional<Question> oq = questionRepository.findById(1);
		assertTrue(oq.isPresent()); // assertTrue은 괄호 안의 값이 참인지 테스트 - false면 테스트 종료
		Question q = oq.get();
		q.setSubject("수정된 제목");
		questionRepository.save(q);
	}

	@Test
	@DisplayName("질문 데이터 삭제하기")
	void t8() {
		// 삭제 전 데이터 2개
		assertEquals(2, questionRepository.count());
		Optional<Question> oq = questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		questionRepository.delete(q);
		// 삭제 후 데이터 1개
		assertEquals(1, questionRepository.count());
	}

	@Test
	@DisplayName("답변 데이터 생성 후 저장하기")
	void t9() {
		Optional<Question> oq = questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q); // 어떤 질문의 답변인지 알기 위해 Question 객체가 필요함
		a.setCreateDate(LocalDateTime.now());
		answerRepository.save(a);
	}
}
