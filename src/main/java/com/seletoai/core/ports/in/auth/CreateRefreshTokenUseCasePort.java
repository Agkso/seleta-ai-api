package com.seletoai.core.ports.in.auth;

import com.seletoai.core.domain.auth.RefreshToken;

public interface CreateRefreshTokenUseCasePort {

  RefreshToken execute(Long userId);
}
