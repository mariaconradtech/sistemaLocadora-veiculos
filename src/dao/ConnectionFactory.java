package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:sqlite:sistemalocadora.db"; // arquivo DB na pasta de trabalho

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            // Driver não encontrado no classpath; a aplicação pode falhar ao abrir a conexão
            System.err.println("Aviso: driver SQLite não encontrado no classpath: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
