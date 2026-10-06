# Checklist de acessibilidade do ProdTime

# 1. Escopo

- **OK** — Revisão estática cobre Home, três fluxos produtivos e gestão de feriados, sem alteração das regras de domínio.
- **PENDENTE** — A rodada física 1 foi executada; a confirmação final após os refinamentos permanece reservada ao R8.5, em conjunto com o R6.8.

# 2. Navegação

- **OK** — Ações da Home e ações “Voltar” possuem texto compreensível; o Back do sistema é tratado fora da Home.
- **PENDENTE** — Navegação por toque e Back do sistema no Samsung.

# 3. Touch targets

- **OK** — Botões, switches, campos de data, cards clicáveis e remoção de feriado usam componentes Material com alvo mínimo próprio; ações principais usam altura mínima, sem altura máxima fixa.
- **PENDENTE** — Medição e conforto dos alvos de toque no aparelho.

# 4. Texto e tipografia

- **OK** — Textos usam a tipografia Material e não possuem altura fixa; rótulos e valores dos resumos podem quebrar em áreas flexíveis independentes.
- **PENDENTE** — Inspeção visual com fontes ampliadas no aparelho.

# 5. Campos e erros

- **OK** — Campos têm rótulo visível, unidade ou erro textual próximo e estado de erro do Material; erros gerais e de período também são textuais e não dependem somente de cor.
- **OK** — Entradas decimais continuam aceitando vírgula e ponto e são convertidas diretamente para `BigDecimal`.
- **OK** — Ao receber foco pelo IME Next, o campo posiciona a seleção no fim; durante a edição, a seleção manual não é sobrescrita.

# 6. Teclado

- **OK** — Inteiros solicitam teclado numérico, decimais solicitam teclado decimal, campos intermediários usam IME Next e o último campo usa IME Done; as ações movem o foco ou dispensam o teclado.
- **PENDENTE** — Confirmar no teclado real a correção da posição inicial do cursor e o alcance das ações por rolagem.

# 7. Semântica

- **OK** — O indicador visual da Home foi excluído da árvore semântica; switches agrupam rótulo e estado; seletores de tipo de feriado expõem o estado selecionado.
- **OK** — Cards informam textualmente “Produção estimada”, “Conclusão estimada”, “Meta atendida” ou “Meta não atendida”, sem cálculo duplicado na semântica.
- **OK** — A ação de cópia usa `IconButton`, alvo Material e descrição “Copiar resultado”; o resultado de viabilidade permanece identificado por texto além da cor.

# 8. Light mode

- **OK** — A revisão estática não encontrou cores de conteúdo fixas nas telas; componentes usam `MaterialTheme.colorScheme`.
- **PENDENTE** — Aparência e contraste visual no tema claro em aparelho.

# 9. Dark mode

- **OK** — Cards neutros e de viabilidade usam pares de container/content do Material; labels derivam da cor de conteúdo do card e preservam contraste semântico.
- **PENDENTE** — Aparência e contraste visual no tema escuro em aparelho.

# 10. Font scale

- **OK** — Botões principais não limitam mais o conteúdo a 52 dp e linhas de resumo distribuem espaço para rótulo e valor.
- **PENDENTE** — Testes visuais com escalas de fonte ampliadas.

# 11. Responsividade

- **OK** — Telas permanecem roláveis; cards e campos ocupam a largura disponível; não foram introduzidas larguras fixas nem dependência de aparelho específico.
- **PENDENTE** — Telas estreitas, textos longos, valores longos e rolagem com teclado no Samsung.

# 12. Validações automatizadas

- **PENDENTE** — Consultar em `docs/process/VALIDATIONS.md` os resultados Cloud desta alteração e repetir a validação local antes da rodada física final.
- **OK** — A geração do texto copiado e as classificações produtivas de calendário possuem testes JVM puros, sem teste Android desnecessário do Clipboard.

# 13. Validações físicas pendentes

- **OK** — Rodada 1 no Samsung SM-A066M confirmou Home, três cálculos, CRUD e compartilhamento de feriados, tema escuro, rolagem e integração do feriado com o cálculo.
- **PENDENTE** — R6.8 e R8.5 permanecem abertos até nova rodada no Samsung para validar cursor, cópia, cores semânticas, resumos de calendário e remoção da contagem nos formulários.
