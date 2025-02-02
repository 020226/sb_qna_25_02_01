package com.sbs.qna_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

// 데이터베이스에서 특정 엔티티 또는 데이터를 찾을 수 없을 때 발생시키는 예외 클래스
// 이 예외가 발생 시 HTTP 상태 코드(HttpStatus.NOT_FOUND)와 이유("entity not found")를 포함해 응답
// RuntimeException 클래스를 상속하는 것은 사용자 정의 예외 클래스를 정의하는 방법 중 하나
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;
  public DataNotFoundException(String message) {
    super(message);
  }
}
