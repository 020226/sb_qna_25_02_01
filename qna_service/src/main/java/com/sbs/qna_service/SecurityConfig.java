package com.sbs.qna_service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 스프링의 환경 설정 파일임을 의미
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만듦. 스프링 시큐리티 활성화.
public class SecurityConfig {
  // 내부적으로 SecurityFilterChain 클래스가 동작하여 모든 요청 URL에 이 클래스가 필터로 적용되어 URL 별로 특별한 설정을 하도록
  @Bean // SecurityFilterChain 빈을 생성
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.build();
  }
}
