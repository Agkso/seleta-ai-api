package com.seletoai.core.ports.in.user;

import com.seletoai.dto.auth.AuthDTO;

public interface CreateUserUseCasePort {

  AuthDTO.AuthResponse execute(AuthDTO.RegisterRequest dto);
}
