package com.seletoai.core.useCase.processoSeletivo;

import com.seletoai.core.domain.auth.AuthContext;
import com.seletoai.core.domain.exception.RecursoNaoEncontradoException;
import com.seletoai.core.domain.processoSeletivo.ProcessoLifecycleRules;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.domain.processoSeletivo.ProcessoStatusCodes;
import com.seletoai.core.mapper.processoSeletivo.ProcessoSeletivoMapper;
import com.seletoai.core.ports.in.processoSeletivo.ProcessoSeletivoUseCasePort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoSeletivoRepositoryPort;
import com.seletoai.core.ports.out.status.StatusRepositoryPort;
import com.seletoai.dto.processoSeletivo.ProcessoSeletivoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ProcessoSeletivoUseCase implements ProcessoSeletivoUseCasePort {

  private final ProcessoSeletivoRepositoryPort repositoryPort;
  private final ProcessoSeletivoMapper mapper;
  private final StatusRepositoryPort statusRepositoryPort;

  @Override
  @Transactional
  public ProcessoSeletivo criar(ProcessoSeletivoDTO.ProcessoSeletivoRequest request, AuthContext authContext) {
    authContext.garantirAcessoInstituicao(request.instituicaoId());
    ProcessoSeletivo processo = mapper.toEntity(request);
    processo.setStatus(
      statusRepositoryPort.findByCodigoAndTipo(
        ProcessoStatusCodes.RASCUNHO,
        ProcessoStatusCodes.TIPO_DOMINIO_PROCESSO
      )
    );
    return repositoryPort.save(processo);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ProcessoSeletivo> listarPublicos(Pageable pageable) {
    return repositoryPort.findAllByStatusCodigo(ProcessoStatusCodes.PUBLICADO, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public ProcessoSeletivoDTO.ProcessoSeletivoResponse buscar(Long id) {
    ProcessoSeletivo processo = repositoryPort.findById(id)
      .orElseThrow(() -> new RecursoNaoEncontradoException("Processo não encontrado."));
    return ProcessoSeletivoDTO.ProcessoSeletivoResponse.from(processo);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ProcessoSeletivoDTO.ProcessoSeletivoResponse> listar(Pageable pageable, AuthContext authContext) {
    return repositoryPort.findAllByInstituicaoId(authContext.instituicaoId(), pageable)
      .map(ProcessoSeletivoDTO.ProcessoSeletivoResponse::from);
  }

  @Override
  @Transactional
  public ProcessoSeletivo atualizar(Long id, ProcessoSeletivoDTO.ProcessoSeletivoRequest request, AuthContext authContext) {
    ProcessoSeletivo processo = repositoryPort.findById(id)
      .orElseThrow(() -> new RecursoNaoEncontradoException("Processo não encontrado."));
    authContext.garantirAcessoInstituicao(processo.getInstituicao().getId());
    ProcessoLifecycleRules.garantirPodeAdicionarCargo(processo);
    processo.setTitulo(request.titulo());
    processo.setNumeroEdital(request.numeroEdital());
    processo.setDataInicioInscricao(request.dataInicioInscricao());
    processo.setDataFimInscricao(request.dataFimInscricao());
    if (request.tipoProcesso() != null) {
      processo.setTipoProcesso(request.tipoProcesso());
    }
    return repositoryPort.save(processo);
  }

  @Override
  @Transactional
  public void excluir(Long id, AuthContext authContext) {
    ProcessoSeletivo processo = repositoryPort.findById(id)
      .orElseThrow(() -> new RecursoNaoEncontradoException("Processo não encontrado."));
    authContext.garantirAcessoInstituicao(processo.getInstituicao().getId());
    ProcessoLifecycleRules.garantirPodeAdicionarCargo(processo);
    repositoryPort.deleteById(id);
  }
}