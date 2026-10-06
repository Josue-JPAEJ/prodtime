# Auditoria de conteúdo do artigo

## 1. Escopo da auditoria

Revisão integral de `ARTICLE_DRAFT.md` para R9.3, tendo `develop` em `0618d76b432e77808edd9f609a0ae08ffb5f6a23` como base. Foram lidas as fontes acadêmicas, de produto, domínio, arquitetura, regressões e processo solicitadas. Consultas pontuais ao código resolveram o contrato de prazo, a ordem matemática, a busca de fitas e uma divergência sobre limites de entrada. Não houve alteração nem execução de código Android ou testes.

## 2. Afirmações técnicas verificadas

- Android nativo com Kotlin, Compose e Material 3; domínio Kotlin puro separado da apresentação; funcionamento local, sem backend.
- Seis componentes identificados conforme a arquitetura: capacidade, calendário, resolução de feriados, estimativa, prazo e viabilidade.
- Conversão `cm/min × 60 / 100`; produção bruta decimal; bruta integral `HALF_EVEN`; desperdício decimal sobre a bruta arredondada; líquida integral `HALF_EVEN`; saldo igual à líquida menos a meta. Conferidos em `CALCULATION_SPEC.md` e `ProductionCapacityCalculator.kt`.
- Pontas aplicadas antes da classificação; decisão única por data; sábado, domingo e feriado sujeitos às políticas; duplicatas deduplicadas; 29/02 anual somente em ano bissexto; cadastro em memória sem persistência entre processos.
- **Prazo corrigido:** `ProductionDeadlineInput` não recebe `endDate`; recebe meta, início e parâmetros/políticas. A busca percorre datas, acumula dias produtivos e reutiliza `ProductionCapacityCalculator`; conclusão é a primeira data suficiente e resultado, com horizonte técnico finito. Fonte: `PRODUCTION_DEADLINE_SPEC.md` e código correspondente.
- Viabilidade determinística: atendimento, diferença, mínimo de fitas e adicional; não recomenda fitas em zero dias produtivos; não modela disponibilidade de máquinas nem otimização industrial.
- UI: limites na entrada, conversão decimal, sanitização, teclado/foco/cursor, cópia, temas e semântica de acessibilidade, sem alegação de avaliação abrangente.
- **Divergência contextual:** `OPEN_QUESTIONS.md` menciona fitas menores que 1.000; o código usa limite exclusivo de 1.000.000, coerente com `BUSINESS_RULES.md` e validação física. O artigo não enumera valores de limites e não reproduz a divergência. Nenhuma regra foi modificada; harmonização das fontes de domínio permanece fora do escopo desta tarefa.

## 3. Valores quantitativos conferidos

Valores confrontados com `ARTICLE_EVIDENCE.md`, `CALCULATION_SPEC.md`, `REGRESSION_MATRIX.md` e os registros físicos em `VALIDATIONS.md`.

| Caso | Condições essenciais | Evidência preservada |
|---|---|---|
| Regressão 2 fitas | 25 cm/min; 16 h/dia; 19 dias; 3% | Bruta 9.120 m; desperdício 273,6 m; líquida 8.846 m. |
| Regressão 3 fitas | Mesmas condições; 3 fitas | Bruta 13.680 m; desperdício 410,4 m; líquida 13.270 m. |
| Prazo | Início 05/10/2026; meta 10.000 m; 3 fitas; 25 cm/min; 16 h/dia; 3%; sem fins de semana/feriados | 23/10/2026; 15 dias produtivos; 10.476 m; saldo +476 m. |
| Viabilidade 2 fitas | Período 01/10–27/10/2026; 19 dias; meta 10.000 m; condições da regressão | Déficit 1.154 m; mínimo 3; adicional 1. |
| Viabilidade 3 fitas | Mesmo cenário; 3 fitas | Excedente +3.270 m; mínimo 3; adicional 0. |
| Feriado não trabalhado | 01/12–31/12/2026; 28 cm/min; 1 fita; 16 h/dia; 3% | 22 dias produtivos; 5.737 m. |
| Feriado trabalhado | Mesmo cenário; política alterada | 23 dias produtivos; 5.997 m. |

A conferência aritmética respeitou ambas as fronteiras de arredondamento, inclusive no cenário de dezembro. A contagem de dias do prazo e do intervalo histórico também foi conferida. Essas verificações documentais não são apresentadas como nova execução do domínio.

Os 118 métodos anotados com `@Test` foram confirmados nas fontes, sem afirmar que 118 testes passaram. Os registros históricos de `testDebugUnitTest` e `assembleDebug` com `BUILD SUCCESSFUL` permanecem separados; tarefas atualizadas não implicam reexecução dos métodos. O usuário informou validação local após R9.2 com todas as tarefas atualizadas. Não há métrica nova de cobertura ou precisão.

