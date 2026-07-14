package com.seletoai.core.ports.in.onboarding;

import com.seletoai.dto.auth.AuthDTO;
import com.seletoai.dto.onboarding.OnboardingDTO;

public interface OnboardingUseCasePort {

  AuthDTO.AuthResponse execute(OnboardingDTO.OnboardingRequest request);
}
