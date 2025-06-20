/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telas;

/**
 *
 * @author rodri
 */
import modelo.Cliente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TelaCliente extends JFrame {
    private JTextField txtNome, txtSobrenome, txtRg, txtCpf, txtEndereco;
    private JButton btnAdicionar, btnAtualizar, btnExcluir;
    private JTable tabela;
    private ClienteTableModel modelo;

    public TelaCliente() {
        setTitle("Cadastro de Clientes");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        modelo = new ClienteTableModel(new ArrayList<>());
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        JPanel painelForm = new JPanel(new GridLayout(6, 2));
        txtNome = new JTextField();
        txtSobrenome = new JTextField();
        txtRg = new JTextField();
        txtCpf = new JTextField();
        txtEndereco = new JTextField();

        painelForm.add(new JLabel("Nome:"));
        painelForm.add(txtNome);
        painelForm.add(new JLabel("Sobrenome:"));
        painelForm.add(txtSobrenome);
        painelForm.add(new JLabel("RG:"));
        painelForm.add(txtRg);
        painelForm.add(new JLabel("CPF:"));
        painelForm.add(txtCpf);
        painelForm.add(new JLabel("Endereço:"));
        painelForm.add(txtEndereco);

        btnAdicionar = new JButton("Adicionar");
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");

        painelForm.add(btnAdicionar);
        painelForm.add(btnAtualizar);

        add(painelForm, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(btnExcluir, BorderLayout.SOUTH);

        // Ações
        btnAdicionar.addActionListener(e -> adicionarCliente());
        btnAtualizar.addActionListener(e -> atualizarCliente());
        btnExcluir.addActionListener(e -> excluirCliente());

        setVisible(true);
    }

    private void adicionarCliente() {
        Cliente c = new Cliente(
                txtNome.getText(),
                txtSobrenome.getText(),
                txtRg.getText(),
                txtCpf.getText(),
                txtEndereco.getText()
        );
        modelo.adicionar(c);
        limparCampos();
    }

    private void atualizarCliente() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.");
            return;
        }

        Cliente c = new Cliente(
                txtNome.getText(),
                txtSobrenome.getText(),
                txtRg.getText(),
                txtCpf.getText(),
                txtEndereco.getText()
        );
        c.setTemVeiculoLocado(modelo.getCliente(row).isTemVeiculoLocado()); // manter info
        modelo.atualizar(row, c);
        limparCampos();
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
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtSobrenome.setText("");
        txtRg.setText("");
        txtCpf.setText("");
        txtEndereco.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaCliente::new);
    }
}
