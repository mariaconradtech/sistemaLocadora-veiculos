package telas;

import modelo.*;
import controle.VeiculoController;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;

public class TelaCadastroVeiculos extends JFrame {

    private JComboBox<String> comboTipo;
    private JComboBox<Marca> comboMarca;
    private JComboBox<Categoria> comboCategoria;
    private JFormattedTextField txtValorCompra;
    private JFormattedTextField txtPlaca;
    private JTextField txtAno;
    private JComboBox<Object> comboModelo;
    private VeiculoController veiculoController = new VeiculoController();

    public TelaCadastroVeiculos() {
        setTitle("Cadastro de Veículos - VeloCuritiba");

        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

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

        // Painel de campos
        JPanel painelCampos = new JPanel(new GridBagLayout());
        painelCampos.setBackground(new Color(64, 64, 64));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;

        // Máscara para Placa (ABC-1234)
        try {
            MaskFormatter placaFormatter = new MaskFormatter("UUU-####");
            placaFormatter.setPlaceholderCharacter('_');
            txtPlaca = new JFormattedTextField(placaFormatter);
        } catch (ParseException e) {
            txtPlaca = new JFormattedTextField();
        }

        // Máscara para Valor de Compra (Moeda)
        NumberFormat formatoMoeda = NumberFormat.getNumberInstance();
        txtValorCompra = new JFormattedTextField(formatoMoeda);
        txtValorCompra.setColumns(10);

        // Campos e Labels
        JLabel[] labels = {
            new JLabel("Tipo de Veículo:"),
            new JLabel("Marca:"),
            new JLabel("Modelo:"),
            new JLabel("Categoria:"),
            new JLabel("Valor de Compra (R$):"),
            new JLabel("Placa:"),
            new JLabel("Ano:")
        };

        JComponent[] campos = {
            comboTipo = new JComboBox<>(new String[]{"Automóvel", "Motocicleta", "Van"}),
            comboMarca = new JComboBox<>(Marca.values()),
            comboModelo = new JComboBox<>(),
            comboCategoria = new JComboBox<>(Categoria.values()),
            txtValorCompra,
            txtPlaca,
            txtAno = new JTextField()
        };

        Dimension campoDimensao = new Dimension(300, 30);

        for (int i = 0; i < labels.length; i++) {
            labels[i].setForeground(Color.WHITE);
            labels[i].setFont(new Font("SansSerif", Font.BOLD, 16));

            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.3;
            painelCampos.add(labels[i], gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;

            campos[i].setPreferredSize(campoDimensao);
            painelCampos.add(campos[i], gbc);
        }

        painelPrincipal.add(painelCampos);

        comboTipo.addActionListener(e -> atualizarModelos());

        painelPrincipal.add(Box.createVerticalStrut(30));

        JButton btnSalvar = criarBotaoEstilo("Salvar Veículo");
        btnSalvar.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(btnSalvar);

        btnSalvar.addActionListener(e -> salvarVeiculo());

        add(painelPrincipal, BorderLayout.CENTER);
    }

    private JButton criarBotaoEstilo(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(new Color(255, 102, 0));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 18));
        botao.setFocusPainted(false);
        return botao;
    }

    private void atualizarModelos() {
        comboModelo.removeAllItems();
        String tipo = (String) comboTipo.getSelectedItem();
        
        if (tipo == null || tipo.isEmpty()) {
            return; // Sai se nenhum tipo selecionado
        }
        
        if (tipo.equals("Automóvel")) {
            for (ModeloAutomovel m : ModeloAutomovel.values()) {
                comboModelo.addItem(m);
            }
        } else if (tipo.equals("Motocicleta")) {
            for (ModeloMotocicleta m : ModeloMotocicleta.values()) {
                comboModelo.addItem(m);
            }
        } else if (tipo.equals("Van")) {
            for (ModeloVan m : ModeloVan.values()) {
                comboModelo.addItem(m);
            }
        }
    }

    private void salvarVeiculo() {
        try {
            String tipo = (String) comboTipo.getSelectedItem();
            Marca marca = (Marca) comboMarca.getSelectedItem();
            Categoria categoria = (Categoria) comboCategoria.getSelectedItem();

            // Converte o texto da máscara para double
            String valorTexto = txtValorCompra.getText().replace(".", "").replace(",", ".");
            double valorCompra = Double.parseDouble(valorTexto);

            int ano = Integer.parseInt(txtAno.getText());
            int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
            if (ano < 1900 || ano > (anoAtual + 1)) {
                JOptionPane.showMessageDialog(this, "Ano inválido! Digite um ano entre 1900 e " + (anoAtual + 1) + ".");
                return;
            }

            String placa = txtPlaca.getText();
            Object modeloSelecionado = comboModelo.getSelectedItem();

            Veiculo veiculo = null;

            if (tipo.equals("Automóvel") && modeloSelecionado instanceof ModeloAutomovel) {
                veiculo = veiculoController.inserirAutomovel(marca, categoria, valorCompra, placa, ano, (ModeloAutomovel) modeloSelecionado);
            } else if (tipo.equals("Motocicleta") && modeloSelecionado instanceof ModeloMotocicleta) {
                veiculo = veiculoController.inserirMotocicleta(marca, categoria, valorCompra, placa, ano, (ModeloMotocicleta) modeloSelecionado);
            } else if (tipo.equals("Van") && modeloSelecionado instanceof ModeloVan) {
                veiculo = veiculoController.inserirVan(marca, categoria, valorCompra, placa, ano, (ModeloVan) modeloSelecionado);
            }

            if (veiculo != null) {
                JOptionPane.showMessageDialog(this, "Veículo cadastrado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao criar o veículo. Verifique os campos.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preencha corretamente o Valor de Compra e o Ano.");
        }
    }

    private void limparCampos() {
        txtValorCompra.setValue(null);
        txtPlaca.setValue(null);
        txtAno.setText("");
        comboModelo.setSelectedIndex(-1);
    }
}