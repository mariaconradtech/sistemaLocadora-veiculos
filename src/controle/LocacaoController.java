package controle;

import modelo.Veiculo;
import dao.LocacaoDAO;
import java.util.Calendar;
import java.util.List;

public class LocacaoController {
    private final VeiculoController veiculoController = new VeiculoController();
    private final LocacaoDAO locacaoDAO = new LocacaoDAO();

    public boolean locar(int veiculoId, int clienteId, int dias, Calendar data) {
        return veiculoController.locar(veiculoId, clienteId, dias, data);
    }

    public boolean devolver(int veiculoId) {
        return veiculoController.devolver(veiculoId);
    }

    public boolean vender(int veiculoId) {
        return veiculoController.vender(veiculoId);
    }

    public List<Veiculo> listarVeiculosLocados() {
        return veiculoController.listarLocados();
    }

    public List<Veiculo> listarVeiculosDisponiveis() {
        return veiculoController.listarDisponiveis();
    }

    public List<Veiculo> filtrarVeiculosDisponiveisComFiltros(String tipo, modelo.Marca marca, modelo.Categoria categoria) {
        return veiculoController.filtrarDisponiveis(tipo, marca, categoria);
    }

    public boolean clientePossuiLocacaoAtiva(int clienteId) {
        return locacaoDAO.existePorClienteId(clienteId);
    }
}
