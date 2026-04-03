package com.seletoai.adapters.controller.auth;

import com.seletoai.core.ports.in.auth.RefreshTokenUseCasePort;
import com.seletoai.core.ports.in.user.CreateUserUseCasePort;
import com.seletoai.core.ports.in.user.LoginUseCasePort;
import com.seletoai.dto.auth.AuthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final LoginUseCasePort loginUseCase;
  private final RefreshTokenUseCasePort refreshTokenUseCase;
  private final CreateUserUseCasePort createUserUseCase;

  @PostMapping("/login")
  public ResponseEntity<AuthDTO.AuthResponse> login(@RequestBody AuthDTO.LoginRequest dto) {
    return ResponseEntity.ok(loginUseCase.execute(dto));
  }

  @PostMapping("/register")
  public ResponseEntity<AuthDTO.AuthResponse> register(@RequestBody AuthDTO.RegisterRequest dto) {
    return ResponseEntity.ok(createUserUseCase.execute(dto));
  }

  @PostMapping("/refresh")
  public Map<String, String> refresh(@RequestBody Map<String, String> body) {
    String newAccessToken = refreshTokenUseCase.execute(body.get("refreshToken"));
    return Map.of("accessToken", newAccessToken);
  }
}