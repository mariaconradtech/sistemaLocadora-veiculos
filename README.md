# Sistema de Locadora de Veículos - VeloCuritiba

## 📋 Descrição
Sistema de gerenciamento de locadora de veículos (automóveis, vans e motos) desenvolvido em Java com interface Swing, arquitetura MVC, persistência em SQLite via JDBC/DAO.

## 🏗️ Arquitetura

### Padrão MVC (Model-View-Controller)
- **Model** (`src/modelo/`): Classes de domínio (Cliente, Veiculo, Locacao, enums)
- **Controller** (`src/controle/`): Orquestração de lógica de negócios (ClienteController, VeiculoController, LocacaoController)
- **View** (`src/telas/`): Interface Swing (TelaCliente, TelaCadastroVeiculos, TelaLocacao, TelaDevolucao, TelaVenda)
- **DAO** (`src/dao/`): Persistência em banco (ClienteDAO, VeiculoDAO, LocacaoDAO, ConnectionFactory)

### Banco de Dados
- **SGBD**: SQLite (arquivo `sistemalocadora.db`)
- **Tabelas**: 
  - `cliente`: id, nome, sobrenome, rg, cpf, endereco
  - `veiculo`: id, tipo, marca, categoria, estado, valorCompra, placa, ano, modelo
  - `locacao`: id, veiculo_id, cliente_id, dias, valor, data
