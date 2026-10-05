# Regras de negócio

## Modo A — Quanto consigo produzir?

Para responder quanto é possível produzir em determinado período, o sistema resolve as definições de feriados, calcula os dias produtivos conforme as políticas do calendário e calcula a capacidade do período. A saída principal é a produção líquida estimada em metros; meta e prazo não participam deste modo.

Este documento resume as regras conhecidas. As especificações auditáveis, as coerções do VBA e as questões abertas estão em `docs/domain/CALCULATION_SPEC.md`, `docs/domain/CALENDAR_SPEC.md` e `docs/domain/OPEN_QUESTIONS.md`.

## Modo B — Quando vou terminar?

O fluxo de prazo recebe uma meta maior que zero em metros inteiros e uma data inicial conhecida. O término é a primeira data cuja capacidade líquida acumulada atinge ou supera a meta, respeitando calendário e feriados; a data final participa do cálculo. As regras e fronteiras de arredondamento permanecem exclusivamente no motor R1.

## Fatos conhecidos

- O domínio é a estimativa de capacidade e prazo na produção de fitas têxteis.
- Velocidade da máquina é informada em centímetros por minuto (`cm/min`).
- A quantidade de fitas simultâneas influencia a capacidade.
- Horas produtivas, desperdício e calendário de trabalho participam da estimativa.
- Sábados, domingos e feriados precisam ser representados no calendário produtivo.
- O modo de capacidade usa um período; o modo de prazo usa uma quantidade alvo e uma data inicial.
- O resultado deve ser determinístico para as mesmas entradas e regras.
- A velocidade por fita em `cm/min` é convertida em `m/h` por `(cm/min × 60) / 100`.
- A produção bruta multiplica velocidade em `m/h`, quantidade de fitas, horas produtivas por dia e dias contabilizados.
- O desperdício percentual incide sobre a produção bruta; a produção estimada exibida pelo legado é líquida.
- O saldo legado é a produção líquida menos a quantidade alvo em metros.
- O total de horas é horas produtivas por dia vezes dias contabilizados.
- A calculadora de peso converte o peso líquido total de kg para g e o divide pela massa linear da fita para obter metros.
- O motor de capacidade usa `BigDecimal`, sem `Double` ou `Float`, e não possui defaults embutidos.
- A produção bruta e a produção líquida são as duas fronteiras de arredondamento, ambas com `RoundingMode.HALF_EVEN`; o desperdício é calculado sobre a produção bruta já arredondada.
- Horas produtivas fracionárias são aceitas.
- Velocidade, quantidade de fitas, horas produtivas e dias produtivos devem ser maiores que zero; o desperdício deve ser maior ou igual a zero e menor que 100%.
- A meta em metros é opcional e, quando informada, deve ser inteira e não negativa; o saldo também é opcional e só existe quando há meta.

## Calendário produtivo

- O calendário usa `startDate` e `endDate` explícitos e exige `startDate <= endDate`; intervalo invertido é inválido e uma única data é um intervalo válido.
- O intervalo-base é inclusivo, e `includeStartDate` e `includeEndDate` controlam o pertencimento de cada ponta. Para uma data única, ambas as opções precisam estar ativas para que ela permaneça no intervalo considerado.
- Sábados e domingos podem ser trabalhados conforme `includeSaturdays` e `includeSundays`.
- Feriados são datas explícitas em `Set<LocalDate>` e sua possibilidade de trabalho é controlada por `workOnHolidays`; não há recorrência ou API externa implícita no MVP.
- Feriados não são informados por quantidade: definições cadastráveis são anuais recorrentes (`MonthDay`) ou de data específica (`LocalDate`).
- A recorrência é resolvida antes do calendário em `LocalDate` concretas; definições que coincidam na mesma data produzem um único efeito.
- UI e persistência do cadastro de feriados ainda não foram implementadas nem definidas.
- Pontas são removidas antes da classificação. Cada data restante é classificada uma única vez, sem dupla subtração por colisão entre fim de semana e feriado.
- Zero dias produtivos é resultado válido do calendário, inclusive quando não sobra nenhuma data considerada.

## Decisões ainda não validadas

- Confirmar contratos de peso (`kg/caixa`) e massa linear (`g/m`) antes da UI.
- Definir limites operacionais máximos, caso sejam necessários, e a regra de apresentação na UI.
- Definir os defaults da UI.

Hipóteses não podem ser convertidas em comportamento sem validação explícita.

## Regras pendentes de validação

1. Definir contratos e validações de peso e massa linear e as mensagens de UI.
2. Definir o critério determinístico das recomendações e seus limites.

## Regressões históricas confirmadas

| Caso | Fitas | Velocidade | Horas | Desperdício | Resultado legado |
|---|---:|---:|---:|---:|---:|
| 1 | 2 | 25 cm/min | 304 h | 3% | 8.846 m |
| 2 | 3 | 25 cm/min | 304 h | 3% | 13.270 m |

O código VBA explica exatamente os dois resultados: a produção líquida fracionária é atribuída a um `Long`, usando a coerção integral do VBA. Os casos permanecem referências de regressão, agora apoiadas pela fórmula real.

## Questões abertas

As questões abertas vigentes e suas prioridades estão centralizadas em `docs/domain/OPEN_QUESTIONS.md`.
