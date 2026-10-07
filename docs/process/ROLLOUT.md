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
- **Critério de conclusão:** atendido pelas três jornadas funcionais, navegação, calendário, feriados, viabilidade, feedback de resultados, cópia, responsividade, tratamento de erros e validações locais e físicas no Samsung SM-A066M.
- **Etapas:** R6.1–R6.7 concluídas. R6.8 — integração final e jornada principal aprovadas no dispositivo físico.
- **Status:** concluído.

## R7 — Testes/regressão VBA

- **Objetivo:** ampliar confiança e comparar o domínio com referências históricas.
- **Escopo:** regressões dos casos VBA, datas, inválidos e fronteiras.
- **Critério de conclusão:** tolerâncias justificadas e suíte focada reproduzível.
- **Evidência:** matriz rastreável em `docs/testing/REGRESSION_MATRIX.md`; casos históricos, calendário, feriados, três fluxos integrados e apresentação mapeados para testes reais; lacuna de parsing de inteiro positivo adicionada.
- **Status:** concluído.

## R8 — Polimento e acessibilidade

- **Objetivo:** preparar qualidade de uso do MVP.
- **Escopo:** semântica, contraste, fonte ampliada, teclado, modos claro/escuro e revisão visual.
- **Critério de conclusão:** atendido pelo checklist de acessibilidade, semântica, responsividade, teclado/foco/cursor, evidências de light/dark, cards semanticamente diferenciados, touch targets, mensagens textuais, sanitização de input, limites operacionais e validações local e física.
- **Etapas:** R8.1 — concluído. R8.2 — concluído. R8.3 — concluído. R8.4 — concluído. R8.5 — concluído. R8.6 — concluído.
- **Status:** concluído.

## R9 — Artigo SBC

- **Objetivo:** documentar problema, método, solução e resultados no contexto acadêmico.
- **Escopo:** texto e referências no formato acadêmico requerido.
- **Critério de conclusão:** artigo revisado e aderente ao modelo aplicável.
- **Etapas:** R9.0 — base de evidências e plano do artigo concluídos. R9.1 — validação bibliográfica e requisitos de entrega concluídos. R9.2 — primeira versão completa e checklist de releitura concluídos, registrados em `docs/academic/ARTICLE_DRAFT.md` e `ARTICLE_DRAFT_REVIEW.md`. R9.3 — revisão técnica/acadêmica integral e auditoria de conteúdo concluídas, registradas em `ARTICLE_DRAFT_REVIEW.md` e `ARTICLE_CONTENT_AUDIT.md`; conteúdo pronto para formatação. R9.4 — em andamento: R9.4A consolidou autoria/texto; R9.4B integrou as quatro capturas reais e gerou primeira diagramação no template tradicional SBC em `docs/academic/submission/prodtime_sbc_preview.pdf` (11 páginas; 357.298 bytes; abaixo de 5 MB), pronta para revisão humana. R9.4C — revisão humana da estrutura/figuras aprovada, unidade de desperdício corrigida e Resumo/Abstract condensados para dez linhas cada; PDF recompilado e inspecionado (11 páginas; 355.967 bytes). R9.4 concluído editorialmente; eventual adequação ao limite institucional de páginas será necessária somente se a Gran informar uma regra ainda indisponível.
- **Status:** concluído quanto ao conteúdo e artigo produzido.

## R10 — Vídeo/README/portfólio

- **Objetivo:** apresentar o projeto com honestidade e clareza.
- **Escopo:** identidade, documentação de uso, vídeo acadêmico e materiais de portfólio.
- **Critério de conclusão:** atendido por identidade, README, roteiro, vídeo gravado/revisado/publicado e materiais de portfólio coerentes com funcionalidades entregues.
- **Etapas:** R10.1 — concluído, identidade integrada pelo PR #29 e aprovação física informada pelo autor. R10.2 — concluído, README integrado pelo PR #30. R10.3 — concluído, roteiro integrado pelo PR #31 e vídeo gravado/revisado/publicado pelo autor, registrado em R10.5. R10.4 — concluído, materiais de portfólio integrados pelo PR #32; preparação documental, sem publicação automática desses materiais. R10.5 — registro do vídeo e encerramento formal concluídos.
- **Evidência do vídeo:** https://youtu.be/UrrcNwLq76w — não listado, aproximadamente 5min48s, conforme relato do autor. A plataforma solicita vídeo de 5 minutos no YouTube e permite modo não listado; a tela relatada não indica máximo de 5 minutos. Não se presume aprovação institucional da duração.
- **Status:** concluído.

## R11 — Release v1.0.0

- **Objetivo:** consolidar a primeira versão acadêmica estável.
- **Escopo:** revisão final, evidências, versionamento e artefato de entrega.
- **Critério de conclusão:** gates técnicos e auditoria de publicação aprovados, revisão humana e release reproduzível.
- **Etapas:** R11.1 — concluído: versão 1.0.0/código 1, testes/builds aprovados e APK debug assinado preparado localmente; proprietário avaliou e aceitou a exposição do e-mail adicional e VBA histórico em R11.1B, dispensando explicitamente sanitização/reescrita histórica. R11.2 — concluído: main promovida pelo PR #35, tag v1.0.0 e GitHub Release publicada com APK acadêmico e checksum. R11.3 — concluído: repositório público. R11.4 — concluído: verificação pública confirmada pelo autor e fechamento documental preparado.
- **Status:** concluído.
- **Gate de preparação R11.1:** SIM, conforme decisão explícita do proprietário; ver `docs/release/PUBLIC_REPOSITORY_AUDIT.md`. Publicação e verificação pública confirmadas pelo autor em R11.4; histórico preservado. A API permanece inacessível neste ambiente; integração deste fechamento documental depende dos PRs.
