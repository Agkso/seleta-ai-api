package com.seletoai.core.ports.in.auth;

public interface RefreshTokenUseCasePort {

  String execute(String refreshToken);
}
