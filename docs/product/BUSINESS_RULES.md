# Regras de negócio

Este documento resume as regras conhecidas. A especificação auditável, as coerções do VBA e as questões abertas estão em `docs/domain/CALCULATION_SPEC.md` e `docs/domain/OPEN_QUESTIONS.md`.

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

## Decisões ainda não validadas

- Corrigir colisões entre feriados, fins de semana e exclusões das pontas no calendário do R2.
- Confirmar contratos de peso (`kg/caixa`) e massa linear (`g/m`) antes da UI.
- Definir limites operacionais máximos, caso sejam necessários, e a regra de apresentação na UI.
- Decidir se as horas produtivas podem variar entre dias.
- Definir os defaults da UI e as regras específicas de datas.

Hipóteses não podem ser convertidas em comportamento sem validação explícita.

## Regras pendentes de validação

1. Definir a política do novo calendário para colisões, pontas e dias não produtivos.
2. Definir feriados, jornadas parciais e intervalos sem dias produtivos.
3. Definir contratos e validações de peso e massa linear, regras específicas de datas e mensagens de UI.
4. Definir o critério determinístico das recomendações e seus limites.

## Regressões históricas confirmadas

| Caso | Fitas | Velocidade | Horas | Desperdício | Resultado legado |
|---|---:|---:|---:|---:|---:|
| 1 | 2 | 25 cm/min | 304 h | 3% | 8.846 m |
| 2 | 3 | 25 cm/min | 304 h | 3% | 13.270 m |

O código VBA explica exatamente os dois resultados: a produção líquida fracionária é atribuída a um `Long`, usando a coerção integral do VBA. Os casos permanecem referências de regressão, agora apoiadas pela fórmula real.

## Questões abertas

As questões abertas vigentes e suas prioridades estão centralizadas em `docs/domain/OPEN_QUESTIONS.md`.
