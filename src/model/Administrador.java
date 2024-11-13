package model;

public class Administrador extends Usuario {

    // Construtor da subclasse
    public Administrador(int id, String nome, String email, String senha) {
        super(id, nome, email, senha);
    }

    // Sobrescreve o método isAdmin para indicar que é administrador
    @Override
    public boolean isAdmin() {
        return true;
    }
}
