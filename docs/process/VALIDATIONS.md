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
