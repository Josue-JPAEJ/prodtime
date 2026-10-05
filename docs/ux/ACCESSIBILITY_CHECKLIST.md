# Checklist de acessibilidade do ProdTime

# 1. Escopo

- **OK** — Revisão estática cobre Home, três fluxos produtivos e gestão de feriados, sem alteração das regras de domínio.
- **PENDENTE** — Confirmação final em aparelho físico está reservada ao R8.5, em conjunto com o R6.8.

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

# 6. Teclado

- **OK** — Inteiros solicitam teclado numérico, decimais solicitam teclado decimal, campos intermediários usam IME Next e o último campo usa IME Done; as ações movem o foco ou dispensam o teclado.
- **PENDENTE** — Teclado real, sequência de foco e alcance das ações por rolagem com o teclado aberto.

# 7. Semântica

- **OK** — O indicador visual da Home foi excluído da árvore semântica; switches agrupam rótulo e estado; seletores de tipo de feriado expõem o estado selecionado.
- **OK** — Cards informam textualmente “Produção estimada”, “Conclusão estimada”, “Meta atendida” ou “Meta não atendida”, sem cálculo duplicado na semântica.

# 8. Light mode

- **OK** — A revisão estática não encontrou cores de conteúdo fixas nas telas; componentes usam `MaterialTheme.colorScheme`.
- **PENDENTE** — Aparência e contraste visual no tema claro em aparelho.

# 9. Dark mode

- **OK** — A revisão estática confirmou tokens Material para cards normais, resultado, erro, texto secundário e divisores.
- **PENDENTE** — Aparência e contraste visual no tema escuro em aparelho.

# 10. Font scale

- **OK** — Botões principais não limitam mais o conteúdo a 52 dp e linhas de resumo distribuem espaço para rótulo e valor.
- **PENDENTE** — Testes visuais com escalas de fonte ampliadas.

# 11. Responsividade

- **OK** — Telas permanecem roláveis; cards e campos ocupam a largura disponível; não foram introduzidas larguras fixas nem dependência de aparelho específico.
- **PENDENTE** — Telas estreitas, textos longos, valores longos e rolagem com teclado no Samsung.

# 12. Validações automatizadas

- **PENDENTE** — A suíte JVM e a montagem do APK desta alteração não foram executadas no Cloud por limitações do wrapper, proxy e Android SDK registradas em `docs/process/VALIDATIONS.md`; permanece necessária nova execução local.
- **NÃO APLICÁVEL** — Não foi adicionada lógica nova de domínio que justificasse novos testes unitários.

# 13. Validações físicas pendentes

- **PENDENTE** — R6.5 e R6.6A em aparelho; teclado real; rolagem com teclado; fonte ampliada; tema claro; tema escuro; contraste visual; Back; touch targets; feriados; viabilidade; e descarte dos feriados após encerramento do processo.
- **PENDENTE** — R6.8 e R8.5 permanecem abertos até a execução no Samsung disponível para a validação final.
