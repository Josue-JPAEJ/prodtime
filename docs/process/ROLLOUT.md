# Rollout incremental

Os status possíveis são **não iniciado**, **em andamento**, **validado** e **concluído**. Uma etapa só avança com evidências registradas em `VALIDATIONS.md`.

## R0 — Bootstrap e baseline

- **Objetivo:** estabelecer um projeto Android executável e sua fundação documental.
- **Escopo:** projeto Kotlin/Compose, API mínima 26, baseline Git e documentação operacional, de produto, arquitetura e processo.
- **Evidências validadas:** projeto Android criado; Kotlin; Jetpack Compose; Minimum SDK API 26; Android SDK configurado; Command-line Tools; Platform-Tools; Build-Tools compatíveis; ADB funcional; aparelho físico reconhecido; Gradle Sync; build bem-sucedido; aplicação padrão executada no aparelho físico; Git inicializado; branches `main` e `develop` criadas e publicadas; baseline `cac1966`; `AGENTS.md`; Skills; documentação de produto; arquitetura direcional; rollout; validações; referências; fundação documental integrada em `develop` pelo PR #2.
- **Critério de conclusão:** fundação documental revisada e integrada sem alterar o baseline Android.
- **Status:** concluído.

## R1 — Motor matemático

- **Objetivo:** especificar e implementar o núcleo puro de cálculo.
- **Escopo:** fórmula validada, unidades, precisão, arredondamento, contratos e testes unitários.
- **Fora de escopo:** UI final e inferência de fórmula a partir de exemplos isolados.
- **Critério de conclusão:** atendido; regras aprovadas e testes determinísticos passando, incluindo as regressões históricas.
- **Etapas:** R1.1 — engenharia reversa e especificação matemática concluídas. R1.2 — motor matemático puro implementado com `BigDecimal`, `HALF_EVEN`, validações de entrada e testes unitários. R1.3 — validação Gradle local concluída e documentação harmonizada.
- **Status:** concluído.

## R2 — Calendário produtivo

- **Objetivo:** representar e calcular tempo produtivo.
- **Escopo:** período, dias úteis, sábados, domingos e feriados segundo regras validadas.
- **Critério de conclusão:** bordas de datas e calendários cobertas por testes unitários.
- **Etapas:** R2.1 — especificação do calendário produtivo concluída. R2.2 — motor puro do calendário implementado e validado localmente. R2.3 — modelo e resolução de feriados implementados e validados localmente. R2.4 — gate final revisado, validação registrada e encerramento concluído.
- **Status:** concluído.

## R3 — Quanto consigo produzir?

- **Objetivo:** entregar o primeiro fluxo funcional do MVP.
- **Escopo:** entradas e resultados do Modo A integrados ao domínio.
- **Critério de conclusão:** fluxo validado, resultados rastreáveis e estados inválidos tratados.
- **Etapas:** R3.1 — contrato concluído. R3.2 — integração concluída. R3.3 — testes implementados. R3.4 — validação Gradle local e encerramento concluídos.
- **Status:** concluído.

## R4 — Quando vou terminar?

- **Objetivo:** entregar o segundo fluxo funcional do MVP.
- **Escopo:** quantidade alvo, data inicial, condições produtivas e data estimada.
- **Critério de conclusão:** fluxo validado com calendário e casos de borda.
- **Etapas:** R4.1 — contrato do Modo B especificado. R4.2 — cálculo de prazo implementado. R4.3 — testes ponta a ponta implementados. R4.4 — validação Gradle local e encerramento concluídos.
- **Status:** concluído.

## R5 — Viabilidade e recomendações

- **Objetivo:** oferecer possibilidades determinísticas simples.
- **Escopo:** recomendações baseadas exclusivamente em regras e premissas validadas.
- **Fora de escopo:** IA, otimização de PCP ou promessa automática de atendimento.
- **Critério de conclusão:** critérios documentados, explicáveis e testados.
- **Etapas:** R5.1 — contrato concluído. R5.2 — implementação concluída. R5.3 — testes concluídos. R5.4 — validação Gradle local e encerramento concluídos.
- **Status:** concluído.

## R6 — UX mobile guiada

- **Objetivo:** consolidar uma experiência simples, específica e rápida.
- **Escopo:** fluxo guiado, hierarquia, feedback, responsividade e prevenção de erros.
- **Critério de conclusão:** jornada principal validada manualmente em dispositivo apropriado.
- **Etapas:** R6.1 e R6.2 — implementados e validados localmente. R6.3 — implementado e validado no aparelho físico. R6.4 — implementado e validado funcionalmente no aparelho; rótulo “Produção” e responsividade refinados. R6.5 — UI de viabilidade implementada e confirmada na rodada física 1. R6.6A — estado compartilhado e gestão de feriados em memória implementados e confirmados na rodada física 1. R6.6B — encerrado por decisão de escopo: persistência é evolução futura, fora do MVP acadêmico. R6.7 — revisão de integração e UX concluída, sem refatoração ampla; gate automatizado registrado em `VALIDATIONS.md`. R6.8 — validação física rodada 1 executada, refinamentos encontrados e implementados; nova validação física pendente.
- **Status:** em andamento.

## R7 — Testes/regressão VBA

- **Objetivo:** ampliar confiança e comparar o domínio com referências históricas.
- **Escopo:** regressões dos casos VBA, datas, inválidos e fronteiras.
- **Critério de conclusão:** tolerâncias justificadas e suíte focada reproduzível.
- **Evidência:** matriz rastreável em `docs/testing/REGRESSION_MATRIX.md`; casos históricos, calendário, feriados, três fluxos integrados e apresentação mapeados para testes reais; lacuna de parsing de inteiro positivo adicionada.
- **Status:** concluído.

## R8 — Polimento e acessibilidade

- **Objetivo:** preparar qualidade de uso do MVP.
- **Escopo:** semântica, contraste, fonte ampliada, teclado, modos claro/escuro e revisão visual.
- **Critério de conclusão:** checklist de acessibilidade e validação manual registrados.
- **Etapas:** R8.1 — auditoria estática de acessibilidade implementada. R8.2 — responsividade e tipografia revisadas. R8.3 — teclado, campos e foco revisados. R8.4 — temas claro/escuro e semântica revisados. R8.5 — validação física rodada 1 executada, refinamentos encontrados e implementados; nova validação física pendente junto com o R6.8.
- **Status:** em andamento.

## R9 — Artigo SBC

- **Objetivo:** documentar problema, método, solução e resultados no contexto acadêmico.
- **Escopo:** texto e referências no formato acadêmico requerido.
- **Critério de conclusão:** artigo revisado e aderente ao modelo aplicável.
- **Status:** não iniciado.

## R10 — Vídeo/README/portfólio

- **Objetivo:** apresentar o projeto com honestidade e clareza.
- **Escopo:** demonstração, documentação de uso e material de portfólio.
- **Critério de conclusão:** materiais coerentes com funcionalidades realmente entregues.
- **Status:** não iniciado.

## R11 — Release v1.0.0

- **Objetivo:** consolidar a primeira versão acadêmica estável.
- **Escopo:** revisão final, evidências, versionamento e artefato de entrega.
- **Critério de conclusão:** escopo do MVP validado, documentação atualizada e release reproduzível.
- **Status:** não iniciado.
