# ProdTime: aplicativo móvel para estimativa de capacidade e prazo na produção de fitas têxteis

[AUTOR]

[INSTITUIÇÃO] — [CURSO]

[E-MAIL]

> Primeira versão completa — R9.2. Texto de trabalho, sujeito à revisão técnica/acadêmica em R9.3 e à formatação em R9.4. Dados institucionais, figuras e número de páginas ainda pendentes.

## Resumo

Estimativas de capacidade e prazo na produção de fitas têxteis envolvem velocidade, quantidade de fitas simultâneas, horas produtivas, desperdício e calendário de trabalho. No contexto que originou este projeto, tais estimativas dependiam de cálculos manuais e do conhecimento de profissionais experientes, posteriormente incorporados a uma calculadora em VBA. Este trabalho apresenta o desenvolvimento do ProdTime, aplicativo Android para estimar a produção em um período, a data de conclusão de uma quantidade e a viabilidade de uma meta. O desenvolvimento aplicado de software compreendeu análise do legado, engenharia reversa das regras, especificação matemática e de calendário, implementação incremental e validação automatizada e física. A solução utiliza Kotlin e Jetpack Compose, com domínio determinístico independente da interface e cálculos decimais explícitos. As evidências documentadas incluem reprodução dos resultados históricos de 8.846 m e 13.270 m, casos de prazo e viabilidade, efeito da política de feriados, execução bem-sucedida das tarefas de testes e compilação e validação no Samsung SM-A066M. O aplicativo opera localmente, depende dos parâmetros informados e não recebe dados de máquinas em tempo real. Sua contribuição consiste na explicitação de regras produtivas rastreáveis e em sua disponibilização em três jornadas móveis, sem demonstrar ganhos operacionais quantitativos.

**Palavras-chave:** produção têxtil; aplicativo móvel; estimativa de capacidade; prazo de produção; Kotlin.

## Abstract

Capacity and completion-date estimates in textile tape production involve speed, the number of simultaneous tapes, productive hours, waste, and the working calendar. In the context that motivated this project, these estimates relied on manual calculations and the knowledge of experienced professionals, later incorporated into a VBA calculator. This paper presents the development of ProdTime, an Android application for estimating production over a period, the completion date for a quantity, and the feasibility of a target. The applied software development process comprised legacy analysis, reverse engineering of rules, mathematical and calendar specification, incremental implementation, and automated and physical-device validation. The solution uses Kotlin and Jetpack Compose, with a deterministic domain independent of the interface and explicit decimal calculations. Documented evidence includes reproduction of historical results of 8,846 m and 13,270 m, completion-date and feasibility cases, the effect of the holiday policy, successful execution of test and build tasks, and validation on a Samsung SM-A066M. The application operates locally, depends on user-supplied parameters, and does not receive real-time machine data. Its contribution consists of making production rules explicit and traceable and providing them through three mobile workflows, without demonstrating quantitative operational gains.

**Keywords:** textile production; mobile application; capacity estimation; production completion date; Kotlin.

## 1. Introdução

O planejamento da produção têxtil envolve decisões sobre quantidades, recursos e prazos. Uma estimativa de metragem depende de variáveis como velocidade de produção, quantidade de fitas simultâneas, horas disponíveis, desperdício e dias efetivamente trabalhados. A combinação dessas variáveis exige explicitar as premissas usadas para responder a demandas da produção e de vendas.

A literatura descreve problemas mais amplos de planejamento e controle em processos têxteis multifásicos, com múltiplas unidades e requisitos produtivos (KARACAPILIDIS; PAPPIS, 1996), e problemas formais de programação da produção com máquinas ou teares paralelos (SERAFINI; SPERANZA, 1992). Esse contexto mostra a complexidade do setor e ajuda a delimitar o problema abordado neste trabalho: estimar capacidade e prazo sob condições produtivas informadas, sem realizar o sequenciamento industrial completo.

