package com.seletoai.core.domain.processoSeletivo;

import com.seletoai.core.domain.exception.RegraNegocioException;
import com.seletoai.core.domain.status.Status;

public final class ProcessoLifecycleRules {

  private ProcessoLifecycleRules() {}

  public static String codigo(Status status) {
    return status != null ? status.getCodigo() : null;
  }

  public static boolean exigeEditalFormal(TipoProcesso tipo) {
    return tipo == TipoProcesso.PUBLICO || tipo == TipoProcesso.UNIVERSIDADE;
  }

  public static void garantirPodePublicar(ProcessoSeletivo processo, long quantidadeCargos) {
    garantirStatus(processo, ProcessoStatusCodes.RASCUNHO, "Somente processo em RASCUNHO pode ser publicado.");
    if (quantidadeCargos < 1) {
      throw new RegraNegocioException("É necessário cadastrar ao menos um cargo antes de publicar.");
    }
    if (exigeEditalFormal(processo.getTipoProcesso())) {
      String edital = processo.getNumeroEdital();
      if (edital == null || edital.isBlank()) {
        throw new RegraNegocioException("Processos do tipo " + processo.getTipoProcesso() + " exigem número de edital.");
      }
    }
  }

  public static void garantirPodeIniciar(ProcessoSeletivo processo) {
    garantirStatus(processo, ProcessoStatusCodes.PUBLICADO, "Somente processo PUBLICADO pode ser iniciado (inscrições).");
  }

  public static void garantirPodeEncerrar(ProcessoSeletivo processo) {
    garantirStatus(processo, ProcessoStatusCodes.EM_ANDAMENTO, "Somente processo EM_ANDAMENTO pode ser encerrado.");
  }

  public static void garantirPodeCancelar(ProcessoSeletivo processo) {
    String c = codigo(processo.getStatus());
    if (ProcessoStatusCodes.ENCERRADO.equals(c) || ProcessoStatusCodes.CANCELADO.equals(c)) {
      throw new RegraNegocioException("Processo encerrado ou cancelado não pode ser cancelado.");
    }
  }

  public static void garantirPodeAdicionarCargo(ProcessoSeletivo processo) {
    garantirStatus(processo, ProcessoStatusCodes.RASCUNHO, "Cargos só podem ser incluídos enquanto o processo está em RASCUNHO.");
  }

  public static void garantirProcessoAceitaInscricao(ProcessoSeletivo processo, java.time.LocalDateTime agora) {
    String c = codigo(processo.getStatus());
    if (!ProcessoStatusCodes.PUBLICADO.equals(c) && !ProcessoStatusCodes.EM_ANDAMENTO.equals(c)) {
      throw new RegraNegocioException("Inscrições não estão abertas para este processo.");
    }
    if (agora.isBefore(processo.getDataInicioInscricao()) || agora.isAfter(processo.getDataFimInscricao())) {
      throw new RegraNegocioException("Fora do período de inscrição do processo.");
    }
  }

  private static void garantirStatus(ProcessoSeletivo processo, String esperado, String mensagem) {
    String atual = codigo(processo.getStatus());
    if (!esperado.equals(atual)) {
      throw new RegraNegocioException(mensagem + " Status atual: " + atual);
    }
  }
}
