# Especificação matemática do ProdTime

# 1. Objetivo

Reconstruir, de forma auditável, o comportamento matemático da calculadora legada e separar regras de negócio de coerções, restrições e possíveis defeitos do VBA. O documento distingue o legado do contrato aprovado para o novo ProdTime; decisões ainda pendentes estão em `OPEN_QUESTIONS.md`.

# 2. Fonte primária

A fonte primária é `docs/legacy/vba/CalMetrosNaMaq.txt`, lida integralmente. Ela é a **fonte do comportamento legado**, não uma arquitetura a ser copiada. A documentação atual em `docs/product/` e `docs/process/` foi usada como contexto secundário.

# 3. Mapa do legado

| Procedimento | Responsabilidade |
|---|---|
| `ProducaoEstimada` | Conta dias do intervalo, aplica opções de calendário, converte velocidade, calcula produção bruta, desperdício, produção líquida e saldo. |
| `TotalMetros` | Calcula peso líquido por caixa e converte o peso líquido total em metragem. |
| `ListFeriados` | Carrega da planilha `Relatorios` nome, data e descrição dos feriados, anexando à data o ano corrente no momento da carga. |
| `iUserForm_Activate` | Define defaults históricos, carrega feriados e atualiza o título do formulário. |
| `UserForm_Initialize` | Obtém a planilha, verifica o servidor, ativa o formulário e compila os valores dos campos. |
| `Compilacao_Exits` | Executa handlers de saída para transferir valores da UI às variáveis legadas. |
| `itb*\_Exit` | Faz validação/conversão básica dos campos e dispara os cálculos. |
| `chb*\_Click` | Transfere a seleção das opções de calendário para Boolean e recalcula. |
| `tb*\_KeyPress` / `tb*\_Change` | Delega filtragem e verificação numérica a rotinas externas ao arquivo analisado. |

# 4. Glossário

## 4.1 Grandezas do cálculo produtivo

| Grandeza | Unidade | Tipo legado | Status |
|---|---|---|---|
| `metrosCalcular` | m | `Long` | CONFIRMADA como meta usada no saldo |
| `qntFita` | fitas simultâneas | `Integer` | CONFIRMADA |
| `CM_MIN` | cm/min por fita | `Double` | CONFIRMADA; “por fita” é inferido da multiplicação posterior por `qntFita` |
| `dDesperd` | % | `Double` | CONFIRMADA |
| `HorasUteis` | h/dia | `Long` | CONFIRMADA |
| `m_h` | m/h por fita | `Double` | CONFIRMADA pela fórmula; “por fita” é INFERIDA pela estrutura |
| `IntervaloDeDias` | dias contabilizados | `Integer` local | CONFIRMADA |
| `lProdEstimada` | m | `Long` local | CONFIRMADA; recebe primeiro a produção bruta e depois a líquida |
| `lSaldo` | m | `Long` local | variável declarada, mas não utilizada |
| `Sabado`, `Domingo`, `Feriado` | incluir categoria? | `Boolean` | CONFIRMADA |
| `bDatInic`, `bDatEnt` | incluir a ponta? | `Boolean` | CONFIRMADA |

## 4.2 Grandezas de peso e metragem

| Grandeza | Unidade | Tipo legado | Status |
|---|---|---|---|
| `tara` | kg/caixa | `Double` | CONFIRMADA pela fórmula e exemplo |
| `pBruto` | kg/caixa | `Double` | CONFIRMADA pela fórmula e exemplo |
| `pLiq` | kg/caixa | `Double` | CONFIRMADA |
| `tMetros` | m | `Long` | CONFIRMADA |
| `qntCaixas` | caixas | `Integer` | CONFIRMADA |
| `pFita` | g/m | `Double` | INFERIDA com alta confiança pela fórmula, sufixo visual `g` e resultado histórico |

`Double` preserva frações em operações intermediárias, enquanto atribuições a `Integer`/`Long` fazem coerção integral. `Integer` (16 bits, −32.768 a 32.767) traz risco especialmente para contadores, caixas, fitas e dias; `Long` (32 bits, −2.147.483.648 a 2.147.483.647) traz risco para metros e produtos. Operações podem ainda transbordar antes ou na atribuição conforme os tipos promovidos. Entradas textuais são convertidas implicitamente, dependem da localidade e podem perder frações quando destinadas a tipos integrais. Essas limitações não constituem requisitos do novo domínio.

# 5. Fórmula de capacidade produtiva

## 5.1 Conversão dimensional de velocidade

O legado executa:

