package com.seletoai.core.useCase.instituicao;

import com.seletoai.core.domain.exception.RecursoNaoEncontradoException;
import com.seletoai.core.domain.instituicao.Instituicao;
import com.seletoai.core.ports.in.instituicao.BuscarInstituicaoUseCasePort;
import com.seletoai.core.ports.out.instituicao.InstituicaoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscarInstituicaoUseCase implements BuscarInstituicaoUseCasePort {

  private final InstituicaoRepositoryPort repository;

  @Override
  public Instituicao execute(Long id) {
    return repository.findById(id)
      .orElseThrow(() -> new RecursoNaoEncontradoException("Instituição não encontrada."));
  }
}
