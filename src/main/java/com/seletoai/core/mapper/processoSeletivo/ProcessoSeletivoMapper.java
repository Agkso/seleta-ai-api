package com.seletoai.core.mapper.processoSeletivo;

import com.seletoai.core.domain.instituicao.Instituicao;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.domain.processoSeletivo.TipoProcesso;
import com.seletoai.dto.processoSeletivo.ProcessoSeletivoDTO;
import org.springframework.stereotype.Component;

@Component
public class ProcessoSeletivoMapper {

  public ProcessoSeletivo toEntity(ProcessoSeletivoDTO.ProcessoSeletivoRequest dto) {
    ProcessoSeletivo processo = new ProcessoSeletivo();

    Instituicao instituicao = new Instituicao();
    instituicao.setId(dto.instituicaoId());
    processo.setInstituicao(instituicao);

    processo.setTitulo(dto.titulo());
    processo.setNumeroEdital(dto.numeroEdital());
    processo.setDataInicioInscricao(dto.dataInicioInscricao());
    processo.setDataFimInscricao(dto.dataFimInscricao());

    processo.setTipoProcesso(dto.tipoProcesso() != null ? dto.tipoProcesso() : TipoProcesso.PUBLICO);

    return processo;
  }
}