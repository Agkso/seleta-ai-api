package com.seletoai.core.ports.repository.contact;

import com.seletoai.core.domain.contact.Contact;

public interface ContactRepositoryPort {

  Contact save(Contact contact);
}