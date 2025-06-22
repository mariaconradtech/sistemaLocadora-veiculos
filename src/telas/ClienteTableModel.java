package telas;

import modelo.Cliente;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ClienteTableModel extends AbstractTableModel {
    private final List<Cliente> clientes;
    private final String[] colunas = {"Nome", "Sobrenome", "RG", "CPF", "Endereço"};

    public ClienteTableModel(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Cliente getCliente(int row) {
        return clientes.get(row);
    }

    public void adicionar(Cliente cliente) {
        clientes.add(cliente);
        fireTableRowsInserted(clientes.size() - 1, clientes.size() - 1);
    }

    public void atualizar(int row, Cliente cliente) {
        clientes.set(row, cliente);
        fireTableRowsUpdated(row, row);
    }

    public void remover(int row) {
        clientes.remove(row);
        fireTableRowsDeleted(row, row);
    }

    @Override
    public int getRowCount() {
        return clientes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente c = clientes.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> c.getNome();
            case 1 -> c.getSobrenome();
            case 2 -> c.getRg();
            case 3 -> c.getCpf();
            case 4 -> c.getEndereco();
            default -> null;
        };
    }
}
.