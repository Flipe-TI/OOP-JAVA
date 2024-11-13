package dao;

import model.Administrador;
import model.Database;
import model.Usuario;
import model.UsuarioComum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    // SQL Queries
    private static final String CONSULTAR_USUARIO = "SELECT * FROM Usuarios WHERE email = ? AND senha = ?";
    private static final String ADICIONAR_USUARIO = "INSERT INTO Usuarios (nome, email, senha, is_admin) VALUES (?, ?, ?, ?)";
    private static final String ATUALIZAR_USUARIO = "UPDATE Usuarios SET nome = ?, email = ?, senha = ?, is_admin = ? WHERE id = ?";
    private static final String DELETAR_USUARIO = "DELETE FROM Usuarios WHERE id = ?";
    private static final String LISTAR_USUARIOS = "SELECT * FROM Usuarios";
    private static final String BUSCAR_USUARIO_POR_ID = "SELECT * FROM Usuarios WHERE id = ?";

    // Método para autenticar o usuário
    public Usuario autenticarUsuario(String email, String senha) {
        Connection connection = Database.connect();
        Usuario usuario = null;
        try {
            preparedStatement = connection.prepareStatement(CONSULTAR_USUARIO);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Verificar se o usuário é administrador ou comum
                boolean isAdmin = resultSet.getBoolean("is_admin");

                if (isAdmin) {
                    usuario = new Administrador(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("senha")
                    );
                } else {
                    usuario = new UsuarioComum(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("senha")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao autenticar usuário: " + e.getMessage());
        } finally {
            fecharConexao();
        }
        return usuario;  // Retorna null se o usuário não for encontrado
    }

    
    public Usuario buscarUsuarioPorId(int id) {
        Usuario usuario = null;
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(BUSCAR_USUARIO_POR_ID)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Verificar se o usuário é administrador ou comum
                boolean isAdmin = rs.getBoolean("is_admin");

                if (isAdmin) {
                    usuario = new Administrador(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                    );
                } else {
                    usuario = new UsuarioComum(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário por ID: " + e.getMessage());
        }
        return usuario;  // Retorna null se o usuário não for encontrado
    }

    
 // Adicionar usuário
    public boolean adicionarUsuario(String nome, String email, String senha, Boolean admin) {
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(ADICIONAR_USUARIO)) {

            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            pstmt.setString(3, senha);
            pstmt.setBoolean(4, admin);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar usuário: " + e.getMessage());
            return false;
        }
    }

    // Atualizar usuário
    public boolean atualizarUsuario(int id, String nome, String email, String senha, boolean isAdmin) {
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(ATUALIZAR_USUARIO)) {

            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            pstmt.setString(3, senha);
            pstmt.setInt(5, id);
            pstmt.setBoolean(4, isAdmin);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
            return false;
        }
    }

    // Deletar usuário
    public boolean deletarUsuario(int id) {
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(DELETAR_USUARIO)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar usuário: " + e.getMessage());
            return false;
        }
    }

    // Listar todos os usuários
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(LISTAR_USUARIOS)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");
                boolean isAdmin = rs.getBoolean("is_admin");

                // Verificar se o usuário é administrador ou comum
                Usuario usuario;
                if (isAdmin) {
                    usuario = new Administrador(id, nome, email, senha);
                } else {
                    usuario = new UsuarioComum(id, nome, email, senha);
                }

                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
        return usuarios;
    }

    
    // Método para fechar a conexão
    private void fecharConexao() {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }
    
    
}
