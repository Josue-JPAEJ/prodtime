# Referência do legado

## Origem

Antes da automação, estimativas de capacidade e prazo eram realizadas manualmente por profissionais experientes da produção têxtil. O processo concentrava conhecimento e podia atrasar decisões de produção e respostas de vendas.

O autor desenvolveu posteriormente uma calculadora integrada a um ERP legado também desenvolvido por ele em VBA. Ela considerava fatores como velocidade em cm/min, quantidade de fitas simultâneas, horas produtivas, desperdício, calendário, datas e capacidade.

## Impacto operacional conhecido

A automação reduziu a dependência direta de especialistas para cada estimativa e tornou o cálculo acessível a outros usuários, inclusive vendedores que precisavam responder a clientes.

## Limitações conhecidas

- A solução estava acoplada ao ERP legado em VBA.
- A fonte histórica está preservada em `docs/legacy/vba/CalMetrosNaMaq.txt`.
- Os principais procedimentos são `ProducaoEstimada` (capacidade, desperdício, calendário e saldo), `TotalMetros` (peso para metragem), `ListFeriados` (carga dos feriados) e `iUserForm_Activate` (defaults históricos).
- Handlers de campos e checkboxes transferem entradas da UI, fazem validações básicas e disparam recálculos; helpers numéricos externos não estão contidos no arquivo.
- A engenharia reversa e a separação entre regra e limitação técnica estão em `docs/domain/CALCULATION_SPEC.md`.
- Coerções integrais, comparações com `Empty`, calendário sem deduplicação e dependência de UI/localidade são limitações do legado, não requisitos automáticos do ProdTime.

## Evolução para o ProdTime

O ERP VBA foi substituído por um novo ERP Web, também desenvolvido pelo autor. O ProdTime isola e moderniza a necessidade específica de estimar capacidade e prazo em um aplicativo Android. A integração com o ERP Web é uma possibilidade futura, fora do MVP acadêmico atual.
