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
| Segurança | Spring Security + JWT (HS256) + BCrypt |
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
   JWT_SECRET=troque-este-segredo-em-producao-com-pelo-menos-32-caracteres
   ```

> O `JWT_SECRET` é usado para assinar os tokens (mínimo de 32 caracteres).

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

A `V2__seed_admin.sql` cria um administrador inicial para o bootstrap — veja a seção
[Autenticação](#autenticação).

---

## Como rodar

Na raiz do backend (`Geared_logistics_backend`):

```bash
./mvnw spring-boot:run
```

A aplicação sobe em **http://localhost:8080**.

### Primeiro acesso

O banco já é semeado com um administrador inicial (migration `V2__seed_admin.sql`):

| Campo | Valor |
| --- | --- |
| E-mail | `admin@gearedlogistics.com` |
| Senha | `admin123` |

> ⚠️ **Troque a senha após o primeiro acesso.** Use este usuário para obter o token em
> `POST /api/auth/login` — veja a seção [Autenticação](#autenticação).

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
├── config/                 # Configurações do Spring (OpenAPI, CORS, Jackson, JPA Auditing, Security/JWT)
├── shared/                 # Código transversal reutilizado por todos os módulos
│   ├── domain/             #   BaseEntity, Pessoa, Endereco (base para pessoas do domínio)
│   ├── dto/                #   ApiError, PageResponse
│   ├── exception/          #   Exceções + GlobalExceptionHandler
│   ├── mapper/             #   Contrato BaseMapper
│   └── validation/         #   @CpfCnpj (validação de CPF/CNPJ)
└── modules/                # Um pacote por domínio de negócio
    ├── autenticacao/       #   Login e emissão do token JWT
    ├── usuarios/           #   Acesso ao sistema (login)
    ├── clientes/           #   Cliente (+ endereço embutido e telefones)
    ├── veiculos/           #   Veículo (vinculado a um Cliente)
    ├── catalogo/           #   Item de catálogo: Peça ou Material (+ preços e margem)
    ├── estoque/            #   Estoque, PosicaoEstoque e MovimentacaoEstoque
    └── ordemservico/       #   OrdemServico, Servico e AplicacaoItem
```

Cada módulo é autossuficiente: contém suas próprias camadas `Controller → Service → Repository`
+ `domain/dto/mapper`. Para criar um novo domínio, basta replicar uma das pastas de `modules/`.

---

## Autenticação

A API usa **JWT** (Bearer token). As credenciais são os usuários da tabela `usuarios` e a senha é
gravada como hash **BCrypt**.

- `POST /api/auth/login` é o único endpoint de negócio público. Ele autentica e devolve o token.
- Todas as demais rotas `/api/**` exigem o header `Authorization: Bearer <token>`.
- A documentação (`/v3/api-docs/**`, `/swagger-ui/**`) é pública.

O banco já vem com um administrador inicial (migration `V2__seed_admin.sql`):

```
admin@gearedlogistics.com / admin123    <-- troque a senha após o primeiro acesso
```

```bash
# 1) obtém o token
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@gearedlogistics.com","senha":"admin123"}' \
  | sed -n 's/.*"token":"\([^"]*\)".*/\1/p')

# 2) usa o token em um endpoint protegido
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/usuarios
```

O token carrega o e-mail (`sub`) e os perfis no claim `roles` (ex.: `["ROLE_ADMIN"]`), e vale o
tempo definido em `jwt.expiration` (padrão `PT2H`). No Swagger UI, use o botão **Authorize** para
informar o token.

---

## Endpoints

Base: `/api`. Todas as rotas exigem token, exceto o login.

### Autenticação

| Método | Rota | Descrição | Status |
| --- | --- | --- | --- |
| `POST` | `/api/auth/login` | Autentica e devolve o token JWT | `200` / `401` |

### Usuários

