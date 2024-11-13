package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminView extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable perguntasTable;
    private JTextField respostaField;
    private JButton responderButton;
    private JButton deletarButton;
    private JButton gerenciarUsuariosButton;
    private JButton abrirChatButton; // Botão para abrir o Chat
    private DefaultTableModel tableModel;

    public AdminView(String emailAdmin) {
        setTitle("Área Administrativa - Gerenciar Perguntas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Pergunta", "Usuário", "Resposta"};
        tableModel = new DefaultTableModel(columnNames, 0);
        perguntasTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(perguntasTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new BorderLayout());
        respostaField = new JTextField();
        responderButton = new JButton("Responder");
        deletarButton = new JButton("Deletar");
        gerenciarUsuariosButton = new JButton("Gerenciar Usuários");

        answerPanel.add(new JLabel("Resposta: "), BorderLayout.WEST);
        answerPanel.add(respostaField, BorderLayout.CENTER);
        answerPanel.add(responderButton, BorderLayout.EAST);
        answerPanel.add(deletarButton, BorderLayout.SOUTH);

        panel.add(answerPanel, BorderLayout.SOUTH);

        abrirChatButton = new JButton("Abrir Chat");

        

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(abrirChatButton);

        if ("admin@teste.com".equals(emailAdmin)) {
            buttonPanel.add(gerenciarUsuariosButton);
        }

        panel.add(buttonPanel, BorderLayout.NORTH);
        add(panel);
    }

    // Outros métodos da AdminView...

    public JButton getChatButton() {
        return abrirChatButton;
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public int getPerguntaSelecionada() {
        int selectedRow = perguntasTable.getSelectedRow();
        if (selectedRow != -1) {
            return (int) tableModel.getValueAt(selectedRow, 0);
        }
        return -1;
    }

    public String getResposta() {
        return respostaField.getText();
    }

    public void limparResposta() {
        respostaField.setText("");
    }

    public void limparTabela() {
        tableModel.setRowCount(0);
    }

    public void adicionarPergunta(int id, String pergunta, String usuario, String resposta) {
        tableModel.addRow(new Object[]{id, pergunta, usuario, resposta});
    }

    public JButton getResponderButton() {
        return responderButton;
    }

    public JButton getDeletarButton() {
        return deletarButton;
    }

    public JButton getGerenciarUsuariosButton() {
        return gerenciarUsuariosButton;
    }
}
