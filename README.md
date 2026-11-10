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

3. **Prepare a tabela `servicos`.** O repositório, na versão analisada, ainda não inclui um arquivo de migração SQL. O exemplo abaixo é um **esquema sugerido**, compatível com os campos lidos e gravados pelo código atual; não representa uma migração já presente no projeto:

   ```sql
   CREATE TABLE servicos (
       id SERIAL PRIMARY KEY,
       nome VARCHAR(255) NOT NULL,
       valor DOUBLE PRECISION NOT NULL,
       categoria VARCHAR(30) NOT NULL,
       status VARCHAR(30) NOT NULL DEFAULT 'PENDENTE',
       largura DOUBLE PRECISION NOT NULL,
       comprimento DOUBLE PRECISION NOT NULL,
       arquivado BOOLEAN NOT NULL DEFAULT FALSE
   );
   ```

4. Configure as variáveis de ambiente usadas pela classe `ConexaoPostgreSQL`:

   - `DRYGEST_DB_LOGIN` — usuário do PostgreSQL.
   - `DRYGEST_DB_PASSWORD` — senha do PostgreSQL.

   A URL de conexão atual no código é `jdbc:postgresql://localhost:5432/drygest`.

5. Importe o projeto Maven no IntelliJ IDEA e execute a classe `Main.java`.

> **Observação:** por enquanto, a aplicação depende de um PostgreSQL local e da criação manual da tabela. A execução de ponta a ponta não foi verificada durante a elaboração desta documentação.

## Testes

O repositório contém **42 métodos de teste com `@Test`**, distribuídos entre `GerenciadorServicosTest` (24) e `ServicoTest` (18), cobrindo regras de negócio, validações e operações de gerenciamento.

```bash
mvn test
```

A execução efetiva dos testes via Maven ainda precisa ser confirmada no ambiente local; pode ser necessário configurar explicitamente uma versão atual do Maven Surefire Plugin para reconhecer o JUnit 5.

## Aprendizados e próximas melhorias

Este projeto foi utilizado para praticar **orientação a objetos, encapsulamento, enums, exceções, JDBC, SQL, separação de responsabilidades e testes unitários**.

Melhorias planejadas ou possíveis:

- Disponibilizar um script SQL versionado para inicializar o banco.
- Externalizar a URL de conexão e revisar o processo de configuração.
- Evoluir a aplicação para uma API REST utilizando Spring Boot.
- Criar uma interface para facilitar o uso por profissionais da área.

## Autor

Projeto desenvolvido por [jaaodev](https://github.com/jaaodev) como parte dos estudos em desenvolvimento backend Java.
