package com.seletoai.core.ports.in.instituicao;

import com.seletoai.core.domain.instituicao.Instituicao;

public interface BuscarInstituicaoUseCasePort {

  Instituicao execute(Long id);
}
