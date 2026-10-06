# Preview SBC do artigo ProdTime

Fonte normativa: `../ARTICLE_FINAL.md`. Fonte diagramada: `main.tex`. PDF: `prodtime_sbc_preview.pdf`. As quatro imagens são referenciadas em `../figures/`, sem recorte, recompressão ou alteração dos arquivos originais; largura proporcional de 0,40 da área de texto, centralizadas. As quatro referências estão no ambiente `thebibliography` do próprio `main.tex`; não há BibTeX nem referência adicional.

## Template e proveniência

Cópia do modelo tradicional SBC mantida em [uefs/sbc-template-latex](https://github.com/uefs/sbc-template-latex), commit `0748264951381dfed3e3fea6a39287a64098fb82`. Foram copiados com conteúdo funcional original preservado, normalizando somente finais de linha e espaços finais em comentários, somente `sbc-template.sty` e `caption2.sty`, dependência exigida pelo modelo. O estilo identifica Jomi Hubner e Rafael Bordini, criação em junho de 2001 e atualização em março de 2005. O download direto do site SBC retornou HTTP 403; a cópia do repositório da UEFS foi obtida por Git. É o modelo tradicional para preview, sem afirmar aprovação institucional da Gran.

O modelo define A4, coluna única, margens superior 3,5 cm, inferior 2,5 cm e laterais 3 cm, Times 12 pt, títulos e legendas próprios e ausência de paginação visível. Nenhum parâmetro do estilo foi alterado para reduzir páginas. Tabelas adaptadas em `tabularx`, sem redução da fonte; fórmulas transcritas com quebras de linha, preservando sua ordem e significado. A bibliografia autor-data é preservada sem rótulos numéricos.

## Compilar

Requer TeX Live com pdfLaTeX, fontes PSNFSS, geometry, titlesec, Babel com locale português, graphicx, booktabs, tabularx, array, hyperref e url. O `brazil.ldf` não estava instalado; a importação do locale português pelo Babel disponível permite compilar sem alterar o estilo. Não é necessário Pandoc para recompilar a fonte pronta.

A partir de qualquer diretório, executar:

```sh
sh docs/academic/submission/build.sh
```

O script executa duas passagens de pdfLaTeX em diretório temporário e copia somente o PDF para o repositório. Para conferir páginas/tamanho, usar `pdfinfo docs/academic/submission/prodtime_sbc_preview.pdf`. O arquivo é um preview para revisão humana, não a entrega institucional definitiva. Metadados de data/identificador podem variar em recompilações.

## Estado editorial R9.4C

Após aprovação humana da estrutura e das figuras em R9.4B, Resumo e Abstract foram condensados em R9.4C e ocupam dez linhas cada na primeira página. A unidade de Desperdício na Tabela 1 foi corrigida para `\%` na fonte LaTeX. O modelo, as imagens e o restante do artigo permanecem preservados.

R9.4 concluído editorialmente; R9 concluído quanto ao conteúdo e artigo produzido. O limite institucional de páginas continua sem confirmação; poderá exigir adequação futura. Estado e evidências atuais em `../ARTICLE_SUBMISSION.md`. R10/R11 não iniciados.
