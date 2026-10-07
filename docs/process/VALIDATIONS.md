# Registro de validações

## Evidências anteriores ao rollout funcional

As seguintes evidências foram informadas e aceitas como baseline conhecido desta tarefa:

- Android SDK funcional;
- `adb` funcional;
- aparelho físico reconhecido;
- build bem-sucedido;
- aplicativo padrão “Hello Android!” executado no aparelho;
- baseline Git `cac1966` (`chore: bootstrap ProdTime Android project`).

Essas evidências registram o baseline; não significam que foram repetidas durante toda mudança documental.

## Encerramento do R0

- **Etapa:** R0 — Bootstrap e baseline
- **Branch:** `develop`
- **Baseline Android:** `cac1966`
- **Merge de integração:** `5a4ebd6`
- **Pull Request:** #2 — `docs: establish ProdTime project foundation`
- **Validações conhecidas:** SDK funcional; ADB funcional; aparelho físico reconhecido; build Android bem-sucedido; “Hello Android!” executado no aparelho; documentação criada e revisada; nenhum código Android alterado durante a fundação documental; `develop` sincronizada após o merge.
- **Observação:** as evidências técnicas de SDK, ADB, aparelho físico, build e execução foram obtidas manualmente antes desta tarefa e não foram repetidas neste encerramento documental.

## R1.1 — Engenharia reversa e especificação matemática

- **Data:** 2026-10-04
- **Etapa:** R1.1
- **Branch:** branch interna `work`, tendo `develop` como base de integração solicitada
- **Fonte primária:** `docs/legacy/vba/CalMetrosNaMaq.txt`, lido integralmente.
- **Procedimentos analisados:** `ProducaoEstimada`, `TotalMetros`, `ListFeriados`, `iUserForm_Activate`, inicialização, handlers de campos e opções de calendário.
- **Regressões verificadas:** 3 fitas resultam em 13.270 m líquidos, com 410,4 m de desperdício; 2 fitas resultam em 8.846 m líquidos, com 273,6 m de desperdício; peso líquido total de 4,620 kg a 0,12 g/m resulta em 38.500 m.
- **Fórmulas confirmadas:** conversão `cm/min → m/h`; produção bruta; desperdício sobre a bruta; produção líquida; saldo; total de horas; peso para metragem.
- **Comportamento numérico:** identificadas as coerções `Double → Long`, inclusive arredondamento VBA para o inteiro mais próximo e para o par em empates, e os limites de `Integer`/`Long`.
- **Riscos identificados:** subtrações múltiplas no calendário, recorrência textual de feriados, zero confundido com `Empty`, ausência de limites explícitos, coerções dependentes de tipo/localidade e helpers externos indisponíveis.
- **Resultado:** especificação documental preparada; R1 permanece em andamento e o calendário permanece reservado ao R2.
- **Validação manual:** não aplicável; não houve alteração executável.
- **Android:** nenhum código, teste, UI, dependência ou configuração Android alterado.

## R1.2 — Motor matemático puro de capacidade

- **Data:** 2026-10-04
- **Etapa:** R1.2
- **Branch:** branch interna `work`, tendo `develop` como base de integração solicitada
- **Arquitetura:** modelos de entrada e resultado e calculadora Kotlin pura em `br.com.prodtime.domain`, sem dependências de Android, Compose, calendário ou UI.
- **Contrato numérico:** `BigDecimal` determinístico; velocidade, horas e percentual decimais; fitas e dias inteiros positivos; meta inteira opcional e não negativa; nenhum default embutido.
- **Política:** `RoundingMode.HALF_EVEN` explícito na produção bruta e na produção líquida; desperdício decimal calculado sobre a bruta já arredondada.
- **Cobertura criada:** 8 testes unitários com as regressões de 3 e 2 fitas, empates `HALF_EVEN` para o par inferior e superior, desperdício zero, meta ausente, 7,5 horas/dia e todas as entradas inválidas do contrato.
- **Regressões especificadas:** 13.680 m brutos, 410,4 m de desperdício, 13.270 m líquidos e saldo 3.270 m para 3 fitas; 9.120 m brutos, 273,6 m de desperdício, 8.846 m líquidos e saldo −1.154 m para 2 fitas.
- **Comandos/testes:** `./gradlew testDebugUnitTest` não iniciou porque o wrapper não tem permissão de execução; `bash ./gradlew testDebugUnitTest` não baixou o Gradle 8.13 porque o proxy retornou HTTP 403; `gradle testDebugUnitTest`, com Gradle 8.14.4 já instalado, chegou à configuração e falhou porque não existe Android SDK configurado no ambiente (`ANDROID_HOME`/`sdk.dir`). Como validação equivalente focada, as duas fontes Kotlin foram compiladas diretamente com o compilador 2.0.21 presente no cache e executadas pelo `JUnitCore` 4.13.2 local.
- **Resultado:** compilação Kotlin concluída e 8 testes JUnit aprovados (`OK (8 tests)`); a tarefa Gradle permanece limitada exclusivamente pela ausência do Android SDK no ambiente.
- **Validação manual:** não aplicável a motor Kotlin puro.
- **Riscos:** a suíte deve ser executada em ambiente com Android SDK antes da integração; políticas de calendário permanecem fora do escopo e reservadas ao R2.
- **Proteção de escopo:** nenhuma UI, configuração Gradle, dependência, recurso Android ou fonte VBA foi alterada.

## R1.3 — Validação local e encerramento do motor matemático

- **Ambiente:** Windows 11 amd64; Microsoft OpenJDK 17.0.20.1 LTS 64-bit; Gradle 8.13; Kotlin 2.0.21.
- **Comando:** `.\gradlew.bat testDebugUnitTest`
- **Resultado:** `BUILD SUCCESSFUL in 1m 47s`; `22 actionable tasks: 12 executed, 10 up-to-date`.
- **Git:** branch `develop`, sincronizada com `origin/develop`, com working tree clean.
- **Evidência:** execução realizada manualmente no ambiente local real do projeto; o R1.2 está validado pela tarefa Gradle.
- **Limitação anterior:** a falha registrada no R1.2 era causada pelo Java 8 32-bit ativo no terminal. O ambiente foi corrigido para Microsoft OpenJDK 17 64-bit, resolvendo a limitação ambiental sem qualquer alteração de código.
- **Resultado da etapa:** motor matemático validado e R1 encerrado; as questões restantes pertencem ao calendário, UI, peso/metragem ou evoluções futuras.

## R2.1 — Especificação do calendário produtivo

- **Data:** 2026-10-05
- **Etapa:** R2.1
- **Branch:** branch interna `work`, tendo `develop` como base de integração solicitada
- **Fonte primária:** `docs/legacy/vba/CalMetrosNaMaq.txt`, com análise do intervalo inclusivo, contadores de sábado, domingo e feriado, opções das pontas e reconstrução textual de feriados.
- **Fragilidades identificadas:** dupla ou tripla subtração da mesma data, possibilidade de total negativo, recorrência textual dependente de formato/localidade, dependência da UI e efeito cumulativo de duplicatas.
- **Decisões aprovadas:** classificação booleana única por data; pontas removidas antes da classificação; intervalo de uma data permitido; sábados, domingos e trabalho em feriados configuráveis; feriados explícitos em `Set<LocalDate>`, sem recorrência ou API externa no MVP; zero dias produtivos aceito pelo calendário.
- **Casos especificados:** critérios de aceitação A–M, incluindo colisões, pontas, data única, duplicata, intervalo invertido e intervalo sem dia produtivo.
- **Implementação:** nenhuma implementação, teste ou UI foi criada; o R2.2 não foi iniciado.
- **Proteção de escopo:** nenhuma alteração no `ProductionCapacityCalculator`, no motor R1, em Kotlin, Gradle, dependências ou fontes legadas.
- **Validação manual:** não aplicável; alteração exclusivamente documental.
- **Resultado:** contrato documental suficiente para iniciar a implementação do R2.2; R2 permanece em andamento.

## R2.2 — Implementação do calendário produtivo

- **Data:** 2026-10-05
- **Etapa:** R2.2
- **Branch:** branch interna `work`, tendo `develop` como base de integração solicitada
- **Arquitetura:** modelos de entrada e resultado e calculadora Kotlin pura em `br.com.prodtime.domain`, sem Android, Compose, UI, integração com R1 ou nova camada arquitetural.
- **Contrato:** intervalo, políticas das pontas, sábado, domingo e feriado em `ProductiveCalendarInput`; totais, listas cronológicas e contagens informativas em `ProductiveCalendarResult`.
- **Datas e feriados:** `LocalDate` de ponta a ponta e feriados explícitos em `Set<LocalDate>`, com deduplicação natural, sem parsing, recorrência ou API externa.
- **Pontas:** o início e o fim são filtrados antes da classificação; quando representam a mesma data, ela permanece somente se ambas as opções de inclusão forem verdadeiras.
- **Classificação:** cada data considerada recebe uma única decisão produtiva pela conjunção da permissão do dia da semana com a permissão de feriado; contagens informativas não são subtraídas do total.
- **Cobertura:** 21 testes do calendário cobrem os casos A–Q, incluindo os quatro subcasos de data única e uma verificação adicional das invariantes das listas; os 8 testes existentes do motor R1 também foram executados na validação focada.
- **Comandos/testes:** `./gradlew testDebugUnitTest` não iniciou porque o wrapper não possui permissão de execução; `bash ./gradlew testDebugUnitTest` não baixou o Gradle 8.13 porque o proxy retornou HTTP 403. Como validação equivalente focada, as fontes Kotlin puras e seus testes foram compilados diretamente com o compilador Kotlin 2.0.21 presente no cache e executados com JUnit 4.13.2 local.
- **Resultado:** a validação focada aprovou 29 testes JUnit, sendo 21 do calendário e 8 do motor R1. A tarefa Gradle completa permanece pendente de execução em ambiente local com wrapper executável e distribuição disponível.
- **Validação manual:** não aplicável ao núcleo Kotlin puro.
- **Limitações ambientais:** permissão ausente no `gradlew` e bloqueio HTTP 403 do proxy ao download da distribuição Gradle.
- **Proteção de escopo:** `ProductionCapacityCalculator` e seu teste permaneceram intactos; nenhuma UI, configuração Gradle, dependência, regra de prazo ou integração R1/R2 foi criada. R2 permanece em andamento e R3 não foi iniciado.

## R2.2V — Validação local do calendário

