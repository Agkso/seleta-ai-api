package com.seletoai.core.ports.out.instituicao;

import com.seletoai.core.domain.instituicao.Instituicao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InstituicaoRepositoryPort {

  Instituicao save(Instituicao instituicao);

  Optional<Instituicao> findById(Long id);

  Page<Instituicao> findAllAtivas(Pageable pageable);

  Optional<Instituicao> findByCnpjAtiva(String cnpj);
}
