# Especificação do calendário produtivo do ProdTime

# 1. Objetivo

Definir o contrato determinístico do novo calendário produtivo do ProdTime para o R2. O calendário recebe um intervalo e políticas explícitas, classifica cada data considerada como **produtiva** ou **não produtiva** e fornece os dias produtivos ao motor de capacidade. Esta especificação separa o comportamento legado confirmado, suas fragilidades e as decisões aprovadas para o novo domínio; não define implementação, UI nem jornada diária.

# 2. Comportamento legado relevante

O VBA parte de um intervalo inclusivo entre `DataInicial` e `DataEntrega`. Durante a iteração, mantém contadores independentes de sábados, domingos e feriados; depois subtrai cada contador cuja opção esteja desmarcada e também subtrai separadamente as pontas desmarcadas.

As opções booleanas do formulário significam incluir sábado, domingo, feriado, data inicial e data final. A UI rejeita datas iguais ou invertidas. Os feriados são carregados da planilha para a UI como texto e o cálculo recompõe o ano de cada data iterada, produzindo recorrência anual por dia e mês.

# 3. Problemas identificados no legado

- **Dupla ou tripla subtração:** sábado, domingo, feriado e ponta são descontados por contadores independentes, embora possam representar a mesma data.
- **Contagem negativa possível:** as subtrações cumulativas podem reduzir o total abaixo de zero.
- **Recorrência textual de feriados:** o ano é reconstruído a partir de texto, com dependência de formato e localidade, e feriados móveis não são representados corretamente sem atualizar a origem.
- **Dependência da UI:** opções, datas e feriados são lidos ou transformados diretamente por controles do formulário.
- **Duplicatas:** entradas repetidas de um feriado incrementam o contador repetidamente.
- **Intervalo de uma data:** a UI rejeita `DataInicial = DataEntrega`, apesar de a rotina de contagem isolada iterar essa data.

Essas fragilidades são referências históricas, não regras do novo ProdTime.

# 4. Contrato aprovado do ProdTime

## Entradas

| Entrada | Tipo conceitual | Contrato |
|---|---|---|
| `startDate` | `LocalDate` | Primeira data do intervalo-base inclusivo. Deve ser menor ou igual a `endDate`. |
| `endDate` | `LocalDate` | Última data do intervalo-base inclusivo. Deve ser maior ou igual a `startDate`. |
| `includeStartDate` | `Boolean` | Define se `startDate` pertence ao intervalo considerado. |
| `includeEndDate` | `Boolean` | Define se `endDate` pertence ao intervalo considerado. |
| `includeSaturdays` | `Boolean` | Permite que sábados sejam produtivos; não neutraliza um feriado não trabalhado. |
| `includeSundays` | `Boolean` | Permite que domingos sejam produtivos; não neutraliza um feriado não trabalhado. |
| `workOnHolidays` | `Boolean` | Se `false`, datas de `holidays` não são produtivas; se `true`, o motivo “feriado” não as exclui. |
| `holidays` | `Set<LocalDate>` | Datas explícitas classificadas como feriado, sem recorrência implícita. O conjunto deduplica naturalmente a origem. |

`workOnHolidays` é adotado em lugar de `includeHolidays` para tornar inequívoca a política. Quando é `true`, um feriado pode ser produtivo, mas ainda obedece à política do dia da semana. De segunda a sexta, uma data considerada é produtiva por padrão, salvo feriado com `workOnHolidays = false`.

## Resultado conceitual

O futuro domínio deve fornecer, sem exigir arquitetura além da necessidade da implementação:

- quantidade total de datas consideradas (`consideredDays`);
- quantidade de dias produtivos (`productiveDays`);
- lista ou conjunto das datas produtivas;
- lista ou conjunto das datas não produtivas, quando adotado para rastreabilidade;
- contagens informativas de sábados, domingos e feriados entre as datas consideradas.

As contagens informativas podem se sobrepor — por exemplo, uma data pode constar como domingo e feriado —, mas nunca são usadas para subtrair do total. R2 calcula dias; `productiveHoursPerDay` continua sendo responsabilidade da entrada do motor R1.

# 5. Ordem de avaliação

1. Validar que `startDate <= endDate`; rejeitar o intervalo invertido.
2. Construir uma sequência de datas entre `startDate` e `endDate`, inclusiva nas duas pontas e sem duplicatas.
3. Remover `startDate` se `includeStartDate = false`.
4. Remover `endDate` se `includeEndDate = false`; se as pontas forem a mesma data, qualquer exclusão remove essa única data.
5. Para cada data restante, identificar dia da semana e presença em `holidays`.
6. Classificar a data uma única vez como produtiva ou não produtiva, conforme a regra por dia.
7. Derivar listas/conjuntos, total de datas consideradas, total produtivo e contagens informativas a partir dessas classificações.

