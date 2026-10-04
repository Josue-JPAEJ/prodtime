# Instruções para agentes — ProdTime

Estas instruções valem para todo o repositório. Instruções mais específicas, quando existirem em diretórios descendentes, complementam ou substituem este arquivo em seu próprio escopo.

## Comunicação e investigação

- Responda e documente em português, com linguagem técnica e objetiva.
- Antes de alterar qualquer arquivo, examine o código e a estrutura reais. O código vigente é a fonte definitiva da implementação.
- Respeite integralmente a arquitetura, os contratos, as versões e as convenções existentes.
- Não invente regras de negócio, fórmulas, dados históricos ou decisões. Registre incertezas como pendências.
- Execute somente o escopo recebido e prefira a menor alteração correta, definitiva e verificável.

## Implementação

- Evite gambiarra, código placeholder, mocks como solução final, arquitetura antecipada e refatoração ampla sem necessidade concreta.
- Mantenha regras e cálculos de negócio fora da UI e independentes do Compose.
- Crie ViewModels, camadas, packages e abstrações somente quando uma necessidade real os justificar.
- Considere UX mobile, acessibilidade, responsividade, modos claro/escuro e manutenção.
- Preserve a compatibilidade do projeto e não atualize dependências, Kotlin, Compose, Gradle ou SDK sem necessidade explícita.
- Atualize a documentação relacionada sempre que uma regra, decisão, contrato ou validação mudar.

## Skills especializadas

Consulte apenas as Skills aplicáveis, sem usá-las para substituir a inspeção do projeto:

- `.agents/skills/prodtime-android/SKILL.md`: alterações em Kotlin, Android, Compose, apresentação, navegação, UX ou acessibilidade.
- `.agents/skills/prodtime-domain/SKILL.md`: definição ou alteração de regras produtivas, fórmulas, unidades, precisão, datas, calendário, capacidade, prazo, desperdício ou recomendações.
- `.agents/skills/prodtime-testing/SKILL.md`: planejamento e implementação de testes, regressões históricas ou validações Android/manuais.

Quando uma tarefa atravessar esses temas, use as Skills em conjunto e preserve a separação entre UI, domínio e validação.

## Validação e entrega

- Execute testes proporcionais ao risco e diretamente relacionados à mudança.
- Não declare sucesso sem evidência observável.
- Relate testes executados, resultados, limitações e testes relevantes não executados.
- Antes de finalizar, revise `git diff`, execute `git diff --check` e confira `git status` para detectar alterações acidentais.
