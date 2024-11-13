package dao;

import model.Database;
import model.Pergunta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PerguntaDAO {
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    // Querys
    private static final String ADICIONAR_PERGUNTA = "INSERT INTO Perguntas (id_usuario, pergunta) VALUES (?, ?)";
    private static final String LISTAR_PERGUNTAS_POR_USUARIO = "SELECT * FROM Perguntas WHERE id_usuario = ?";
    private static final String LISTAR_TODAS_PERGUNTAS = "SELECT * FROM Perguntas";
    private static final String RESPONDER_PERGUNTA = "UPDATE Perguntas SET resposta = ?, data_resposta = CURRENT_TIMESTAMP WHERE id = ?";
    private static final String DELETAR_PERGUNTA = "DELETE FROM Perguntas WHERE id = ?";

    
    // Método para adicionar uma nova pergunta
    public boolean adicionarPergunta(int idUsuario, String perguntaTexto) {
        Connection connection = Database.connect();
        try {
            preparedStatement = connection.prepareStatement(ADICIONAR_PERGUNTA);
            preparedStatement.setInt(1, idUsuario);
            preparedStatement.setString(2, perguntaTexto);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;  // se a quantidade de linhas afetadas for maior que 0 retorna true, confirmando a inserção

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar pergunta: " + e.getMessage());
            return false;
        } finally {
            fecharConexao();
        }
    }

    // listar perguntas, somente perguntas daquele usuario
    public List<Pergunta> listarPerguntasPorUsuario(int idUsuario) {
        List<Pergunta> perguntas = new ArrayList<>();
        Connection connection = Database.connect();
        try {
            preparedStatement = connection.prepareStatement(LISTAR_PERGUNTAS_POR_USUARIO);
            preparedStatement.setInt(1, idUsuario);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                perguntas.add(new Pergunta(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_usuario"),
                        resultSet.getString("pergunta"),
                        resultSet.getString("resposta"),
                        resultSet.getString("data_criacao"),
                        resultSet.getString("data_resposta")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar perguntas por usuário: " + e.getMessage());
        } finally {
            fecharConexao();
        }
        return perguntas;
    }

    // Método para listar todas as perguntas (para administradores)
    public List<Pergunta> listarTodasPerguntas() {
        List<Pergunta> perguntas = new ArrayList<>();
        Connection connection = Database.connect();
        try {
            preparedStatement = connection.prepareStatement(LISTAR_TODAS_PERGUNTAS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                perguntas.add(new Pergunta(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_usuario"),
                        resultSet.getString("pergunta"),
                        resultSet.getString("resposta"),
                        resultSet.getString("data_criacao"),
                        resultSet.getString("data_resposta")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar todas as perguntas: " + e.getMessage());
        } finally {
            fecharConexao();
        }
        return perguntas;
    }
    
    public boolean responderPergunta(int idPergunta, String resposta) {
        Connection connection = Database.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(RESPONDER_PERGUNTA)) {
            preparedStatement.setString(1, resposta);
            preparedStatement.setInt(2, idPergunta);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;  // Retorna true se a resposta foi registrada com sucesso

        } catch (SQLException e) {
            System.out.println("Erro ao responder pergunta: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deletarPergunta(int idPergunta) {
        Connection connection = Database.connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETAR_PERGUNTA)) {
            preparedStatement.setInt(1, idPergunta);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;  // Retorna true se a exclusão foi bem-sucedida

        } catch (SQLException e) {
            System.out.println("Erro ao deletar pergunta: " + e.getMessage());
            return false;
        }
    }
    // Método para fechar a conexão
    private void fecharConexao() {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }
}
