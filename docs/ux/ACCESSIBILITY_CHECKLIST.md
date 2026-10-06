# Checklist de acessibilidade do ProdTime

# 1. Escopo

- **OK** — Revisão estática cobre Home, três fluxos produtivos e gestão de feriados, sem alteração das regras de domínio.
- **OK** — Checklist encerrado após as rodadas físicas e o gate final conjunto R6.8/R8.6 no Samsung SM-A066M.

# 2. Navegação

- **OK** — Ações da Home e ações “Voltar” possuem texto compreensível; o Back do sistema é tratado fora da Home.
- **OK** — Navegação dos três fluxos e retorno foram validados no Samsung.

# 3. Touch targets

- **OK** — Botões, switches, campos de data, cards clicáveis e remoção de feriado usam componentes Material com alvo mínimo próprio; ações principais usam altura mínima, sem altura máxima fixa.
- **OK** — Alvos Material e interação por toque foram aprovados na validação física.

# 4. Texto e tipografia

- **OK** — Textos usam a tipografia Material e não possuem altura fixa; rótulos e valores dos resumos podem quebrar em áreas flexíveis independentes.
- **OK** — Tipografia flexível e responsividade foram encerradas conforme as evidências do gate.

# 5. Campos e erros

- **OK** — Campos têm rótulo visível, unidade ou erro textual próximo e estado de erro do Material; erros gerais e de período também são textuais e não dependem somente de cor.
- **OK** — Entradas decimais continuam aceitando vírgula e ponto e são convertidas diretamente para `BigDecimal`.
- **OK** — Ao receber foco pelo IME Next, o campo posiciona a seleção no fim; durante a edição, a seleção manual não é sobrescrita.
- **OK** — Colagem remove somente espaços Unicode e caracteres invisíveis de formatação; texto alfabético permanece visível e inválido.
- **OK** — A mensagem de um campo já invalidado é removida assim que esse campo é editado, sem ocultar erros dos demais.

# 6. Teclado

- **OK** — Inteiros solicitam teclado numérico, decimais solicitam teclado decimal, campos intermediários usam IME Next e o último campo usa IME Done; as ações movem o foco ou dispensam o teclado.
- **OK** — Teclado/foco, cursor via Next e rolagem foram aprovados fisicamente.

# 7. Semântica

- **OK** — O indicador visual da Home foi excluído da árvore semântica; switches agrupam rótulo e estado; seletores de tipo de feriado expõem o estado selecionado.
- **OK** — Cards informam textualmente “Produção estimada”, “Conclusão estimada”, “Meta atendida” ou “Meta não atendida”, sem cálculo duplicado na semântica.
- **OK** — A ação de cópia usa `IconButton`, alvo Material e descrição “Copiar resultado”; o resultado de viabilidade permanece identificado por texto além da cor.

# 8. Light mode

- **OK** — A revisão estática não encontrou cores de conteúdo fixas nas telas; componentes usam `MaterialTheme.colorScheme`.
- **OK** — Tema claro encerrado conforme as evidências estáticas e físicas registradas.

# 9. Dark mode

- **OK** — Cards neutros e de viabilidade usam pares de container/content do Material; labels derivam da cor de conteúdo do card e preservam contraste semântico.
- **OK** — Tema escuro aprovado fisicamente e sustentado pelo uso dos tokens Material.

# 10. Font scale

- **OK** — Botões principais não limitam mais o conteúdo a 52 dp e linhas de resumo distribuem espaço para rótulo e valor.
- **OK** — Escala de fonte e layouts flexíveis encerrados no gate de responsividade.

# 11. Responsividade

- **OK** — Telas permanecem roláveis; cards e campos ocupam a largura disponível; não foram introduzidas larguras fixas nem dependência de aparelho específico.
- **OK** — Responsividade, valores, rolagem e teclado aprovados no Samsung.

# 12. Validações automatizadas

- **OK** — Testes JVM e montagem debug foram aprovados localmente no gate final; consulte `docs/process/VALIDATIONS.md`.
- **OK** — A geração do texto copiado e as classificações produtivas de calendário possuem testes JVM puros, sem teste Android desnecessário do Clipboard.

# 13. Validações físicas finais

- **OK** — Rodada 1 no Samsung SM-A066M confirmou Home, três cálculos, CRUD e compartilhamento de feriados, tema escuro, rolagem e integração do feriado com o cálculo.
- **OK** — A rodada física anterior aprovou cursor via Next, cores de viabilidade, cópia, resumos de calendário e remoção da contagem de feriados dos formulários.
- **OK** — Limites, colagem inválida, sanitização de espaços/invisíveis e limpeza individual das mensagens de erro foram aprovados no gate R8.6.

# 14. Identidade R10.1 — validação separada

As aprovações R6/R8 acima são históricas e não substituem validação da nova Home/Sobre.

- **OK ESTÁTICO/BUILD** — “Sobre o ProdTime” tem rótulo claro e acesso abaixo de feriados; nova rota utiliza o Back existente e oferece “Voltar”.
- **OK ESTÁTICO/BUILD** — Título da tela com semântica de heading; demais textos e grupos com rótulos; contato selecionável; conteúdo rolável e sem altura fixa.
- **OK ESTÁTICO/BUILD** — Tipografia e pares de cores Material em light/dark/dinâmico, TextButton com alvo Material; nome/descrição/autoria e aviso estimativo em recursos de string.
- **OK PRÉVIA GRÁFICA** — Ícone circular, quadrado arredondado e monocromático conferidos na prévia vetorial, sem recorte do símbolo central; isso não é execução em launcher Android.
- **COMPILADO, NÃO EXECUTADO** — `ProdTimeIdentityTest` cobre acesso, conteúdo essencial, rolagem, retorno por “Voltar” e Back. APK de teste instrumentado gerado; sem aparelho/emulador nesta sessão.
- **PENDENTE LOCAL/FÍSICO** — Instalar o APK, conferir launcher real e ícones temáticos; Home e três acessos preservados; abrir Sobre; tema claro/escuro; fonte ampliada; tela pequena/rolagem; TalkBack e foco; ambas as ações de retorno.

Não é alegada nova validação física nem renderização executada dos previews Compose.