- **Ambiente:** Windows 11 amd64; Microsoft OpenJDK 17.0.20.1 LTS 64-bit; Gradle 8.13; Kotlin 2.0.21.
- **Comando:** `.\gradlew.bat testDebugUnitTest`
- **Resultado:** `BUILD SUCCESSFUL in 1m 11s`; `22 actionable tasks: 5 executed, 17 up-to-date`.
- **Git:** branch `develop` sincronizada com `origin/develop`, com working tree clean.
- **Evidência:** execução realizada no ambiente local real após o merge do R2.2; nenhuma alteração de código foi necessária para a validação.
- **Conclusão:** R2.2 está validado localmente. A limitação anterior do ambiente Cloud não representa falha do motor; a validação focada no Cloud já havia aprovado 29 testes, sendo 21 do calendário e 8 do motor R1.

## R2.3 — Modelo e resolução de feriados

- **Data:** 2026-10-05
- **Etapa:** R2.3A/B/C
- **Branch:** branch interna `work`, tendo `develop` como base de integração solicitada
- **Arquitetura:** `HolidayDefinition`, `AnnualHoliday`, `SpecificDateHoliday` e `HolidayResolver` em Kotlin puro, sem alteração do contrato ou da regra interna de `ProductiveCalendarCalculator`.
- **Contrato:** nomes não vazios; datas estruturadas por `MonthDay` ou `LocalDate`; intervalo inclusivo e invertido inválido; recorrência anual resolvida para todos os anos atravessados; 29/02 somente em anos bissextos; saída deduplicada como `Set<LocalDate>`.
- **Cobertura:** casos H1–H12, validação de nomes e teste direto `HolidayResolver → Set<LocalDate> → ProductiveCalendarCalculator`.
- **Comandos Cloud:** `./gradlew testDebugUnitTest` não iniciou porque o wrapper não possui permissão de execução; `bash ./gradlew testDebugUnitTest` tentou obter o Gradle 8.13, mas o proxy retornou `HTTP/1.1 403 Forbidden`.
- **Validação focada equivalente:** as fontes Kotlin puras foram compiladas diretamente com o compilador Kotlin 2.0.21 disponível no cache, e um executor determinístico validou H1–H12 e o fluxo `HolidayResolver → ProductiveCalendarCalculator`.
- **Resultado Cloud:** `OK: H1-H12 e integração Resolver -> Calendar`. A suíte criada contém 14 novos testes; somada aos 29 testes anteriores, a suíte Gradle passa a ter 43 testes, cuja execução completa aguarda ambiente local.
- **Validação manual:** não aplicável ao núcleo Kotlin puro; a suíte Gradle local permanece requerida para o R2.4.
- **Proteção de escopo:** sem UI, persistência, API externa, dependência, alteração do motor R1 ou início do R3.

## R2.4 — Validação local e encerramento do calendário

- **Ambiente:** Windows 11 amd64; Microsoft OpenJDK 17.0.20.1 LTS 64-bit; Gradle 8.13; Kotlin 2.0.21.
- **Comando:** `.\gradlew.bat testDebugUnitTest`
- **Resultado:** `BUILD SUCCESSFUL in 25s`; `22 actionable tasks: 5 executed, 17 up-to-date`.
- **Git:** branch `develop` sincronizada com `origin/develop`, com working tree clean.
- **Evidência:** o R2.3 foi validado pela suíte Gradle local real; nenhuma alteração foi necessária para obter a aprovação.
- **Gate final:** aprovado. O calendário usa `LocalDate`, rejeita intervalo invertido, aceita uma data, aplica pontas antes da classificação única, não faz subtrações cumulativas, configura sábados e domingos e recebe `Set<LocalDate>`. Feriados anuais usam `MonthDay`, específicos usam `LocalDate`, 29/02 respeita anos bissextos e duplicatas convergem para uma data. R2.2 e R2.3 possuem validação Gradle local e nenhuma questão aberta bloqueia o calendário fixo do MVP.
- **Conclusão:** o R2 atingiu seu critério de conclusão e está encerrado.

## R3.1–R3.3 — Contrato, integração e testes do Modo A

- **Data:** 2026-10-05.
- **Etapa:** R3.1, R3.2 e R3.3.
- **Arquitetura:** `ProductionEstimateInput`, `ProductionEstimateResult` e `ProductionEstimateCalculator` compõem diretamente `HolidayResolver`, `ProductiveCalendarCalculator` e `ProductionCapacityCalculator`, sem camada abstrata, Android ou duplicação de fórmula.
- **Cobertura:** 12 testes ponta a ponta E1–E12, incluindo regressões de três e duas fitas, políticas de feriado e pontas, zero dias produtivos, feriados anual/específico/deduplicado, intervalo invertido, horas fracionárias e rastreabilidade.
- **Comando Gradle Cloud:** `./gradlew testDebugUnitTest` não iniciou porque o wrapper não possui permissão de execução (`Permission denied`).
- **Validação focada equivalente:** todas as fontes e testes Kotlin puros de domínio foram compilados com Kotlin 2.0.21 disponível no cache e executados com JUnit 4.13.2.
- **Resultado focado:** `OK (55 tests)`, incluindo os 12 testes do fluxo e os 43 testes anteriores de capacidade, calendário e feriados.
- **Limitação:** a suíte Gradle do R3.3 aguarda validação local em ambiente apto a executar o wrapper. A limitação é ambiental e não foi mascarada.
- **Proteção de escopo:** motores existentes intactos; sem UI, persistência, dependência, meta no Modo A ou implementação do R4.

## Modelo para futuras validações

### Validação: título curto

- **Data:** AAAA-MM-DD
- **Etapa:** Rn
- **Branch:** nome da branch ou branch interna do ambiente
- **Commit:** hash ou “alterações ainda não commitadas”
- **Comandos/testes:** comandos exatos ou procedimento
- **Resultado:** aprovado, reprovado ou limitado, com evidência objetiva
- **Validação manual:** passos, dispositivo/ambiente e resultado, ou “não aplicável”
- **Riscos:** riscos residuais conhecidos
- **Observações:** contexto necessário para reprodução

## R3.4 — Validação local e encerramento do Modo A

- **Ambiente:** Windows 11 amd64; Microsoft OpenJDK 17.0.20.1 LTS 64-bit; Gradle 8.13; Kotlin 2.0.21.
- **Comando:** `.\gradlew.bat testDebugUnitTest`
- **Resultado:** `BUILD SUCCESSFUL in 24s`; `22 actionable tasks: 5 executed, 17 up-to-date`.
- **Git:** branch `develop` sincronizada com `origin/develop`, com working tree clean.
- **Gate:** aprovado. `HolidayResolver`, `ProductiveCalendarCalculator` e `ProductionCapacityCalculator` estão integrados sem fórmula duplicada; dias produtivos são derivados; o Modo A não recebe meta; zero dias retorna 0 m; regressões de três e duas fitas, feriados anuais e específicos e horas fracionárias estão cobertos.
- **Conclusão:** R3 aprovado e encerrado.

## R4.1–R4.3 — Contrato, cálculo e testes do Modo B

- **Data:** 2026-10-05.
- **Etapa:** R4.1, R4.2 e R4.3.
- **Arquitetura:** `ProductionDeadlineInput`, `ProductionDeadlineResult` e `ProductionDeadlineCalculator` compõem diretamente `HolidayResolver`, `ProductiveCalendarCalculator` e `ProductionCapacityCalculator`, sem fórmula ou regra de calendário paralela.
- **Cobertura:** 18 testes ponta a ponta D1–D18 para minimalidade da conclusão, calendários, feriados, arredondamento, metas e parâmetros inválidos, horizonte técnico, rastreabilidade e regressões de três e duas fitas.
- **Comando Gradle Cloud:** `./gradlew testDebugUnitTest` não iniciou porque o wrapper não possui permissão de execução (`Permission denied`); `bash ./gradlew testDebugUnitTest` tentou baixar o Gradle 8.13, mas o proxy retornou `HTTP/1.1 403 Forbidden`.
- **Validação focada equivalente:** todas as fontes e testes Kotlin puros do domínio foram compilados com Kotlin 2.0.21 disponível no cache e executados com JUnit 4.13.2.
- **Resultado focado:** `OK (73 tests)`, incluindo os 18 testes do prazo e os 55 testes anteriores.
- **Limitação:** a suíte Gradle do R4.3 aguarda validação local em ambiente apto a executar o wrapper. A limitação ambiental não foi mascarada.
- **Proteção de escopo:** motores existentes intactos; sem UI, persistência, dependência, recomendações ou implementação do R5.

## R4.4 — Validação local e encerramento do Modo B

- **Ambiente:** Windows 11 amd64; Microsoft OpenJDK 17.0.20.1 LTS 64-bit; Gradle 8.13; Kotlin 2.0.21.
- **Comando:** `.\gradlew.bat testDebugUnitTest`
- **Resultado:** `BUILD SUCCESSFUL in 46s`; `22 actionable tasks: 5 executed, 17 up-to-date`.
- **Git:** branch `develop` sincronizada com `origin/develop`, com working tree clean.
- **Gate:** aprovado. A meta é positiva e inteira; o Modo B não recebe data final; a conclusão é a primeira data suficiente; calendário, feriados e capacidade são reutilizados sem fórmula paralela; `HALF_EVEN` permanece no R1; o horizonte técnico impede loop infinito; D1–D18 estão cobertos.
- **Conclusão:** R4 validado e encerrado.

## R5.1–R5.3 — Especificação, implementação e testes de viabilidade

- **Data:** 2026-10-05.
- **Etapa:** R5.1, R5.2 e R5.3.
- **Branch:** branch interna `work`, tendo `develop` como base de integração solicitada.
- **Arquitetura:** `ProductionViabilityInput`, `ProductionViabilityResult` e `ProductionViabilityAdvisor` compõem os motores existentes de estimativa e capacidade, sem fórmula paralela, Android ou nova camada arquitetural.
- **Cobertura:** 18 testes V1–V18 para atendimento, diferença com sinal, mínimo e adicional de fitas, calendário e feriados, zero dias produtivos, entradas inválidas, minimalidade, horas fracionárias, limite técnico e rastreabilidade.
- **Comando Gradle Cloud:** `./gradlew testDebugUnitTest` não iniciou porque o wrapper não possui permissão de execução (`Permission denied`); `bash ./gradlew testDebugUnitTest` tentou baixar o Gradle 8.13, mas o proxy retornou `HTTP/1.1 403 Forbidden`.
- **Validação focada equivalente:** todas as fontes e testes Kotlin puros do domínio foram compilados diretamente com Kotlin 2.0.21 disponível no cache e executados com JUnit 4.13.2.
- **Resultado focado:** `OK (91 tests)`, incluindo os 18 testes de viabilidade e os 73 testes anteriores.
- **Limitação:** a suíte Gradle do R5.3 aguarda validação local em ambiente apto a executar o wrapper. A limitação ambiental não foi mascarada.
- **Proteção de escopo:** motores existentes intactos; sem UI, persistência, PCP, IA, dependência ou início do R6.

