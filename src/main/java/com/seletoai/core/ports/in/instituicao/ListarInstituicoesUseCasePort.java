package com.seletoai.core.ports.in.instituicao;

import com.seletoai.core.domain.instituicao.Instituicao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarInstituicoesUseCasePort {

  Page<Instituicao> execute(Pageable pageable);
}
