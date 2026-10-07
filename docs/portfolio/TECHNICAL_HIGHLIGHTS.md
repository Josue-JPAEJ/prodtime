# ProdTime — destaques técnicos para entrevistas

Use as respostas para discutir decisões sustentadas pelo projeto. A autoria e a contribuição individual estão na [descrição do portfólio](PORTFOLIO_PROJECT.md); implementação/revisão tiveram assistência de ferramentas de desenvolvimento. Os registros de validação não devem ser apresentados como experimentos novos realizados ao preparar estes materiais.

## 1. BigDecimal e HALF_EVEN

- **O que foi feito:** Cálculo decimal e duas fronteiras de arredondamento integral: bruta e líquida; desperdício sobre a bruta já arredondada.
- **Por que foi importante:** Preserva uma política explícita e as regressões conhecidas, evitando conversões intermediárias por Double/Float.
- **Pergunta provável:** Por que não arredondar apenas no fim?
- **Resposta sugerida:** O legado armazena a bruta antes de descontar desperdício. O domínio preserva essa sequência deliberadamente; HALF_EVEN escolhe o inteiro par em empates.

[Fonte de conferência](../domain/CALCULATION_SPEC.md).

## 2. Regras determinísticas

- **O que foi feito:** As mesmas entradas e políticas alimentam os mesmos motores, sem modelo preditivo.
- **Por que foi importante:** Permite conferir premissas e repetir resultados nos cenários especificados.
- **Pergunta provável:** Essa recomendação usa IA?
- **Resposta sugerida:** Não. A viabilidade aplica capacidade e calendário às entradas e encontra o mínimo suficiente de fitas; não prevê dados de máquinas.

[Fonte de conferência](../product/BUSINESS_RULES.md).

## 3. Separação UI/domínio

- **O que foi feito:** UI coleta/valida entradas e apresenta resultados; motores são Kotlin puro, sem Android/Compose.
- **Por que foi importante:** Torna cálculos testáveis na JVM e reutilizáveis nos três fluxos.
- **Pergunta provável:** Você usou ViewModel, repository ou arquitetura em camadas completa?
- **Resposta sugerida:** Não acrescentei essas abstrações sem necessidade. A implementação usa estado Compose e composição direta das calculadoras de domínio.

[Fonte de conferência](../architecture/ARCHITECTURE.md).

## 4. java.time

- **O que foi feito:** LocalDate representa datas; MonthDay representa feriados anuais.
- **Por que foi importante:** Evita calcular datas produtivas por manipulação textual dependente de localidade.
- **Pergunta provável:** Como representa feriado recorrente?
- **Resposta sugerida:** Guardo dia/mês com MonthDay e resolvo LocalDate para cada ano do período antes de calcular o calendário.

[Fonte de conferência](../domain/HOLIDAY_SPEC.md).

## 5. Calendário produtivo

- **O que foi feito:** Pontas são filtradas antes da classificação; cada data recebe uma decisão produtiva segundo as políticas.
- **Por que foi importante:** Evita dupla subtração em colisões e permite período válido com zero dias produtivos.
- **Pergunta provável:** Trabalhar em feriado torna qualquer domingo produtivo?
- **Resposta sugerida:** Não. A política remove somente o bloqueio do feriado; um domingo excluído continua não produtivo.

[Fonte de conferência](../domain/CALENDAR_SPEC.md).

## 6. Feriados anuais/específicos

- **O que foi feito:** HolidayResolver converte definições em um Set de datas concretas, deduplicando ocorrências.
- **Por que foi importante:** Separa recorrência da classificação do calendário e mantém comportamento comum aos fluxos.
- **Pergunta provável:** O que acontece com 29 de fevereiro em ano comum?
- **Resposta sugerida:** A ocorrência anual não é criada nem deslocada. Datas coincidentes contam uma vez; não há importação automática de calendário externo.

[Fonte de conferência](../domain/HOLIDAY_SPEC.md).

## 7. Prazo progressivo

- **O que foi feito:** A partir do início, percorre datas e recalcula capacidade acumulada quando há novo dia produtivo.
- **Por que foi importante:** Encontra a primeira data suficiente usando o mesmo arredondamento do motor de capacidade.
- **Pergunta provável:** O usuário informa uma data final?
- **Resposta sugerida:** Não. Ele informa meta e início; a conclusão é resultado. A busca tem horizonte técnico finito e os testes verificam a primeira data suficiente.

[Fonte de conferência](../domain/PRODUCTION_DEADLINE_SPEC.md).

## 8. Mínimo de fitas

- **O que foi feito:** Busca exponencial delimita uma quantidade suficiente e busca binária encontra o mínimo, reutilizando a capacidade.
- **Por que foi importante:** Preserva arredondamento e evita uma fórmula paralela para a recomendação.
- **Pergunta provável:** Por que não dividir a meta pela produção de uma fita?
- **Resposta sugerida:** A suficiência é verificada pelo próprio motor arredondado. Isso permite testar a minimalidade; em período sem dias produtivos, a recomendação não se aplica.

