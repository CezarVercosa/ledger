# Ledger Service

Sistema de ledger financeiro desenvolvido com:

- Java 25
- Spring Boot 4
- Arquitetura Hexagonal
- PostgreSQL
- Flyway
- MapStruct
- Lombok
- Maven

---

# Objetivo

O projeto implementa um sistema de ledger baseado em:

- Double-entry bookkeeping
- Imutabilidade de movimentações
- Separação de domínio
- Arquitetura desacoplada
- Persistência relacional

Cada transação financeira precisa fechar em zero:

```text
-100
+100
= 0
```

---

# Tecnologias

| Tecnologia | Finalidade |
|---|---|
| Java 25 | Linguagem principal |
| Spring Boot 4 | Framework backend |
| PostgreSQL | Banco relacional |
| Flyway | Versionamento de banco |
| MapStruct | Mapeamento de objetos |
| Lombok | Redução de boilerplate |
| Maven | Build do projeto |

---

# Arquitetura

O projeto utiliza Arquitetura Hexagonal (Ports and Adapters).

## Estrutura

```text
src/main/java/com/br/ledger
│
├── domain
│   ├── model
│   ├── exception
│   └── port
│
├── application
│   ├── dto
│   ├── mapper
│   ├── port
│   └── service
│
├── adapter
│   ├── in
│   │   └── web
│   │
│   └── out
│       └── persistence
│
├── config
│
└── common
```

---

# Banco de Dados

## Criando usuário PostgreSQL

```sql
CREATE USER ledger_user
WITH PASSWORD 'ledger_password';
```

## Criando banco

```sql
CREATE DATABASE ledger
OWNER ledger_user;
```

## Permissões

```sql
GRANT ALL PRIVILEGES ON DATABASE ledger TO ledger_user;
```

## Extensão UUID

```sql
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
```

---

# Configuração da aplicação

## application.properties

```properties
spring.application.name=ledger

spring.datasource.url=jdbc:postgresql://localhost:5432/ledger
spring.datasource.username=ledger_user
spring.datasource.password=ledger_password

spring.jpa.hibernate.ddl-auto=validate

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.flyway.enabled=true

server.port=8080
```

---

# Flyway

As migrations ficam em:

```text
src/main/resources/db/migration
```

Exemplo:

```text
V1__initial_schema.sql
V2__create_indexes.sql
```

---

# Rodando o projeto

## Instalar dependências

```bash
mvn clean install
```

## Rodar aplicação

```bash
mvn spring-boot:run
```

---

# Endpoints

## Criar conta

```http
POST /accounts
```

### Body

```json
{
  "name": "Conta Principal"
}
```

---

## Transferência

```http
POST /ledger/transfer
```

### Body

```json
{
  "fromAccountId": "UUID",
  "toAccountId": "UUID",
  "amount": 100.00,
  "description": "Transferência"
}
```

---

## Consultar saldo

```http
GET /ledger/balance/{accountId}
```

---

## Consultar extrato

```http
GET /ledger/statement/{accountId}
```
