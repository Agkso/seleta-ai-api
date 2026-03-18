package com.seletoai.adapters.controller.auth;

import com.seletoai.core.useCase.auth.RefreshTokenUseCase;
import com.seletoai.core.useCase.user.CreateUserUseCase;
import com.seletoai.core.useCase.user.LoginUseCase;
import com.seletoai.dto.auth.AuthResponseDTO;
import com.seletoai.dto.login.LoginRequestDTO;
import com.seletoai.dto.register.RegisterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final LoginUseCase loginUseCase;
  private final RefreshTokenUseCase refreshTokenUseCase;
  private final CreateUserUseCase createUserUseCase;

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO dto) {
    return ResponseEntity.ok(loginUseCase.execute(dto));
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO dto) {
    return ResponseEntity.ok(createUserUseCase.execute(dto));
  }

  @PostMapping("/refresh")
  public Map<String, String> refresh(@RequestBody Map<String, String> body) {
    String newAccessToken = refreshTokenUseCase.execute(body.get("refreshToken"));
    return Map.of("accessToken", newAccessToken);
  }
}