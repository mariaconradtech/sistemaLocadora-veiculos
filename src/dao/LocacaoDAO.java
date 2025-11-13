package dao;

import modelo.Cliente;
import modelo.Locacao;

import java.sql.*;
import java.util.Calendar;

public class LocacaoDAO {

    public Locacao buscarPorVeiculoId(int veiculoId) {
        String sql = "SELECT id, veiculo_id, cliente_id, dias, valor, data FROM locacao WHERE veiculo_id=? LIMIT 1";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, veiculoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int dias = rs.getInt("dias");
                    double valor = rs.getDouble("valor");
                    long dataMillis = rs.getLong("data");
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(dataMillis);
                    Cliente c = new ClienteDAO().buscarPorId(rs.getInt("cliente_id"));
                    return new Locacao(dias, valor, cal, c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean inserir(Locacao loc, int veiculoId, int clienteId) {
        String sql = "INSERT INTO locacao(veiculo_id, cliente_id, dias, valor, data) VALUES(?,?,?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, veiculoId);
            ps.setInt(2, clienteId);
            ps.setInt(3, loc.getDias());
            ps.setDouble(4, loc.getValor());
            ps.setLong(5, loc.getData().getTimeInMillis());
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluirPorVeiculoId(int veiculoId) {
        String sql = "DELETE FROM locacao WHERE veiculo_id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, veiculoId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existePorClienteId(int clienteId) {
        String sql = "SELECT 1 FROM locacao WHERE cliente_id=? LIMIT 1";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, clienteId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
