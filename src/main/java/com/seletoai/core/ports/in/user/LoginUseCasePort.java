package com.seletoai.core.ports.in.user;

import com.seletoai.dto.auth.AuthDTO;

public interface LoginUseCasePort {

  AuthDTO.AuthResponse execute(AuthDTO.LoginRequest request);
}
