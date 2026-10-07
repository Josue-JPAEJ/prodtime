# Evidências para o artigo

Este documento organiza fatos rastreáveis do projeto; não constitui o artigo final.

Os requisitos acadêmicos, o estado da verificação bibliográfica e os limites de uso das referências estão consolidados em `docs/academic/ACADEMIC_REQUIREMENTS.md`.

## 1. Problema real

As estimativas de capacidade e prazo na produção de fitas têxteis eram manuais, dependiam de profissionais experientes, demoravam a produzir respostas operacionais e comerciais e concentravam conhecimento. Não há métrica validada de tempo economizado.

## 2. Evolução histórica

**Fatos documentados:** processo manual → calculadora integrada ao ERP legado em VBA → substituição desse ERP por um ERP Web → ProdTime mobile. **Visão futura:** integração do ProdTime com ERP Web, PCP, histórico, IA e IoT; esses itens não existem no MVP.

## 3. Objetivo do ProdTime

1. Quanto consigo produzir em determinado período?
2. Quando vou terminar determinada quantidade?
3. Verificar se uma meta é viável e calcular o mínimo e o adicional de fitas.

## 4. Escopo do MVP

- domínio determinístico para capacidade, estimativa, prazo e viabilidade;
- calendário produtivo e feriados anuais ou específicos em memória;
- três jornadas mobile com prevenção e tratamento de erros;
- acessibilidade, responsividade e temas claro/escuro;
- testes JVM, regressões históricas, build Android e validação física.

## 5. Fora de escopo

Backend, autenticação, ERP/PCP completo, IoT, IA, múltiplas fábricas e planejamento dinâmico por máquinas reais.

## 6. Stack

Android nativo; Kotlin 2.0.21; Jetpack Compose; Material 3; API mínima 26; `BigDecimal`; `java.time`; JUnit 4; Gradle 8.13.

## 7. Arquitetura

- `ProductionCapacityCalculator`: calcula produção bruta, desperdício, produção líquida e saldo.
- `ProductiveCalendarCalculator`: classifica o intervalo e determina datas e contagens produtivas.
- `HolidayResolver`: resolve definições anuais e específicas em datas concretas, com deduplicação.
- `ProductionEstimateCalculator`: combina calendário, feriados e capacidade para estimar um período.
- `ProductionDeadlineCalculator`: encontra a primeira data cuja produção atende à meta.
- `ProductionViabilityAdvisor`: avalia atendimento e calcula o mínimo e o adicional de fitas.

O domínio é Kotlin puro e independente de Android/Compose; a UI coleta entradas e apresenta resultados.

## 8. Regras matemáticas

A fonte normativa é `docs/domain/CALCULATION_SPEC.md`. A velocidade é convertida por `cm/min × 60 / 100 = m/h`; a produção bruta multiplica velocidade convertida, fitas, horas/dia e dias produtivos; o desperdício percentual incide sobre a produção bruta; a produção líquida é bruta menos desperdício; resultados integrais usam `HALF_EVEN`; saldo é produção líquida menos meta.

## 9. Calendário

O intervalo considera as pontas conforme configuração, sábados, domingos e feriados. Feriados podem ser anuais ou de data específica, são deduplicados e tratam 29/02 sem deslocamento em ano não bissexto. Colisões entre categorias classificam a mesma data sem múltiplas subtrações.

## 10. Regressões históricas

Condições: 25 cm/min, 16 h/dia, 19 dias produtivos e 3% de desperdício.

- 2 fitas: 9.120 m brutos, 273,6 m de desperdício e **8.846 m líquidos**.
- 3 fitas: 13.680 m brutos, 410,4 m de desperdício e **13.270 m líquidos**.

## 11. Prazo

Caso validado: meta 10.000 m; início 05/10/2026; 25 cm/min; 3 fitas; 16 h/dia; 3%; sem sábados, domingos ou feriados. Resultado: 23/10/2026; 15 dias produtivos; 10.476 m; saldo +476 m.

## 12. Viabilidade

- 3 fitas: 13.270 m; meta atendida; excedente +3.270 m; mínimo 3; adicional 0.
- 2 fitas: 8.846 m; meta não atendida; déficit 1.154 m; mínimo 3; adicional 1.

## 13. Feriados

Evidência física: 01/12/2026 a 31/12/2026; 28 cm/min; 1 fita; 16 h/dia; 3%. Feriado não trabalhado: 22 dias e 5.737 m. Feriado trabalhado: 23 dias e 5.997 m. Não se extrapolam outros resultados deste caso.

## 14. Testes

A matriz rastreável está em `docs/testing/REGRESSION_MATRIX.md` e cobre capacidade e `HALF_EVEN`, calendário e bordas, feriados e recorrência, estimativa, prazo, viabilidade, parsing, sanitização, limites e apresentação. Foram identificados objetivamente 118 métodos anotados com `@Test` em `app/src/test`; esse número descreve o estado atual das fontes, não execuções individuais.

## 15. Validação

- **Automática:** `testDebugUnitTest` aprovado no gate final.
- **Build:** `assembleDebug` aprovado no gate final.
- **Regressões históricas:** casos de 2 e 3 fitas cobertos e aprovados.
- **Física:** três fluxos, calendário, feriados, prazo, viabilidade, cópia, foco/cursor, limites e sanitização aprovados no Samsung SM-A066M.
- **UX:** semântica, responsividade, feedback textual, cards diferenciados e temas registrados no checklist e nas rodadas físicas.

## 16. Limitações

Feriados não persistem entre processos; o app é local; não recebe dados de máquina em tempo real; parâmetros dependem do usuário; estimativas não são promessa de produção; não há IA nem PCP.

## 17. Trabalhos futuros

Integração com ERP Web e PCP; disponibilidade de máquinas; capacidade variável; persistência; histórico; IA; e IoT. Todos permanecem visão futura e dependem de especificação e evidência próprias.

## 18. Evidências visuais necessárias

Screenshots reais a produzir na etapa apropriada: Home; “Quanto consigo produzir?”; resultado do Modo A; “Quando vou terminar?”; resultado do prazo; “Verificar uma meta” atendida; “Verificar uma meta” não atendida; feriados; resultado com resumo de calendário. Nenhuma imagem foi criada nesta etapa.

Para a primeira versão do artigo, a seleção mínima recomendada de figuras está definida em `docs/academic/ARTICLE_PLAN.md`; os screenshots adicionais permanecem disponíveis para README e vídeo.
