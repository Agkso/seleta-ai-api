package com.seletoai.adapters.controller.error;

import com.seletoai.core.domain.exception.AcessoNegadoException;
import com.seletoai.core.domain.exception.CredenciaisInvalidasException;
import com.seletoai.core.domain.exception.RecursoNaoEncontradoException;
import com.seletoai.core.domain.exception.RegraNegocioException;
import com.seletoai.dto.error.ErroDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(RegraNegocioException.class)
  public ResponseEntity<ErroDTO.ErroResponse> handleRegraNegocio(RegraNegocioException ex, HttpServletRequest request) {
    return build(HttpStatus.BAD_REQUEST, "regra_negocio", ex.getMessage(), request, null);
  }

  @ExceptionHandler(RecursoNaoEncontradoException.class)
  public ResponseEntity<ErroDTO.ErroResponse> handleNaoEncontrado(RecursoNaoEncontradoException ex, HttpServletRequest request) {
    return build(HttpStatus.NOT_FOUND, "recurso_nao_encontrado", ex.getMessage(), request, null);
  }

  @ExceptionHandler(AcessoNegadoException.class)
  public ResponseEntity<ErroDTO.ErroResponse> handleAcessoNegado(AcessoNegadoException ex, HttpServletRequest request) {
    return build(HttpStatus.FORBIDDEN, "acesso_negado", ex.getMessage(), request, null);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErroDTO.ErroResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
    return build(HttpStatus.FORBIDDEN, "acesso_negado", "Você não tem permissão para executar esta ação.", request, null);
  }

  @ExceptionHandler({CredenciaisInvalidasException.class, BadCredentialsException.class})
  public ResponseEntity<ErroDTO.ErroResponse> handleCredenciaisInvalidas(RuntimeException ex, HttpServletRequest request) {
    return build(HttpStatus.UNAUTHORIZED, "credenciais_invalidas", ex.getMessage(), request, null);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErroDTO.ErroResponse> handleValidacao(MethodArgumentNotValidException ex, HttpServletRequest request) {
    List<ErroDTO.CampoErro> detalhes = ex.getBindingResult().getFieldErrors().stream()
      .map(fe -> new ErroDTO.CampoErro(fe.getField(), fe.getDefaultMessage()))
      .toList();
    return build(HttpStatus.BAD_REQUEST, "validacao", "Dados inválidos.", request, detalhes);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErroDTO.ErroResponse> handleIntegridade(DataIntegrityViolationException ex, HttpServletRequest request) {
    log.warn("Violação de integridade em {}: {}", request.getRequestURI(), ex.getMessage());
    return build(HttpStatus.CONFLICT, "dado_duplicado", "Já existe um registro com esses dados.", request, null);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErroDTO.ErroResponse> handleGenerico(Exception ex, HttpServletRequest request) {
    log.error("Erro não tratado em {}", request.getRequestURI(), ex);
    return build(HttpStatus.INTERNAL_SERVER_ERROR, "erro_interno", "Erro interno inesperado.", request, null);
  }

  private ResponseEntity<ErroDTO.ErroResponse> build(
    HttpStatus status, String erro, String mensagem, HttpServletRequest request, List<ErroDTO.CampoErro> detalhes
  ) {
    ErroDTO.ErroResponse body = ErroDTO.ErroResponse.builder()
      .status(status.value())
      .erro(erro)
      .mensagem(mensagem)
      .timestamp(Instant.now())
      .path(request.getRequestURI())
      .detalhes(detalhes)
      .build();
    return ResponseEntity.status(status).body(body);
  }
}
