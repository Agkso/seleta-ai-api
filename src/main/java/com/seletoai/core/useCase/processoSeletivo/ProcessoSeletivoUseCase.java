package com.seletoai.core.useCase.processoSeletivo;

import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.mapper.processoSeletivo.ProcessoSeletivoMapper;
import com.seletoai.core.ports.in.processoSeletivo.ProcessoSeletivoUseCasePort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoSeletivoRepositoryPort;
import com.seletoai.dto.processoSeletivo.ProcessoSeletivoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProcessoSeletivoUseCase implements ProcessoSeletivoUseCasePort {

  private final ProcessoSeletivoRepositoryPort repositoryPort;
  private final ProcessoSeletivoMapper mapper;

  public ProcessoSeletivo criar(ProcessoSeletivoDTO.ProcessoSeletivoRequest request) {
    ProcessoSeletivo processo = mapper.toEntity(request);
    return repositoryPort.save(processo);
  }

  public List<ProcessoSeletivo> listarPublicos() {
    return repositoryPort.findAllByStatus("PUBLICADO");
  }
}