/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author maria
 */
public class Van extends Veiculo {
    private ModeloVan modelo;

    public Van(Marca marca, Estado estado, Categoria categoria, double valorDeCompra, String placa, int ano, ModeloVan modelo) {
        super(marca, estado, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    public ModeloVan getModelo() {
        return modelo;
    }

    @Override
    public double getValorDiariaLocacao() {
        return switch (this.getCategoria()) {
            case POPULAR -> 200.0;
            case INTERMEDIARIO -> 400.0;
            case LUXO -> 600.0;
            default -> 0.0;
        };
    }
}

