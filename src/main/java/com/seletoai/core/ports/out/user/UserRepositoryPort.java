package com.seletoai.core.ports.out.user;

import com.seletoai.core.domain.user.User;

import java.util.Optional;

public interface UserRepositoryPort {

  User save(User user);

  Optional<User> findByEmailAndDeletedAtIsNull(String email);

  Optional<User> findById(Long id);
}