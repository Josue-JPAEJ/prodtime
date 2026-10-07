# Especificação de viabilidade da produção

# 1. Objetivo

Definir a recomendação determinística do R5 para responder se uma configuração produtiva atende uma meta dentro de um período e qual é a quantidade mínima de fitas simultâneas necessária nas mesmas condições.

# 2. Escopo

O fluxo compara a estimativa líquida da configuração atual com uma meta, informa excedente ou déficit e recomenda o mínimo de fitas. É uma camada fina de domínio que compõe os motores validados; não altera automaticamente a produção nem promete atendimento comercial.

# 3. Entradas

`ProductionViabilityInput` recebe `startDate`, `endDate`, políticas de inclusão das pontas, sábados, domingos e feriados, `holidayDefinitions`, `speedCmPerMinute`, `currentTapeCount`, `productiveHoursPerDay`, `wastePercent` e `targetMeters`.

A meta deve ser maior que zero e inteira em metros. Dias produtivos, datas concretas de feriado e produção manual não são entradas. As unidades e validações produtivas permanecem sob responsabilidade de `ProductionCapacityCalculator`.

# 4. Resultado

`ProductionViabilityResult` contém a estimativa rastreável atual, a meta, a diferença com sinal, o indicador de atendimento, o mínimo de fitas e a quantidade adicional. Os dois últimos campos são anuláveis exclusivamente quando não existe dia produtivo.

# 5. Avaliação da configuração atual

Antes do calendário, uma avaliação técnica de um dia chama `ProductionCapacityCalculator` com a configuração atual e meta ausente. Assim, parâmetros produtivos inválidos são rejeitados inclusive quando o período não contém dias produtivos.

Depois, `ProductionEstimateCalculator` resolve uma única vez feriados, calendário e capacidade para `currentTapeCount`. A recomendação reutiliza `currentEstimate.calendar.productiveDays`; não recalcula calendário nem feriados para cada candidato.

# 6. Excedente e déficit

A diferença é sempre `currentEstimate.netProductionMeters - targetMeters`. Valor positivo é excedente, negativo é déficit e zero é atendimento exato. `meetsTarget` é verdadeiro quando a diferença é maior ou igual a zero.

# 7. Quantidade mínima de fitas

Cada candidato é calculado por `ProductionCapacityCalculator`, com velocidade, horas, desperdício e dias produtivos já apurados, usando `targetMeters = null`. O candidato atende quando sua produção líquida é maior ou igual à meta. Não há regra de três, divisão por produção unitária nem fórmula paralela; portanto, os arredondamentos `HALF_EVEN` do R1 são preservados.

`additionalTapesNeeded` é `maxOf(minimumTapeCount - currentTapeCount, 0)` e nunca é negativo.

# 8. Algoritmo de busca

A busca testa uma fita e dobra o candidato (`1, 2, 4, 8...`) até encontrar capacidade suficiente ou alcançar `Int.MAX_VALUE`. Encontrado um limite superior suficiente, uma busca binária entre o último insuficiente mais um e o primeiro suficiente retorna o menor inteiro que atende. O procedimento exige quantidade logarítmica de avaliações.

# 9. Zero dias produtivos

Com zero dias produtivos, a produção atual é zero, `meetsTarget` é falso, a diferença é `-targetMeters` e tanto `minimumTapeCount` quanto `additionalTapesNeeded` são nulos. A busca não é iniciada porque aumentar fitas não resolve um período sem tempo produtivo.

# 10. Guarda técnica

`Int.MAX_VALUE` é somente o limite técnico do tipo usado no contrato, não um limite operacional de negócio. Se nem esse candidato atingir a meta, o fluxo lança `IllegalStateException` com mensagem explícita. Nenhum máximo operacional de fitas foi inventado.

# 11. Rastreabilidade

O resultado preserva integralmente `ProductionEstimateResult`: feriados resolvidos, calendário, capacidade e produção da configuração atual. A diferença pode ser auditada diretamente a partir dessa estimativa e da meta.

# 12. Casos de aceitação

Os testes V1–V18 cobrem atendimento e déficit históricos, mínimo inferior à configuração atual, meta exata, feriado trabalhado ou não, zero dias, metas inválidas, pré-validação produtiva, minimalidade, horas fracionárias, deduplicação de feriados, intervalo invertido, guarda de `Int.MAX_VALUE`, rastreabilidade e coerência dos candidatos com o motor de capacidade.

# 13. Fora de escopo

Não fazem parte do R5: PCP, sequenciamento, múltiplas máquinas, otimização matemática ampla, IA, probabilidade, promessa comercial, limites operacionais reais ou recomendação de nova data de conclusão.

# 14. Integração futura com UI

Uma UI futura poderá apresentar atendimento, excedente ou déficit e fitas mínimas/adicionais, deixando explícitas as premissas do período. Este contrato não define defaults, textos de interface, ViewModel ou navegação.

# 15. Conclusão

O R5 fornece uma recomendação pequena, determinística, explicável e testável, derivada exclusivamente dos motores existentes. O fluxo responde à viabilidade no período sem recriar a recomendação de prazo do Modo B.
