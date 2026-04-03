package com.seletoai.core.domain.analytics;

import com.seletoai.core.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "insights")
public class Insight extends BaseEntity {

  @Column(name = "process_id", nullable = false)
  private Long processId;

  private String type;
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;
}