package com.seletoai.core.ports.in.candidate;

import com.seletoai.core.domain.auth.AuthContext;
import com.seletoai.dto.candidate.CandidateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarCandidatosUseCasePort {

  Page<CandidateDTO.CandidateResponse> execute(Long processoId, Pageable pageable, AuthContext authContext);
}
