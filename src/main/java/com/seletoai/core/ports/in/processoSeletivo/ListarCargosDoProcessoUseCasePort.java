package com.seletoai.core.ports.in.processoSeletivo;

import com.seletoai.dto.processoSeletivo.ProcessoSeletivoDTO;

import java.util.List;

public interface ListarCargosDoProcessoUseCasePort {

  List<ProcessoSeletivoDTO.CargoResponse> execute(Long processoId);
}
