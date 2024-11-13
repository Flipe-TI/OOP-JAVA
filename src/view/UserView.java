package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserView extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable perguntasTable;
    private JTextField perguntaField;
    private JButton perguntarButton;
    private JButton abrirChatButton;  // Novo botão para abrir o Chat
    private DefaultTableModel tableModel;

    // Construtor que aceita o nome do usuário
    public UserView(String nomeUsuario) {
        setTitle("Área do Usuário - " + nomeUsuario);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel Principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Tabela para exibir perguntas e respostas
        String[] columnNames = {"ID", "Pergunta", "Resposta", "Data Criação", "Data Resposta"};
        tableModel = new DefaultTableModel(columnNames, 0);
        perguntasTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(perguntasTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel inferior para adicionar novas perguntas
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BorderLayout());
        perguntaField = new JTextField();
        perguntarButton = new JButton("Fazer Pergunta");

        questionPanel.add(new JLabel("Pergunta: "), BorderLayout.WEST);
        questionPanel.add(perguntaField, BorderLayout.CENTER);
        questionPanel.add(perguntarButton, BorderLayout.EAST);

        panel.add(questionPanel, BorderLayout.SOUTH);

        // Novo botão para abrir o chat
        abrirChatButton = new JButton("Abrir Chat");

        // Ação do botão para abrir o chat
        abrirChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Quando clicado, abre a janela de chat
                new ChatView(); // Abre a nova tela de chat
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(abrirChatButton);
        
        // Adicionando o botão "Abrir Chat" ao painel
        panel.add(buttonPanel, BorderLayout.NORTH); // Posiciona o botão no topo

        // Adicionando o painel principal à janela
        add(panel);
    }
    
    public JButton getChatButton() {
        return abrirChatButton;
    }

    // Obter a pergunta digitada pelo usuário
    public String getPergunta() {
        return perguntaField.getText();
    }

    // Limpar o campo de pergunta
    public void limparPergunta() {
        perguntaField.setText("");
    }

    // Limpar a tabela de perguntas (para recarregar os dados)
    public void limparTabela() {
        DefaultTableModel model = (DefaultTableModel) perguntasTable.getModel();
        model.setRowCount(0);
    }

    // Adicionar uma linha de pergunta na tabela
    public void adicionarPergunta(int id, String pergunta, String resposta, String dataCriacao, String dataResposta) {
        DefaultTableModel model = (DefaultTableModel) perguntasTable.getModel();
        model.addRow(new Object[]{id, pergunta, resposta, dataCriacao, dataResposta});
    }

    // Fornece o botão para que o controlador adicione um ActionListener
    public JButton getPerguntarButton() {
        return perguntarButton;
    }

    // Exibir mensagens para o usuário
    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
