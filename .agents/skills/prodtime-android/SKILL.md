# Skill: ProdTime Android

## Quando usar

Use esta Skill em tarefas que alterem Kotlin, Jetpack Compose, estrutura Android, estado de apresentação, navegação, UX mobile, acessibilidade, responsividade ou temas claro/escuro.

## Procedimento

1. Inspecione o código, o Gradle e as convenções existentes antes de propor componentes.
2. Preserve a API mínima, versões, dependências e configuração vigentes, salvo solicitação explícita.
3. Modele estados de UI explícitos e previsíveis; introduza ViewModel apenas quando ciclo de vida, coordenação de estado ou testabilidade justificarem.
4. Mantenha componentes coesos, conteúdo legível e ações guiadas, considerando tamanhos de toque, semântica, contraste, fontes ampliadas e modos claro/escuro.
5. Siga Material e Compose conforme o projeto real, evitando padrões ou bibliotecas ainda não adotados.
6. Valide a mudança no menor nível adequado e registre limitações.

## Limites obrigatórios

- Nunca coloque cálculo produtivo ou regra de negócio na UI.
- Separe apresentação e domínio sem criar camadas, packages ou abstrações antecipadamente.
- Evite overengineering, refatorações amplas e componentes genéricos sem uso concreto.
- Não invente navegação, arquitetura ou contratos futuros.
- Não altere a API mínima definida sem decisão explícita e documentada.
