package com.seletoai.adapters.persistence.processoSeletivo;

import com.seletoai.adapters.persistence.base.BaseRepository;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpringProcessoSeletivoRepository extends BaseRepository<ProcessoSeletivo> {

  List<ProcessoSeletivo> findAllByInstituicaoId(Long instituicaoId);

  Page<ProcessoSeletivo> findAllByInstituicaoId(Long instituicaoId, Pageable pageable);

  Page<ProcessoSeletivo> findAllByStatus_Codigo(String statusCodigo, Pageable pageable);
}