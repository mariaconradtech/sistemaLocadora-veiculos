package telas;

import modelo.*;
import controle.VeiculoController;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

public class TelaVenda extends JFrame {

    private JComboBox<String> comboTipoVeiculo;
    private JComboBox<Marca> comboMarca;
    private JComboBox<Categoria> comboCategoria;
    private JTable tabelaVeiculos;

    private List<Veiculo> listaVeiculos;
    private List<Veiculo> veiculosFiltrados = new ArrayList<>();
    private VeiculoController veiculoController = new VeiculoController();

    private final Color laranja = new Color(255, 102, 0);
    private final Color textoClaro = Color.WHITE;
    private final Font fontePadrao = new Font("Arial", Font.PLAIN, 16);

    public TelaVenda(List<Veiculo> veiculos) {
        this.listaVeiculos = veiculos;

        setTitle("Venda de Veículos - VeloCuritiba");
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
            criarLabel("Tipo de Veículo:"),
            criarLabel("Marca:"),
            criarLabel("Categoria:")
        };

        comboTipoVeiculo = new JComboBox<>(new String[]{"--Selecione--", "Automovel", "Motocicleta", "Van"});

        comboMarca = new JComboBox<>();
        comboMarca.addItem(null);
        for (Marca m : Marca.values()) comboMarca.addItem(m);

        comboCategoria = new JComboBox<>();
        comboCategoria.addItem(null);
        for (Categoria c : Categoria.values()) comboCategoria.addItem(c);

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

        JComponent[] campos = {comboTipoVeiculo, comboMarca, comboCategoria};

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
        JButton btnVender = criarBotaoEstilo("Vender");

        btnFiltrar.addActionListener(e -> filtrarVeiculos());
        btnVender.addActionListener(e -> venderVeiculo());

        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(64, 64, 64));
        painelBotoes.add(btnFiltrar);
        painelBotoes.add(btnVender);

        painelPrincipal.add(painelBotoes);
        add(painelPrincipal, BorderLayout.CENTER);

        filtrarVeiculos();
        setVisible(true);
    }

    // construtor que carrega do DB
    public TelaVenda() {
        this(new VeiculoController().listarTodos());
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
        tabelaVeiculos = new JTable(new VeiculoVendaTableModel(veiculosFiltrados));
        return tabelaVeiculos;
    }

    private void filtrarVeiculos() {
        veiculosFiltrados.clear();
        // recarrega do DB
        listaVeiculos = veiculoController.listarTodos();

        String tipo = (String) comboTipoVeiculo.getSelectedItem();
        Marca marca = (Marca) comboMarca.getSelectedItem();
        Categoria categoria = (Categoria) comboCategoria.getSelectedItem();

        for (Veiculo v : listaVeiculos) {
            boolean tipoOK = tipo.equals("--Selecione--") || v.getClass().getSimpleName().equals(tipo);
            boolean marcaOK = marca == null || v.getMarca() == marca;
            boolean categoriaOK = categoria == null || v.getCategoria() == categoria;

            if ((v.getEstado() == Estado.DISPONIVEL || v.getEstado() == Estado.NOVO) && tipoOK && marcaOK && categoriaOK)
                veiculosFiltrados.add(v);
        }

        tabelaVeiculos.setModel(new VeiculoVendaTableModel(veiculosFiltrados));
    }

    private void venderVeiculo() {
        int linha = tabelaVeiculos.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo.");
            return;
        }

        Veiculo v = veiculosFiltrados.get(linha);
        v.vender();
        boolean ok = veiculoController.vender(v.getId());
        if (ok) {
            JOptionPane.showMessageDialog(this, "Veículo vendido com sucesso!");
            filtrarVeiculos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao persistir venda. Verifique o log.");
        }
    }

    class VeiculoVendaTableModel extends AbstractTableModel {
        private final String[] colunas = {"Placa", "Marca", "Modelo", "Ano", "Preço para Venda"};
        private final List<Veiculo> veiculos;

        public VeiculoVendaTableModel(List<Veiculo> veiculos) {
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
                case 4: return NumberFormat.getCurrencyInstance(Locale.of("pt", "BR")).format(v.getValorParaVenda());
                default: return "";
            }
        }
    }

 }
