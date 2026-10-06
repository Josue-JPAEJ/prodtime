# Especificação de UX do ProdTime

## 1. Objetivo

Entregar uma experiência mobile simples e guiada para estimar produção, prazo e viabilidade de uma meta, sem transportar a complexidade de um formulário industrial legado para o telefone.

## 2. Princípios

- Priorizar as três perguntas centrais do produto e reduzir a carga cognitiva.
- Expor unidades e premissas relevantes sem duplicar regras do domínio na apresentação.
- Usar componentes Material 3, layout rolável e os tokens do tema nos modos claro e escuro.
- Validar entradas com mensagens compreensíveis e manter os motores de domínio como autoridade final.

## 3. Jornada principal

A aplicação inicia na Home. Cada ação abre um fluxo independente; a ação visual e o botão Back do Android retornam à Home. A navegação usa estado interno, sem biblioteca adicional, para os três fluxos e a gestão de feriados.

## 4. Home

A Home apresenta “Quanto consigo produzir?”, “Quando vou terminar?” e “Verificar uma meta”. O acesso secundário “Configurar feriados” não compete visualmente com os três fluxos principais.

## 5. Quanto consigo produzir?

O fluxo coleta período, políticas de calendário, velocidade, fitas, horas produtivas e desperdício. A ação “Calcular produção” constrói `ProductionEstimateInput` e delega o cálculo a `ProductionEstimateCalculator`. O resultado destaca produção líquida e resume dias produtivos, categorias de calendário efetivamente incluídas, produção bruta, desperdício e período. Zero dias produtivos é um resultado válido e recebe explicação própria.

## 6. Quando vou terminar?

O fluxo coleta meta inteira em metros, data inicial, políticas de calendário e condições produtivas. A ação “Calcular prazo” constrói `ProductionDeadlineInput` e delega o cálculo a `ProductionDeadlineCalculator`. O resultado destaca a data de conclusão e resume meta, dias produtivos, categorias de calendário efetivamente incluídas, “Produção” e saldo. “Produção” é o rótulo aprovado para preservar legibilidade em telas estreitas.

## 6.1. Verificar uma meta

O fluxo compara a capacidade de um período com uma meta inteira positiva e chama `ProductionViabilityAdvisor`. O card informa textualmente “Meta atendida” ou “Meta não atendida”, dias produtivos, categorias de calendário efetivamente incluídas, produção, meta, mínimo necessário e fitas adicionais. O estado atendido usa `tertiaryContainer`/`onTertiaryContainer`; o não atendido usa `errorContainer`/`onErrorContainer`, sem depender somente da cor. Diferença positiva aparece como “Excedente” com sinal `+`; diferença negativa aparece como “Déficit” em módulo, sem sinal negativo; zero aparece como “Diferença — 0 m”. Sem dias produtivos, mínimo e adicional são “Não aplicável”.

## 7. Calendário

Datas permanecem como `LocalDate`. A seleção usa o DatePicker do Material 3 e converte epoch millis em UTC para evitar deslocamento de dia. As políticas de inclusão das pontas, sábados, domingos e feriados são editáveis conforme cada fluxo.

Os cards contam feriados, sábados e domingos a partir das datas produtivas do resultado e exibem somente categorias com contagem positiva. Uma data que seja simultaneamente feriado e sábado ou domingo participa das duas classificações, sem que as contagens sejam somadas para reconstruir o total produtivo.

## 8. Defaults de UI

Os defaults editáveis são 28 cm/min, 1 fita, 16 h/dia e 3% de desperdício. A interface inicia com inclusão da data inicial e, no fluxo por período, da data final; sábados, domingos e feriados começam desativados. Esses valores são somente conveniência de apresentação e não pertencem aos motores de domínio.

## 9. Unidades

Todos os campos numéricos exibem sua unidade: `cm/min`, fitas, `h/dia`, `%` e `m`. A apresentação não converte texto formatado de volta para cálculos.

## 10. Validação e mensagens

