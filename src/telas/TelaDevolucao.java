package telas;

import modelo.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class TelaDevolucao extends JFrame {
    private JTable tabelaVeiculosLocados;
    private List<Veiculo> listaVeiculos;
    private List<Veiculo> veiculosLocados = new ArrayList<>();

    private final Color fundo = new Color(64, 64, 64);
    private final Color laranja = new Color(255, 102, 0);
    private final Color textoClaro = Color.LIGHT_GRAY;
    private final Font fontePadrao = new Font("SansSerif", Font.BOLD, 20);

    public TelaDevolucao(List<Veiculo> veiculos) {
        this.listaVeiculos = veiculos;

        setTitle("Devolução de Veículos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(fundo);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titulo = new JLabel("DEVOLUÇÃO DE VEÍCULOS");
        titulo.setForeground(laranja);
        titulo.setFont(new Font("Impact", Font.BOLD, 50));
        painelPrincipal.add(titulo, gbc);

        gbc.gridy++;
        JScrollPane scroll = new JScrollPane(criarTabela());
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        painelPrincipal.add(scroll, gbc);
        
        gbc.gridy++;
        JButton btnDevolver = criarBotaoEstilo("Devolver Veículo");
        btnDevolver.addActionListener(e -> devolverVeiculo());

        // Ajuste para o botão não ocupar toda a largura
        gbc.fill = GridBagConstraints.NONE; 
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza o botão
        painelPrincipal.add(btnDevolver, gbc);


        add(painelPrincipal);
        filtrarVeiculosLocados();
        setVisible(true);
    }

    private JTable criarTabela() {
        tabelaVeiculosLocados = new JTable(new VeiculoLocadoTableModel(veiculosLocados));
        return tabelaVeiculosLocados;
    }

    private JButton criarBotaoEstilo(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(laranja);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 20));
        botao.setFocusPainted(false);
        return botao;
    }

    private void filtrarVeiculosLocados() {
        veiculosLocados.clear();
        for (Veiculo v : listaVeiculos) {
            if (v.getEstado() == Estado.LOCADO) {
                veiculosLocados.add(v);
            }
        }
        VeiculoLocadoTableModel model = (VeiculoLocadoTableModel) tabelaVeiculosLocados.getModel();
        model.setVeiculos(new ArrayList<>(veiculosLocados));
    }

    private void devolverVeiculo() {
        int linha = tabelaVeiculosLocados.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para devolver.");
            return;
        }

        Veiculo v = veiculosLocados.get(linha);
        v.devolver(); // método que coloca locacao = null e estado = DISPONIVEL
        JOptionPane.showMessageDialog(this, "Veículo devolvido com sucesso!");

        filtrarVeiculosLocados(); // atualiza a tabela após devolução
    }

    class VeiculoLocadoTableModel extends AbstractTableModel {
        private final String[] colunas = {"Cliente", "Placa", "Marca", "Modelo", "Ano", "Data Locação", "Diária", "Dias Locados", "Valor Total"};
        private List<Veiculo> veiculos;

        public VeiculoLocadoTableModel(List<Veiculo> veiculos) {
            this.veiculos = veiculos;
        }

        public void setVeiculos(List<Veiculo> veiculos) {
            this.veiculos = veiculos;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() { return veiculos.size(); }

        @Override
        public int getColumnCount() { return colunas.length; }

        @Override
        public String getColumnName(int i) { return colunas[i]; }

        @Override
        public Object getValueAt(int row, int col) {
            Veiculo v = veiculos.get(row);
            Locacao loc = v.getLocacao();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            return switch (col) {
                case 0 -> loc != null ? loc.getCliente().getNome() + " " + loc.getCliente().getSobrenome() : "N/A";
                case 1 -> v.getPlaca();
                case 2 -> v.getMarca();
                case 3 -> (v instanceof Automovel a) ? a.getModelo() :
                          (v instanceof Motocicleta m) ? m.getModelo() :
                          (v instanceof Van va) ? va.getModelo() : "N/A";
                case 4 -> v.getAno();
                case 5 -> loc != null ? sdf.format(loc.getData().getTime()) : "N/A"; 
                case 6 -> String.format("R$ %.2f", v.getValorDiariaLocacao());
                case 7 -> loc != null ? loc.getDias() : 0; 
                case 8 -> loc != null ? String.format("R$ %.2f", loc.getValor()) : "R$ 0,00"; 
                default -> "";
            };
        }
    }
}
