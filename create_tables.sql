PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS cliente (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    sobrenome TEXT,
    rg TEXT,
    cpf TEXT UNIQUE,
    endereco TEXT
);

CREATE TABLE IF NOT EXISTS veiculo (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    tipo TEXT,
    marca TEXT,
    categoria TEXT,
    estado TEXT,
    valorCompra REAL,
    placa TEXT UNIQUE,
    ano INTEGER,
    modelo TEXT
);

CREATE TABLE IF NOT EXISTS locacao (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    veiculo_id INTEGER,
    cliente_id INTEGER,
    dias INTEGER,
    valor REAL,
    data INTEGER,
    FOREIGN KEY(veiculo_id) REFERENCES veiculo(id),
    FOREIGN KEY(cliente_id) REFERENCES cliente(id)
);
