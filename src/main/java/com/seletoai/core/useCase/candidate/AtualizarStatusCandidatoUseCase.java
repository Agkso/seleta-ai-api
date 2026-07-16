package com.seletoai.core.useCase.candidate;

import com.seletoai.core.domain.auth.AuthContext;
import com.seletoai.core.domain.candidate.Candidate;
import com.seletoai.core.domain.candidate.CandidateStatusCodes;
import com.seletoai.core.domain.exception.RecursoNaoEncontradoException;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.ports.in.candidate.AtualizarStatusCandidatoUseCasePort;
import com.seletoai.core.ports.out.candidate.CandidateRepositoryPort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoSeletivoRepositoryPort;
import com.seletoai.core.ports.out.status.StatusRepositoryPort;
import com.seletoai.dto.candidate.CandidateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizarStatusCandidatoUseCase implements AtualizarStatusCandidatoUseCasePort {

  private final CandidateRepositoryPort candidateRepository;
  private final ProcessoSeletivoRepositoryPort processoRepository;
  private final StatusRepositoryPort statusRepository;

  @Override
  @Transactional
  public CandidateDTO.CandidateResponse execute(Long candidatoId, CandidateDTO.AtualizarStatusRequest request, AuthContext authContext) {
    Candidate candidato = candidateRepository.findById(candidatoId);

    ProcessoSeletivo processo = processoRepository.findById(candidato.getProcessoId())
      .orElseThrow(() -> new RecursoNaoEncontradoException("Processo não encontrado."));
    authContext.garantirAcessoInstituicao(processo.getInstituicao().getId());

    candidato.setStatus(
      statusRepository.findByCodigoAndTipo(
        request.statusCodigo(),
        CandidateStatusCodes.TIPO_DOMINIO_CANDIDATO
      )
    );

    return CandidateDTO.CandidateResponse.from(candidateRepository.save(candidato));
  }
}
