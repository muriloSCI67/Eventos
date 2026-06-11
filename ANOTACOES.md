# Tutorial do Projeto Eventos

Este arquivo serve como anotacao e guia de estudo. Ele nao muda o funcionamento do projeto.

## 1. Criar o projeto Spring

Primeiro, crie um projeto Spring Boot com as dependencias principais:

- Spring Web: para criar endpoints REST.
- Spring Data JPA: para trabalhar com banco de dados usando entidades e repositories.
- PostgreSQL Driver: para conectar no banco PostgreSQL.
- Validation: para validar os dados que chegam nas requisicoes.
- Lombok: para reduzir codigo repetitivo como getters, setters e construtores.

Depois disso, configure o banco no arquivo `src/main/resources/application.properties`.

Exemplo do que esse arquivo faz:

- define o nome da aplicacao;
- informa a URL do banco;
- informa usuario e senha;
- diz para o Hibernate atualizar as tabelas automaticamente com `spring.jpa.hibernate.ddl-auto=update`.

## 2. Criar a entidade `Evento`

A entidade representa a tabela principal do sistema.

Arquivo:

`src/main/java/br/com/exercicios/spring/eventos/Entity/Evento.java`

Ela deve ter `@Entity`, porque o JPA precisa entender que essa classe vira uma tabela no banco.

Campos importantes:

- `id`: identificador do evento.
- `titulo`: nome do evento.
- `palestrante`: pessoa que vai apresentar.
- `descricao`: descricao do evento.
- `emailContato`: email para contato.
- `cargaHoraria`: duracao do evento.
- `dataEvento`: data em que o evento acontece.
- `quantidadeVagas`: quantidade de vagas disponiveis.
- `valorInscricao`: valor cobrado para participar.
- `status`: situacao do evento.
- `dataCadastro`: data em que o evento foi cadastrado.
- `codigoInterno`: codigo gerado automaticamente.

Anotacoes importantes:

