# Plano do artigo ProdTime

Este plano orienta a redação futura; não é o artigo final. A redação deve usar somente as evidências rastreáveis de `ARTICLE_EVIDENCE.md` e respeitar os limites registrados em `ACADEMIC_REQUIREMENTS.md`.

## Títulos provisórios

1. **ProdTime: aplicativo móvel para estimativa de capacidade e prazo na produção de fitas têxteis** — título de trabalho principal.
2. **Uma solução móvel determinística para estimativas produtivas na indústria de fitas têxteis** — alternativa.
3. **Estimativa de produção, prazo e viabilidade de metas em fitas têxteis por aplicativo Android** — alternativa.

O título definitivo poderá ser ajustado na revisão acadêmica.

## Estrutura consolidada

- Título;
- Autor / instituição — somente placeholders documentais enquanto os dados finais não forem fornecidos;
- Resumo;
- Palavras-chave;
- Abstract;
- Keywords;
- 1. Introdução;
- 2. Fundamentação e Trabalhos Relacionados;
- 3. Metodologia;
- 4. Desenvolvimento do ProdTime;
- 5. Resultados e Validação;
- 6. Limitações e Trabalhos Futuros;
- 7. Conclusão;
- Referências.

Se o template específico posteriormente entregue pela instituição adotar outros nomes ou outra organização, a estrutura será adaptada na formatação R9.4.

## Resumo e Abstract

Devem sintetizar contexto e problema, objetivo, recorte do MVP, método de especificação e implementação, evidências verificáveis, limitações e contribuição. Não devem incluir métricas não medidas. O Abstract deve refletir fielmente o conteúdo do Resumo, sem introduzir alegações adicionais.

## 1. Introdução — sequência argumentativa

1. contextualizar a produção têxtil;
2. explicar que o planejamento depende de múltiplas variáveis;
3. usar a literatura para mostrar que scheduling e controle em ambientes têxteis podem ser complexos;
4. apresentar o contexto real que originou o ProdTime, no qual as estimativas dependiam do conhecimento de profissionais experientes;
5. descrever a evolução manual → VBA → ERP Web → aplicação móvel isolada;
6. declarar o objetivo do ProdTime;
7. delimitar o MVP;
8. explicitar a contribuição do trabalho.

O texto não deve sugerir que o ProdTime resolve o problema geral de scheduling industrial.

## 2. Fundamentação e Trabalhos Relacionados

| Referência | Tema | Como apoia o artigo | O que não podemos afirmar |
|---|---|---|---|
| Karacapilidis e Pappis (1996) | Planejamento e controle da produção têxtil, com foco em MPS e processo multifásico. | **Fundamentação central:** contextualiza a complexidade do planejamento em sistemas produtivos têxteis com fases, unidades, horizontes e requisitos distintos. | Que o trabalho propõe uma aplicação móvel equivalente ao ProdTime, valida suas regras ou sustenta conclusões além do resumo editorial consultado. |
| Serafini e Speranza (1992) | Problemas de scheduling têxtil, atraso máximo, teares paralelos, algoritmos, limites e heurística baseada em programação linear. | **Fundamentação central:** sustenta que a programação da produção têxtil envolve problemas formais de alocação e sequenciamento. | Que o ProdTime implementa os algoritmos estudados, otimiza scheduling geral ou foi validado pelo artigo. |
| Laoboonlur, Hodgson e Thoney (2006) | Scheduling detalhado em tingimento e acabamento de malha, flexible job shop, setups dependentes de sequência e family scheduling. | **Fundamentação complementar:** exemplifica características específicas e complexidade de scheduling em outro processo do setor têxtil. | Que o contexto de tingimento/acabamento é equivalente à produção de fitas ou que sua abordagem foi implementada no ProdTime. |
| Hodge et al. (2011) | Princípios lean na indústria têxtil, desperdícios, atividades sem valor e modelo de implementação. | **Fundamentação complementar:** oferece contexto setorial sobre redução de desperdícios e melhoria sistemática de processos. | Que o ProdTime implementa integralmente lean, comprova redução mensurada de desperdícios ou é validado pelos estudos de caso do artigo. |

A classificação distingue função central ou complementar sem criar ranking entre os trabalhos. Metadados e escopos temáticos foram verificados em páginas editoriais; não há alegação de leitura integral.

## 3. Metodologia

Caracterizar objetivamente o trabalho como desenvolvimento aplicado de software baseado em um problema real e em engenharia reversa de uma solução legada. Descrever:

1. identificação do problema;
2. análise do fluxo manual;
3. análise do VBA legado;
4. especificação das regras;
5. modelagem determinística;
6. implementação incremental;
7. testes automatizados;
8. regressões históricas;
9. validação local;
10. validação física.

Não usar rótulos metodológicos não comprovados, como “Design Science Research”, “estudo experimental”, “pesquisa quantitativa” ou “estudo de caso formal”.

## 4. Desenvolvimento do ProdTime

