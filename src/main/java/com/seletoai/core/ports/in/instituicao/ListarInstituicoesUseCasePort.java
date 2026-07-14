package com.seletoai.core.ports.in.instituicao;

import com.seletoai.core.domain.instituicao.Instituicao;

import java.util.List;

public interface ListarInstituicoesUseCasePort {

  List<Instituicao> execute();
}
