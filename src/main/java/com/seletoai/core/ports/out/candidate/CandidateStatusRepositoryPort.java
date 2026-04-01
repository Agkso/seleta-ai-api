package com.seletoai.core.ports.out.candidate;


import com.seletoai.core.domain.candidateStatus.CandidateStatus;

import java.util.Optional;

public interface CandidateStatusRepositoryPort {

  Optional<CandidateStatus> findByCode(String code);
}