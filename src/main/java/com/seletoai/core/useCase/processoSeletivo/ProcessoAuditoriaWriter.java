package com.seletoai.core.useCase.processoSeletivo;

import com.seletoai.core.domain.processoSeletivo.ProcessoAuditoria;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoAuditoriaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessoAuditoriaWriter {

  private final ProcessoAuditoriaRepositoryPort repository;

  public void registrar(ProcessoSeletivo processo, String tipoEvento, String detalhe) {
    ProcessoAuditoria row = new ProcessoAuditoria();
    row.setProcesso(processo);
    row.setTipoEvento(tipoEvento);
    row.setDetalhe(detalhe);
    repository.save(row);
  }
}
