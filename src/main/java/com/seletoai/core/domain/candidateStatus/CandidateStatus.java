package com.seletoai.core.domain.candidateStatus;

import com.seletoai.core.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "candidate_status")
public class CandidateStatus extends BaseEntity {

  @Column(unique = true, nullable = false)
  private String code;

  private String name;

  private String description;

  private Boolean active = true;
}