## 4. Literatura e citações

Preservadas somente as quatro entradas de `REFERENCES.md`, agora em ordem alfabética por primeiro autor, com todos os metadados e DOIs inalterados. Citações autor-data conferidas contra cada entrada.

- Karacapilidis e Pappis (1996): planejamento e controle multifásico como contexto central.
- Serafini e Speranza (1992): scheduling com máquinas/teares paralelos como contexto central, sem implementação dos algoritmos no aplicativo.
- Laoboonlur, Hodgson e Thoney (2006): contexto complementar de tingimento/acabamento, sem equivalência à produção de fitas.
- Hodge et al. (2011): contexto complementar lean e desperdícios, sem atribuir redução medida ao ProdTime.

O alcance da consulta é declarado uma vez na fundamentação: metadados e resumos editoriais, sem leitura integral ou validação direta da solução. Não há nova referência, citação direta, página específica inventada ou data de acesso criada.

## 5. Limitações preservadas

Operação local e sem backend; feriados somente em memória; dependência dos parâmetros do usuário; capacidade constante por estimativa; ausência de telemetria em tempo real; estimativas sem promessa operacional; ausência de IA e PCP no MVP. Integração ERP/PCP, disponibilidade de máquinas, capacidade variável, persistência, histórico, IA e IoT continuam futuras.

A validação física é circunscrita a um dispositivo e aos cenários registrados. O texto não sustenta ganhos de produtividade, economia de tempo, redução de desperdício, satisfação de usuários, cobertura percentual, validação estatística ou generalização industrial.

## 6. Meta-linguagem removida

Removidos do artigo a nota de versão, códigos Rn, rollout, gate e nomes dos arquivos internos de evidência. Desenvolvimento incremental e validação são apresentados como método e resultados, sem narrativa de PRs ou gestão de projeto. “Mobile” na narrativa em português foi substituído por “móvel”.

Foram reduzidas ressalvas repetidas, duplicação entre subseções e Tabela 2 e repetição do modelo do dispositivo. Resumo e Abstract foram harmonizados após o corpo; palavras-chave passaram a representar o problema, mantendo Kotlin na descrição tecnológica.

## 7. Pendências antes da formatação

- Dados reais para `[AUTOR]`, `[INSTITUIÇÃO]`, `[CURSO]` e `[E-MAIL]`.
- Figuras reais: tela inicial, resultado de estimativa e resultado de viabilidade; os marcadores e legendas permanecem.
- Confirmação institucional do número de páginas e do template SBC aplicável.
- Ajustes de extensão, posicionamento de figuras/tabelas, formatação final e geração de PDF em R9.4.

Nenhuma dessas pendências autoriza inventar informação. PDF, DOCX, screenshots e formatação não foram produzidos nesta tarefa.

## 8. Gate para R9.4

**SIM — conteúdo acadêmico pronto para formatação final.** R9.3 concluída, R9.4 pendente e não iniciada; R9 em andamento. Os gates documentais incluem conferência técnica/quantitativa, equivalência Resumo/Abstract, integridade das referências, buscas solicitadas e diff restrito aos cinco arquivos acadêmicos/de processo.

O gate avalia conteúdo. PR, ausência de conflitos no GitHub e merge só podem ser declarados mediante confirmação própria; bloqueio de API não é tratado como sucesso de integração.

## Atualização documental R9.4

O registro R9.3 acima permanece histórico. Na base `develop` em `74ddfbda862c429877d8e641f301193046780786` (PR #25), a autoria fornecida pelo usuário foi aplicada ao rascunho e à nova fonte `ARTICLE_FINAL.md`. Título, Resumo/Abstract, fórmulas, resultados e quatro referências foram preservados; somente autoria, chamadas, marcadores e legendas das figuras distinguem a fonte consolidada do corpo revisado.

A seleção temática passa a quatro figuras: tela inicial, produção de 13.270 m, prazo de 23/10/2026 e viabilidade com déficit de 1.154 m. Não há imagens reais acessíveis para integração ou inspeção; as legendas descrevem cenários documentados, sem certificar capturas ausentes. Dados institucionais deixaram de ser pendência; imagens reais, template, paginação e PDF continuam pendentes.

`ARTICLE_SUBMISSION.md` registra insumos, critérios e checklist. A disponibilidade de Pandoc/LaTeX não equivale a PDF validado. **Gate final R9.4: NÃO; base textual preparada.** Sem código alterado, nova execução Gradle, métricas, experimentos ou referências.
