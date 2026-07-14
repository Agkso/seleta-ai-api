package com.seletoai.adapters.controller.instituicao;

import com.seletoai.core.domain.instituicao.Instituicao;
import com.seletoai.core.ports.in.instituicao.AtualizarInstituicaoUseCasePort;
import com.seletoai.core.ports.in.instituicao.BuscarInstituicaoUseCasePort;
import com.seletoai.core.ports.in.instituicao.CriarInstituicaoUseCasePort;
import com.seletoai.core.ports.in.instituicao.ExcluirInstituicaoUseCasePort;
import com.seletoai.core.ports.in.instituicao.ListarInstituicoesUseCasePort;
import com.seletoai.dto.instituicao.InstituicaoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instituicoes")
@RequiredArgsConstructor
public class InstituicaoController {

  private final CriarInstituicaoUseCasePort criarInstituicaoUseCase;
  private final AtualizarInstituicaoUseCasePort atualizarInstituicaoUseCase;
  private final BuscarInstituicaoUseCasePort buscarInstituicaoUseCase;
  private final ListarInstituicoesUseCasePort listarInstituicoesUseCase;
  private final ExcluirInstituicaoUseCasePort excluirInstituicaoUseCase;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Instituicao> criar(@RequestBody InstituicaoDTO.InstituicaoRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(criarInstituicaoUseCase.execute(request));
  }

  @GetMapping
  public ResponseEntity<List<Instituicao>> listar() {
    return ResponseEntity.ok(listarInstituicoesUseCase.execute());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Instituicao> buscar(@PathVariable Long id) {
    return ResponseEntity.ok(buscarInstituicaoUseCase.execute(id));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Instituicao> atualizar(
    @PathVariable Long id,
    @RequestBody InstituicaoDTO.InstituicaoRequest request
  ) {
    return ResponseEntity.ok(atualizarInstituicaoUseCase.execute(id, request));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> excluir(@PathVariable Long id) {
    excluirInstituicaoUseCase.execute(id);
    return ResponseEntity.noContent().build();
  }
}
