package com.seletoai.core.ports.out.processoSeletivo;

import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;

import java.util.List;
import java.util.Optional;

public interface ProcessoSeletivoRepositoryPort {

  ProcessoSeletivo save(ProcessoSeletivo processoSeletivo);

  Optional<ProcessoSeletivo> findById(Integer id);

  List<ProcessoSeletivo> findAllByInstituicaoId(Integer instituicaoId);

  List<ProcessoSeletivo> findAllByStatus(String status);
}