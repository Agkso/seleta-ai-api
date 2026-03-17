package com.seletoai.adapters.persistence.contact;

import com.seletoai.core.domain.contact.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringContactRepository
  extends JpaRepository<Contact, Long> {
}