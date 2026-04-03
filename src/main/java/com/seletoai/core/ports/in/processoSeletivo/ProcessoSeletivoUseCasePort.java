package com.seletoai.core.ports.in.processoSeletivo;

import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.dto.processoSeletivo.ProcessoSeletivoDTO;

import java.util.List;

public interface ProcessoSeletivoUseCasePort {

  ProcessoSeletivo criar(ProcessoSeletivoDTO.ProcessoSeletivoRequest request);

  List<ProcessoSeletivo> listarPublicos();
}
