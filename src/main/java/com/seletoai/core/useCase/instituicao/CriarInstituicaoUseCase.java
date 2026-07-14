package com.seletoai.core.useCase.instituicao;

import com.seletoai.core.domain.exception.RegraNegocioException;
import com.seletoai.core.domain.instituicao.Instituicao;
import com.seletoai.core.mapper.instituicao.InstituicaoMapper;
import com.seletoai.core.ports.in.instituicao.CriarInstituicaoUseCasePort;
import com.seletoai.core.ports.out.instituicao.InstituicaoRepositoryPort;
import com.seletoai.dto.instituicao.InstituicaoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CriarInstituicaoUseCase implements CriarInstituicaoUseCasePort {

  private final InstituicaoRepositoryPort repository;
  private final InstituicaoMapper mapper;

  @Override
  @Transactional
  public Instituicao execute(InstituicaoDTO.InstituicaoRequest request) {
    if (request == null || request.cnpj() == null || request.cnpj().isBlank()) {
      throw new RegraNegocioException("CNPJ é obrigatório.");
    }
    if (repository.findByCnpjAtiva(request.cnpj()).isPresent()) {
      throw new RegraNegocioException("Já existe uma instituição cadastrada com este CNPJ.");
    }
    return repository.save(mapper.toEntity(request));
  }
}
