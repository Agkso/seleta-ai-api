package com.seletoai.core.ports.out.status;

import com.seletoai.core.domain.status.Status;

import java.util.List;

public interface StatusRepositoryPort {

  Status findByCodigoAndTipo(String codigo, String tipoCodigo);

  List<Status> findByTipo(String tipoCodigo);

}
