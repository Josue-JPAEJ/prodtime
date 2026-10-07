# Identidade do ProdTime — R10.1

## Nome, finalidade e autoria

Nome oficial: **ProdTime**. Descrição: **Planejamento rápido de capacidade e prazo para produção de fitas têxteis.** Nome e descrição são recursos de string compartilhados pela Home/Sobre; o manifest referencia a descrição. Package/applicationId permanecem `br.com.prodtime`.

Desenvolvido por Josué Paulo Alexandrina, no projeto acadêmico de Análise e Desenvolvimento de Sistemas (ADS), Gran Faculdade. Contato: josue_jpaej@hotmail.com. A tela Sobre contém somente esses dados fornecidos, sem telefone, endereço ou identificadores pessoais adicionais.

O produto estima capacidade, prazo e viabilidade a partir de parâmetros informados. Resultados não representam promessa operacional, e a identidade não acrescenta ganhos comerciais ou recursos ao domínio.

## Conceito visual

Símbolo original composto por relógio de traço espesso e faixa contínua que sugere fita e passagem do tempo. Sem texto, logotipo de terceiro, engrenagens ou detalhes industriais pequenos. Construído como vetor Android; os SVGs documentais representam os mesmos paths. Não utiliza arte de terceiros nem imagem gerada para substituir evidências.

Fundo `#6650A4`, igual ao `Purple40` da paleta local já existente; símbolo branco. O launcher tem fundo estável, enquanto a UI continua seguindo Material 3 e cores dinâmicas do sistema. O símbolo está centralizado na área segura do viewport de 108 × 108; a máscara pode ser circular ou quadrado arredondado.

![Prévia gráfica das máscaras circular, arredondada e temática](launcher-preview.png)

A imagem acima é uma **prévia gráfica**, não captura de launcher ou validação em dispositivo. O exemplo temático ilustra tingimento; as cores reais são escolhidas pelo sistema. [Vetor documental](prodtime-icon.svg).

## Recursos Android

- `app/src/main/res/drawable/ic_launcher_foreground.xml`: vetor branco de relógio/fita e silhueta monocromática.
- `app/src/main/res/drawable/ic_launcher_background.xml`: plano de fundo sólido com `@color/prodtime_launcher_background`.
- `mipmap-anydpi-v26/ic_launcher.xml` e `ic_launcher_round.xml`: ícones adaptativos para API 26–32.
- `mipmap-anydpi-v33/ic_launcher.xml` e `ic_launcher_round.xml`: mesmos componentes com `monochrome` para ícones temáticos a partir da API 33.
- `mipmap-{mdpi,hdpi,xhdpi,xxhdpi,xxxhdpi}/ic_launcher*.webp`: variantes raster lossless de 48, 72, 96, 144 e 192 px, derivadas dos mesmos paths, com versão redonda transparente.
- Os antigos adaptativos sem qualificador foram substituídos pelos diretórios por API. A API mínima permanece 26; nenhuma dependência ou configuração Gradle foi adicionada.

O manifest continua usando `@mipmap/ic_launcher`, `@mipmap/ic_launcher_round` e `@string/app_name`. Para manutenção, usar o vetor Android como fonte de verdade, preservar proporções e área segura, e atualizar os rasters/SVGs documentais quando o símbolo mudar. Não incluir textos pequenos no ícone.

## Sobre e acessibilidade

Acesso discreto abaixo de “Configurar feriados”. Sobre utiliza `Destination.About`, o Back existente e “Voltar”; sem nova biblioteca de navegação. Textos usam `stringResource`, cores/tipografia Material, rolagem e título semântico. Contato selecionável; não há envio automático de mensagens. Não foi acrescentado ícone decorativo redundante à árvore de acessibilidade.

Previews Compose declarados para claro, escuro e fonte ampliada/tela pequena; ainda precisam ser renderizados/revisados em ambiente Android. Checklist em `docs/ux/ACCESSIBILITY_CHECKLIST.md`. A prévia de máscaras não comprova funcionamento real de launchers específicos.

## Versão e estado

Versão atual **1.0.0**, código **1**, confirmados no Gradle e nos APKs debug/release de R11.1. Release candidate local; nenhuma tag ou GitHub Release criada. Package preservado.

R10 concluído. A aprovação física de ícone/Home/Sobre foi informada pelo autor em R10.2; a execução do teste instrumentado continua sem registro. R11.1 concluído com preparação técnica e achados de auditoria aceitos explicitamente pelo proprietário; R11.2–R11.4 não iniciados. O artigo e as capturas acadêmicas já aprovadas não foram alterados para simular a nova identidade.
