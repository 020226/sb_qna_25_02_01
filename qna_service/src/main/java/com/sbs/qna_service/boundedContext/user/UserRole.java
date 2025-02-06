package com.sbs.qna_service.boundedContext.user;

import lombok.Getter;

// 스프링 시큐리티(인증, 권한 관리)
// 사용자가 로그인한 후, ADMIN 또는 USER와 같은 권한을 부여해야 한다
@Getter // ADMIN과 USER 상수는 값을 변경할 필요가 없으므로 @Setter 없이 @Getter만 사용할 수 있도록
public enum UserRole { // enum 자료형(열거 자료형)
  ADMIN("ROLE_ADMIN"), // 관리자를 의미하는 ADMIN 상수에 ‘ROLE_ADMIN’ 값 부여
  USER("ROLE_USER"); // 사용자를 의미하는 USER 상수에 ‘ROLE_USER’ 값 부여
  UserRole(String value) {
    this.value = value;
  }
  private String value;
}
