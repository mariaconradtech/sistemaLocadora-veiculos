/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package telas;

import modelo.*;
import controle.ClienteController;
import controle.VeiculoController;

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
    private VeiculoController veiculoController = new VeiculoController();

    public TelaLocacao(List<Cliente> clientes, List<Veiculo> veiculos) {
        this.listaClientes = clientes;
        this.listaVeiculos = veiculos;

        setTitle("Locação de Veículos - VeloCuritiba");
        
        // Tamanho fixo da janela
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(64, 64, 64));
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        // Logo
        JLabel lblLogo = new JLabel("VELOCURITIBA", SwingConstants.CENTER);
        lblLogo.setForeground(new Color(255, 102, 0));
        lblLogo.setFont(new Font("Impact", Font.BOLD, 50));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(lblLogo);

        // Slogan
        JLabel lblSlogan = new JLabel("Seu caminho, nossa direção", SwingConstants.CENTER);
        lblSlogan.setForeground(Color.LIGHT_GRAY);
        lblSlogan.setFont(new Font("SansSerif", Font.ITALIC, 20));
        lblSlogan.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(lblSlogan);

        painelPrincipal.add(Box.createVerticalStrut(30));

        // Painel para os campos do formulário
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

        comboTipoVeiculo = new JComboBox<>(new String[]{"--Selecione--", "Automovel", "Motocicleta", "Van"});

        comboMarca = new JComboBox<>();
        comboMarca.addItem(null);
        for (Marca m : Marca.values()) comboMarca.addItem(m);

        comboCategoria = new JComboBox<>();
        comboCategoria.addItem(null);
        for (Categoria c : Categoria.values()) comboCategoria.addItem(c);

        txtDias = new JTextField();
        txtDataLocacao = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        //Exibir "Todas"
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
            labels[i].setForeground(Color.WHITE);
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

    // novo construtor que carrega via DAO
    public TelaLocacao() {
        this(loadClientes(), loadVeiculos());
    }
    
    private static java.util.List<Cliente> loadClientes() {
        try {
            return new ClienteController().listarTodos();
        } catch (Exception ex) {
            System.err.println("Erro ao carregar clientes: " + ex.getMessage());
            ex.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
    
    private static java.util.List<Veiculo> loadVeiculos() {
        try {
            return new VeiculoController().listarTodos();
        } catch (Exception ex) {
            System.err.println("Erro ao carregar veículos: " + ex.getMessage());
            ex.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }

    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 16));
        return lbl;
    }

    private JButton criarBotaoEstilo(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(new Color(255, 102, 0));
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
        // recarrega veículos do banco para refletir estado atual
        listaVeiculos = veiculoController.listarTodos();

        String tipo = (String) comboTipoVeiculo.getSelectedItem();
        Marca marca = (Marca) comboMarca.getSelectedItem();
        Categoria categoria = (Categoria) comboCategoria.getSelectedItem();

        for (Veiculo v : listaVeiculos) {
            boolean tipoOK = tipo.equals("--Selecione--") || v.getClass().getSimpleName().equals(tipo);
            boolean marcaOK = marca == null || v.getMarca() == marca;
            boolean categoriaOK = categoria == null || v.getCategoria() == categoria;

            if ((v.getEstado() == Estado.DISPONIVEL ||v.getEstado() == Estado.NOVO)  && tipoOK && marcaOK && categoriaOK)
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
            // persiste alterações
            boolean ok = veiculoController.locar(v.getId(), c.getId(), dias, cal);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Veículo locado com sucesso!");
                filtrarVeiculos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao persistir locação. Verifique o log.");
            }
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
            switch (col) {
                case 0: return v.getPlaca();
                case 1: return v.getMarca();
                case 2:
                    if (v instanceof Automovel) return ((Automovel) v).getModelo();
                    if (v instanceof Motocicleta) return ((Motocicleta) v).getModelo();
                    if (v instanceof Van) return ((Van) v).getModelo();
                    return "N/A";
                case 3: return v.getAno();
                case 4: return String.format("R$ %.2f", v.getValorDiariaLocacao());
                default: return "";
            }
        }    
    }
}
