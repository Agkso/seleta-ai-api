package com.seletoai.adapters.controller;

import com.seletoai.core.useCase.CreateUserUseCase;
import com.seletoai.core.useCase.LoginUseCase;
import com.seletoai.dto.auth.AuthResponseDTO;
import com.seletoai.dto.login.LoginRequestDTO;
import com.seletoai.dto.register.RegisterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final LoginUseCase loginUseCase;
  private final CreateUserUseCase createUserUseCase;

  @PostMapping("/login")
  public AuthResponseDTO login(@RequestBody LoginRequestDTO dto) {
    return loginUseCase.execute(dto);
  }

  @PostMapping("/register")
  public void register(@RequestBody RegisterRequestDTO dto) {
    createUserUseCase.execute(dto);
  }
}
