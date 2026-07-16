package com.seletoai.core.useCase.candidate;

import com.seletoai.core.domain.auth.AuthContext;
import com.seletoai.core.domain.candidate.Candidate;
import com.seletoai.core.domain.candidate.CandidateStatusCodes;
import com.seletoai.core.domain.exception.RecursoNaoEncontradoException;
import com.seletoai.core.domain.exception.RegraNegocioException;
import com.seletoai.core.domain.processoSeletivo.ProcessoLifecycleRules;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.domain.user.User;
import com.seletoai.core.ports.in.candidate.InscreverCandidatoUseCasePort;
import com.seletoai.core.ports.out.candidate.CandidateRepositoryPort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoCargoRepositoryPort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoSeletivoRepositoryPort;
import com.seletoai.core.ports.out.status.StatusRepositoryPort;
import com.seletoai.core.ports.out.user.UserRepositoryPort;
import com.seletoai.dto.candidate.CandidateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InscreverCandidatoUseCase implements InscreverCandidatoUseCasePort {

  private final ProcessoSeletivoRepositoryPort processoRepository;
  private final ProcessoCargoRepositoryPort cargoRepository;
  private final CandidateRepositoryPort candidateRepository;
  private final StatusRepositoryPort statusRepository;
  private final UserRepositoryPort userRepository;

  @Override
  @Transactional
  public CandidateDTO.CandidateResponse execute(Long processoId, CandidateDTO.InscricaoRequest request, AuthContext authContext) {
    ProcessoSeletivo processo = processoRepository.findById(processoId)
      .orElseThrow(() -> new RecursoNaoEncontradoException("Processo não encontrado."));

    ProcessoLifecycleRules.garantirProcessoAceitaInscricao(processo, LocalDateTime.now());

    if (!cargoRepository.existsByIdAndProcesso_Id(request.cargoId(), processoId)) {
      throw new RegraNegocioException("Cargo não pertence a este processo.");
    }

    if (candidateRepository.existsByUserIdAndProcessoId(authContext.userId(), processoId)) {
      throw new RegraNegocioException("Candidato já inscrito neste processo.");
    }

    User user = userRepository.findById(authContext.userId())
      .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

    Candidate candidato = new Candidate();
    candidato.setUserId(authContext.userId());
    candidato.setNome(user.getName());
    candidato.setEmail(user.getEmail());
    candidato.setCpf(request.cpf().replaceAll("[^0-9]", ""));
    candidato.setProcessoId(processoId);
    candidato.setCargoId(request.cargoId());
    candidato.setStatus(
      statusRepository.findByCodigoAndTipo(
        CandidateStatusCodes.INSCRITO,
        CandidateStatusCodes.TIPO_DOMINIO_CANDIDATO
      )
    );

    return CandidateDTO.CandidateResponse.from(candidateRepository.save(candidato));
  }
}
