package com.seletoai.core.domain.instituicao;

import com.seletoai.core.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "instituicoes")
public class Instituicao extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String cnpj;

  @Column(nullable = false)
  private String razaoSocial;

  @Column(nullable = false)
  private String nomeFantasia;
}