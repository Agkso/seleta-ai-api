package com.seletoai.core.domain.status;

import com.seletoai.core.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "status")
public class Status extends BaseEntity {

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String codigo;

  private String descricao;

  private Integer ordem;

  private Boolean ativo = true;

  @ManyToOne
  @JoinColumn(name = "tipo_status_id", nullable = false)
  private TipoStatus tipoStatus;
}