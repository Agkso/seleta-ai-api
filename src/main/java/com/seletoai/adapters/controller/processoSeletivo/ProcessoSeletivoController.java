package com.seletoai.adapters.controller.processoSeletivo;

import com.seletoai.adapters.security.AuthenticatedUser;
import com.seletoai.core.domain.auth.AuthContext;
import com.seletoai.core.domain.processoSeletivo.ProcessoCargo;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.ports.in.processoSeletivo.AdicionarCargoAoProcessoUseCasePort;
import com.seletoai.core.ports.in.processoSeletivo.CancelarProcessoUseCasePort;
import com.seletoai.core.ports.in.processoSeletivo.EncerrarProcessoUseCasePort;
import com.seletoai.core.ports.in.processoSeletivo.IniciarProcessoUseCasePort;
import com.seletoai.core.ports.in.processoSeletivo.ProcessoSeletivoUseCasePort;
import com.seletoai.core.ports.in.processoSeletivo.PublicarProcessoUseCasePort;
import com.seletoai.dto.processoSeletivo.ProcessoSeletivoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/processos")
@RequiredArgsConstructor
public class ProcessoSeletivoController {

  private final ProcessoSeletivoUseCasePort processoSeletivoUseCase;
  private final PublicarProcessoUseCasePort publicarProcessoUseCase;
  private final IniciarProcessoUseCasePort iniciarProcessoUseCase;
  private final EncerrarProcessoUseCasePort encerrarProcessoUseCase;
  private final CancelarProcessoUseCasePort cancelarProcessoUseCase;
  private final AdicionarCargoAoProcessoUseCasePort adicionarCargoAoProcessoUseCase;

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'CONTRATANTE')")
  public ResponseEntity<ProcessoSeletivo> criarProcesso(
    @RequestBody ProcessoSeletivoDTO.ProcessoSeletivoRequest request,
    @AuthenticationPrincipal AuthenticatedUser principal
  ) {
    ProcessoSeletivo novoProcesso = processoSeletivoUseCase.criar(request, authContext(principal));
    return ResponseEntity.status(HttpStatus.CREATED).body(novoProcesso);
  }

  @GetMapping("/publicos")
  public ResponseEntity<List<ProcessoSeletivo>> listarProcessosPublicos() {
    return ResponseEntity.ok(processoSeletivoUseCase.listarPublicos());
  }

  @PostMapping("/{id}/cargos")
  @PreAuthorize("hasAnyRole('ADMIN', 'CONTRATANTE')")
  public ResponseEntity<ProcessoCargo> adicionarCargo(
    @PathVariable Long id,
    @RequestBody ProcessoSeletivoDTO.AdicionarCargoRequest request,
    @AuthenticationPrincipal AuthenticatedUser principal
  ) {
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(adicionarCargoAoProcessoUseCase.execute(id, request, authContext(principal)));
  }

  @PostMapping("/{id}/publicar")
  @PreAuthorize("hasAnyRole('ADMIN', 'CONTRATANTE')")
  public ResponseEntity<ProcessoSeletivo> publicar(@PathVariable Long id, @AuthenticationPrincipal AuthenticatedUser principal) {
    return ResponseEntity.ok(publicarProcessoUseCase.execute(id, authContext(principal)));
  }

  @PostMapping("/{id}/iniciar")
  @PreAuthorize("hasAnyRole('ADMIN', 'CONTRATANTE')")
  public ResponseEntity<ProcessoSeletivo> iniciar(@PathVariable Long id, @AuthenticationPrincipal AuthenticatedUser principal) {
    return ResponseEntity.ok(iniciarProcessoUseCase.execute(id, authContext(principal)));
  }

  @PostMapping("/{id}/encerrar")
  @PreAuthorize("hasAnyRole('ADMIN', 'CONTRATANTE')")
  public ResponseEntity<ProcessoSeletivo> encerrar(@PathVariable Long id, @AuthenticationPrincipal AuthenticatedUser principal) {
    return ResponseEntity.ok(encerrarProcessoUseCase.execute(id, authContext(principal)));
  }

  @PostMapping("/{id}/cancelar")
  @PreAuthorize("hasAnyRole('ADMIN', 'CONTRATANTE')")
  public ResponseEntity<ProcessoSeletivo> cancelar(@PathVariable Long id, @AuthenticationPrincipal AuthenticatedUser principal) {
    return ResponseEntity.ok(cancelarProcessoUseCase.execute(id, authContext(principal)));
  }

  private AuthContext authContext(AuthenticatedUser principal) {
    return new AuthContext(principal.getUserId(), principal.getInstituicaoId(), principal.isAdmin());
  }
}
