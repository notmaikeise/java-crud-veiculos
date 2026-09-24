CREATE TABLE veiculo (
                         id INTEGER PRIMARY KEY AUTO_INCREMENT,
                         placa VARCHAR(10) NOT NULL UNIQUE,
                         marca VARCHAR(100) NOT NULL,
                         modelo VARCHAR(100) NOT NULL,
                         ano INTEGER NOT NULL,
                         cor VARCHAR(50),
                         ativo BOOLEAN NOT NULL
);