## R5.4 — Validação local e encerramento das recomendações

- **Ambiente:** Windows 11 amd64; Microsoft OpenJDK 17.0.20.1 LTS 64-bit; Gradle 8.13; Kotlin 2.0.21.
- **Comando:** `.\gradlew.bat testDebugUnitTest`
- **Resultado:** `BUILD SUCCESSFUL in 17s`; `22 actionable tasks: 22 up-to-date`.
- **Git:** `develop` sincronizada com `origin/develop`, com working tree clean.
- **Gate final:** aprovado. `ProductionViabilityAdvisor` integra os motores reais; a diferença preserva o sinal; o mínimo de fitas é determinístico por busca exponencial e binária; zero dias produtivos é tratado; `Int.MAX_VALUE` é somente guarda técnica; não existe fórmula produtiva paralela; V1–V18 estão cobertos.
- **Conclusão:** R5 aprovado e encerrado.

## R6.1–R6.4 — Núcleo da UX mobile

- **Data:** 2026-10-05.
- **Etapa:** R6.1, R6.2, R6.3 e R6.4.
- **Arquitetura:** navegação interna por estado Compose, Home e dois formulários roláveis; sem Navigation Compose, ViewModel, persistência ou dependência nova.
- **Integração:** os fluxos constroem os inputs e chamam diretamente `ProductionEstimateCalculator` e `ProductionDeadlineCalculator`; feriados são uma coleção vazia nesta etapa.
- **Cobertura nova:** parsing decimal com vírgula/ponto, inteiro inválido, formatação de metros e data em testes JVM.
- **Comandos Cloud:** `bash ./gradlew testDebugUnitTest assembleDebug` tentou baixar o Gradle 8.13, mas o proxy retornou `HTTP/1.1 403 Forbidden`; `gradle testDebugUnitTest assembleDebug`, com Gradle 8.14.4 instalado, não configurou as tarefas porque não existe Android SDK (`ANDROID_HOME`/`sdk.dir`) no ambiente.
- **Validação manual:** não executada; não há dispositivo ou emulador Android configurado neste ambiente.
- **Pendência:** executar `./gradlew testDebugUnitTest` e `./gradlew assembleDebug` em ambiente local com Android SDK e validar os fluxos em aparelho antes de encerrar o R6.

## R6.4V — Validação local e física dos fluxos mobile

- **Data:** 2026-10-05.
- **Ambiente local:** Windows 11 amd64; Microsoft OpenJDK 17.0.20.1 LTS 64-bit; Gradle 8.13; Kotlin 2.0.21.
- **Testes:** `.\gradlew.bat testDebugUnitTest` — `BUILD SUCCESSFUL in 1m 23s`; `22 actionable tasks: 7 executed, 15 up-to-date`.
- **Build Android:** `.\gradlew.bat assembleDebug` — `BUILD SUCCESSFUL in 19s`; `34 actionable tasks: 5 executed, 29 up-to-date`.
- **Git:** `develop` sincronizada com `origin/develop`; working tree clean.
- **Dispositivo:** Samsung SM-A066M; ADB `R9XYC04PKLH    device`; `adb install -r app-debug.apk` retornou `Success`; `br.com.prodtime/.MainActivity` iniciou com sucesso via ADB.
- **Modo A aprovado:** 01/10/2026 a 27/10/2026; 25 cm/min; 3 fitas; 16 h/dia; 3% de desperdício; sem sábados, domingos ou feriados. Resultado: 13.270 m líquidos, 19 dias produtivos, 13.680 m brutos e 410,4 m de desperdício.
- **Modo B aprovado funcionalmente:** meta de 10.000 m a partir de 05/10/2026; 25 cm/min; 3 fitas; 16 h/dia; 3% de desperdício; sem sábados, domingos ou feriados. Resultado: conclusão em 23/10/2026, 15 dias produtivos, produção de 10.476 m e saldo de +476 m.
- **Achado visual:** o rótulo “Produção estimada na conclusão” prejudicava o valor em tela estreita. Decisão aplicada: “Produção”; `SummaryRow` ajustado para reservar espaço flexível ao rótulo e manter o valor alinhado e íntegro.

## R6.5/R6.6A — Implementação no ambiente Cloud

- **Data:** 2026-10-05.
- **Escopo:** terceira jornada de viabilidade, estado compartilhado e gestão de feriados em memória, sem persistência.
- **Comandos Cloud:** `bash ./gradlew testDebugUnitTest assembleDebug` não obteve o Gradle 8.13 porque o proxy retornou `HTTP/1.1 403 Forbidden`; `gradle testDebugUnitTest assembleDebug`, com Gradle 8.14.4 instalado, falhou na configuração por ausência de Android SDK (`ANDROID_HOME`/`sdk.dir`).
- **Pendência:** executar `./gradlew testDebugUnitTest` e `./gradlew assembleDebug` em ambiente local com Android SDK e validar R6.5/R6.6A no aparelho.

## R6.5/R6.6A — Validação local após o hotfix

- **Data:** 2026-10-05.
- **Ambiente:** execução local real após o hotfix `fix: import TextButton in home screen` integrado à `develop`.
- **Testes:** `.\gradlew.bat testDebugUnitTest` — `BUILD SUCCESSFUL in 1m 11s`; `22 actionable tasks: 6 executed, 16 up-to-date`.
- **Build Android:** `.\gradlew.bat assembleDebug` — `BUILD SUCCESSFUL in 11s`; `34 actionable tasks: 4 executed, 30 up-to-date`.
- **Git:** `develop` sincronizada com `origin/develop`; working tree clean.
- **Conclusão:** R6.5 e R6.6A compilam e passam na suíte local.
- **Validação física:** pendente por indisponibilidade temporária do aparelho; R6.5 e R6.6A não foram visualmente validados.

## R6.6B/R6.7/R7 — Escopo, integração e regressões

- **Data:** 2026-10-05.
- **R6.6B:** persistência de feriados encerrada como evolução futura, fora do MVP acadêmico. O estado permanece em memória durante a sessão, sem banco, arquivo, repository ou dependência adicional.
- **R6.7:** revisados navegação e Back, estado compartilhado, contagem e CRUD de feriados, campos, unidades, defaults, validações, mensagens, resultados, formatação, zero dias produtivos e uso de tokens Material. A observação de sessão na tela de feriados foi alinhada ao texto aprovado; não houve refatoração ampla nem alteração do domínio.
- **R7:** rastreabilidade consolidada em `docs/testing/REGRESSION_MATRIX.md`; foi adicionada somente a lacuna de apresentação para inteiro positivo. Casos históricos, prazo integrado, viabilidade, calendário, feriados e integração compartilhada já tinham cobertura suficiente e não foram duplicados.
- **Validação física:** não executada nesta etapa; permanece reservada ao R6.8.
- **Comandos Cloud desta tarefa:** `./gradlew testDebugUnitTest` não iniciou porque o wrapper não possui permissão de execução (`Permission denied`). `bash ./gradlew testDebugUnitTest` tentou baixar o Gradle 8.13, mas o proxy retornou `HTTP/1.1 403 Forbidden`. `gradle testDebugUnitTest assembleDebug`, com Gradle 8.14.4 disponível, falhou na configuração porque o Android SDK não está configurado (`ANDROID_HOME` ou `sdk.dir`).
- **Limitação Cloud:** a suíte e o APK desta alteração precisam ser confirmados em ambiente com a distribuição do wrapper e Android SDK; a falha ambiental não foi tratada como aprovação.

## R7.4 — Validação local final e encerramento

- **Ambiente:** validação local real após a integração do PR de R7.
- **Testes:** `.\gradlew.bat testDebugUnitTest` — `BUILD SUCCESSFUL in 34s`; `22 actionable tasks: 5 executed, 17 up-to-date`.
- **Build Android:** `.\gradlew.bat assembleDebug` — `BUILD SUCCESSFUL in 7s`; `34 actionable tasks: 3 executed, 31 up-to-date`.
- **Git:** `develop` sincronizada com `origin/develop`; working tree clean.
- **Gate:** matriz de regressão existente; casos históricos de duas e três fitas, prazo integrado, viabilidade, calendário, feriados e apresentação mapeados; lacuna de parsing de inteiro positivo coberta; suíte local e APK debug aprovados.
- **Conclusão:** R7 concluído.
- **Validação física:** nenhuma nova validação física foi executada; a pendência de R6.8 permanece.

## R8.1–R8.4 — Revisão estática e automatizada

### VALIDADO AUTOMATICAMENTE

- **Data:** 2026-10-05.
- **Etapa:** R8.1, R8.2, R8.3 e R8.4.
- **Revisão estática:** auditados componentes clicáveis, textos e erros, layouts roláveis, campos, ações IME, conversão UTC do DatePicker, tokens de tema, cards de resultado e seleção do tipo de feriado.
- **Correções:** indicador decorativo da Home removido da árvore semântica; rótulo e estado de switches agrupados; seleção anual/específica exposta semanticamente; linhas de resumo flexibilizadas; ações principais passaram de altura fixa para altura mínima; IME Next/Done passou a mover ou liberar o foco; caracteres decorativos foram removidos do texto de “Voltar”.
- **Contratos preservados:** entrada decimal por vírgula ou ponto e conversão direta para `BigDecimal`; datas internas em `LocalDate`, conversão do DatePicker em UTC e apresentação `dd/MM/yyyy`; nenhuma regra de domínio foi alterada.
- **Temas:** telas usam `MaterialTheme.colorScheme` e `MaterialTheme.typography`; tema dinâmico permanece ativo no Android 12 ou superior e as paletas locais são usadas nas versões anteriores.
- **Checklist:** evidências e pendências consolidadas em `docs/ux/ACCESSIBILITY_CHECKLIST.md`.
- **Comandos Cloud:** `./gradlew testDebugUnitTest` não iniciou porque o wrapper não possui permissão de execução (`Permission denied`). `bash ./gradlew testDebugUnitTest` tentou baixar o Gradle 8.13, mas o proxy retornou `HTTP/1.1 403 Forbidden`. `gradle testDebugUnitTest assembleDebug`, com Gradle 8.14.4 disponível, falhou porque o Android SDK não está configurado (`ANDROID_HOME` ou `sdk.dir`).
- **Limitação Cloud:** testes e montagem desta alteração permanecem pendentes em ambiente com Gradle 8.13 e Android SDK; os resultados locais de R7.4 não são apresentados como validação do código de R8.
- **Resultado:** R8.1–R8.4 implementados e revisados; R8 permanece em andamento.

