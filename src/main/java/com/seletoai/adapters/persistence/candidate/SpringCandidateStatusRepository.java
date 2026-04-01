package com.seletoai.adapters.persistence.candidate;

import com.seletoai.adapters.persistence.base.BaseRepository;
import com.seletoai.core.domain.candidateStatus.CandidateStatus;

import java.util.Optional;

public interface SpringCandidateStatusRepository
  extends BaseRepository<CandidateStatus> {

  Optional<CandidateStatus> findByCode(String code);
}