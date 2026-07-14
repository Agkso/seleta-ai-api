package com.seletoai.core.domain.user;

import com.seletoai.core.domain.base.BaseEntity;
import com.seletoai.core.domain.instituicao.Instituicao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "instituicao_id")
  private Instituicao instituicao;
}