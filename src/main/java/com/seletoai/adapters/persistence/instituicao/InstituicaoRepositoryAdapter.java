package com.seletoai.adapters.persistence.instituicao;

import com.seletoai.core.domain.instituicao.Instituicao;
import com.seletoai.core.ports.out.instituicao.InstituicaoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InstituicaoRepositoryAdapter implements InstituicaoRepositoryPort {

  private final SpringInstituicaoRepository repository;

  @Override
  public Instituicao save(Instituicao instituicao) {
    return repository.save(instituicao);
  }

  @Override
  public Optional<Instituicao> findById(Long id) {
    return repository.findByIdAndDeletedAtIsNull(id);
  }

  @Override
  public List<Instituicao> findAllAtivas() {
    return repository.findAllByDeletedAtIsNull();
  }

  @Override
  public Optional<Instituicao> findByCnpjAtiva(String cnpj) {
    return repository.findByCnpjAndDeletedAtIsNull(cnpj);
  }
}
