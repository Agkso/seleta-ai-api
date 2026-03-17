package com.seletoai.adapters.persistence;

import com.seletoai.core.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringUserRepository
  extends JpaRepository<User, Long> {

  Optional<User> findByEmailAndDeletedAtIsNull(String email);
}