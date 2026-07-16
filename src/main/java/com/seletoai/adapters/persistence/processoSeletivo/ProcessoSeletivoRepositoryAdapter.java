package com.seletoai.adapters.persistence.processoSeletivo;

import com.seletoai.adapters.persistence.processoSeletivo.SpringProcessoSeletivoRepository;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoSeletivoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProcessoSeletivoRepositoryAdapter implements ProcessoSeletivoRepositoryPort {

  private final SpringProcessoSeletivoRepository repository;

  @Override
  public ProcessoSeletivo save(ProcessoSeletivo processoSeletivo) {
    return repository.save(processoSeletivo);
  }

  @Override
  public Optional<ProcessoSeletivo> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  public List<ProcessoSeletivo> findAllByInstituicaoId(Long instituicaoId) {
    return repository.findAllByInstituicaoId(instituicaoId);
  }

  @Override
  public Page<ProcessoSeletivo> findAllByStatusCodigo(String statusCodigo, Pageable pageable) {
    return repository.findAllByStatus_Codigo(statusCodigo, pageable);
  }

  @Override
  public Page<ProcessoSeletivo> findAllByInstituicaoId(Long instituicaoId, Pageable pageable) {
    return repository.findAllByInstituicaoId(instituicaoId, pageable);
  }

  @Override
  public void deleteById(Long id) {
    repository.deleteById(id);
  }
}