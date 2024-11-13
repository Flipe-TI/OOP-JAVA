package controller;

import dao.PerguntaDAO;
import model.Usuario;
import view.ChatView;
import view.UserView;

public class UserController {
    private UserView userView;
    private Usuario usuario;
    private PerguntaDAO perguntaDAO;

    public UserController(UserView userView, Usuario usuario) {
        this.userView = userView;
        this.usuario = usuario;
        this.perguntaDAO = new PerguntaDAO();

        // Carregar perguntas
        carregarPerguntas();

        // Ação do botão "Fazer Pergunta"
        this.userView.getPerguntarButton().addActionListener(e -> {
            String perguntaTexto = userView.getPergunta();
            if (!perguntaTexto.isEmpty()) {
                boolean sucesso = perguntaDAO.adicionarPergunta(usuario.getId(), perguntaTexto);
                if (sucesso) {
                    userView.limparPergunta();
                    userView.displayMessage("Pergunta enviada com sucesso!");
                    carregarPerguntas();
                } else {
                    userView.displayMessage("Erro ao registrar a pergunta.");
                }
            } else {
                userView.displayMessage("A pergunta não pode ser vazia.");
            }
        });
        
        this.userView.getChatButton().addActionListener(e -> {
        	ChatView chatView = new ChatView();  // Inicializar ChatView
            new ChatController(chatView);
            
            chatView.setVisible(true);  // Exibir a janela de chat
        });
    }

    private void carregarPerguntas() {
        userView.limparTabela();
        perguntaDAO.listarPerguntasPorUsuario(usuario.getId()).forEach(p -> {
            userView.adicionarPergunta(p.getId(), p.getPergunta(), p.getResposta(), p.getDataCriacao(), p.getDataResposta());
        });
    }
}
