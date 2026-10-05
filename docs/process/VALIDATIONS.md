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
