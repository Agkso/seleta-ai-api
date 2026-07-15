package com.seletoai.adapters.persistence.instituicao;

import com.seletoai.adapters.persistence.base.BaseRepository;
import com.seletoai.core.domain.instituicao.Instituicao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SpringInstituicaoRepository extends BaseRepository<Instituicao> {

  Optional<Instituicao> findByIdAndDeletedAtIsNull(Long id);

  Page<Instituicao> findAllByDeletedAtIsNull(Pageable pageable);

  Optional<Instituicao> findByCnpjAndDeletedAtIsNull(String cnpj);
}
