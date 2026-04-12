package com.seletoai.adapters.controller.processoSeletivo;

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
  public ResponseEntity<ProcessoSeletivo> criarProcesso(@RequestBody ProcessoSeletivoDTO.ProcessoSeletivoRequest request) {
    ProcessoSeletivo novoProcesso = processoSeletivoUseCase.criar(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(novoProcesso);
  }

  @GetMapping("/publicos")
  public ResponseEntity<List<ProcessoSeletivo>> listarProcessosPublicos() {
    return ResponseEntity.ok(processoSeletivoUseCase.listarPublicos());
  }

  @PostMapping("/{id}/cargos")
  public ResponseEntity<ProcessoCargo> adicionarCargo(
    @PathVariable Long id,
    @RequestBody ProcessoSeletivoDTO.AdicionarCargoRequest request
  ) {
    return ResponseEntity.status(HttpStatus.CREATED).body(adicionarCargoAoProcessoUseCase.execute(id, request));
  }

  @PostMapping("/{id}/publicar")
  public ResponseEntity<ProcessoSeletivo> publicar(@PathVariable Long id) {
    return ResponseEntity.ok(publicarProcessoUseCase.execute(id));
  }

  @PostMapping("/{id}/iniciar")
  public ResponseEntity<ProcessoSeletivo> iniciar(@PathVariable Long id) {
    return ResponseEntity.ok(iniciarProcessoUseCase.execute(id));
  }

  @PostMapping("/{id}/encerrar")
  public ResponseEntity<ProcessoSeletivo> encerrar(@PathVariable Long id) {
    return ResponseEntity.ok(encerrarProcessoUseCase.execute(id));
  }

  @PostMapping("/{id}/cancelar")
  public ResponseEntity<ProcessoSeletivo> cancelar(@PathVariable Long id) {
    return ResponseEntity.ok(cancelarProcessoUseCase.execute(id));
  }
}