### PENDENTE DE VALIDAÇÃO FÍSICA

- **Etapas:** R6.8 e R8.5.
- **Dispositivo:** Samsung temporariamente indisponível nesta etapa.
- **Itens:** R6.5 e R6.6A em aparelho; teclado real; rolagem com teclado; fonte ampliada; tema claro; tema escuro; contraste visual; Back; touch targets; feriados; viabilidade; e descarte após encerramento do processo.
- **Conclusão:** nenhuma validação física nova é alegada; R6 e R8 permanecem em andamento, e R9 não foi iniciado.

## R6.8/R8.5 — validação física, rodada 1

- **Data:** 2026-10-06.
- **Dispositivo:** Samsung SM-A066M.
- **Home:** três fluxos principais funcionais; acesso à configuração de feriados funcional; layout e rolagem confirmados no tema escuro.
- **Modo A:** 01/10/2026 a 27/10/2026, 25 cm/min, 3 fitas, 16 h/dia, 3% de desperdício, sem sábado/domingo — 13.270 m líquidos, 19 dias produtivos, 13.680 m brutos e 410,4 m de desperdício; resultado correto.
- **Modo B:** meta de 10.000 m, início em 05/10/2026, 25 cm/min, 3 fitas, 16 h/dia e 3% de desperdício — conclusão em 23/10/2026, 15 dias produtivos, produção de 10.476 m e saldo de +476 m; resultado correto. O rótulo “Produção” corrigiu a quebra visual anterior.
- **Viabilidade atendida:** produção de 13.270 m, meta de 10.000 m, excedente de +3.270 m, mínimo de 3 fitas e 0 fitas adicionais; resultado correto.
- **Viabilidade não atendida:** com 2 fitas, produção de 8.846 m, meta de 10.000 m, déficit de 1.154 m, mínimo de 3 fitas e 1 fita adicional; resultado correto.
- **Feriados:** CRUD em memória funcional para Natal anual (25/12) e data específica; coleção compartilhada corretamente entre os fluxos.
- **Integração real do calendário:** 01/12/2026 a 31/12/2026, 28 cm/min, 1 fita, 16 h/dia e 3% de desperdício. Com feriado não trabalhado: 22 dias produtivos, 5.737 m líquidos, 5.914 m brutos e 177,42 m de desperdício. Com feriado trabalhado: 23 dias produtivos, 5.997 m líquidos e 6.182 m brutos. A política alterou efetivamente o cálculo.
- **Achados:** cursor do próximo campo iniciava antes do texto; necessidade de diferenciar visualmente meta atendida/não atendida; ação para copiar resultado; resumo de sábados, domingos e feriados efetivamente incluídos; remoção da contagem de feriados cadastrados das telas de cálculo.
- **Resultado:** validação física parcialmente aprovada; o gate final permanece aberto até nova rodada após os refinamentos.
- **Rollout:** R6 em andamento; R6.8 com rodada 1 validada e rodada final pendente; R7 concluído; R8 em andamento; R8.5 com rodada 1 validada e rodada final pendente; R9 não iniciado.
- **Validação Cloud dos refinamentos:** `./gradlew testDebugUnitTest` não iniciou porque o wrapper não possui permissão de execução; `bash ./gradlew testDebugUnitTest` não obteve o Gradle 8.13 porque o proxy retornou `HTTP/1.1 403 Forbidden`; `gradle testDebugUnitTest assembleDebug`, com Gradle 8.14.4 disponível, falhou na configuração pela ausência de Android SDK (`ANDROID_HOME` ou `sdk.dir`). A suíte e o APK permanecem pendentes de confirmação local antes da rodada física final.

## R6.8/R8.5 — validação física dos refinamentos anteriores

- **Dispositivo:** Samsung SM-A066M.
- **Aprovado:** cursor posicionado no fim ao avançar por Next; distinção visual e textual de meta atendida/não atendida; cópia de resultados; resumo de calendário; remoção de “Feriados cadastrados” das telas de cálculo; três fluxos, calendário, feriados, CRUD em memória e resultados históricos.
- **Novo achado:** texto colado em “Velocidade” deixou espaços/caracteres invisíveis residuais, mantendo a mensagem e o cálculo bloqueado mesmo após substituição visual do conteúdo.
- **Gate remanescente:** validar somente limites operacionais, colagem inválida, remoção de whitespace/invisíveis e limpeza individual de erro.

## R8.6 — robustez de inputs e limites operacionais

- **Data:** 2026-10-06.
- **Implementação:** sanitização pura e compartilhada antes do parsing e no `NumericField`; validadores compartilhados para meta, fitas, horas, velocidade e desperdício; limpeza do erro do campo editado.
- **Cobertura:** testes JVM para espaços, tab, quebra de linha, NBSP, narrow NBSP, zero-width space, ZWNJ, ZWJ, word joiner, BOM, vírgula decimal, preservação de texto inválido e fronteiras operacionais.
- **Proteção:** motores matemáticos, `HALF_EVEN`, calendário, `HolidayResolver` e regras de viabilidade não foram alterados.
- **Identidade:** nome ProdTime preservado; launcher de template confirmado; acabamento de ícone e eventual seção “Sobre” reservado ao R10.
- **Clipboard:** a BOM Compose declarada é `2024.09.00`; a migração para `LocalClipboard` introduziria uma chamada suspensa e não pôde ser compilada neste ambiente sem Android SDK. Para evitar risco no fluxo fisicamente aprovado, `LocalClipboardManager` foi mantido e seu warning de depreciação continua não bloqueador, sem `suppress`.
- **Cloud:** `./gradlew testDebugUnitTest` e `./gradlew assembleDebug` não iniciaram porque o wrapper não possui permissão de execução; as variantes com `bash ./gradlew` tentaram baixar o Gradle 8.13, mas o proxy retornou `HTTP/1.1 403 Forbidden`. A tentativa equivalente `gradle testDebugUnitTest assembleDebug` com o Gradle instalado falhou antes das tarefas porque não há Android SDK (`ANDROID_HOME`/`sdk.dir`).
- **Validação local/física:** pendente; R6 e R8 permanecem em andamento e R9 não foi iniciado.


## R6.8/R8.6 — validação local e física final

- **Ambiente local:** Windows; Gradle 8.13; Kotlin 2.0.21.
- **Dispositivo:** Samsung SM-A066M.
- **Testes:** `.\gradlew.bat testDebugUnitTest` — `BUILD SUCCESSFUL in 21s`; `22 actionable tasks: 6 executed, 16 up-to-date`.
- **Build Android:** `.\gradlew.bat assembleDebug` — `BUILD SUCCESSFUL in 6s`; `34 actionable tasks: 4 executed, 30 up-to-date`.
- **Git no início da validação:** `develop` sincronizada com `origin/develop`; working tree clean.
- **Validação física:** cursor via Next aprovado; cards de viabilidade atendida/não atendida aprovados; cópia aprovada; calendário no resultado aprovado; contagem “Feriados cadastrados” ausente dos formulários; três fluxos funcionais.
- **Limites aprovados:** velocidade `999,99` válida e `1000` inválida; meta `999.999.999` válida e `1.000.000.000` inválida; fitas `999.999` válida e `1.000.000` inválida; horas `24` válida e `24,01` inválida; desperdício `0` e `99,99` válidos e `100` inválido.
- **Robustez aprovada:** whitespace e caracteres invisíveis são sanitizados; texto inválido permanece rejeitado; a edição limpa somente o erro do campo corrigido; o cálculo volta a funcionar sem sair da tela e sem regressão perceptível de edição ou cursor.
- **Observação:** o warning de depreciação de `LocalClipboardManager` permanece não bloqueador; a cópia foi validada fisicamente e a migração foi evitada neste gate para não introduzir risco.
- **Conclusão:** R6 encerrado. R8 encerrado.

## R9.1 — validação bibliográfica e requisitos acadêmicos

- **Data:** 2026-10-06.
- **Referências:** metadados bibliográficos das quatro referências foram verificados externamente em páginas editoriais e considerados utilizáveis.
- **Limite bibliográfico:** o escopo temático está restrito aos metadados e resumos editoriais consultados; não se alega leitura integral nem validação direta do ProdTime pelos trabalhos.
- **Formato:** a existência do formato/modelo SBC foi confirmada; o template, por si só, não determina limite de páginas.
- **Pendência:** o número de páginas específico da entrega continua pendente de confirmação institucional e não bloqueia a redação inicial concisa.
- **Requisitos conhecidos:** artigo em PDF no padrão/modelo SBC, arquivo de até 5 MB, vídeo no YouTube com aproximadamente 5 minutos e prazo geral em 24/10/2026.
- **Estrutura:** estrutura acadêmica e planos de introdução, fundamentação, metodologia, resultados, figuras e tabelas aprovados para orientar R9.2.
- **Execução:** nenhuma tarefa Gradle foi necessária, pois a alteração é exclusivamente documental e acadêmica.
- **Conclusão:** R9.1 concluído; R9 permanece em andamento; R9.2, R9.3 e R9.4 permanecem pendentes.

## R9.2 — primeira versão completa do artigo

