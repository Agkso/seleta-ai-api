package com.seletoai.core.ports.in.candidate;

import com.seletoai.core.domain.auth.AuthContext;
import com.seletoai.dto.candidate.CandidateDTO;

public interface InscreverCandidatoUseCasePort {

  CandidateDTO.CandidateResponse execute(Long processoId, CandidateDTO.InscricaoRequest request, AuthContext authContext);
}
