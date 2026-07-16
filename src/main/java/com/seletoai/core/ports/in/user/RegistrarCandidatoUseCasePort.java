package com.seletoai.core.ports.in.user;

import com.seletoai.dto.auth.AuthDTO;

public interface RegistrarCandidatoUseCasePort {

  AuthDTO.AuthResponse execute(AuthDTO.CandidatoRegisterRequest request);
}
