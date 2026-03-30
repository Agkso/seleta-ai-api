package com.seletoai.adapters.controller.processoSeletivo;

import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.useCase.processoSeletivo.ProcessoSeletivoUseCase;
import com.seletoai.dto.processoSeletivo.ProcessoSeletivoRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/processos")
@RequiredArgsConstructor
public class ProcessoSeletivoController {

  private final ProcessoSeletivoUseCase processoSeletivoUseCase;

  @PostMapping
  public ResponseEntity<ProcessoSeletivo> criarProcesso(@RequestBody ProcessoSeletivoRecord.ProcessoSeletivoRequest request) {
    ProcessoSeletivo novoProcesso = processoSeletivoUseCase.criar(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(novoProcesso);
  }

  @GetMapping("/publicos")
  public ResponseEntity<List<ProcessoSeletivo>> listarProcessosPublicos() {
    return ResponseEntity.ok(processoSeletivoUseCase.listarPublicos());
  }
}