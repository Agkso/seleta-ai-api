package com.seletoai.core.useCase.processoSeletivo;

import com.seletoai.core.domain.exception.RecursoNaoEncontradoException;
import com.seletoai.core.domain.exception.RegraNegocioException;
import com.seletoai.core.domain.processoSeletivo.ProcessoCargo;
import com.seletoai.core.domain.processoSeletivo.ProcessoLifecycleRules;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.ports.in.processoSeletivo.AdicionarCargoAoProcessoUseCasePort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoCargoRepositoryPort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoSeletivoRepositoryPort;
import com.seletoai.dto.processoSeletivo.ProcessoSeletivoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdicionarCargoAoProcessoUseCase implements AdicionarCargoAoProcessoUseCasePort {

  private final ProcessoSeletivoRepositoryPort processoRepository;
  private final ProcessoCargoRepositoryPort cargoRepository;

  @Override
  @Transactional
  public ProcessoCargo execute(Long processoId, ProcessoSeletivoDTO.AdicionarCargoRequest request) {
    if (request == null || request.titulo() == null || request.titulo().isBlank()) {
      throw new RegraNegocioException("Título do cargo é obrigatório.");
    }
    ProcessoSeletivo processo = processoRepository.findById(processoId)
      .orElseThrow(() -> new RecursoNaoEncontradoException("Processo não encontrado."));
    ProcessoLifecycleRules.garantirPodeAdicionarCargo(processo);
    ProcessoCargo cargo = new ProcessoCargo();
    cargo.setProcesso(processo);
    cargo.setTitulo(request.titulo().trim());
    return cargoRepository.save(cargo);
  }
}
