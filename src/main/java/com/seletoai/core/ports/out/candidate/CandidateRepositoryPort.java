package com.seletoai.core.ports.out.candidate;

import com.seletoai.core.domain.candidate.Candidate;

import java.util.List;

public interface CandidateRepositoryPort {

  Candidate save(Candidate candidate);

  List<Candidate> findByProcessoId(Integer processoId);

  Candidate findById(Integer id);
}