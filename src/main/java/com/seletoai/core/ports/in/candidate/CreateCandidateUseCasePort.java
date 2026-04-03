package com.seletoai.core.ports.in.candidate;

import com.seletoai.core.domain.candidate.Candidate;

public interface CreateCandidateUseCasePort {

  Candidate execute(Candidate candidate);
}
