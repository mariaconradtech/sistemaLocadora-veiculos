/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Calendar;
/**
 *
 * @author maria
 */
public class Locacao {
    private int dias;
    private double valor;
    private Calendar data;
    private Cliente cliente;

    public Locacao(int dias, double valor, Calendar data, Cliente cliente) {
        this.dias = dias;
        this.valor = valor;
        this.data = data;
        this.cliente = cliente;
    }

    public double getValor() { return valor; }
    public Calendar getData() { return data; }
    public Cliente getCliente() { return cliente; }
    public int getDias() { return dias; }
}
