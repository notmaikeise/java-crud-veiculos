# Java CRUD — Cadastro de Veículos

<div align="center">
  <img src="https://skillicons.dev/icons?i=java,maven,mysql,idea,git,github" alt="Java, Maven, MySQL, IntelliJ IDEA, Git e GitHub" />
</div>

<p align="center">
  <strong>Projeto acadêmico de estudo sobre CRUD, POO, JDBC, MySQL e regras de negócio em Java.</strong>
</p>

---

## Sobre o projeto

Este projeto foi desenvolvido para estudar, de forma simples e prática, a construção de um **CRUD em Java**.

O domínio usado como exemplo é **Cadastro de Veículos**, mas o objetivo principal é entender uma estrutura que possa ser reutilizada em outros contextos, como:

- alunos;
- produtos;
- clientes;
- livros;
- funcionários;
- pedidos.

Além de ser um exercício funcional, este README também foi escrito como **material de revisão para prova**, principalmente para situações em que seja necessário montar a lógica de um CRUD no papel.

> O foco do projeto é aprendizado. A intenção não é criar uma aplicação de produção complexa, e sim compreender bem cada responsabilidade do fluxo.

---

## Fluxo geral

```text
Main
  ↓
Service
  ↓
DAO
  ↓
JDBC
  ↓
MySQL
```

### Responsabilidade de cada parte

| Camada | Responsabilidade |
|---|---|
| `Model` | Representa os dados e objetos do sistema |
| `Service` | Aplica regras de negócio e validações |
| `DAO` | Executa comandos SQL e acessa o banco |
| `ConnectionFactory` | Abre a conexão JDBC |
| `Main` | Executa o fluxo e os cenários de teste |

Uma forma simples de memorizar:

```text
MODEL   = dados
SERVICE = regras
DAO     = banco / SQL
MAIN    = execução
```

---

## Estrutura do projeto

```text
java-crud-veiculos/
│
├── pom.xml
├── README.md
├── sql/
│   └── schema.sql
│
└── src/
    └── main/
        └── java/
            ├── Main.java
            │
            ├── model/
            │   └── Veiculo.java
            │
            ├── dao/
            │   ├── ConnectionFactory.java
            │   └── VeiculoDAO.java
            │
            └── service/
                └── VeiculoService.java
```

---

# 1. Model — entendendo a entidade

A classe `Veiculo` representa o objeto principal do sistema.

```java
public class Veiculo {

    private int id;
    private String placa;
    private String marca;
    private String modelo;
    private int ano;
    private String cor;
    private boolean ativo;
}
```

## Classe x objeto

A **classe** funciona como um molde.

```text
Veiculo
```

Um **objeto** é uma instância criada a partir dessa classe.

```java
Veiculo carro = new Veiculo(
    "ABC1D23",
    "Toyota",
    "Corolla",
    2023,
    "Prata"
);
```

Nesse exemplo:

```text
Classe  → Veiculo
Objeto  → carro
Marca   → Toyota
Modelo  → Corolla
Placa   → ABC1D23
```

---

## Encapsulamento

Os atributos são privados:

```java
private String placa;
```

O acesso é feito por getters e setters:

```java
public String getPlaca() {
    return placa;
}

public void setPlaca(String placa) {
    this.placa = placa;
}
```

Isso é **encapsulamento**.

### Para lembrar na prova

```text
private → protege o atributo
get     → lê o valor
set     → altera o valor
```

---

# 2. Construtor e `this`

O construtor cria e inicializa um objeto.

```java
public Veiculo(
        String placa,
        String marca,
        String modelo,
        int ano,
        String cor
) {
    this.placa = placa;
    this.marca = marca;
    this.modelo = modelo;
    this.ano = ano;
    this.cor = cor;
    this.ativo = true;
}
```

Quando usamos:

```java
this.placa = placa;
```

podemos ler como:

```text
atributo do objeto = valor recebido pelo construtor
```

Ou:

```text
this.placa → atributo
placa      → parâmetro
```

---

# 3. Banco de dados

A tabela utilizada no projeto é:

```sql
CREATE TABLE veiculo (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    placa VARCHAR(10) NOT NULL UNIQUE,
    marca VARCHAR(100) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    ano INTEGER NOT NULL,
    cor VARCHAR(50),
    ativo BOOLEAN NOT NULL
);
```

