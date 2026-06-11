# Estoque Spring API — Gerenciamento de Produtos e Insumos

## Descrição Geral
API REST para gerenciamento de estoque desenvolvida com Spring Boot.  
O sistema realiza operações de CRUD para produtos e insumos, utilizando arquitetura em camadas, validações de dados, tratamento global de erros e documentação interativa com Swagger/OpenAPI.

## Objetivo do Projeto
Este projeto foi desenvolvido com os seguintes objetivos:

- Praticar o desenvolvimento de APIs REST utilizando Spring Boot
- Consolidar conceitos de arquitetura em camadas (Controller, Service, Repository)
- Aplicar validações de dados com Bean Validation
- Implementar tratamento global de exceções com `@ControllerAdvice`
- Documentar a API utilizando Swagger/OpenAPI
- Trabalhar com persistência de dados utilizando JPA/Hibernate
- Utilizar PostgreSQL com separação de ambientes (dev e prod)

## Arquitetura do Sistema
A aplicação segue uma arquitetura em camadas:

- **Controller**  
  Responsável por expor os endpoints REST, receber requisições HTTP e retornar respostas apropriadas.

- **Service**  
  Contém as regras de negócio, validações adicionais e coordenação do fluxo das operações.

- **Repository**  
  Responsável pelo acesso ao banco de dados, utilizando Spring Data JPA.

- **DTO**  
  Utilizados para controlar os dados de entrada e saída da API, desacoplando a camada externa do modelo interno.

- **Entity**  
  Representa as entidades persistidas no banco de dados, mapeadas com anotações JPA.

- **Exception**  
  Camada responsável pelo tratamento global de erros, padronizando as respostas da API através da classe `ApiError`.

## Execução do Projeto

### Pré-requisitos
- Java 17+
- Maven
- PostgreSQL

### Configuração
1. Clone o repositório
2. Configure o banco de dados PostgreSQL
3. Ajuste os arquivos de configuração:
   - `application-dev.properties`
   - `application-prod.properties`
4. Defina o profile ativo:

```bash
SPRING_PROFILES_ACTIVE=dev
```

Execute a aplicação:

```bash
mvn spring-boot:run
```

## Documentação da API (Swagger)
A API possui documentação interativa via Swagger/OpenAPI.

Swagger UI:  
http://localhost:8080/swagger-ui/index.html

OpenAPI JSON:  
http://localhost:8080/v3/api-docs

Através do Swagger é possível:

Visualizar todos os endpoints

Ver exemplos de request e response

Testar chamadas de sucesso e erro diretamente pelo navegador

## Tratamento de Erros
A aplicação utiliza tratamento global de exceções, retornando erros de forma padronizada.

Exemplo de erro de validação (400):

```json
{
  "timestamp": "2026-02-01T13:10:13.539686",
  "status": 400,
  "error": "Bad Request",
  "message": "Erro de validação",
  "path": "/produtos",
  "fields": {
    "nome": "Nome é obrigatório",
    "quantidade": "Quantidade não pode ser negativa"
  }
}
```

## Funcionalidades
A API permite realizar operações completas de CRUD para produtos e insumos:

Cadastro de novos registros

Consulta de itens por ID

Listagem de registros

Atualização total (PUT)

Atualização parcial (PATCH)

Remoção de registros (DELETE)

## Tecnologias Utilizadas
Java 17

Spring Boot

Spring Web

Spring Data JPA / Hibernate

Bean Validation

PostgreSQL

Swagger / OpenAPI (springdoc-openapi)

## Testes automatizados

O projeto possui uma suíte de testes automatizados cobrindo as principais camadas da aplicação.

### Tipos de testes implementados

#### Testes unitários

Os testes unitários validam regras de negócio de forma isolada, principalmente nos services.

Exemplos de cenários testados:

- validação de nome vazio;
- validação de quantidade negativa;
- busca por ID inexistente;
- regras de atualização;
- comportamento esperado em cenários válidos e inválidos.

Nesses testes, os repositories são simulados com mocks, permitindo validar apenas a lógica da camada de service.

#### Testes de controller

Os testes de controller validam o contrato HTTP da API utilizando `MockMvc`.

Exemplos de cenários testados:

- criação de Produto e Insumo com dados válidos;
- retorno `400 Bad Request` para dados inválidos;
- retorno `404 Not Found` para IDs inexistentes;
- retorno `204 No Content` em exclusões bem-sucedidas;
- validação de rotas, JSONs e status HTTP.

Esses testes garantem que os endpoints respondem corretamente sem depender do banco de dados real.

#### Testes de integração

Os testes de integração validam o funcionamento real entre service, repository e banco de dados de teste.

Exemplos de cenários testados:

- salvar Produto e Insumo no banco;
- buscar registros por ID;
- atualizar registros;
- atualizar parcialmente;
- remover registros;
- validar paginação;
- validar regras críticas em ambiente integrado.

