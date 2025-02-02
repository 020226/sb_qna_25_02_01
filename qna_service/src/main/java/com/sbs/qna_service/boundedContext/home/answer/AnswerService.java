package com.sbs.qna_service.boundedContext.home.answer;

import com.sbs.qna_service.boundedContext.home.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {
  private final AnswerRepository answerRepository;

  // 답변(Answer)을 생성하기 위한 create 메서드
  // 입력받은 2개의 매개변수인 question과 content를 사용하여 Answer 객체를 생성하여 저장
  public void create(Question question, String content) {
    Answer answer = new Answer();
    answer.setContent(content);
    answer.setCreateDate(LocalDateTime.now());
    answer.setQuestion(question);
    answerRepository.save(answer);
  }
}