No contexto real que originou o ProdTime, as estimativas eram feitas manualmente por profissionais experientes. A dependência desse conhecimento concentrava a análise e podia atrasar respostas operacionais e comerciais. Posteriormente, o autor desenvolveu uma calculadora integrada a um ERP legado em VBA, tornando as regras acessíveis a outros usuários. A substituição desse ERP por um ERP Web motivou a separação da necessidade de cálculo em uma aplicação móvel. A trajetória foi, portanto, processo manual → calculadora VBA → ERP Web → ProdTime mobile; a integração do aplicativo com o ERP Web permanece futura.

O problema de desenvolvimento é como disponibilizar, em uma interface móvel, estimativas reproduzíveis de produção, prazo e viabilidade, preservando as relações matemáticas conhecidas e especificando de forma explícita o calendário e o arredondamento. O objetivo é desenvolver um aplicativo que responda quanto é possível produzir em determinado período, quando uma quantidade poderá ser concluída e se uma meta é atendida, indicando o mínimo e o adicional de fitas necessários sob as mesmas premissas.

O escopo é um MVP Android local, com parâmetros constantes por estimativa e feriados configuráveis em memória. O ProdTime não é um ERP, um sistema de planejamento e controle da produção (PCP), um sistema completo de scheduling industrial, uma solução de inteligência artificial ou um sistema preditivo. A contribuição está na formalização rastreável das regras provenientes do legado, na separação entre domínio e apresentação e na implementação de três jornadas móveis verificadas por casos documentados. Não são atribuídos ao aplicativo ganhos de tempo ou redução de desperdício que não tenham sido medidos.

## 2. Fundamentação e Trabalhos Relacionados

Karacapilidis e Pappis (1996) tratam do planejamento e controle da produção têxtil em um estudo que considera processos multifásicos, múltiplas unidades, horizontes e requisitos produtivos. O trabalho oferece a fundamentação central para situar a necessidade de informação estruturada em ambientes produtivos complexos. Seu escopo não corresponde a uma aplicação móvel equivalente ao ProdTime nem valida as regras deste aplicativo.

Serafini e Speranza (1992) estudam problemas de scheduling na indústria têxtil, incluindo máquinas ou teares paralelos, algoritmos, limites e heurísticas. A referência sustenta a existência de problemas formais de programação produtiva. O ProdTime, por sua vez, calcula estimativas determinísticas a partir de parâmetros fornecidos e não implementa os algoritmos discutidos nesse trabalho.

Como exemplo complementar, Laoboonlur, Hodgson e Thoney (2006) abordam scheduling em tingimento e acabamento de malha, com ambiente flexible job shop, setups dependentes da sequência e family scheduling. A referência evidencia particularidades de outro processo têxtil; tingimento e acabamento não são equiparados à produção de fitas, e sua abordagem não foi incorporada ao aplicativo.

Hodge et al. (2011) discutem a adaptação de princípios lean à indústria têxtil, com atenção a desperdícios e atividades sem valor. Essa discussão fornece contexto para a melhoria sistemática no setor. No ProdTime, o desperdício é um percentual informado para o cálculo da produção líquida, não uma perda medida pelo sistema. O trabalho apresentado não demonstra implementação integral de lean nem redução de desperdício causada pelo aplicativo.

As quatro referências foram verificadas em R9.1 quanto a metadados e escopo dos resumos editoriais. Seu uso nesta primeira versão permanece restrito a esse material, sem alegação de leitura integral, citações diretas ou validação do ProdTime pelos autores citados. Os trabalhos contextualizam o setor; a evidência sobre o aplicativo provém de suas especificações, regressões e registros de validação.

## 3. Metodologia

O trabalho foi conduzido como desenvolvimento aplicado de software baseado em um problema real e na análise de uma solução legada. A identificação da necessidade e o levantamento do processo manual estabeleceram as perguntas de produção e prazo e a dependência do conhecimento de profissionais experientes. A análise da calculadora VBA permitiu reconstruir as relações entre velocidade, fitas, horas, dias, desperdício e saldo.

A engenharia reversa separou o comportamento histórico da regra desejada para o novo produto. As relações matemáticas foram preservadas com política decimal explícita; coerções implícitas, dependência da interface e subtrações repetidas no calendário foram tratadas como limitações do legado, sem transferência automática para o novo domínio. Em seguida, foram especificados unidades, contratos, duas fronteiras de arredondamento, classificação das datas e resolução de feriados.

