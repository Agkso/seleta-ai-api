package com.seletoai.core.useCase.processoSeletivo;

import com.seletoai.core.domain.exception.RecursoNaoEncontradoException;
import com.seletoai.core.domain.processoSeletivo.ProcessoEventos;
import com.seletoai.core.domain.processoSeletivo.ProcessoLifecycleRules;
import com.seletoai.core.domain.processoSeletivo.ProcessoSeletivo;
import com.seletoai.core.domain.processoSeletivo.ProcessoStatusCodes;
import com.seletoai.core.ports.in.processoSeletivo.EncerrarProcessoUseCasePort;
import com.seletoai.core.ports.out.processoSeletivo.ProcessoSeletivoRepositoryPort;
import com.seletoai.core.ports.out.status.StatusRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EncerrarProcessoUseCase implements EncerrarProcessoUseCasePort {

  private final ProcessoSeletivoRepositoryPort processoRepository;
  private final StatusRepositoryPort statusRepository;
  private final ProcessoAuditoriaWriter auditoriaWriter;

  @Override
  @Transactional
  public ProcessoSeletivo execute(Long processoId) {
    ProcessoSeletivo processo = processoRepository.findById(processoId)
      .orElseThrow(() -> new RecursoNaoEncontradoException("Processo não encontrado."));
    ProcessoLifecycleRules.garantirPodeEncerrar(processo);
    processo.setStatus(
      statusRepository.findByCodigoAndTipo(ProcessoStatusCodes.ENCERRADO, ProcessoStatusCodes.TIPO_DOMINIO_PROCESSO)
    );
    ProcessoSeletivo salvo = processoRepository.save(processo);
    auditoriaWriter.registrar(salvo, ProcessoEventos.PROCESSO_ENCERRADO, null);
    return salvo;
  }
}