# 6. Regra por dia

Para toda data que permaneceu no intervalo considerado, aplica-se a decisão abaixo. A exclusão de uma ponta já ocorreu e não participa desta tabela.

| Sábado | Domingo | Feriado | Opção correspondente | Produtivo? |
|---|---|---|---|---|
| Não | Não | Não | Nenhuma opção adicional | Sim |
| Não | Não | Sim | `workOnHolidays = false` | Não |
| Não | Não | Sim | `workOnHolidays = true` | Sim |
| Sim | Não | Não | `includeSaturdays = false` | Não |
| Sim | Não | Não | `includeSaturdays = true` | Sim |
| Sim | Não | Sim | `workOnHolidays = false` | Não, independentemente de `includeSaturdays` |
| Sim | Não | Sim | `workOnHolidays = true` | Conforme `includeSaturdays` |
| Não | Sim | Não | `includeSundays = false` | Não |
| Não | Sim | Não | `includeSundays = true` | Sim |
| Não | Sim | Sim | `workOnHolidays = false` | Não, independentemente de `includeSundays` |
| Não | Sim | Sim | `workOnHolidays = true` | Conforme `includeSundays` |

Uma data não pode ser simultaneamente sábado e domingo. Em termos conceituais, ela é produtiva somente se todas as condições aplicáveis permitirem trabalho: a condição de feriado, quando ativa, e a condição de sábado ou domingo, quando aplicável.

# 7. Colisões

A unidade de decisão é a data, não um contador de motivos de exclusão. Depois de pertencer ao intervalo considerado, cada data recebe exatamente uma classificação final: **produtiva** ou **não produtiva**.

Assim, domingo que também seja feriado continua sendo uma única data não produtiva quando ambas as categorias não são trabalhadas. Uma ponta removida deixa de pertencer ao intervalo e não é posteriormente classificada como fim de semana ou feriado. Motivos e contagens podem ser preservados para rastreabilidade, mas não geram subtrações cumulativas.

# 8. Feriados

`holidays` é um `Set<LocalDate>` de datas exatas não produtivas quando `workOnHolidays = false`. O conjunto elimina duplicatas naturalmente. Um feriado móvel é suportado quando o chamador fornece sua data exata.

O núcleo não infere recorrência anual, não interpreta nomes ou textos, não depende de formatação ou localidade e não consulta API externa no MVP. Com `workOnHolidays = true`, pertencer a `holidays` deixa de ser, isoladamente, motivo de exclusão; as regras de sábado e domingo continuam válidas.

Definições cadastráveis são resolvidas externamente conforme `HOLIDAY_SPEC.md`, pelo fluxo `HolidayDefinition → HolidayResolver → Set<LocalDate> → ProductiveCalendarCalculator`. Portanto, uma definição anual pode originar datas concretas antes da chamada, enquanto o calendário continua sem recorrência implícita e sem conhecer nomes ou cadastros.

# 9. Pontas do intervalo

O intervalo-base contém `startDate` e `endDate`. As opções das pontas definem pertencimento, antes de qualquer classificação produtiva:

- `includeStartDate = false` remove a data inicial;
- `includeEndDate = false` remove a data final;
- uma ponta incluída ainda pode ser não produtiva por sábado, domingo ou feriado;
- uma ponta removida não participa de totais, listas nem contagens informativas das datas consideradas.

Quando `startDate == endDate`, existe apenas uma data. Ela pertence ao intervalo considerado **somente quando** `includeStartDate = true` **e** `includeEndDate = true`. Se qualquer uma das opções for `false`, `consideredDays = 0` e `productiveDays = 0`. Se ambas forem `true`, a data é avaliada uma única vez pelas regras de dia da semana e feriado.

# 10. Invariantes

- `startDate <= endDate`; caso contrário, a entrada é inválida.
- `consideredDays >= 0`.
- `productiveDays >= 0`.
- `productiveDays <= consideredDays`.
- Cada data pertence no máximo uma vez ao intervalo considerado e é contabilizada no máximo uma vez.
- As classificações produtiva e não produtiva são mutuamente exclusivas e cobrem todas as datas consideradas.
- Feriados duplicados na origem não alteram o resultado representado por `Set<LocalDate>`.
- Remover uma ponta nunca provoca subtração adicional por outro motivo.
- Um intervalo válido sem dia produtivo retorna zero; nunca retorna valor negativo.

# 11. Casos de aceitação

