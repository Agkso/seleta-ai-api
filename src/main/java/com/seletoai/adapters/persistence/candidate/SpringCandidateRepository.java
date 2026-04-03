package com.seletoai.adapters.persistence.candidate;

import com.seletoai.core.domain.candidate.Candidate;
import com.seletoai.adapters.persistence.base.BaseRepository;

import java.util.List;
import java.util.UUID;

public interface SpringCandidateRepository
  extends BaseRepository<Candidate> {

  List<Candidate> findByProcessoId(Long processoId);
}