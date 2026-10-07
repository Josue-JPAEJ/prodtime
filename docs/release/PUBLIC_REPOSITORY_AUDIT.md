# Auditoria para publicação do repositório ProdTime

## Base e alcance

Data: 07/10/2026 (America/Sao_Paulo). Base develop `620b70febf6c64c02610e4e24ec5713b9eb21e77`, R10 integrado pelo PR #33, árvore inicialmente limpa. Repositório privado conforme informação do autor; visibilidade não alterada.

Auditoria do checkout e dos objetos alcançáveis por `git rev-list --objects --all`: **67 commits, 289 blobs, 261 blobs textuais e 125 arquivos rastreados na base**. Checkout não superficial (`--is-shallow-repository = false`). O alcance é o histórico acessível pelas referências locais; não certifica branches remotas não trazidas ao checkout, objetos inacessíveis ou conteúdos externos.

## Categorias pesquisadas e método

Inventário com git ls-files, rev-list e cat-file. Varredura de cada versão acessível de arquivo, não apenas HEAD; busca de tokens/chaves também aplicada aos bytes de blobs binários e aos objetos de commit, incluindo mensagens. Metadados autor/committer examinados separadamente. Duas versões históricas do PDF foram extraídas por pdftotext e pesquisadas, sem ocorrências de tokens/chaves de alta confiança. Buscas no checkout com rg complementaram a revisão.

Categorias: passwords/password, senha, secret, token, api_key/apikey, client_secret, private_key, cabeçalhos de chave privada, Authorization/Bearer, prefixos de tokens de serviços, AWS_ACCESS_KEY, URLs de banco/JDBC, credenciais e caminhos pessoais Windows. Inventário de .env, local.properties, .gradle, build, app/build, .idea, keystores, logs, ZIP/APK/AAB, bancos SQLite, backups e temporários.

Foram usadas expressões de alta confiança para tokens/chaves/URLs com credenciais e caminhos, além de buscas amplas por palavras-chave com revisão contextual. Nenhum scanner externo especializado foi instalado; não se afirma detecção universal. As ocorrências amplas anteriores se concentraram em documentação de temas/tokens Material, UX e limitações de ambiente; não eram credenciais. A documentação nova de auditoria menciona categorias, sem incluir valores secretos.

## Resultado de secrets

Não foram identificadas credenciais, tokens válidos ou chaves privadas nas buscas realizadas em arquivos ou histórico. Nenhum arquivo de risco (ambiente, chave, dump, banco, log, APK/AAB/ZIP, temporário) foi encontrado como rastreado na base ou entre caminhos históricos inventariados. O Gradle Wrapper JAR é artefato de tooling já existente, não pacote de distribuição do ProdTime.

A ausência de detecção não significa garantia absoluta. Não foi encontrado segredo real que exigisse revogação ou reescrita; os achados anteriores de dados pessoais e fonte legado foram avaliados e aceitos pelo proprietário em R11.1B.

## Dados pessoais e histórico

Dados explicitamente autorizados para o projeto: Josué Paulo Alexandrina, Gran Faculdade, Análise e Desenvolvimento de Sistemas e josue_jpaej@hotmail.com. Podem permanecer nas fontes/artigo/Sobre.

**Achado avaliado pelo proprietário em R11.1B:** há um e-mail pessoal adicional, inicialmente fora da autorização, em metadados de autor/committer de **43 commits**. Valor omitido. Exemplos: `cb9bcec903b148aedb149bea3ef747d31101de3d` e `620b70febf6c64c02610e4e24ec5713b9eb21e77`. Publicar o repositório exporia também esses metadados. O proprietário avaliou o achado e autorizou explicitamente sua manutenção/exposição no histórico em R11.1B. O valor continua omitido neste documento. Reescrita/sanitização histórica dispensada por decisão explícita; nenhum commit ou branch removido.

