package com.seletoai.core.domain.contact;

import com.seletoai.core.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "contacts")
public class Contact extends BaseEntity {

  private String phone;
  private String whatsapp;
  private String email;

  @Column(name = "user_id", nullable = false)
  private Long userId;
}