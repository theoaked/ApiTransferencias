# Api de Transferencias

Api responsável por cadastrar e buscar clientes, além de efetuar e buscar transações financeiras.


## Autores

- [Eduardo de Souza Carvalho (escafcd)](https://www.github.com/theoaked)


## Especificações de negócio

- Endpoint para cadastrar um cliente, com as seguintes informações: id (único), nome, número da conta (único) e saldo em conta;
- Endpoint para listar todos os clientes cadastrados;
- Endpoint para buscar um cliente pelo número da conta;
- Endpoint para realizar transferência entre 2 contas. A conta origem precisa ter saldo o suficiente para a realização da transferência e a transferência deve ser de no máximo R$ 1000,00 reais;
- Endpoint para buscar as transferências relacionadas à uma conta, por ordem de data decrescente. Lembre-se que transferências sem sucesso também devem armazenadas.


## Instalação

Instale a API com Maven

```bash
  mvn clean install
```

Execute a aplicação

```bash
  mvn spring-boot:run
```

Execute os testes unitarios/integrados:

```bash
  mvn test
```


## Documentação da API

Para facilitar o uso da API em ambiente local, eu recomendo subir a aplicação como serviço e acessar http://localhost:8080/swagger-ui/index.html#/

Dessa forma, é possível consultar todos os endpoints de forma mais visual e simples.

```http
  PORT: 8080
```

#### Retorna todos os clientes

```http
  GET /v1/clientes
```


#### Retorna um cliente chaveado por numero de conta

```http
  GET /v1/clientes/{conta}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `conta`      | `string` | **Obrigatório**. A conta do cliente a ser buscado |


#### Cadastra um novo cliente

```http
  POST /v1/clientes?conta={conta}&nome={nome}&saldo={saldo}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `conta`      | `string` | **Obrigatório**. A conta do cliente a ser inserido |
| `nome`       | `string` | **Obrigatório**. O nome do cliente a ser inserido |
| `saldo`      | `double` | **Obrigatório**. O saldo do cliente a ser inserido (ex. 999.9) |

#### Retorna todos as transações

```http
  GET /v1/transacoes
```


#### Retorna as transações chaveadas por numero de conta (origem ou destino)

```http
  GET /v1/transacoes/{conta}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `conta`      | `string` | **Obrigatório**. A conta do cliente a ser buscadas as transações |


#### Cadastra um novo cliente

```http
  POST /v1/transacoes?contaOrigem={contaOrigem}&contaDestino={contaDestino}&valor={valor}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `contaOrigem`   | `string` | **Obrigatório**. A conta de origem da transação |
| `contaDestino`  | `string` | **Obrigatório**. A conta de destino da transação |
| `valor`         | `double` | **Obrigatório**. O valor a ser transacionado (ex. 999.9) |



