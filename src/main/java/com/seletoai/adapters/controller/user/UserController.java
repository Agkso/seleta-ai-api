package com.seletoai.adapters.controller.user;

import com.seletoai.core.ports.in.user.CreateUserUseCasePort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  private final CreateUserUseCasePort createUserUseCase;

  public UserController(CreateUserUseCasePort createUserUseCase) {
    this.createUserUseCase = createUserUseCase;
  }

}