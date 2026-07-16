package com.seletoai.adapters.controller.exception;

import com.seletoai.core.domain.exception.RecursoNaoEncontradoException;
import com.seletoai.core.domain.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(RecursoNaoEncontradoException.class)
  public ProblemDetail handleNotFound(RecursoNaoEncontradoException ex) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    problem.setType(URI.create("urn:seletoai:erro:recurso-nao-encontrado"));
    return problem;
  }

  @ExceptionHandler(RegraNegocioException.class)
  public ProblemDetail handleRegraNegocio(RegraNegocioException ex) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    problem.setType(URI.create("urn:seletoai:erro:regra-negocio"));
    return problem;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
    String detail = ex.getBindingResult().getFieldErrors().stream()
      .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
      .collect(Collectors.joining("; "));
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
    problem.setType(URI.create("urn:seletoai:erro:validacao"));
    return problem;
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleGeneric(Exception ex) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno inesperado.");
    problem.setType(URI.create("urn:seletoai:erro:interno"));
    return problem;
  }
}
