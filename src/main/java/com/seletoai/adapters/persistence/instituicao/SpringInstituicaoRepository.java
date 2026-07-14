package com.seletoai.adapters.persistence.instituicao;

import com.seletoai.adapters.persistence.base.BaseRepository;
import com.seletoai.core.domain.instituicao.Instituicao;

import java.util.List;
import java.util.Optional;

public interface SpringInstituicaoRepository extends BaseRepository<Instituicao> {

  Optional<Instituicao> findByIdAndDeletedAtIsNull(Long id);

  List<Instituicao> findAllByDeletedAtIsNull();

  Optional<Instituicao> findByCnpjAndDeletedAtIsNull(String cnpj);
}
