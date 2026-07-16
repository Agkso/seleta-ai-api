package com.seletoai.adapters.persistence.candidate;

import com.seletoai.core.domain.candidate.Candidate;
import com.seletoai.core.domain.exception.RecursoNaoEncontradoException;
import com.seletoai.core.ports.out.candidate.CandidateRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  public Page<Candidate> findByProcessoId(Long processoId, Pageable pageable) {
    return repository.findByProcessoId(processoId, pageable);
  }

  @Override
  public Candidate findById(Long id) {
    return repository.findById(id)
      .orElseThrow(() -> new RecursoNaoEncontradoException("Candidato não encontrado"));
  }

  @Override
  public boolean existsByUserIdAndProcessoId(Long userId, Long processoId) {
    return repository.existsByUserIdAndProcessoId(userId, processoId);
  }
}