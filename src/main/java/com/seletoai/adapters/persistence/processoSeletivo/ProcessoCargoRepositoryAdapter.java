package com.seletoai.adapters.persistence.processoSeletivo;

import com.seletoai.core.domain.processoSeletivo.ProcessoCargo;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoCargoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessoCargoRepositoryAdapter implements ProcessoCargoRepositoryPort {

  private final SpringProcessoCargoRepository repository;

  @Override
  public ProcessoCargo save(ProcessoCargo cargo) {
    return repository.save(cargo);
  }

  @Override
  public long countByProcessoId(Long processoId) {
    return repository.countByProcesso_Id(processoId);
  }

  @Override
  public boolean existsByIdAndProcesso_Id(Long cargoId, Long processoId) {
    return repository.existsByIdAndProcesso_Id(cargoId, processoId);
  }
}
