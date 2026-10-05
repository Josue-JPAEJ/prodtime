# Questões abertas do domínio

Este arquivo contém apenas decisões reais do novo ProdTime. Fórmulas e comportamentos já respondidos pelo VBA estão especificados em `CALCULATION_SPEC.md`.

## P1 — necessário antes da UI/calendário

1. **Colisões de calendário:** no R2, um mesmo dia excluído por ser feriado, fim de semana e/ou ponta deve contar zero vezes apenas uma vez, corrigindo as subtrações múltiplas do legado?
2. **Pontas e categorias:** qual precedência deve existir entre “incluir data inicial/final” e a exclusão de sábado, domingo ou feriado?
3. **Feriados:** quais localidades/fontes serão suportadas, como feriados móveis serão modelados e duplicatas serão rejeitadas?
4. **Contrato das unidades:** confirmar com o responsável de produto que o peso bruto e a tara são kg/caixa e que `pFita` é massa linear em g/m; definir os rótulos inequívocos da UI.
5. **Limites e mensagens:** definir, se necessários, limites operacionais máximos para o motor de capacidade; definir intervalos válidos para pesos, massa linear e datas e as mensagens da UI. Os limites inferiores de velocidade, fitas, horas, dias, desperdício e meta já pertencem ao contrato aprovado do R1.2.

## P2 — pode ser decidido posteriormente

1. Os defaults históricos — 3%, 16 h/dia, 1 fita e 28 cm/min — serão mantidos, alterados ou removidos?
2. A apresentação deve mostrar metros fracionários para produção, desperdício, saldo e conversão de peso, ou somente valores inteiros?
3. Deve existir limite operacional inferior ao limite técnico do tipo numérico para evitar estimativas irreais?
4. Além do calendário fixo do MVP, jornadas variáveis ou parciais serão tratadas em evolução futura?
