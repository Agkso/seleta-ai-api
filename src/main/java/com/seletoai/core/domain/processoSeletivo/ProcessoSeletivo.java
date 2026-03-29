package com.seletoai.core.domain.processoSeletivo;

import com.seletoai.core.domain.base.BaseEntity;
import com.seletoai.core.domain.instituicao.Instituicao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "processos_seletivos")
public class ProcessoSeletivo extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "instituicao_id", nullable = false)
  private Instituicao instituicao;

  @Column(nullable = false)
  private String titulo;

  @Column(nullable = false, unique = true)
  private String numeroEdital;

  @Column(nullable = false)
  private LocalDateTime dataInicioInscricao;

  @Column(nullable = false)
  private LocalDateTime dataFimInscricao;

  @Column(nullable = false)
  private String status = "RASCUNHO";
}