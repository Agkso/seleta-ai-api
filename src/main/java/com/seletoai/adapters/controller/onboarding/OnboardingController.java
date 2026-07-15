package com.seletoai.adapters.controller.onboarding;

import com.seletoai.core.ports.in.onboarding.OnboardingUseCasePort;
import com.seletoai.dto.auth.AuthDTO;
import com.seletoai.dto.onboarding.OnboardingDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/onboarding")
@RequiredArgsConstructor
public class OnboardingController {

  private final OnboardingUseCasePort onboardingUseCase;

  @PostMapping
  public ResponseEntity<AuthDTO.AuthResponse> onboard(@RequestBody @Valid OnboardingDTO.OnboardingRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(onboardingUseCase.execute(request));
  }
}
