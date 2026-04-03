package com.seletoai.core.useCase.candidate;

import com.seletoai.core.domain.candidate.Candidate;
import com.seletoai.core.domain.status.Status;
import com.seletoai.core.ports.in.candidate.CreateCandidateUseCasePort;
import com.seletoai.core.ports.out.candidate.CandidateRepositoryPort;
import com.seletoai.core.ports.out.status.StatusRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCandidateUseCase implements CreateCandidateUseCasePort {

  private final CandidateRepositoryPort candidateRepository;
  private final StatusRepositoryPort statusRepository;

  public Candidate execute(Candidate candidate) {

    Status status = statusRepository.findByCodigoAndTipo("INSCRITO", "CANDIDATE");

    candidate.setStatus(status);

    return candidateRepository.save(candidate);
  }
}