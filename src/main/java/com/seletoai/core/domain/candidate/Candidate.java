package com.seletoai.core.domain.candidate;

import com.seletoai.core.domain.base.BaseEntity;
import com.seletoai.core.domain.status.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "candidatos")
public class Candidate extends BaseEntity {

  private String nome;

  private String cpf;

  private String email;

  @ManyToOne
  @JoinColumn(name = "status_id")
  private Status status;

  @Column(name = "processo_id")
  private Long processoId;

  @Column(name = "cargo_id")
  private Long cargoId;
}