package com.seletoai.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNPJValidator implements ConstraintValidator<CNPJ, String> {

  private static final int[] PESOS_DV1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
  private static final int[] PESOS_DV2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

  @Override
  public boolean isValid(String valor, ConstraintValidatorContext context) {
    if (valor == null || valor.isBlank()) {
      return false;
    }
    String digitos = valor.replaceAll("\\D", "");
    if (digitos.length() != 14 || digitos.chars().distinct().count() == 1) {
      return false;
    }

    String base = digitos.substring(0, 12);
    int dv1 = calcularDigito(base, PESOS_DV1);
    int dv2 = calcularDigito(base + dv1, PESOS_DV2);

    return digitos.equals(base + dv1 + dv2);
  }

  private int calcularDigito(String base, int[] pesos) {
    int soma = 0;
    for (int i = 0; i < base.length(); i++) {
      soma += Character.getNumericValue(base.charAt(i)) * pesos[i];
    }
    int resto = soma % 11;
    return resto < 2 ? 0 : 11 - resto;
  }
}