| Caso | Configuração resumida | Resultado esperado |
|---|---|---|
| A — dia útil normal | Uma segunda-feira, sem feriado, ambas as pontas incluídas | `consideredDays = 1`; `productiveDays = 1`. |
| B — domingo não trabalhado | Um domingo incluído; `includeSundays = false` | `productiveDays = 0`. |
| C — domingo trabalhado | Um domingo incluído, não feriado; `includeSundays = true` | `productiveDays = 1`. |
| D — domingo + feriado | Domingo incluído; `includeSundays = false`; data em `holidays`; `workOnHolidays = false` | `productiveDays = 0`, nunca −1 ou −2. |
| E — sábado trabalhado + feriado | Sábado incluído; `includeSaturdays = true`; data em `holidays`; `workOnHolidays = false` | `productiveDays = 0`; o feriado não trabalhado prevalece. |
| F — sábado + feriado, ambos trabalhados | Sábado incluído; `includeSaturdays = true`; data em `holidays`; `workOnHolidays = true` | `productiveDays = 1`. |
| G — data inicial excluída | Data inicial potencialmente produtiva; `includeStartDate = false` | A data não pertence ao intervalo considerado e não é classificada. |
| H — data final excluída | Data final potencialmente produtiva; `includeEndDate = false` | A data não pertence ao intervalo considerado e não é classificada. |
| I — ponta excluída + domingo + feriado | Data inicial é domingo e feriado; `includeStartDate = false` | A data não pertence ao intervalo considerado; nenhuma subtração adicional ocorre. |
| J — início = fim | Mesma data nas duas pontas | Com ambas as pontas incluídas, avaliar uma vez; se qualquer ponta for excluída, `consideredDays = 0` e `productiveDays = 0`. |
| K — feriado duplicado | A origem fornece repetidamente a mesma data, normalizada em `Set<LocalDate>` | Efeito de um único feriado; resultado inalterado pela duplicata. |
| L — intervalo invertido | `startDate > endDate` | Entrada inválida; o futuro contrato deve rejeitá-la. |
| M — nenhum dia produtivo | Intervalo válido cujas datas consideradas são todas não produtivas, ou nenhuma data permanece após as pontas | `productiveDays = 0`, resultado válido do calendário. |

# 12. Integração futura com ProductionCapacityCalculator

R2 fornece a quantidade — e, para rastreabilidade, as datas — de dias produtivos. R1 calcula capacidade usando `productiveDays` e `productiveHoursPerDay`; o calendário não calcula horas variáveis por data.

Se R2 retornar `productiveDays = 0`, a futura camada de integração deverá tratar esse resultado antes de chamar `ProductionCapacityCalculator`, cujo contrato atual exige `productiveDays > 0`. Esta etapa não implementa nem altera a integração ou o motor R1.

# 13. Decisões adiadas

- jornadas variáveis ou parciais por data;
- turnos;
- carregamento automático de feriados;
- localidade do calendário;
- calendário corporativo fornecido por ERP;
- exceções de calendário por máquina.

# 14. Conclusão

**Sim.** A especificação está suficiente para implementar o R2.2: entradas, validade do intervalo, pertencimento das pontas, precedência das regras, classificação booleana por data, feriados explícitos, invariantes, resultado esperado e casos A–M estão definidos. As decisões adiadas não bloqueiam o calendário fixo do MVP.

# 15. Implementação R2.2

O núcleo puro foi implementado em `br.com.prodtime.domain` pelas classes `ProductiveCalendarInput`, `ProductiveCalendarResult` e pelo objeto `ProductiveCalendarCalculator`. O contrato usa `LocalDate` de ponta a ponta e recebe feriados como `Set<LocalDate>`, sem texto, timezone, recorrência ou dependência Android.

A calculadora valida o intervalo, gera suas datas em ordem cronológica, aplica as regras das pontas e cria uma única classificação booleana para cada data considerada. Essa classificação registra sábado, domingo, feriado e a decisão produtiva final, obtida pela conjunção entre a permissão do dia da semana e a permissão de feriado; não há subtração de contadores.

O resultado deriva da mesma coleção classificada as listas cronológicas produtiva e não produtiva, seus totais e as contagens informativas. Com isso, permanecem preservadas a unicidade das datas, a partição completa e mutuamente exclusiva das datas consideradas, os limites `0 <= productiveDays <= consideredDays` e a exclusão das pontas de todas as métricas.

Os testes unitários do R2.2 cobrem explicitamente os casos A–Q: dias úteis e fins de semana, colisões com feriados, políticas de trabalho, exclusão das pontas, intervalo de uma data, deduplicação, intervalo invertido, zero dias produtivos, intervalo com múltiplas datas e feriado fora do intervalo.
