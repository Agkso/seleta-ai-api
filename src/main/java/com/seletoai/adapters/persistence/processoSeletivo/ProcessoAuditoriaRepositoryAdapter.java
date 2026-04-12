package com.seletoai.adapters.persistence.processoSeletivo;

import com.seletoai.core.domain.processoSeletivo.ProcessoAuditoria;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoAuditoriaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessoAuditoriaRepositoryAdapter implements ProcessoAuditoriaRepositoryPort {

  private final SpringProcessoAuditoriaRepository repository;

  @Override
  public ProcessoAuditoria save(ProcessoAuditoria auditoria) {
    return repository.save(auditoria);
  }
}
