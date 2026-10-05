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
