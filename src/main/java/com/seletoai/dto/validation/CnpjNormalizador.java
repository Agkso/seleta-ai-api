package com.seletoai.dto.validation;

public final class CnpjNormalizador {

  private CnpjNormalizador() {}

  public static String normalizar(String cnpj) {
    return cnpj == null ? null : cnpj.replaceAll("\\D", "");
  }
}
