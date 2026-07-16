package com.seletoai.core.useCase.processoSeletivo;

import com.seletoai.core.domain.auth.AuthContext;
import com.seletoai.core.domain.exception.RecursoNaoEncontradoException;
import com.seletoai.core.domain.processoSeletivo.ProcessoLifecycleRules;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.ports.in.processoSeletivo.RemoverCargoDoProcessoUseCasePort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoCargoRepositoryPort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoSeletivoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverCargoDoProcessoUseCase implements RemoverCargoDoProcessoUseCasePort {

  private final ProcessoSeletivoRepositoryPort processoRepository;
  private final ProcessoCargoRepositoryPort cargoRepository;

  @Override
  @Transactional
  public void execute(Long processoId, Long cargoId, AuthContext authContext) {
    ProcessoSeletivo processo = processoRepository.findById(processoId)
      .orElseThrow(() -> new RecursoNaoEncontradoException("Processo não encontrado."));
    authContext.garantirAcessoInstituicao(processo.getInstituicao().getId());
    ProcessoLifecycleRules.garantirPodeAdicionarCargo(processo);
    if (!cargoRepository.existsByIdAndProcesso_Id(cargoId, processoId)) {
      throw new RecursoNaoEncontradoException("Cargo não encontrado neste processo.");
    }
    cargoRepository.deleteById(cargoId);
  }
}
