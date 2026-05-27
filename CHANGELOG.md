# Andamento do Projeto - Estoque Spring API

## Data
30/04/2026

## Objetivo do dia
Revisar, corrigir e consolidar a suíte de testes automatizados do projeto Estoque Spring API, incluindo testes unitários, testes de controller e testes de integração.

---

## Atividades realizadas

### Testes unitários

- Revisados os testes unitários de `ProdutoServiceTest` e `InsumoServiceTest`.
- Corrigidos cenários de ID inexistente.
- Ajustada a lógica dos testes para garantir que:
  - o repository seja apenas mockado;
  - o método executado dentro do `assertThrows` seja o método do service;
  - a exceção de negócio seja lançada pelo service, não pelo repository.
- Reforçado o entendimento de que:
  - `when(...)` configura o comportamento do mock;
  - `assertThrows(...)` deve executar o método que contém a regra;
  - o repository apenas informa, enquanto o service decide.

---

### Testes de controller

- Revisados e corrigidos testes de `ProdutoControllerTest` e `InsumoControllerTest`.
- Corrigidas URLs dos endpoints nos testes.
- Ajustado o uso correto de rotas com `{id}`, como:
  - `GET /produtos/{id}`
  - `PUT /produtos/{id}`
  - `PATCH /produtos/{id}`
  - `DELETE /produtos/{id}`
- Corrigido uso incorreto de valores como `1L` na URL, substituindo por valores HTTP válidos como `/1`.
- Corrigidos JSONs inválidos nos testes, principalmente strings sem aspas.
- Ajustado status esperado em operações de delete:
  - de `200 OK`
  - para `204 No Content`
- Corrigidos testes de validação para retornar corretamente `400 Bad Request`.
- Corrigidos testes de erro para ID inexistente retornando `404 Not Found`.

---

### Tratamento global de exceções

- Ajustado o tratamento global de exceções.
- Adicionado tratamento para formato inválido de parâmetro de rota.
- Garantido que erros como `/produtos/abc` ou `/insumos/abc` retornem `400 Bad Request`.
- Revisado o comportamento do handler genérico para evitar que erros esperados caiam como `500 Internal Server Error`.

---

### Testes de integração

- Revisados os testes de integração de Produto e Insumo.
- Removido uso manual de `setId(...)` em entidades que são salvas no banco.
- Ajustado o fluxo para usar o ID gerado pelo banco através de:
  - `produtoSalvo.getId()`
  - `insumoSalvo.getId()`
- Corrigidos testes de salvar, buscar, atualizar, atualizar parcialmente e deletar.
- Melhorada a validação dos efeitos reais no banco.
- Ajustados testes de paginação para evitar dependência de valores fixos frágeis.
- Utilizado `repository.count()` para comparar corretamente o total de elementos quando necessário.
- Criado teste para validar regra de tamanho máximo da paginação.

---

## Problemas encontrados

- Testes de controller retornando `500` quando o esperado era `400` ou `404`.
- Rotas em testes sem o `{id}` necessário.
- Uso incorreto de singular/plural em endpoints.
- JSON malformado por falta de aspas em strings.
- Expectativa incorreta de status HTTP em delete.
- Uso manual de ID em entidades persistidas no banco.
- Testes de paginação frágeis por dependerem de quantidade fixa de registros.
- Testes unitários chamando o repository diretamente em vez do service.
- Exceções esperadas não sendo lançadas por erro na responsabilidade testada.

---

## Correções realizadas

- Corrigidas URLs dos testes de controller.
- Corrigidos corpos JSON enviados pelo `MockMvc`.
- Ajustados status esperados conforme o contrato real da API.
- Corrigido tratamento de exceções no handler global.
- Ajustados mocks nos testes unitários.
- Corrigida lógica de ID inexistente nos testes de service.
- Removidos IDs manuais nos testes de integração.
- Ajustadas validações de paginação.
- Rodada suíte completa de testes com Maven Wrapper.

---

## Resultado final

A suíte completa de testes foi executada com sucesso:

Tests run: 66, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS

```bash
./mvnw test
````

## [Unreleased]

### 04/05
- Criada estrutura inicial dos pacotes de autenticação e segurança:
  - `auth`;
  - `security`;
  - `usuario`;
  - `dto.auth`.

### 05/05
- Implementada base inicial da autenticação:
  - `Role`;
  - `Usuario`;
  - `UsuarioRepository`;
  - `RegisterRequestDTO`;
  - `LoginRequestDTO`;
  - `AuthResponseDTO`;
  - `PasswordEncoder` com `BCryptPasswordEncoder`.
  
### 12/05
- Implementada integração inicial com o fluxo de autenticação do Spring Security.
- Criado `UsuarioDetailsService` usando `UsuarioRepository.findByEmail`.
- Configurado `AuthenticationManager` no `SecurityConfig`.
- Adicionada dependência `java-jwt` para futura geração e validação de tokens JWT.

### 14/05

- Implementado `TokenService` para geração e validação de tokens JWT.
- Adicionadas configurações `jwt.secret` e `jwt.expiration-hours`.
- Configurado uso de variável de ambiente para chave secreta do JWT.
- Implementada geração de token com issuer, subject, role, expiração e assinatura.
- Implementada validação de token retornando o subject/email do usuário.

### 15/05

- Implementado fluxo inicial de login em `/auth/login`.
- Implementado `AuthService.login`, usando `AuthenticationManager` para validar email e senha.
- Adicionada criação de `UsernamePasswordAuthenticationToken` com email e senha recebidos pelo `LoginRequestDTO`.
- Integrado `TokenService` ao fluxo de login para gerar JWT após autenticação bem-sucedida.
- Implementado retorno de `AuthResponseDTO` com token e tipo `Bearer`.
- Implementado endpoint de login no `AuthController`, retornando `200 OK`.

### 18/05

- Implementado `SecurityFilter` para interceptar requisições, extrair token Bearer do header `Authorization`, validar JWT e montar autenticação no `SecurityContext`.
- Integrado `TokenService` e `UsuarioDetailsService` ao fluxo de autenticação por token.
- Ajustados testes de controller com `@AutoConfigureMockMvc(addFilters = false)` e exclusão do `SecurityFilter`, mantendo esses testes focados no contrato HTTP dos controllers.

### 20/05

- Finalizada configuração inicial de segurança com JWT.
- Configuradas rotas públicas, rotas protegidas e autorização por roles no `SecurityConfig`.
- Liberadas rotas de autenticação e Swagger.
- Adicionado tratamento para credenciais inválidas no login.
- Adicionada validação de tamanho mínimo de senha no `RegisterRequestDTO`.
- Validado manualmente o fluxo de autenticação com Bearer Token.

### 26/05

- Adicionados testes unitários para `AuthService`, cobrindo registro, login e validações de regras de negócio.
- Ajustada ordem das validações no registro para validar campos obrigatórios antes de consultar o banco.
- Corrigido `issuer` do JWT no `TokenService`.
- Adicionado `setId` em `Usuario` para suporte aos testes unitários.