package controle;

import modelo.Cliente;
import dao.ClienteDAO;
import dao.LocacaoDAO;
import java.util.List;

public class ClienteController {
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final LocacaoDAO locacaoDAO = new LocacaoDAO();

    public List<Cliente> listarTodos() {
        return clienteDAO.listarTodos();
    }

    public Cliente inserir(String nome, String sobrenome, String rg, String cpf, String endereco) {
        Cliente c = new Cliente(nome, sobrenome, rg, cpf, endereco);
        return clienteDAO.inserir(c);
    }

    public boolean atualizar(int id, String nome, String sobrenome, String rg, String cpf, String endereco) {
        Cliente c = new Cliente(id, nome, sobrenome, rg, cpf, endereco);
        return clienteDAO.atualizar(c);
    }

    public boolean excluir(int id) {
        // verifica se cliente tem locações ativas
        if (locacaoDAO.existePorClienteId(id)) {
            return false; // não permite excluir
        }
        return clienteDAO.excluir(id);
    }

    public Cliente buscarPorId(int id) {
        return clienteDAO.buscarPorId(id);
    }

    public void atualizarStatusLocacao(int clienteId) {
        Cliente c = clienteDAO.buscarPorId(clienteId);
        if (c != null) {
            c.setTemVeiculoLocado(locacaoDAO.existePorClienteId(clienteId));
        }
    }
}
