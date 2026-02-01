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

## Próximas Evoluções
Implementar testes automatizados com JUnit e MockMvc

Realizar deploy da aplicação

Criar um front-end consumindo a API

Implementar autenticação e autorização
