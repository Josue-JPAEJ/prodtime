# Regras de negócio

Este documento contém apenas o conhecimento disponível. Ele não especifica ainda a fórmula matemática do ProdTime.

## Fatos conhecidos

- O domínio é a estimativa de capacidade e prazo na produção de fitas têxteis.
- Velocidade da máquina é informada em centímetros por minuto (`cm/min`).
- A quantidade de fitas simultâneas influencia a capacidade.
- Horas produtivas, desperdício e calendário de trabalho participam da estimativa.
- Sábados, domingos e feriados precisam ser representados no calendário produtivo.
- O modo de capacidade usa um período; o modo de prazo usa uma quantidade alvo e uma data inicial.
- O resultado deve ser determinístico para as mesmas entradas e regras.

## Hipóteses ainda não validadas

- A forma exata de aplicar o percentual de desperdício.
- A inclusão ou exclusão das datas inicial e final na contagem.
- O significado e a fórmula de “saldo, quando aplicável”.
- A unidade da quantidade alvo e os limites aceitos para cada entrada.
- Se horas úteis podem variar entre dias no MVP.
- A precisão interna e a regra de apresentação/arredondamento.

Hipóteses não podem ser convertidas em comportamento sem validação explícita.

## Regras pendentes de validação

1. Obter e especificar a fórmula real da calculadora anterior.
2. Definir conversões de `cm/min` para a unidade final de produção.
3. Definir a ordem de aplicação de fitas simultâneas, tempo e desperdício.
4. Definir precisão, arredondamento e tolerância de regressão.
5. Definir dias inclusivos/exclusivos e comportamento para início em dia não produtivo.
6. Definir feriados, fins de semana, jornadas parciais e intervalos sem dias produtivos.
7. Definir validações e mensagens para zero, negativos, datas invertidas e percentuais fora do limite.
8. Definir o critério determinístico das recomendações e seus limites.

## Referências históricas, não fórmulas

| Caso | Fitas | Velocidade | Horas | Desperdício | Resultado observado aproximado |
|---|---:|---:|---:|---:|---:|
| 1 | 2 | 25 cm/min | 304 h | 3% | 8.846 m |
| 2 | 3 | 25 cm/min | 304 h | 3% | 13.270 m |

Esses valores vieram da calculadora anterior e devem orientar futura regressão. Eles são aproximados, não demonstram por si sós a fórmula, a ordem das operações nem o arredondamento. A fórmula real deverá ser investigada, especificada e validada antes do motor matemático.

## Questões abertas

- Existe documentação, planilha, tela, código ou especialista que possa confirmar a fórmula legada?
- Quais unidades de entrada e saída serão aceitas além das já conhecidas?
- Como jornadas especiais e feriados locais serão cadastrados no MVP?
- Qual tolerância caracteriza equivalência com os resultados históricos?
- Quando um saldo existe e qual referência o determina?
