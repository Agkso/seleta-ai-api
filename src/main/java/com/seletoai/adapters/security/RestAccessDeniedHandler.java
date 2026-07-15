package com.seletoai.adapters.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seletoai.dto.error.ErroDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class RestAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
    throws java.io.IOException {
    ErroDTO.ErroResponse body = ErroDTO.ErroResponse.builder()
      .status(HttpStatus.FORBIDDEN.value())
      .erro("acesso_negado")
      .mensagem("Você não tem permissão para executar esta ação.")
      .timestamp(Instant.now())
      .path(request.getRequestURI())
      .detalhes(null)
      .build();

    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(objectMapper.writeValueAsString(body));
  }
}
