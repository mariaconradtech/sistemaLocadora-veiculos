package telas;

import modelo.Cliente;
import controle.DadosSistema;

import javax.swing.*;
import java.awt.*;

// Classe da tela de cadastro de clientes
public class TelaCliente extends JFrame {

    // Campos de entrada de texto
    private JTextField txtNome, txtSobrenome, txtRg, txtCpf, txtEndereco;
    // Botões de ação
    private JButton btnAdicionar, btnAtualizar, btnExcluir;
    // Tabela para listar os clientes e seu modelo
    private JTable tabela;
    private ClienteTableModel modelo;

    // Construtor da tela
    public TelaCliente() {
        setTitle("Cadastro de Clientes - VeloCuritiba"); // Título da janela
        setSize(900, 650); // Tamanho da janela
        setLocationRelativeTo(null); // Centraliza a janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas essa janela
        setLayout(new BorderLayout()); // Layout principal

        // Painel principal com fundo e layout vertical
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(45, 45, 45));
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Margens internas

        // Logo estilizado
        JLabel lblLogo = new JLabel("VELOCURITIBA", SwingConstants.CENTER);
        lblLogo.setForeground(new Color(255, 102, 0));
        lblLogo.setFont(new Font("Impact", Font.BOLD, 56));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(lblLogo);

        // Slogan abaixo do logo
        JLabel lblSlogan = new JLabel("Seu caminho, nossa direção", SwingConstants.CENTER);
        lblSlogan.setForeground(Color.LIGHT_GRAY);
        lblSlogan.setFont(new Font("SansSerif", Font.ITALIC, 22));
        lblSlogan.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(lblSlogan);

        painelPrincipal.add(Box.createVerticalStrut(25)); // Espaçamento

        // Painel para os campos de formulário
        JPanel painelCampos = new JPanel(new GridBagLayout());
        painelCampos.setBackground(new Color(64, 64, 64));
        painelCampos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            "Informações do Cliente",
            0, 0,
            new Font("Arial", Font.BOLD, 16),
            Color.WHITE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expandir horizontalmente

        // Rótulos dos campos
        JLabel[] labels = {
            new JLabel("Nome:"),
            new JLabel("Sobrenome:"),
            new JLabel("RG:"),
            new JLabel("CPF:"),
            new JLabel("Endereço:")
        };

        // Campos de texto
        JComponent[] campos = {
            txtNome = new JTextField(),
            txtSobrenome = new JTextField(),
            txtRg = new JTextField(),
            txtCpf = new JTextField(),
            txtEndereco = new JTextField()
        };

        // Adiciona os rótulos e campos ao painel com GridBagLayout
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
        painelPrincipal.add(Box.createVerticalStrut(25));

        // Tabela de clientes com fundo cinza estilizado
        modelo = new ClienteTableModel(DadosSistema.listaClientes);
        tabela = new JTable(modelo);
        tabela.setRowHeight(24);
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        tabela.setBackground(new Color(80, 80, 80));
        tabela.setForeground(Color.WHITE);
        tabela.setGridColor(Color.GRAY);
        tabela.getTableHeader().setBackground(new Color(100, 100, 100));
        tabela.getTableHeader().setForeground(Color.WHITE);

        // Scroll da tabela
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.getViewport().setBackground(new Color(80, 80, 80));
        scrollPane.setPreferredSize(new Dimension(820, 160));
        painelPrincipal.add(scrollPane);

        painelPrincipal.add(Box.createVerticalStrut(20));

        // Painel de botões com fundo escuro
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(64, 64, 64));

        // Botões de ação
        btnAdicionar = criarBotaoEstilo("Adicionar");
        btnAtualizar = criarBotaoEstilo("Atualizar");
        btnExcluir = criarBotaoEstilo("Excluir");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);

        painelPrincipal.add(painelBotoes);

        // Adiciona tudo à tela principal
        add(painelPrincipal, BorderLayout.CENTER);

        // Ações dos botões
        btnAdicionar.addActionListener(e -> adicionarCliente());
        btnAtualizar.addActionListener(e -> atualizarCliente());
        btnExcluir.addActionListener(e -> excluirCliente());

        // Preenche campos ao selecionar linha na tabela
        tabela.getSelectionModel().addListSelectionListener(e -> preencherCampos());

        setVisible(true); // Exibe a janela
    }

    // Cria botões com estilo personalizado
    private JButton criarBotaoEstilo(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(new Color(255, 102, 0));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(130, 40));
        return botao;
    }

    // Adiciona cliente à lista
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

    // Atualiza cliente selecionado na tabela
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

    // Remove cliente, se permitido
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

    // Preenche os campos com dados do cliente selecionado
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

    // Verifica se algum campo está vazio
    private boolean camposEstaoVazios() {
        return txtNome.getText().trim().isEmpty() ||
               txtSobrenome.getText().trim().isEmpty() ||
               txtRg.getText().trim().isEmpty() ||
               txtCpf.getText().trim().isEmpty() ||
               txtEndereco.getText().trim().isEmpty();
    }

    // Limpa os campos e desmarca a seleção da tabela
    private void limparCampos() {
        txtNome.setText("");
        txtSobrenome.setText("");
        txtRg.setText("");
        txtCpf.setText("");
        txtEndereco.setText("");
        tabela.clearSelection();
    }

    // Método principal que inicia a tela
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaCliente::new);
    }
}
