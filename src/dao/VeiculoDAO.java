package dao;

import modelo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {
    private final LocacaoDAO locacaoDAO = new LocacaoDAO();

    public List<Veiculo> listarTodos() {
        List<Veiculo> lista = new ArrayList<>();
        String sql = "SELECT id, tipo, marca, categoria, estado, valorCompra, placa, ano, modelo FROM veiculo";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Veiculo v = criarVeiculoAPartirResultSet(rs);
                lista.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Veiculo buscarPorId(int id) {
        String sql = "SELECT id, tipo, marca, categoria, estado, valorCompra, placa, ano, modelo FROM veiculo WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return criarVeiculoAPartirResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Veiculo inserir(Veiculo v) {
        String sql = "INSERT INTO veiculo(tipo, marca, categoria, estado, valorCompra, placa, ano, modelo) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, v.getClass().getSimpleName());
            ps.setString(2, v.getMarca().name());
            ps.setString(3, v.getCategoria().name());
            ps.setString(4, v.getEstado().name());
            ps.setDouble(5, v.getValorDeCompra());
            ps.setString(6, v.getPlaca());
            ps.setInt(7, v.getAno());
            // modelo
            String modelo = null;
            if (v instanceof Automovel) modelo = ((Automovel) v).getModelo().name();
            else if (v instanceof Motocicleta) modelo = ((Motocicleta) v).getModelo().name();
            else if (v instanceof Van) modelo = ((Van) v).getModelo().name();
            ps.setString(8, modelo);

            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("Inserção de veículo falhou, nenhuma linha afetada.");

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    v.setId(keys.getInt(1));
                }
            }
            // se já está LOCADO, persistir locacao
            if (v.getEstado() == Estado.LOCADO && v.getLocacao() != null) {
                locacaoDAO.inserir(v.getLocacao(), v.getId(), v.getLocacao().getCliente().getId());
            }
            return v;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean atualizar(Veiculo v) {
        String sql = "UPDATE veiculo SET tipo=?, marca=?, categoria=?, estado=?, valorCompra=?, placa=?, ano=?, modelo=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getClass().getSimpleName());
            ps.setString(2, v.getMarca().name());
            ps.setString(3, v.getCategoria().name());
            ps.setString(4, v.getEstado().name());
            ps.setDouble(5, v.getValorDeCompra());
            ps.setString(6, v.getPlaca());
            ps.setInt(7, v.getAno());
            String modelo = null;
            if (v instanceof Automovel) modelo = ((Automovel) v).getModelo().name();
            else if (v instanceof Motocicleta) modelo = ((Motocicleta) v).getModelo().name();
            else if (v instanceof Van) modelo = ((Van) v).getModelo().name();
            ps.setString(8, modelo);
            ps.setInt(9, v.getId());

            int updated = ps.executeUpdate();
            if (updated > 0) {
                // tratar locacao: se LOCADO -> inserir/atualizar locacao; se não LOCADO -> remover locacao
                if (v.getEstado() == Estado.LOCADO && v.getLocacao() != null) {
                    // verifica se já existe locacao
                    Locacao existing = locacaoDAO.buscarPorVeiculoId(v.getId());
                    if (existing == null) {
                        locacaoDAO.inserir(v.getLocacao(), v.getId(), v.getLocacao().getCliente().getId());
                    } else {
                        // atualizar não implementado; remover+inserir pra simplicidade
                        locacaoDAO.excluirPorVeiculoId(v.getId());
                        locacaoDAO.inserir(v.getLocacao(), v.getId(), v.getLocacao().getCliente().getId());
                    }
                } else {
                    locacaoDAO.excluirPorVeiculoId(v.getId());
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        // remover locacao antes
        locacaoDAO.excluirPorVeiculoId(id);
        String sql = "DELETE FROM veiculo WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Veiculo criarVeiculoAPartirResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String tipo = rs.getString("tipo");
        Marca marca = Marca.valueOf(rs.getString("marca"));
        Categoria categoria = Categoria.valueOf(rs.getString("categoria"));
        Estado estado = Estado.valueOf(rs.getString("estado"));
        double valorCompra = rs.getDouble("valorCompra");
        String placa = rs.getString("placa");
        int ano = rs.getInt("ano");
        String modeloStr = rs.getString("modelo");

        Veiculo v = null;
        switch (tipo) {
            case "Automovel" -> v = new Automovel(marca, estado, categoria, valorCompra, placa, ano, ModeloAutomovel.valueOf(modeloStr));
            case "Motocicleta" -> v = new Motocicleta(marca, estado, categoria, valorCompra, placa, ano, ModeloMotocicleta.valueOf(modeloStr));
            case "Van" -> v = new Van(marca, estado, categoria, valorCompra, placa, ano, ModeloVan.valueOf(modeloStr));
            default -> v = null;
        }
        if (v != null) {
            v.setId(id);
            // se LOCADO, carregar locação
            if (estado == Estado.LOCADO) {
                Locacao loc = locacaoDAO.buscarPorVeiculoId(id);
                v.setLocacao(loc);
            }
        }
        return v;
    }
}
