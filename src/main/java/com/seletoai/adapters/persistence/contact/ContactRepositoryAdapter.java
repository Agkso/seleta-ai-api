package com.seletoai.adapters.persistence.contact;

import com.seletoai.core.domain.contact.Contact;
import com.seletoai.core.ports.repository.ContactRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactRepositoryAdapter
  implements ContactRepositoryPort {

  private final SpringContactRepository repository;

  @Override
  public Contact save(Contact contact) {
    return repository.save(contact);
  }
}