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

## Decisões ainda não validadas

- Preservar exatamente ou substituir conscientemente o arredondamento e as coerções integrais do VBA.
- Corrigir colisões entre feriados, fins de semana e exclusões das pontas no calendário do R2.
- Confirmar contratos de peso (`kg/caixa`) e massa linear (`g/m`) antes da UI.
- Definir limites de entrada, precisão interna e regra de apresentação.
- Decidir se horas úteis podem ser fracionárias ou variar entre dias.

Hipóteses não podem ser convertidas em comportamento sem validação explícita.

## Regras pendentes de validação

1. Definir precisão, arredondamento e tolerância de regressão.
2. Definir a política do novo calendário para colisões, pontas e dias não produtivos.
3. Definir feriados, jornadas parciais e intervalos sem dias produtivos.
4. Definir validações e mensagens para zero, negativos, datas invertidas e percentuais fora do limite.
5. Definir o critério determinístico das recomendações e seus limites.

## Regressões históricas confirmadas

| Caso | Fitas | Velocidade | Horas | Desperdício | Resultado legado |
|---|---:|---:|---:|---:|---:|
| 1 | 2 | 25 cm/min | 304 h | 3% | 8.846 m |
| 2 | 3 | 25 cm/min | 304 h | 3% | 13.270 m |

O código VBA explica exatamente os dois resultados: a produção líquida fracionária é atribuída a um `Long`, usando a coerção integral do VBA. Os casos permanecem referências de regressão, agora apoiadas pela fórmula real.

## Questões abertas

As questões abertas vigentes e suas prioridades estão centralizadas em `docs/domain/OPEN_QUESTIONS.md`.
