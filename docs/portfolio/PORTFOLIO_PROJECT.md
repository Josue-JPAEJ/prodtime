# ProdTime

## Resumo

Aplicativo Android para estimar capacidade, prazo e viabilidade de metas na produção de fitas têxteis. Funciona localmente, com regras determinísticas e parâmetros informados pelo usuário.

<img src="../branding/launcher-preview.png" alt="Prévia gráfica do ícone ProdTime nas máscaras circular, arredondada e temática" width="400">

Prévia gráfica da identidade relógio + fita, não captura de launcher. [Conceito e recursos da identidade](../branding/BRAND_IDENTITY.md).

## Problema

Estimativas manuais dependiam de profissionais experientes e concentravam conhecimento sobre velocidade, fitas simultâneas, jornada, desperdício e calendário. Uma calculadora VBA desenvolvida por mim apoiava essas respostas. O ProdTime formaliza essa lógica em uma aplicação móvel independente, sem expor dados empresariais ou detalhes confidenciais do legado.

## Minha contribuição

Sou autor da calculadora de origem e do projeto ProdTime. Conduzi a definição do problema e do escopo, a formalização das regras e a evolução das jornadas móveis. A implementação e a revisão tiveram assistência de ferramentas de desenvolvimento; a validação local/física e a revisão humana do artigo foram realizadas por mim conforme os registros do projeto. Essa contribuição abrange especificação, acompanhamento das decisões, conferência dos resultados e documentação acadêmica, sem pressupor escrita manual exclusiva de todo o código.

## Solução

Três jornadas usam os mesmos motores de capacidade, calendário e feriados. Os resultados expõem unidades e premissas, permitindo conferir produção líquida, prazo, saldo e condições de atendimento da meta.

## Principais funcionalidades

- **Quanto consigo produzir?** Capacidade líquida de um período, com produção bruta, desperdício e dias produtivos.
- **Quando vou terminar?** Primeira data suficiente para a meta, a partir da data inicial; produção acumulada e saldo.
- **Verificar uma meta:** atendimento, déficit/excedente, mínimo e adicional de fitas.
- **Calendário/feriados:** pontas do período, sábados, domingos, feriados anuais e datas específicas.
- **Interação:** temas claro/escuro, fonte ampliada, mensagens por campo, sanitização de entradas e cópia de resultados.

## Stack

Android nativo, Kotlin, Jetpack Compose, Material 3, BigDecimal, java.time, Gradle Wrapper e JUnit 4.

## Arquitetura

A UI valida entradas, coordena o estado e apresenta resultados. O domínio Kotlin puro não depende de Android ou Compose. Capacidade, calendário e resolução de feriados são compostos pelas calculadoras de estimativa, prazo e viabilidade.

A navegação é coordenada por estado Compose. Os feriados ficam em uma coleção compartilhada durante a sessão; não existe camada de persistência. [Arquitetura documentada](../architecture/ARCHITECTURE.md).

## Desafios técnicos

- Preservar regressões do VBA com duas fronteiras explícitas de arredondamento HALF_EVEN.
- Classificar cada data uma vez, sem dupla subtração em colisões de feriados e fins de semana.
- Encontrar a primeira data suficiente e o menor número suficiente de fitas sem duplicar a fórmula.
- Tratar espaços/caracteres invisíveis, limites numéricos, cursor, teclado e fonte ampliada sem mudar regras produtivas.

## Testes e validação

As fontes JVM têm **118 métodos anotados com @Test**. Essa contagem não representa cobertura percentual nem execução automática de todos os métodos em qualquer comando. Os registros incluem testes e builds bem-sucedidos; a primeira execução Cloud de R10.1 registrou 118 testes sem falhas, erros ou ignorados.

Houve validação física das três jornadas e dos refinamentos de interação no Samsung SM-A066M. A aprovação física da identidade foi informada pelo autor e registrada em R10.2. O teste instrumentado de identidade foi compilado; sua execução em dispositivo não está registrada. Evidências na [matriz de regressão](../testing/REGRESSION_MATRIX.md) e em [validações](../process/VALIDATIONS.md).

## Resultados demonstráveis

Condições comuns: 25 cm/min, 16 h/dia, desperdício de 3%, sem sábados/domingos/feriados. Produção e viabilidade usam o período inclusivo 01/10/2026–27/10/2026, com 19 dias produtivos. Prazo usa início em 05/10/2026 e três fitas.

| Cenário | Resultado documentado |
|---|---|
| Produção com 2 fitas | 8.846 m líquidos. |
| Produção com 3 fitas | 13.270 m líquidos. |
| Prazo para meta de 10.000 m | 23/10/2026; 15 dias produtivos; 10.476 m; saldo +476 m. |
| Meta de 10.000 m com 2 fitas | Déficit 1.154 m; mínimo 3 fitas; adicional 1. |

Capturas reais: [Home](../academic/figures/figura_1_home_prodtime.jpg), [produção](../academic/figures/figura_2_estimativa_13270m.jpg), [prazo](../academic/figures/figura_3_prazo_23102026.jpg) e [viabilidade](../academic/figures/figura_4_viabilidade_deficit_1154m.jpg). São anteriores à identidade de R10.1, sem o acesso a Sobre na Home; não foram alteradas. Os valores demonstram os cenários avaliados, não ganhos operacionais mensurados.

## Limitações

Operação local, sem backend ou dados de máquinas em tempo real. Feriados não persistem após o encerramento do processo. A estimativa assume parâmetros constantes e não garante disponibilidade real. O aplicativo não é ERP, não oferece PCP e não utiliza IA. Não há estudo amplo com usuários ou medição de economia, produtividade industrial ou redução de desperdício.

## Próximos passos

A gravação/publicação do vídeo e a release formal v1.0.0 ainda estão pendentes. Persistência, histórico, integração com ERP Web/PCP, disponibilidade de máquinas, capacidade variável, IA e IoT são possibilidades futuras, fora da versão atual e sujeitas a requisitos e validação próprios.

## Contexto acadêmico

Projeto Integrador de **Josué Paulo Alexandrina**, no curso **Análise e Desenvolvimento de Sistemas (ADS)**, **Gran Faculdade**.

O [artigo produzido no formato SBC](../academic/submission/prodtime_sbc_preview.pdf) contém 11 páginas e está abaixo de 5 MB. Eventual adequação ao limite institucional depende de regra ainda não informada. A [fonte final](../academic/ARTICLE_FINAL.md) reúne o texto e as referências, consultadas nos limites dos metadados/resumos editoriais, sem alegação de leitura integral da literatura.

Este texto está preparado para reutilização; página pública de portfólio e vídeo não foram publicados nesta etapa. A visibilidade do repositório não foi confirmada, portanto não se promete acesso público a seus arquivos.
