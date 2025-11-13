package controle;

import modelo.*;
import dao.VeiculoDAO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VeiculoController {
    private final VeiculoDAO veiculoDAO = new VeiculoDAO();

    public List<Veiculo> listarTodos() {
        return veiculoDAO.listarTodos();
    }

    public List<Veiculo> listarDisponiveis() {
        List<Veiculo> todos = veiculoDAO.listarTodos();
        List<Veiculo> disponiveis = new ArrayList<>();
        for (Veiculo v : todos) {
            if (v.getEstado() == Estado.DISPONIVEL || v.getEstado() == Estado.NOVO) {
                disponiveis.add(v);
            }
        }
        return disponiveis;
    }

    public List<Veiculo> listarLocados() {
        List<Veiculo> todos = veiculoDAO.listarTodos();
        List<Veiculo> locados = new ArrayList<>();
        for (Veiculo v : todos) {
            if (v.getEstado() == Estado.LOCADO) {
                locados.add(v);
            }
        }
        return locados;
    }

    public Veiculo inserirAutomovel(Marca marca, Categoria categoria, double valorCompra, String placa, int ano, ModeloAutomovel modelo) {
        Veiculo v = new Automovel(marca, Estado.NOVO, categoria, valorCompra, placa, ano, modelo);
        return veiculoDAO.inserir(v);
    }

    public Veiculo inserirMotocicleta(Marca marca, Categoria categoria, double valorCompra, String placa, int ano, ModeloMotocicleta modelo) {
        Veiculo v = new Motocicleta(marca, Estado.NOVO, categoria, valorCompra, placa, ano, modelo);
        return veiculoDAO.inserir(v);
    }

    public Veiculo inserirVan(Marca marca, Categoria categoria, double valorCompra, String placa, int ano, ModeloVan modelo) {
        Veiculo v = new Van(marca, Estado.NOVO, categoria, valorCompra, placa, ano, modelo);
        return veiculoDAO.inserir(v);
    }

    public boolean locar(int veiculoId, int clienteId, int dias, Calendar data) {
        Veiculo v = veiculoDAO.buscarPorId(veiculoId);
        Cliente c = new controle.ClienteController().buscarPorId(clienteId);
        if (v != null && c != null && (v.getEstado() == Estado.DISPONIVEL || v.getEstado() == Estado.NOVO)) {
            v.locar(dias, data, c);
            c.setTemVeiculoLocado(true);
            return veiculoDAO.atualizar(v);
        }
        return false;
    }

    public boolean devolver(int veiculoId) {
        Veiculo v = veiculoDAO.buscarPorId(veiculoId);
        if (v != null && v.getEstado() == Estado.LOCADO && v.getLocacao() != null) {
            Cliente c = v.getLocacao().getCliente();
            v.devolver();
            if (c != null) {
                c.setTemVeiculoLocado(false);
            }
            return veiculoDAO.atualizar(v);
        }
        return false;
    }

    public boolean vender(int veiculoId) {
        Veiculo v = veiculoDAO.buscarPorId(veiculoId);
        if (v != null && (v.getEstado() == Estado.DISPONIVEL || v.getEstado() == Estado.NOVO)) {
            v.vender();
            return veiculoDAO.atualizar(v);
        }
        return false;
    }

    public Veiculo buscarPorId(int id) {
        return veiculoDAO.buscarPorId(id);
    }

    public List<Veiculo> filtrarDisponiveis(String tipo, Marca marca, Categoria categoria) {
        List<Veiculo> disponiveis = listarDisponiveis();
        List<Veiculo> filtrados = new ArrayList<>();
        for (Veiculo v : disponiveis) {
            boolean tipoOK = tipo == null || tipo.equals("--Selecione--") || v.getClass().getSimpleName().equals(tipo);
            boolean marcaOK = marca == null || v.getMarca() == marca;
            boolean categoriaOK = categoria == null || v.getCategoria() == categoria;
            if (tipoOK && marcaOK && categoriaOK) {
                filtrados.add(v);
            }
        }
        return filtrados;
    }
}