| Método | Rota | Descrição | Status |
| --- | --- | --- | --- |
| `POST` | `/api/usuarios` | Cria um usuário | `201` / `409` |
| `GET` | `/api/usuarios?page=0&size=20` | Lista paginada | `200` |
| `GET` | `/api/usuarios/{id}` | Busca por id | `200` / `404` |
| `PUT` | `/api/usuarios/{id}` | Atualiza um usuário | `200` / `404` |
| `DELETE` | `/api/usuarios/{id}` | Remove um usuário | `204` / `404` |

### Clientes

| Método | Rota | Descrição | Status |
| --- | --- | --- | --- |
| `POST` | `/api/clientes` | Cria um cliente (CPF ou CNPJ) | `201` / `400` / `409` |
| `GET` | `/api/clientes?nome=&cpfCnpj=&page=0&size=20` | Lista paginada (filtros opcionais) | `200` |
| `GET` | `/api/clientes/{id}` | Busca por id | `200` / `404` |
| `PUT` | `/api/clientes/{id}` | Atualiza um cliente | `200` / `404` |
| `DELETE` | `/api/clientes/{id}` | Remove um cliente | `204` / `404` |

### Veículos

| Método | Rota | Descrição | Status |
| --- | --- | --- | --- |
| `POST` | `/api/veiculos` | Cria um veículo vinculado a um cliente | `201` / `404` / `409` |
| `GET` | `/api/veiculos?placa=&clienteId=&page=0&size=20` | Lista paginada (filtros opcionais) | `200` |
| `GET` | `/api/veiculos/{id}` | Busca por id | `200` / `404` |
| `PUT` | `/api/veiculos/{id}` | Atualiza um veículo | `200` / `404` |
| `DELETE` | `/api/veiculos/{id}` | Remove um veículo | `204` / `404` |

Exemplo (criar cliente com token):

```bash
curl -H "Authorization: Bearer $TOKEN" -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{
        "nome": "João Silva",
        "cpfCnpj": "111.444.777-35",
        "email": "joao@email.com",
        "profissao": "Mecânico",
        "endereco": {
          "logradouro": "Rua das Flores", "numero": "123", "bairro": "Centro",
          "cidade": "Mossoró", "estado": "RN", "cep": "59600-000"
        },
        "telefones": ["(84) 99999-1111"]
      }'
```

Exemplo (criar veículo do cliente 1):

```bash
curl -H "Authorization: Bearer $TOKEN" -X POST http://localhost:8080/api/veiculos \
  -H "Content-Type: application/json" \
  -d '{"placa":"ABC1D23","marca":"Chevrolet","modelo":"Onix","tipo":"Carro","anoFabricacao":2020,"clienteId":1}'
```

### Itens (peças e materiais)

| Método | Rota | Descrição | Status |
| --- | --- | --- | --- |
| `POST` | `/api/itens` | Cria um item (`PECA` ou `MATERIAL`) | `201` / `400` / `409` |
| `GET` | `/api/itens?nome=&tipo=&page=0&size=20` | Lista paginada (filtros opcionais) | `200` |
| `GET` | `/api/itens/{id}` | Busca por id | `200` / `404` |
| `PUT` | `/api/itens/{id}` | Atualiza um item | `200` / `404` |
| `DELETE` | `/api/itens/{id}` | Remove um item | `204` / `404` / `409` |

> Cada item devolve `margemLucro` calculada: `(precoVenda - precoCusto) / precoVenda * 100`.

### Estoques

| Método | Rota | Descrição | Status |
| --- | --- | --- | --- |
| `POST` | `/api/estoques` | Cria um local de estoque | `201` |
| `GET` | `/api/estoques?nome=&page=0&size=20` | Lista paginada | `200` |
| `GET` | `/api/estoques/{id}` | Busca por id | `200` / `404` |
| `GET` | `/api/estoques/{id}/posicoes` | Saldo atual dos itens no local | `200` / `404` |
| `PUT` | `/api/estoques/{id}` | Atualiza um local | `200` / `404` |
| `DELETE` | `/api/estoques/{id}` | Remove um local | `204` / `404` / `409` |

### Movimentações de estoque

