
package telas; 

import modelo.Cliente; 
import controle.DadosSistema; 

import javax.swing.*; // Importa todos os componentes Swing para interface gráfica
import java.awt.*; // Importa classes para layout e cores


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
        setTitle("Cadastro de Clientes - VeloCuritiba"); 
        setSize(900, 650); // Tamanho da janela
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas esta janela ao clicar no X
        setLayout(new BorderLayout()); 

        JPanel painelPrincipal = new JPanel(); // Painel principal que conterá todos os componentes
        painelPrincipal.setBackground(new Color(45, 45, 45)); 
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS)); // Layout vertical
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Margem interna

        JLabel lblLogo = new JLabel("VELOCURITIBA", SwingConstants.CENTER); // Título estilizado
        lblLogo.setForeground(new Color(255, 102, 0)); 
        lblLogo.setFont(new Font("Impact", Font.BOLD, 56)); // Fonte grande 
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza no painel
        painelPrincipal.add(lblLogo);

        JLabel lblSlogan = new JLabel("Seu caminho, nossa direção", SwingConstants.CENTER); 
        lblSlogan.setForeground(Color.LIGHT_GRAY);
        lblSlogan.setFont(new Font("SansSerif", Font.ITALIC, 22));
        lblSlogan.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(lblSlogan);

        painelPrincipal.add(Box.createVerticalStrut(25)); 

        JPanel painelCampos = new JPanel(new GridBagLayout()); // Painel com layout flexível
        painelCampos.setBackground(new Color(64, 64, 64)); 
        painelCampos.setBorder(BorderFactory.createTitledBorder( // Borda com título
            BorderFactory.createLineBorder(Color.GRAY),
            "Informações do Cliente",
            0, 0,
            new Font("Arial", Font.BOLD, 16),
            Color.WHITE));

        GridBagConstraints gbc = new GridBagConstraints(); // Restrições para posicionamento
        gbc.insets = new Insets(8, 10, 8, 10); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Ocupa todo o espaço horizontal

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

        // Adiciona os rótulos e campos no painel de forma organizada
        for (int i = 0; i < labels.length; i++) {
            labels[i].setForeground(Color.WHITE);
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0.3;
            painelCampos.add(labels[i], gbc);

            gbc.gridx = 1; gbc.weightx = 1.0;
            painelCampos.add(campos[i], gbc);
        }

        painelPrincipal.add(painelCampos); // Adiciona painel de campos ao painel principal
        painelPrincipal.add(Box.createVerticalStrut(25)); 

        modelo = new ClienteTableModel(DadosSistema.listaClientes); // Modelo da tabela com dados
        tabela = new JTable(modelo); // Cria tabela com o modelo

        // Estiliza a tabela
        tabela.setRowHeight(24);
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        tabela.setBackground(new Color(80, 80, 80));
        tabela.setForeground(Color.WHITE);
        tabela.setGridColor(Color.GRAY);
        tabela.getTableHeader().setBackground(new Color(100, 100, 100));
        tabela.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tabela); // Adiciona rolagem à tabela
        scrollPane.getViewport().setBackground(new Color(80, 80, 80)); 
        scrollPane.setPreferredSize(new Dimension(820, 160)); 
        painelPrincipal.add(scrollPane);

        painelPrincipal.add(Box.createVerticalStrut(20));

        JPanel painelBotoes = new JPanel(); // Painel para os botões
        painelBotoes.setBackground(new Color(64, 64, 64));

        // Cria os botões com estilo
        btnAdicionar = criarBotaoEstilo("Adicionar");
        btnAtualizar = criarBotaoEstilo("Atualizar");
        btnExcluir = criarBotaoEstilo("Excluir");

        // Adiciona os botões ao painel
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);

        painelPrincipal.add(painelBotoes); // Adiciona o painel de botões

        add(painelPrincipal, BorderLayout.CENTER); // Adiciona tudo à janela

        // Define ações dos botões
        btnAdicionar.addActionListener(e -> adicionarCliente());
        btnAtualizar.addActionListener(e -> atualizarCliente());
        btnExcluir.addActionListener(e -> excluirCliente());

        // Define ação ao selecionar linha na tabela
        tabela.getSelectionModel().addListSelectionListener(e -> preencherCampos());

        setVisible(true); // Torna a janela visível
    }

    // Método para criar botão com estilo personalizado
    private JButton criarBotaoEstilo(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(new Color(255, 102, 0)); // Cor laranja
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setFocusPainted(false); // Remove borda ao focar
        botao.setPreferredSize(new Dimension(130, 40));
        return botao;
    }

    // Adiciona novo cliente à lista
    private void adicionarCliente() {
        if (camposEstaoVazios()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }
        if (!validarCpf(txtCpf.getText().trim())) {
            JOptionPane.showMessageDialog(this, "CPF inválido.");
            return;
        }
        if (!validarRg(txtRg.getText().trim())) {
            JOptionPane.showMessageDialog(this, "RG inválido. Deve conter apenas números e ter entre 5 e 14 dígitos.");
            return;
        }

        Cliente c = new Cliente(
            txtNome.getText().trim(),
            txtSobrenome.getText().trim(),
            txtRg.getText().trim(),
            txtCpf.getText().trim(),
            txtEndereco.getText().trim()
        );

        DadosSistema.listaClientes.add(c); // Adiciona à lista
        modelo.fireTableDataChanged(); // Atualiza a tabela
        JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso.");
        limparCampos(); // Limpa os campos após adicionar
    }

    // Atualiza os dados de um cliente selecionado
    private void atualizarCliente() {
        int row = tabela.getSelectedRow(); // Linha selecionada
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para atualizar.");
            return;
        }
        if (camposEstaoVazios()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }
        if (!validarCpf(txtCpf.getText().trim())) {
            JOptionPane.showMessageDialog(this, "CPF inválido.");
            return;
        }
        if (!validarRg(txtRg.getText().trim())) {
            JOptionPane.showMessageDialog(this, "RG inválido.");
            return;
        }

        Cliente cliente = DadosSistema.listaClientes.get(row); // Obtém cliente da lista
        cliente.setNome(txtNome.getText().trim());
        cliente.setSobrenome(txtSobrenome.getText().trim());
        cliente.setRg(txtRg.getText().trim());
        cliente.setCpf(txtCpf.getText().trim());
        cliente.setEndereco(txtEndereco.getText().trim());

        modelo.fireTableDataChanged(); // Atualiza tabela
        JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso.");
        limparCampos();
    }

    // Exclui o cliente da lista (se permitido)
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
            DadosSistema.listaClientes.remove(row); // Remove cliente da lista
            modelo.fireTableDataChanged(); // Atualiza a tabela
            JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso.");
            limparCampos();
        }
    }

    // Preenche os campos com os dados do cliente selecionado na tabela
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

    // Verifica se há algum campo em branco
    private boolean camposEstaoVazios() {
        return txtNome.getText().trim().isEmpty() ||
               txtSobrenome.getText().trim().isEmpty() ||
               txtRg.getText().trim().isEmpty() ||
               txtCpf.getText().trim().isEmpty() ||
               txtEndereco.getText().trim().isEmpty();
    }

    // Valida RG: deve conter apenas números e ter de 5 a 14 dígitos
    private boolean validarRg(String rg) {
        return rg.matches("\\d{5,14}");
    }

    // Valida CPF com base nos dígitos verificadores
    private boolean validarCpf(String cpf) {
        cpf = cpf.replaceAll("[.-]", ""); // Remove pontos e traço
        if (!cpf.matches("\\d{11}")) return false; // Verifica se tem 11 dígitos
        if (cpf.chars().distinct().count() == 1) return false; // Verifica se todos os dígitos são iguais

        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cpf.charAt(i));
            soma1 += digito * (10 - i);
            soma2 += digito * (11 - i);
        }
        int digito1 = (soma1 * 10) % 11;
        if (digito1 == 10) digito1 = 0;
        soma2 += digito1 * 2;
        int digito2 = (soma2 * 10) % 11;
        if (digito2 == 10) digito2 = 0;

        return digito1 == Character.getNumericValue(cpf.charAt(9)) &&
               digito2 == Character.getNumericValue(cpf.charAt(10));
    }

    // Limpa todos os campos da tela
    private void limparCampos() {
        txtNome.setText("");
        txtSobrenome.setText("");
        txtRg.setText("");
        txtCpf.setText("");
        txtEndereco.setText("");
        tabela.clearSelection();
    }

    // Método principal: executa a tela
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaCliente::new); // Executa na thread de interface gráfica
    }
}