```text
m_h = (CM_MIN × 60) / 100
```

Dimensionalmente:

```text
(cm/min × 60 min/h) / 100 cm/m = m/h
```

Como a quantidade de fitas ainda não participa dessa expressão e é multiplicada depois, `m_h` representa a velocidade linear por fita (**INFERIDA com alta confiança**). Para 25 cm/min: `25 × 60 / 100 = 15 m/h/fita`.

## 5.2 Produção bruta

O legado executa:

```text
lProdEstimada = m_h × qntFita × HorasUteis × IntervaloDeDias
```

Logo, antes da coerção de atribuição:

```text
P_bruta = (CM_MIN × 60 / 100) × F × H × D
```

em que `F` é a quantidade de fitas simultâneas, `H` são horas produtivas por dia e `D` são dias contabilizados. As unidades são `(m/(h·fita)) × fita × (h/dia) × dia = m`. O total de horas exibido é `H × D`. Como `HorasUteis` é `Long`, o caminho legado armazena apenas horas inteiras, sujeito à coerção na entrada.

## 5.3 Desperdício e produção líquida

Após armazenar a produção bruta em `lProdEstimada`, o formulário exibe:

```text
W_exibido = P_bruta_armazenada × (dDesperd / 100)
```

Em seguida calcula e reatribui:

```text
P_líquida = P_bruta_armazenada −
            (P_bruta_armazenada × dDesperd / 100)
          = P_bruta_armazenada × (1 − dDesperd / 100)
```

Portanto, o percentual incide sobre a produção bruta já coercida para `Long`; primeiro o desperdício é exibido como resultado fracionário e depois a produção líquida é coercida para `Long`. A saída `tbProdEstimada` é líquida. Esse comportamento é **CONFIRMADO para o legado**.

# 6. Arredondamento e coerção VBA

`m_h` e os cálculos com percentual usam `Double`, porém `lProdEstimada` é `Long`. Há duas fronteiras explícitas de coerção:

1. a expressão bruta em `Double` é atribuída a `lProdEstimada`, convertendo-a em inteiro;
2. a expressão líquida, novamente `Double` por envolver `dDesperd`, é reatribuída ao mesmo `Long`.

Conversões numéricas VBA de valor fracionário para tipo integral arredondam para o inteiro mais próximo; em empate exato (`x,5`), aplica-se arredondamento bancário, isto é, para o inteiro par mais próximo. Não se trata de truncamento. Assim, valores com fração `,6` sobem e valores com `,4` descem; a regra de empate pode afetar futuros casos `,5`. A produção bruta também pode ser arredondada antes de servir de base ao desperdício quando a fórmula bruta não for integral.

Nos casos históricos, a primeira coerção não altera `13.680` nem `9.120`. Na segunda coerção, `13.269,6 → 13.270` e `8.846,4 → 8.846`. Isso explica exatamente a assimetria observada sem forçar os resultados.

Há outra fronteira equivalente em `tMetros = (tLiq × 1000) / pFita`, pois a expressão fracionária é atribuída a `Long`. `HorasUteis`, `metrosCalcular`, `qntFita` e `qntCaixas` também recebem texto da UI em tipos integrais por coerção implícita. A implementação Kotlin deverá definir conscientemente precisão e compatibilidade; esta etapa não escolhe a estratégia.

# 7. Regressões históricas

| Caso | Esperado | Calculado | Diferença | Status |
|---|---:|---:|---:|---|
| 1 — 3 fitas | 13.270 m | 13.270 m | 0 m | CONFIRMADA |
| 2 — 2 fitas | 8.846 m | 8.846 m | 0 m | CONFIRMADA |

### Caso 1

1. `m_h = 25 × 60 / 100 = 15 m/h/fita`.
2. Bruta: `15 × 3 × 16 × 19 = 13.680 m`; atribuição ao `Long` permanece `13.680`.
3. Desperdício exibido: `13.680 × 3/100 = 410,4 m`.
4. Líquida antes da coerção: `13.680 − 410,4 = 13.269,6 m`.
5. Atribuição ao `Long`: `13.269,6 → 13.270 m`.

### Caso 2

1. `m_h = 15 m/h/fita`.
2. Bruta: `15 × 2 × 16 × 19 = 9.120 m`; atribuição ao `Long` permanece `9.120`.
3. Desperdício exibido: `9.120 × 3/100 = 273,6 m`.
4. Líquida antes da coerção: `9.120 − 273,6 = 8.846,4 m`.
5. Atribuição ao `Long`: `8.846,4 → 8.846 m`.

