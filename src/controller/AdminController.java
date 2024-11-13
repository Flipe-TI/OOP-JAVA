package controller;

import dao.PerguntaDAO;
import model.Pergunta;
import view.AdminUserView;
import view.AdminView;
import view.ChatView;

import java.util.List;

import javax.swing.JOptionPane;

public class AdminController {
    private AdminView adminView;
    private PerguntaDAO perguntaDAO;

    public AdminController(AdminView adminView) {
        this.adminView = adminView;
        this.perguntaDAO = new PerguntaDAO();

        // Carregar todas as perguntas quando a view for aberta
        carregarPerguntas();
        
        this.adminView.getGerenciarUsuariosButton().addActionListener(e -> {
            AdminUserView adminUserView = new AdminUserView();
            new AdminUserController(adminUserView);
            adminUserView.setVisible(true);  // Exibir a tela de gerenciamento de usuários
        });
        
        // Ação para o botão "Responder"
        this.adminView.getResponderButton().addActionListener(e -> {
            int idPergunta = adminView.getPerguntaSelecionada();
            String resposta = adminView.getResposta();

            if (idPergunta >= 0 && !resposta.isEmpty()) {
                boolean sucesso = perguntaDAO.responderPergunta(idPergunta, resposta);
                if (sucesso) {
                    adminView.limparResposta();
                    adminView.displayMessage("Resposta enviada com sucesso!");
                    carregarPerguntas();
                } else {
                    adminView.displayMessage("Erro ao registrar a resposta. Tente novamente.");
                }
            } else {
                adminView.displayMessage("Selecione uma pergunta e insira uma resposta válida.");
            }
        });

        // Ação para o botão "Deletar"
        this.adminView.getDeletarButton().addActionListener(e -> {
            int idPergunta = adminView.getPerguntaSelecionada();
            if (idPergunta >= 0) {
                int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar a pergunta?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean sucesso = perguntaDAO.deletarPergunta(idPergunta);
                    if (sucesso) {
                        adminView.displayMessage("Pergunta deletada com sucesso!");
                        carregarPerguntas();
                    } else {
                        adminView.displayMessage("Erro ao deletar a pergunta. Tente novamente.");
                    }
                }
            } else {
                adminView.displayMessage("Selecione uma pergunta para deletar.");
            }
        });

        // Ação para o botão "Abrir Chat"
        this.adminView.getChatButton().addActionListener(e -> {
            ChatView chatView = new ChatView();  // Inicializar ChatView
            new ChatController(chatView);
            
            chatView.setVisible(true);  // Exibir a janela de chat
        });
    }

    // Método para carregar todas as perguntas na tabela
    private void carregarPerguntas() {
        adminView.limparTabela();
        List<Pergunta> perguntas = perguntaDAO.listarTodasPerguntas();
        for (Pergunta pergunta : perguntas) {
            adminView.adicionarPergunta(
                pergunta.getId(),
                pergunta.getPergunta(),
                String.valueOf(pergunta.getIdUsuario()),
                pergunta.getResposta() != null ? pergunta.getResposta() : "Sem Resposta"
            );
        }
    }
}
