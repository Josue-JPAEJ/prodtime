# Lista de tomadas do vídeo ProdTime

Alvo: cinco minutos. Tempos correspondem ao vídeo editado; capturar formulários e resultados em trechos separados, encurtando preenchimento sem ocultar premissas ou alterar respostas. Fala em [NARRATION.md](NARRATION.md); detalhes em [VIDEO_SCRIPT.md](VIDEO_SCRIPT.md).

| Ordem | Tempo | Tela | Ação | Dados necessários | Observação |
|---|---|---|---|---|---|
| 1 | 0:00–0:30 | Apresentação e problema | Apresentar o autor em áudio, com cartela simples de nome, curso e instituição sobre a captura vertical da Home atual. | Josué Paulo Alexandrina; Análise e Desenvolvimento de Sistemas; Gran Faculdade. | Não incluir e-mail na cartela; manter tom acadêmico e natural. |
| 2 | 0:30–1:00 | Origem e objetivo | Manter a Home em tela enquanto explica a evolução manual → VBA → aplicativo. Não é necessário abrir o ERP ou a calculadora legada. | Home atual; descrição oficial do produto. | Integração com o ERP Web é futura; a origem no legado não significa integração atual. |
| 3 | 1:00–1:35 | Home e três jornadas | Mostrar brevemente o ícone real no aparelho, abrir o aplicativo e percorrer os três botões e “Configurar feriados”. Se houver tempo, abrir Sobre e voltar à Home. | App com identidade R10.1; Home e ícone atuais. | Ícone somente em gravação real do launcher; a prévia documental não é screenshot. Sobre é opcional, sem leitura do e-mail. Reservar o restante do bloco para navegação. |
| 4 | 1:35–2:10 | Quanto consigo produzir? | Abrir o fluxo, mostrar as entradas conferidas e tocar “Calcular produção”. Ocultar o teclado e rolar devagar para os resultados. | 01/10/2026–27/10/2026; 25 cm/min; 3 fitas; 16 h/dia; 3%; pontas incluídas; sábados/domingos/feriados não trabalhados; coleção vazia. | Incluir ambas as pontas; nenhum feriado cadastrado nesta parte. Mostrar 13.270 m / 19 dias / 13.680 m / 410,4 m. Reduzir apenas trechos de preenchimento na edição. |
| 5 | 2:10–2:45 | Quando vou terminar? | Voltar à Home, abrir prazo, conferir entradas e tocar “Calcular prazo”. Mostrar conclusão, dias, produção e saldo. | Meta 10000; início 05/10/2026; 25 cm/min; 3 fitas; 16 h/dia; 3%; início incluído; sem fins de semana/feriados. | Repreencher cada formulário: não presumir transferência das entradas entre fluxos. Início incluído. Não apresentar 23/10/2026 como data informada. |
| 6 | 2:45–3:25 | Verificar uma meta | Voltar à Home, abrir viabilidade, conferir entradas e tocar “Verificar meta”. Mostrar déficit, mínimo e adicional. A comparação com três fitas é apenas verbal. | 01/10/2026–27/10/2026; meta 10000; 25 cm/min; 2 fitas; 16 h/dia; 3%; pontas incluídas; sem fins de semana/feriados. | Manter o resultado insuficiente em tela; não refazer com três fitas no corte principal. Não prometer prazo operacional ou disponibilidade de máquinas. |
| 7 | 3:25–3:50 | Calendário e feriados | Voltar à Home e abrir “Configurar feriados”. Mostrar o cadastro anual e a opção de data específica. Depois voltar e abrir produção para mostrar o switch “Trabalhar em feriados”, sem recalcular os casos anteriores. | Anual: Natal, 25/12; específica: nome demonstrativo “Data específica”, 25/12/2026. Apenas exemplos de cadastro; não implicam calendário oficial. | O switch fica nos formulários, não na tela de cadastro. Preparar este trecho separado depois dos cálculos. Não refazer a comparação de dezembro para manter a duração. |
| 8 | 3:50–4:25 | Arquitetura, testes e validação | Mostrar brevemente as seções Arquitetura e Validação do README, em recortes legíveis para vídeo vertical, ou manter um resultado do app em tela. Não abrir a IDE. | README, matriz de regressão e histórico de validações; 118 métodos @Test nas fontes JVM. | Dizer “anotados como testes” corresponde a @Test nas fontes; não significa 118 execuções em toda tarefa. Ler o modelo como “Samsung esse eme, a zero sessenta e seis eme”. Teste instrumentado compilado não deve ser descrito como executado. |
| 9 | 4:25–4:50 | Limitações e trabalhos futuros | Retornar à Home atual; manter imagem estável durante a explicação. Não mostrar interfaces de recursos futuros. | Limitações e visão futura já documentadas. | Não afirmar economia de tempo, ganho de produtividade ou redução de desperdício mensurados. |
| 10 | 4:50–5:00 | Encerramento | Manter a Home e exibir cartela final com nome, curso e instituição. | Nome, curso e instituição; sem e-mail. | Fechamento simples, sem chamada comercial. |

## Resultados a conferir antes de gravar

| Fluxo | Resultado esperado no cenário do roteiro |
|---|---|
| Produção | 13.270 m líquidos; 19 dias produtivos; 13.680 m brutos; 410,4 m de desperdício. |
| Prazo | 23/10/2026; 15 dias produtivos; produção 10.476 m; saldo +476 m. |
| Viabilidade | Produção 8.846 m; meta 10.000 m; déficit 1.154 m; mínimo 3 fitas; adicional 1. |

As [quatro capturas reais](../academic/figures/README_FIGURAS.md) são referências de resultado, não substituem a gravação da identidade atual. Não produzir novas respostas para ajustar o vídeo. Se um valor divergir, revisar datas, políticas, feriados e parâmetros antes de gravar.

## Comparação de feriado — referência opcional fora do corte principal

O [histórico de validação](../process/VALIDATIONS.md) registra 01/12/2026–31/12/2026, 28 cm/min, uma fita, 16 h/dia, desperdício 3%, sem sábados/domingos, com Natal anual em 25/12: não trabalhar no feriado resulta em 22 dias e 5.737 m; trabalhar resulta em 23 dias e 5.997 m. Não demonstrar com os parâmetros de outubro nem inserir essa comparação no vídeo principal se ultrapassar o limite. Os exemplos de cadastro anual/data específica no bloco 7 não alegam uma nova validação dessa comparação.
