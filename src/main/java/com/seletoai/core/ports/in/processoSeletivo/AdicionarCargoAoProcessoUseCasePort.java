package com.seletoai.core.ports.in.processoSeletivo;

import com.seletoai.core.domain.processoSeletivo.ProcessoCargo;
import com.seletoai.dto.processoSeletivo.ProcessoSeletivoDTO;

public interface AdicionarCargoAoProcessoUseCasePort {

  ProcessoCargo execute(Long processoId, ProcessoSeletivoDTO.AdicionarCargoRequest request);
}
