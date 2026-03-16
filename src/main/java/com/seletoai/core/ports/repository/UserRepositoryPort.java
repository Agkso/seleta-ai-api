package com.seletoai.core.ports.repository;

import com.seletoai.core.domain.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {

  User save(User user);

  Optional<User> findById(UUID id);

  Optional<User> findByEmail(String email);

}