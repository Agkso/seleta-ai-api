package com.seletoai.core.ports.in.processoSeletivo;

import com.seletoai.core.domain.auth.AuthContext;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.dto.processoSeletivo.ProcessoSeletivoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProcessoSeletivoUseCasePort {

  ProcessoSeletivo criar(ProcessoSeletivoDTO.ProcessoSeletivoRequest request, AuthContext authContext);

  Page<ProcessoSeletivo> listarPublicos(Pageable pageable);
}
