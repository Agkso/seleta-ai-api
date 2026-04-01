package com.seletoai.core.domain.status;

import com.seletoai.core.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tipo_status")
public class TipoStatus extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String codigo; // CANDIDATE, DOCUMENTO, PROCESSO

  @Column(nullable = false)
  private String nome;

  private String descricao;

  private Boolean ativo = true;
}