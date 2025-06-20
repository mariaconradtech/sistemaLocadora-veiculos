/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistemalocadora;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import modelo.Automovel;
import modelo.Categoria;
import modelo.Cliente;
import modelo.Estado;
import modelo.Marca;
import modelo.ModeloAutomovel;
import modelo.ModeloMotocicleta;
import modelo.ModeloVan;
import modelo.Motocicleta;
import modelo.Van;
import modelo.Veiculo;




/**
 *
 * @author maria
 */
public class SistemaLocadora {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Cliente> listaClientes = new ArrayList<>();
        Cliente cliente1 = new Cliente("Maria", "Silva", "123456", "111.222.333-44", "Rua A");
        Cliente cliente2 = new Cliente("João", "Souza", "654321", "555.666.777-88", "Rua B");
        listaClientes.add(cliente1);
        listaClientes.add(cliente2);

       
        List<Veiculo> listaVeiculos = new ArrayList<>();

        Automovel automovel = new Automovel(Marca.Fiat, Estado.DISPONIVEL, Categoria.Popular, 50000.0, "ABC-1234", 2020, ModeloAutomovel.Palio);
        Motocicleta moto = new Motocicleta(Marca.Honda, Estado.DISPONIVEL, Categoria.Intermediário, 20000.0, "DEF-5678", 2021, ModeloMotocicleta.CG_125);
        Van van = new Van(Marca.VW, Estado.DISPONIVEL, Categoria.Luxo, 90000.0, "GHI-9012", 2019, ModeloVan.Kombi);

        listaVeiculos.add(automovel);
        listaVeiculos.add(moto);
        listaVeiculos.add(van);

        System.out.println("\nVeículos disponíveis:");
        for (Veiculo v : listaVeiculos) {
            System.out.println(v.getPlaca() + " - " + v.getEstado() + " - Categoria: " + v.getCategoria() + " - Marca: " + v.getMarca());
        }

        System.out.println("\nLocando o automóvel para Maria...");
        Calendar dataLocacao = Calendar.getInstance();
        automovel.locar(5, dataLocacao, cliente1);
        System.out.println("Estado após locação: " + automovel.getEstado());
        System.out.println("Locado por: " + automovel.getLocacao().getCliente().getNome());
        System.out.println("Valor da locação: R$" + automovel.getLocacao().getValor());

        // Devolvendo o veículo
        System.out.println("\nDevolvendo o veículo...");
        automovel.devolver();
        System.out.println("Estado após devolução: " + automovel.getEstado());

        // Vendendo a Van
        System.out.println("\nVendendo a Van...");
        van.vender();
        System.out.println("Estado da Van após venda: " + van.getEstado());
        System.out.println("Valor para venda: R$" + van.getValorParaVenda());

        // Listando veículos novamente
        System.out.println("\nSituação final dos veículos:");
        for (Veiculo v : listaVeiculos) {
            System.out.println(v.getPlaca() + " - Estado: " + v.getEstado());
        }
    }   
    
}
