# Especificação de feriados do ProdTime

# 1. Objetivo

Definir o modelo puro e a resolução determinística de feriados cadastráveis em datas concretas consumidas pelo calendário produtivo no R2.3.

# 2. Decisão de produto

O ProdTime não recebe uma quantidade manual de feriados. Cada feriado é identificável por nome e data, em uma das formas aprovadas: anual recorrente, aplicável pelo mesmo dia e mês em todos os anos válidos, ou de data específica, aplicável uma única vez.

# 3. Modelo

`HolidayDefinition` é o contrato comum. `AnnualHoliday` contém `name: String` não vazio e `monthDay: MonthDay`. `SpecificDateHoliday` contém `name: String` não vazio e `date: LocalDate`. Nomes vazios ou compostos somente por espaços são inválidos; as APIs `MonthDay` e `LocalDate` garantem a validade estrutural das datas, sem representação textual no domínio.

# 4. Resolução

`HolidayResolver.resolve` recebe uma coleção de definições, `startDate` e `endDate`, e retorna somente as ocorrências aplicáveis como `Set<LocalDate>`. Definições específicas são filtradas pelo intervalo; para cada definição anual, o resolver avalia todos os anos atravessados.

# 5. Recorrência

A recorrência anual pertence exclusivamente ao resolver. O fluxo é:

```text
HolidayDefinition → HolidayResolver → Set<LocalDate> → ProductiveCalendarCalculator
```

O calendário permanece sem recorrência implícita e conhece apenas datas concretas.

# 6. Intervalos

O intervalo é inclusivo e permite `startDate == endDate`. `startDate > endDate` é rejeitado com `IllegalArgumentException` por `require`. Intervalos entre anos consideram todos os anos atravessados, mantendo somente ocorrências entre as duas pontas.

# 7. Deduplicação

O resultado é um conjunto. Se definições iguais ou distintas produzirem a mesma `LocalDate`, há uma única ocorrência e, portanto, um único efeito no calendário.

# 8. 29 de fevereiro

`MonthDay.of(2, 29)` é uma definição anual válida. Ela gera ocorrência somente em anos bissextos. Em anos comuns não gera data, não é deslocada para 28/02 ou 01/03 e não causa erro durante a resolução.

# 9. Integração com ProductiveCalendarCalculator

O conjunto resolvido é entregue diretamente a `ProductiveCalendarInput.holidays`. O `ProductiveCalendarCalculator` continua desacoplado de nomes, recorrência, cadastro, persistência, UI e origem dos feriados, e classifica cada data concreta uma única vez.

# 10. Persistência futura

A persistência das definições não foi definida nesta etapa. Não há banco, arquivo, repositório, DTO ou identificador no modelo do R2.3.

# 11. UI futura

A futura UX deverá permitir informar o nome, escolher entre repetição anual e data específica e fornecer, respectivamente, dia/mês ou dia/mês/ano. Nenhuma tela, navegação, ViewModel ou formulário é implementado nesta etapa.

# 12. Casos H1–H12

| Caso | Cobertura |
|---|---|
| H1 | Feriado anual no mesmo ano. |
| H2 | A mesma definição anual em ano diferente. |
| H3 | Ocorrências anuais em intervalo entre anos. |
| H4 | Data específica dentro do intervalo. |
| H5 | Data específica fora do intervalo. |
| H6 | Definições distintas produzindo uma única data. |
| H7 | Intervalo de uma data com feriado. |
| H8 | Intervalo de uma data sem feriado. |
| H9 | Rejeição de intervalo invertido. |
| H10 | 29/02 em ano bissexto. |
| H11 | Ausência de 29/02 em ano comum, sem deslocamento ou erro. |
| H12 | Múltiplas definições anuais e específicas, dentro e fora do intervalo. |

# 13. Decisões adiadas

- mecanismo de persistência;
- operações e regras de CRUD;
- importação de calendários;
- feriados nacionais automáticos;
- localidade;
- integração com ERP.

# 14. Conclusão

**Sim, após a validação local do R2.3.** O contrato conjunto de calendário e feriados está tecnicamente suficiente para encerrar o R2: modelo, recorrência externa, intervalos, deduplicação, 29/02 e integração por datas concretas estão definidos e cobertos por testes. Até essa validação, o R2 permanece em andamento.
