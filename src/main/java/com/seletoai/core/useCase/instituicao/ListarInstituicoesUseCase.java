package com.seletoai.core.useCase.instituicao;

import com.seletoai.core.domain.instituicao.Instituicao;
import com.seletoai.core.ports.in.instituicao.ListarInstituicoesUseCasePort;
import com.seletoai.core.ports.out.instituicao.InstituicaoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarInstituicoesUseCase implements ListarInstituicoesUseCasePort {

  private final InstituicaoRepositoryPort repository;

  @Override
  public List<Instituicao> execute() {
    return repository.findAllAtivas();
  }
}