Campos vazios, decimais inválidos, inteiros não positivos, período invertido, limites operacionais excedidos e desperdício negativo ou igual/superior a 100% recebem mensagens próximas ao campo. Entradas decimais aceitam vírgula ou ponto e são convertidas diretamente para `BigDecimal`, sem `Double` ou `Float`. Espaços Unicode e caracteres invisíveis de formatação são removidos no campo e antes do parsing; letras e outros textos arbitrários são preservados e continuam inválidos. Depois que um erro é exibido, editar somente aquele campo remove imediatamente sua mensagem, sem apagar erros dos demais campos; a validação completa ocorre na próxima ação. Erros contratuais do domínio são apresentados em um card; o horizonte técnico do prazo recebe mensagem amigável sem expor detalhes internos.

Os limites de entrada são: velocidade maior que zero e menor que 1.000 cm/min; fitas inteiras entre 1 e 999.999; horas maiores que zero e no máximo 24; desperdício de 0% a menos de 100%; e meta inteira entre 1 m e 999.999.999 m nos fluxos aplicáveis.

## 11. Resultados

Datas usam `dd/MM/yyyy`. Números usam locale `pt-BR`, agrupamento de milhares e preservação das casas decimais relevantes. Resultados integrais são exibidos sem casas decimais. Saldo positivo recebe `+`; zero não recebe sinal. Cada card oferece “Copiar resultado” e copia em texto simples exatamente as informações apresentadas, incluindo os resumos de calendário visíveis, com confirmação breve após a ação.

## 12. Light/Dark

Os componentes usam `MaterialTheme.colorScheme` e `MaterialTheme.typography`. Nenhuma cor de conteúdo é fixa fora dos tokens existentes, permitindo adaptação natural aos temas claro, escuro e dinâmico.

O tema dinâmico permanece habilitado em Android 12 ou superior e acompanha o modo claro ou escuro do sistema. Em versões anteriores, o aplicativo usa as paletas clara e escura locais. A inspeção estática confirmou o uso dos tokens; as evidências físicas registradas encerraram a revisão de aparência nos modos claro e escuro.

## 13. Acessibilidade base

Campos têm rótulos e unidades visíveis, switches têm texto explícito, ações usam áreas de toque Material e mensagens não dependem apenas de cor. O conteúdo é rolável para telas menores e para uso com teclado aberto.

O indicador de avanço dos cards da Home é somente decorativo e não participa da árvore semântica. Rótulo e estado de cada switch são agrupados, e os seletores “Anual” e “Data específica” expõem semanticamente a seleção. As ações principais possuem altura mínima, em vez de altura fixa, para acomodar fonte ampliada.

## 14. Feriados — estado atual

`ProdTimeApp` mantém uma coleção compartilhada de `HolidayDefinition` durante a sessão. Os três fluxos usam a mesma coleção nos inputs de domínio; a quantidade cadastrada é exibida somente na tela de gestão de feriados, onde é relevante. A gestão em memória permite listar, adicionar e remover feriados anuais (`MonthDay`) e específicos (`LocalDate`). Não existem feriados fictícios, lista nacional, API ou persistência; ao encerrar o processo, os cadastros são descartados. A tela informa que os feriados cadastrados ficam disponíveis durante a sessão. Por decisão do R6.6B, persistência é evolução futura, fora do MVP acadêmico.

## 15. Evoluções R6.5–R6.8

- R6.5: UI de viabilidade implementada, validada por build/test local e confirmada na rodada física 1.
- R6.6A: estado compartilhado e gestão em memória implementados, validados por build/test local e confirmados na rodada física 1.
- R6.6B: persistência encerrada como evolução futura, fora do MVP acadêmico.
- R6.7: integração, textos, validações e estados de resultado revisados; concluído por testes/build, com revisão física final reservada ao R6.8.
- R6.8: concluído após as rodadas físicas de navegação, foco, feedback dos cards, cópia, calendário, responsividade e tratamento de erros.
- R8.6: sanitização de entradas, limites operacionais e limpeza individual de erros aprovados local e fisicamente.

## 16. Identidade e acabamento

O nome público permanece **ProdTime** e os recursos atuais de launcher ainda são os do template Android. Não será criada uma tela “Sobre” nem um logo arbitrário nesta etapa. Ícone personalizado, versão, descrição curta e finalidade acadêmica ficam planejados para o acabamento de identidade no R10.

Peso, tara e massa linear continuam adiados porque o recurso secundário não integra a jornada principal atual.
