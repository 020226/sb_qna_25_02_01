package com.sbs.qna_service.boundedContext.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 스프링 시큐리티가 로그인 시 사용할 서비스
// `UserSecurityService`는 스프링 시큐리티가 제공하는 `UserDetailsService` 인터페이스를 구현(implements)
// `UserDetailsService`는 `loadUserByUsername` 메서드를 구현하도록 강제하는 인터페이스
// `loadUserByUsername` 메서드는 사용자명(username)으로 스프링 시큐리티의 사용자(User) 객체를 조회하여 리턴하는 메서드
@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

  private final UserRepository userRepository;

  // 시큐리티가 특정 회원을 username을 받았을 때
  // 그 username에 해당하는 회원정보를 얻는 수단
  // 시큐리티는 siteUser 테이블이 존재하는지 모르기 때문에, 요정도는 구현해줘야 함.
  // 스프링 시큐리티는 loadUserByUsername 메서드에 의해 리턴된 User 객체의 비밀번호가
  // 사용자로부터 입력받은 비밀번호와 일치하는지를 검사하는 기능을 내부에 가지고 있다
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<SiteUser> _siteUser = userRepository.findByUsername(username);

    if(_siteUser.isEmpty()) {
      throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }

    SiteUser siteUser = _siteUser.get();

    // 권한을 담을 빈 리스트 생성
    List<GrantedAuthority> authorities = new ArrayList<>();

    if ("admin".equals(username)) {
      // UserRole.ADMIN.getValue() == "ROLE_ADMIN"

      // admin 이라면 관리자 권한 부여
      authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
    } else {
      // admin이 아니라면 일반 사용자 권한 부여
      authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
    }

    // 이 객체는 스프링 시큐리티에서 사용하며 User 생성자에는 사용자명, 비밀번호, 권한 리스트가 전달됨
    return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
  }
}