# 8. Saldo

Se `metrosCalcular` não for considerado `Empty`, o legado define:

```text
Saldo = P_líquida_inteira − metrosCalcular
```

Logo, `13.270 − 10.000 = 3.270 m` e `8.846 − 10.000 = −1.154 m`. O comportamento é **CONFIRMADO**. Saldo positivo/zero colore o indicador de verde; negativo, de vermelho. Como zero compara igual a `Empty` no teste numérico, uma meta zero impede a atualização do saldo.

# 9. Calendário legado

## intervalo inclusivo

`IntervaloDeDias = (DataEntrega + 1) - DataInicial` equivale à diferença em dias mais um. O `For DateIni = DataInicial To DataEntrega` também visita ambas as pontas. Datas iguais são rejeitadas pelos handlers (`DataInicial >= DataEntrega`), embora a rotina isolada pudesse contar uma data.

## sábados

`Weekday(DateIni) = 7` incrementa o contador. Se `Sabado = False`, todos os sábados encontrados são subtraídos. `True` significa incluir a categoria.

## domingos

`Weekday(DateIni) = 1` incrementa o contador. Se `Domingo = False`, todos os domingos encontrados são subtraídos. `True` significa incluir a categoria.

## feriados

`ListFeriados` lê a data da planilha, concatena `/` e o ano corrente e a exibe na lista. Durante cada dia do intervalo, o cálculo remove os quatro últimos caracteres do texto dessa data e anexa `Year(DateIni)`. Assim, mês/dia são reaproveitados e o ano é reconstruído para cada ano percorrido: comportamento de feriado anual recorrente (**CONFIRMADO para o legado**).

Esse mecanismo não representa corretamente, sem atualização externa do mês/dia, feriados móveis. O formato depende de texto/localidade e da suposição de ano com quatro caracteres. Cada item igual incrementa o contador; duplicatas na lista contam mais de uma vez.

## data inicial

Depois das exclusões por categoria, `bDatInic = False` subtrai mais um dia, sem verificar se a data inicial já foi removida. `True` inclui explicitamente a ponta, mas não a protege de uma categoria desmarcada.

## data final

Depois das exclusões por categoria, `bDatEnt = False` subtrai mais um dia, com a mesma independência em relação às categorias. `True` inclui explicitamente a ponta, mas não a protege de uma categoria desmarcada.

## colisões entre categorias

As subtrações são aditivas e não deduplicadas:

- um feriado em sábado ou domingo é subtraído uma vez por cada categoria desmarcada;
- um feriado em data inicial/final pode ser subtraído pela categoria e novamente pela ponta desmarcada;
- um domingo na data inicial/final pode ser subtraído como domingo e novamente como ponta;
- um feriado que também seja domingo pode ser subtraído duas vezes, ou três se também estiver numa ponta desmarcada;
- entradas duplicadas de feriado acrescentam novas subtrações.

## potenciais defeitos

As duplas/triplas subtrações e até um `IntervaloDeDias` negativo são possíveis por análise estática. O arquivo não contém evidência de que isso seja deliberado; portanto são **possíveis defeitos legados**, não regras a preservar. Também é **NÃO DETERMINADO** se os usuários evitavam essas combinações operacionalmente. A correção e a semântica definitiva pertencem ao R2; nesta etapa não há implementação de calendário.

# 10. Peso → metragem

O legado calcula:

```text
pLiq = pBruto − tara
tBruto = pBruto × qntCaixas
tTara = tara × qntCaixas
tLiq = tBruto − tTara
tMetros = (tLiq × 1000) / pFita
```

No caso histórico:

```text
pLiq = 2,000 − 0,460 = 1,540 kg/caixa
tLiq = (2,000 × 3) − (0,460 × 3) = 4,620 kg
tMetros = (4,620 × 1000) / 0,12 = 4.620 g / (0,12 g/m) = 38.500 m
```

A conversão `kg × 1000 = g` e a divisão `g ÷ (g/m) = m` fecham dimensionalmente. O sufixo visual `g` sozinho poderia significar apenas gramas, mas, combinado com a divisão e o resultado, sustenta com **alta confiança, ainda INFERIDA**, que `pFita` é massa linear em gramas por metro. O resultado de 38.500 m é **CONFIRMADO** pelo caminho matemático e não sofre arredondamento por ser inteiro.

# 11. Defaults históricos

| Campo | Default legado |
|---|---:|
| Desperdício | 3% |
| Horas úteis | 16 h/dia |
| Quantidade de fitas | 1 |
| Velocidade | 28 cm/min |

