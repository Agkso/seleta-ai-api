package com.seletoai.adapters.controller.processoSeletivo;

import com.seletoai.adapters.security.AuthenticatedUser;
import com.seletoai.core.domain.auth.AuthContext;
import com.seletoai.core.domain.processoSeletivo.ProcessoCargo;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.ports.in.processoSeletivo.AdicionarCargoAoProcessoUseCasePort;
import com.seletoai.core.ports.in.processoSeletivo.CancelarProcessoUseCasePort;
import com.seletoai.core.ports.in.processoSeletivo.EncerrarProcessoUseCasePort;
import com.seletoai.core.ports.in.processoSeletivo.IniciarProcessoUseCasePort;
import com.seletoai.core.ports.in.processoSeletivo.ListarCargosDoProcessoUseCasePort;
import com.seletoai.core.ports.in.processoSeletivo.ProcessoSeletivoUseCasePort;
import com.seletoai.core.ports.in.processoSeletivo.PublicarProcessoUseCasePort;
import com.seletoai.core.ports.in.processoSeletivo.RemoverCargoDoProcessoUseCasePort;
import com.seletoai.dto.processoSeletivo.ProcessoSeletivoDTO;
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
  private final ListarCargosDoProcessoUseCasePort listarCargosUseCase;
  private final RemoverCargoDoProcessoUseCasePort removerCargoUseCase;

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'CONTRATANTE')")
  public ResponseEntity<ProcessoSeletivo> criarProcesso(
    @RequestBody @Valid ProcessoSeletivoDTO.ProcessoSeletivoRequest request,
    @AuthenticationPrincipal AuthenticatedUser principal
  ) {
    ProcessoSeletivo novoProcesso = processoSeletivoUseCase.criar(request, authContext(principal));
    return ResponseEntity.status(HttpStatus.CREATED).body(novoProcesso);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'CONTRATANTE')")
  public ResponseEntity<Page<ProcessoSeletivoDTO.ProcessoSeletivoResponse>> listar(
    @PageableDefault(size = 20, sort = "id") Pageable pageable,
    @AuthenticationPrincipal AuthenticatedUser principal
  ) {
    return ResponseEntity.ok(processoSeletivoUseCase.listar(pageable, authContext(principal)));
  }

  @GetMapping("/publicos")
  public ResponseEntity<Page<ProcessoSeletivo>> listarProcessosPublicos(
    @PageableDefault(size = 20, sort = "id") Pageable pageable
  ) {
    return ResponseEntity.ok(processoSeletivoUseCase.listarPublicos(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProcessoSeletivoDTO.ProcessoSeletivoResponse> buscar(@PathVariable Long id) {
    return ResponseEntity.ok(processoSeletivoUseCase.buscar(id));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN', 'CONTRATANTE')")
  public ResponseEntity<ProcessoSeletivo> atualizar(
    @PathVariable Long id,
    @RequestBody @Valid ProcessoSeletivoDTO.ProcessoSeletivoRequest request,
    @AuthenticationPrincipal AuthenticatedUser principal
  ) {
    return ResponseEntity.ok(processoSeletivoUseCase.atualizar(id, request, authContext(principal)));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN', 'CONTRATANTE')")
  public ResponseEntity<Void> excluir(
    @PathVariable Long id,
    @AuthenticationPrincipal AuthenticatedUser principal
  ) {
    processoSeletivoUseCase.excluir(id, authContext(principal));
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}/cargos")
  public ResponseEntity<List<ProcessoSeletivoDTO.CargoResponse>> listarCargos(@PathVariable Long id) {
    return ResponseEntity.ok(listarCargosUseCase.execute(id));
  }

  @PostMapping("/{id}/cargos")
  @PreAuthorize("hasAnyRole('ADMIN', 'CONTRATANTE')")
  public ResponseEntity<ProcessoCargo> adicionarCargo(
    @PathVariable Long id,
    @RequestBody @Valid ProcessoSeletivoDTO.AdicionarCargoRequest request,
    @AuthenticationPrincipal AuthenticatedUser principal
  ) {
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(adicionarCargoAoProcessoUseCase.execute(id, request, authContext(principal)));
  }

  @DeleteMapping("/{id}/cargos/{cargoId}")
  @PreAuthorize("hasAnyRole('ADMIN', 'CONTRATANTE')")
  public ResponseEntity<Void> removerCargo(
    @PathVariable Long id,
    @PathVariable Long cargoId,
    @AuthenticationPrincipal AuthenticatedUser principal
  ) {
    removerCargoUseCase.execute(id, cargoId, authContext(principal));
    return ResponseEntity.noContent().build();
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
