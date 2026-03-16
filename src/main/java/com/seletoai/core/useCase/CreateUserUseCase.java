package com.seletoai.core.useCase;

import com.seletoai.core.domain.user.User;
import com.seletoai.core.ports.repository.UserRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

  private final UserRepositoryPort repository;

  public CreateUserUseCase(UserRepositoryPort repository) {
    this.repository = repository;
  }

  public User execute(User user) {

    repository.findByEmail(user.getEmail())
      .ifPresent(u -> {
        throw new RuntimeException("Email already exists");
      });

    return repository.save(user);
  }

}