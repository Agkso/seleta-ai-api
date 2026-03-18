package com.seletoai.adapters.controller.user;

import com.seletoai.core.useCase.user.CreateUserUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  private final CreateUserUseCase createUserUseCase;

  public UserController(CreateUserUseCase createUserUseCase) {
    this.createUserUseCase = createUserUseCase;
  }

}