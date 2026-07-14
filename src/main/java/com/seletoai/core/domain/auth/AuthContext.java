package com.seletoai.core.domain.auth;

import com.seletoai.core.domain.exception.AcessoNegadoException;

public record AuthContext(Long userId, Long instituicaoId, boolean admin) {

  public void garantirAcessoInstituicao(Long instituicaoIdDoRecurso) {
    if (admin) {
      return;
    }
    if (instituicaoId == null || !instituicaoId.equals(instituicaoIdDoRecurso)) {
      throw new AcessoNegadoException("Usuário não pertence à instituição deste recurso.");
    }
  }
}
