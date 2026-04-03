package com.seletoai.adapters.persistence.processoSeletivo;

import com.seletoai.adapters.persistence.base.BaseRepository;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;

import java.util.List;

public interface SpringProcessoSeletivoRepository extends BaseRepository<ProcessoSeletivo> {

  List<ProcessoSeletivo> findAllByInstituicaoId(Long instituicaoId);

  List<ProcessoSeletivo> findAllByStatus(String status);
}