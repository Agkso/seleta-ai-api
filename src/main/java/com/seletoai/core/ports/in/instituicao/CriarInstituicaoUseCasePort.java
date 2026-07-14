package com.seletoai.core.ports.in.instituicao;

import com.seletoai.core.domain.instituicao.Instituicao;
import com.seletoai.dto.instituicao.InstituicaoDTO;

public interface CriarInstituicaoUseCasePort {

  Instituicao execute(InstituicaoDTO.InstituicaoRequest request);
}
