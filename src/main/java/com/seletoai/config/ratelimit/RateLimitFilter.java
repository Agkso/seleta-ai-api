package com.seletoai.config.ratelimit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seletoai.dto.error.ErroDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Deque;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Rate limiting em memória, por IP, restrito às rotas públicas mais sensíveis a
 * brute force/spam ({@code /auth/login} e {@code /auth/onboarding}).
 * Não é distribuído: numa implantação com múltiplas instâncias, migrar para Redis.
 */
@Component
public class RateLimitFilter extends OncePerRequestFilter {

  private static final Set<String> PATHS_LIMITADOS = Set.of("/auth/login", "/auth/onboarding");

  @Value("${rate-limit.max-requests:10}")
  private int maxRequests;

  @Value("${rate-limit.window-seconds:60}")
  private long windowSeconds;

  private final ObjectMapper objectMapper;
  private final ConcurrentHashMap<String, Deque<Long>> historico = new ConcurrentHashMap<>();

  public RateLimitFilter(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return !PATHS_LIMITADOS.contains(request.getServletPath());
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {

    String chave = clienteId(request) + ":" + request.getServletPath();
    long agora = System.currentTimeMillis();
    long janelaMs = windowSeconds * 1000;

    Deque<Long> timestamps = historico.computeIfAbsent(chave, k -> new ConcurrentLinkedDeque<>());
    boolean permitido;
    synchronized (timestamps) {
      while (!timestamps.isEmpty() && agora - timestamps.peekFirst() > janelaMs) {
        timestamps.pollFirst();
      }
      permitido = timestamps.size() < maxRequests;
      if (permitido) {
        timestamps.addLast(agora);
      } else if (timestamps.isEmpty()) {
        historico.remove(chave, timestamps);
      }
    }

    if (!permitido) {
      responderLimiteExcedido(request, response);
      return;
    }

    filterChain.doFilter(request, response);
  }

  private String clienteId(HttpServletRequest request) {
    String forwarded = request.getHeader("X-Forwarded-For");
    if (forwarded != null && !forwarded.isBlank()) {
      return forwarded.split(",")[0].trim();
    }
    return request.getRemoteAddr();
  }

  private void responderLimiteExcedido(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ErroDTO.ErroResponse body = ErroDTO.ErroResponse.builder()
      .status(HttpStatus.TOO_MANY_REQUESTS.value())
      .erro("limite_requisicoes_excedido")
      .mensagem("Muitas tentativas. Aguarde antes de tentar novamente.")
      .timestamp(Instant.now())
      .path(request.getRequestURI())
      .detalhes(null)
      .build();

    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(objectMapper.writeValueAsString(body));
  }
}
