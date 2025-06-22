/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package telas;

import modelo.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class TelaLocacao extends JFrame {

    private JComboBox<String> comboCliente, comboTipoVeiculo;
    private JComboBox<Marca> comboMarca;
    private JComboBox<Categoria> comboCategoria;
    private JTable tabelaVeiculos;
    private JTextField txtDias, txtDataLocacao;

    private List<Cliente> listaClientes;
    private List<Veiculo> listaVeiculos;
    private List<Veiculo> veiculosFiltrados = new ArrayList<>();

    private final Color laranja = new Color(255, 102, 0);
    private final Color textoClaro = Color.WHITE;
    private final Font fontePadrao = new Font("Arial", Font.PLAIN, 16);

    public TelaLocacao(List<Cliente> clientes, List<Veiculo> veiculos) {
        this.listaClientes = clientes;
        this.listaVeiculos = veiculos;

        setTitle("Cadastro de Veículos - VeloCuritiba");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(64, 64, 64));
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        JLabel lblLogo = new JLabel("VELOCURITIBA", SwingConstants.CENTER);
        lblLogo.setForeground(laranja);
        lblLogo.setFont(new Font("Impact", Font.BOLD, 50));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(lblLogo);

        JLabel lblSlogan = new JLabel("Seu caminho, nossa direção", SwingConstants.CENTER);
        lblSlogan.setForeground(Color.LIGHT_GRAY);
        lblSlogan.setFont(new Font("SansSerif", Font.ITALIC, 20));
        lblSlogan.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(lblSlogan);
        painelPrincipal.add(Box.createVerticalStrut(30));

        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(new Color(64, 64, 64));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel[] labels = {
            criarLabel("Cliente:"),
            criarLabel("Tipo de Veículo:"),
            criarLabel("Marca:"),
            criarLabel("Categoria:"),
            criarLabel("Dias de locação:"),
            criarLabel("Data da locação:")
        };

        comboCliente = new JComboBox<>();
        for (Cliente c : listaClientes) {
            comboCliente.addItem(c.getNome() + " " + c.getSobrenome());
        }

        comboTipoVeiculo = new JComboBox<>(new String[]{"--Selecione--", "Automóvel", "Motocicleta", "Van"});

        comboMarca = new JComboBox<>();
        comboMarca.addItem(null);
        for (Marca m : Marca.values()) comboMarca.addItem(m);

        comboCategoria = new JComboBox<>();
        comboCategoria.addItem(null);
        for (Categoria c : Categoria.values()) comboCategoria.addItem(c);

        txtDias = new JTextField();
        txtDataLocacao = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        // Renderizadores para exibir "Todas"
        comboMarca.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value == null ? "--Todas as Marcas--" : value.toString());
            label.setOpaque(true);
            label.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            label.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return label;
        });

        comboCategoria.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value == null ? "--Todas as Categorias--" : value.toString());
            label.setOpaque(true);
            label.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            label.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return label;
        });

        JComponent[] campos = {
            comboCliente, comboTipoVeiculo, comboMarca, comboCategoria, txtDias, txtDataLocacao
        };

        for (int i = 0; i < labels.length; i++) {
            labels[i].setForeground(textoClaro);
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.3;
            painelForm.add(labels[i], gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            painelForm.add(campos[i], gbc);
        }

        painelPrincipal.add(painelForm);
        painelPrincipal.add(Box.createVerticalStrut(20));
        painelPrincipal.add(new JScrollPane(criarTabela()));
        painelPrincipal.add(Box.createVerticalStrut(20));

        JButton btnFiltrar = criarBotaoEstilo("Filtrar");
        JButton btnLocar = criarBotaoEstilo("Locar");

        btnFiltrar.addActionListener(e -> filtrarVeiculos());
        btnLocar.addActionListener(e -> locarVeiculo());

        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(64, 64, 64));
        painelBotoes.add(btnFiltrar);
        painelBotoes.add(btnLocar);

        painelPrincipal.add(painelBotoes);
        add(painelPrincipal, BorderLayout.CENTER);

        filtrarVeiculos();
        setVisible(true);
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

    private JTable criarTabela() {
        tabelaVeiculos = new JTable(new VeiculoTableModel(veiculosFiltrados));
        return tabelaVeiculos;
    }

    private void filtrarVeiculos() {
        veiculosFiltrados.clear();
        String tipo = (String) comboTipoVeiculo.getSelectedItem();
        Marca marca = (Marca) comboMarca.getSelectedItem();
        Categoria categoria = (Categoria) comboCategoria.getSelectedItem();

        for (Veiculo v : listaVeiculos) {
            boolean tipoOK = tipo.equals("--Selecione--") || v.getClass().getSimpleName().equals(tipo);
            boolean marcaOK = marca == null || v.getMarca() == marca;
            boolean categoriaOK = categoria == null || v.getCategoria() == categoria;

            if (v.getEstado() == Estado.DISPONIVEL && tipoOK && marcaOK && categoriaOK)
                veiculosFiltrados.add(v);
        }

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
            Calendar cal = Calendar.getInstance();
            cal.setTime(data);

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
        public int getRowCount() {
            return veiculos.size();
        }

        @Override
        public int getColumnCount() {
            return colunas.length;
        }

        @Override
        public String getColumnName(int i) {
            return colunas[i];
        }

        @Override
        public Object getValueAt(int row, int col) {
            Veiculo v = veiculos.get(row);
            return null;
        };
    }
}
