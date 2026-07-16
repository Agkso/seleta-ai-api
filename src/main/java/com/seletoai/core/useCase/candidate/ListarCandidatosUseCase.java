package com.seletoai.core.useCase.candidate;

import com.seletoai.core.domain.auth.AuthContext;
import com.seletoai.core.domain.exception.RecursoNaoEncontradoException;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.ports.in.candidate.ListarCandidatosUseCasePort;
import com.seletoai.core.ports.out.candidate.CandidateRepositoryPort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoSeletivoRepositoryPort;
import com.seletoai.dto.candidate.CandidateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListarCandidatosUseCase implements ListarCandidatosUseCasePort {

  private final ProcessoSeletivoRepositoryPort processoRepository;
  private final CandidateRepositoryPort candidateRepository;

  @Override
  @Transactional(readOnly = true)
  public Page<CandidateDTO.CandidateResponse> execute(Long processoId, Pageable pageable, AuthContext authContext) {
    ProcessoSeletivo processo = processoRepository.findById(processoId)
      .orElseThrow(() -> new RecursoNaoEncontradoException("Processo não encontrado."));
    authContext.garantirAcessoInstituicao(processo.getInstituicao().getId());
    return candidateRepository.findByProcessoId(processoId, pageable)
      .map(CandidateDTO.CandidateResponse::from);
  }
}
