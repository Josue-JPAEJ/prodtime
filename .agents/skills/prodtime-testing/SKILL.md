# Skill: testes ProdTime

## Quando usar

Use esta Skill ao definir, implementar ou revisar testes, critérios de regressão e validações manuais do ProdTime.

## Estratégia

### Testes unitários de domínio

- Priorize-os para fórmulas, unidades, precisão, arredondamento, datas, calendário, entradas inválidas e fronteiras.
- Exija resultados determinísticos e cubra os casos históricos VBA somente depois que a fórmula real estiver especificada e validada.
- Não ajuste uma fórmula apenas para coincidir com exemplos aproximados.

### Testes Android/UI

- Use-os para comportamento específico do Android ou Compose: interação, navegação, renderização, semântica e integração de estado.
- Não exija testes instrumentados quando testes unitários rápidos cobrirem adequadamente o risco.

### Validação manual

- Use aparelho físico quando apropriado para fluxo, teclado, legibilidade, acessibilidade, orientação, responsividade e modos claro/escuro.
- Registre aparelho/ambiente, passos, resultado e limitações. Validação manual complementa, mas não substitui, testes automatizados de domínio.

## Evidência

Registre comando ou procedimento, resultado real, data/etapa e riscos residuais. Nunca declare uma validação executada sem evidência.
