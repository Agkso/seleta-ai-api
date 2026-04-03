package com.seletoai.core.domain.analytics;

import com.seletoai.core.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "analytics_snapshots")
public class AnalyticsSnapshot extends BaseEntity {

  @Column(name = "process_id", nullable = false)
  private Long processId;

  private LocalDate date;

  private Integer totalInscricoes;
  private Integer totalAprovados;
  private Integer totalReprovados;
}