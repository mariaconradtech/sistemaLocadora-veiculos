/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author maria
 */
public abstract class Veiculo implements VeiculoI {
    protected Marca marca;
    protected Estado estado;
    protected Locacao locacao;
    protected Categoria categoria;
    protected double valorDeCompra;
    protected String placa;
    protected int ano;

    public Veiculo(Marca marca, Estado estado, Categoria categoria, double valorDeCompra, String placa, int ano) {
        this.marca = marca;
        this.estado = estado;
        this.categoria = categoria;
        this.valorDeCompra = valorDeCompra;
        this.placa = placa;
        this.ano = ano;
        this.locacao = null;
    }

    @Override
    public void locar(int dias, java.util.Calendar data, Cliente cliente) {
        if (estado == Estado.DISPONIVEL) {
            double valorLocacao = dias * getValorDiariaLocacao();
            this.locacao = new Locacao(dias, valorLocacao, data, cliente);
            this.estado = Estado.LOCADO;
        }
    }

    @Override
    public void vender() {
        this.estado = Estado.VENDIDO;
        this.locacao = null;
    }

    @Override
    public void devolver() {
        this.locacao = null;
        this.estado = Estado.DISPONIVEL;
    }

    @Override
    public Estado getEstado() { return estado; }

    @Override
    public Marca getMarca() { return marca; }

    @Override
    public Categoria getCategoria() { return categoria; }

    @Override
    public Locacao getLocacao() { return locacao; }

    @Override
    public String getPlaca() { return placa; }

    @Override
    public int getAno() { return ano; }

    @Override
    public double getValorParaVenda() {
        int idade = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) - ano;
        double depreciacao = idade * 0.15 * valorDeCompra;
        double valorVenda = valorDeCompra - depreciacao;
        if (valorVenda < 0.1 * valorDeCompra) {
            valorVenda = 0.1 * valorDeCompra;
        }
        return valorVenda;
    }
    
    @Override
    public abstract double getValorDiariaLocacao();
}
