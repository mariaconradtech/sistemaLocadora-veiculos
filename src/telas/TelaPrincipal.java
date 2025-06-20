/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package telas;

import javax.swing.*;
import java.awt.*;
import controle.DadosSistema;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("VeloCuritiba - Locadora de Veículos");

        // Tela cheia
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Painel principal com fundo cinza
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(64, 64, 64)); 
        painelPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Espaçamento entre os componentes

        // Logo estilizada
        JLabel lblLogo = new JLabel("VELOCURITIBA");
        lblLogo.setForeground(new Color(255, 102, 0)); 
        lblLogo.setFont(new Font("Impact", Font.BOLD, 80)); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        painelPrincipal.add(lblLogo, gbc);

        // Slogan
        JLabel lblSlogan = new JLabel("Seu caminho, nossa direção");
        lblSlogan.setForeground(Color.LIGHT_GRAY);
        lblSlogan.setFont(new Font("SansSerif", Font.ITALIC, 28));
        gbc.gridy = 1;
        painelPrincipal.add(lblSlogan, gbc);

        // Painel para botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(64, 64, 64));  
        painelBotoes.setLayout(new GridLayout(5, 1, 25, 25));  // 5 linhas, 1 coluna, espaço entre botões

       
        JButton btnClientes = criarBotaoEstilo("Clientes");
        JButton btnVeiculos = criarBotaoEstilo("Cadastrar veículo");
        JButton btnLocacao = criarBotaoEstilo("Locação");
        JButton btnDevolucao = criarBotaoEstilo("Devolução");
        JButton btnVenda = criarBotaoEstilo("Venda");

        painelBotoes.add(btnClientes);
        painelBotoes.add(btnVeiculos);
        painelBotoes.add(btnLocacao);
        painelBotoes.add(btnDevolucao);
        painelBotoes.add(btnVenda);

        gbc.gridy = 2;
        painelPrincipal.add(painelBotoes, gbc);

        // Ações dos botões
        btnClientes.addActionListener(e -> new TelaCliente().setVisible(true));
        btnVeiculos.addActionListener(e -> new TelaCadastroVeiculos().setVisible(true));
        //btnLocacao.addActionListener(e -> new TelaLocacao(DadosSistema.listaClientes, DadosSistema.listaVeiculos).setVisible(true));
        //btnDevolucao.addActionListener(e -> new TelaDevolucao(DadosSistema.listaVeiculos).setVisible(true));
        //btnVenda.addActionListener(e -> new TelaVenda(DadosSistema.listaVeiculos).setVisible(true));


        add(painelPrincipal);
    }

    private JButton criarBotaoEstilo(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(new Color(255, 102, 0)); 
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 26));
        botao.setFocusPainted(false); 
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 
        return botao;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}


