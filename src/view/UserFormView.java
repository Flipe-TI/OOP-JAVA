package view;

import javax.swing.*;
import java.awt.*;

public class UserFormView extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nomeField, emailField, senhaField;
    private JCheckBox adminCheckBox;  // Checkbox para indicar se é admin
    private JButton salvarButton, cancelarButton;

    public UserFormView(String titulo) {
        setTitle(titulo);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Para fechar somente esta janela
        setLocationRelativeTo(null);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(5, 2));  // Ajustar o GridLayout para 5 linhas e 2 colunas

        formPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        formPanel.add(nomeField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Senha:"));
        senhaField = new JTextField();
        formPanel.add(senhaField);

        formPanel.add(new JLabel("Administrador:"));
        adminCheckBox = new JCheckBox();  // Checkbox para selecionar se o usuário é administrador
        formPanel.add(adminCheckBox);

        salvarButton = new JButton("Salvar");
        cancelarButton = new JButton("Cancelar");

        formPanel.add(salvarButton);
        formPanel.add(cancelarButton);

        add(formPanel, BorderLayout.CENTER);
    }

    // Getters para os campos
    public String getNome() {
        return nomeField.getText();
    }

    public void setNome(String nome) {
        nomeField.setText(nome);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public void setEmail(String email) {
        emailField.setText(email);
    }

    public String getSenha() {
        return senhaField.getText();
    }

    public void setSenha(String senha) {
        senhaField.setText(senha);
    }

    // Getter e setter para o checkbox de administrador
    public boolean isAdmin() {
        return adminCheckBox.isSelected();
    }

    public void setAdmin(boolean isAdmin) {
        adminCheckBox.setSelected(isAdmin);
    }

    // Getters para os botões
    public JButton getSalvarButton() {
        return salvarButton;
    }

    public JButton getCancelarButton() {
        return cancelarButton;
    }

    // Exibir mensagem de erro ou sucesso
    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
