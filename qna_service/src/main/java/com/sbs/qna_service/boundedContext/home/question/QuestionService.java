package com.sbs.qna_service.boundedContext.home.question;

import com.sbs.qna_service.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {
  // questionRepository 객체는 @RequiredArgsConstructor에 의해 생성자 방식으로 주입
  private final QuestionRepository questionRepository;

  public Page<Question> getList(int page) {
    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("createDate"));
    /* 게시물을 역순(최신순)으로 조회하려면 이와 같이 PageRequest.of 메서드의 세 번째 매개변수에 Sort 객체를 전달 */
    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // page는 조회할 페이지의 번호이고, 10은 한 페이지에 보여 줄 게시물의 개수
    return questionRepository.findAll(pageable);
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
