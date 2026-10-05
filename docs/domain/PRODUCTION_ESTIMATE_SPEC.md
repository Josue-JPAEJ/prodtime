# Especificação da estimativa de produção

# 1. Objetivo

Definir o contrato puro do primeiro fluxo funcional do ProdTime: responder quanto é possível produzir em um período, compondo os motores já validados de feriados, calendário e capacidade.

# 2. Escopo do Modo A

O Modo A estima a produção líquida em metros para um intervalo e condições produtivas informadas. Ele não recebe meta, não calcula saldo ou prazo e não contém UI, persistência ou integração externa.

# 3. Entradas

- intervalo: `startDate` e `endDate` como `LocalDate`;
- pontas: `includeStartDate` e `includeEndDate`;
- calendário: `includeSaturdays`, `includeSundays` e `workOnHolidays`;
- feriados: `holidayDefinitions` como coleção de `HolidayDefinition`;
- produção: `speedCmPerMinute` e `productiveHoursPerDay` como `BigDecimal`, `tapeCount` como inteiro e `wastePercent` como `BigDecimal`.

Os dias produtivos não são entrada: são derivados do calendário. Datas concretas de feriados também não são entrada: são resolvidas das definições. `targetMeters` não pertence ao Modo A.

# 4. Pipeline de domínio

```text
HolidayDefinition
→ HolidayResolver
→ ProductiveCalendarCalculator
→ ProductionCapacityCalculator
```

Primeiro, `HolidayResolver` resolve as definições no intervalo. O conjunto resultante alimenta `ProductiveCalendarCalculator`. Havendo ao menos um dia produtivo, o total calculado alimenta `ProductionCapacityCalculator`, sempre com `targetMeters = null`.

# 5. Resultado

`ProductionEstimateResult` preserva:

- `resolvedHolidays`: datas concretas e deduplicadas;
- `calendar`: datas consideradas, produtivas e não produtivas, totais e contagens;
- `capacity`: produção bruta, desperdício e produção líquida quando existe dia produtivo;
- `netProductionMeters`: produção líquida derivada, ou zero quando `capacity` é ausente.

Saldo não integra a saída funcional do Modo A.

# 6. Zero dias produtivos

Zero dias produtivos é resultado válido do calendário. Nesse caso, o motor de capacidade não é chamado, `capacity = null` e `netProductionMeters = 0`. Assim, o fluxo não cria um resultado fictício nem altera o contrato do R1, que exige `productiveDays > 0`.

# 7. Validações

O fluxo preserva os contratos dos componentes. O resolvedor e o calendário rejeitam intervalo invertido. Quando há dia produtivo, o motor de capacidade valida velocidade, fitas, horas e desperdício. Quando não há dia produtivo, essas validações produtivas não são executadas porque não existe cálculo de capacidade.

Não há limites máximos adicionais nem duplicação indiscriminada das validações.

# 8. Rastreabilidade

O resultado expõe o conjunto efetivamente resolvido, o calendário completo e, quando aplicável, o resultado original do motor R1. Isso permite relacionar cada data à quantidade de dias produtivos e à capacidade calculada, sem reimplementar fórmulas.

# 9. Casos de aceitação

- E1 e E2: regressões históricas de três e duas fitas com 19 dias produtivos;
- E3 e E4: feriado não trabalhado reduz capacidade e feriado trabalhado a preserva;
- E5: período sem dia produtivo retorna zero e capacidade ausente;
- E6: política da ponta altera dias e capacidade;
- E7 e E8: feriados anual e específico são resolvidos;
- E9: definições coincidentes resultam em uma data e uma classificação;
- E10: intervalo invertido é rejeitado;
- E11: horas fracionárias permanecem em `BigDecimal`;
- E12: feriados, listas do calendário, dias e capacidade permanecem coerentes.

# 10. Fora de escopo

UI, ViewModel, banco, API, repositório, injeção de dependência, meta, saldo, prazo, recomendações, jornadas variáveis e R4 não pertencem a este contrato.

# 11. Integração futura com UI

Uma futura UI poderá construir `ProductionEstimateInput` e apresentar `netProductionMeters` e a rastreabilidade. A UI não deverá reproduzir resolução de feriados, calendário, fórmula, arredondamento ou tratamento de zero dias.

# 12. Conclusão

O contrato do Modo A compõe os três componentes existentes por uma camada fina, determinística e independente de Android. A implementação mantém as regras em seus motores de origem e trata explicitamente a fronteira válida de zero dias produtivos.
