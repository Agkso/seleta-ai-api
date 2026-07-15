package com.seletoai.core.mapper.instituicao;

import com.seletoai.core.domain.instituicao.Instituicao;
import com.seletoai.dto.instituicao.InstituicaoDTO;
import com.seletoai.dto.validation.CnpjNormalizador;
import org.springframework.stereotype.Component;

@Component
public class InstituicaoMapper {

  public Instituicao toEntity(InstituicaoDTO.InstituicaoRequest dto) {
    Instituicao instituicao = new Instituicao();
    aplicar(instituicao, dto);
    return instituicao;
  }

  public void aplicar(Instituicao instituicao, InstituicaoDTO.InstituicaoRequest dto) {
    instituicao.setCnpj(CnpjNormalizador.normalizar(dto.cnpj()));
    instituicao.setRazaoSocial(dto.razaoSocial());
    instituicao.setNomeFantasia(dto.nomeFantasia());
  }
}
