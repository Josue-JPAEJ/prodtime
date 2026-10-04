# Arquitetura direcional

## Estado atual

O repositório contém o bootstrap Android nativo com Kotlin e Jetpack Compose e a tela padrão “Hello Android!”. Ainda não existem motor de cálculo, domínio funcional, navegação do produto ou arquitetura de apresentação específica do ProdTime.

## Direção pretendida

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
