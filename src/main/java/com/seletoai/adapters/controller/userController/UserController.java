package com.seletoai.adapters.controller;

import com.seletoai.core.domain.user.User;
import com.seletoai.core.useCase.CreateUserUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  private final CreateUserUseCase createUserUseCase;

  public UserController(CreateUserUseCase createUserUseCase) {
    this.createUserUseCase = createUserUseCase;
  }

}