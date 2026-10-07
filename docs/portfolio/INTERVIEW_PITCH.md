# ProdTime — apresentação para entrevista

Tempos aproximados, sujeitos a ensaio. Ler de forma natural e selecionar a versão conforme o espaço disponível. As falas descrevem funcionalidades atuais e possibilidades futuras, sem promessa operacional.

## Pitch de 30 segundos

Desenvolvi o ProdTime para estimar produção, prazo e viabilidade de metas na fabricação de fitas têxteis. Separei as regras da interface Android para testar cálculos e calendário. O desafio foi preservar resultados do VBA com arredondamento explícito. Testes JVM e validação em aparelho sustentam os cenários documentados. A integração com dados reais de máquinas é uma possibilidade futura.

## Pitch de 60 segundos

O ProdTime nasceu de cálculos manuais de produção e de uma calculadora VBA que eu havia desenvolvido. O objetivo foi levar essa lógica para um aplicativo Android independente, com Kotlin e Compose. Separei o domínio da interface para testar as regras sem depender do Android.

Os desafios incluíram o arredondamento em duas etapas, os feriados coincidentes com fins de semana e a busca da primeira data suficiente para uma meta. Usei BigDecimal e java.time, mantendo os mesmos motores nos três fluxos.

As regressões reproduzem os resultados históricos de duas e três fitas. A suíte JVM e a validação física no Samsung sustentam os cenários avaliados. O aplicativo também informa déficit e mínimo de fitas. Persistência e consulta à disponibilidade real de máquinas continuam como evoluções futuras.

## Pitch de 2 minutos

O ProdTime é um aplicativo Android que desenvolvi como Projeto Integrador de Análise e Desenvolvimento de Sistemas na Gran Faculdade. Ele surgiu de uma necessidade real na produção de fitas têxteis: estimar quantidades e prazos com cálculos antes manuais. Uma calculadora VBA que eu havia desenvolvido foi a referência para explicitar essas regras.

A decisão arquitetural foi separar a interface Compose de um domínio Kotlin puro. Assim, capacidade, calendário e feriados podem ser testados sem Android e reutilizados na estimativa de produção, no prazo e na verificação de metas. A navegação usa estado Compose, e os feriados ficam em memória durante a sessão.

Um desafio foi definir o arredondamento. Usei BigDecimal e HALF_EVEN nas saídas bruta e líquida, calculando o desperdício sobre a bruta já arredondada. Outro foi o calendário: cada data é classificada uma vez, evitando descontar duas vezes um feriado que também seja fim de semana. No prazo, a busca termina na primeira data suficiente. Na viabilidade, o mínimo de fitas reutiliza o mesmo cálculo de capacidade.

As regressões reproduzem oito mil oitocentos e quarenta e seis metros com duas fitas e treze mil duzentos e setenta com três, sob as condições documentadas. Existem cento e dezoito métodos anotados como testes nas fontes JVM, além dos registros de execução e build. Também houve validação física dos três fluxos e refinamentos de teclado, cópia e entradas no Samsung.

O resultado é uma estimativa rastreável, dependente dos parâmetros informados. Não consulta máquinas em tempo real. Persistência, histórico e disponibilidade real de máquinas são possibilidades futuras, que exigem novos requisitos e validação.

## Apoio para ensaio

As versões têm aproximadamente 58, 126 e 261 palavras, excluindo títulos e notas. A 120–140 palavras/minuto, a fala isolada ocupa cerca de 25–29 s, 54–63 s e 1min52s–2min11s. Nomes técnicos e pausas podem alterar esses tempos; não foi realizado ensaio cronometrado.

Ao perguntarem pela contribuição individual, usar a descrição factual: autoria da calculadora de origem e do projeto, definição de problema/escopo, revisão e validação local/física, com assistência de ferramentas de desenvolvimento na implementação e revisão. Não apresentar a implementação como escrita manual exclusiva. Consultar [destaques técnicos](TECHNICAL_HIGHLIGHTS.md) e [ficha factual](PROJECT_FACT_SHEET.md) para responder perguntas adicionais.
