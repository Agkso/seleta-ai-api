package com.seletoai.core.domain.processoSeletivo;

import com.seletoai.core.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "processo_auditoria")
public class ProcessoAuditoria extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "processo_id", nullable = false)
  private ProcessoSeletivo processo;

  @Column(name = "tipo_evento", nullable = false, length = 64)
  private String tipoEvento;

  @Column(columnDefinition = "TEXT")
  private String detalhe;
}