A implementação foi incremental, organizada no rollout R0–R8: fundação do projeto, motor matemático, calendário, jornadas de estimativa e prazo, viabilidade, interface, regressões e robustez das entradas. Os testes automatizados acompanharam os contratos, incluindo exemplos históricos e casos de borda. A matriz de regressão relaciona os comportamentos esperados aos testes reais.

A validação reuniu verificações locais de testes e compilação e rodadas físicas no Samsung SM-A066M. Os registros distinguem falhas ambientais anteriores, resultados locais bem-sucedidos, achados visuais e validação dos refinamentos. Esta redação utiliza essas evidências já registradas; não constitui nova execução de testes nem nova avaliação com usuários. Não foi adotado um rótulo metodológico formal além do desenvolvimento aplicado descrito.

## 4. Desenvolvimento do ProdTime

### 4.1 Arquitetura

O aplicativo é Android nativo, desenvolvido em Kotlin, com interface em Jetpack Compose e Material 3. O domínio é Kotlin puro, independente de Android e Compose. A interface coleta e valida entradas, coordena o estado e apresenta resultados; as regras produtivas ficam nos componentes de cálculo. A operação é local e não exige backend.

O `ProductionCapacityCalculator` calcula capacidade, desperdício, produção líquida e saldo. O `ProductiveCalendarCalculator` classifica as datas do intervalo. O `HolidayResolver` converte definições de feriados em datas concretas. O `ProductionEstimateCalculator` integra esses componentes para estimar um período; o `ProductionDeadlineCalculator` encontra a primeira data suficiente para a meta; e o `ProductionViabilityAdvisor` compara a estimativa com a meta e determina o mínimo e o adicional de fitas. Essa composição reutiliza as regras comuns nas três jornadas.

[FIGURA 1 — Tela inicial do ProdTime]

Figura 1. Tela inicial com acesso às três jornadas e configuração de feriados.

### 4.2 Motor de capacidade

Considere velocidade `v` em cm/min, quantidade de fitas simultâneas `F`, horas produtivas por dia `H`, dias produtivos `D` e desperdício percentual `p`. A conversão é:

```text
velocidade em m/h = (v × 60) / 100
```

O cálculo preserva a sequência aprovada na especificação:

```text
produção bruta decimal = velocidade convertida × F × H × D
produção bruta = arredondamento integral HALF_EVEN da produção bruta decimal
desperdício = produção bruta × p / 100
produção líquida decimal = produção bruta − desperdício
produção líquida = arredondamento integral HALF_EVEN da produção líquida decimal
```

O desperdício incide sobre a produção bruta já arredondada e permanece decimal. Quando há meta, o saldo é a produção líquida integral menos a meta. O total de horas produtivas é `H × D`.

O uso de `BigDecimal`, construído a partir de texto ou constantes exatas, evita conversões intermediárias por `Double` ou `Float`. A política `HALF_EVEN` arredonda para o inteiro mais próximo e, em empate, escolhe o inteiro par. Ela é aplicada somente às fronteiras bruta e líquida descritas, preservando os resultados históricos conhecidos sem truncamento arbitrário. Horas fracionárias são aceitas, e o domínio não incorpora valores padrão da interface.

**Tabela 1. Principais parâmetros das jornadas.**

| Parâmetro | Unidade | Papel |
|---|---|---|
| Velocidade | cm/min | Velocidade linear convertida para o cálculo de capacidade. |
| Fitas | unidade | Quantidade de fitas produzidas simultaneamente. |
| Horas produtivas | h/dia | Jornada produtiva aplicada aos dias considerados produtivos. |
| Desperdício | % | Parcela da produção bruta descontada na estimativa líquida. |
| Período | data | Delimita o calendário na estimativa e na viabilidade; o prazo recebe a data inicial. |
| Meta | m | Quantidade desejada nos fluxos de prazo e viabilidade. |

### 4.3 Calendário e feriados

O calendário usa `LocalDate`, com intervalo-base inclusivo e início não posterior ao fim. As opções de inclusão das pontas são aplicadas antes da classificação produtiva. Em um intervalo de uma única data, ambas as pontas precisam estar incluídas. Cada data restante é avaliada uma vez conforme as políticas de sábados, domingos e feriados.

