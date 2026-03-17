package com.seletoai.dto.register;

public record RegisterRequestDTO(

  String name,
  String email,
  String password,
  Integer roleId

) {}