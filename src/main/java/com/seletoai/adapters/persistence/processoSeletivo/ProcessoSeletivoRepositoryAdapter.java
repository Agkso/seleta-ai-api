package com.seletoai.adapters.persistence.processoSeletivo;

import com.seletoai.adapters.persistence.processoSeletivo.SpringProcessoSeletivoRepository;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoSeletivoRepositoryPort;
import lombok.RequiredArgsConstructor;
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
  public List<ProcessoSeletivo> findAllByStatus(String status) {
    return repository.findAllByStatus(status);
  }
}