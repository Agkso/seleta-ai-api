package com.seletoai.adapters.persistence.user;

import com.seletoai.core.domain.user.User;
import com.seletoai.core.ports.repository.user.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

  private final SpringUserRepository repository;

  @Override
  public User save(User user) {
    return repository.save(user);
  }

  @Override
  public Optional<User> findByEmailAndDeletedAtIsNull(String email) {
    return repository.findByEmailAndDeletedAtIsNull(email);
  }

  @Override
  public Optional<User> findById(Long id) {
    return repository.findById(id);
  }
}