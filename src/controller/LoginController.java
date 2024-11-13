package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Database;
import model.Usuario;
import model.UsuarioComum;  // Importar a subclasse UsuarioComum
import model.Administrador;  // Importar a subclasse Administrador
import view.AdminView;
import view.LoginView;
import view.UserView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        this.loginView.addLoginListener(new LoginListener());
    }

    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String email = loginView.getEmail();
            String senha = loginView.getSenha();

            // Autenticar o usuário
            Usuario usuario = authenticate(email, senha);

            if (usuario != null) {
                // Verificar se o usuário é administrador
                if (usuario.isAdmin()) {
                    AdminView adminView = new AdminView(email);
                    new AdminController(adminView);
                    adminView.setVisible(true);  // Abrir a interface de administrador
                } else {
                    UserView userView = new UserView(usuario.getNome());
                    new UserController(userView, usuario);
                    userView.setVisible(true);  // Abrir a interface de usuário comum
                }
                loginView.dispose();  // Fechar a tela de login
            } else {
                // Exibir mensagem de erro se as credenciais forem inválidas
                loginView.displayErrorMessage("Credenciais inválidas! Tente novamente.");
            }
        }

        // Método que autentica o usuário com base no email e senha
        private Usuario authenticate(String email, String senha) {
            String sql = "SELECT * FROM Usuarios WHERE email = ? AND senha = ?";

            try (Connection conn = Database.connect(); 
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // Definir os parâmetros da consulta
                pstmt.setString(1, email);
                pstmt.setString(2, senha);

                // Executar a consulta
                ResultSet rs = pstmt.executeQuery();

                // Se encontrar um resultado, criar e retornar o objeto Usuario adequado
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    boolean isAdmin = rs.getBoolean("is_admin");

                    // Retorna instância de Administrador ou UsuarioComum com base no valor de isAdmin
                    if (isAdmin) {
                        return new Administrador(id, nome, email, senha);
                    } else {
                        return new UsuarioComum(id, nome, email, senha);
                    }
                }

            } catch (SQLException e) {
                System.out.println("Erro ao autenticar: " + e.getMessage());
            }

            // Retorna null se o usuário não for encontrado ou a senha estiver incorreta
            return null;
        }
    }
}