Existe uma relação direta entre o objeto Java e a tabela:

| Java | Banco |
|---|---|
| `int id` | `INTEGER` |
| `String placa` | `VARCHAR` |
| `String marca` | `VARCHAR` |
| `String modelo` | `VARCHAR` |
| `int ano` | `INTEGER` |
| `String cor` | `VARCHAR` |
| `boolean ativo` | `BOOLEAN` |

### Termos importantes

```text
PRIMARY KEY    → identifica cada registro
AUTO_INCREMENT → banco gera o ID automaticamente
NOT NULL       → campo obrigatório
UNIQUE         → não permite valor repetido
```

---

# 4. ConnectionFactory e JDBC

A `ConnectionFactory` centraliza a criação da conexão com o banco.

```java
public static Connection getConnection()
        throws SQLException {

    return DriverManager.getConnection(
            URL,
            USER,
            PASSWORD
    );
}
```

Fluxo:

```text
Java
 ↓
DriverManager
 ↓
JDBC
 ↓
MySQL
```

## JDBC

JDBC é a tecnologia usada para fazer o Java conversar com o banco de dados.

Três nomes importantes para memorizar:

```text
Connection        → conexão com o banco
PreparedStatement → prepara/executa SQL
ResultSet         → recebe resultados de SELECT
```

---

# 5. DAO — acesso ao banco

DAO significa:

```text
Data Access Object
```

Neste projeto, `VeiculoDAO` é responsável pelas operações de banco:

```text
inserir()
listarTodos()
buscarPorId()
buscarPorPlaca()
atualizar()
deletar()
```

O DAO **não deve decidir regras de negócio**.

Por exemplo, não é responsabilidade do DAO decidir se:

```text
"O ano é válido?"
"A placa deveria ser aceita?"
"O veículo pode ser removido?"
```

Essas decisões ficam no `Service`.

---

# 6. CRUD

CRUD representa quatro operações fundamentais:

```text
C → Create
R → Read
U → Update
D → Delete
```

No SQL:

| CRUD | SQL |
|---|---|
| Create | `INSERT` |
| Read | `SELECT` |
| Update | `UPDATE` |
| Delete | `DELETE` ou exclusão lógica |

Uma das principais coisas para memorizar para a prova é:

```text
CREATE → INSERT
READ   → SELECT
UPDATE → UPDATE
DELETE → DELETE
```

---

## CREATE — cadastrar

SQL:

```sql
INSERT INTO veiculo
(placa, marca, modelo, ano, cor, ativo)
VALUES (?, ?, ?, ?, ?, ?);
```

Java:

```java
PreparedStatement stmt =
        conexao.prepareStatement(sql);

stmt.setString(1, veiculo.getPlaca());
stmt.setString(2, veiculo.getMarca());
stmt.setString(3, veiculo.getModelo());
stmt.setInt(4, veiculo.getAno());
stmt.setString(5, veiculo.getCor());
stmt.setBoolean(6, veiculo.isAtivo());

stmt.executeUpdate();
```

### O que são os `?`

Os `?` são valores que serão preenchidos pelo `PreparedStatement`.

```java
stmt.setString(1, veiculo.getPlaca());
```

significa:

```text
preencha o primeiro ? com a placa
```

---

## READ — consultar

### Listar todos

```sql
SELECT * FROM veiculo;
```

```java
ResultSet rs = stmt.executeQuery();

while (rs.next()) {
    // lê cada linha retornada
}
```

Usamos `while` porque podem existir vários registros.

### Buscar por ID

```sql
SELECT * FROM veiculo
WHERE id = ?;
```

```java
if (rs.next()) {
    // cria o objeto encontrado
}
```

Podemos usar `if` porque um ID identifica apenas um registro.

---

## ResultSet

O `ResultSet` representa os dados retornados por um `SELECT`.

```java
rs.getString("placa");
rs.getString("marca");
rs.getInt("ano");
```

O raciocínio é:

```text
Banco
 ↓
ResultSet
 ↓
Objeto Java
```

---

## UPDATE — atualizar

```sql
UPDATE veiculo
SET placa = ?,
    marca = ?,
    modelo = ?,
    ano = ?,
    cor = ?,
    ativo = ?
WHERE id = ?;
```

O ponto mais importante é:

```sql
WHERE id = ?
```

