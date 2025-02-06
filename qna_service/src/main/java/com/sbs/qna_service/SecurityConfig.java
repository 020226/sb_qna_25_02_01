package com.sbs.qna_service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링의 환경 설정 파일임을 의미
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만듦. 스프링 시큐리티 활성화.
public class SecurityConfig {
  // 내부적으로 SecurityFilterChain 클래스가 동작하여 모든 요청 URL에 이 클래스가 필터로 적용되어 URL 별로 특별한 설정을 하도록
  @Bean // SecurityFilterChain 빈을 생성
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
            .requestMatchers(new AntPathRequestMatcher("/question/list")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/question/detail/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/user/signup")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/user/login")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/style.css")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
            .anyRequest().authenticated() // 그 외의 요청은 인증이 필요!
        )

        .formLogin(formLogin -> formLogin
            /*
            .usernameParameter("name") // 아이디 필드 이름 변경
            .passwordParameter("pass") // 비밀번호 필드 이름 변경
             */

            // GET
            // 시큐리티에게 우리가 만든 로그인 페이지 URL을 알려준다.
            // 만약에 하지 않으면 기본 로그인 페이지 url은 /login 이다.
            .loginPage("/user/login") // 로그인 폼으로 이동

            // POST
            // 시큐리티에게 로그인 폼 처리 url을 알려준다.
            .loginProcessingUrl("/user/login") // 로그인 처리시 요청 경로
            .defaultSuccessUrl("/")) // 로그인 성공시 리다이렉트 경로
        .logout((logout) -> logout
            // .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
            .logoutUrl("/user/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true));

    return http.build();
  }

  // PasswordEncoder 객체를 빈으로 등록해서 사용
  // BCryptPasswordEncoder를 사용한 모든 프로그램을 일일이 찾아다니며 수정하지 않도록
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
    // 스프링 시큐리티 인증을 처리
    // 커스텀 인증 로직을 구현할 때 필요
  AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

}