Referências bibliográficas e créditos de terceiros preservados são atribuições das fontes, distintas dos dados pessoais adicionais encontrados nos metadados do projeto. As quatro capturas JPG reais não contêm tags EXIF na inspeção realizada. Não foram identificados nomes de clientes, preços ou bases empresariais na revisão textual e nas capturas já documentadas; isso não substitui revisão do titular sobre confidencialidade.

## Fonte VBA e direitos de exposição

Arquivo revisado: `docs/legacy/vba/CalMetrosNaMaq.txt`, introduzido no commit `de74ba8`. Contém a calculadora e handlers de formulário, com vínculos a estruturas do ERP: login, verificação de servidor, relatório em workbook e helpers externos. Não foram encontrados valores de login, credenciais, clientes ou banco embutidos.

`docs/product/LEGACY_REFERENCE.md` atribui a calculadora e o ERP ao autor. Em R11.1B, o proprietário avaliou o fonte histórico e autorizou explicitamente mantê-lo, aceitando sua exposição. O achado anterior foi resolvido por essa decisão; o arquivo e os commits permanecem intactos. Nenhuma remoção ou reescrita foi executada.

Os cenários numéricos deliberadamente fornecidos pelo autor e já aprovados para artigo/README foram mantidos. Não foi criada autorização genérica para outros dados operacionais.

## Arquivos locais e proteção do Git

- .gradle e app/build existem localmente e estão ignorados; inventário após builds encontrou 1.996 arquivos locais ignorados antes de preparar dist.
- local.properties, .idea e build raiz não constam como rastreados; padrões de ignore conferidos. Não havia esses caminhos locais no inventário relevante.
- Não foram encontrados .env, keystores de produção, ZIPs, bancos, dumps, backups ou temporários rastreados. Nenhum .jks/.keystore encontrado dentro do repositório.
- Adicionadas regras para `/dist/`, `*.jks`, `*.keystore`, `.env` e `.env.*`, como prevenção segura. Ignorar não limpa histórico: a auditoria histórica é separada.
- dist contém apenas staging local: APK debug, SHA256SUMS.txt e APK_METADATA.json. Ignorado; não versionado. APKs de build também ignorados. A chave debug pertence ao tooling Android fora do repositório; nenhuma chave de produção foi criada/configurada.

## Licença

Não há LICENSE/COPYING do projeto rastreado. Não foi escolhida licença automaticamente. Repositório público sem licença significa código visível, mas não concede automaticamente direitos amplos de reutilização.

LICENSE ausente por decisão atual do proprietário em R11.1B; nenhuma licença será adicionada nesta etapa. Créditos e avisos dos templates SBC/caption2 foram preservados; a origem está no README de submissão e caption2 inclui seu aviso de licença. Uma licença futura do projeto não deve apagar obrigações de terceiros.

## Correções e ações não realizadas

Versionamento, documentação de candidate e regras de ignore preparados. Nenhuma alteração de dados de evidência, Kotlin, dependência ou segredo. Sem remoção automática do VBA, reescrita de histórico, criação de keystore de produção, visibilidade pública, merge main, tag, GitHub Release ou publicação de LinkedIn.

## Decisão do proprietário — R11.1B

Em 07/10/2026 (America/Sao_Paulo), o proprietário informou que avaliou o e-mail adicional e o VBA histórico, aceitou sua exposição e decidiu não removê-los nem reescrever o histórico. Esta decisão autoriza a manutenção dos dois achados identificados; não representa garantia universal de ausência de outros dados sensíveis. A auditoria histórica extensa não foi repetida.

## Gate final

**SIM — R11.1 concluído e pronto para R11.2**, com achados anteriores aceitos pelo proprietário e validações técnicas reconfirmadas. R11 permanece em andamento. R11.2–R11.4 não iniciados; nenhuma tag/release, merge main ou alteração de visibilidade efetuada. Integração em develop depende de confirmação própria de PR/merge.
