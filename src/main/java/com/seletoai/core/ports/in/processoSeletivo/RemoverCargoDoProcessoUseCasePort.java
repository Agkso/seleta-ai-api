package com.seletoai.core.ports.in.processoSeletivo;

import com.seletoai.core.domain.auth.AuthContext;

public interface RemoverCargoDoProcessoUseCasePort {

  void execute(Long processoId, Long cargoId, AuthContext authContext);
}
