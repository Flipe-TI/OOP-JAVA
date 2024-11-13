package controller;

import dao.UsuarioDAO;
import model.Usuario;
import view.AdminUserView;
import view.UserFormView;

import java.util.List;

public class AdminUserController {
    private AdminUserView adminUserView;
    private UsuarioDAO usuarioDAO;

    public AdminUserController(AdminUserView adminUserView) {
        this.adminUserView = adminUserView;
        this.usuarioDAO = new UsuarioDAO();

        // Carregar todos os usuários quando a view for aberta
        carregarUsuarios();

        // Ação para adicionar um usuário
        this.adminUserView.getAdicionarButton().addActionListener(e -> {
            UserFormView userFormView = new UserFormView("Adicionar Usuário");
            new UserFormController(userFormView, null, this::carregarUsuarios);  // Recarregar usuários após adicionar
            userFormView.setVisible(true);
        });

        // Ação para editar um usuário
        this.adminUserView.getEditarButton().addActionListener(e -> {
            int id = adminUserView.getUsuarioSelecionado();
            if (id >= 0) {
                // Buscar o usuário no banco de dados
                Usuario usuario = usuarioDAO.buscarUsuarioPorId(id);
                if (usuario != null) {
                    UserFormView userFormView = new UserFormView("Editar Usuário");
                    new UserFormController(userFormView, usuario, this::carregarUsuarios);  // Recarregar usuários após editar
                    userFormView.setVisible(true);
                }
            } else {
                adminUserView.displayMessage("Selecione um usuário para editar.");
            }
        });

        // Ação para deletar um usuário
        this.adminUserView.getDeletarButton().addActionListener(e -> {
            int id = adminUserView.getUsuarioSelecionado();

            if (id >= 0) {
                boolean sucesso = usuarioDAO.deletarUsuario(id);
                if (sucesso) {
                    adminUserView.displayMessage("Usuário deletado com sucesso!");
                    carregarUsuarios();  // Recarregar a lista após deletar
                } else {
                    adminUserView.displayMessage("Erro ao deletar o usuário.");
                }
            } else {
                adminUserView.displayMessage("Selecione um usuário para deletar.");
            }
        });
    }

    // Método para carregar os usuários na tabela
    private void carregarUsuarios() {
        adminUserView.limparTabela();
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();

        for (Usuario usuario : usuarios) {
            adminUserView.adicionarUsuario(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.isAdmin());
        }
    }
}
