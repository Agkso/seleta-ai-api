package com.seletoai.core.ports.out.contact;

import com.seletoai.core.domain.contact.Contact;

public interface ContactRepositoryPort {

  Contact save(Contact contact);
}