# Seleto AI — Escopo do Produto

## O que é

Plataforma SaaS B2B para gestão de processos seletivos. Permite que instituições (empresas, universidades, órgãos públicos) criem, publiquem e conduzam processos seletivos de ponta a ponta, enquanto candidatos descobrem vagas, se inscrevem e acompanham seu status em tempo real. O sufixo "AI" indica que insights e triagem serão assistidos por inteligência artificial.

---

## Problema que resolve

Processos seletivos hoje são gerenciados com ferramentas fragmentadas: planilhas para candidatos, e-mail para comunicação, PDFs para editais, e sistemas legados sem integração. Resultado: retrabalho, inconsistência de dados, falta de rastreabilidade e experiência ruim para candidato e avaliador.

Seleto AI centraliza tudo em um único sistema auditável, com lifecycle formal e comunicação automatizada.

---

## Personas

| Persona | Papel no sistema | Necessidade principal |
|---|---|---|
| **Contratante** | Instituição que abre o processo | Criar e gerenciar processos, avaliar candidatos |
| **Candidato** | Pessoa que se inscreve | Descobrir processos, se inscrever, acompanhar status |
| **Admin** | Operador Seleto AI | Gerir instituições, suporte, configurações globais |

> Roles PRESTADOR e SUPORTE estão seeded no banco mas ainda sem escopo definido — reservados para fase futura (prestadores de serviço que operam o processo por uma instituição, e suporte técnico interno).

---

## Funcionalidades por módulo

### 1. Autenticação e Acesso
- Login com e-mail e senha, JWT com refresh token
- Registro de contratante via onboarding (cria instituição + usuário em transação)
- Registro de candidato via endpoint dedicado
- Rate limiting por IP

### 2. Instituição
- CRUD de instituição com validação de CNPJ
- Instituição vinculada ao usuário CONTRATANTE no onboarding
- Múltiplos usuários por instituição (fase futura)

### 3. Processo Seletivo
- Criar processo com título, número de edital, período de inscrição, tipo (PÚBLICO, PRIVADO, UNIVERSIDADE)
- Ciclo de vida formal: `RASCUNHO → PUBLICADO → EM_ANDAMENTO → ENCERRADO / CANCELADO`
- Regras de transição: processo PÚBLICO e UNIVERSIDADE exigem número de edital; cargo obrigatório antes de publicar
- Adicionar/remover cargos com número de vagas (só em RASCUNHO)
- Auditoria de eventos (cada transição de status registrada)
- Listagem pública de processos PUBLICADOS
- Listagem privada por instituição

### 4. Inscrição de Candidatos
- Candidato com conta própria (e-mail/senha)
- Inscrição vinculada a processo + cargo específico
- Validação de período de inscrição, unicidade (1 inscrição por processo)
- CPF coletado no momento da inscrição
- Status da inscrição: `INSCRITO → EM_ANALISE → CLASSIFICADO → APROVADO / REPROVADO`
- Contratante avança status dos candidatos manualmente

### 5. Analytics (em desenvolvimento)
- Dashboard por processo: total de inscrições, aprovados, reprovados
- Snapshots diários (estrutura criada, alimentação pendente)
- Insights (estrutura criada, geração por IA pendente)
- Alertas configuráveis (estrutura criada, regras pendentes)

### 6. Notificações por E-mail (fase futura)
- Confirmação de inscrição
- Mudança de status da inscrição
- Abertura de processo para instituições cadastradas
- E-mails transacionais via Resend

### 7. Triagem por IA (fase futura)
- Análise de currículo enviado vs. requisitos do cargo
- Score de aderência por candidato
- Ranking automático de candidatos
- Geração de insights e alertas no dashboard analytics

### 8. Upload de Documentos (fase futura)
- Currículo em PDF/DOCX por candidato
- Edital em PDF por processo
- Armazenamento em object storage (S3/R2)

---

## O que está fora do escopo atual

- Pagamento de taxa de inscrição
- Assinatura digital de documentos
- Videoentrevista integrada
- Aprovação em múltiplas etapas (prova, entrevista, prática) — o status atual é linear
- Portal público com SEO para candidatos descobrirem vagas organicamente
- App mobile

---

## Ciclo de vida completo (fluxo de uso)

```
[Contratante] Onboarding → Cria processo (RASCUNHO) → Adiciona cargos
           → Publica (PUBLICADO) → Inicia inscrições (EM_ANDAMENTO)
           → Candidatos se inscrevem → Contratante avalia e avança status
           → Encerra processo (ENCERRADO)
```

---

## Métricas de sucesso do produto

- Tempo médio de criação de processo (da ideia ao publicado)
- Taxa de inscrição por processo
- Taxa de conclusão (processos que chegam a ENCERRADO vs. CANCELADO)
- NPS de candidatos e contratantes
