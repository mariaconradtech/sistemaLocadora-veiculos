package controle;

import dao.ConnectionFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

public class DBInit {
    public static void main(String[] args) {
        String scriptPath = "create_tables.sql"; // assume working dir is project root
        try (Connection conn = ConnectionFactory.getConnection();
             BufferedReader br = new BufferedReader(new FileReader(scriptPath))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            String[] statements = sb.toString().split(";\n");
            try (Statement st = conn.createStatement()) {
                for (String s : statements) {
                    String sql = s.trim();
                    if (!sql.isEmpty()) {
                        st.execute(sql);
                    }
                }
            }
            System.out.println("Script executado com sucesso (" + scriptPath + ").");
        } catch (Exception e) {
            System.err.println("Erro ao executar script: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