- **Data:** 2026-10-06.
- **Base:** `develop` no commit `4bc2bd30d0eb02fec8225330f90279e0b1b93986`; branch interna `work`. O checkout inicial estava no bootstrap `cac1966`; após confirmação de árvore limpa e ancestralidade, foi alinhado à base solicitada por fast-forward, sem troca de branch nem commit de merge.
- **Fontes:** leitura integral das fontes acadêmicas, de produto, domínio, arquitetura, regressões, rollout, validações, decisões e README solicitadas para a tarefa.
- **Entrega:** primeira versão integral em `docs/academic/ARTICLE_DRAFT.md`, com título, autoria por placeholders, Resumo, Palavras-chave, Abstract, Keywords, sete seções acadêmicas, duas tabelas, três marcadores de figuras e referências. Checklist da releitura inicial em `ARTICLE_DRAFT_REVIEW.md`.
- **Bibliografia:** somente as quatro referências verificadas em R9.1; usos centrais e complementares dentro dos limites dos metadados e resumos editoriais. Sem alegação de leitura integral, citação direta ou validação do ProdTime pela literatura.
- **Resultados:** regressões, prazo, viabilidade e feriados conferidos contra `ARTICLE_EVIDENCE.md` e matriz de regressão. Nenhuma nova métrica criada; não há alegação de tempo economizado, redução de desperdício ou cobertura percentual.
- **Testes:** contagem estática de 118 métodos anotados com `@Test` em `app/src/test`, separada dos resultados históricos `BUILD SUCCESSFUL` de `testDebugUnitTest` e `assembleDebug` registrados em R6.8/R8.6. Não houve nova execução Gradle nem nova validação física nesta tarefa documental.
- **Releitura:** verificados estrutura, coerência técnica, equivalência Resumo/Abstract, termos sensíveis, alegações futuras e atribuição das referências. Essa releitura de redação não inicia nem substitui R9.3.
- **Verificações documentais:** `git diff --check`, `git diff --stat`, `git diff --name-only`, `git status` e buscas solicitadas no artigo; apenas quatro arquivos documentais compõem a alteração em relação à base `develop`.
- **Pendências:** placeholders institucionais preservados; figuras reais ainda pendentes; número de páginas ainda pendente de confirmação institucional; revisão R9.3 necessária antes da versão final e formatação R9.4 posterior.
- **Proteção de escopo:** nenhum código Android, teste, domínio, Gradle ou README alterado pelo commit; sem PDF, DOCX, logo, vídeo ou release.
- **Conclusão:** R9.2 concluído e primeira versão pronta para revisão. R0–R8 concluídos; R9 em andamento; R9.0 e R9.1 concluídos; R9.3 e R9.4 pendentes; R10 e R11 não iniciados.

## R9.3 — revisão técnica/acadêmica integral

- **Data:** 2026-10-06.
- **Base confirmada:** `develop`, commit `0618d76b432e77808edd9f609a0ae08ffb5f6a23`, merge do PR #24 de R9.2. Branch interna `work` limpa no início, alinhada por fast-forward sem troca de branch.
- **Validação local informada pelo usuário após R9.2:** `.\gradlew.bat testDebugUnitTest` — `BUILD SUCCESSFUL in 44s`, `22 actionable tasks: 22 up-to-date`; `.\gradlew.bat assembleDebug` — `BUILD SUCCESSFUL in 5s`, `34 actionable tasks: 34 up-to-date`; `develop` sincronizada e working tree clean. Esses resultados não foram produzidos em R9.3 e não demonstram reexecução de todos os métodos.
- **Revisão:** leitura integral das fontes solicitadas e do artigo; consultas pontuais ao código para prazo, capacidade, viabilidade e limites de entrada.
- **Correção técnica:** removida a ambiguidade “A data final participa do cálculo”. O prazo não recebe data final; recebe meta/início/condições/calendário, busca progressivamente a primeira data suficiente e retorna a conclusão. Fórmulas, arredondamento e comportamento preservados.
- **Meta-linguagem:** nota interna, códigos Rn, rollout, gate e nomes de arquivos de evidência removidos do artigo, mantendo a rastreabilidade nos documentos de revisão/processo.
- **Resultados:** valores confrontados com as evidências; aritmética decimal e dias conferidos como auditoria documental; contagem estática de 118 métodos `@Test` separada das execuções históricas. Nenhuma métrica criada.
- **Bibliografia:** quatro referências e citações conferidas; entradas ordenadas alfabeticamente com metadados preservados; usos limitados aos resumos e metadados editoriais, sem validação direta do aplicativo.
- **Texto:** introdução e contribuição delimitadas; fundamentação sintetizada; metodologia sem narrativa interna; repetição com Tabela 2 reduzida; modelo do aparelho mencionado uma vez; Resumo/Abstract harmonizados e palavras-chave revistas.
- **Auditoria:** `docs/academic/ARTICLE_CONTENT_AUDIT.md` criada; revisão R9.3 registrada em `ARTICLE_DRAFT_REVIEW.md`. Divergência contextual de limite de fitas entre `OPEN_QUESTIONS.md` e código registrada na auditoria, sem reproduzi-la no artigo nem alterar domínio.
- **Validação documental:** `git diff --check`, `git diff --stat`, `git diff --name-only`, `git status`, buscas solicitadas, integridade de referências, estrutura/figuras/placeholders e escopo dos cinco arquivos conferidos.
- **Execução:** nenhuma nova execução Gradle nem nova rodada física nesta tarefa; nenhum Kotlin, teste, UI, recurso Android, Gradle, dependência ou regra de domínio alterado.
- **Pendências:** dados institucionais, imagens reais, número de páginas, template SBC, formatação e PDF em R9.4; essa etapa é necessária antes da entrega e não foi iniciada.
- **Integração:** a API do GitHub retornou HTTP 403; essa limitação é distinta do gate de conteúdo e não confirma criação de PR, ausência de conflitos no GitHub ou merge. Não será feita integração direta em `develop` para substituir o fluxo solicitado.
- **Conclusão:** revisão de conteúdo R9.3 concluída; conteúdo pronto para R9.4. R0–R8 e R9.0–R9.2 concluídos; R9 em andamento; R9.4 pendente; R10 e R11 não iniciados.

## R9.4 — consolidação textual e preparação para submissão

- **Data:** 2026-10-06.
- **Base confirmada:** `develop` em `74ddfbda862c429877d8e641f301193046780786`, merge do PR #25 de R9.3. Branch interna `work`, limpa no início e alinhada por fast-forward, sem checkout manual.
- **Fontes:** revisados integralmente os nove documentos acadêmicos/de processo solicitados e as instruções `AGENTS.md`.
- **Autoria fornecida pelo usuário:** Josué Paulo Alexandrina; Gran Faculdade; Análise e Desenvolvimento de Sistemas (ADS); josue_jpaej@hotmail.com. Placeholders substituídos no rascunho e na nova fonte `ARTICLE_FINAL.md`, mantendo os registros históricos anteriores.
- **Consolidação:** título aprovado, Resumo/Abstract, palavras-chave, sete seções, duas tabelas, cálculos, resultados e quatro referências preservados. Na fonte consolidada foram preparadas chamadas, posições e legendas curtas para quatro figuras, conforme solicitado; o corpo permanece sem meta-linguagem interna.
- **Figuras:** seleção temática definida para tela inicial, produção de 13.270 m, prazo de 23/10/2026 e viabilidade com déficit de 1.154 m. Nenhuma captura real localizada no repositório ou anexos acessíveis; somente os anexos textuais e os ícones do app estavam disponíveis. Integração e seleção visual dos melhores arquivos não concluídas. Reenvio solicitado ao usuário; nenhum screenshot gerado ou alterado.
- **Preparação:** `ARTICLE_SUBMISSION.md` registra insumos, critérios de conferência, checklist e gate atual. Feriados e VBA permanecem opcionais, sem inclusão nesta versão.
- **Ferramentas:** `pandoc`, `pdflatex`, `xelatex` e `lualatex` disponíveis; `kpsewhich sbc-template.sty` e `kpsewhich sbc-template.cls` não localizaram o modelo. Não há template institucional nos arquivos acessíveis. PDF/Word não gerados por ausência de imagens reais e template aplicável; não por indisponibilidade de compiladores. Limite de páginas continua sem confirmação.
- **Validação documental:** `git diff --check`, `git diff --stat`, `git diff --name-only` e `git status`; conferência de autoria, ausência de placeholders institucionais e meta-linguagem, estrutura, numeração/chamadas das quatro figuras e preservação das quatro referências. Comparação automatizada confirmou Resumo/Abstract, fórmulas e seção de resultados em diante idênticos à base R9.3; leitura estrutural pelo Pandoc confirmou duas tabelas e cabeçalhos. Isso não equivale à aplicação do template ou revisão de um PDF.
- **Escopo:** nove arquivos exclusivamente em `docs/academic` e `docs/process`; nenhum Android, Kotlin, domínio, teste, Gradle, recurso ou dependência alterado. Nenhuma nova execução Gradle ou rodada física; nenhuma métrica, experimento ou referência adicionada.
- **Integração:** consulta à API do GitHub retornou `Forbidden`; criação de PR depende de acesso à API. Não se afirma PR ou merge sem confirmação. O merge anterior de R9.3 foi confirmado por Git, não atribuído à execução desta tarefa.
- **Conclusão:** base textual com autoria e preparação documental concluídas; **R9.4 em andamento, gate NÃO**, com integração das quatro capturas, confirmação/aplicação do template, ajuste de páginas e PDF final pendentes. R0–R8 e R9.0–R9.3 concluídos; R9 em andamento; R10/R11 não iniciados.

## R9.4B — figuras reais e preview SBC

