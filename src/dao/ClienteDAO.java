package dao;

import modelo.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id, nome, sobrenome, rg, cpf, endereco FROM cliente";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getString("rg"),
                        rs.getString("cpf"),
                        rs.getString("endereco")
                );
                c.setId(rs.getInt("id"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Cliente inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente(nome, sobrenome, rg, cpf, endereco) VALUES(?,?,?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getSobrenome());
            ps.setString(3, cliente.getRg());
            ps.setString(4, cliente.getCpf());
            ps.setString(5, cliente.getEndereco());

            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("Inserção falhou, nenhuma linha afetada.");

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    cliente.setId(keys.getInt(1));
                }
            }
            return cliente;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean atualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome=?, sobrenome=?, rg=?, cpf=?, endereco=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getSobrenome());
            ps.setString(3, cliente.getRg());
            ps.setString(4, cliente.getCpf());
            ps.setString(5, cliente.getEndereco());
            ps.setInt(6, cliente.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM cliente WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cliente buscarPorId(int id) {
        String sql = "SELECT id, nome, sobrenome, rg, cpf, endereco FROM cliente WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente(
                            rs.getString("nome"),
                            rs.getString("sobrenome"),
                            rs.getString("rg"),
                            rs.getString("cpf"),
                            rs.getString("endereco")
                    );
                    c.setId(rs.getInt("id"));
                    return c;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