É ele que define **qual registro será atualizado**.

Fluxo:

```text
buscar objeto
      ↓
alterar objeto
      ↓
DAO.atualizar()
      ↓
UPDATE no banco
```

---

## DELETE — remover

Neste projeto foi utilizada **exclusão lógica**.

Em vez de apagar fisicamente:

```sql
DELETE FROM veiculo
WHERE id = ?;
```

utilizamos:

```sql
UPDATE veiculo
SET ativo = false
WHERE id = ?;
```

Assim:

```text
ativo = true  → registro ativo
ativo = false → removido logicamente
```

O registro continua no banco, mas passa a ser considerado inativo.

---

# 7. Service — regras de negócio

O `Service` valida os dados antes de chamar o DAO.

Exemplo:

```java
public void cadastrarVeiculo(Veiculo veiculo) {

    if (veiculo.getPlaca() == null
            || veiculo.getPlaca().isEmpty()) {

        throw new IllegalArgumentException(
                "Placa é obrigatória."
        );

    } else if (veiculo.getAno()
            > Year.now().getValue() + 1) {

        throw new IllegalArgumentException(
                "Ano inválido."
        );

    } else if (veiculoDAO.buscarPorPlaca(
            veiculo.getPlaca()) != null) {

        throw new IllegalArgumentException(
                "Placa já cadastrada."
        );

    } else {

        veiculo.setAtivo(true);
        veiculoDAO.inserir(veiculo);
    }
}
```

A lógica pode ser resumida assim:

```text
dado inválido?
→ erro

outra regra inválida?
→ erro

está tudo certo?
→ chama o DAO
```

---

## Regras utilizadas

### Cadastro

```text
placa vazia?
→ erro

ano inválido?
→ erro

placa duplicada?
→ erro

senão
→ cadastrar
```

### Atualização

```text
veículo existe?
→ não → erro

veículo está ativo?
→ não → erro

ano é válido?
→ não → erro

senão
→ atualizar
```

### Remoção

```text
veículo existe?
→ não → erro

já está inativo?
→ sim → erro

senão
→ ativo = false
```

### Consulta

```text
veículo encontrado?
→ sim → retorna objeto

não encontrado?
→ erro
```

---

# 8. Testes de fluxo

A `Main` do projeto também foi utilizada como um pequeno executor de cenários de teste.

Entre os cenários verificados estão:

```text
[TESTE 01] Cadastro válido
[TESTE 02] Placa duplicada
[TESTE 03] Ano inválido
[TESTE 04] Consulta por ID
[TESTE 05] Atualização
[TESTE 06] Remoção lógica
[TESTE 07] Remoção duplicada
```

O console mostra entrada, resultado esperado, resultado obtido e status.

Exemplo:

```text
[TESTE 02] Bloqueio de placa duplicada

ENTRADA:
Tentativa de cadastrar novamente TST9A99

ESPERADO:
O cadastro deve ser bloqueado.

RESULTADO:
Placa já cadastrada.

STATUS: [OK]
```

> Atualmente esses testes são executados pela `Main` e acessam o banco real. Uma evolução natural seria utilizar JUnit e separar testes unitários de testes de integração.

---

# Revisão para a prova

Esta seção foi feita para conseguir reconstruir um CRUD mesmo que o tema da prova seja diferente.

## Passo 1 — descubra a entidade

Se a questão falar sobre produtos:

```text
Entidade = Produto
```

Se falar sobre alunos:

```text
Entidade = Aluno
```

Se falar sobre livros:

```text
Entidade = Livro
```

---

## Passo 2 — descubra os atributos

Exemplo com `Produto`:

```java
private int id;
private String nome;
private double preco;
private int estoque;
```

Exemplo com `Aluno`:

```java
private int id;
private String nome;
private String matricula;
private String curso;
```

O tema muda. **A estrutura continua praticamente a mesma.**

---

## Passo 3 — crie a classe

Modelo genérico:

```java
public class Entidade {

    private int id;
    private String campo1;
    private String campo2;
}
```

---

## Passo 4 — construtor

```java
public Entidade(
        String campo1,
        String campo2
) {
    this.campo1 = campo1;
    this.campo2 = campo2;
}
```

---

## Passo 5 — getters e setters

```java
public String getCampo1() {
    return campo1;
}

public void setCampo1(String campo1) {
    this.campo1 = campo1;
}
```

