package com.seletoai.adapters.persistence.candidate;

import com.seletoai.core.domain.candidate.Candidate;
import com.seletoai.core.ports.out.candidate.CandidateRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CandidateRepositoryAdapter
  implements CandidateRepositoryPort {

  private final SpringCandidateRepository repository;

  @Override
  public Candidate save(Candidate candidate) {
    return repository.save(candidate);
  }

  @Override
  public List<Candidate> findByProcessoId(Long processoId) {
    return repository.findByProcessoId(processoId);
  }

  @Override
  public Candidate findById(Long id) {
    return repository.findById(id)
      .orElseThrow(() -> new RuntimeException("Candidato não encontrado"));
  }
}