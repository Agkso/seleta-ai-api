package com.seletoai.adapters.controller.processoSeletivo;

import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.ports.in.processoSeletivo.ProcessoSeletivoUseCasePort;
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

  @PostMapping
  public ResponseEntity<ProcessoSeletivo> criarProcesso(@RequestBody ProcessoSeletivoDTO.ProcessoSeletivoRequest request) {
    ProcessoSeletivo novoProcesso = processoSeletivoUseCase.criar(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(novoProcesso);
  }

  @GetMapping("/publicos")
  public ResponseEntity<List<ProcessoSeletivo>> listarProcessosPublicos() {
    return ResponseEntity.ok(processoSeletivoUseCase.listarPublicos());
  }
}