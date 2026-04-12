package com.seletoai.adapters.persistence.processoSeletivo;

import com.seletoai.adapters.persistence.base.BaseRepository;
import com.seletoai.core.domain.processoSeletivo.ProcessoCargo;

public interface SpringProcessoCargoRepository extends BaseRepository<ProcessoCargo> {

  long countByProcesso_Id(Long processoId);

  boolean existsByIdAndProcesso_Id(Long cargoId, Long processoId);
}
