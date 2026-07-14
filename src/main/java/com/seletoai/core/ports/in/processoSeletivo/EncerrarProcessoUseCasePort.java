package com.seletoai.core.ports.in.processoSeletivo;

import com.seletoai.core.domain.auth.AuthContext;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;

public interface EncerrarProcessoUseCasePort {

  ProcessoSeletivo execute(Long processoId, AuthContext authContext);
}
