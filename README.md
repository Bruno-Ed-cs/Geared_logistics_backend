# Geared Logistics — Backend

API REST de gestão logística, construída com **Spring Boot 4** (Java 21), organizada como
**monólito modular** seguindo o padrão **MVC com camada de serviço**.

---

## Stack

| Item | Versão / tecnologia |
| --- | --- |
| Java | 21 |
| Spring Boot | 4.0.8 (Spring Framework 7) |
| Build | Maven (wrapper incluso) |
| Persistência | Spring Data JPA / Hibernate |
| Banco | PostgreSQL |
| Migrations | Flyway |
| JSON | Jackson 3 (padrão do Spring Boot 4) |
| Documentação | SpringDoc OpenAPI (Swagger UI) |
| Segurança | Spring Security (HTTP Basic) + BCrypt |
| Utilitários | Lombok |

---

## Pré-requisitos

- **JDK 21** instalado (`java -version`).
- **PostgreSQL** rodando (local ou remoto).
- **Não** é necessário ter o Maven instalado — o projeto inclui o `mvnw`.

---

## Configuração

As credenciais ficam em variáveis de ambiente, carregadas de um arquivo `.env` na raiz do projeto.

1. Crie seu `.env` a partir do exemplo:

   ```bash
   cp .env.example .env
   ```

2. Edite os valores conforme seu ambiente:

   ```dotenv
   DB_URL=jdbc:postgresql://localhost:5432/geared_logistics
   DB_USERNAME=postgres
   DB_PASSWORD=postgres
   ```

> O `.env` está no `.gitignore` e **não** deve ser commitado. O `.env.example`, sim.
> As variáveis também podem ser exportadas no shell (`export DB_URL=...`), que têm prioridade.

---

## Banco de dados

Crie o banco (o schema é criado automaticamente pelo Flyway):

```bash
createdb geared_logistics
```

ou via SQL:

```sql
CREATE DATABASE geared_logistics;
```

As **migrations** ficam em `src/main/resources/db/migration` (ex.: `V1__create_table_usuarios.sql`)
e rodam automaticamente na inicialização da aplicação. Para adicionar uma mudança de schema, crie
um novo arquivo seguindo o padrão `V2__descricao.sql`.

---

## Como rodar

Na raiz do backend (`Geared_logistics_backend`):

```bash
./mvnw spring-boot:run
```

A aplicação sobe em **http://localhost:8080**.

Para gerar o pacote executável:

```bash
./mvnw clean package
java -jar target/geared_logistics-0.0.1-SNAPSHOT.jar
```

---

## Documentação da API (Swagger)

Com a aplicação rodando:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

---

## Testes

```bash
./mvnw test
```

> Os testes sobem o contexto completo do Spring (`@SpringBootTest`) e, portanto, **precisam de um
> PostgreSQL acessível** com as variáveis do `.env`. Para testes sem banco, o ideal é adicionar
> H2 (perfil de teste) ou Testcontainers em um passo futuro.

---

## Estrutura do projeto

```
src/main/java/br/com/gearedlogistics/gearedlogistics
├── GearedLogisticsApplication.java
├── config/                 # Configurações do Spring (OpenAPI, CORS, Jackson, JPA Auditing)
├── shared/                 # Código transversal reutilizado por todos os módulos
│   ├── domain/             #   BaseEntity (id + timestamps de auditoria)
│   ├── dto/                #   ApiError, PageResponse
│   ├── exception/          #   Exceções + GlobalExceptionHandler
│   └── mapper/             #   Contrato BaseMapper
└── modules/                # Um pacote por domínio de negócio
    └── usuarios/
        ├── UsuarioController.java
        ├── domain/         #   Entidade + enums
        ├── repository/     #   Spring Data JPA
        ├── service/        #   Interface + implementação
        ├── dto/            #   Request / Response
        └── mapper/         #   Conversão Entidade <-> DTO
```

Cada módulo é autossuficiente: contém suas próprias camadas `Controller → Service → Repository`
+ `domain/dto/mapper`. Para criar um novo domínio, basta replicar a pasta `usuarios/`.

---

## Autenticação

A API usa **HTTP Basic** de forma provisória (o próximo passo é migrar para JWT). As credenciais
são os usuários da tabela `usuarios` e a senha é gravada como hash **BCrypt**.

- Todas as rotas `/api/**` exigem autenticação, **exceto** `POST /api/usuarios`, liberado para
  criar o primeiro usuário (bootstrap).
- A documentação (`/v3/api-docs/**`, `/swagger-ui/**`) é pública.

```bash
# cria o primeiro usuário (não exige autenticação - bootstrap)
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"Ana","email":"ana@gearedlogistics.com","senha":"secret123","perfil":"ADMIN","ativo":true}'

# acessa um endpoint protegido
curl -u ana@gearedlogistics.com:secret123 http://localhost:8080/api/usuarios
```

---

## Endpoints

Base: `/api/usuarios`

| Método | Rota | Descrição | Status | Auth |
| --- | --- | --- | --- | --- |
| `POST` | `/api/usuarios` | Cria um usuário | `201` | Não (bootstrap) |
| `GET` | `/api/usuarios?page=0&size=20` | Lista paginada | `200` | Sim |
| `GET` | `/api/usuarios/{id}` | Busca por id | `200` / `404` | Sim |
| `PUT` | `/api/usuarios/{id}` | Atualiza um usuário | `200` / `404` | Sim |
| `DELETE` | `/api/usuarios/{id}` | Remove um usuário | `204` / `404` | Sim |

Exemplo:

```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
        "nome": "Ana Souza",
        "email": "ana@gearedlogistics.com",
        "senha": "secret123",
        "perfil": "ADMIN",
        "ativo": true
      }'
```

### Padrão de erro

Todas as falhas retornam o mesmo corpo (`ApiError`):

```json
{
  "dataHora": "2026-10-13T14:00:00Z",
  "status": 409,
  "erro": "Regra de negócio violada",
  "mensagem": "Já existe um usuário com o e-mail ana@gearedlogistics.com",
  "caminho": "/api/usuarios",
  "detalhes": []
}
```

---

## Observações

- As senhas são gravadas como hash **BCrypt**.
- A autenticação é **HTTP Basic** e é provisória — será substituída por JWT. O
  `POST /api/usuarios` está liberado apenas para o bootstrap do primeiro usuário.
- O CORS já está configurado em `CorsConfig` liberando `/api/**` para o app React Native.
