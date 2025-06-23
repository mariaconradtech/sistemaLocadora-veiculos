package telas;

import modelo.Cliente;
import controle.DadosSistema;

import javax.swing.*;
import java.awt.*;

public class TelaCliente extends JFrame {

    private JTextField txtNome, txtSobrenome, txtRg, txtCpf, txtEndereco;
    private JButton btnAdicionar, btnAtualizar, btnExcluir;
    private JTable tabela;
    private ClienteTableModel modelo;

    public TelaCliente() {
        setTitle("Cadastro de Clientes - VeloCuritiba");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel principal com fundo cinza
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

        JLabel[] labels = {
            new JLabel("Nome:"),
            new JLabel("Sobrenome:"),
            new JLabel("RG:"),
            new JLabel("CPF:"),
            new JLabel("Endereço:")
        };

        JComponent[] campos = {
            txtNome = new JTextField(),
            txtSobrenome = new JTextField(),
            txtRg = new JTextField(),
            txtCpf = new JTextField(),
            txtEndereco = new JTextField()
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

        painelPrincipal.add(Box.createVerticalStrut(30));

        // Tabela de clientes
        modelo = new ClienteTableModel(DadosSistema.listaClientes);
        tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setPreferredSize(new Dimension(700, 150));
        painelPrincipal.add(scrollPane);

        painelPrincipal.add(Box.createVerticalStrut(30));

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(64, 64, 64));

        btnAdicionar = criarBotaoEstilo("Adicionar");
        btnAtualizar = criarBotaoEstilo("Atualizar");
        btnExcluir = criarBotaoEstilo("Excluir");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);

        painelPrincipal.add(painelBotoes);

        add(painelPrincipal, BorderLayout.CENTER);

        // Ações dos botões
        btnAdicionar.addActionListener(e -> adicionarCliente());
        btnAtualizar.addActionListener(e -> atualizarCliente());
        btnExcluir.addActionListener(e -> excluirCliente());

        // Ação para preencher campos ao selecionar linha da tabela
        tabela.getSelectionModel().addListSelectionListener(e -> preencherCampos());

        setVisible(true);
    }

    private JButton criarBotaoEstilo(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(new Color(255, 102, 0));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 18));
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
        modelo.fireTableDataChanged();
        JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso.");
        limparCampos();
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
        Cliente cliente = DadosSistema.listaClientes.get(row);
        cliente.setNome(txtNome.getText().trim());
        cliente.setSobrenome(txtSobrenome.getText().trim());
        cliente.setRg(txtRg.getText().trim());
        cliente.setCpf(txtCpf.getText().trim());
        cliente.setEndereco(txtEndereco.getText().trim());

        modelo.fireTableDataChanged();
        JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso.");
        limparCampos();
    }

    private void excluirCliente() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.");
            return;
        }
        Cliente cliente = DadosSistema.listaClientes.get(row);
        if (cliente.isTemVeiculoLocado()) {
            JOptionPane.showMessageDialog(this, "Cliente possui veículo locado. Não pode ser excluído.");
        } else {
            DadosSistema.listaClientes.remove(row);
            modelo.fireTableDataChanged();
            JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso.");
            limparCampos();
        }
    }

    private void preencherCampos() {
        int row = tabela.getSelectedRow();
        if (row != -1) {
            Cliente cliente = DadosSistema.listaClientes.get(row);
            txtNome.setText(cliente.getNome());
            txtSobrenome.setText(cliente.getSobrenome());
            txtRg.setText(cliente.getRg());
            txtCpf.setText(cliente.getCpf());
            txtEndereco.setText(cliente.getEndereco());
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
        SwingUtilities.invokeLater(TelaCliente::new);
    }
}
