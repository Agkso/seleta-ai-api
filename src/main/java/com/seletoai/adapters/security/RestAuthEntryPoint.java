package com.seletoai.adapters.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seletoai.dto.error.ErroDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class RestAuthEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
    throws java.io.IOException {
    ErroDTO.ErroResponse body = ErroDTO.ErroResponse.builder()
      .status(HttpStatus.UNAUTHORIZED.value())
      .erro("nao_autenticado")
      .mensagem("Autenticação necessária para acessar este recurso.")
      .timestamp(Instant.now())
      .path(request.getRequestURI())
      .detalhes(null)
      .build();

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(objectMapper.writeValueAsString(body));
  }
}
