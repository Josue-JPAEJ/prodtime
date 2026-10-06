# Revisão da primeira versão

Checklist da releitura de redação em R9.2. As verificações abaixo não substituem a revisão técnica/acadêmica R9.3, que permanece pendente. A primeira versão está em `ARTICLE_DRAFT.md`.

## 1. Estrutura

- [x] Título de trabalho solicitado, autoria institucional com placeholders, Resumo, Palavras-chave, Abstract e Keywords presentes.
- [x] Sete seções acadêmicas, subseções técnicas e de resultados, duas tabelas e quatro referências presentes.
- [x] Cinco palavras-chave e Keywords correspondentes; Abstract conferido com o Resumo, sem informação adicional.
- [x] Versão identificada como inicial, sem aplicação do template nem geração de PDF/DOCX.
- [ ] Adequar a extensão ao número de páginas quando houver confirmação institucional.

## 2. Coerência técnica

- [x] Domínio Kotlin puro separado da UI Compose/Material 3; funcionamento local e sem backend.
- [x] Seis componentes descritos conforme `ARTICLE_EVIDENCE.md` e `ARCHITECTURE.md`.
- [x] Conversão cm/min para m/h e ordem do cálculo preservadas: bruta arredondada, desperdício decimal sobre a bruta e líquida arredondada.
- [x] `BigDecimal` e as duas fronteiras `HALF_EVEN` coerentes com `CALCULATION_SPEC.md`.
- [x] Pontas antes da classificação, políticas de fins de semana/feriados, deduplicação e 29/02 coerentes com as especificações.
- [x] Feriados somente em memória durante a sessão; estimativa sem meta; prazo pela primeira data suficiente; viabilidade condicionada aos parâmetros.
- [x] UI descrita com sanitização, limites, foco/cursor, temas, acessibilidade, feedback e cópia, sem funcionar como manual.
- [x] Nenhuma implementação, teste, regra de domínio ou configuração Gradle alterada pelo commit documental.

## 3. Evidências quantitativas

- [x] Regressão de 2 fitas: 9.120 m brutos, 273,6 m de desperdício e 8.846 m líquidos.
- [x] Regressão de 3 fitas: 13.680 m brutos, 410,4 m de desperdício e 13.270 m líquidos.
- [x] Prazo: 23/10/2026, 15 dias produtivos, 10.476 m e saldo +476 m para a meta de 10.000 m nas condições documentadas.
- [x] Viabilidade: déficit de 1.154 m com 2 fitas; excedente +3.270 m com 3; mínimo 3; adicionais 1 e 0.
- [x] Feriados: 22 dias/5.737 m e 23 dias/5.997 m, restritos ao caso de dezembro/2026.
- [x] 118 métodos anotados com `@Test` nas fontes, sem conversão dessa contagem em alegação de execução. Contagem estática confirmada nesta tarefa.
- [x] `testDebugUnitTest` e `assembleDebug` com `BUILD SUCCESSFUL` atribuídos ao gate local histórico R6.8/R8.6.
- [x] Samsung SM-A066M e itens físicos atribuídos aos registros anteriores, sem alegar nova rodada nesta tarefa.
- [x] Nenhuma métrica nova de cobertura, precisão, desempenho, tempo economizado ou redução de desperdício.

## 4. Uso das referências

- [x] Karacapilidis e Pappis (1996): contexto de planejamento e controle multifásico, sem equivalência ou validação do aplicativo.
- [x] Serafini e Speranza (1992): scheduling têxtil e máquinas/teares paralelos, sem atribuir seus algoritmos ao ProdTime.
- [x] Laoboonlur, Hodgson e Thoney (2006): exemplo complementar de tingimento/acabamento, sem equiparação à produção de fitas.
- [x] Hodge et al. (2011): contexto lean e desperdício, sem atribuir ganhos medidos ao aplicativo.
- [x] Metadados completos preservados de `REFERENCES.md`; somente quatro referências, com citações autor-data e paráfrases.
- [x] Limite a metadados e resumos editoriais declarado; nenhuma alegação de leitura integral ou página de citação inventada.

## 5. Alegações futuras

- [x] IA e PCP aparecem somente como ausentes do MVP ou possibilidades futuras.
- [x] Integração ERP Web, disponibilidade de máquinas, capacidade variável, persistência, histórico e IoT separados das funcionalidades atuais.
- [x] Estimativa não apresentada como promessa operacional; mínimo de fitas não implica disponibilidade real.

## 6. Linguagem acadêmica

- [x] Releitura da redação inicial realizada para marketing, extrapolações, repetições e confusão entre fato, resultado, limitação e futuro.
- [x] Desenvolvimento aplicado descrito sem rótulo metodológico formal não comprovado.
- [x] Resumo autônomo e tradução fiel; conclusão restrita às evidências expostas.
- [x] Buscas de termos sensíveis e de alegações indevidas sobre 118 testes realizadas.
- [ ] Revisão técnica/acadêmica R9.3 por leitura integral do texto e avaliação da argumentação.

## 7. Figuras pendentes

- [x] Marcadores e legendas explícitos para as Figuras 1, 2 e 3, em ordem de aparição.
- [ ] Obter screenshot real da Home e da configuração de acesso aos fluxos.
- [ ] Obter screenshot real de resultado da estimativa de produção.
- [ ] Selecionar screenshot real de prazo ou viabilidade e conferir a legenda.
- [ ] Ajustar tamanho, legibilidade, chamadas e posicionamento no template na etapa apropriada.

Nenhuma imagem foi inventada ou produzida em R9.2.

## 8. Dados institucionais pendentes

- [x] `[AUTOR]`, `[INSTITUIÇÃO]`, `[CURSO]` e `[E-MAIL]` preservados.
- [ ] Substituir placeholders somente por dados fornecidos e conferidos.
- [ ] Confirmar número de páginas e orientações específicas da instituição.
- [ ] Confirmar o template aplicável antes da formatação final R9.4.

Essas pendências não bloqueiam a revisão da primeira versão e não autorizam inferir nomes, contatos ou limite de páginas.

## 9. Ajustes para R9.3

- [ ] Revisar tecnicamente fórmulas, calendário, prazo e recomendação contra as especificações vigentes.
- [ ] Avaliar clareza da contribuição e limites dos casos de validação.
- [ ] Refinar concisão, transições, terminologia e equivalência entre Resumo e Abstract.
- [ ] Conferir citações e referências dentro dos limites bibliográficos de R9.1.
- [ ] Revisar a seleção das figuras e evitar duplicação entre texto e Tabela 2.
- [ ] Preparar ajustes de extensão após confirmação institucional, sem inventar requisito de páginas.

**Gate R9.2:** primeira versão pronta para revisão R9.3. **R9.3 não iniciada; R9.4 pendente; R9 em andamento; R10 e R11 não iniciados.**