Trabalhar em feriados remove apenas o impedimento associado ao feriado; não torna produtivo um sábado ou domingo que continue excluído. Colisões entre fim de semana e feriado não geram dupla subtração. As contagens informativas podem se sobrepor, mas a decisão produtiva é única por data. Um calendário válido pode retornar zero dias produtivos; a integração de estimativa trata esse caso com produção zero.

Os feriados cadastrados têm nome e são anuais, por dia e mês, ou de data específica. O `HolidayResolver` resolve as ocorrências do intervalo e entrega um conjunto de datas ao calendário, deduplicando definições coincidentes. Um feriado anual em 29/02 ocorre somente nos anos bissextos, sem deslocamento para outra data nos anos comuns. Não há carregamento automático de feriados externos. A coleção é compartilhada pelas jornadas durante a sessão e descartada quando o processo termina.

### 4.4 Estimativa de produção

A jornada “Quanto consigo produzir?” recebe período, velocidade, fitas, horas produtivas por dia, desperdício e políticas de calendário. O sistema resolve os feriados, identifica os dias produtivos e calcula a capacidade do período. A saída principal é a produção líquida estimada, acompanhada de produção bruta, desperdício e detalhes do calendário. Esse fluxo não utiliza uma meta nem procura uma data de conclusão.

[FIGURA 2 — Resultado da estimativa de produção]

Figura 2. Exemplo do resultado do fluxo de estimativa de produção.

### 4.5 Estimativa de prazo

A jornada “Quando vou terminar?” recebe uma meta positiva em metros inteiros, a data inicial e as condições produtivas. O `ProductionDeadlineCalculator` encontra a primeira data cuja produção líquida acumulada, calculada pelas mesmas regras de capacidade e calendário, atinge ou supera a meta. A data final participa do cálculo. A resposta apresenta conclusão estimada, dias produtivos, produção na conclusão e saldo. A existência de um horizonte técnico de busca impede iteração indefinida; não representa garantia operacional de entrega.

### 4.6 Viabilidade da meta

A jornada de verificação de meta compara a produção líquida estimada no período com a quantidade desejada. O resultado informa atendimento, excedente ou déficit, quantidade mínima de fitas suficiente e quantidade adicional em relação à configuração atual. O mínimo é encontrado reutilizando o motor de capacidade e sua política de arredondamento, mantendo fixos os demais parâmetros. Em um período sem dias produtivos, a recomendação de fitas não é aplicável.

A análise é determinística e condicionada às entradas. Não considera disponibilidade real de máquinas nem executa otimização de PCP; não utiliza IA. A indicação de fitas adicionais expressa uma condição calculada, cuja disponibilidade precisa ser avaliada pelo usuário.

[FIGURA 3 — Resultado de prazo ou viabilidade]

Figura 3. Exemplo de resultado de prazo/viabilidade.

### 4.7 Interface móvel e robustez das entradas

A Home oferece acesso às três jornadas e à configuração de feriados. Os formulários Compose organizam unidades, opções e mensagens de erro; ações de teclado conduzem o foco e o cursor. A apresentação utiliza temas claro e escuro, semântica de acessibilidade, layouts roláveis e feedback textual para resultados e viabilidade. Os resultados podem ser copiados.

A entrada numérica aceita separador decimal por vírgula ou ponto. A sanitização remove whitespace e caracteres invisíveis previstos antes do parsing, preservando a rejeição de texto inválido. Limites operacionais de velocidade, fitas, horas, desperdício e meta são verificados na apresentação, sem modificar as fórmulas do domínio. A edição limpa o erro do campo corrigido, permitindo retomar o cálculo sem sair da tela. Essas medidas foram objeto de testes e registros físicos, sem constituir uma avaliação abrangente de usabilidade ou acessibilidade.

## 5. Resultados e Validação

Os resultados abaixo são casos documentados, sob parâmetros definidos. As fontes internas de rastreabilidade são `ARTICLE_EVIDENCE.md`, `REGRESSION_MATRIX.md` e `VALIDATIONS.md`. A concordância entre casos conhecidos e resultados não estabelece uma taxa de acerto para produção real.