| Método | Rota | Descrição | Status |
| --- | --- | --- | --- |
| `POST` | `/api/movimentacoes` | Registra `ENTRADA`, `SAIDA` ou `AJUSTE` | `201` / `400` / `404` / `409` |
| `GET` | `/api/movimentacoes?itemId=&estoqueId=&tipo=&page=0&size=20` | Histórico (filtros opcionais) | `200` |

- `ENTRADA` soma ao saldo, `SAIDA` subtrai (erro **409** se não houver saldo) e `AJUSTE` define o saldo.
- A resposta inclui `quantidadeResultante` (saldo após o movimento).

Exemplo (entrada de estoque):

```bash
curl -H "Authorization: Bearer $TOKEN" -X POST http://localhost:8080/api/movimentacoes \
  -H "Content-Type: application/json" \
  -d '{"estoqueId":1,"itemId":1,"tipo":"ENTRADA","quantidade":50,"motivo":"compra do fornecedor"}'
```

### Ordens de serviço

| Método | Rota | Descrição | Status |
| --- | --- | --- | --- |
| `POST` | `/api/ordens-servico` | Abre uma OS (cliente + veículo + problema) | `201` / `400` / `409` |
| `GET` | `/api/ordens-servico?status=&clienteId=&veiculoId=&page=0&size=20` | Lista paginada (filtros opcionais) | `200` |
| `GET` | `/api/ordens-servico/{id}` | Busca por id | `200` / `404` |
| `POST` | `/api/ordens-servico/{id}/servicos` | Adiciona mão de obra | `201` / `404` / `409` |
| `POST` | `/api/ordens-servico/{id}/itens` | Aplica peça/material (**dá baixa no estoque**) | `201` / `404` / `409` |
| `POST` | `/api/ordens-servico/{id}/iniciar` | `Aberta` → `EmAndamento` | `200` / `404` / `409` |
| `POST` | `/api/ordens-servico/{id}/concluir` | `EmAndamento` → `Concluída` | `200` / `404` / `409` |
| `POST` | `/api/ordens-servico/{id}/cancelar` | Cancela (só `Aberta`/`EmAndamento`) | `200` / `404` / `409` |
| `DELETE` | `/api/ordens-servico/{id}` | Exclui (só `Aberta`) | `204` / `404` / `409` |

- A resposta traz `numero` (`OS-000001`), `progresso` (derivado do status) e os totais
  `valorMaoDeObra`, `valorItens` e `valorTotal`.
- **Aplicar um item dá baixa no estoque**: é preciso informar o `estoqueId`; se não houver saldo,
  retorna **409**.
- O veículo precisa pertencer ao cliente informado (**409** caso contrário).
- O orçamento ainda **não** faz parte deste módulo (próximo passo).

Exemplo (abrir uma OS e aplicar uma peça):

```bash
# abre a OS
curl -H "Authorization: Bearer $TOKEN" -X POST http://localhost:8080/api/ordens-servico \
  -H "Content-Type: application/json" \
  -d '{"clienteId":1,"veiculoId":1,"descricaoProblema":"Freios rangendo ao frear"}'

# adiciona mão de obra
curl -H "Authorization: Bearer $TOKEN" -X POST http://localhost:8080/api/ordens-servico/1/servicos \
  -H "Content-Type: application/json" -d '{"descricao":"Troca de pastilhas","valorMaoDeObra":150.00}'

# aplica 2 unidades do item 1, debitando do estoque 1
curl -H "Authorization: Bearer $TOKEN" -X POST http://localhost:8080/api/ordens-servico/1/itens \
  -H "Content-Type: application/json" -d '{"itemId":1,"quantidade":2,"estoqueId":1}'
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
- A autenticação é via **JWT** (HS256), assinado com o `JWT_SECRET`; a expiração é `jwt.expiration`.
- O administrador inicial do seed (`admin@gearedlogistics.com / admin123`) deve ter a senha trocada.
- O domínio (Clientes, Veículos e os próximos módulos) segue o **modelo conceitual** documentado no
  repositório `Geared_logistics` (diagramas de classe, MER e casos de uso).
- CPF e CNPJ são validados pelo `@CpfCnpj` (dígitos verificadores).
- O CORS já está configurado em `CorsConfig` liberando `/api/**` para o app React Native.
