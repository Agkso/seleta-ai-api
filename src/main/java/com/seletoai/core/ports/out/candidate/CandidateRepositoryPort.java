package com.seletoai.core.ports.out.candidate;

import com.seletoai.core.domain.candidate.Candidate;

import java.util.List;

public interface CandidateRepositoryPort {

  Candidate save(Candidate candidate);

  List<Candidate> findByProcessoId(Long processoId);

  org.springframework.data.domain.Page<Candidate> findByProcessoId(Long processoId, org.springframework.data.domain.Pageable pageable);

  Candidate findById(Long id);

  boolean existsByUserIdAndProcessoId(Long userId, Long processoId);
}