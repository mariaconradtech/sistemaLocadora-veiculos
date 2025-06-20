/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telas;


import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private JButton btnClientes, btnVeiculos, btnLocacoes, btnSair;

    public TelaPrincipal() {
        setTitle("Sistema de Locadora de Veículos");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Cabeçalho
        JLabel titulo = new JLabel("Bem-vindo à Locadora de Veículos", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titulo, BorderLayout.NORTH);

        // Painel de botões central
        JPanel painelCentral = new JPanel(new GridLayout(3, 1, 10, 10));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        btnClientes = new JButton("Gerenciar Clientes");
        btnVeiculos = new JButton("Gerenciar Veículos");
        btnLocacoes = new JButton("Gerenciar Locações");

        painelCentral.add(btnClientes);
        painelCentral.add(btnVeiculos);
        painelCentral.add(btnLocacoes);

        add(painelCentral, BorderLayout.CENTER);

        // Rodapé com botão de sair
        btnSair = new JButton("Sair");
        JPanel painelRodape = new JPanel();
        painelRodape.add(btnSair);
        add(painelRodape, BorderLayout.SOUTH);

        // Eventos dos botões
        btnClientes.addActionListener(e -> new TelaCliente());
        btnVeiculos.addActionListener(e -> new TelaVeiculo());
        btnLocacoes.addActionListener(e -> new TelaLocacao());
        btnSair.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaPrincipal::new);
    }
}
