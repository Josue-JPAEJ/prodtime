# Questões abertas do domínio

Este arquivo separa decisões já tomadas de questões ainda abertas do novo ProdTime. Fórmulas e comportamentos respondidos pelo VBA estão especificados em `CALCULATION_SPEC.md`; o contrato aprovado do calendário está em `CALENDAR_SPEC.md`.

## Decisões tomadas no R2.1 — calendário produtivo

- Cada data considerada é avaliada uma única vez e recebe uma única classificação final; colisões não produzem subtrações cumulativas.
- As pontas excluídas são removidas antes da classificação por dia da semana ou feriado.
- Feriados são datas explícitas em `Set<LocalDate>`; o conjunto deduplica entradas repetidas.
- Não existe recorrência anual implícita no núcleo. Feriados móveis são suportados pelo fornecimento de sua data exata.
- Não haverá API externa de feriados no MVP.
- Jornadas variáveis ou parciais ficam para evolução futura; o R2 calcula dias produtivos.

## P1 — necessário antes da UI

1. **Contrato das unidades:** confirmar com o responsável de produto que o peso bruto e a tara são kg/caixa e que `pFita` é massa linear em g/m; definir os rótulos inequívocos da UI.
2. **Limites e mensagens:** definir, se necessários, limites operacionais máximos para o motor de capacidade; definir intervalos válidos para pesos e massa linear e as mensagens da UI. Os limites inferiores de velocidade, fitas, horas, dias, desperdício e meta já pertencem ao contrato aprovado do R1.2; a validade estrutural do intervalo de calendário pertence ao contrato do R2.1.

## P2 — pode ser decidido posteriormente

1. Os defaults históricos — 3%, 16 h/dia, 1 fita e 28 cm/min — serão mantidos, alterados ou removidos?
2. A apresentação deve mostrar metros fracionários para produção, desperdício, saldo e conversão de peso, ou somente valores inteiros?
3. Deve existir limite operacional inferior ao limite técnico do tipo numérico para evitar estimativas irreais?
