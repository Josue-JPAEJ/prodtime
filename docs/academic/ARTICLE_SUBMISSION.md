# Preparação para submissão do artigo

## Estado documental

Fonte textual consolidada: [ARTICLE_FINAL.md](ARTICLE_FINAL.md). R9.4 está **em andamento**, com autoria e texto consolidados, mas sem integração das capturas nem aplicação do template. O nome do arquivo identifica a fonte destinada à versão final; não certifica um artigo diagramado ou pronto para envio. O rascunho e os registros anteriores foram preservados.

Autor: Josué Paulo Alexandrina. Instituição: Gran Faculdade. Curso: Análise e Desenvolvimento de Sistemas (ADS). E-mail: josue_jpaej@hotmail.com. Dados fornecidos pelo autor nesta tarefa.

## Figuras principais

A seleção temática segue a solicitação do autor; ainda não foi possível escolher os melhores arquivos, porque nenhuma captura real está acessível no repositório ou nos anexos desta sessão. Não foram criadas imagens, referências a arquivos inexistentes ou declarações de integração concluída. Os marcadores em `ARTICLE_FINAL.md` explicitam essa pendência.

| Figura / posição | Captura a integrar e conferência necessária | Justificativa |
|---|---|---|
| 1 / seção 4.1 | Tela inicial real do ProdTime, com acesso às três jornadas. | Situa a organização do aplicativo. |
| 2 / seção 4.4 | “Quanto consigo produzir?”: 13.270 m líquidos, 19 dias produtivos, 13.680 m brutos, desperdício 410,4 m; 01/10/2026 a 27/10/2026. Condições registradas: 25 cm/min, 3 fitas, 16 h/dia, 3%, sem sábados/domingo/feriados. | Ilustra a estimativa e a regressão de três fitas. |
| 3 / seção 4.5 | “Quando vou terminar?”: 23/10/2026; meta 10.000 m; 15 dias produtivos; produção 10.476 m; saldo +476 m. Início 05/10/2026, 25 cm/min, 3 fitas, 16 h/dia, 3%, sem sábados/domingo/feriados. | Mostra a data calculada e o atendimento da meta. |
| 4 / seção 4.6 | “Verificar uma meta”: 2 fitas, produção 8.846 m, meta 10.000 m, déficit 1.154 m, mínimo 3 e adicional 1. Período 01/10/2026 a 27/10/2026; demais condições da regressão. | Explica a indicação determinística de fitas adicionais. |

As legendas curtas e as chamadas foram preparadas na fonte do artigo. Os valores decorrem dos registros de validação física, não da inspeção de imagens ausentes. Telas de feriados, comparação de políticas e calculadora VBA permanecem opcionais; não foram acrescentadas porque o conjunto principal já contempla as três jornadas e o limite de páginas não está confirmado.

Ao receber os arquivos, conferir visualmente todos os valores, escolher as capturas mais legíveis e arquivar os originais em diretório documental. Inserir as imagens correspondentes nos quatro pontos marcados, preservando proporções, resultados e vínculo com as evidências. Não alterar valores exibidos nem simular screenshots. A legenda de origem deve refletir a proveniência efetivamente confirmada.

## Template e saída de submissão

Os requisitos registrados são PDF no padrão SBC e arquivo de até 5 MB. O número de páginas depende de confirmação institucional; não foi adotado um limite arbitrário. Pandoc, pdfLaTeX, XeLaTeX e LuaLaTeX estão disponíveis. As consultas `kpsewhich sbc-template.sty` e `kpsewhich sbc-template.cls` não localizaram o template, e nenhum modelo institucional foi fornecido no repositório/anexos.

Não foi gerado PDF ou Word: faltam as imagens reais e o template aplicável para produzir e conferir a saída final. A limitação atual é de insumos de submissão, não de ausência de compiladores. `ARTICLE_FINAL.md` é a fonte organizada e ajustável para transferência ao modelo confirmado; nenhum layout genérico foi apresentado como SBC.

## Checklist de encerramento de R9.4

- [x] Dados reais aplicados ao rascunho e à fonte consolidada.
- [x] Título aprovado, Resumo/Abstract, palavras-chave, sete seções, duas tabelas e quatro referências preservados.
- [x] Seleção temática das quatro figuras, posições, chamadas e legendas preparada.
- [x] Limites acadêmicos, contrato de prazo e distinção entre contagem estática e execuções históricas preservados.
- [ ] Receber, inspecionar e integrar as quatro capturas reais.
- [ ] Confirmar o template aplicável e o número de páginas institucional.
- [ ] Aplicar o modelo, ajustar paginação e conferir legibilidade de figuras, tabelas, fórmulas, referências e links.
- [ ] Gerar e revisar visualmente o PDF; conferir tamanho de até 5 MB e requisitos institucionais.

**Gate R9.4: NÃO — base textual preparada, entrega acadêmica final ainda pendente.** R9 permanece em andamento; R10 e R11 não iniciados. Nenhum Gradle, teste Android ou nova validação física foi executado.