- **Data:** 2026-10-06. Base `develop` em `87dc982ce2dd2de1767632940b6150c76846b124`, PR #26 integrado; branch interna `work`, árvore limpa no início.
- **Entrada:** ZIP fornecido pelo usuário com quatro JPGs e `README_FIGURAS.md`. Extração controlada, inspeção visual das quatro capturas e cópia para `docs/academic/figures/` com nomes e bytes preservados. Nenhuma geração, recorte ou edição visual.
- **Artigo:** apenas quatro placeholders substituídos por links reais em `ARTICLE_FINAL.md`; comparação confirmou todas as legendas e conteúdo textual restante inalterados. Autoria, fórmulas, resultados, limites bibliográficos e quatro referências preservados.
- **Template:** download direto do site SBC retornou HTTP 403. Obtida cópia tradicional de `https://github.com/uefs/sbc-template-latex`, commit `0748264951381dfed3e3fea6a39287a64098fb82`; `sbc-template.sty` e dependência `caption2.sty` copiados com conteúdo funcional preservado; somente finais de linha e espaços finais em comentários foram normalizados. Margens, fontes e espaçamentos originais mantidos. Proveniência e reprodução em `submission/README.md`.
- **Fonte:** `docs/academic/submission/main.tex`, com bibliografia manual de quatro entradas, tabelas `tabularx`, fórmulas transcritas e imagens centralizadas a 0,40 da largura de texto, com proporção preservada. Não foi necessário `references.bib` para essa fonte.
- **Compilação:** tentativa inicial interrompida por ausência de `brazil.ldf`; resolvida pela importação do locale português pelo Babel disponível, sem alterar o estilo. Tentativa exploratória LuaLaTeX não foi utilizada no artefato entregue devido a glifos; a saída final usa pdfLaTeX e não apresenta glifos ausentes. Numeração de seções e apresentação bibliográfica conferidas/corrigidas na adaptação LaTeX. Compilação final reproduzida com `sh docs/academic/submission/build.sh`, duas passagens pdfLaTeX em diretório temporário.
- **PDF:** `prodtime_sbc_preview.pdf`; `pdfinfo` confirmou 11 páginas A4, 357.298 bytes (357,298 KB / 0,357298 MB decimais), abaixo de 5 MB. Preview para avaliação, não versão institucional definitiva.
- **Inspeção:** todas as onze páginas renderizadas com `pdftoppm` e conferidas visualmente: título/autoria, resumo/abstract, fórmulas, duas tabelas, quatro imagens/legendas e referências presentes. Sem cortes novos, deformação, imagens fora dos limites ou sobreposição. Capturas originais de formulários rolados preservadas integralmente. Extração por `pdftotext` confirmou números/autoria, quatro legendas de figuras e duas de tabelas.
- **Warnings:** final sem `Overfull`, glifos ausentes ou referências indefinidas. Nove `Underfull hbox` e um `Underfull vbox` (badness 1237) de espaçamento, inspecionados sem perda de conteúdo; não impedem revisão humana. Nenhum ajuste artificial de margens/fontes para forçar extensão.
- **Pendências:** revisão humana, limite de páginas e modelo institucional; Resumo/Abstract excedem a orientação de dez linhas do exemplo SBC, permanecendo completos na primeira página. Texto não reduzido nesta etapa; eventual adequação editorial e legibilidade em impressão dependem de revisão.
- **Validação documental:** comparação de artigo e integridade das imagens/estilos; compilação reproduzida; `git diff --check`, diff/stat/name-only e status conferidos. Escopo exclusivamente acadêmico/processo. Nenhuma alteração Android/Kotlin/domínio/testes/Gradle/dependências; nenhum Gradle ou nova rodada física executado.
- **Conclusão:** gate do preview **SIM**, pronto para revisão humana. R9.4 e R9 continuam em andamento; R0–R8/R9.0–R9.3 concluídos; R10/R11 não iniciados. Criação de PR e merge dependem de confirmação própria.

## R9.4C — correções finais e encerramento editorial

- **Data:** 2026-10-06. Base `develop` em `e1c19edf2e0bfb8aff84cab371617a931f16ffb0`, merge do PR #27; branch interna `work`, alinhada por fast-forward com árvore limpa e sem checkout manual.
- **Revisão humana informada:** autor revisou R9.4B página por página e aprovou estrutura, figuras/posicionamento, Tabela 2, fórmulas, referências, autoria e resultados. Os ajustes solicitados foram unidade de Desperdício e condensação Resumo/Abstract.
- **Tabela 1:** Markdown já continha `%`; a conversão anterior deixou a célula vazia em LaTeX. Corrigida para `\%` em `main.tex`, sem outra alteração na tabela. Inspeção do PDF confirmou a unidade.
- **Resumos:** Resumo com 97 palavras; Abstract semântico correspondente com 94; dez linhas cada na primeira página. Mantidos contexto manual/VBA, problema, solução Android/Kotlin/Compose, produção/prazo/viabilidade, desenvolvimento aplicado, validação automatizada/física, 8.846 m e 13.270 m, contribuição e limitação principal. Keywords preservadas; sem fatos novos.
- **Compilação:** `sh docs/academic/submission/build.sh`, duas passagens pdfLaTeX. `pdfinfo`: 11 páginas A4 antes/depois; 355.967 bytes (355,967 KB / 0,355967 MB decimais), abaixo de 5 MB; antes 357.298 bytes. Última passagem sem `Overfull`, glifos ausentes, referências indefinidas ou avisos de recompilação. Oito `Underfull hbox` de justificação; nenhum `Underfull vbox`.
- **Inspeção visual:** primeira página/autoria/instituição/resumos/termos e página 5/Tabela 1 conferidas na saída final; demais páginas também inspecionadas. Sem páginas vazias, cortes ou texto sobreposto. Refluxo natural após condensação: Introdução começa na primeira página e Figura 1 ocupa página de float (página 4); nenhum parâmetro de figura ou template alterado, nem paginação forçada. Total permanece 11 páginas.
- **Validação documental:** comparação confirmou alterações de conteúdo exclusivamente nos resumos e célula da Tabela 1. Restante do Markdown/LaTeX, imagens, legendas, referências, fórmulas e estilos intactos. `git diff --check`, diff/stat/name-only e status conferidos; sete arquivos acadêmicos/de processo, incluindo README de compilação atualizado para refletir o fechamento. Nenhum código/teste/UI/Gradle/dependência/recurso alterado; nenhum Gradle ou nova rodada física executado.
- **Conclusão:** gate editorial SIM; R9.4 concluído editorialmente e R9 concluído quanto ao conteúdo/artigo produzido. Limite institucional ainda não confirmado: eventual adequação futura depende da regra da Gran. R10/R11 não iniciados. PR/merge não são declarados sem confirmação própria.

## R10.1 — identidade do aplicativo

- **Data:** 2026-10-06. Base `develop` em `1f1ef92f506d053e4abf03db15ce8cab4a850503`, PR #28 de R9.4C integrado. Branch interna `work` limpa no início, alinhada por fast-forward sem checkout manual.
- **Skills:** `.agents/skills/prodtime-android/SKILL.md` e `.agents/skills/prodtime-testing/SKILL.md` lidas/aplicadas; implementação segue estado de navegação Compose e dependências existentes.
- **Ícone:** substituídos os componentes de template por vetor original de relógio/fita, branco sobre `#6650A4` da paleta local. Adaptativos por API em `mipmap-anydpi-v26`; variantes `v33` com silhueta monocromática. Dez rasters por densidade substituídos, incluindo versões redondas. SVG/PNG documental apresenta máscaras sem simular screenshot de aparelho. Prévia gráfica inspecionada visualmente, sem corte do símbolo central.
- **Metadados:** `app_name = ProdTime`; description de string compartilhada na Home/Sobre e referenciada pelo manifest. `applicationId`/namespace/package preservados, API mínima 26/target 36 e dependências intactas. `versionName = "1.0"`, `versionCode = 1`; versão 1.0.0 reservada ao R11. `aapt dump badging` do APK confirmou nome, package, versões, SDK e recursos de launcher empacotados.
- **Sobre:** acesso abaixo de feriados, `Destination.About`, Back existente e botão “Voltar”. Conteúdo real fornecido: autor, curso/Gran, e-mail, descrição oficial e aviso de estimativa. Cores/tipografia Material, coluna rolável, título com semântica heading, TextButton Material e contato selecionável. Previews claro/escuro/fonte ampliada declarados e compilados, sem alegar renderização Android executada.
- **Comandos diretos:** `./gradlew testDebugUnitTest` e `./gradlew assembleDebug` não iniciaram por `Permission denied`, pois o wrapper não tem bit executável. Não foi necessário alterar suas permissões.
- **Comando funcional:** após carregar `/workspace/cloud-setup/android-env.sh`, `bash ./gradlew testDebugUnitTest assembleDebug assembleDebugAndroidTest` — `BUILD SUCCESSFUL in 34s`; `65 actionable tasks: 41 executed, 24 up-to-date`. JDK 21/SDK 36 e Gradle do wrapper já disponíveis no Cloud.
- **Conferência após atualização final de recursos/manifest:** o mesmo comando passou em 1s, `65 actionable tasks: 10 executed, 55 up-to-date`. Essa segunda passagem confirma o APK com os recursos finais; testes JVM não foram apresentados como novamente executados quando atualizados.
- **Conferência final da fonte:** após organização dos imports, o mesmo comando passou em 2s, `65 actionable tasks: 1 executed, 64 up-to-date`; sem alegar nova execução dos testes atualizados.
- **Resultados de testes:** nove relatórios XML da primeira execução registram 118 testes JVM, zero falhas/erros/ignorados. Nenhum teste de domínio foi alterado. Warning existente de `LocalClipboardManager` permanece; o código de clipboard não foi modificado.
- **Teste instrumentado proporcional:** novo `ProdTimeIdentityTest` cobre navegação até Sobre, conteúdo essencial/rolagem, retorno por “Voltar” e Back e preservação dos acessos da Home. `assembleDebugAndroidTest` compilou o teste e gerou seu APK; **teste não executado** em dispositivo. Não foram criados testes estáticos artificiais de strings/XML.
- **Limitação visual real:** não há emulador/system image configurados; `adb devices -l` não conseguiu inicializar, com `Cannot mkdir '/home/agent/.android': Read-only file system`, inclusive com a variável de diretório Android configurada. Não houve execução física, validação TalkBack nem renderização da Home/Sobre em Android. Checklist local/físico registrado em `ACCESSIBILITY_CHECKLIST.md`; não se afirma ausência de regressão visual sem essa execução.
- **Documentação:** README atualizado somente para identidade/status; UX e acessibilidade atualizados, `docs/branding/BRAND_IDENTITY.md` e recursos vetoriais de prévia criados. R9/artigo e screenshots acadêmicos preservados; sem início do README final R10.2.
- **Escopo/verificações:** `git diff --check`, diff/stat/name-only e status; XML/rasters/recursos de ícone e APK conferidos. Motores, calendários, parâmetros, parsing, limites, feriados, clipboard, telas de cálculo, testes JVM, Gradle e dependências intactos.
- **Conclusão:** identidade implementada e pronta para validação local/física. R10 iniciado e em andamento; R10.1 validado por suíte/build, com inspeção Android real pendente. R10.2/R10.3/R10.4/R11 não iniciados. PR/merge dependem de confirmação própria.

## R10.2 — README final e consolidação da validação da identidade

