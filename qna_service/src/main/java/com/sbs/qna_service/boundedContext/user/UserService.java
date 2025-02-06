package com.sbs.qna_service.boundedContext.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public SiteUser create(String username, String email, String password) {
    SiteUser user = new SiteUser();
    user.setUsername(username);
    user.setEmail(email);
    // BcryptPasswordEncoder 객체를 직접 생성하여 사용하지 않고
    // 빈으로 등록한 Password Encoder 객체를 주입받아 사용할 수 있도록
    user.setPassword(passwordEncoder.encode(password));
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
    return user;
  }
}
