package com.seletoai.core.useCase.processoSeletivo;

import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.domain.processoSeletivo.ProcessoStatusCodes;
import com.seletoai.core.mapper.processoSeletivo.ProcessoSeletivoMapper;
import com.seletoai.core.ports.in.processoSeletivo.ProcessoSeletivoUseCasePort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoSeletivoRepositoryPort;
import com.seletoai.core.ports.out.status.StatusRepositoryPort;
import com.seletoai.dto.processoSeletivo.ProcessoSeletivoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProcessoSeletivoUseCase implements ProcessoSeletivoUseCasePort {

  private final ProcessoSeletivoRepositoryPort repositoryPort;
  private final ProcessoSeletivoMapper mapper;
  private final StatusRepositoryPort statusRepositoryPort;

  public ProcessoSeletivo criar(ProcessoSeletivoDTO.ProcessoSeletivoRequest request) {
    ProcessoSeletivo processo = mapper.toEntity(request);
    processo.setStatus(
      statusRepositoryPort.findByCodigoAndTipo(
        ProcessoStatusCodes.RASCUNHO,
        ProcessoStatusCodes.TIPO_DOMINIO_PROCESSO
      )
    );
    return repositoryPort.save(processo);
  }

  public List<ProcessoSeletivo> listarPublicos() {
    return repositoryPort.findAllByStatusCodigo(ProcessoStatusCodes.PUBLICADO);
  }
}