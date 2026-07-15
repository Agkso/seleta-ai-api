package com.seletoai.core.useCase.instituicao;

import com.seletoai.core.domain.instituicao.Instituicao;
import com.seletoai.core.ports.in.instituicao.ListarInstituicoesUseCasePort;
import com.seletoai.core.ports.out.instituicao.InstituicaoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListarInstituicoesUseCase implements ListarInstituicoesUseCasePort {

  private final InstituicaoRepositoryPort repository;

  @Override
  public Page<Instituicao> execute(Pageable pageable) {
    return repository.findAllAtivas(pageable);
  }
}
