package controller;

import dao.UsuarioDAO;
import model.Usuario;
import view.UserFormView;

public class UserFormController {
    private UserFormView userFormView;
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;  // Usuário existente (para edição) ou null (para criação)
    private Runnable onSuccessCallback;  // Callback para notificar sucesso

    public UserFormController(UserFormView userFormView, Usuario usuario, Runnable onSuccessCallback) {
        this.userFormView = userFormView;
        this.usuarioDAO = new UsuarioDAO();
        this.usuario = usuario;  // Se for null, estamos criando um novo usuário
        this.onSuccessCallback = onSuccessCallback;  // Callback para ser chamado após sucesso

        // Se o usuário não for null, preenche os campos com os dados do usuário
        if (usuario != null) {
            userFormView.setNome(usuario.getNome());
            userFormView.setEmail(usuario.getEmail());
            userFormView.setSenha(usuario.getSenha());
            userFormView.setAdmin(usuario.isAdmin());  // Preencher o checkbox de admin
        }

        // Ação para salvar o usuário (criar ou atualizar)
        this.userFormView.getSalvarButton().addActionListener(e -> salvarUsuario());

        // Ação para cancelar e fechar a janela
        this.userFormView.getCancelarButton().addActionListener(e -> userFormView.dispose());
    }

    // Método para salvar ou atualizar o usuário
    private void salvarUsuario() {
        String nome = userFormView.getNome();
        String email = userFormView.getEmail();
        String senha = userFormView.getSenha();
        boolean isAdmin = userFormView.isAdmin();  // Obter o valor do checkbox de admin

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            userFormView.displayMessage("Por favor, preencha todos os campos.");
            return;
        }

        if (usuario == null) {
            // Criar um novo usuário
            boolean sucesso = usuarioDAO.adicionarUsuario(nome, email, senha, isAdmin);
            if (sucesso) {
                userFormView.displayMessage("Usuário adicionado com sucesso!");
                userFormView.dispose();  // Fechar a janela após a criação
                if (onSuccessCallback != null) {
                    onSuccessCallback.run();  // Notificar que o sucesso ocorreu
                }
            } else {
                userFormView.displayMessage("Erro ao adicionar o usuário. O email pode já estar em uso.");
            }
        } else {
            // Atualizar usuário existente
            boolean sucesso = usuarioDAO.atualizarUsuario(usuario.getId(), nome, email, senha, isAdmin);
            if (sucesso) {
                userFormView.displayMessage("Usuário atualizado com sucesso!");
                userFormView.dispose();  // Fechar a janela após a atualização
                if (onSuccessCallback != null) {
                    onSuccessCallback.run();  // Notificar que o sucesso ocorreu
                }
            } else {
                userFormView.displayMessage("Erro ao atualizar o usuário. O email pode já estar em uso.");
            }
        }
    }
}
