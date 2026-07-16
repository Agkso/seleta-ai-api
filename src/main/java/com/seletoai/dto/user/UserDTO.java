package com.seletoai.dto.user;

import java.util.List;

public final class UserDTO {

  private UserDTO() {}

  public record MeResponse(
    Long id,
    String name,
    String email,
    List<String> roles,
    Long instituicaoId
  ) {}
}
