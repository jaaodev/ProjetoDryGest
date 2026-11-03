package br.com.drygest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgreSQL {
    public static Connection conectar() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/drygest";
        String usuario = System.getenv("DRYGEST_DB_LOGIN");
        String senha = System.getenv("DRYGEST_DB_PASSWORD");
        return DriverManager.getConnection(url, usuario, senha);
    }
}