Apresentar arquitetura UI/domínio, os componentes de domínio, uso de `BigDecimal` e `java.time`, calendário e feriados, jornadas Compose, viabilidade determinística, sanitização e acessibilidade. Manter regras de negócio separadas da apresentação e não comunicar visão futura como funcionalidade entregue.

## 5. Resultados e Validação

Separar claramente evidências numéricas, evidência automatizada e validação manual:

### 5.1 Regressão histórica

- 2 fitas: **8.846 m**;
- 3 fitas: **13.270 m**.

### 5.2 Prazo

- meta: **10.000 m**;
- resultado: **23/10/2026**;
- **15 dias produtivos**;
- **10.476 m** produzidos;
- saldo de **+476 m**.

### 5.3 Viabilidade

- 3 fitas: meta atendida, excedente de **+3.270 m**, mínimo de 3 fitas e adicional 0;
- 2 fitas: meta não atendida, déficit de **1.154 m**, mínimo de 3 fitas e adicional 1.

### 5.4 Calendário e feriado

- feriado não trabalhado: **22 dias** e **5.737 m**;
- feriado trabalhado: **23 dias** e **5.997 m**.

### 5.5 Testes e gates

Registrar separadamente que foram identificados **118 métodos anotados com `@Test` nas fontes da suíte JVM** e que a tarefa `testDebugUnitTest` foi concluída com `BUILD SUCCESSFUL` no gate final. Não converter a contagem de métodos em “118 testes passaram”. Distinguir também build Android, regressões e observações da validação física.

## 6. Limitações e Trabalhos Futuros

Registrar operação local, feriados sem persistência entre processos, ausência de dados de máquina em tempo real, dependência dos parâmetros informados, caráter estimativo sem promessa operacional e ausência de backend, IA e PCP. Apresentar como trabalhos futuros somente ERP Web, PCP, disponibilidade de máquinas, capacidade variável, persistência, histórico, IA e IoT.

## 7. Conclusão

Responder, dentro das limitações declaradas, se o MVP estima produção em um período, estima a primeira data de atendimento de uma quantidade, avalia viabilidade e fitas mínimas/adicionais, reproduz regressões conhecidas e possui validação técnica e física documentada.

## Plano de figuras

Conjunto mínimo recomendado, sem geração de imagens nesta etapa:

1. **Figura 1 — Tela inicial do ProdTime.** Legenda: “Tela inicial com acesso aos três fluxos do ProdTime.”
2. **Figura 2 — Fluxo de capacidade com resultado.** Legenda: “Estimativa de produção para um período e parâmetros informados.”
3. **Figura 3 — Fluxo de prazo ou de verificação de meta.** Legenda: “Estimativa de prazo ou análise de uma meta produtiva.”
4. **Figura 4 — Resultado de viabilidade.** Legenda: “Exemplo de resultado com meta atendida ou não atendida e recomendação de fitas.”

Evitar nove screenshots se isso prejudicar o espaço disponível. As imagens adicionais continuam úteis para README e vídeo.

## Plano de tabelas

Usar no máximo três tabelas, somente quando contribuírem para síntese ou comparação:

1. **Tabela 1 — Principais parâmetros de entrada:** grandeza, unidade e função no cálculo;
2. **Tabela 2 — Casos de validação e regressão:** cenário, entradas essenciais e resultado observado;
3. **Tabela 3 — Escopo entregue versus limitações:** opcional e condicionada ao espaço disponível.

Não criar tabelas decorativas.

## Requisitos de entrega

Requisitos conhecidos:

- artigo em PDF;
- padrão/modelo SBC;
- arquivo PDF de até 5 MB;
- vídeo no YouTube com aproximadamente 5 minutos;
- prazo geral do projeto: 24/10/2026.

O modelo SBC existe e é disponibilizado em LaTeX e, em diversas chamadas, MS Word; a submissão normalmente ocorre em PDF. O limite de páginas não é inerente ao template e depende do evento ou da instituição.

**PENDENTE DE CONFIRMAÇÃO INSTITUCIONAL:** número de páginas específico desta entrega. A pendência não bloqueia R9.2; a primeira versão deve ser concisa para permitir ajuste posterior ao limite confirmado.

## Consolidação executada em R9.4

O plano anterior é mantido como histórico. A fonte textual consolidada está em `ARTICLE_FINAL.md`, com o título principal preservado e dados reais do autor aplicados. O rascunho continua disponível em `ARTICLE_DRAFT.md`.

A seleção temática atual tem quatro figuras: (1) tela inicial; (2) estimativa de 13.270 m em 19 dias; (3) prazo em 23/10/2026; (4) viabilidade com duas fitas e déficit de 1.154 m. Chamadas e legendas foram preparadas; nenhuma imagem foi integrada por ausência dos arquivos reais nesta sessão. Feriados e VBA continuam opcionais.

O checklist e os critérios de conferência constam de `ARTICLE_SUBMISSION.md`. R9.4 permanece em andamento, com template, limite de páginas, integração visual e PDF final pendentes; não houve expansão experimental nem início de R10/R11.
