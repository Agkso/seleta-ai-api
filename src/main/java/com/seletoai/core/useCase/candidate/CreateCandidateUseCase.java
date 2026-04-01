package com.seletoai.core.useCase.candidate;

import com.seletoai.core.domain.candidate.Candidate;
import com.seletoai.core.domain.candidateStatus.CandidateStatus;
import com.seletoai.core.ports.out.candidate.CandidateRepositoryPort;
import com.seletoai.core.ports.out.candidate.CandidateStatusRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCandidateUseCase {

  private final CandidateRepositoryPort candidateRepository;
  private final CandidateStatusRepositoryPort statusRepository;

  public Candidate execute(Candidate candidate) {

    CandidateStatus status = statusRepository
      .findByCode("INSCRITO")
      .orElseThrow(() -> new RuntimeException("Status padrão não encontrado"));

    candidate.setStatus(status);

    return candidateRepository.save(candidate);
  }
}