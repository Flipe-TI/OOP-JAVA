package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminUserView extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable usuariosTable;
    private JButton adicionarButton, editarButton, deletarButton;
    private DefaultTableModel tableModel;

    public AdminUserView() {
        setTitle("Gerenciar Usuários");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Para fechar somente esta janela
        setLocationRelativeTo(null);

        // Painel Principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Tabela para exibir usuários
        String[] columnNames = {"ID", "Nome", "Email", "Administrador"};
        tableModel = new DefaultTableModel(columnNames, 0);
        usuariosTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(usuariosTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel inferior para adicionar, editar e deletar usuários
        JPanel buttonPanel = new JPanel();
        adicionarButton = new JButton("Adicionar");
        editarButton = new JButton("Editar");
        deletarButton = new JButton("Deletar");

        buttonPanel.add(adicionarButton);
        buttonPanel.add(editarButton);
        buttonPanel.add(deletarButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    // Métodos para acessar os botões
    public JButton getAdicionarButton() {
        return adicionarButton;
    }

    public JButton getEditarButton() {
        return editarButton;
    }

    public JButton getDeletarButton() {
        return deletarButton;
    }

    // Método para adicionar ou atualizar usuários na tabela
    public void adicionarUsuario(int id, String nome, String email, boolean isAdmin) {
        tableModel.addRow(new Object[]{id, nome, email, isAdmin ? "Sim" : "Não"});
    }

    public void limparTabela() {
        tableModel.setRowCount(0);
    }

    public int getUsuarioSelecionado() {
        int selectedRow = usuariosTable.getSelectedRow();
        if (selectedRow >= 0) {
            return (int) tableModel.getValueAt(selectedRow, 0);  // Retornar o ID do usuário selecionado
        }
        return -1;
    }

    public String getNomeSelecionado() {
        int selectedRow = usuariosTable.getSelectedRow();
        return (String) tableModel.getValueAt(selectedRow, 1);  // Retorna o nome do usuário selecionado
    }

    public String getEmailSelecionado() {
        int selectedRow = usuariosTable.getSelectedRow();
        return (String) tableModel.getValueAt(selectedRow, 2);  // Retorna o email do usuário selecionado
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
