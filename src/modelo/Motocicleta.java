/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author maria
 */
public class Motocicleta extends Veiculo {
    private ModeloMotocicleta modelo;
    
    public Motocicleta(Marca marca, Estado estado, Categoria categoria, double valorDeCompra, String placa, int ano, ModeloMotocicleta modelo) {
        super(marca, estado, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }
    
    public ModeloMotocicleta getModelo() {
        return modelo;
    }
    
    @Override 
    public double getValorDiariaLocacao() {
        switch (categoria) {
            case POPULAR:
                return 70.0;
            case INTERMEDIARIO:
                return 200.0;
            case LUXO:
                return 350.0;
            default:
                return 0.0;
        }
    }

    public enum ModeloMotocicleta {
        CG_125, CBR_500
    }
}
