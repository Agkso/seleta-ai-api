package com.seletoai.core.domain.user;

import com.seletoai.core.domain.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = now() WHERE id=?")
@Where(clause = "deleted_at IS NULL")
public class User extends BaseEntity {

  private String name;

  private String email;

  private String password;

  private String role;

}