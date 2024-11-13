package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:resources/bdSys.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void initializeDatabase() {
        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios ("
                           + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                           + "nome TEXT NOT NULL,"
                           + "email TEXT UNIQUE NOT NULL,"
                           + "senha TEXT NOT NULL,"
                           + "is_admin BOOLEAN NOT NULL DEFAULT 0"
                           + ");";

        String sqlPerguntas = "CREATE TABLE IF NOT EXISTS Perguntas ("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "id_usuario INTEGER NOT NULL,"
                            + "pergunta TEXT NOT NULL,"
                            + "resposta TEXT,"
                            + "data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,"
                            + "data_resposta DATETIME,"
                            + "FOREIGN KEY (id_usuario) REFERENCES Usuarios(id)"
                            + ");";

        try (Connection conn = connect(); 
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlUsuarios);
            stmt.execute(sqlPerguntas);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
