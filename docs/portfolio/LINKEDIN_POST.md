# Post do ProdTime para LinkedIn

Desenvolvi o ProdTime, um aplicativo Android para estimar capacidade e prazo na produção de fitas têxteis.

O projeto nasceu de um problema real: cálculos manuais dependiam de profissionais experientes e concentravam conhecimento operacional. Eu já havia desenvolvido uma calculadora em VBA para apoiar essas estimativas. No ProdTime, essa lógica ganhou regras explícitas e uma interface móvel independente.

O aplicativo reúne três fluxos:
• Quanto consigo produzir em um período?
• Quando vou terminar uma quantidade?
• A configuração atual atende à meta e quantas fitas adicionais seriam necessárias?

O calendário considera sábados, domingos e feriados anuais ou de data específica. A stack usa Kotlin, Jetpack Compose e Material 3, com BigDecimal nos cálculos e java.time nas datas.

Os principais aprendizados foram separar domínio e interface, definir as fronteiras de arredondamento HALF_EVEN, tratar colisões no calendário e melhorar entradas, teclado e acessibilidade a partir da validação em aparelho.

Os testes JVM incluem regressões que reproduzem 8.846 m e 13.270 m nos cenários históricos. Os três fluxos também foram validados em um Samsung SM-A066M.

O MVP funciona localmente, com feriados em memória e estimativas condicionadas aos parâmetros informados. Não consulta máquinas em tempo real nem garante disponibilidade operacional.

O ProdTime é meu Projeto Integrador em Análise e Desenvolvimento de Sistemas, na Gran Faculdade. Foi uma oportunidade de transformar conhecimento do processo em software testável e rastreável, com apoio de ferramentas de desenvolvimento.

#Android #Kotlin #DesenvolvimentoDeSoftware
