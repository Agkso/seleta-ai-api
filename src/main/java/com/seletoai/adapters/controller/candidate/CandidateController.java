package com.seletoai.adapters.controller.candidate;

import com.seletoai.adapters.security.AuthenticatedUser;
import com.seletoai.core.domain.auth.AuthContext;
import com.seletoai.core.ports.in.candidate.AtualizarStatusCandidatoUseCasePort;
import com.seletoai.core.ports.in.candidate.InscreverCandidatoUseCasePort;
import com.seletoai.core.ports.in.candidate.ListarCandidatosUseCasePort;
import com.seletoai.dto.candidate.CandidateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CandidateController {

  private final InscreverCandidatoUseCasePort inscreverCandidatoUseCase;
  private final ListarCandidatosUseCasePort listarCandidatosUseCase;
  private final AtualizarStatusCandidatoUseCasePort atualizarStatusUseCase;

  @PostMapping("/api/processos/{processoId}/inscricao")
  @PreAuthorize("hasRole('CANDIDATO')")
  public ResponseEntity<CandidateDTO.CandidateResponse> inscrever(
    @PathVariable Long processoId,
    @RequestBody @Valid CandidateDTO.InscricaoRequest request,
    @AuthenticationPrincipal AuthenticatedUser principal
  ) {
    AuthContext authContext = new AuthContext(principal.getUserId(), principal.getInstituicaoId(), principal.isAdmin());
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(inscreverCandidatoUseCase.execute(processoId, request, authContext));
  }

  @GetMapping("/api/processos/{processoId}/candidatos")
  @PreAuthorize("hasAnyRole('ADMIN', 'CONTRATANTE')")
  public ResponseEntity<Page<CandidateDTO.CandidateResponse>> listar(
    @PathVariable Long processoId,
    @PageableDefault(size = 20, sort = "id") Pageable pageable,
    @AuthenticationPrincipal AuthenticatedUser principal
  ) {
    AuthContext authContext = new AuthContext(principal.getUserId(), principal.getInstituicaoId(), principal.isAdmin());
    return ResponseEntity.ok(listarCandidatosUseCase.execute(processoId, pageable, authContext));
  }

  @PatchMapping("/api/candidatos/{candidatoId}/status")
  @PreAuthorize("hasAnyRole('ADMIN', 'CONTRATANTE')")
  public ResponseEntity<CandidateDTO.CandidateResponse> atualizarStatus(
    @PathVariable Long candidatoId,
    @RequestBody @Valid CandidateDTO.AtualizarStatusRequest request,
    @AuthenticationPrincipal AuthenticatedUser principal
  ) {
    AuthContext authContext = new AuthContext(principal.getUserId(), principal.getInstituicaoId(), principal.isAdmin());
    return ResponseEntity.ok(atualizarStatusUseCase.execute(candidatoId, request, authContext));
  }
}
