# Ficha factual do ProdTime

Referência para reutilizar números, stack e estado sem ampliar as alegações. Atualizada em **07/10/2026 (America/Sao_Paulo)** no fechamento documental R11.4.

| Campo | Fato registrado |
|---|---|
| Nome | ProdTime |
| Descrição | Planejamento rápido de capacidade e prazo para produção de fitas têxteis. |
| Autor | Josué Paulo Alexandrina |
| Curso | Análise e Desenvolvimento de Sistemas (ADS) |
| Instituição | Gran Faculdade |
| Contexto | Projeto Integrador |
| Plataforma | Android nativo |
| Package/applicationId/namespace | br.com.prodtime |
| Minimum SDK | API 26 |
| Target SDK / Compile SDK | API 36 / API 36 |
| Versão atual / código | 1.0.0 / 1 |
| Stack | Kotlin; Jetpack Compose; Material 3; BigDecimal; java.time; Gradle; JUnit 4 |
| Funcionalidades | Produção; prazo; viabilidade; calendário/feriados; cópia de resultados; Sobre |
| Feriados | Anuais ou de data específica; coleção compartilhada em memória na sessão |
| Testes nas fontes | 118 métodos @Test JVM; contagem estática, sem cobertura percentual inferida |
| Execução histórica Cloud R10.1 | 118 testes nos relatórios XML da primeira execução; zero falhas/erros/ignorados |
| Build e testes | Gates anteriores registram BUILD SUCCESSFUL; tarefas atualizadas não implicam reexecução da suíte |
| Teste instrumentado de identidade | APK compilado; execução em dispositivo não registrada |
| Validação física | Samsung SM-A066M; três jornadas e refinamentos de UX nos cenários documentados |
| Identidade física | Ícone, Home e Sobre aprovados conforme relato do autor registrado em R10.2 |
| Artigo | Formato SBC; 11 páginas; PDF de 355.967 bytes, abaixo de 5 MB |
| Requisito institucional pendente | Limite exato de páginas ainda não informado; eventual ajuste do artigo depende dessa regra |
| Estado do produto | MVP funcional concluído |
| Estado de apresentação | R10 concluído; vídeo publicado conforme relato do autor |
| Vídeo | https://youtu.be/UrrcNwLq76w; não listado; aproximadamente 5min48s; gravado/revisado/publicado conforme relato do autor |
| Release formal | 1.0.0 publicada; tag v1.0.0; [GitHub Release](https://github.com/Josue-JPAEJ/prodtime/releases/tag/v1.0.0) com APK acadêmico e checksum, conforme confirmação do autor em R11.4 |
| SHA-256 público do APK | 1162ac6b8f99430cb818460537eb406963fb07b43d5a609bd81a89f675fdeeb5; informado pelo autor, distinto do build local histórico |
| Publicação dos materiais | Textos preparados; nenhum post LinkedIn ou página de portfólio publicado nesta tarefa |
| Acesso ao repositório | Público; publicação e verificação pública confirmadas pelo autor em R11.4 |

## Casos demonstráveis

Condições comuns: 25 cm/min, 16 h/dia, desperdício de 3%, sem fins de semana/feriados. Para produção/viabilidade, período inclusivo 01/10/2026–27/10/2026 e 19 dias produtivos; para prazo, início 05/10/2026 e três fitas.

| Caso | Resultado |
|---|---|
| Produção com 2 fitas | 8.846 m líquidos |
| Produção com 3 fitas | 13.270 m líquidos; 13.680 m brutos; desperdício 410,4 m |
| Prazo para 10.000 m | 23/10/2026; 15 dias produtivos; 10.476 m; saldo +476 m |
| Viabilidade de 10.000 m com 2 fitas | Não atende; déficit 1.154 m; mínimo 3 fitas; adicional 1 |

Não extrapolar esses casos para taxas de acerto industrial, produtividade, economia ou redução mensurada de desperdício.

## Limites de comunicação

- Resultados determinísticos condicionados aos parâmetros, sem promessa operacional.
- Sem backend, persistência de feriados ou dados de máquinas em tempo real.
- Sem IA, ERP ou PCP na versão atual; integração com ERP Web/PCP, IA e IoT são possibilidades futuras.
- Não atribuir número de usuários, cobertura percentual ou métricas comerciais inexistentes.
- Não divulgar código proprietário, nomes de clientes ou dados empresariais do legado.
- Autoria do projeto e da calculadora original é do autor; implementação/revisão tiveram assistência de ferramentas de desenvolvimento.
- Referências acadêmicas contextualizam o tema; consulta limitada a metadados/resumos editoriais, sem leitura integral declarada.
- Imagens reais existentes são anteriores a R10.1; prévia de launcher é gráfica, não screenshot.

## Fontes de conferência

- [README](../../README.md), [escopo](../product/PROJECT_SCOPE.md) e [arquitetura](../architecture/ARCHITECTURE.md).
- [Matriz de regressão](../testing/REGRESSION_MATRIX.md) e [histórico de validações](../process/VALIDATIONS.md).
- [Identidade](../branding/BRAND_IDENTITY.md) e [artigo final](../academic/ARTICLE_FINAL.md).
- [PDF SBC](../academic/submission/prodtime_sbc_preview.pdf), [narração do vídeo](../video/NARRATION.md) e [rollout](../process/ROLLOUT.md).
