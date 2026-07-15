# 🚀 Seleto AI – API

API backend do sistema **Seleto AI**, responsável por gerenciar autenticação, usuários, permissões e comunicação com os serviços do ecossistema da plataforma.

---

## 📌 Visão Geral

A API foi construída utilizando **Spring Boot**, seguindo princípios de **arquitetura hexagonal (Ports & Adapters)**, garantindo alta coesão, baixo acoplamento e facilidade de evolução.

Ela fornece:

* Autenticação via JWT
* Registro de usuários
* Controle de permissões (roles)
* Integração com banco de dados PostgreSQL
* Documentação automática via OpenAPI (Swagger)

---

## 🏗️ Arquitetura

O projeto segue **Arquitetura Hexagonal**, dividida em:

### 🔹 Core (Domínio)

Contém a regra de negócio pura:

* `domain` → entidades principais (User, Role, RefreshToken)
* `useCase` → casos de uso (login, criação de usuário, etc.)
* `ports` → contratos (interfaces)

---

### 🔹 Adapters

Responsável pela comunicação com o mundo externo:

* `controller` → endpoints REST
* `repository` → acesso ao banco (Spring Data JPA)
* `security` → autenticação e autorização

---

### 🔹 Config

Configurações globais:

* Segurança (JWT + Spring Security)
* CORS
* Filtros
* Beans

---

## 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)** com dois níveis:

### Access Token

* Usado nas requisições
* Expiração curta (ex: 2 horas)

### Refresh Token

* Usado para renovar sessão
* Expiração maior
* Persistido no banco

---

## 📡 Endpoints Principais

### 🔑 Autenticação

#### Login

```http
POST /auth/login
```

Request:

```json
{
  "email": "user@email.com",
  "password": "123456"
}
```

Response:

```json
{
  "token": "jwt_token"
}
```

---

#### Registro

```http
POST /auth/register
```

Request:

```json
{
  "name": "Usuário",
  "email": "user@email.com",
  "password": "123456",
  "roleId": 1
}
```

---

### 👥 Roles

#### Listar todas

```http
GET /roles
```

---

## 📄 Documentação da API

Disponível automaticamente via Swagger:

```
http://localhost:8081/swagger-ui/index.html
```

Ou JSON:

```
http://localhost:8081/v3/api-docs
```

---

## 🛠️ Tecnologias Utilizadas

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL
* Flyway (migração de banco)
* JWT (jjwt)
* OpenAPI / Swagger

---

## 🗄️ Banco de Dados

Utiliza PostgreSQL com migrações via Flyway.

### Configuração padrão (sobrescrita por variáveis de ambiente):

```yaml
spring:
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/seleto_ai}
    username: ${DB_USERNAME:seleto}
    password: ${DB_PASSWORD:seleto}
```

---

## 🔑 Variáveis de Ambiente

| Variável | Obrigatória | Descrição |
|---|---|---|
| `JWT_SECRET` | **sim** | Chave usada para assinar os JWT. Sem ela a aplicação **não sobe** (fail-fast). Mínimo de 32 bytes. |
| `JWT_EXPIRATION_MS` | não | Expiração do access token em ms (padrão 24h). |
| `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` | não | Sobrescrevem as credenciais padrão de dev (`seleto`/`seleto`). |
| `MAIL_HOST`, `MAIL_PORT`, `MAIL_USERNAME`, `MAIL_PASSWORD` | não | SMTP usado para o e-mail de confirmação de cadastro. |
| `APP_BASE_URL` | não | URL base usada para montar o link de confirmação de e-mail. |

Gere um `JWT_SECRET` local com `openssl rand -base64 48`.

---

## ▶️ Como Executar

### 1. Subir dependências (Docker)

```bash
docker-compose up -d
```

---

### 2. Rodar aplicação

```bash
export JWT_SECRET=$(openssl rand -base64 48)
./mvnw spring-boot:run
```

ou

```bash
JWT_SECRET=$(openssl rand -base64 48) mvn spring-boot:run
```

---

### 3. Acessar

* API: http://localhost:8081
* Swagger: http://localhost:8081/swagger-ui/index.html

---

## 🔁 Integração com Frontend

A API expõe documentação OpenAPI, permitindo geração automática de client:

```bash
yarn generate
```

Isso gera:

* Tipagens TypeScript
* Services Axios
* Integração direta com frontend

---

## 📦 Padrões Adotados

* Arquitetura Hexagonal
* DTO Pattern
* Mapper Pattern
* Clean Code
* SOLID

---

## 🔒 Segurança

* Autenticação stateless (JWT)
* Senhas criptografadas com BCrypt
* Filtros de autenticação
* Controle de rotas via Spring Security

---

## 📈 Evoluções Futuras

* Implementação completa de Refresh Token
* Controle granular de permissões (RBAC)
* Auditoria de ações
* Integração com IA (módulo de análise de edital)

---

## 👨‍💻 Autor

Projeto desenvolvido como base para plataforma de gestão de liçitações com inteligência artificial.

Augusto Ogawa
---

## 📄 Licença

Uso interno / educacional. Ajustável conforme necessidade do projeto.

---
