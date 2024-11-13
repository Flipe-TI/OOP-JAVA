package controller;

import model.ChatModel;
import view.ChatView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatController {
    private ChatModel model;
    private ChatView view;

    public ChatController(ChatView view) {
        this.model = new ChatModel();
        this.view = view;

        // Configura o botão "Enviar" para enviar a mensagem quando clicado
        this.view.getSendButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processMessage();
            }
        });

        // Configura o campo de entrada para enviar a mensagem quando "Enter" é pressionado
        this.view.getMessageField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processMessage();
            }
        });
    }

    // Inicia a interface de chat
    public void startChat() {
        view.displayChatInterface();
    }

    // Processa a mensagem do usuário
    private void processMessage() {
        String userMessage = view.getMessageField().getText();
        if (!userMessage.trim().isEmpty()) {
            view.displayUserMessage(userMessage);
            view.getMessageField().setText(""); // Limpa o campo de entrada

            // Envia a mensagem para o modelo e obtém a resposta da API
            model.enviarPerguntasParaAPI(userMessage, new ChatModel.ModelCallback() {
                @Override
                public void onResponse(String response) {
                    // Exibe a resposta da API na View
                    view.displayResponse(response);
                }

                @Override
                public void onError(String error) {
                    // Exibe o erro na View
                    view.displayError(error);
                }
            });
        }
    }
}