### 5.1 Regressões históricas

Com 25 cm/min, 16 h/dia, 19 dias produtivos e 3% de desperdício, a configuração de duas fitas produz 9.120 m brutos, 273,6 m de desperdício e 8.846 m líquidos. Para três fitas, os resultados são 13.680 m brutos, 410,4 m de desperdício e 13.270 m líquidos.

Antes do arredondamento líquido, os valores são 8.846,4 m e 13.269,6 m, respectivamente. A política integral resulta nos valores históricos conhecidos da calculadora VBA. Os testes de regressão verificam esses exemplos e a matriz os relaciona aos contratos matemáticos; não se deduz deles um percentual de precisão.

### 5.2 Validação do prazo

Para a meta de 10.000 m, início em 05/10/2026, velocidade de 25 cm/min, três fitas, 16 h/dia, desperdício de 3% e calendário sem sábados, domingos ou feriados, o resultado registrado é 23/10/2026. São 15 dias produtivos, produção líquida de 10.476 m e saldo de +476 m. A matriz também registra o teste de minimalidade: 14 dias são insuficientes e 15 são suficientes nesse cenário. A data é uma estimativa sob essas condições, não um compromisso de entrega.

### 5.3 Validação da viabilidade

No período de 01/10/2026 a 27/10/2026, com 19 dias produtivos e as condições das regressões, a meta de 10.000 m é atendida com três fitas: produção de 13.270 m, excedente de +3.270 m, mínimo de três fitas e adicional zero. Com duas fitas, a produção é 8.846 m, a meta não é atendida e o déficit é 1.154 m. O mínimo permanece três fitas, com uma fita adicional. Esses resultados demonstram a resposta determinística nos casos documentados, sem avaliar disponibilidade operacional de recursos.

### 5.4 Validação do calendário e dos feriados

A validação física registra o período de 01/12/2026 a 31/12/2026, velocidade de 28 cm/min, uma fita, 16 h/dia e desperdício de 3%. Com feriado não trabalhado, foram obtidos 22 dias produtivos e 5.737 m líquidos. Com feriado trabalhado, foram obtidos 23 dias produtivos e 5.997 m líquidos. A alteração da política impactou o resultado conforme esperado. A comparação se limita a esse cenário registrado, sem extrapolação para outros calendários.

**Tabela 2. Síntese dos casos documentados de validação.**

| Caso | Entrada principal | Resultado |
|---|---|---|
| Regressão 2 fitas | 25 cm/min; 16 h/dia; 19 dias; 3% | 9.120 m brutos; 273,6 m de desperdício; 8.846 m líquidos. |
| Regressão 3 fitas | Mesmas condições; 3 fitas | 13.680 m brutos; 410,4 m de desperdício; 13.270 m líquidos. |
| Prazo | Meta 10.000 m; início 05/10/2026; 3 fitas; 25 cm/min; 16 h/dia; 3% | 23/10/2026; 15 dias produtivos; 10.476 m; saldo +476 m. |
| Viabilidade 2 fitas | Meta 10.000 m; condições da regressão | Não atende; déficit 1.154 m; mínimo 3; adicional 1. |
| Viabilidade 3 fitas | Meta 10.000 m; condições da regressão | Atende; excedente +3.270 m; mínimo 3; adicional 0. |
| Feriado não trabalhado | Dezembro/2026; 28 cm/min; 1 fita; 16 h/dia; 3% | 22 dias produtivos; 5.737 m líquidos. |
| Feriado trabalhado | Mesmas condições; política de trabalho alterada | 23 dias produtivos; 5.997 m líquidos. |

### 5.5 Testes e validação física

Na versão analisada das fontes, foram identificados 118 métodos anotados com @Test em app/src/test. Essa contagem descreve as fontes e não o total executado por uma tarefa. A matriz cobre capacidade e `HALF_EVEN`, calendário e pontas, recorrência e deduplicação de feriados, estimativa, prazo, viabilidade, parsing, sanitização, limites e apresentação.

Separadamente, o gate local final de R6.8/R8.6 registra `testDebugUnitTest` com `BUILD SUCCESSFUL` e `assembleDebug` com `BUILD SUCCESSFUL`. Esses são resultados históricos documentados de execução e compilação; não foram repetidos nesta tarefa documental, e não se infere cobertura percentual a partir deles.

