package com.seletoai.core.useCase.processoSeletivo;

import com.seletoai.core.domain.exception.RecursoNaoEncontradoException;
import com.seletoai.core.ports.in.processoSeletivo.ListarCargosDoProcessoUseCasePort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoCargoRepositoryPort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoSeletivoRepositoryPort;
import com.seletoai.dto.processoSeletivo.ProcessoSeletivoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarCargosDoProcessoUseCase implements ListarCargosDoProcessoUseCasePort {

  private final ProcessoSeletivoRepositoryPort processoRepository;
  private final ProcessoCargoRepositoryPort cargoRepository;

  @Override
  @Transactional(readOnly = true)
  public List<ProcessoSeletivoDTO.CargoResponse> execute(Long processoId) {
    processoRepository.findById(processoId)
      .orElseThrow(() -> new RecursoNaoEncontradoException("Processo não encontrado."));
    return cargoRepository.findByProcessoId(processoId).stream()
      .map(ProcessoSeletivoDTO.CargoResponse::from)
      .toList();
  }
}
