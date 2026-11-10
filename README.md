# DryGest — Gerenciamento de serviços de gesso

**Aplicação de terminal em Java para organizar serviços de gesso e drywall**, com acompanhamento do andamento dos trabalhos e persistência em PostgreSQL.

O projeto nasceu da experiência prática no setor de gesso, com a proposta de transformar necessidades reais de organização de serviços em um sistema desenvolvido para estudo e evolução contínua.

> **Status:** projeto pessoal em desenvolvimento. A versão atual é uma aplicação de linha de comando, **não** uma API REST nem uma aplicação Spring Boot.

## Funcionalidades

Pelo menu do terminal, é possível:

- Cadastrar serviços com nome, valor, categoria e dimensões (largura e comprimento).
- Listar serviços ativos (não arquivados).
- Iniciar, concluir e cancelar serviços conforme as regras de status.
- Arquivar serviços concluídos ou cancelados e consultar o histórico de arquivados.
- Recarregar os dados persistidos no banco de dados.

A camada de gerenciamento também implementa buscas por ID, categoria e status, além de cálculos de totais por categoria/status e de área de serviço.

### Regras de negócio

- Um serviço novo começa com status `PENDENTE`.
- Transições permitidas: `PENDENTE` → `EM_ANDAMENTO` → `CONCLUIDO`; também é possível cancelar serviços `PENDENTE` ou `EM_ANDAMENTO`.
- Apenas serviços `CONCLUIDO` ou `CANCELADO` podem ser arquivados.
- Um serviço já arquivado não pode ser arquivado novamente.
- Nome, categoria, valor e dimensões passam por validações no modelo.
- Atualizações de status e arquivamento utilizam condições no `UPDATE` para detectar alterações concorrentes e sinalizar conflitos.

## Tecnologias

- **Java 21** — linguagem e regras de negócio.
- **PostgreSQL** — armazenamento dos serviços.
- **JDBC** — acesso ao banco com `PreparedStatement`.
- **Maven** — gerenciamento do projeto e dependências.
- **JUnit 5** — testes unitários.

## Organização do código

```text
src/
├── main/java/
│   ├── Main.java                         # Menu e interação no terminal
│   └── br/com/drygest/
│       ├── Servico.java                  # Entidade e regras de negócio
│       ├── GerenciadorServicos.java      # Operações e gerenciamento
│       ├── ServicoRepository.java        # Consultas SQL e persistência
│       ├── ConexaoPostgreSQL.java        # Conexão JDBC
│       ├── Status.java                   # Estados do serviço
│       ├── Categoria.java                # Categorias de serviço
│       └── ConflitoAtualizacaoException.java
└── test/java/
    ├── GerenciadorServicosTest.java
    └── br/com/drygest/ServicoTest.java
```

O fluxo principal da aplicação é: **`Main` → `GerenciadorServicos` → `ServicoRepository` → PostgreSQL**. As regras de status e as validações do serviço ficam na classe `Servico`.

## Como executar localmente

### Pré-requisitos

- JDK 21
- Maven
- PostgreSQL em execução local na porta `5432`

1. Clone o repositório:

   ```bash
   git clone https://github.com/jaaodev/ProjetoDryGest.git
   cd ProjetoDryGest
   ```

2. Crie um banco PostgreSQL chamado `drygest`.

3. **Crie a tabela `servicos`** executando o script [`database/schema.sql`](database/schema.sql) no banco `drygest`. Por exemplo, com o cliente `psql` instalado:

   ```bash
   psql -U SEU_USUARIO -d drygest -f database/schema.sql
   ```

   O script cria a tabela com os campos necessários, restrições de valores e status. Se a tabela já existir, `IF NOT EXISTS` não atualiza sua estrutura; revise eventuais diferenças antes de executar o sistema.

4. Configure as variáveis de ambiente usadas pela classe `ConexaoPostgreSQL`:

   - `DRYGEST_DB_LOGIN` — usuário do PostgreSQL.
   - `DRYGEST_DB_PASSWORD` — senha do PostgreSQL.

   A URL de conexão atual no código é `jdbc:postgresql://localhost:5432/drygest`.

5. Importe o projeto Maven no IntelliJ IDEA e execute a classe `Main.java`.

> **Observação:** por enquanto, a aplicação depende de um PostgreSQL local; o script de inicialização deve ser executado manualmente. A execução de ponta a ponta não foi verificada durante a elaboração desta documentação.

## Testes

O repositório contém **42 métodos de teste com `@Test`**, distribuídos entre `GerenciadorServicosTest` (24) e `ServicoTest` (18), cobrindo regras de negócio, validações e operações de gerenciamento.

```bash
mvn test
```

A execução efetiva dos testes via Maven ainda precisa ser confirmada no ambiente local; pode ser necessário configurar explicitamente uma versão atual do Maven Surefire Plugin para reconhecer o JUnit 5.

## Aprendizados e próximas melhorias

Este projeto foi utilizado para praticar **orientação a objetos, encapsulamento, enums, exceções, JDBC, SQL, separação de responsabilidades e testes unitários**.

Melhorias planejadas ou possíveis:

- Validar o script SQL de inicialização em um ambiente PostgreSQL limpo.
- Externalizar a URL de conexão e revisar o processo de configuração.
- Evoluir a aplicação para uma API REST utilizando Spring Boot.
- Criar uma interface para facilitar o uso por profissionais da área.

## Autor

Projeto desenvolvido por [jaaodev](https://github.com/jaaodev) como parte dos estudos em desenvolvimento backend Java.
