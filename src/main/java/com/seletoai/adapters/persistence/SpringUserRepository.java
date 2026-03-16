package com.seletoai.adapters.persistence;

import com.seletoai.core.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringUserRepository
  extends JpaRepository<User, UUID> {

  Optional<User> findByEmail(String email);

}