São **defaults históricos do legado**, explicitamente definidos em `iUserForm_Activate`, e não defaults definitivos do novo aplicativo.

# 12. Validações legadas

- Datas vazias ou inválidas são limpas; quando ambas existem, `DataInicial >= DataEntrega` limpa o campo recém-processado. Datas iguais e invertidas são, portanto, rejeitadas na UI.
- Produção retorna cedo quando desperdício, horas, datas ou velocidade comparam iguais a `Empty`. Quantidade de fitas não participa dessa guarda; zero produz capacidade zero.
- Saldo não é atualizado quando `metrosCalcular = Empty`; numericamente, zero também compara igual a `Empty`.
- Peso retorna cedo quando tara ou peso bruto, e depois caixas ou peso da fita, comparam iguais a `Empty`; isso faz zero ser tratado como ausência em vários campos.
- Campos textuais vazios são tratados nos handlers. `IsDate` existe para datas. A validação numérica depende de `VerificarNumerico`, `Numeros`, `soNumeros` e `DatasFormatacao`, cujas implementações não estão na fonte fornecida.
- Não há neste arquivo validação explícita de negativos, percentual entre 0% e 100%, tara menor ou igual ao bruto, quantidade/velocidade/horas estritamente positivas, nem divisão por zero de `pFita`.
- Não há guarda explícita de overflow. Erros estão especialmente expostos porque os `On Error GoTo Erro` estão comentados.

Campos obrigatórios são implícitos pelas saídas antecipadas, não por um contrato formal. A combinação `Empty`/zero e as coerções da UI são detalhes frágeis do legado e não devem ser copiadas automaticamente.

# 13. Limitações técnicas do VBA

- domínio e UI estão acoplados em variáveis de formulário e eventos;
- coerções implícitas dependem de tipo e localidade;
- `Integer` de 16 bits limita contadores e quantidades;
- `Long` de 32 bits limita metragem e produção;
- frações são perdidas por arredondamento nas atribuições integrais;
- o arredondamento ocorre em etapas intermediárias, podendo alterar a base do desperdício;
- calendário subtrai categorias sem deduplicação;
- feriados são reconstruídos por manipulação textual e recorrência anual;
- validações numéricas essenciais estão fora do arquivo e não podem ser auditadas aqui;
- zero é confundido com ausência por comparações com `Empty`;
- tratamento de erros está comentado.

# 14. Regras que devem ser preservadas

Com base no comportamento comprovado, o novo motor preserva semanticamente, conforme a política decimal aprovada na seção 17:

1. conversão de cm/min para m/h pelo fator `60/100`;
2. multiplicação da velocidade por fitas simultâneas, horas por dia e dias produtivos;
3. desperdício percentual calculado sobre a produção bruta;
4. produção líquida igual à bruta menos o desperdício;
5. saldo igual à produção líquida menos a meta em metros;
6. total de horas igual a horas produtivas por dia vezes dias produtivos;
7. conversão de peso líquido total em gramas e divisão pela massa linear para obter metros.

Essas relações não decidem defaults, limites operacionais máximos, apresentação ou política de calendário.

# 15. Comportamentos que NÃO devem ser copiados sem decisão

- subtrações duplicadas entre feriado, fim de semana e pontas;
- recorrência textual simplista para todos os feriados;
- limites e overflow de `Integer`/`Long`;
- coerção acidental por atribuição a `Long` (as duas fronteiras compatíveis são agora deliberadas e usam `BigDecimal`);
- dependência de localidade e da UI para conversão;
- comparação numérica com `Empty`, que confunde zero e ausência;
- aceitação implícita de entradas negativas ou percentuais fora de faixa;
- restrição de horas produtivas a inteiros;
- defaults históricos como se fossem requisitos atuais.

# 16. Matriz de evidências

