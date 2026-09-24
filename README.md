# Java CRUD — Cadastro de Veículos

<div align="center">

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-Database%20Access-007396?style=for-the-badge)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-IDE-000000?style=for-the-badge&logo=intellijidea&logoColor=white)
![Git](https://img.shields.io/badge/Git-Versionamento-F05032?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-Repositório-181717?style=for-the-badge&logo=github&logoColor=white)

</div>

## Sobre este repositório

Este projeto é um **exercício acadêmico propositalmente simples**, desenvolvido para revisar os fundamentos de **Java, Programação Orientada a Objetos, JDBC, MySQL, regras de negócio e CRUD**.

Meu nível técnico vai além do escopo apresentado aqui. A simplicidade deste repositório é intencional: a proposta da atividade é praticar a construção manual de um CRUD e entender claramente o papel de cada parte do sistema, sem adicionar frameworks ou abstrações que escondam o funcionamento básico.

Também organizei este README como um material de apoio para colegas que estão tendo **primeiro contato com Java**. Por isso, alguns conceitos são explicados desde o início, com exemplos pequenos e linguagem direta.

> O domínio usado no projeto é **Veículos**, mas a mesma lógica pode ser adaptada para alunos, produtos, clientes, livros, funcionários, pedidos e muitas outras entidades.

---

# 1. O que é um CRUD?

CRUD é uma sigla para quatro operações básicas presentes em muitos sistemas:

| Letra | Operação | Significado | SQL mais comum |
|---|---|---|---|
| C | Create | Criar / cadastrar | `INSERT` |
| R | Read | Ler / consultar | `SELECT` |
| U | Update | Atualizar | `UPDATE` |
| D | Delete | Excluir | `DELETE` |

Neste projeto, a exclusão foi tratada como **exclusão lógica**:

```text
ativo = true   → registro ativo
ativo = false  → registro removido logicamente
```

Ou seja, o registro continua no banco, mas deixa de ser considerado ativo.

---

# 2. Antes de Java: o que é uma variável?

Uma variável é um espaço usado para guardar um valor durante a execução do programa.

Exemplo:

```java
int ano = 2023;
```

Podemos ler assim:

```text
int       ano       = 2023;
│          │           │
tipo      nome        valor
```

- `int` → tipo da variável;
- `ano` → nome da variável;
- `2023` → valor armazenado.

Também podemos declarar primeiro e atribuir depois:

```java
String marca;
marca = "Toyota";
```

Ou fazer os dois ao mesmo tempo:

```java
String marca = "Toyota";
```

---

# 3. Tipos de variáveis em Java

Java é uma linguagem **fortemente tipada**. Isso significa que uma variável precisa ter um tipo definido.

## `int`

Usado para números inteiros.

```java
int idade = 20;
int ano = 2023;
int quantidade = 10;
int id = 1;
```

Exemplos válidos:

```text
-10
0
25
2026
```

---

## `double`

Usado para números com casas decimais.

```java
double altura = 1.69;
double preco = 49.90;
```

> Em sistemas financeiros reais, normalmente usamos tipos como `BigDecimal` para valores monetários. Aqui o objetivo é apenas entender os tipos básicos.

---

## `boolean`

Guarda apenas dois valores:

```java
true
false
```

Exemplo:

```java
boolean ativo = true;
```

No projeto:

```java
private boolean ativo;
```

é usado para identificar se o veículo está ativo ou foi removido logicamente.

---

## `char`

Guarda um único caractere.

```java
char letra = 'A';
char opcao = 'S';
```

`char` usa aspas simples:

```java
'A'
```

---

## `String`

Usado para textos.

```java
String nome = "Anny";
String placa = "ABC1D23";
String modelo = "Corolla";
```

`String` utiliza aspas duplas:

```java
"Toyota"
```

Tecnicamente, `String` não é um tipo primitivo: é uma classe do Java. Mas, para quem está começando, o importante é lembrar que ela é usada para armazenar textos.

---

## Outros tipos que podem aparecer

```java
byte numeroPequeno = 10;
short numero = 300;
long numeroGrande = 100000L;
float decimal = 10.5f;
```

Para este CRUD, os tipos mais importantes são:

```text
int
String
boolean
```

---

# 4. Tipos primitivos x tipos por referência

Alguns tipos básicos do Java são chamados de **primitivos**:

```text
byte
short
int
long
float
double
char
boolean
```

Já classes e objetos são tipos por referência.

Exemplos:

```java
String nome = "Toyota";
Veiculo carro = new Veiculo(...);
```

Para este projeto, não é necessário aprofundar gerenciamento de memória. Basta entender:

```text
int, boolean, double... → tipos primitivos
String, Veiculo...      → objetos / referências
```

---

# 5. Operadores básicos

Alguns operadores aparecem o tempo todo em Java.

## Comparação

```java
idade > 18
ano <= 2026
id == 1
id != 0
```

## Operadores lógicos

```java
&&  // E
||  // OU
!   // NÃO
```

Exemplo:

```java
if (placa == null || placa.isEmpty()) {
    // placa inválida
}
```

Nesse caso, a condição é verdadeira se:

```text
placa for null
OU
placa estiver vazia
```

---

# 6. `if`, `else if` e `else`

São usados para tomar decisões.

```java
if (idade < 18) {

    System.out.println("Menor de idade");

} else {

    System.out.println("Maior de idade");
}
```

Podemos ter mais condições:

```java
if (nota >= 7) {

    System.out.println("Aprovado");

} else if (nota >= 5) {

    System.out.println("Recuperação");

} else {

    System.out.println("Reprovado");
}
```

No nosso CRUD, `if / else` é usado principalmente no `Service` para validar regras de negócio.

---

# 7. Estrutura do projeto

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

O fluxo principal é:

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

Cada parte possui uma responsabilidade específica.

---

# 8. Model — `Veiculo.java`

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

---

# 9. Classe e objeto

## Classe

Uma classe funciona como um molde.

```text
Veiculo
```

Ela define quais informações e comportamentos um veículo possui.

## Objeto

Um objeto é uma instância criada a partir da classe.

```java
Veiculo carro = new Veiculo(
        "ABC1D23",
        "Toyota",
        "Corolla",
        2023,
        "Prata"
);
```

Podemos visualizar assim:

```text
CLASSE
Veiculo
   ↓
OBJETO
Toyota Corolla
```

Outro exemplo:

```text
Classe: Aluno

Objeto:
Nome: Maria
Matrícula: 12345
Curso: Engenharia de Software
```

---

# 10. Atributos

Atributos são informações guardadas dentro de uma classe.

```java
private String placa;
private String marca;
private int ano;
```

Eles representam aquilo que o objeto **tem**.

Exemplo:

```text
Veiculo TEM:
- placa
- marca
- modelo
- ano
- cor
```

Se fosse uma classe `Produto`:

```java
private int id;
private String nome;
private double preco;
private int estoque;
```

---

# 11. Métodos

Métodos representam ações.

Exemplo:

```java
public String getPlaca() {
    return placa;
}
```

Um método pode:

- receber valores;
- executar alguma lógica;
- retornar um resultado;
- não retornar nada.

Exemplo sem retorno:

```java
public void setCor(String cor) {
    this.cor = cor;
}
```

O tipo `void` significa:

```text
este método não retorna nenhum valor
```

---

# 12. `private`, `public` e encapsulamento

Quando escrevemos:

```java
private String placa;
```

o atributo só pode ser acessado diretamente dentro da própria classe.

Isso é parte do conceito de **encapsulamento**.

Para permitir acesso controlado, usamos métodos `public`.

```java
public String getPlaca() {
    return placa;
}
```

Resumo:

```text
private → acesso restrito
public  → acesso permitido
```

---

# 13. Getter e Setter

## Getter

Usado para obter um valor.

```java
public String getPlaca() {
    return placa;
}
```

Uso:

```java
veiculo.getPlaca();
```

---

## Setter

Usado para alterar um valor.

```java
public void setPlaca(String placa) {
    this.placa = placa;
}
```

Uso:

```java
veiculo.setPlaca("XYZ1A23");
```

Resumo:

```text
get → pegar / ler
set → definir / alterar
```

---

# 14. Construtor

O construtor é utilizado para criar e preparar um objeto.

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

Quando fazemos:

```java
Veiculo carro = new Veiculo(
        "ABC1D23",
        "Toyota",
        "Corolla",
        2023,
        "Prata"
);
```

o Java chama esse construtor.

---

# 15. O que significa `this`?

Veja:

```java
this.placa = placa;
```

Podemos interpretar assim:

```text
this.placa = placa;
     │         │
 atributo   parâmetro
 do objeto  recebido
```

Ou:

> A placa deste objeto recebe a placa passada como parâmetro.

---

# 16. Banco de dados

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

Existe uma relação entre os dados do Java e os dados do banco.

| Java | MySQL |
|---|---|
| `int` | `INTEGER` |
| `String` | `VARCHAR` |
| `boolean` | `BOOLEAN` |

---

# 17. `PRIMARY KEY`, `AUTO_INCREMENT` e `UNIQUE`

## `PRIMARY KEY`

Identifica unicamente cada registro.

```sql
id INTEGER PRIMARY KEY
```

Exemplo:

```text
id 1 → Corolla
id 2 → Civic
id 3 → Gol
```

---

## `AUTO_INCREMENT`

O próprio banco gera o próximo ID.

```sql
id INTEGER PRIMARY KEY AUTO_INCREMENT
```

Não precisamos escolher manualmente:

```text
1
2
3
4
...
```

---

## `UNIQUE`

Não permite valores repetidos.

```sql
placa VARCHAR(10) UNIQUE
```

Assim, duas linhas não podem ter a mesma placa.

---

# 18. JDBC

JDBC é a tecnologia utilizada pelo Java para conversar com bancos de dados relacionais.

O fluxo é:

```text
Java
 ↓
JDBC
 ↓
MySQL
```

Neste projeto, usamos principalmente:

```text
Connection
PreparedStatement
ResultSet
```

---

# 19. `ConnectionFactory`

A `ConnectionFactory` centraliza a criação da conexão com o banco.

Exemplo simplificado:

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

Assim, outras classes não precisam repetir toda a lógica de conexão.

> Credenciais reais de banco não devem ser publicadas no GitHub. Em projetos reais, use variáveis de ambiente ou mecanismos próprios de configuração.

---

# 20. DAO

DAO significa:

```text
Data Access Object
```

O DAO cuida do acesso ao banco.

Neste projeto:

```text
VeiculoDAO
```

possui operações como:

```text
inserir()
listarTodos()
buscarPorId()
buscarPorPlaca()
atualizar()
deletar()
```

Regra mental:

```text
DAO = SQL + banco
```

O DAO não deve decidir regras de negócio.

---

# 21. `PreparedStatement`

É usado para preparar comandos SQL.

Exemplo:

```java
String sql = """
        INSERT INTO veiculo
        (placa, marca, modelo, ano, cor, ativo)
        VALUES (?, ?, ?, ?, ?, ?)
        """;
```

Os `?` são valores que serão preenchidos depois.

```java
stmt.setString(1, veiculo.getPlaca());
stmt.setString(2, veiculo.getMarca());
stmt.setInt(4, veiculo.getAno());
```

O número representa a posição do `?`.

```text
1 → primeiro ?
2 → segundo ?
3 → terceiro ?
```

---

# 22. CREATE — cadastrar

SQL:

```sql
INSERT INTO veiculo
(placa, marca, modelo, ano, cor, ativo)
VALUES (?, ?, ?, ?, ?, ?);
```

Depois:

```java
stmt.executeUpdate();
```

Fluxo:

```text
Objeto Java
    ↓
VeiculoDAO
    ↓
INSERT
    ↓
MySQL
```

---

# 23. READ — consultar

## Listar todos

```sql
SELECT * FROM veiculo;
```

Como podem existir vários resultados:

```java
while (rs.next()) {
    // lê cada linha
}
```

---

## Buscar por ID

```sql
SELECT * FROM veiculo
WHERE id = ?;
```

Como um ID identifica apenas um registro:

```java
if (rs.next()) {
    // encontrou
}
```

---

# 24. `ResultSet`

`ResultSet` representa o resultado retornado pelo banco.

Exemplo:

```java
ResultSet rs = stmt.executeQuery();
```

Podemos ler os valores:

```java
rs.getString("placa");
rs.getString("marca");
rs.getInt("ano");
```

Depois transformamos a linha do banco em um objeto:

```text
Banco
 ↓
ResultSet
 ↓
Veiculo
```

---

# 25. UPDATE — atualizar

SQL:

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

O `WHERE` é muito importante.

```sql
WHERE id = ?
```

significa:

> atualize somente o registro daquele ID.

Sem `WHERE`, vários registros poderiam ser alterados.

---

# 26. DELETE — remover

Em um CRUD tradicional:

```sql
DELETE FROM veiculo
WHERE id = ?;
```

Neste projeto usamos exclusão lógica:

```sql
UPDATE veiculo
SET ativo = false
WHERE id = ?;
```

O registro permanece salvo, mas passa a ficar inativo.

---

# 27. Service

O `Service` concentra as regras de negócio.

Regra mental:

```text
Service = validações + decisões
```

Exemplo:

```java
if (veiculo.getPlaca() == null
        || veiculo.getPlaca().isEmpty()) {

    throw new IllegalArgumentException(
            "Placa é obrigatória."
    );

} else if (veiculoDAO.buscarPorPlaca(
        veiculo.getPlaca()) != null) {

    throw new IllegalArgumentException(
            "Placa já cadastrada."
    );

} else {

    veiculoDAO.inserir(veiculo);
}
```

---

# 28. DAO x Service

Essa diferença é importante.

## DAO

Pergunta:

> Como salvo isso no banco?

Exemplo:

```text
INSERT
SELECT
UPDATE
DELETE
```

## Service

Pergunta:

> Essa operação pode acontecer?

Exemplo:

```text
placa está vazia?
ano é válido?
registro existe?
veículo está ativo?
placa já existe?
```

Fluxo:

```text
Service valida
      ↓
se estiver tudo certo
      ↓
DAO executa
```

---

# 29. Exceções e `try / catch`

Algumas operações podem gerar erros.

Exemplo:

```java
try {

    service.cadastrarVeiculo(veiculo);

} catch (IllegalArgumentException e) {

    System.out.println(e.getMessage());
}
```

Podemos interpretar:

```text
try
→ tente executar

catch
→ se acontecer aquele erro, trate aqui
```

No acesso ao banco também podemos ter:

```java
catch (SQLException e)
```

---

# 30. Testes no `Main`

O projeto possui um fluxo simples de testes executado pela `Main`.

Exemplos de cenários:

```text
Cadastro válido
Placa duplicada
Ano inválido
Consulta por ID
Atualização
Remoção lógica
Remoção duplicada
```

O objetivo é visualizar:

```text
ENTRADA
ESPERADO
OBTIDO
STATUS
```

Exemplo:

```text
[TESTE 02] Bloqueio de placa duplicada

ENTRADA:
Placa: TST9A99

ESPERADO:
O sistema deve impedir o cadastro.

OBTIDO:
Placa já cadastrada.

STATUS: [OK]
```

Esses testes são didáticos e executados pela própria aplicação. Não substituem uma suíte profissional com JUnit, mas ajudam a compreender o fluxo durante o estudo.

---

# 31. Como adaptar para outro tema

A parte mais importante deste exercício é perceber que o domínio pode mudar sem alterar o raciocínio principal.

## Se a prova pedir `Aluno`

```java
public class Aluno {

    private int id;
    private String nome;
    private String matricula;
    private String curso;
}
```

O CRUD pode ter:

```text
cadastrarAluno()
buscarAluno()
listarAlunos()
atualizarAluno()
removerAluno()
```

---

## Se a prova pedir `Produto`

```java
public class Produto {

    private int id;
    private String nome;
    private double preco;
    private int estoque;
}
```

O CRUD continua sendo:

```text
CREATE
READ
UPDATE
DELETE
```

O tema muda.

A lógica permanece.

---

# 32. Modelo mental para montar um CRUD no papel

Quando receber o enunciado, pense nesta ordem:

```text
1. Qual é a entidade?

2. Quais são os atributos?

3. Qual é o tipo de cada atributo?

4. Qual campo identifica o registro?

5. Criar a classe Model.

6. Criar construtor.

7. Criar getters e setters.

8. Criar CREATE.

9. Criar READ.

10. Criar UPDATE.

11. Criar DELETE.

12. Criar regras de negócio.

13. Main chama Service.

14. Service chama DAO.

15. DAO acessa o banco.
```

---

# 33. Modelo genérico de classe

```java
public class Entidade {

    private int id;
    private String campo1;
    private String campo2;

    public Entidade(
            String campo1,
            String campo2
    ) {

        this.campo1 = campo1;
        this.campo2 = campo2;
    }

    public int getId() {
        return id;
    }

    public String getCampo1() {
        return campo1;
    }

    public void setCampo1(String campo1) {
        this.campo1 = campo1;
    }
}
```

---

# 34. Modelo genérico de INSERT

```java
String sql = """
        INSERT INTO tabela
        (campo1, campo2)
        VALUES (?, ?)
        """;

PreparedStatement stmt =
        conexao.prepareStatement(sql);

stmt.setString(1, objeto.getCampo1());
stmt.setString(2, objeto.getCampo2());

stmt.executeUpdate();
```

---

# 35. Modelo genérico de SELECT

```java
String sql =
        "SELECT * FROM tabela WHERE id = ?";

PreparedStatement stmt =
        conexao.prepareStatement(sql);

stmt.setInt(1, id);

ResultSet rs =
        stmt.executeQuery();

if (rs.next()) {

    // transformar os dados em objeto
}
```

---

# 36. Modelo genérico de UPDATE

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

# 37. Modelo genérico de DELETE

```java
String sql =
        "DELETE FROM tabela WHERE id = ?";

stmt.setInt(1, id);

stmt.executeUpdate();
```

Se houver exclusão lógica:

```java
String sql = """
        UPDATE tabela
        SET ativo = false
        WHERE id = ?
        """;
```

---

# 38. Modelo genérico de Service

```java
if (dadoInvalido) {

    throw new IllegalArgumentException(
            "Mensagem de erro"
    );

} else if (outraRegraInvalida) {

    throw new IllegalArgumentException(
            "Outra mensagem"
    );

} else {

    dao.executarOperacao();
}
```

---

# 39. Resumo rápido para revisão

## POO

```text
Classe      → molde
Objeto      → instância da classe
Atributo    → dado do objeto
Método      → ação
Construtor  → cria/prepara objeto
Getter      → lê valor
Setter      → altera valor
private     → acesso restrito
public      → acesso permitido
```

## CRUD

```text
CREATE → INSERT
READ   → SELECT
UPDATE → UPDATE
DELETE → DELETE
```

## JDBC

```text
Connection
PreparedStatement
ResultSet
```

## Camadas

```text
Model
= dados

Service
= regras de negócio

DAO
= acesso ao banco

ConnectionFactory
= conexão

Main
= execução / demonstração
```

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

---

# 40. Para quem está começando

Se você nunca programou em Java, não tente decorar o projeto inteiro de uma vez.

Tente entender nesta ordem:

```text
variáveis
   ↓
if / else
   ↓
classe
   ↓
objeto
   ↓
atributos
   ↓
construtor
   ↓
getters / setters
   ↓
CRUD
   ↓
banco
   ↓
DAO
   ↓
Service
```

Depois que esse fluxo fizer sentido, trocar `Veiculo` por `Aluno`, `Produto` ou `Cliente` fica muito mais simples.

---

## Contexto acadêmico

Este repositório foi criado para uma atividade acadêmica de revisão de CRUD em Java.

A implementação foi mantida intencionalmente direta para tornar visíveis os fundamentos da linguagem, da orientação a objetos, das regras de negócio e do acesso ao banco com JDBC.

Embora eu trabalhe e estude conceitos de software além deste nível introdutório, manter este exemplo simples também permite que ele seja utilizado como material de apoio por colegas que estão começando na linguagem.

A ideia não é mostrar a maior complexidade possível, e sim mostrar que **fundamentos bem entendidos tornam estruturas mais avançadas muito mais fáceis de compreender**.