[Fonte de conferência](../domain/PRODUCTION_VIABILITY_SPEC.md).

## 9. Compose e Material 3

- **O que foi feito:** Home, formulários, feriados e Sobre usam componentes Compose, tokens Material e navegação por estado.
- **Por que foi importante:** Mantém interação e apresentação coerentes sem adicionar biblioteca de navegação.
- **Pergunta provável:** Como os feriados chegam aos três fluxos?
- **Resposta sugerida:** Uma coleção em memória na composição principal é compartilhada com as telas. Não há banco ou repository persistente.

[Fonte de conferência](../architecture/ARCHITECTURE.md).

## 10. Acessibilidade

- **O que foi feito:** Semântica de títulos/controles, resultados textuais, altura mínima de ações, rolagem e suporte a fonte ampliada.
- **Por que foi importante:** Ajuda a interpretar controles e resultados em telas pequenas e com texto ampliado.
- **Pergunta provável:** Você comprovou acessibilidade em todos os dispositivos?
- **Resposta sugerida:** Não. Há revisão e verificações documentadas nos cenários avaliados; isso não equivale a auditoria universal ou validação TalkBack registrada.

[Fonte de conferência](../ux/ACCESSIBILITY_CHECKLIST.md).

## 11. Sanitização Unicode

- **O que foi feito:** Remove whitespace, espaços Unicode e invisíveis previstos antes do parsing e no campo numérico.
- **Por que foi importante:** Corrige colagem com resíduos invisíveis sem aceitar texto inválido como número.
- **Pergunta provável:** Você remove qualquer caractere não numérico?
- **Resposta sugerida:** Não. A sanitização remove apenas os caracteres previstos. Parsing e validações continuam rejeitando entradas inválidas; vírgula ou ponto são aceitos nos decimais.

[Fonte de conferência](../process/VALIDATIONS.md).

## 12. Limites operacionais

- **O que foi feito:** Guardas de entrada para velocidade, fitas, horas, desperdício e meta, separadas dos motores.
- **Por que foi importante:** Impede valores fora do escopo operacional sem alterar contratos matemáticos históricos.
- **Pergunta provável:** Quais são os limites e onde ficam?
- **Resposta sugerida:** Na apresentação: velocidade maior que zero e menor que 1.000 cm/min; 1–999.999 fitas; horas maiores que zero e até 24; desperdício de 0% a menos de 100%; meta inteira de 1–999.999.999 m.

[Fonte de conferência](../product/BUSINESS_RULES.md).

## 13. Testes JVM

- **O que foi feito:** Fontes com 118 métodos @Test: capacidade, calendário, feriados, fluxos e apresentação.
- **Por que foi importante:** Verifica contratos e fronteiras com cenários rastreáveis, sem depender de aparelho para o domínio.
- **Pergunta provável:** Isso significa 100% de cobertura ou 118 testes executados sempre?
- **Resposta sugerida:** Não. É contagem das fontes. Há resultados de execução registrados; tarefas UP-TO-DATE não demonstram nova execução de cada método e não há percentual de cobertura medido.

[Fonte de conferência](../testing/REGRESSION_MATRIX.md).

## 14. Regressões do VBA

- **O que foi feito:** Comparação com 8.846 m e 13.270 m usando 25 cm/min, 16 h/dia, 19 dias, 3% e duas/três fitas.
- **Por que foi importante:** Ancora a migração em resultados históricos sem copiar coerções e fragilidades do legado indiscriminadamente.
- **Pergunta provável:** O resultado coincide para qualquer entrada do VBA?
- **Resposta sugerida:** Não afirmo equivalência universal. Os casos conhecidos são reproduzidos sob suas premissas; domínio decimal e calendário têm contratos explícitos próprios.

[Fonte de conferência](../domain/CALCULATION_SPEC.md).

## 15. Validação física

- **O que foi feito:** Samsung SM-A066M usado para conferir três jornadas, teclado/cursor, rolagem, cópia, entradas e feriados.
- **Por que foi importante:** Complementa testes de regras com observação da interação e levou a refinamentos registrados.
- **Pergunta provável:** O teste instrumentado de identidade também foi executado?
- **Resposta sugerida:** Sua compilação está registrada, a execução não. A aprovação física da identidade foi informada pelo autor em R10.2; são evidências distintas.

[Fonte de conferência](../process/VALIDATIONS.md).

## 16. Adaptive launcher icon

- **O que foi feito:** Ícone original relógio/fita com vetores, adaptativos API 26+, versão monocromática API 33+ e rasters por densidade.
- **Por que foi importante:** Mantém proporção e área segura em máscaras diferentes, sem texto pequeno no símbolo.
- **Pergunta provável:** A imagem de três máscaras prova funcionamento no launcher?
- **Resposta sugerida:** Não. É prévia gráfica. Os recursos foram empacotados no APK; a aprovação física do ícone no Samsung é relato do autor registrado separadamente.

[Fonte de conferência](../branding/BRAND_IDENTITY.md).
