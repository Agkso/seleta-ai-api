package com.seletoai.adapters.persistence;

import com.seletoai.core.domain.user.User;
import com.seletoai.core.ports.repository.UserRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {

  private final SpringUserRepository repository;

  public UserRepositoryAdapter(SpringUserRepository repository) {
    this.repository = repository;
  }

  @Override
  public User save(User user) {
    return repository.save(user);
  }

  @Override
  public Optional<User> findById(UUID id) {
    return repository.findById(id);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return repository.findByEmail(email);
  }
}