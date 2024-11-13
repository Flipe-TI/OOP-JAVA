package view;  

import controller.LoginController;
import model.Database;

public class Main {

    public static void main(String[] args) {
        // Inicializar o banco de dados 
        try {
            Database.initializeDatabase();
        } catch (Exception e) {
            System.out.println("Erro ao inicializar o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }

        // iniciando a tela de login jutamente com o controller
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
    }
}
