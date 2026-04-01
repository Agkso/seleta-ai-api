package com.seletoai.adapters.persistence.status;

import com.seletoai.core.domain.status.Status;
import com.seletoai.adapters.persistence.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface SpringStatusRepository
  extends BaseRepository<Status> {

  Optional<Status> findByCodigoAndTipoStatus_Codigo(String codigo, String tipoCodigo);

  List<Status> findByTipoStatus_Codigo(String tipoCodigo);
}