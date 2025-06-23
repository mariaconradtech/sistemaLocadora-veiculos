package telas; 

import modelo.Cliente; 
import javax.swing.table.AbstractTableModel; // Importa classe base para modelos de tabela personalizados
import java.util.List; // Importa a interface List

// Modelo personalizado para exibir clientes em uma JTable
public class ClienteTableModel extends AbstractTableModel {

    // Lista de clientes que serão exibidos na tabela
    private final List<Cliente> clientes;

    // Nomes das colunas da tabela
    private final String[] colunas = {"Nome", "Sobrenome", "RG", "CPF", "Endereço"};

    // Construtor: recebe a lista de clientes
    public ClienteTableModel(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    // Retorna um cliente específico pelo índice da linha
    public Cliente getCliente(int row) {
        return clientes.get(row);
    }

    // Adiciona um novo cliente à lista e atualiza a tabela
    public void adicionar(Cliente cliente) {
        clientes.add(cliente); // Adiciona cliente à lista
        fireTableRowsInserted(clientes.size() - 1, clientes.size() - 1); // Notifica que uma linha foi inserida
    }

    // Atualiza um cliente existente na posição indicada
    public void atualizar(int row, Cliente cliente) {
        clientes.set(row, cliente); // Substitui cliente na posição
        fireTableRowsUpdated(row, row); 
    }

    // Remove um cliente da posição indicada
    public void remover(int row) {
        clientes.remove(row); // Remove cliente da lista
        fireTableRowsDeleted(row, row); 
    }

    // Retorna o número total de linhas (clientes)
    @Override
    public int getRowCount() {
        return clientes.size();
    }

    // Retorna o número de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    // Retorna o nome de uma coluna, dado seu índice
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    // Retorna o valor que será exibido na célula [linha][coluna]
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente c = clientes.get(rowIndex); // Obtém o cliente da linha

        // Retorna o campo correspondente à coluna
        return switch (columnIndex) {
            case 0 -> c.getNome();
            case 1 -> c.getSobrenome();
            case 2 -> c.getRg();
            case 3 -> c.getCpf();
            case 4 -> c.getEndereco();
            default -> null; // Caso inválido
        };
    }
}
