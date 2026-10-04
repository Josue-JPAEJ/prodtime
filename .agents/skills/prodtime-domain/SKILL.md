# Skill: domínio ProdTime

## Quando usar

Use esta Skill ao trabalhar com regras produtivas, fórmulas, precisão, unidades, datas, calendário produtivo, desperdício, quantidade de fitas, velocidade, capacidade, prazo ou recomendações.

## Procedimento obrigatório

1. Classifique cada informação como fato conhecido, hipótese, referência histórica ou questão aberta.
2. Não invente fórmula. Investigue a fonte e valide a especificação antes de implementar cálculos.
3. Use os casos históricos do legado como referências de regressão, não como prova automática de uma fórmula.
4. Explicite grandezas e unidades em nomes, contratos e documentação; não misture centímetros, metros, minutos, horas, percentuais e datas implicitamente.
5. Documente precisão, estratégia de arredondamento e momento em que o arredondamento ocorre.
6. Trate bordas relevantes: zero, valores negativos, intervalos inválidos, percentuais-limite, dias não produtivos e ausência de capacidade.
7. Mantenha o cálculo determinístico, reproduzível e independente da UI e do Compose.
8. Submeta mudanças de regra a testes unitários e registre a evidência de validação.

## Recomendações

Recomendações devem derivar de regras determinísticas validadas, declarar suas premissas e não ser apresentadas como otimização inteligente. IA e histórico operacional pertencem à visão futura.
