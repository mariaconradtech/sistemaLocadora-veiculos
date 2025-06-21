package telas;

import modelo.Cliente;
import controle.DadosSistema;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class TelaCliente extends JFrame {
    private JTextField txtNome, txtSobrenome, txtRg, txtCpf, txtEndereco;
    private JButton btnAdicionar, btnAtualizar, btnExcluir;
    private JTable tabela;
    private ClienteTableModel modelo;

    public TelaCliente() {
        setTitle("Cadastro de Clientes");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Tema moderno FlatLaf Dark
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            System.out.println("Não foi possível aplicar FlatLaf: " + ex.getMessage());
        }

        // Layout principal
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        modelo = new ClienteTableModel(new ArrayList<>());
        tabela = new JTable(modelo);
        tabela.setAutoCreateRowSorter(true);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setPreferredSize(new Dimension(700, 300));
        add(scroll, BorderLayout.CENTER);

        // Painel formulário com GridBagLayout para flexibilidade
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBorder(BorderFactory.createTitledBorder("Dados do Cliente"));
        add(painelForm, BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels e TextFields
        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField(15);
        JLabel lblSobrenome = new JLabel("Sobrenome:");
        txtSobrenome = new JTextField(15);
        JLabel lblRg = new JLabel("RG:");
        txtRg = new JTextField(15);
        JLabel lblCpf = new JLabel("CPF:");
        txtCpf = new JTextField(15);
        JLabel lblEndereco = new JLabel("Endereço:");
        txtEndereco = new JTextField(15);

        // Linha 1
        gbc.gridx = 0; gbc.gridy = 0;
        painelForm.add(lblNome, gbc);
        gbc.gridx = 1;
        painelForm.add(txtNome, gbc);

        gbc.gridx = 2;
        painelForm.add(lblSobrenome, gbc);
        gbc.gridx = 3;
        painelForm.add(txtSobrenome, gbc);

        // Linha 2
        gbc.gridx = 0; gbc.gridy = 1;
        painelForm.add(lblRg, gbc);
        gbc.gridx = 1;
        painelForm.add(txtRg, gbc);

        gbc.gridx = 2;
        painelForm.add(lblCpf, gbc);
        gbc.gridx = 3;
        painelForm.add(txtCpf, gbc);

        // Linha 3
        gbc.gridx = 0; gbc.gridy = 2;
        painelForm.add(lblEndereco, gbc);
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        painelForm.add(txtEndereco, gbc);
        gbc.gridwidth = 1;

        // Painel para botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        painelBotoes.setBorder(new EmptyBorder(10, 0, 0, 0));
        btnAdicionar = criarBotao("Adicionar");
        btnAtualizar = criarBotao("Atualizar");
        btnExcluir = criarBotao("Excluir");
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);

        gbc.gridx = 3; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        painelForm.add(painelBotoes, gbc);

        // Ações dos botões
        btnAdicionar.addActionListener(e -> adicionarCliente());
        btnAtualizar.addActionListener(e -> atualizarCliente());
        btnExcluir.addActionListener(e -> excluirCliente());

        // Preencher campos ao selecionar na tabela
        tabela.getSelectionModel().addListSelectionListener(e -> preencherCampos());

        setVisible(true);
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setPreferredSize(new Dimension(110, 35));
        botao.setBackground(new Color(255, 102, 0));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setFocusPainted(false);
        return botao;
    }

    private void adicionarCliente() {
        if (camposEstaoVazios()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }
        Cliente c = new Cliente(
                txtNome.getText().trim(),
                txtSobrenome.getText().trim(),
                txtRg.getText().trim(),
                txtCpf.getText().trim(),
                txtEndereco.getText().trim()
        );
        DadosSistema.listaClientes.add(c);
        modelo.adicionar(c);
        limparCampos();
        JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso.");
    }

    private void atualizarCliente() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para atualizar.");
            return;
        }
        if (camposEstaoVazios()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }
        Cliente clienteAtual = modelo.getCliente(row);
        Cliente atualizado = new Cliente(
                txtNome.getText().trim(),
                txtSobrenome.getText().trim(),
                txtRg.getText().trim(),
                txtCpf.getText().trim(),
                txtEndereco.getText().trim()
        );
        atualizado.setTemVeiculoLocado(clienteAtual.isTemVeiculoLocado());

        modelo.atualizar(row, atualizado);
        limparCampos();
        JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso.");
    }

    private void excluirCliente() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.");
            return;
        }
        Cliente c = modelo.getCliente(row);
        if (c.isTemVeiculoLocado()) {
            JOptionPane.showMessageDialog(this, "Cliente possui veículo locado. Não pode ser excluído.");
        } else {
            modelo.remover(row);
            limparCampos();
            JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso.");
        }
    }

    private void preencherCampos() {
        int row = tabela.getSelectedRow();
        if (row != -1) {
            Cliente c = modelo.getCliente(row);
            txtNome.setText(c.getNome());
            txtSobrenome.setText(c.getSobrenome());
            txtRg.setText(c.getRg());
            txtCpf.setText(c.getCpf());
            txtEndereco.setText(c.getEndereco());
        }
    }

    private boolean camposEstaoVazios() {
        return txtNome.getText().trim().isEmpty() ||
                txtSobrenome.getText().trim().isEmpty() ||
                txtRg.getText().trim().isEmpty() ||
                txtCpf.getText().trim().isEmpty() ||
                txtEndereco.getText().trim().isEmpty();
    }

    private void limparCampos() {
        txtNome.setText("");
        txtSobrenome.setText("");
        txtRg.setText("");
        txtCpf.setText("");
        txtEndereco.setText("");
        tabela.clearSelection();
    }

    public static void main(String[] args) {
        // Aplica tema antes da criação da UI
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
        } catch (Exception ex) {
            System.out.println("Não foi possível aplicar FlatLaf: " + ex.getMessage());
        }
        SwingUtilities.invokeLater(TelaCliente::new);
    }
}
