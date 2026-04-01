package com.seletoai.adapters.persistence.status;

import com.seletoai.core.domain.status.Status;
import com.seletoai.core.ports.out.status.StatusRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StatusRepositoryAdapter
  implements StatusRepositoryPort {

  private final SpringStatusRepository repository;

  @Override
  public Status findByCodigoAndTipo(String codigo, String tipoCodigo) {
    return repository.findByCodigoAndTipoStatus_Codigo(codigo, tipoCodigo)
      .orElseThrow(() -> new RuntimeException("Status não encontrado"));
  }

  @Override
  public List<Status> findByTipo(String tipoCodigo) {
    return repository.findByTipoStatus_Codigo(tipoCodigo);
  }
}