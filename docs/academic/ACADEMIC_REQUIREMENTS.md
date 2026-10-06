# Requisitos acadêmicos e bibliográficos

## 1. Estado da verificação

A etapa R9.1 consolidou os requisitos conhecidos da entrega, confirmou a existência do formato/modelo SBC e validou metadados e escopos temáticos das quatro referências selecionadas por meio de páginas editoriais. A verificação alcança metadados e resumos editoriais, não a leitura integral dos artigos.

A base acadêmica está apta a orientar a primeira redação em R9.2, desde que todas as alegações permaneçam dentro das evidências documentadas. A redação não deve ser iniciada nesta etapa.

## 2. Requisitos conhecidos da entrega

- artigo em PDF;
- padrão/modelo SBC;
- arquivo PDF de até 5 MB;
- vídeo publicado no YouTube com aproximadamente 5 minutos;
- prazo geral do projeto: 24/10/2026.

Nenhum requisito adicional deve ser inferido sem fonte institucional.

## 3. Formato SBC

Fontes oficiais da Sociedade Brasileira de Computação confirmam a existência de formato/modelo para artigos. Eventos SBC disponibilizam modelos em LaTeX e, em diversas chamadas, também em MS Word; a submissão normalmente é realizada em PDF.

O limite de páginas não é uma característica inerente ao template SBC. Cada evento ou instituição define suas próprias condições. Portanto, o simples uso do modelo SBC não sustenta a afirmação de um número máximo de páginas.

## 4. Requisitos ainda não confirmados

**PENDENTE DE CONFIRMAÇÃO INSTITUCIONAL:** número de páginas específico desta entrega.

Não há evidência documental suficiente no repositório nem verificação pública oficial da instituição que determine esse limite. A pendência não bloqueia a redação inicial: R9.2 deve produzir texto conciso e ajustável quando a regra for confirmada.

## 5. Referências verificadas

### 5.1 Karacapilidis e Pappis (1996)

