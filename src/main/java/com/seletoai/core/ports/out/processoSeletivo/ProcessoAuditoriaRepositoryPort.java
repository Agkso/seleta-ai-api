package com.seletoai.core.ports.out.processoSeletivo;

import com.seletoai.core.domain.processoSeletivo.ProcessoAuditoria;

public interface ProcessoAuditoriaRepositoryPort {

  ProcessoAuditoria save(ProcessoAuditoria auditoria);
}
