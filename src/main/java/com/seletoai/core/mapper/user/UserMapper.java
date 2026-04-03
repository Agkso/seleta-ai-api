package com.seletoai.core.mapper.user;

import com.seletoai.core.domain.user.User;
import com.seletoai.dto.auth.AuthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final PasswordEncoder encoder;

  public User toEntity(AuthDTO.RegisterRequest dto) {
    User user = new User();
    user.setName(dto.name());
    user.setEmail(dto.email());
    user.setPassword(encoder.encode(dto.password()));
    return user;
  }
}