# Questões abertas do domínio

Este arquivo separa decisões já tomadas de questões ainda abertas do novo ProdTime. Fórmulas e comportamentos respondidos pelo VBA estão especificados em `CALCULATION_SPEC.md`; o contrato aprovado do calendário está em `CALENDAR_SPEC.md`.

## Decisões tomadas no R2 — calendário produtivo e feriados

- Cada data considerada é avaliada uma única vez e recebe uma única classificação final; colisões não produzem subtrações cumulativas.
- As pontas excluídas são removidas antes da classificação por dia da semana ou feriado.
- Feriados são datas explícitas em `Set<LocalDate>`; o conjunto deduplica entradas repetidas.
- Não existe recorrência anual implícita no calendário. `HolidayResolver` transforma definições anuais ou específicas em datas exatas antes do cálculo; 29/02 anual ocorre somente em anos bissextos.
- Não haverá API externa de feriados no MVP.
- Jornadas variáveis ou parciais ficam para evolução futura; o R2 calcula dias produtivos.

## Questões futuras do cadastro de feriados

- mecanismo de persistência e operações de CRUD;
- UX, mensagens e defaults do cadastro;
- importação, feriados nacionais automáticos, localidade e integração com ERP.

## P1 — questões do recurso secundário e limites operacionais

1. **Contrato das unidades:** confirmar com o responsável de produto que o peso bruto e a tara são kg/caixa e que `pFita` é massa linear em g/m; definir os rótulos inequívocos da UI. Essa questão permanece adiada porque a conversão por peso não integra os dois fluxos principais atuais.
2. **Limites e mensagens:** definir, se necessários, limites operacionais máximos para o motor de capacidade; definir intervalos válidos para pesos e massa linear e as mensagens da UI. Os limites inferiores de velocidade, fitas, horas, dias, desperdício e meta já pertencem ao contrato aprovado do R1.2; a validade estrutural do intervalo de calendário pertence ao contrato do R2.1.

## P2 — pode ser decidido posteriormente

1. **Decidida no R6:** os defaults históricos 28 cm/min, 1 fita, 16 h/dia e 3% são defaults editáveis exclusivos da apresentação; não alteram os contratos nem criam defaults no domínio.
2. **Decidida no R6:** a apresentação usa formato `pt-BR`; produção integral é exibida em metros sem casas decimais e valores realmente decimais preservam as casas relevantes.
3. Deve existir limite operacional inferior ao limite técnico do tipo numérico para evitar estimativas irreais?
4. **Encerrada no escopo do MVP do R5:** a recomendação determinística compara a estimativa no período com a meta e busca o mínimo de fitas pelos motores existentes. Limites operacionais reais permanecem abertos; `Int.MAX_VALUE` é somente uma guarda técnica.

A persistência e o CRUD de feriados permanecem abertos até o R6.6.
