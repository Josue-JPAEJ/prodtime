# Escopo do projeto ProdTime

## Problema

Estimativas de capacidade e prazo na produção de fitas têxteis eram feitas manualmente e dependiam de profissionais experientes. Isso concentrava conhecimento, consumia tempo e atrasava respostas operacionais e comerciais.

## Público

Profissionais que precisam estimar capacidade ou prazo de produção de fitas, incluindo produção e vendas, sem transformar o aplicativo em um ERP ou PCP completo.

## Objetivo

Oferecer uma experiência mobile simples, guiada, rápida e acessível para responder:

1. **Quanto consigo produzir em determinado período?**
2. **Quando conseguirei concluir determinada quantidade?**

## Escopo do MVP acadêmico

### Modo A — Quanto consigo produzir?

Entradas previstas: data inicial e final, velocidade em cm/min, quantidade de fitas, horas úteis por dia, percentual de desperdício e calendário produtivo, incluindo configurações de sábados, domingos e feriados.

Resultados previstos: dias produtivos, horas produtivas, produção estimada, desperdício estimado e saldo quando aplicável.

### Modo B — Quando vou terminar?

Entradas previstas: quantidade alvo, data inicial, velocidade em cm/min, quantidade de fitas, horas úteis por dia, percentual de desperdício e calendário produtivo.

Resultados previstos: tempo produtivo necessário, dias produtivos e data estimada de conclusão.

### Recomendações determinísticas

O MVP apresenta uma análise determinística de viabilidade para uma meta de produção em determinado período. A partir das mesmas regras validadas de capacidade e calendário, informa se a configuração atual atende à meta, qual o déficit ou excedente, a quantidade mínima de fitas necessária e quantas fitas adicionais seriam necessárias.

Essa funcionalidade não representa PCP, otimização automática, IA ou promessa operacional de atendimento.

## Fora do MVP atual

- autenticação e gestão de usuários;
- backend, API remota e banco remoto;
- ERP ou PCP completo;
- múltiplas fábricas;
- IoT e telemetria de máquinas;
- integração com o ERP Web;
- planejamento dinâmico baseado em máquinas reais;
- inteligência artificial;
- previsão baseada em histórico.

## Critérios de sucesso

- responder às duas perguntas principais com regras especificadas e validadas;
- apresentar entradas, unidades, premissas e resultados com clareza;
- reproduzir os casos históricos dentro da precisão que vier a ser validada;
- operar de forma determinística, local e independente de backend;
- oferecer fluxo mobile utilizável e acessível;
- manter testes proporcionais para domínio e UI.

## Restrição acadêmica

O ProdTime é o Projeto Integrador final de Análise e Desenvolvimento de Sistemas. O prazo acadêmico limitado exige um MVP pequeno que resolva muito bem um problema específico. A narrativa parte do processo manual, passa pela automação em VBA e pelo ERP legado, acompanha a evolução para um ERP Web e chega ao aplicativo mobile, mantendo integrações e inteligência como evolução posterior.

## Visão futura, não requisito atual

A persistência dos feriados cadastrados é uma evolução futura, fora do MVP acadêmico. No MVP atual, as definições são compartilhadas pelos três fluxos somente durante a sessão e são descartadas quando o processo da aplicação é encerrado.

1. Integração com o novo ERP Web.
2. Integração com PCP para consultar máquinas disponíveis, quantidade de fitas por máquina, ocupações e liberações previstas.
3. Capacidade variável calculada por intervalos. Por exemplo: duas fitas entre 03/11 e 07/11; mais uma a partir de 08/11; e outra máquina com duas fitas a partir de 10/11.
4. IA para analisar históricos, identificar padrões, estimar velocidade real, sugerir desperdício, aprimorar previsões e sugerir cenários.

Nenhum item desta visão deve ser comunicado como existente ou incluído implicitamente no MVP atual.
