package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatView extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField messageField;
    private JTextArea chatArea;
    private JButton sendButton;

    public ChatView() {
        
        setTitle("Chat");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        add(chatScrollPane, BorderLayout.CENTER);

        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        messageField = new JTextField();
        sendButton = new JButton("Enviar");
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);

        
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Envio de mensagem ao pressionar "Enter"
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    // Método para obter a mensagem do usuário
    private void sendMessage() {
        String userMessage = messageField.getText();
        if (!userMessage.trim().isEmpty()) {
            displayUserMessage(userMessage);
            messageField.setText(""); 
        }
    }

    // Exibe a mensagem do usuário na área de chat
    public void displayUserMessage(String message) {
        chatArea.append("Você: " + message + "\n");
    }

    // Exibe a resposta da API na área de chat
    public void displayResponse(String response) {
        chatArea.append("Bot: " + response + "\n");
    }

    // Exibe a interface do chat
    public void displayChatInterface() {
        setVisible(true);
    }
    
    // Métodos para obter o campo de entrada de mensagem e o botão de enviar
    public JTextField getMessageField() {
        return messageField;
    }
    public void displayError(String error) {
        // Exibe o erro em uma janela de diálogo
        JOptionPane.showMessageDialog(this, error, "Erro", JOptionPane.ERROR_MESSAGE);
    }
    public JButton getSendButton() {
        return sendButton;
    }

}
