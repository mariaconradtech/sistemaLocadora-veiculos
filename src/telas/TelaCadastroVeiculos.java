/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telas;

import modelo.*;
import controle.DadosSistema;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroVeiculos extends JFrame {

    private JComboBox<String> comboTipo;
    private JComboBox<Marca> comboMarca;
    private JComboBox<Categoria> comboCategoria;
    private JTextField txtValorCompra, txtPlaca, txtAno;
    private JComboBox<Object> comboModelo;

    public TelaCadastroVeiculos() {
        setTitle("Cadastro de Veículos - VeloCuritiba");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel principal com fundo cinza
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(64, 64, 64));
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        
        JLabel lblLogo = new JLabel("VELOCURITIBA", SwingConstants.CENTER);
        lblLogo.setForeground(new Color(255, 102, 0));
        lblLogo.setFont(new Font("Impact", Font.BOLD, 50));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(lblLogo);

        
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

        JLabel[] labels = {
            new JLabel("Tipo de Veículo:"),
            new JLabel("Marca:"),
            new JLabel("Categoria:"),
            new JLabel("Valor de Compra:"),
            new JLabel("Placa:"),
            new JLabel("Ano:"),
            new JLabel("Modelo:")
        };

        JComponent[] campos = {
            comboTipo = new JComboBox<>(new String[]{"Automóvel", "Motocicleta", "Van"}),
            comboMarca = new JComboBox<>(Marca.values()),
            comboCategoria = new JComboBox<>(Categoria.values()),
            txtValorCompra = new JTextField(),
            txtPlaca = new JTextField(),
            txtAno = new JTextField(),
            comboModelo = new JComboBox<>()
        };

        for (int i = 0; i < labels.length; i++) {
            labels[i].setForeground(Color.WHITE);

            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.3;
            painelCampos.add(labels[i], gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
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
            double valorCompra = Double.parseDouble(txtValorCompra.getText());
            String placa = txtPlaca.getText();
            int ano = Integer.parseInt(txtAno.getText());

            Veiculo veiculo = null;
            Object modeloSelecionado = comboModelo.getSelectedItem();

            if (tipo.equals("Automóvel") && modeloSelecionado instanceof ModeloAutomovel) {
                veiculo = new Automovel(marca, Estado.DISPONIVEL, categoria, valorCompra, placa, ano, (ModeloAutomovel) modeloSelecionado);
            } else if (tipo.equals("Motocicleta") && modeloSelecionado instanceof ModeloMotocicleta) {
                veiculo = new Motocicleta(marca, Estado.DISPONIVEL, categoria, valorCompra, placa, ano, (ModeloMotocicleta) modeloSelecionado);
            } else if (tipo.equals("Van") && modeloSelecionado instanceof ModeloVan) {
                veiculo = new Van(marca, Estado.DISPONIVEL, categoria, valorCompra, placa, ano, (ModeloVan) modeloSelecionado);
            }

            if (veiculo != null) {
                DadosSistema.listaVeiculos.add(veiculo);
                JOptionPane.showMessageDialog(this, "Veículo cadastrado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao criar o veículo. Verifique os campos.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preencha os campos numéricos corretamente.");
        }
    }

    private void limparCampos() {
        txtValorCompra.setText("");
        txtPlaca.setText("");
        txtAno.setText("");
        comboModelo.setSelectedIndex(-1);
    }
}