| Regra | Status | Evidência | Confiança | Decisão necessária |
|---|---|---|---|---|
| `cm/min × 60 / 100 = m/h` | CONFIRMADA (legado) | atribuição a `m_h` | Alta | `BigDecimal`, sem arredondamento desnecessário (R1.2) |
| velocidade é por fita | INFERIDA | `qntFita` é multiplicada depois | Alta | confirmar vocabulário de produto |
| produção bruta multiplica velocidade, fitas, horas e dias | CONFIRMADA (legado) | atribuição inicial a `lProdEstimada` | Alta | limites de entrada |
| desperdício incide na produção bruta | CONFIRMADA (legado) | duas expressões em `ProducaoEstimada` | Alta | precisão e exibição |
| produção final é líquida | CONFIRMADA (legado) | subtração antes de `tbProdEstimada` | Alta | `HALF_EVEN` na saída líquida (R1.2) |
| saldo é líquida menos meta | CONFIRMADA (legado) | atribuição a `tbSaldo` | Alta | meta zero é válida; ausência é `null` (R1.2) |
| coerção a inteiro arredonda ao mais próximo e empates para par | CONFIRMADA (semântica VBA) | atribuições `Double → Long` | Alta | duas fronteiras deliberadas com `HALF_EVEN` (R1.2) |
| período base inclui as duas pontas | CONFIRMADA (legado) | `+ 1` e loop inclusivo | Alta | política definitiva do R2 |
| opções falsas excluem categoria/ponta | CONFIRMADA (legado) | condicionais após o loop | Alta | UX e regra definitiva do R2 |
| colisões são subtraídas repetidamente | CONFIRMADA (mecânica legado) | contadores e subtrações independentes | Alta | corrigir no R2 |
| colisões repetidas são regra de negócio | NÃO DETERMINADA | nenhuma justificativa na fonte | Baixa | decisão de produto no R2 |
| feriados são recorrentes por mês/dia | CONFIRMADA (legado) | reconstrução com `Year(DateIni)` | Alta | modelo de feriados no R2 |
| `pFita` é g/m | INFERIDA | fórmula, sufixo `g` e caso 38.500 m | Alta | confirmar rótulo/contrato |
| defaults 3/16/1/28 | CONFIRMADA (legado) | `iUserForm_Activate` | Alta | manter ou substituir |
| horas legadas são inteiras | CONFIRMADA (legado) | declaração `As Long` | Alta | horas fracionárias aceitas (R1.2) |
| validação rejeita todo número inválido | NÃO DETERMINADA | helpers externos ausentes | Baixa | definir contrato explícito |

Não foi encontrada contradição entre as fórmulas do arquivo e os casos históricos. A documentação anterior que dizia que a fórmula/código não estava disponível ficou desatualizada e foi atualizada.

# 17. Contrato aprovado do motor de capacidade (R1.2)

Como **decisão do ProdTime**, o núcleo matemático usa `java.math.BigDecimal`, criado a partir de texto ou constantes exatas, sem conversões por `Double` ou `Float`. Não são reproduzidos os limites, overflow, `Empty` ou coerções implícitas do VBA.

O contrato recebe velocidade em cm/min por fita, quantidade inteira de fitas, horas produtivas por dia em `BigDecimal`, quantidade inteira de dias produtivos, percentual de desperdício em `BigDecimal` e meta opcional em metros inteiros, representada por `BigDecimal` de escala não significativa. Horas fracionárias, como `7.5 h/dia`, são aceitas conscientemente. O motor não define defaults nem calcula calendário.

A política explícita é `RoundingMode.HALF_EVEN`, aplicada em exatamente duas fronteiras:

1. `metrosPorHoraPorFita = velocidadeCmMin × 60 / 100`, sem arredondamento desnecessário;
2. `producaoBrutaDecimal = metrosPorHoraPorFita × quantidadeFitas × horasProdutivasPorDia × diasProdutivos`;
3. `producaoBruta = producaoBrutaDecimal` arredondada para zero casas com `HALF_EVEN`;
4. `desperdicioMetros = producaoBruta × percentualDesperdicio / 100`, preservado como decimal;
5. `producaoLiquidaDecimal = producaoBruta − desperdicioMetros`;
6. `producaoLiquida = producaoLiquidaDecimal` arredondada para zero casas com `HALF_EVEN`;
7. quando houver meta, `saldo = producaoLiquida − metaMetros`; sem meta, o saldo é ausente.

As entradas exigem velocidade, fitas, horas e dias estritamente positivos; desperdício no intervalo `0 <= percentual < 100`; e meta, quando presente, não negativa. A produção bruta, a produção líquida e o saldo são metros inteiros no contrato inicial. O desperdício permanece decimal.

# 18. Conclusão

**Sim, as relações do núcleo matemático estão suficientemente conhecidas e o contrato numérico de R1.2 está aprovado**: conversão, fatores de capacidade, ordem do desperdício, saldo, representação decimal, arredondamento e regressões estão determinados. O calendário foi reconstruído apenas como referência; suas colisões e políticas definitivas ficam para R2. A unidade `pFita` e os limites operacionais de entrada ainda exigem confirmação/decisão antes de expor contratos finais de UI.
