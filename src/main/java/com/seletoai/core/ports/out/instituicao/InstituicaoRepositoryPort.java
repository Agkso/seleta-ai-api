package com.seletoai.core.ports.out.instituicao;

import com.seletoai.core.domain.instituicao.Instituicao;

import java.util.List;
import java.util.Optional;

public interface InstituicaoRepositoryPort {

  Instituicao save(Instituicao instituicao);

  Optional<Instituicao> findById(Long id);

  List<Instituicao> findAllAtivas();

  Optional<Instituicao> findByCnpjAtiva(String cnpj);
}
