# ProdTime

Aplicativo Android em desenvolvimento para estimar capacidade e prazo na produção de fitas têxteis.

## Propósito e problema

Estimativas historicamente manuais dependiam de profissionais experientes, concentravam conhecimento e atrasavam decisões da produção e de vendas. O ProdTime busca tornar essa análise simples, guiada, rápida e acessível, respondendo:

1. **Quanto consigo produzir em determinado período?**
2. **Quando conseguirei concluir determinada quantidade?**

A ideia evolui de uma calculadora criada pelo autor e integrada a um ERP legado em VBA. O ProdTime moderniza e isola essa necessidade; não pretende substituir um ERP ou PCP completo no MVP.

## Status atual

O MVP funcional está implementado. As etapas R0–R8 foram concluídas, incluindo os três fluxos, calendário produtivo, feriados em memória, validações automatizadas e validação física. O R9, dedicado à documentação acadêmica, está em andamento.

## Stack atual

- Android nativo;
- Kotlin;
- Jetpack Compose e Material 3;
- Minimum SDK API 26;
- package `br.com.prodtime`.

## Escopo resumido do MVP

- **Quanto consigo produzir?** — estima a produção em um período a partir de velocidade, fitas, jornada, desperdício e calendário produtivo;
- **Quando vou terminar?** — estima a data de conclusão de uma quantidade alvo;
- **Verificar uma meta** — avalia a viabilidade e recomenda, de forma determinística e explicável, o mínimo e o adicional de fitas;
- configurar feriados anuais ou de data específica, mantidos em memória durante a sessão;
- operar localmente, sem backend.

Autenticação, backend, ERP/PCP, múltiplas fábricas, IoT, capacidade dinâmica baseada em máquinas, IA e previsão por histórico estão fora do MVP acadêmico atual.

## Roadmap resumido

O rollout avança do motor matemático e calendário para os dois modos de cálculo, recomendações, UX, regressão VBA, acessibilidade e entrega acadêmica. Consulte [`docs/process/ROLLOUT.md`](docs/process/ROLLOUT.md). Integrações com ERP/PCP, capacidade variável e IA são visão futura, não funcionalidades existentes.

## Documentação

- [Escopo do produto](docs/product/PROJECT_SCOPE.md)
- [Regras de negócio](docs/product/BUSINESS_RULES.md)
- [Referência do legado](docs/product/LEGACY_REFERENCE.md)
- [Arquitetura direcional](docs/architecture/ARCHITECTURE.md)
- [Rollout](docs/process/ROLLOUT.md)
- [Decisões](docs/process/DECISIONS.md)
- [Validações](docs/process/VALIDATIONS.md)
- [Referências acadêmicas](docs/academic/REFERENCES.md)
- [Instruções para agentes](AGENTS.md)

## Executar o aplicativo

Pré-requisitos: JDK compatível, Android SDK configurado e um emulador ou aparelho com depuração USB disponível.

```bash
./gradlew assembleDebug
./gradlew installDebug
```

Após a instalação, abra o ProdTime no dispositivo. O aplicativo é Android nativo, desenvolvido em Kotlin com Jetpack Compose e Material 3, funciona localmente e não depende de backend. Em Windows, use `gradlew.bat` nos mesmos comandos.

> O ProdTime está em desenvolvimento. A documentação separa explicitamente o que já existe, o escopo planejado do MVP e a visão futura.