A validação física no Samsung SM-A066M reuniu as três jornadas, calendário, feriados, prazo e viabilidade. As rodadas registraram achados e posterior aprovação de cursor, cópia, limites de entrada, sanitização e feedback. A evidência é de funcionamento nos cenários verificados em um dispositivo; não demonstra compatibilidade com todos os aparelhos, satisfação de usuários ou ganhos produtivos mensurados.

## 6. Limitações e Trabalhos Futuros

### 6.1 Limitações atuais

O aplicativo opera localmente, sem backend, e os feriados cadastrados não persistem após o encerramento do processo. Velocidade, horas, fitas, desperdício e calendário dependem das informações fornecidas pelo usuário. O sistema não recebe dados de máquinas em tempo real e assume condições constantes na estimativa, sem modelar ocupações, liberações ou capacidade variável por intervalo.

Os resultados representam estimativas condicionais, não promessas operacionais. O MVP não utiliza inteligência artificial nem oferece PCP. A validação disponível reúne regressões, testes e observações físicas documentadas; não inclui avaliação quantitativa de economia de tempo, redução de desperdício, desempenho industrial ou estudo abrangente de usabilidade. O alcance bibliográfico desta versão é limitado aos metadados e resumos editoriais verificados.

### 6.2 Trabalhos futuros

As possibilidades de evolução incluem integração com ERP Web e PCP, consulta à disponibilidade de máquinas, modelagem de capacidade variável, persistência de feriados e histórico de estimativas. IA para análise de históricos e IoT para obtenção de dados produtivos são possibilidades posteriores, não funcionalidades atuais. Cada evolução exige requisitos, dados e validação próprios; não decorre automaticamente da implementação determinística do MVP.

## 7. Conclusão

O ProdTime implementa estimativa de produção em um período, estimativa da primeira data de atendimento de uma quantidade e avaliação determinística de viabilidade, com indicação do mínimo e do adicional de fitas. A separação entre interface móvel e domínio permite aplicar as mesmas regras de capacidade, calendário e feriados às três jornadas. Os casos documentados reproduzem os resultados históricos de duas e três fitas e apresentam respostas coerentes para prazo, viabilidade e política de feriados.

Os registros de testes e compilação bem-sucedidos, complementados pela validação física no Samsung SM-A066M, sustentam o funcionamento nos cenários analisados. A contribuição se limita à explicitação das regras e à implementação móvel rastreável. A operação local, a ausência de persistência de feriados e de dados em tempo real e a dependência das entradas restringem seu uso a estimativas sob premissas informadas. Não se demonstra ganho operacional quantitativo; integrações e recursos de planejamento ampliado permanecem trabalhos futuros.

## Referências

KARACAPILIDIS, Nikos I.; PAPPIS, Costas P. Production planning and control in textile industry: a case study. *Computers in Industry*, v. 30, n. 2, p. 127–144, 1996. DOI: [10.1016/0166-3615(96)00038-3](https://doi.org/10.1016/0166-3615(96)00038-3).

SERAFINI, Paolo; SPERANZA, M. Grazia. Production scheduling problems in a textile industry. *European Journal of Operational Research*, v. 58, n. 2, p. 173–190, 1992. DOI: [10.1016/0377-2217(92)90205-N](https://doi.org/10.1016/0377-2217(92)90205-N).

LAOBOONLUR, P.; HODGSON, T. J.; THONEY, K. A. Production scheduling in a knitted fabric dyeing and finishing process. *The Journal of The Textile Institute*, v. 97, n. 5, p. 391–399, 2006. DOI: [10.1533/joti.2006.0145](https://doi.org/10.1533/joti.2006.0145).

HODGE, George L.; GOFORTH ROSS, Kelly; JOINES, Jeff A.; THONEY, Kristin. Adapting lean manufacturing principles to the textile industry. *Production Planning & Control*, v. 22, n. 3, p. 237–247, 2011. DOI: [10.1080/09537287.2010.498577](https://doi.org/10.1080/09537287.2010.498577).
