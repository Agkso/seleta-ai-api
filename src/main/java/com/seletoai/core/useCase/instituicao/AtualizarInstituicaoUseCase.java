package com.seletoai.core.useCase.instituicao;

import com.seletoai.core.domain.exception.RecursoNaoEncontradoException;
import com.seletoai.core.domain.exception.RegraNegocioException;
import com.seletoai.core.domain.instituicao.Instituicao;
import com.seletoai.core.mapper.instituicao.InstituicaoMapper;
import com.seletoai.core.ports.in.instituicao.AtualizarInstituicaoUseCasePort;
import com.seletoai.core.ports.out.instituicao.InstituicaoRepositoryPort;
import com.seletoai.dto.instituicao.InstituicaoDTO;
import com.seletoai.dto.validation.CnpjNormalizador;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizarInstituicaoUseCase implements AtualizarInstituicaoUseCasePort {

  private final InstituicaoRepositoryPort repository;
  private final InstituicaoMapper mapper;

  @Override
  @Transactional
  public Instituicao execute(Long id, InstituicaoDTO.InstituicaoRequest request) {
    Instituicao instituicao = repository.findById(id)
      .orElseThrow(() -> new RecursoNaoEncontradoException("Instituição não encontrada."));

    if (request == null || request.cnpj() == null || request.cnpj().isBlank()) {
      throw new RegraNegocioException("CNPJ é obrigatório.");
    }

    repository.findByCnpjAtiva(CnpjNormalizador.normalizar(request.cnpj()))
      .filter(outra -> !outra.getId().equals(id))
      .ifPresent(outra -> {
        throw new RegraNegocioException("Já existe uma instituição cadastrada com este CNPJ.");
      });

    mapper.aplicar(instituicao, request);
    return repository.save(instituicao);
  }
}
