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

## 🚀 Como Executar no NetBeans

### 1. Abrir o Projeto
- Abra o NetBeans
- File → Open Project
- Selecione a pasta `sistemaLocadora-veiculos`

### 2. Adicionar Driver SQLite
**Importante:** Sem o driver, o projeto não funcionará.

#### Opção A: Adicionar via NetBeans (Recomendado)
1. Download: Baixe `sqlite-jdbc-3.41.2.1.jar` (ou versão estável)
   - URL: https://github.com/xerial/sqlite-jdbc/releases
2. No NetBeans:
   - Clique com botão direito no projeto → **Properties**
   - Vá para **Libraries** → **Compile**
   - Clique em **Add JAR/Folder**
   - Selecione o arquivo `sqlite-jdbc-3.41.2.1.jar`
   - Clique OK

#### Opção B: Adicionar em Pasta do Projeto
1. Crie a pasta `lib/` na raiz do projeto
2. Copie `sqlite-jdbc-3.41.2.1.jar` para `lib/`
3. NetBeans detectará automaticamente

### 3. Criar o Banco de Dados
Na primeira execução, o banco é criado automaticamente.

**Alternativa (manual):**
```bash
# Se tiver sqlite3 CLI instalado
sqlite3 sistemalocadora.db < create_tables.sql
```

### 4. Executar a Aplicação
1. Clique com botão direito no projeto
2. **Run** (ou F6)
3. Ou navegue até a classe `telas.TelaPrincipal` e clique em **Run File**

## 📦 Estrutura do Projeto

```
sistemaLocadora-veiculos/
├── src/
│   ├── dao/                          # Persistência (DAO + JDBC)
│   │   ├── ConnectionFactory.java
│   │   ├── ClienteDAO.java
│   │   ├── VeiculoDAO.java
│   │   └── LocacaoDAO.java
│   ├── modelo/                       # Domain Model
│   │   ├── Cliente.java
│   │   ├── Veiculo.java (abstract)
│   │   ├── Automovel.java
│   │   ├── Motocicleta.java
│   │   ├── Van.java
│   │   ├── Locacao.java
│   │   ├── Marca.java (enum)
│   │   ├── Categoria.java (enum)
│   │   ├── Estado.java (enum)
│   │   ├── ModeloAutomovel.java (enum)
│   │   ├── ModeloMotocicleta.java (enum)
│   │   ├── ModeloVan.java (enum)
│   │   └── VeiculoI.java (interface)
│   ├── controle/                     # Controllers (MVC)
│   │   ├── ClienteController.java
│   │   ├── VeiculoController.java
│   │   ├── LocacaoController.java
│   │   └── DBInit.java (utilitário - não mais necessário)
│   └── telas/                        # Views (Swing)
│       ├── TelaPrincipal.java
│       ├── TelaCliente.java
│       ├── TelaCadastroVeiculos.java
│       ├── TelaLocacao.java
│       ├── TelaDevolucao.java
│       ├── TelaVenda.java
│       └── *TableModel.java
├── create_tables.sql                 # Script de criação do BD
├── build.xml                         # Configuração Ant
├── nbproject/                        # Configuração NetBeans
└── README.md                         # Este arquivo
```

## 🎯 Funcionalidades Implementadas

### 1. Gerenciamento de Clientes
- ✅ Listar clientes
- ✅ Adicionar cliente
- ✅ Atualizar cliente
- ✅ Excluir cliente (bloqueado se tem locação ativa)
- ✅ Validação de CPF/RG

### 2. Cadastro de Veículos
- ✅ Adicionar automóvel, moto ou van
- ✅ Combobox para tipo, marca, categoria, modelo
- ✅ Validação de placa (formato XXX-0000)
- ✅ Validação de ano
- ✅ Máscaras de entrada (moeda, placa)

### 3. Locação de Veículos
- ✅ Filtrar por tipo, marca, categoria
- ✅ Listar veículos disponíveis em tabela
- ✅ Selecionar cliente
- ✅ Informar dias e data da locação
- ✅ Persistir locação no BD

### 4. Devolução de Veículos
- ✅ Listar veículos locados
- ✅ Tabela com dados do cliente, veículo e locação
- ✅ Devolver e limpar locação do BD

### 5. Venda de Veículos
- ✅ Filtrar por tipo, marca, categoria
- ✅ Listar veículos disponíveis para venda
- ✅ Calcular valor de venda com depreciação
- ✅ Persistir venda no BD

## 🔌 Dependências

### Bibliotecas Externas
- **SQLite JDBC**: `sqlite-jdbc-3.41.2.1.jar` (ou versão estável)
  - Download: https://github.com/xerial/sqlite-jdbc/releases

### Java Version
- **Java 11+** (recomendado Java 17+)

## 🗄️ Banco de Dados

### Criar Tabelas (primeira execução)
O banco é criado automaticamente quando a aplicação tenta conectar.

**Manual (se necessário):**
```bash
sqlite3 sistemalocadora.db < create_tables.sql
```

### Arquivo do Banco
- Localização: raiz do projeto (`sistemalocadora.db`)
- Tipo: SQLite 3
- Tamanho inicial: ~50KB

## 🧪 Testes

### Teste Funcional Básico
1. Execute a aplicação (`TelaPrincipal`)
2. Abra "Cadastrar clientes" → adicione um cliente
3. Abra "Cadastrar veículo" → adicione um automóvel
4. Abra "Locação" → filtre e alugue o veículo para o cliente
5. Abra "Devolução" → devolva o veículo
6. Abra "Venda" → venda um veículo disponível

### Teste de Validação
- Tente excluir um cliente com locação ativa → deve bloquear ✅
- Tente adicionar CPF inválido → deve rejeitar ✅
- Tente adicionar placa com formato errado → deve rejeitar ✅

## 📝 Decisões Arquiteturais

### Por que MVC?
- Separação de responsabilidades
- Facilita manutenção e testes
- Requisito do trabalho

### Por que SQLite?
- Leve e sem dependência de servidor
- Arquivo único (portável)
- Suporte a JDBC nativo

### Por que DAO?
- Abstração de acesso a dados
- Facilita mudanças de BD
- Reutilizável em múltiplas views

### Herança em Veículos
- Veiculo (abstrata) define interface comum
- Automovel, Motocicleta, Van especificam valores de diária
- Polimorfismo para manipular veículos genericamente

## 🐛 Troubleshooting

### Erro: "Driver não encontrado"
- **Solução**: Adicione o JAR do sqlite-jdbc ao classpath (ver seção "Adicionar Driver SQLite")

### Erro: "Cannot connect to database"
- **Verificação**: Verifique permissões na pasta do projeto
- **Solução**: Delete o arquivo `sistemalocadora.db` e execute novamente (recria)

### Erro: "Table cliente already exists"
- **Causa**: Banco já foi criado
- **Solução**: Delete `sistemalocadora.db` e execute novamente

### Tela não abre / aplicação trava
- **Solução**: Verifique se o banco SQLite está sendo acessado por outro processo
- **Alternativa**: Delete `sistemalocadora.db`, reinicie a aplicação

## 📚 Referências

- [Java Swing Documentation](https://docs.oracle.com/javase/tutorial/uiswing/)
- [JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/)
- [SQLite JDBC](https://github.com/xerial/sqlite-jdbc)
- [MVC Pattern](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)

## 👨‍💻 Desenvolvedor
Maria Conrad - LPOO II (Programação Orientada a Objetos)

## 📄 Licença
Este projeto é destinado a fins educacionais.

---

**Última atualização**: 13 de novembro de 2025