- **Data do registro:** 2026-10-06.
- **Base confirmada:** `develop` em `d671d4de90882d35ac20c48f1712a7b8d960354b`, merge do PR #29 de R10.1; branch interna `work`, inicialmente limpa e alinhada por fast-forward sem checkout manual.
- **Fontes:** README, escopo, regras de negócio, arquitetura, matriz de regressão, identidade, artigo final, rollout e histórico de validações lidos; código e configuração apenas consultados para confirmar componentes, navegação, parsing, arredondamento, SDK/package/versão e wrapper.
- **Validação local/física informada pelo usuário nas instruções de R10.2:** identidade validada; novo ícone aprovado no Samsung; Home aprovada; Sobre aprovada em dark/light; fonte ampliada, Back, rolagem e dados acadêmicos aprovados. É relato do autor, não nova execução pelo Cloud; não foram fornecidos comandos, horários ou novos screenshots dessa rodada. O histórico anterior que registrava pendência foi preservado. A execução do teste instrumentado continua sem evidência registrada.
- **README final:** 14 seções com visão geral, funcionalidades, capturas, stack real, seis componentes de domínio, resumo das duas fronteiras `HALF_EVEN`, testes/validações, quatro casos de referência com premissas, execução Windows/Bash/ADB, árvore resumida, limitações, visão futura, Projeto Integrador e autoria real.
- **Imagens:** quatro JPGs reais em tabela 2 × 2, largura de 240 px e texto alternativo; nota explícita de captura anterior à identidade de R10.1. Prévia gráfica documental de launcher com largura de 480 px, identificada como prévia, sem alegação de screenshot real. Comparação de bytes confirmou as cinco imagens intactas em relação à base.
- **Links:** 15 destinos relativos do README conferidos, incluindo links Markdown e `src` HTML; todos existem com caminhos/capitalização correspondentes a arquivos rastreados. Conferência local de destinos e estrutura HTML, sem alegar inspeção da renderização publicada no GitHub. E-mail usa `mailto` e não é caminho local.
- **Testes:** contagem estática reconfirmou 118 métodos `@Test` em `app/src/test`; não equivale à execução da suíte. O README distingue contagem, resultados históricos, tarefas atualizadas e aprovação física informada. **Nenhuma tarefa Gradle executada em R10.2**, conforme o escopo documental.
- **Verificações:** `git diff --check`, `git diff --stat`, `git diff --name-only` e `git status`; releitura do diff e confirmação de escopo restrito a README e dois documentos de processo. Nenhum Kotlin, UI, ícone, domínio, teste, Gradle, dependência, screenshot ou artigo SBC alterado.
- **Integração:** consulta de PRs via `gh pr list` bloqueada com `Post "https://api.github.com/graphql": Forbidden`; criação de PR e merge dependem de acesso à API e não são presumidos. Publicação da branch por Git é distinta de criação de PR.
- **Conclusão:** README final pronto; gate documental **SIM**. R10.1/R10.2 concluídos, R10 em andamento, R10.3/R10.4 pendentes (não iniciados), R11 não iniciado. Pendência institucional de limite de páginas do artigo somente referenciada; artigo não reaberto.

## R10.3 — preparação do roteiro do vídeo acadêmico

- **Data do registro:** 2026-10-07 (UTC).
- **Base confirmada:** `develop` em `219f05b5180751693209c30c1a9576ac56f30993`, merge do PR #30 de R10.2. Branch interna `work` limpa no início e alinhada por fast-forward, sem checkout manual.
- **Fontes:** README, artigo final, preparação da submissão, escopo, identidade, rollout, validações e matriz de regressão consultados. Históricos preservados; estados vigentes de R9.4C/R10.2 usados em vez das pendências antigas. Código somente consultado para confirmar defaults, rótulos de ações, políticas e localização do switch de trabalho em feriados.
- **Entregas:** `docs/video/VIDEO_SCRIPT.md`, `SHOT_LIST.md`, `NARRATION.md` e `RECORDING_CHECKLIST.md`. Dez blocos com tela/ação, fala e observações; lista de tomadas com dados necessários; narração contínua; checklist antes/durante/depois e pendências externas.
- **Cenários:** produção de 13.270 m/19 dias/13.680 m brutos/410,4 m desperdício; prazo em 23/10/2026/15 dias/10.476 m/saldo +476 m; viabilidade de 8.846 m/déficit 1.154 m/mínimo 3/adicional 1. Premissas completas mantidas e distintas para produção/viabilidade (01/10–27/10/2026) e prazo (início 05/10/2026). Feriados apresentados depois dos cálculos para não interferir nos exemplos.
- **Calendário:** gestão anual/data específica e switch nos formulários previstos. Comparação histórica de dezembro ficou apenas como referência opcional na lista de tomadas, com parâmetros completos; não é nova demonstração ou nova validação.
- **Duração:** 655 palavras na narração, por separação de espaços e excluindo o título; números por extenso. Estimativa a 120/130/135/140 palavras por minuto: 5min27,5s / 5min02,3s / 4min51,1s / 4min40,7s, respectivamente. Dez blocos contíguos totalizam 300 segundos. A estimativa não inclui automaticamente pausas/ações; ensaio cronometrado e edição de preenchimento/transições necessários para confirmar 4min30s–5min30s. Não foi realizado ensaio nem produzido áudio/vídeo nesta tarefa.
- **Coerência documental:** fala do roteiro idêntica à narração; tempos da lista de tomadas sincronizados; 20 links relativos válidos. Autoria/curso/Gran corretos, sem necessidade de e-mail no vídeo. Mantidas distinções de contagem `@Test`/execução histórica, limitações e recursos futuros; sem métricas ou resultados inventados.
- **Verificações:** releitura dos quatro documentos; `git diff --check`, `git diff --stat`, `git diff --name-only` e `git status`; conferência do escopo de seis arquivos exclusivamente documentais. README, artigo, imagens, app/Kotlin/UI/domínio, testes, Gradle e dependências intactos. Nenhum Gradle ou nova validação física executado.
- **Integração:** consulta de PRs via GitHub CLI retornou `Post "https://api.github.com/graphql": Forbidden`. Publicação de branch por Git não equivale a PR ou merge; esses resultados dependem de confirmação própria.
- **Pendências externas do autor:** ensaio, gravação vertical com identidade atual, captura de áudio, edição, revisão, publicação no YouTube e registro/conferência do link e duração real. Checklist sem itens marcados como executados.
- **Conclusão:** gate do roteiro **SIM**, pronto para ensaio e gravação. R10.3 concluído quanto à preparação documental; vídeo não gravado/publicado nesta tarefa. R10 em andamento; R10.4 e R11 não iniciados.

## R10.4 — preparação dos materiais de portfólio e LinkedIn

- **Data do registro:** 2026-10-06 (America/Sao_Paulo).
- **Base confirmada:** `develop` em `ea118b619bd9d5354daf4cb4cb9b6c70ddfbd80b`, merge do PR #31 de R10.3. Branch interna `work`, limpa no início e alinhada por fast-forward, sem checkout manual.
- **Fontes:** README, artigo final, identidade, narração do vídeo, rollout, validações, escopo, arquitetura e matriz de regressão consultados. Código/configuração apenas lidos para confirmar busca exponencial/binária e metadados Android. R10.3 integrado; ensaio, gravação e publicação do vídeo continuam sem registro de conclusão.
- **Entregas:** seis documentos em `docs/portfolio/`: post LinkedIn, descrição completa do projeto, três versões para currículo, três pitches de entrevista, 16 destaques técnicos e ficha factual. Primeira pessoa usada como material para o autor; autoria do problema/calculadora/projeto e assistência de ferramentas de desenvolvimento na implementação/revisão distinguidas, sem alegação de escrita manual exclusiva.
- **LinkedIn:** corpo com 1.635 caracteres, contando espaços/quebras e excluindo título Markdown; três hashtags. Sem link de acesso, informações empresariais, clientes ou código proprietário. A consulta de visibilidade via `gh repo view` retornou `Post "https://api.github.com/graphql": Forbidden`; não se afirma repositório público nem privado sem confirmação.
- **Pitches:** aproximadamente 58, 126 e 261 palavras nas versões de 30 s, 60 s e 2 min. A 120–140 palavras/minuto, fala isolada estimada em 24,9–29,0 s; 54,0–63,0 s; 111,9–130,5 s. Tempos não representam ensaio executado; pausas/pronúncia precisam ser ajustadas pelo autor.
- **Ficha factual:** autoria/curso/Gran, Android, package, API mínima 26/target e compile 36, versão 1.0/código 1, stack, funcionalidades e estado conferidos. Contagem estática reconfirmou 118 métodos @Test JVM; distinta dos relatórios históricos e sem cobertura percentual inferida. Validação física no Samsung SM-A066M e aprovação da identidade atribuída ao relato do autor. Execução instrumentada não presumida.
- **Resultados:** 8.846 m, 13.270 m, 23/10/2026, 10.476 m, déficit 1.154 m e 118 métodos consistentes com artigo, README e matriz. Premissas comuns e datas distintas de produção/viabilidade e prazo preservadas nas tabelas. `pdfinfo` confirmou artigo existente com 11 páginas; tamanho 355.967 bytes, abaixo de 5 MB, sem recompilação. Limite institucional de páginas continua não informado.
- **Alegações sensíveis:** busca/revisão de “100%”, “cobertura”, “IA”, “inteligência artificial”, “PCP”, “ERP”, “produtividade”, “economia” e “redução”. Ocorrências tratam limites reais de entrada, perguntas com respostas negativas, ausência de métricas ou possibilidades futuras. Nenhuma alegação de ganho industrial, usuários, validação universal ou funcionalidades futuras entregues.
- **Imagens/links:** 41 destinos relativos Markdown/HTML conferidos. Referências aos arquivos existentes sem duplicação ou alteração de imagens. Capturas identificadas como anteriores à identidade de R10.1 e prévia de launcher identificada como gráfica.
- **Verificações:** revisão do diff; `git diff --check`, `git diff --stat`, `git diff --name-only` e `git status`; escopo de oito arquivos exclusivamente documentais. README, artigo, branding, imagens, documentos de vídeo, Kotlin/app/UI/domínio, testes, Gradle e dependências preservados. Nenhum Gradle ou nova validação física executado.
- **Integração/publicação:** API do GitHub bloqueada na consulta; PR/merge dependem de confirmação própria. Textos apenas preparados, sem publicação automática no LinkedIn, página de portfólio ou YouTube. Publicação de branch por Git é distinta dessas ações.
- **Conclusão:** gate dos materiais **SIM**, prontos para reutilização. R10.4 concluído quanto à preparação documental. R10 em andamento enquanto a gravação/publicação externa do vídeo não estiver registrada. R11 não iniciado; nenhuma release criada.