- `@Id`: marca o campo como chave primaria.
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`: deixa o banco gerar o ID.
- `@Enumerated(EnumType.STRING)`: salva o enum como texto no banco.
- `@PrePersist`: executa um metodo antes de salvar pela primeira vez.

No projeto, o `prePersist()` serve para preencher automaticamente:

- `dataCadastro`, se estiver vazia;
- `codigoInterno`, se estiver vazio.

## 3. Criar o enum `Status`

O enum serve para limitar os valores possiveis de status do evento.

Arquivo:

`src/main/java/br/com/exercicios/spring/eventos/Entity/enums/Status.java`

Em vez de deixar qualquer texto ser salvo, o enum obriga o sistema a aceitar apenas valores definidos.

Exemplo de ideia:

```java
public enum Status {
    ATIVO,
    CANCELADO,
    FINALIZADO
}
```

Vantagem:

- evita erro de digitacao;
- deixa o codigo mais organizado;
- facilita validacao e filtros no futuro.

## 4. Criar o DTO de request

DTO de request e o objeto que representa os dados que chegam na API.

Arquivo:

`src/main/java/br/com/exercicios/spring/eventos/dtos/EventosRequestDTO.java`

Ele e usado principalmente no `POST` e no `PUT`.

Por que usar DTO em vez de receber a entidade direto?

- evita expor a entidade completa para quem usa a API;
- permite controlar quais campos podem ser enviados;
- facilita colocar validacoes;
- separa a entrada da API da estrutura do banco.

Validacoes usadas:

- `@NotBlank`: texto obrigatorio e nao vazio.
- `@Email`: valida formato de email.
- `@Min`: define valor minimo para numero.
- `@FutureOrPresent`: exige data atual ou futura.
- `@Positive`: exige numero positivo.
- `@DecimalMin`: define valor minimo para decimal.
- `@NotNull`: exige que o campo nao seja nulo.

Esse DTO tambem tem um metodo `dtoToEntity`, que converte os dados recebidos para uma entidade `Evento`.

## 5. Criar o DTO de response

DTO de response e o objeto devolvido pela API.

Arquivo:

`src/main/java/br/com/exercicios/spring/eventos/dtos/EventosResponseDTO.java`

Ele define o que aparece na resposta quando alguem consulta, cria ou edita um evento.

Por que ele existe?

- permite escolher quais campos serao retornados;
- evita devolver informacoes desnecessarias;
- deixa a resposta da API mais organizada.

O metodo `fromEntity(Evento entity)` converte uma entidade `Evento` em um `EventosResponseDTO`.

Fluxo:

```text
Banco -> Evento -> EventosResponseDTO -> resposta JSON
```

## 6. Criar o repository

Repository e a camada que conversa com o banco de dados.

Arquivo:

`src/main/java/br/com/exercicios/spring/eventos/Repository/EventosRepository.java`

Normalmente ele estende `JpaRepository`.

Exemplo:

```java
public interface EventosRepository extends JpaRepository<Evento, Long> {
}
```

Com isso, o Spring ja cria varios metodos automaticamente:

- `findAll()`: busca todos.
- `findById(id)`: busca por ID.
- `save(evento)`: salva ou atualiza.
- `existsById(id)`: verifica se existe.
- `deleteById(id)`: apaga por ID.

Ou seja, nao precisa escrever SQL para as operacoes basicas.

## 7. Criar o service

Service e a camada onde fica a regra de negocio.

Arquivo:

`src/main/java/br/com/exercicios/spring/eventos/Service/EventosService.java`

O controller nao deve concentrar regra de negocio. Ele deve apenas receber a requisicao e chamar o service.

Responsabilidades do service:

- buscar eventos;
- salvar eventos;
- editar eventos;
- deletar eventos;
- verificar se um evento existe;
- lancar excecao quando algo der errado;
- converter entidade para DTO de resposta.

Metodos principais:

- `findAll()`: retorna todos os eventos como DTO.
- `findById(Long id)`: busca um evento por ID.
- `save(EventosRequestDTO dto)`: cria um novo evento.
- `editarEvento(Long id, EventosRequestDTO dto)`: atualiza um evento existente.
- `delete(Long id)`: remove um evento.

Exemplo de fluxo ao buscar por ID:

```text
Controller recebe GET /eventos/1
Controller chama service.findById(1)
Service chama repository.findById(1)
Repository consulta o banco
Service converte Evento para EventosResponseDTO
Controller devolve JSON
```

## 8. Criar o controller

Controller e a camada que expoe os endpoints da API.

Arquivo:

`src/main/java/br/com/exercicios/spring/eventos/Controller/EventosController.java`

Anotacoes importantes:

- `@RestController`: diz que essa classe responde requisicoes REST.
- `@RequestMapping`: define o caminho base da API.
- `@PostMapping`: cria registro.
- `@GetMapping`: consulta registros.
- `@PutMapping`: atualiza registro.
- `@DeleteMapping`: remove registro.
- `@RequestBody`: pega dados do corpo da requisicao.
- `@PathVariable`: pega valor que vem na URL.
- `@RequestParam`: pega valor que vem como parametro.
- `@Valid`: ativa as validacoes do DTO.

Endpoints do projeto:

- `POST /api/eventos`: cria um evento.
- `GET /api/eventos`: lista todos os eventos.
- `GET /api/eventos/{id}`: busca um evento por ID.
- `PUT /api/eventos?id=1`: edita um evento.
- `DELETE /api/eventos/{id}`: deleta um evento.

## 9. Criar a excecao personalizada

Uma excecao personalizada deixa os erros do projeto mais claros.

Arquivo:

`src/main/java/br/com/exercicios/spring/eventos/handlers/MinhaException.java`

Ela pode ser usada quando algo esperado nao acontece.

Exemplo:

```java
throw new MinhaException("Evento nao encontrado para o id: " + id);
```

No projeto, ela e usada quando:

- tenta buscar um evento que nao existe;
- tenta editar um evento que nao existe;
- tenta deletar um evento que nao existe.

## 10. Criar o handler de excecoes

O handler centraliza o tratamento dos erros.

Arquivo:

`src/main/java/br/com/exercicios/spring/eventos/handlers/ExceptionHandlers.java`

Sem handler, o Spring pode devolver uma resposta grande e pouco amigavel quando ocorre erro.

Com handler, voce consegue controlar:

- status HTTP;
- mensagem de erro;
- formato da resposta;
- tratamento de validacoes.

Exemplos de status:

- `404 Not Found`: quando o evento nao existe.
- `400 Bad Request`: quando os dados enviados estao invalidos.

## 11. Ordem recomendada para construir o projeto

Uma ordem boa para criar esse tipo de projeto e:

1. Criar o projeto Spring Boot com as dependencias.
2. Configurar o `application.properties`.
3. Criar a entidade `Evento`.
4. Criar o enum `Status`.
5. Criar o repository.
6. Criar o DTO de request.
7. Criar o DTO de response.
8. Criar o service com as regras.
9. Criar o controller com os endpoints.
10. Criar a excecao personalizada.
11. Criar o handler de excecoes.
12. Testar as rotas no Postman, Insomnia ou navegador quando for `GET`.

## 12. Fluxo geral do projeto

Quando alguem cria um evento:

```text
JSON da requisicao
-> EventosRequestDTO
-> EventosController
-> EventosService
-> Evento
-> EventosRepository
-> Banco de dados
-> EventosResponseDTO
-> JSON de resposta
```

Quando alguem consulta um evento:

```text
Requisicao GET
-> EventosController
-> EventosService
-> EventosRepository
-> Banco de dados
-> Evento
-> EventosResponseDTO
-> JSON de resposta
```

## 13. O que estudar depois

Depois que o CRUD basico estiver funcionando, os proximos passos podem ser:

- criar testes para service e controller;
- melhorar as mensagens de erro;
- criar filtros por status ou data;
- criar paginacao;
- criar ordenacao;
- separar melhor os pacotes;
- usar migrations com Flyway ou Liquibase;
- criar perfis de ambiente, como `dev` e `test`;
- usar banco H2 nos testes.

## 14. Como testar no Postman

Antes de testar no Postman, a aplicacao precisa estar rodando.

Normalmente a URL base sera:

```text
http://localhost:8080
```

O controller aceita dois caminhos:

```text
/api/eventos
/eventos
```

Entao voce pode usar:

```text
http://localhost:8080/api/eventos
```

ou:

```text
http://localhost:8080/eventos
```

Nos exemplos abaixo vou usar `/api/eventos`.

### Criar um evento

Metodo:

```text
POST
```

URL:

```text
http://localhost:8080/api/eventos
```

No Postman:

- va em `Body`;
- escolha `raw`;
- escolha `JSON`;
- cole o JSON.

Exemplo de JSON:

```json
{
  "titulo": "Semana Java",
  "descricao": "Evento sobre Spring Boot e APIs REST",
  "palestrante": "Murilo Spagnoli",
  "emailContato": "murilo@email.com",
  "cargaHoraria": 8,
  "dataEvento": "2026-07-20",
  "quantidadeVagas": 100,
  "valorInscricao": 49.90,
  "status": "ABERTO"
}
```

Observacoes:

- `dataEvento` precisa estar no formato `YYYY-MM-DD`.
- `status` precisa ser um dos valores do enum: `ABERTO`, `FECHADO` ou `CANCELADO`.
- `emailContato` precisa ter formato de email valido.
- `cargaHoraria` precisa ser no minimo `1`.
- `quantidadeVagas` precisa ser positivo.

Resposta esperada:

```json
{
  "titulo": "Semana Java",
  "descricao": "Evento sobre Spring Boot e APIs REST",
  "id": 1,
  "palestrante": "Murilo Spagnoli",
  "emailContato": "murilo@email.com",
  "cargaHoraria": 8,
  "quantidadeVagas": 100,
  "dataEvento": "2026-07-20",
  "valorInscricao": 49.90,
  "status": "ABERTO",
  "dataCadastro": "2026-06-11"
}
```

A data de cadastro pode mudar, porque ela e gerada automaticamente no dia em que salvar.

### Listar todos os eventos

Metodo:

```text
GET
```

URL:

```text
http://localhost:8080/api/eventos
```

Nao precisa enviar JSON no body.

Resposta esperada:

```json
[
  {
    "titulo": "Semana Java",
    "descricao": "Evento sobre Spring Boot e APIs REST",
    "id": 1,
    "palestrante": "Murilo Spagnoli",
    "emailContato": "murilo@email.com",
    "cargaHoraria": 8,
    "quantidadeVagas": 100,
    "dataEvento": "2026-07-20",
    "valorInscricao": 49.90,
    "status": "ABERTO",
    "dataCadastro": "2026-06-11"
  }
]
```

### Buscar evento por ID

Metodo:

```text
GET
```

URL:

```text
http://localhost:8080/api/eventos/1
```

Nesse exemplo, `1` e o ID do evento.

Nao precisa enviar JSON no body.

Se o evento existir, retorna os dados dele.

Se nao existir, deve retornar uma mensagem de erro tratada pelo handler.

### Editar um evento

Metodo:

```text
PUT
```

URL:

```text
http://localhost:8080/api/eventos?id=1
```

Importante:

- no seu controller, o `PUT` recebe o ID por `@RequestParam`;
- por isso o ID vai na URL como `?id=1`;
- nao e `PUT /api/eventos/1` neste projeto.

No Postman:

- va em `Body`;
- escolha `raw`;
- escolha `JSON`;
- envie os campos que quer atualizar.

Exemplo atualizando todos os campos:

```json
{
  "titulo": "Semana Java Atualizada",
  "descricao": "Evento atualizado sobre Spring Boot",
  "palestrante": "Murilo",
  "emailContato": "contato@email.com",
  "cargaHoraria": 10,
  "dataEvento": "2026-08-15",
  "quantidadeVagas": 150,
  "valorInscricao": 79.90,
  "status": "ABERTO"
}
```

Exemplo atualizando apenas alguns campos:

```json
{
  "titulo": "Novo titulo do evento",
  "status": "FECHADO"
}
```

O service foi feito para manter o valor antigo quando algum campo vier `null`.

Entao, se voce nao mandar um campo no JSON, ele continua como estava.

### Deletar um evento

Metodo:

```text
DELETE
```

URL:

```text
http://localhost:8080/api/eventos/1
```

Nesse exemplo, `1` e o ID do evento que sera deletado.

Nao precisa enviar JSON no body.

Se der certo, a resposta pode vir vazia.

Se o ID nao existir, o service lanca uma excecao dizendo que o evento nao foi encontrado.

## 15. Resumo rapido dos endpoints

```text
POST   http://localhost:8080/api/eventos
GET    http://localhost:8080/api/eventos
GET    http://localhost:8080/api/eventos/1
PUT    http://localhost:8080/api/eventos?id=1
DELETE http://localhost:8080/api/eventos/1
```

## 16. Erros comuns no Postman

Erro ao criar ou editar:

```text
400 Bad Request
```

Possiveis causas:

- faltou algum campo obrigatorio;
- email esta invalido;
- data esta no passado;
- status foi escrito errado;
- numero de vagas veio negativo;
- carga horaria veio menor que 1.

Erro ao buscar, editar ou deletar:

```text
Evento nao encontrado
```

Possivel causa:

- o ID informado nao existe no banco.

Erro de conexao:

```text
Connection refused
```

Possiveis causas:

- a aplicacao Spring nao esta rodando;
- o Postman esta usando porta errada;
- o banco PostgreSQL nao esta ligado;
- a configuracao do `application.properties` esta incorreta.
