package com.seletoai.core.ports.in.candidate;

import com.seletoai.core.domain.auth.AuthContext;
import com.seletoai.dto.candidate.CandidateDTO;

public interface AtualizarStatusCandidatoUseCasePort {

  CandidateDTO.CandidateResponse execute(Long candidatoId, CandidateDTO.AtualizarStatusRequest request, AuthContext authContext);
}
