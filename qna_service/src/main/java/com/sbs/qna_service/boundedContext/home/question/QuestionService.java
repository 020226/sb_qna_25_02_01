package com.sbs.qna_service.boundedContext.home.question;

import com.sbs.qna_service.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {
  // questionRepository 객체는 @RequiredArgsConstructor에 의해 생성자 방식으로 주입
  private final QuestionRepository questionRepository;

  public List<Question> getList() {
    return questionRepository.findAll();
  }

  // id값으로 질문 데이터를 조회하는 메서드
  // Question 객체는 Optional 객체임
  public Question getQuestion(Integer id) {
    Optional<Question> question = questionRepository.findById(id);
    if(question.isPresent()) {
      return question.get();
    } else {
      // 만약 id값에 해당하는 질문 데이터가 없을 경우 DataNotFoundException
      throw new DataNotFoundException("question not found");
    }
  }

  public void create(String subject, String content) {
      Question q = new Question();
      q.setSubject(subject);
      q.setContent(content);
      q.setCreateDate(LocalDateTime.now());
      questionRepository.save(q);
  }
}