## R10.5 — vídeo publicado e encerramento de R10

- **Data do registro:** 2026-10-07 (America/Sao_Paulo).
- **Base:** develop em `4b4cc508040896a0f9053934528edebde7d8fc58`, merge do PR #32; árvore inicialmente limpa, alinhada por fast-forward na branch interna work.
- **Evidência fornecida pelo autor:** vídeo gravado, revisado pelo autor e publicado no YouTube; duração aproximada 5min48s; visibilidade Não listado; URL oficial https://youtu.be/UrrcNwLq76w.
- **Requisito institucional relatado:** plataforma da Gran Faculdade solicita criação de vídeo de 5 minutos, postagem no YouTube e permite explicitamente modo não listado. A tela descrita não apresenta indicação de máximo de 5 minutos. Não se presume aprovação institucional da duração final.
- **Checklist:** somente gravação, revisão do vídeo, publicação, visibilidade não listada e obtenção do link marcados como concluídos. Demais verificações individuais não foram informadas; data exata de publicação desconhecida. Sem inspeção independente do vídeo ou nova publicação nesta tarefa.
- **Documentação:** quatro arquivos solicitados atualizados, mantendo tempos originais como planejamento histórico e preservando registros anteriores de validação. README, artigo SBC, branding, imagens, app, Kotlin, testes, Gradle, versão e APK intactos; nenhum Gradle executado.
- **Verificações documentais:** git diff --check, diff/stat/name-only e status; URL, visibilidade, limites de evidência e encerramento R10/R11 conferidos.
- **Conclusão:** R10.1–R10.4 concluídos; R10.5 concluído; R10 encerrado com gate SIM conforme evidência do autor. R11 não iniciado. PR e merge dependem de confirmação própria, distinta do gate documental.

## R11.1 — candidate 1.0.0, builds e auditoria pública

- **Data:** 2026-10-07 (America/Sao_Paulo). Base develop `620b70febf6c64c02610e4e24ec5713b9eb21e77`, merge PR #33; árvore inicialmente limpa, branch interna work alinhada por fast-forward.
- **Skills:** Android e testes locais consultadas/aplicadas. Alteração executável limitada a versionName 1.0 → 1.0.0; versionCode 1, package br.com.prodtime, SDK mínimo 26/target 36, motores/Kotlin/UX/dependências/testes intactos.
- **Comando conjunto:** após carregar android-env.sh, `bash ./gradlew clean testDebugUnitTest assembleDebug assembleDebugAndroidTest` — BUILD SUCCESSFUL in 36s, 66 actionable tasks: 66 executed. clean, suíte JVM, debug e APK AndroidTest concluídos. Nove XML registram 118 testes, zero falhas/erros/ignorados; não apenas contagem estática. Instrumentação compilada, não executada em dispositivo.
- **Release:** `bash ./gradlew assembleRelease` — BUILD SUCCESSFUL in 37s, 47 actionable tasks: 47 executed. APK release unsigned realmente gerado. Warning existente LocalClipboardManager deprecado em FormComponents.kt:218 nos dois builds; não bloqueador.
- **Artefatos:** três APKs localizados/inspecionados por aapt e apksigner. Debug 10.823.792 bytes, release unsigned 7.094.548 bytes, AndroidTest 1.000.976 bytes. Assinatura debug/AndroidTest verificada com certificado Android Debug; release não verifica por ausência de assinatura. Metadados e SHA-256 completos em RELEASE_1_0_0.md e dist/APK_METADATA.json.
- **Distribuição local:** dist/ProdTime-1.0.0-debug.apk, cópia do debug; SHA-256 d183aae4afd633f42694c844cdc65805f70a679870fe7be9608d994e20731fa8. SHA256SUMS.txt validado com sha256sum -c. Não é assinatura de produção. dist/builds ignorados; nenhum APK versionado. Nenhuma keystore de produção criada. Instalação e nova validação física não executadas.
- **Auditoria:** checkout não superficial, 67 commits e 289 blobs alcançáveis, 261 textuais; 125 arquivos rastreados na base. Buscas de secrets em versões históricas, bytes de blobs e commits, metadados e checkout; sem credenciais/tokens/chaves identificados. Arquivos locais/ignorados e imagens EXIF inspecionados; alcance/limites no relatório.
- **Bloqueios:** e-mail pessoal adicional não autorizado nos metadados de 43 commits, valor omitido; exemplos cb9bcec e 620b70f. Fonte docs/legacy/vba/CalMetrosNaMaq.txt (commit de74ba8) exige confirmação de autorização de exposição por vínculo ao ERP empresarial. Nenhuma autorização presumida, remoção ou reescrita de histórico realizada.
- **Licença:** LICENSE ausente; decisão do autor pendente, sem adoção automática. Repositório permanece privado conforme informado; nenhuma mudança de visibilidade/main/tag/release.
- **Documentação:** release/auditoria criadas; referências atuais de versão e estado em README/branding/ficha/portfólio/UX/processo alinhadas. Artigo, screenshots e fonte VBA preservados. Download público não anunciado enquanto o gate está bloqueado.
- **Verificações:** git diff --check/stat/name-only/status e git log --oneline -10; 42 links relativos, metadados/hashes/ignore e escopo conferidos. Duas versões históricas do PDF extraídas e pesquisadas sem token/chave de alta confiança. Logs e scanner auxiliares ficam fora do Git.
- **Conclusão:** gate técnico SIM; gate público NÃO. R11 e R11.1 em andamento; R11.2–R11.4 não iniciados. Commit condicionado à aprovação da auditoria não criado; sem publicação de branch nova, PR ou merge nesta etapa. Alterações locais reviewáveis e artefatos preservados.

## R11.1B — consolidação do release candidate

- **Data:** 2026-10-07 (America/Sao_Paulo). Workspace anterior preservado: 11 arquivos de R11.1 ainda modificados, sem commit. Base develop reconfirmada em 620b70febf6c64c02610e4e24ec5713b9eb21e77. git status/diff/cached/log examinados; alterações reutilizadas, sem refazer código executável.
- **Decisão explícita do proprietário:** e-mail pessoal adicional e fonte VBA histórico avaliados; manutenção e exposição aceitas. Não remover arquivos/commits/branches nem reescrever histórico. Sanitização histórica dispensada pela decisão; auditoria extensa não repetida. Valor do e-mail adicional permanece omitido nos documentos. Nenhuma garantia universal de ausência de outros dados sensíveis.
- **Licença:** LICENSE ausente por decisão atual do proprietário. Nenhuma licença criada.
- **Versão:** 1.0.0/código 1; package br.com.prodtime; mínimo 26/target 36 preservados e reconfirmados no APK.
- **Validação atual:** `bash ./gradlew testDebugUnitTest assembleDebug` — BUILD SUCCESSFUL in 1s; 40 actionable tasks: 40 up-to-date. Não houve nova execução da suíte; nove XML anteriores preservam 118 testes, zero falhas/erros/ignorados. Builds AndroidTest/release anteriores preservados; não repetidos, pois nenhuma mudança executável foi refeita. Instrumentação não executada em dispositivo.
- **APK reconferido:** dist/ProdTime-1.0.0-debug.apk, 10.823.792 bytes; SHA-256 d183aae4afd633f42694c844cdc65805f70a679870fe7be9608d994e20731fa8; aapt confirma metadados e apksigner verifica assinatura debug v2. Release anterior continua unsigned. dist e APKs ignorados, sem versionamento de binários ou chaves.
- **Documentação:** auditoria atualizada com decisão do proprietário, gate aprovado e ausência de garantia universal; release/branding/ficha/rollout coerentes. Registro R11.1 anterior preservado como histórico da decisão pendente naquele momento.
- **Verificações:** git diff --check/stat/name-only/status/log; hashes/assinatura/XML/metadados/links e ignore conferidos. Escopo permanece nos 11 arquivos legítimos de R11.1, sem alteração de Kotlin/testes/artigo/imagens/legado.
- **Conclusão:** R11.1 concluído e pronto para R11.2. R11 em andamento; R11.2–R11.4 não iniciados. Sem main/tag/GitHub Release/visibilidade pública/LinkedIn/remoção de branches. Commit e PR de consolidação têm confirmação própria.

## R11.4 — fechamento documental pós-publicação

- **Evidência confirmada pelo autor:** PR #35 develop → main mergeado; main contém ProdTime 1.0.0; tag v1.0.0 existente; GitHub Release “ProdTime 1.0.0” publicada com ProdTime-1.0.0-debug.apk e SHA256SUMS.txt; repositório público e verificação pública concluída. Release: https://github.com/Josue-JPAEJ/prodtime/releases/tag/v1.0.0. Vídeo: https://youtu.be/UrrcNwLq76w, não listado.
- **Conferência Git:** origin/develop em 89e71b7ec34ae0b9f711ed55487dc39429bf3c01; origin/main em bc32639 (merge PR #35). Fechamento preparado sobre develop com árvore inicialmente limpa.
- **SHA-256 público informado pelo autor:** 1162ac6b8f99430cb818460537eb406963fb07b43d5a609bd81a89f675fdeeb5. Difere do APK local histórico d183aae4afd633f42694c844cdc65805f70a679870fe7be9608d994e20731fa8; nenhum APK foi substituído, reconstruído ou alterado. Não atribuir os metadados inspecionados do build local ao binário público sem nova inspeção.
- **Limite de verificação independente:** consulta gh à release retornou Forbidden. Publicação, assets, visibilidade e verificação pública registrados como evidência do autor; download ou inspeção independente do APK público não realizados nesta tarefa.
- **Documentação:** README, release, rollout, validações e dois documentos de portfólio atualizados. Históricos temporais preservados; código, Gradle, testes, APKs, artigo e imagens intactos. Nenhum Gradle ou teste físico executado.
- **Validação documental:** revisão do diff, git diff --check, git diff --stat, git diff --name-only e git status; escopo limitado aos seis documentos solicitados.
- **Conclusão documental:** R11.1–R11.4 e R11 concluídos conforme estado publicado confirmado pelo autor. PRs e merges deste fechamento possuem confirmação própria; não presumir integração diante de bloqueio da API.
