# ProdTime 1.0.0

Versão 1.0.0 publicada com tag `v1.0.0` e [GitHub Release](https://github.com/Josue-JPAEJ/prodtime/releases/tag/v1.0.0). Main promovida pelo PR #35; repositório público. APK acadêmico `ProdTime-1.0.0-debug.apk` e `SHA256SUMS.txt` anexados, conforme estado confirmado pelo autor em R11.4. Package `br.com.prodtime`, código 1, API mínima 26 e target/compile 36.

SHA-256 público informado pelo autor:

```text
1162ac6b8f99430cb818460537eb406963fb07b43d5a609bd81a89f675fdeeb5  ProdTime-1.0.0-debug.apk
```

O hash publicado difere do artefato local histórico de R11.1 abaixo. Os registros anteriores foram preservados; o APK público não foi baixado nem novamente inspecionado nesta tarefa. A consulta independente à API retornou `Forbidden`.

Achados de auditoria aceitos pelo proprietário em R11.1B: [relatório](PUBLIC_REPOSITORY_AUDIT.md).

## Funcionalidades

Estimativa de produção em período, primeira data suficiente para uma meta, verificação de viabilidade e indicação do mínimo/adicional de fitas. Calendário com pontas, sábados/domingos e feriados anuais/específicos; feriados em memória. Temas, robustez de entradas, cópia e Sobre permanecem como entregues em R0–R10.

## Stack

Android nativo, Kotlin, Compose, Material 3, BigDecimal, java.time, Gradle Wrapper e JUnit 4. Nenhuma dependência ou regra produtiva alterada no candidate.

## Validação

Ambiente Cloud: JDK 21, SDK 36 e Gradle 8.13 do wrapper, após carregar a configuração local do Android.

| Tarefa | Resultado desta preparação |
|---|---|
| clean | Executada no comando conjunto; build aprovado. |
| testDebugUnitTest | Executada; nove relatórios XML, 118 testes, zero falhas/erros/ignorados. |
| assembleDebug | APK gerado; build conjunto aprovado. |
| assembleDebugAndroidTest | APK de teste compilado; teste não executado em dispositivo. |
| assembleRelease | APK unsigned gerado; build separado aprovado. |

Comandos: `bash ./gradlew clean testDebugUnitTest assembleDebug assembleDebugAndroidTest` — BUILD SUCCESSFUL in 36s, 66 actionable tasks: 66 executed. `bash ./gradlew assembleRelease` — BUILD SUCCESSFUL in 37s, 47 actionable tasks: 47 executed.

Warning existente de depreciação de LocalClipboardManager em FormComponents.kt:218 permaneceu nos dois builds, sem bloquear. Não houve instalação em dispositivo ou novo teste físico de 1.0.0; as aprovações físicas anteriores continuam históricas.

## APK — preparação histórica R11.1

Artefatos realmente gerados, em diretórios locais ignorados:

| Caminho | Bytes | Package | versionName / versionCode | min / target SDK | Assinatura |
|---|---:|---|---|---|---|
| app/build/outputs/apk/debug/app-debug.apk | 10.823.792 | br.com.prodtime | 1.0.0 / 1 | 26 / 36 | Verificada; certificado Android Debug. |
| app/build/outputs/apk/release/app-release-unsigned.apk | 7.094.548 | br.com.prodtime | 1.0.0 / 1 | 26 / 36 | Ausente; apksigner não verifica. |
| app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk | 1.000.976 | br.com.prodtime.test | Ausentes no badging | 26 / 36 | Verificada; certificado Android Debug. |

Metadados inspecionados com aapt dump badging e assinatura com apksigner verify --verbose --print-certs, Build-Tools 36.0.0.

**Recomendado para entrega acadêmica local:** `dist/ProdTime-1.0.0-debug.apk`, cópia byte a byte do APK debug, assinado pelo tooling Android. É compilação debug, não release assinado de produção. O APK AndroidTest é um pacote de testes, não distribuição do aplicativo. O release unsigned precisa de assinatura legítima antes de instalação.

Não foi criada chave/keystore de produção nem configuradas credenciais. Nenhum APK, certificado privado ou artefato de dist será versionado. Metadados locais em `dist/APK_METADATA.json`.

## Checksums — artefatos locais históricos R11.1

SHA-256 dos APKs gerados:

```text
d183aae4afd633f42694c844cdc65805f70a679870fe7be9608d994e20731fa8  app/build/outputs/apk/debug/app-debug.apk
e819d3f878eb67595c2d8d0033717dfc1f3ea46f2842580af422c0e45383b8c8  app/build/outputs/apk/release/app-release-unsigned.apk
6f5967e3def4bf78ca6f2eb0d77b2ac8baf34f375ee4a769ee3c70cddf8d0ae8  app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
```

O APK local preparado em R11.1 conserva o hash do debug daquela execução. Conferência local, dentro de dist: `sha256sum -c SHA256SUMS.txt`.

## Limitações

Estimativas condicionadas às entradas, sem dados reais de máquinas, backend, IA ou PCP. Feriados não persistem após encerrar o processo. Distribuição acadêmica publicada com certificado Android Debug, sem assinatura de produção ou nova validação física nesta tarefa.

## Vídeo

https://youtu.be/UrrcNwLq76w — não listado, aproximadamente 5min48s, gravado/revisado/publicado conforme relato do autor em R10.5. A plataforma solicita vídeo de 5 minutos e permite modo não listado, sem máximo indicado na tela relatada.

## Artigo

[Artigo SBC existente](../academic/submission/prodtime_sbc_preview.pdf): 11 páginas, 355.967 bytes, abaixo de 5 MB; não alterado/recompilado nesta preparação. Limite institucional de páginas ainda não informado.

## Instalação

Para teste acadêmico em Android API 26+, com aparelho autorizado via ADB:

```bash
adb devices
adb install -r dist/ProdTime-1.0.0-debug.apk
```

A instalação não foi executada nesta tarefa. Atualização de uma instalação existente exige certificado compatível; não remover automaticamente a instalação anterior. O download acadêmico publicado está na [GitHub Release v1.0.0](https://github.com/Josue-JPAEJ/prodtime/releases/tag/v1.0.0); o caminho local acima corresponde à preparação histórica.

## Consolidação R11.1B

Workspace e artefatos anteriores preservados, sem refazer alteração executável. Executado `bash ./gradlew testDebugUnitTest assembleDebug`: BUILD SUCCESSFUL in 1s, 40 actionable tasks: 40 up-to-date. A suíte não foi novamente executada; os nove relatórios anteriores de 118 testes/zero falhas/erros/ignorados continuam verificáveis. AndroidTest e release não foram repetidos, pois nenhuma alteração executável foi refeita.

APK de distribuição reconferido: 10.823.792 bytes, SHA-256 inalterado, assinatura debug válida (APK Signature Scheme v2), package br.com.prodtime, versão 1.0.0/código 1, mínimo 26/target 36. Nenhuma instalação física executada. LICENSE ausente por decisão atual do proprietário. Achados de histórico aceitos conforme PUBLIC_REPOSITORY_AUDIT.md; histórico e branches preservados.
