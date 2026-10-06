# Plano do artigo ProdTime

Este plano orienta a redação futura; não é o artigo final.

## Título provisório

1. **ProdTime: aplicativo móvel para estimativa de capacidade e prazo na produção de fitas têxteis**
2. **Uma solução móvel determinística para estimativas produtivas na indústria de fitas têxteis**
3. **Estimativa de produção, prazo e viabilidade de metas em fitas têxteis por aplicativo Android**

O título definitivo permanece pendente.

## Resumo

Deverá sintetizar contexto e problema; objetivo; recorte do MVP; método de especificação e implementação; arquitetura e stack; evidências de regressão, testes e validação física; resultados verificáveis; limitações; e contribuição. Não incluir métricas não medidas.

## 1. Introdução

Apresentar o contexto das estimativas têxteis, o processo manual e a concentração de conhecimento; delimitar problema e motivação; formular as duas perguntas centrais e a verificação de viabilidade; declarar a contribuição técnica sem linguagem de marketing.

## 2. Trabalhos relacionados / fundamentação

As quatro referências de `docs/academic/REFERENCES.md` são apenas candidatas. Organizar futuramente os temas planejamento e controle da produção têxtil, programação da produção, tingimento/acabamento e manufatura enxuta. Antes de atribuir qualquer argumento, validar externamente metadados e conteúdo integral das fontes.

## 3. Metodologia

Descrever análise do processo legado, engenharia reversa do VBA, separação entre comportamento histórico e contrato novo, especificação matemática e de calendário, implementação incremental R0–R8, testes automatizados, regressões e validação física no Samsung SM-A066M.

## 4. Desenvolvimento da solução

Apresentar arquitetura UI/domínio, os seis componentes do domínio, `BigDecimal` e `java.time`, calendário e feriados, jornadas Compose, viabilidade determinística, sanitização e acessibilidade.

## 5. Resultados e validação

Usar exclusivamente evidências de `ARTICLE_EVIDENCE.md`: regressões de 2/3 fitas; caso de prazo; dois casos de viabilidade; comparação do feriado; suíte e build finais; limites; sanitização; e validação física. Separar resultado automatizado, build, regressão e observação manual.

## 6. Limitações

Registrar operação local, feriados sem persistência entre processos, ausência de dados reais em tempo real, dependência dos parâmetros informados, caráter estimativo sem promessa operacional e ausência de backend, IA e PCP.

## 7. Trabalhos futuros

Apresentar somente a visão documentada: ERP Web, PCP, disponibilidade de máquinas, capacidade variável, persistência, histórico, IA e IoT, sem comunicá-los como entregues.

## 8. Conclusão

Responder se o MVP: (1) estima produção em período; (2) estima a primeira data de atendimento da quantidade; (3) avalia viabilidade e fitas mínimas/adicionais; (4) reproduz regressões conhecidas; e (5) foi validado técnica e fisicamente dentro das limitações declaradas.

## Referências

Candidatas a validação, sem alegação de leitura integral:

- Karacapilidis e Pappis (1996);
- Serafini e Speranza (1992);
- Laoboonlur, Hodgson e Thoney (2006);
- Hodge et al. (2011).

## Requisitos de entrega

**Pendente: confirmar o modelo/template acadêmico exigido pela instituição e as regras específicas da SBC aplicáveis à entrega.** Não estão confirmados no repositório número de páginas, margens, fonte, seções obrigatórias ou formato LaTeX/Word.
