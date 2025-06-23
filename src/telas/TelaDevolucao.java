/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package telas;

import modelo.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class TelaDevolucao extends JFrame {

    private JTable tabelaVeiculosLocados;
    private List<Veiculo> listaVeiculos;
    private List<Veiculo> veiculosLocados = new ArrayList<>();

    private final Color fundo = new Color(64, 64, 64);
    private final Color laranja = new Color(255, 102, 0);
    private final Color textoClaro = Color.WHITE;
    private final Font fontePadrao = new Font("Arial", Font.PLAIN, 16);

    public TelaDevolucao(List<Veiculo> veiculos) {
        this.listaVeiculos = veiculos;

        setTitle("Devolução de Veículos - VeloCuritiba");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBackground(fundo);

        // Título
        JLabel lblLogo = new JLabel("VELOCURITIBA", SwingConstants.CENTER);
        lblLogo.setForeground(laranja);
        lblLogo.setFont(new Font("Impact", Font.BOLD, 45));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(Box.createVerticalStrut(30));
        painelPrincipal.add(lblLogo);

        // Slogan
        JLabel lblSlogan = new JLabel("Seu caminho, nossa direção", SwingConstants.CENTER);
        lblSlogan.setForeground(Color.LIGHT_GRAY);
        lblSlogan.setFont(new Font("SansSerif", Font.ITALIC, 20));
        lblSlogan.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(lblSlogan);
        painelPrincipal.add(Box.createVerticalStrut(30));

        // Tabela com veículos locados
        tabelaVeiculosLocados = new JTable(new VeiculoLocadoTableModel(veiculosLocados));
        JScrollPane scroll = new JScrollPane(tabelaVeiculosLocados);
        scroll.setPreferredSize(new Dimension(900, 300));
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(Box.createVerticalStrut(20));
        painelPrincipal.add(scroll);
        painelPrincipal.add(Box.createVerticalStrut(20));

        // Botão devolver
        JButton btnDevolver = criarBotaoEstilo("Devolver Veículo");
        btnDevolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDevolver.addActionListener(e -> devolverVeiculo());
        painelPrincipal.add(btnDevolver);
        painelPrincipal.add(Box.createVerticalStrut(30));

        add(painelPrincipal, BorderLayout.CENTER);
        filtrarVeiculosLocados();
        setVisible(true);
    }

    private JButton criarBotaoEstilo(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(laranja);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 20));
        botao.setFocusPainted(false);
        return botao;
    }

    private void filtrarVeiculosLocados() {
        veiculosLocados.clear();
        for (Veiculo v : listaVeiculos) {
            if (v.getEstado() == Estado.LOCADO) {
                veiculosLocados.add(v);
            }
        }
        VeiculoLocadoTableModel model = (VeiculoLocadoTableModel) tabelaVeiculosLocados.getModel();
        model.setVeiculos(new ArrayList<>(veiculosLocados));
    }

    private void devolverVeiculo() {
        int linha = tabelaVeiculosLocados.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para devolver.");
            return;
        }

        Veiculo v = veiculosLocados.get(linha);
        v.devolver();
        JOptionPane.showMessageDialog(this, "Veículo devolvido com sucesso!");
        filtrarVeiculosLocados();
    }

    class VeiculoLocadoTableModel extends AbstractTableModel {
        private final String[] colunas = {"Cliente", "Placa", "Marca", "Modelo", "Ano", "Data Locação", "Diária", "Dias Locados", "Valor Total"};
        private List<Veiculo> veiculos;

        public VeiculoLocadoTableModel(List<Veiculo> veiculos) {
            this.veiculos = veiculos;
        }

        public void setVeiculos(List<Veiculo> veiculos) {
            this.veiculos = veiculos;
            fireTableDataChanged();
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
            Locacao loc = v.getLocacao();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            return switch (col) {
                case 0 -> loc != null ? loc.getCliente().getNome() + " " + loc.getCliente().getSobrenome() : "N/A";
                case 1 -> v.getPlaca();
                case 2 -> v.getMarca();
                case 3 -> (v instanceof Automovel a) ? a.getModelo() :
                          (v instanceof Motocicleta m) ? m.getModelo() :
                          (v instanceof Van va) ? va.getModelo() : "N/A";
                case 4 -> v.getAno();
                case 5 -> loc != null ? sdf.format(loc.getData().getTime()) : "N/A";
                case 6 -> String.format("R$ %.2f", v.getValorDiariaLocacao());
                case 7 -> loc != null ? loc.getDias() : 0;
                case 8 -> loc != null ? String.format("R$ %.2f", loc.getValor()) : "R$ 0,00";
                default -> "";
            };
        }
    }
}