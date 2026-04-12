package com.seletoai.core.useCase.candidate;

import com.seletoai.core.domain.candidate.Candidate;
import com.seletoai.core.domain.exception.RecursoNaoEncontradoException;
import com.seletoai.core.domain.exception.RegraNegocioException;
import com.seletoai.core.domain.processoSeletivo.ProcessoLifecycleRules;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.domain.status.Status;
import com.seletoai.core.ports.in.candidate.CreateCandidateUseCasePort;
import com.seletoai.core.ports.out.candidate.CandidateRepositoryPort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoCargoRepositoryPort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoSeletivoRepositoryPort;
import com.seletoai.core.ports.out.status.StatusRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateCandidateUseCase implements CreateCandidateUseCasePort {

  private final CandidateRepositoryPort candidateRepository;
  private final StatusRepositoryPort statusRepository;
  private final ProcessoSeletivoRepositoryPort processoRepository;
  private final ProcessoCargoRepositoryPort cargoRepository;

  @Override
  @Transactional
  public Candidate execute(Candidate candidate) {
    if (candidate.getProcessoId() == null) {
      throw new RegraNegocioException("processoId é obrigatório para inscrição.");
    }
    if (candidate.getCargoId() == null) {
      throw new RegraNegocioException("cargoId é obrigatório para inscrição.");
    }

    ProcessoSeletivo processo = processoRepository.findById(candidate.getProcessoId())
      .orElseThrow(() -> new RecursoNaoEncontradoException("Processo seletivo não encontrado."));

    ProcessoLifecycleRules.garantirProcessoAceitaInscricao(processo, LocalDateTime.now());

    if (!cargoRepository.existsByIdAndProcesso_Id(candidate.getCargoId(), candidate.getProcessoId())) {
      throw new RegraNegocioException("Cargo informado não pertence ao processo seletivo.");
    }

    Status status = statusRepository.findByCodigoAndTipo("INSCRITO", "CANDIDATE");
    candidate.setStatus(status);

    return candidateRepository.save(candidate);
  }
}