package model;

public class UsuarioComum extends Usuario {

    // Construtor da subclasse
    public UsuarioComum(int id, String nome, String email, String senha) {
        super(id, nome, email, senha);
    }

    // O método isAdmin não precisa ser sobrescrito, pois o usuário comum não é administrador
}
