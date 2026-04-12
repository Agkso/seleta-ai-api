package com.seletoai.core.ports.out.processoSeletivo;

import com.seletoai.core.domain.processoSeletivo.ProcessoCargo;

public interface ProcessoCargoRepositoryPort {

  ProcessoCargo save(ProcessoCargo cargo);

  long countByProcessoId(Long processoId);

  boolean existsByIdAndProcesso_Id(Long cargoId, Long processoId);
}
