package com.seletoai.adapters.controller.candidate;

import com.seletoai.core.domain.candidate.Candidate;
import com.seletoai.core.ports.in.candidate.CreateCandidateUseCasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidates")
@RequiredArgsConstructor
public class CandidateController {

  private final CreateCandidateUseCasePort useCase;

  @PostMapping
  public Candidate create(@RequestBody Candidate candidate) {
    return useCase.execute(candidate);
  }
}