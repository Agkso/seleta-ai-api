package com.seletoai.adapters.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {


  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


    if (!email.equals("admin@seletoai.com")) {
      throw new UsernameNotFoundException("Usuário não encontrado");
    }

    return new User(
      "admin@seletoai.com",
      "", // A senha real viria do banco, criptografada em BCrypt
      new ArrayList<>() // Aqui virão as Roles (CONTRATANTE ou FORNECEDOR)
    );
  }
}