---

# Modelos genéricos para lembrar na prova

## INSERT

```java
String sql = """
        INSERT INTO tabela
        (campo1, campo2)
        VALUES (?, ?)
        """;

stmt.setString(1, objeto.getCampo1());
stmt.setString(2, objeto.getCampo2());

stmt.executeUpdate();
```

---

## SELECT

```java
String sql =
        "SELECT * FROM tabela WHERE id = ?";

stmt.setInt(1, id);

ResultSet rs = stmt.executeQuery();

if (rs.next()) {
    // montar objeto
}
```

---

## UPDATE

```java
String sql = """
        UPDATE tabela
        SET campo1 = ?, campo2 = ?
        WHERE id = ?
        """;

stmt.setString(1, objeto.getCampo1());
stmt.setString(2, objeto.getCampo2());
stmt.setInt(3, objeto.getId());

stmt.executeUpdate();
```

---

## DELETE

```java
String sql =
        "DELETE FROM tabela WHERE id = ?";

stmt.setInt(1, id);

stmt.executeUpdate();
```

Ou, para exclusão lógica:

```java
String sql = """
        UPDATE tabela
        SET ativo = false
        WHERE id = ?
        """;
```

---

# Modelo mental do DAO

Para quase toda operação JDBC, pense nesta estrutura:

```java
String sql = "...";

try (
    Connection conexao =
            ConnectionFactory.getConnection();

    PreparedStatement stmt =
            conexao.prepareStatement(sql)
) {

    // preencher os ?
    // executar SQL

} catch (SQLException e) {

    // tratar erro
}
```

O que normalmente muda é:

```text
SQL
parâmetros
retorno
```

---

# Modelo mental do Service

```java
if (dadoInvalido) {

    throw new IllegalArgumentException(
            "Mensagem"
    );

} else if (outraRegraInvalida) {

    throw new IllegalArgumentException(
            "Mensagem"
    );

} else {

    dao.executarOperacao();
}
```

---

# Checklist para montar um CRUD no papel

Quando receber o enunciado, siga esta ordem:

```text
1. Qual é a entidade?

2. Quais são os atributos?

3. Qual atributo identifica o registro?

4. Criar Model.

5. Criar construtor.

6. Criar getters e setters.

7. Pensar no CREATE → INSERT.

8. Pensar no READ → SELECT.

9. Pensar no UPDATE → UPDATE.

10. Pensar no DELETE → DELETE ou exclusão lógica.

11. Criar os comandos SQL.

12. Usar PreparedStatement.

13. Usar ResultSet nos SELECTs.

14. Colocar regras de negócio no Service.

15. Main chama Service.

16. Service chama DAO.

17. DAO acessa o banco.
```

---

# Resumo de bolso

```text
MODEL
= dados / objeto

SERVICE
= regras / validações

DAO
= SQL / banco

CONNECTION FACTORY
= conexão JDBC

MAIN
= execução / testes
```

### CRUD

```text
CREATE → INSERT
READ   → SELECT
UPDATE → UPDATE
DELETE → DELETE
```

### JDBC

```text
Connection
PreparedStatement
ResultSet
```

### Regras

```text
if
else if
else
```

### Fluxo completo

```text
Main → Service → DAO → JDBC → MySQL
```

Se esse fluxo estiver claro, o domínio pode mudar de **Veículo** para **Aluno**, **Produto**, **Livro** ou outra entidade sem mudar o raciocínio principal.

---

## Tecnologias

- Java 21
- Maven
- JDBC
- MySQL
- IntelliJ IDEA
- Git
- GitHub

---

## Possíveis evoluções

Algumas melhorias que podem ser exploradas futuramente:

- JUnit;
- testes unitários;
- testes de integração;
- exceções personalizadas;
- usuário próprio do banco para a aplicação;
- variáveis de ambiente para credenciais;
- menu interativo no terminal;
- Spring Boot;
- API REST.

Essas evoluções não fazem parte do objetivo inicial do projeto, que é compreender manualmente os fundamentos de um CRUD simples em Java.

---

## Observação sobre credenciais

Por segurança, senhas reais de banco de dados não devem ser publicadas no repositório.

Em um projeto público, prefira variáveis de ambiente ou valores de exemplo para configurações sensíveis.
