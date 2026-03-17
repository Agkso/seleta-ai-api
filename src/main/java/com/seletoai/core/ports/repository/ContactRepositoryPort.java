package com.seletoai.core.ports.repository;

import com.seletoai.core.domain.contact.Contact;

public interface ContactRepositoryPort {

  Contact save(Contact contact);
}