Esses testes garantem que as camadas principais funcionam corretamente em conjunto.

---

### Execução dos testes

Para executar toda a suíte de testes, use o Maven Wrapper na raiz do projeto:

```bash
./mvnw test
````

## Rodando com Docker

O projeto possui configuração Docker para subir a API Spring Boot junto com um banco PostgreSQL em containers.

### Pré-requisitos

- Docker
- Docker Compose

### Configuração

Crie um arquivo `.env` na raiz do projeto com base no arquivo `.env.example`.

## Docker

O projeto possui configuração Docker para executar a API Spring Boot junto com um banco PostgreSQL em containers.

A estrutura foi criada para padronizar o ambiente da aplicação, evitando a necessidade de instalar e configurar manualmente o PostgreSQL na máquina local.

### Estrutura Docker

O projeto utiliza os seguintes arquivos para configuração do ambiente com Docker:

```text
Dockerfile
.dockerignore
docker-compose.yml
.env.example
application-docker.properties
```

### Tecnologias utilizadas no ambiente Docker

* Docker
* Docker Compose
* Java 21
* Spring Boot
* PostgreSQL 16

### Como funciona

A aplicação é executada em dois serviços principais:

```text
api = container da aplicação Spring Boot
db = container do banco PostgreSQL
```

O serviço `api` é construído a partir do `Dockerfile`, utilizando multi-stage build:

```text
1. Primeira etapa: usa JDK para compilar o projeto e gerar o arquivo .jar.
2. Segunda etapa: usa JRE para executar apenas o .jar gerado.
```

O serviço `db` utiliza a imagem oficial do PostgreSQL:

```yaml
image: postgres:16
```

A comunicação entre a API e o banco acontece pela rede interna do Docker Compose. Por isso, dentro do ambiente Docker, a API acessa o banco usando o host:

```text
db
```

Exemplo de conexão usada pela aplicação:

```properties
spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
```

### Variáveis de ambiente

As configurações sensíveis e valores do ambiente são definidos por variáveis de ambiente.

Para rodar o projeto, crie um arquivo `.env` na raiz do projeto com base no arquivo `.env.example`.

Exemplo:

```env
POSTGRES_USER=seu_usuario
POSTGRES_PASSWORD=sua_senha
POSTGRES_DB=estoque_db_docker

DB_HOST=db
DB_PORT=5432

API_EXTERNAL_PORT=8080
POSTGRES_EXTERNAL_PORT=5433

JWT_SECRET=sua_chave_jwt
JWT_EXPIRATION_HOURS=2
```

O arquivo `.env` não deve ser versionado no GitHub.

### Profile Docker

O projeto possui um profile específico para execução com Docker:

```text
application-docker.properties
```

Esse profile é ativado no `docker-compose.yml` por meio da variável:

```yaml
SPRING_PROFILES_ACTIVE: docker
```

Com isso, a aplicação utiliza as configurações específicas do ambiente Docker, como conexão com o banco via variáveis de ambiente.

### Executando o projeto com Docker

Na raiz do projeto, execute:

```bash
docker compose up --build
```

Esse comando irá:

```text
1. Construir a imagem da API.
2. Baixar a imagem do PostgreSQL, caso ainda não exista localmente.
3. Criar os containers da API e do banco.
4. Criar o volume do PostgreSQL.
5. Subir a aplicação com o profile docker ativo.
```

Após a inicialização, a API estará disponível em:

```text
http://localhost:8080
```

### Acessando o banco PostgreSQL

Caso seja necessário acessar o banco externamente por ferramentas como pgAdmin, DBeaver ou IntelliJ Database, utilize:

```text
Host: localhost
Port: 5433
Database: estoque_db_docker
User: definido no .env
Password: definido no .env
```

Dentro da rede Docker, a API acessa o banco usando:

```text
Host: db
Port: 5432
```

### Parando os containers

Para parar e remover os containers, mantendo os dados persistidos no volume:

```bash
docker compose down
```

Para subir novamente:

```bash
docker compose up
```

Para reconstruir a imagem da API e subir os containers:

```bash
docker compose up --build
```

### Persistência de dados

O PostgreSQL utiliza um volume Docker para persistir os dados:

```yaml
volumes:
  - estoque_postgres_data:/var/lib/postgresql/data
```

Dessa forma, mesmo que os containers sejam removidos com:

```bash
docker compose down
```

os dados continuam salvos no volume.

Para remover também os volumes e apagar os dados do banco Docker, utilize:

```bash
docker compose down -v
```

Use esse comando apenas quando quiser resetar completamente o banco.

### Comandos úteis

```bash
# Subir os containers
docker compose up

# Subir reconstruindo a imagem da API
docker compose up --build

# Parar e remover containers
docker compose down

# Parar containers sem remover
docker compose stop

# Iniciar containers parados
docker compose start

# Listar containers em execução
docker ps

# Listar volumes
docker volume ls
```


