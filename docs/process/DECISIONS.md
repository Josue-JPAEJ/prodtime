# Registro de decisões

Este arquivo usa um ADR simplificado. Cada decisão contém status, contexto, decisão e consequências. Novas decisões devem ser registradas sem reescrever o histórico.

## ADR-001 — Android nativo com Kotlin e Compose

- **Status:** aceita.
- **Contexto:** o produto é mobile e o bootstrap existente usa Android nativo.
- **Decisão:** manter Kotlin e Jetpack Compose como stack da aplicação.
- **Consequências:** mudanças devem seguir as versões e convenções reais do projeto; outras stacks móveis não fazem parte do MVP.

## ADR-002 — Minimum SDK API 26

- **Status:** aceita.
- **Contexto:** o bootstrap define `minSdk = 26`.
- **Decisão:** preservar API mínima 26.
- **Consequências:** APIs e testes devem respeitar essa compatibilidade; qualquer alteração exige decisão explícita.

## ADR-003 — MVP independente de backend

- **Status:** aceita.
- **Contexto:** as duas estimativas principais podem ser resolvidas localmente e o prazo acadêmico é limitado.
- **Decisão:** não incluir backend, API remota ou banco remoto no MVP.
- **Consequências:** o cálculo deve ser determinístico e local; sincronização e autenticação ficam fora do escopo.

## ADR-004 — Domínio independente da UI

- **Status:** aceita.
- **Contexto:** cálculo produtivo requer validação isolada e não deve depender do ciclo de vida visual.
- **Decisão:** manter regras e motor matemático independentes de Compose.
- **Consequências:** a UI apenas coleta, coordena e apresenta; estruturas concretas serão criadas sob demanda.

## ADR-005 — Estratégia de branches

- **Status:** aceita.
- **Contexto:** é necessário manter uma linha estável e integrar mudanças incrementais.
- **Decisão:** usar `main` estável, `develop` para integração e branches temporárias para tarefas.
- **Consequências:** mudanças pequenas são revisadas antes de integração; esta decisão não autoriza merges automáticos.

## ADR-006 — ERP e IA fora do MVP

- **Status:** aceita.
- **Contexto:** integrações e previsões ampliam substancialmente risco e escopo.
- **Decisão:** integração com ERP/PCP e inteligência artificial pertencem à visão futura.
- **Consequências:** não devem ser implementadas nem anunciadas como disponíveis no MVP.

## ADR-007 — Finais de linha LF no repositório

- **Status:** aceita.
- **Contexto:** houve aviso de conversão LF para CRLF em ambiente Windows, o que pode gerar diffs editoriais.
- **Decisão:** adotar `.gitattributes` simples com detecção automática de texto e normalização para LF, preservando binários comuns.
- **Consequências:** novos checkouts ficam consistentes entre plataformas sem renormalizar em massa os arquivos existentes nesta tarefa.
