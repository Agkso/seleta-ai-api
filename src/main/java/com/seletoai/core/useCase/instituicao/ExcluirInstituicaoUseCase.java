package com.seletoai.core.useCase.instituicao;

import com.seletoai.core.domain.exception.RecursoNaoEncontradoException;
import com.seletoai.core.domain.exception.RegraNegocioException;
import com.seletoai.core.domain.instituicao.Instituicao;
import com.seletoai.core.domain.processoSeletivo.ProcessoStatusCodes;
import com.seletoai.core.ports.in.instituicao.ExcluirInstituicaoUseCasePort;
import com.seletoai.core.ports.out.instituicao.InstituicaoRepositoryPort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoSeletivoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExcluirInstituicaoUseCase implements ExcluirInstituicaoUseCasePort {

  private final InstituicaoRepositoryPort repository;
  private final ProcessoSeletivoRepositoryPort processoRepository;

  @Override
  @Transactional
  public void execute(Long id) {
    Instituicao instituicao = repository.findById(id)
      .orElseThrow(() -> new RecursoNaoEncontradoException("Instituição não encontrada."));

    boolean possuiProcessoAtivo = processoRepository.findAllByInstituicaoId(id).stream()
      .anyMatch(processo -> {
        String codigo = processo.getStatus() != null ? processo.getStatus().getCodigo() : null;
        return !ProcessoStatusCodes.ENCERRADO.equals(codigo) && !ProcessoStatusCodes.CANCELADO.equals(codigo);
      });
    if (possuiProcessoAtivo) {
      throw new RegraNegocioException("Instituição possui processos seletivos ativos e não pode ser excluída.");
    }

    instituicao.setDeletedAt(LocalDateTime.now());
    repository.save(instituicao);
  }
}
