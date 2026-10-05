# Especificação do prazo de produção

# 1. Objetivo

Definir o contrato puro do Modo B, que responde quando uma quantidade alvo será concluída a partir de uma data inicial e de condições produtivas conhecidas.

# 2. Escopo do Modo B

O fluxo estima a primeira data em que a produção líquida acumulada alcança a meta. Ele compõe os motores existentes e não inclui UI, persistência, recomendações ou integrações externas.

# 3. Entradas

`ProductionDeadlineInput` recebe `startDate`, `includeStartDate`, as políticas `includeSaturdays`, `includeSundays` e `workOnHolidays`, uma coleção de `HolidayDefinition`, `speedCmPerMinute`, `tapeCount`, `productiveHoursPerDay`, `wastePercent` e `targetMeters`.

`endDate`, `includeEndDate`, `productiveDays` e um conjunto concreto de feriados não são entradas. A data final é resultado, os dias são derivados e os feriados são resolvidos das definições.

# 4. Saída

`ProductionDeadlineResult` contém `completionDate`, `requiredProductiveDays`, `resolvedHolidays`, o `ProductiveCalendarResult` final e o `ProductionCapacityResult` acumulado. O saldo da capacidade é obrigatório e não negativo.

# 5. Pipeline de domínio

```text
HolidayDefinition
→ HolidayResolver
→ ProductiveCalendarCalculator
→ ProductionCapacityCalculator
```

O resolvedor materializa recorrências, o calendário classifica cada candidata e o motor de capacidade avalia a produção acumulada.

# 6. Definição da data de conclusão

A conclusão é a primeira data produtiva cuja capacidade acumulada apresenta `balanceMeters >= 0`. A busca para imediatamente nessa data; portanto, nenhuma data posterior pode substituir uma conclusão já suficiente. A data final sempre participa do calendário final.

# 7. Arredondamento e capacidade acumulada

A cada novo dia produtivo, `ProductionCapacityCalculator` é chamado com o total acumulado de dias e com a meta. Não existe fórmula diária paralela nem divisão da meta pela capacidade. Assim, `BigDecimal`, produção bruta, desperdício, produção líquida e as fronteiras `HALF_EVEN` continuam pertencendo integralmente ao motor R1.

# 8. Regras de calendário

Cada candidata é classificada por `ProductiveCalendarCalculator` em um intervalo de uma data. Sábados, domingos, feriados e `workOnHolidays` não são reinterpretados pelo orquestrador. O calendário final cobre `startDate` até `completionDate`, usa a opção informada para a ponta inicial e inclui obrigatoriamente a ponta final.

# 9. Feriados

`HolidayResolver` é a autoridade para recorrência anual, datas específicas, 29/02 e deduplicação. A busca resolve o horizonte técnico; o resultado expõe novamente apenas as ocorrências entre o início e a conclusão, inclusive.

# 10. Data inicial

`includeStartDate` decide se a data inicial pode ser classificada e contabilizada. Quando excluída, a busca começa efetivamente no dia seguinte. Mesmo incluída, ela só conta se as regras do calendário a classificarem como produtiva.

# 11. Meta

A meta do Modo B deve ser maior que zero e inteira em metros. Zero, negativos e frações são rejeitados. Essa restrição é própria do prazo e não altera o contrato genérico do motor R1.

# 12. Horizonte técnico de busca

A busca alcança no máximo `startDate.plusYears(100)`, inclusive. `MAX_SEARCH_YEARS = 100` é uma guarda técnica do MVP, não um limite operacional ou regra de negócio. Sem conclusão nesse horizonte, o fluxo lança `IllegalStateException` com mensagem clara, sem fabricar uma data.

# 13. Rastreabilidade

O resultado permite auditar datas consideradas, produtivas e não produtivas, feriados resolvidos, dias produtivos requeridos, produção bruta, desperdício, produção líquida, meta, saldo e data de conclusão.

# 14. Casos de aceitação

Os casos D1–D18 cobrem 10.000 m com três fitas, minimalidade, meta exata, um metro adicional, feriado trabalhado e não trabalhado, exclusão do início, início em domingo, feriado específico e duplicado, horas fracionárias, metas inválidas, parâmetros produtivos inválidos, esgotamento do horizonte, rastreabilidade e regressão com duas fitas.

# 15. Fora de escopo

UI, ViewModel, persistência, API, DI, jornadas variáveis, recomendações, inteligência artificial e R5 não pertencem a esta etapa.

# 16. Integração futura com UI

A futura UI deverá somente construir a entrada e apresentar o resultado rastreável. Ela não deverá resolver feriados, classificar datas, calcular capacidade, arredondar valores ou controlar a busca.

# 17. Conclusão

O Modo B possui contrato determinístico, composição direta dos motores validados, conclusão mínima verificável e guarda finita. O cálculo permanece puro e independente de Android.
