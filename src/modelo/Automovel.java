/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author maria
 */
public class Automovel extends Veiculo {
    private ModeloAutomovel modelo;
    
    public Automovel(Marca marca, Estado estado, Categoria categoria, double valorDeCompra, String placa, int ano, ModeloAutomovel modelo) {
       super(marca, estado, categoria, valorDeCompra, placa, ano);
       this.modelo = modelo;
    }
    
    public ModeloAutomovel getModelo() {
        return modelo;
    }
    
    @Override
    public double getValorDiariaLocacao() {
        return switch (categoria) {
            case Popular -> 100.0;
            case Intermediário -> 300.0;
            case Luxo -> 450.0;
            default -> 0.0;
        };
    }
}