- **Metadados:** KARACAPILIDIS, Nikos I.; PAPPIS, Costas P. *Production planning and control in textile industry: a case study*. Computers in Industry, v. 30, n. 2, p. 127–144, 1996.
- **DOI:** [10.1016/0166-3615(96)00038-3](https://doi.org/10.1016/0166-3615(96)00038-3).
- **Tema sustentado pelo resumo:** planejamento e controle da produção têxtil por sistema baseado em modelo interativo, com foco em Master Production Scheduling e na complexidade de processos multifásicos, múltiplas unidades, horizontes e requisitos produtivos.
- **Papel no artigo:** **fundamentação central**, por contextualizar diretamente a complexidade do planejamento e controle em sistemas produtivos têxteis.
- **Limitações de uso:** não alegar leitura integral; não afirmar que propõe aplicação móvel equivalente, valida o ProdTime ou sustenta conclusões além do resumo editorial.

### 5.2 Serafini e Speranza (1992)

- **Metadados:** SERAFINI, Paolo; SPERANZA, M. Grazia. *Production scheduling problems in a textile industry*. European Journal of Operational Research, v. 58, n. 2, p. 173–190, 1992.
- **DOI:** [10.1016/0377-2217(92)90205-N](https://doi.org/10.1016/0377-2217(92)90205-N).
- **Tema sustentado pelo resumo:** problemas de scheduling em indústria têxtil, atraso máximo como função objetivo, máquinas/teares paralelos, análise de algoritmos e limites e heurística baseada em programação linear para o caso geral.
- **Papel no artigo:** **fundamentação central**, por sustentar a existência de problemas formais e complexos de programação da produção no setor.
- **Limitações de uso:** não alegar leitura integral; não afirmar que o ProdTime implementa esses algoritmos, resolve scheduling industrial geral ou foi validado pelo trabalho.

### 5.3 Laoboonlur, Hodgson e Thoney (2006)

- **Metadados:** LAOBOONLUR, P.; HODGSON, T. J.; THONEY, K. A. *Production scheduling in a knitted fabric dyeing and finishing process*. The Journal of The Textile Institute, v. 97, n. 5, p. 391–399, 2006. O ano adotado é o ano editorial do volume, ainda que a página atual da editora informe publicação online posterior.
- **DOI:** [10.1533/joti.2006.0145](https://doi.org/10.1533/joti.2006.0145).
- **Tema sustentado pelo resumo:** scheduling detalhado em tingimento e acabamento de malha, ambiente flexible job shop, setups dependentes de sequência, adaptação de algoritmo e abordagem baseada em family scheduling.
- **Papel no artigo:** **fundamentação complementar**, por exemplificar especificidades de scheduling em um processo têxtil distinto daquele tratado pelo ProdTime.
- **Limitações de uso:** não alegar leitura integral; não equiparar tingimento e acabamento à produção de fitas; não afirmar que a abordagem foi implementada ou valida o ProdTime.

### 5.4 Hodge et al. (2011)

- **Metadados:** HODGE, George L.; GOFORTH ROSS, Kelly; JOINES, Jeff A.; THONEY, Kristin. *Adapting lean manufacturing principles to the textile industry*. Production Planning & Control, v. 22, n. 3, p. 237–247, 2011.
- **DOI:** [10.1080/09537287.2010.498577](https://doi.org/10.1080/09537287.2010.498577).
- **Tema sustentado pelo resumo:** investigação de princípios lean aplicáveis à indústria têxtil, eliminação de desperdícios e atividades sem valor, entrevistas, visitas a plantas, estudos de caso e proposição de modelo de implementação.
- **Papel no artigo:** **fundamentação complementar**, por apoiar o contexto de redução de desperdício e melhoria sistemática de processos no setor têxtil.
- **Limitações de uso:** não alegar leitura integral; não afirmar que o ProdTime implementa integralmente lean, comprova redução mensurada de desperdício ou foi validado pelos estudos de caso.

## 6. Estrutura aprovada para a primeira versão

1. Título;
2. Autor / instituição, inicialmente como placeholders documentais;
3. Resumo;
4. Palavras-chave;
5. Abstract;
6. Keywords;
7. Introdução;
8. Fundamentação e Trabalhos Relacionados;
9. Metodologia;
10. Desenvolvimento do ProdTime;
11. Resultados e Validação;
12. Limitações e Trabalhos Futuros;
13. Conclusão;
14. Referências.

A nomenclatura ou divisão poderá ser adaptada em R9.4 se o template específico fornecido pela instituição exigir outra estrutura.

## 7. Evidências quantitativas permitidas

- regressão histórica: 2 fitas → 8.846 m; 3 fitas → 13.270 m;
- prazo: meta de 10.000 m → 23/10/2026, 15 dias produtivos, 10.476 m e saldo de +476 m;
- viabilidade com 3 fitas: meta atendida, excedente de +3.270 m, mínimo 3 e adicional 0;
- viabilidade com 2 fitas: meta não atendida, déficit de 1.154 m, mínimo 3 e adicional 1;
- feriado não trabalhado: 22 dias e 5.737 m;
- feriado trabalhado: 23 dias e 5.997 m;
- 118 métodos anotados com `@Test` identificados nas fontes atuais da suíte JVM;
- tarefa `testDebugUnitTest` concluída com `BUILD SUCCESSFUL` no gate final, como evidência separada da contagem de métodos.

## 8. Alegações proibidas por falta de evidência

- que os quatro artigos foram lidos integralmente;
- que qualquer referência valida diretamente o ProdTime;
- que os resumos sustentam conclusões além de seus temas confirmados;
- que o ProdTime resolve scheduling industrial geral;
- que “118 testes passaram”, pois 118 é a contagem de métodos anotados, não o total reportado por uma execução;
- que existe um limite específico de páginas antes da confirmação institucional;
- que houve economia de tempo, redução de desperdício ou outro ganho quantitativo não medido;
- que funcionalidades futuras, como ERP/PCP, persistência, IA ou IoT, estão entregues;
- que foi adotada metodologia científica formal não documentada.

## 9. Gate para R9.2

**APROVADO.** A redação da primeira versão pode começar em tarefa posterior porque:

- referências e limites de uso estão definidos;
- requisitos conhecidos e pendência institucional estão separados;
- estrutura, sequência argumentativa, metodologia e planos de resultados estão aprovados;
- figuras e tabelas mínimas estão planejadas;
- a pendência do número de páginas admite uma primeira versão concisa e ajustável.

R9.2 permanece pendente e não foi iniciada nesta etapa.
