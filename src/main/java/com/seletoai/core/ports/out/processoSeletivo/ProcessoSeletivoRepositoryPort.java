package com.seletoai.core.ports.out.processoSeletivo;

import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProcessoSeletivoRepositoryPort {

  ProcessoSeletivo save(ProcessoSeletivo processoSeletivo);

  Optional<ProcessoSeletivo> findById(Long id);

  List<ProcessoSeletivo> findAllByInstituicaoId(Long instituicaoId);

  Page<ProcessoSeletivo> findAllByStatusCodigo(String statusCodigo, Pageable pageable);
}