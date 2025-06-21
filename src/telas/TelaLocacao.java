package telas;

import modelo.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class TelaLocacao extends JFrame {
    private JComboBox<String> comboCliente;
    private JComboBox<String> comboTipoVeiculo;
    private JComboBox<Marca> comboMarca;
    private JComboBox<Categoria> comboCategoria;
    private JTable tabelaVeiculos;
    private JTextField txtDias, txtDataLocacao;
    private List<Cliente> listaClientes;
    private List<Veiculo> listaVeiculos;
    private List<Veiculo> veiculosFiltrados = new ArrayList<>();

    private final Color fundo = new Color(64, 64, 64);
    private final Color laranja = new Color(255, 102, 0);
    private final Color textoClaro = Color.LIGHT_GRAY;
    private final Font fontePadrao = new Font("SansSerif", Font.BOLD, 20);

    public TelaLocacao(List<Cliente> clientes, List<Veiculo> veiculos) {
        this.listaClientes = clientes;
        this.listaVeiculos = veiculos;

        setTitle("Locação de Veículos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(fundo);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        JLabel titulo = new JLabel("LOCAÇÃO DE VEÍCULOS");
        titulo.setForeground(laranja);
        titulo.setFont(new Font("Impact", Font.BOLD, 50));
        painelPrincipal.add(titulo, gbc);

        // Painel Formulário
        gbc.gridy++;
        painelPrincipal.add(criarPainelFormulario(), gbc);

        // Painel Tabela
        JScrollPane scroll = new JScrollPane(criarTabela());
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        painelPrincipal.add(scroll, gbc);

        add(painelPrincipal);
        filtrarVeiculos();
        setVisible(true);
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridLayout(7, 2, 10, 10));
        painel.setBackground(fundo);

        comboCliente = new JComboBox<>();
        for (Cliente c : listaClientes)
            comboCliente.addItem(c.getNome() + " " + c.getSobrenome());

        comboTipoVeiculo = new JComboBox<>(new String[]{"Todos", "Automóvel", "Motocicleta", "Van"});
        comboMarca = new JComboBox<>(Marca.values());
        comboCategoria = new JComboBox<>(Categoria.values());
        txtDias = new JTextField();
        txtDataLocacao = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        painel.add(criarLabel("Cliente:"));          painel.add(comboCliente);
        painel.add(criarLabel("Tipo de Veículo:"));  painel.add(comboTipoVeiculo);
        painel.add(criarLabel("Marca:"));            painel.add(comboMarca);
        painel.add(criarLabel("Categoria:"));        painel.add(comboCategoria);
        painel.add(criarLabel("Dias de Locação:"));  painel.add(txtDias);
        painel.add(criarLabel("Data da Locação:"));  painel.add(txtDataLocacao);

        JButton btnFiltrar = criarBotaoEstilo("Filtrar");
        JButton btnLocar = criarBotaoEstilo("Locar");

        btnFiltrar.addActionListener(e -> filtrarVeiculos());
        btnLocar.addActionListener(e -> locarVeiculo());

        painel.add(btnFiltrar); painel.add(btnLocar);
        return painel;
    }

    private JTable criarTabela() {
        tabelaVeiculos = new JTable(new VeiculoTableModel(veiculosFiltrados));
        return tabelaVeiculos;
    }

    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(textoClaro);
        lbl.setFont(fontePadrao);
        return lbl;
    }

    private JButton criarBotaoEstilo(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(laranja);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 20));
        botao.setFocusPainted(false);
        return botao;
    }

    private void filtrarVeiculos() {
        veiculosFiltrados.clear();
        String tipo = (String) comboTipoVeiculo.getSelectedItem();
        Marca marca = (Marca) comboMarca.getSelectedItem();
        Categoria categoria = (Categoria) comboCategoria.getSelectedItem();

        for (Veiculo v : listaVeiculos) {
            boolean tipoOK = tipo.equals("Todos") || v.getClass().getSimpleName().equals(tipo);
            boolean marcaOK = v.getMarca() == marca;
            boolean categoriaOK = v.getCategoria() == categoria;
            if (v.getEstado() == Estado.DISPONIVEL && tipoOK && marcaOK && categoriaOK)
                veiculosFiltrados.add(v);
        }

        // Atualiza o TableModel
        tabelaVeiculos.setModel(new VeiculoTableModel(veiculosFiltrados));
    }


    private void locarVeiculo() {
        int linha = tabelaVeiculos.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo.");
            return;
        }
        try {
            int dias = Integer.parseInt(txtDias.getText());
            Date data = new SimpleDateFormat("dd/MM/yyyy").parse(txtDataLocacao.getText());
            Calendar cal = Calendar.getInstance(); cal.setTime(data);

            Veiculo v = veiculosFiltrados.get(linha);
            Cliente c = listaClientes.get(comboCliente.getSelectedIndex());

            v.locar(dias, cal, c);
            JOptionPane.showMessageDialog(this, "Veículo locado com sucesso!");
            filtrarVeiculos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    class VeiculoTableModel extends AbstractTableModel {
        private final String[] colunas = {"Placa", "Marca", "Modelo", "Ano", "Diária"};
        private final List<Veiculo> veiculos;
        public VeiculoTableModel(List<Veiculo> veiculos) {
            this.veiculos = veiculos;
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
            return switch (col) {
                case 0 -> v.getPlaca();
                case 1 -> v.getMarca();
                case 2 -> (v instanceof Automovel a) ? a.getModelo() :
                          (v instanceof Motocicleta m) ? m.getModelo() :
                          (v instanceof Van va) ? va.getModelo() : "N/A";
                case 3 -> v.getAno();
                case 4 -> String.format("R$ %.2f", v.getValorDiariaLocacao());
                default -> "";
            };
        }
    }
}