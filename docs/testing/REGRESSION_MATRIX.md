# Matriz de regressão do ProdTime

# 1. Objetivo

Consolidar a rastreabilidade entre referências históricas, contratos vigentes e testes automatizados reais. A matriz não cria regras nem presume cobertura manual ou instrumentada inexistente.

# 2. Fontes de referência

- `docs/product/BUSINESS_RULES.md` e `docs/domain/CALCULATION_SPEC.md`: fórmula, unidades, precisão e regressões VBA.
- `docs/domain/CALENDAR_SPEC.md` e `docs/domain/HOLIDAY_SPEC.md`: calendário, recorrência, deduplicação e 29/02.
- `docs/domain/PRODUCTION_ESTIMATE_SPEC.md`, `PRODUCTION_DEADLINE_SPEC.md` e `PRODUCTION_VIABILITY_SPEC.md`: contratos dos três fluxos.
- `docs/ux/UX_SPEC.md`: parsing e apresentação em pt-BR.
- Testes JVM em `app/src/test/java/br/com/prodtime/`: evidência automatizada mapeada abaixo.

# 3. Motor de capacidade

| ID | Origem | Entrada | Resultado esperado | Teste que cobre |
|---|---|---|---|---|
| VBA-2F | Caso histórico 1 | 2 fitas; 25 cm/min; 304 h; 3% | bruta 9.120 m; desperdício 273,6 m; líquida 8.846 m | `ProductionCapacityCalculatorTest.reproduz regressao historica com duas fitas` |
| VBA-3F | Caso histórico 2 | 3 fitas; 25 cm/min; 304 h; 3% | bruta 13.680 m; desperdício 410,4 m; líquida 13.270 m | `ProductionCapacityCalculatorTest.reproduz regressao historica com tres fitas` |
| CAP-HALF-EVEN | Especificação matemática | empates inferior e superior | arredondamento integral `HALF_EVEN` | `ProductionCapacityCalculatorTest.half even arredonda empate para inteiro par inferior` e `...superior` |
| CAP-INVALID | Contrato R1 | velocidade, fitas, horas ou dias não positivos; desperdício fora de `[0,100)`; meta negativa/fracionária | `IllegalArgumentException` | `ProductionCapacityCalculatorTest.rejeita entradas fora do contrato` |

# 4. Calendário

| ID | Cenário | Resultado esperado | Teste que cobre |
|---|---|---|---|
| CAL-SAB | sábado e política de trabalho | não produtivo por padrão; produtivo somente quando permitido e sem bloqueio de feriado | `ProductiveCalendarCalculatorTest.intervalo multiplo preserva ordem classificacoes e contagens`; `...sabado feriado e produtivo quando ambas as politicas permitem` |
| CAL-DOM | domingo não trabalhado/trabalhado | classificação conforme política | `...domingo nao trabalhado nao e produtivo`; `...domingo trabalhado e produtivo quando nao e feriado` |
| CAL-COL-SAB | sábado + feriado | uma classificação, sem dupla subtração | `...feriado nao trabalhado prevalece sobre sabado trabalhado`; `...sabado feriado e produtivo quando ambas as politicas permitem` |
| CAL-COL-DOM | domingo + feriado | uma classificação, sem dupla subtração | `...domingo feriado nao e subtraido duas vezes`; `...trabalho em feriado nao torna domingo nao trabalhado produtivo` |
| CAL-PONTA | pontas excluídas | data removida antes das contagens | `...data inicial excluida nao participa de listas nem contagens`; `...data final excluida nao participa de listas nem contagens`; `...domingo feriado excluido na ponta inicial nao e classificado` |
| CAL-UNICA | intervalo de uma data | exige ambas as pontas incluídas | quatro testes `data unica ...` de `ProductiveCalendarCalculatorTest` |
| CAL-INVERTIDO | início após fim | rejeição com mensagem clara | `ProductiveCalendarCalculatorTest.intervalo invertido e rejeitado com mensagem clara` |
| CAL-ZERO | período válido sem dia produtivo | zero dias, nunca total negativo | `ProductiveCalendarCalculatorTest.intervalo valido pode ter zero dias produtivos` |
| CAL-DUP | data duplicada no conjunto | uma ocorrência | `ProductiveCalendarCalculatorTest.set construido de duplicatas contabiliza feriado uma vez` |

# 5. Feriados

| ID | Cenário | Resultado esperado | Teste que cobre |
|---|---|---|---|
| HOL-ANUAL | `AnnualHoliday` com `MonthDay` | ocorrência em cada ano aplicável | `HolidayResolverTest.H1 feriado anual e resolvido no mesmo ano`; H2 e H3 |
| HOL-ESPEC | `SpecificDateHoliday` com `LocalDate` | somente a data cadastrada dentro do intervalo | `HolidayResolverTest.H4 data especifica dentro do intervalo e incluida`; H5 |
| HOL-DUP | definições coincidentes | uma `LocalDate` resolvida | `HolidayResolverTest.H6 definicoes diferentes na mesma data sao deduplicadas` |
| HOL-29-B | anual 29/02 em ano bissexto | ocorrência em 29/02 | `HolidayResolverTest.H10 feriado anual em 29 de fevereiro ocorre em ano bissexto` |
| HOL-29-C | anual 29/02 em ano comum | nenhuma ocorrência e nenhum deslocamento | `HolidayResolverTest.H11 feriado anual em 29 de fevereiro nao ocorre em ano comum` |
| HOL-NOME | nome vazio ou espaços | rejeição | `HolidayResolverTest.nome vazio ou composto somente por espacos e rejeitado` |
| HOL-PIPE | coleção resolvida alimenta calendário | impacto real sem testar `mutableStateListOf` | `HolidayResolverTest.feriados resolvidos alimentam diretamente o calendario`; complementado por E3/E4, D5/D6 e V5/V6 para os três motores |

