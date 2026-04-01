package com.seletoai.adapters.persistence.candidate;

import com.seletoai.core.domain.candidateStatus.CandidateStatus;
import com.seletoai.core.ports.out.candidate.CandidateStatusRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CandidateStatusRepositoryAdapter
  implements CandidateStatusRepositoryPort {

  private final SpringCandidateStatusRepository repository;

  @Override
  public Optional<CandidateStatus> findByCode(String code) {
    return repository.findByCode(code);
  }
}