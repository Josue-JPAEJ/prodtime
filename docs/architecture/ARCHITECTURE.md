# Arquitetura direcional

## Estado atual

O aplicativo Android nativo usa Kotlin e Jetpack Compose. A UI oferece Home, os três fluxos de cálculo e gestão de feriados; a navegação é coordenada por estado Compose. O domínio Kotlin puro contém os motores de capacidade, calendário, resolução de feriados, estimativa, prazo e viabilidade, sem dependência de Android. As definições de feriado são mantidas em uma coleção compartilhada somente durante a sessão.

## Estrutura atual

```text
Compose / UI
      ↓
estado / apresentação
      ↓
domínio
      ↓
motor de cálculo puro
```

- **Compose/UI:** coleta entradas e apresenta estados e resultados; não calcula produção.
- **Estado/apresentação:** coordena a interação e traduz resultados do domínio para a UI.
- **Domínio:** representa regras, grandezas e contratos validados sem dependência de Compose.
- **Motor de cálculo puro:** executa cálculos determinísticos, testáveis e independentes do Android sempre que possível.

## Princípios

- O motor matemático deve permanecer independente do Compose.
- Regras de negócio não devem residir em componentes visuais.
- Unidades, precisão, arredondamento e calendário devem ter contratos explícitos antes da implementação.
- Packages, classes, ViewModels, repositories ou outras camadas serão criados somente quando necessários para uma entrega concreta.
- A estrutura deve crescer incrementalmente; este documento não afirma a existência de componentes ainda não implementados.

## Persistência de feriados

Por decisão do R6.6B, persistência é evolução futura, fora do MVP acadêmico. Não existe repository, banco, arquivo nem dependência de armazenamento; encerrar o processo descarta a coleção. Essa decisão não altera os contratos matemáticos ou de calendário.
