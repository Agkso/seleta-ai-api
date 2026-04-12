package com.seletoai.core.domain.processoSeletivo;

import com.seletoai.core.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "processo_cargos")
public class ProcessoCargo extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "processo_id", nullable = false)
  private ProcessoSeletivo processo;

  @Column(nullable = false)
  private String titulo;
}
