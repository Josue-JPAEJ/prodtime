# Especificação de UX do ProdTime

## 1. Objetivo

Entregar uma experiência mobile simples e guiada para responder quanto é possível produzir em um período e quando uma quantidade será concluída, sem transportar a complexidade de um formulário industrial legado para o telefone.

## 2. Princípios

- Priorizar as duas perguntas centrais do produto e reduzir a carga cognitiva.
- Expor unidades e premissas relevantes sem duplicar regras do domínio na apresentação.
- Usar componentes Material 3, layout rolável e os tokens do tema nos modos claro e escuro.
- Validar entradas com mensagens compreensíveis e manter os motores de domínio como autoridade final.

## 3. Jornada principal

A aplicação inicia na Home. Cada ação abre um fluxo independente; a ação visual e o botão Back do Android retornam à Home. A navegação é um estado interno com três destinos (`Home`, `ProductionEstimate` e `ProductionDeadline`), sem biblioteca adicional.

## 4. Home

A Home apresenta o nome ProdTime, o propósito “Planeje capacidade e prazo de produção.” e somente as duas ações disponíveis: “Quanto consigo produzir?” e “Quando vou terminar?”. Viabilidade não aparece enquanto o R6.5 não for implementado.

## 5. Quanto consigo produzir?

O fluxo coleta período, políticas de calendário, velocidade, fitas, horas produtivas e desperdício. A ação “Calcular produção” constrói `ProductionEstimateInput` e delega o cálculo a `ProductionEstimateCalculator`. O resultado destaca produção líquida e resume dias produtivos, produção bruta, desperdício e período. Zero dias produtivos é um resultado válido e recebe explicação própria.

## 6. Quando vou terminar?

O fluxo coleta meta inteira em metros, data inicial, políticas de calendário e condições produtivas. A ação “Calcular prazo” constrói `ProductionDeadlineInput` e delega o cálculo a `ProductionDeadlineCalculator`. O resultado destaca a data de conclusão e resume meta, dias produtivos, produção estimada e saldo.

## 7. Calendário

Datas permanecem como `LocalDate`. A seleção usa o DatePicker do Material 3 e converte epoch millis em UTC para evitar deslocamento de dia. As políticas de inclusão das pontas, sábados, domingos e feriados são editáveis conforme cada fluxo.

## 8. Defaults de UI

Os defaults editáveis são 28 cm/min, 1 fita, 16 h/dia e 3% de desperdício. A interface inicia com inclusão da data inicial e, no fluxo por período, da data final; sábados, domingos e feriados começam desativados. Esses valores são somente conveniência de apresentação e não pertencem aos motores de domínio.

## 9. Unidades

Todos os campos numéricos exibem sua unidade: `cm/min`, fitas, `h/dia`, `%` e `m`. A apresentação não converte texto formatado de volta para cálculos.

## 10. Validação e mensagens

Campos vazios, decimais inválidos, inteiros não positivos, período invertido, desperdício negativo ou igual/superior a 100% recebem mensagens próximas ao campo. Entradas decimais aceitam vírgula ou ponto e são convertidas diretamente para `BigDecimal`, sem `Double` ou `Float`. Erros contratuais do domínio são apresentados em um card; o horizonte técnico do prazo recebe mensagem amigável sem expor detalhes internos.

## 11. Resultados

Datas usam `dd/MM/yyyy`. Números usam locale `pt-BR`, agrupamento de milhares e preservação das casas decimais relevantes. Resultados integrais são exibidos sem casas decimais. Saldo positivo recebe `+`; zero não recebe sinal.

## 12. Light/Dark

Os componentes usam `MaterialTheme.colorScheme` e `MaterialTheme.typography`. Nenhuma cor de conteúdo é fixa fora dos tokens existentes, permitindo adaptação natural aos temas claro, escuro e dinâmico.

## 13. Acessibilidade base

Campos têm rótulos e unidades visíveis, switches têm texto explícito, ações usam áreas de toque Material e mensagens não dependem apenas de cor. O conteúdo é rolável para telas menores e para uso com teclado aberto.

## 14. Feriados — estado atual

Os fluxos informam “Feriados cadastrados: 0” e enviam uma coleção vazia de `HolidayDefinition`. Não existem feriados fictícios, lista nacional, API, persistência ou cadastro nesta etapa. A gestão fica reservada ao R6.6.

## 15. Evoluções R6.5–R6.8

- R6.5: UI de viabilidade.
- R6.6: gestão de feriados.
- R6.7: validações, feedback e integração final da UX.
- R6.8: validação manual em aparelho físico, incluindo teclado, responsividade, tema e acessibilidade.

Peso, tara e massa linear continuam adiados porque o recurso secundário não integra a jornada principal atual.