# 6. Quanto consigo produzir

| ID | Entrada | Resultado esperado | Teste que cobre |
|---|---|---|---|
| EST-3F | 01/10–27/10/2026; 3 fitas; 25 cm/min; 16 h/dia; 3%; sem fins de semana | 19 dias; bruta 13.680 m; desperdício 410,4 m; líquida 13.270 m | `ProductionEstimateCalculatorTest.E1 regressao de tres fitas usa os 19 dias calculados` |
| EST-2F | mesmo período; 2 fitas | 19 dias; bruta 9.120 m; desperdício 273,6 m; líquida 8.846 m | `ProductionEstimateCalculatorTest.E2 regressao de duas fitas usa os 19 dias calculados` |
| EST-FER | feriado anual no período | 18 dias se não trabalhado; 19 se trabalhado | E3 e E4 de `ProductionEstimateCalculatorTest` |
| EST-ZERO | domingo único não trabalhado | zero dias, zero produção e capacidade ausente | `ProductionEstimateCalculatorTest.E5 domingo unico sem trabalho retorna producao zero sem capacidade` |

# 7. Quando vou terminar

| ID | Entrada | Resultado esperado | Teste que cobre |
|---|---|---|---|
| PRAZO-HIST | início 05/10/2026; meta 10.000 m; 25 cm/min; 3 fitas; 16 h/dia; 3%; sem sábado, domingo ou feriado | conclusão 23/10/2026; 15 dias; produção 10.476 m; saldo +476 m | `ProductionDeadlineCalculatorTest.D1 meta de dez mil metros com tres fitas` |
| PRAZO-MIN | mesmo cenário | 14 dias insuficientes e 15 dias suficientes | `ProductionDeadlineCalculatorTest.D2 conclusao usa a primeira data que atinge a meta` |
| PRAZO-FER | feriado não trabalhado/trabalhado | atraso/restauração do prazo | D5 e D6 de `ProductionDeadlineCalculatorTest` |
| PRAZO-EDGE | meta zero, negativa ou fracionária; parâmetros inválidos; horizonte sem produtividade | rejeições e erro técnico claro | D12–D16 de `ProductionDeadlineCalculatorTest` |

# 8. Viabilidade

| ID | Entrada | Resultado esperado | Teste que cobre |
|---|---|---|---|
| VIA-A | 01/10–27/10/2026; meta 10.000 m; 25 cm/min; 16 h/dia; 3%; 3 fitas | produção 13.270 m; atende; diferença +3.270 m; mínimo 3; adicionais 0 | `ProductionViabilityAdvisorTest.V1 configuracao atual atende` |
| VIA-B | mesmo cenário; 2 fitas | produção 8.846 m; não atende; diferença −1.154 m; mínimo 3; adicional 1 | `ProductionViabilityAdvisorTest.V2 configuracao atual nao atende` |
| VIA-ZERO | período sem dia produtivo | produção zero; não atende; recomendação não aplicável | `ProductionViabilityAdvisorTest.V7 zero dias produtivos nao recomenda fitas` |
| VIA-MIN | configuração insuficiente | recomendação é o menor número suficiente | V12 e V18 de `ProductionViabilityAdvisorTest` |
| VIA-FER | feriado não trabalhado/trabalhado | capacidade e recomendação refletem a política | V5 e V6 de `ProductionViabilityAdvisorTest` |

# 9. Apresentação

| ID | Cenário | Resultado esperado | Teste que cobre |
|---|---|---|---|
| UI-DEC-V | decimal com vírgula | `7,5` → `BigDecimal("7.5")` | `PresentationFormattersTest.aceita decimal com virgula` |
| UI-DEC-P | decimal com ponto | `7.5` → `BigDecimal("7.5")` | `PresentationFormattersTest.aceita decimal com ponto` |
| UI-INT-OK | inteiro positivo | `3` → `3` | `PresentationFormattersTest.aceita inteiro positivo` |
| UI-INT-INV | fracionário, zero ou texto | rejeição | `PresentationFormattersTest.rejeita inteiro invalido` |
| UI-PTBR | metro inteiro, decimal e zero | `13.270 m`; `410,4 m`; `0 m` | `PresentationFormattersTest.formata metros com locale brasileiro e preserva casas relevantes` |
| UI-DATA | `LocalDate` | `dd/MM/yyyy` | `PresentationFormattersTest.formata data brasileira` |
| UI-DIF | déficit, excedente e zero | rótulo e módulo coerentes | `PresentationFormattersTest.apresenta deficit sem sinal negativo`; `...apresenta excedente e diferenca zero` |
| UI-SALDO+ | saldo positivo de prazo | prefixo `+` | coberto no domínio por D1; apresentação visual ainda manual |

# 10. Cobertura ainda manual

- Navegação por toque, botão Back do Android e preservação da coleção durante a sessão.
- Layout em larguras, densidades e tamanhos de fonte distintos; teclado/IME; rolagem e alvos de toque.
- Contraste e aparência nos temas claro e escuro.
- Texto, alinhamento e leitura dos resultados completos de R6.5/R6.6A em aparelho físico.
- Encerramento do processo e descarte observável dos feriados.

Esses itens pertencem à validação física final R6.8. Não existe alegação de validação visual de R6.5/R6.6A nesta matriz.
