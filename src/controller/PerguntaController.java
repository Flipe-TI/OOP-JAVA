package controller;

import model.Database;
import model.Pergunta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PerguntaController {

    // Método para adicionar uma nova pergunta no banco de dados
    public void adicionarPergunta(int idUsuario, String perguntaTexto) {
        String sql = "INSERT INTO Perguntas (id_usuario, pergunta) VALUES (?, ?)";

        try (Connection conn = Database.connect(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);  // Passa o ID do usuário
            pstmt.setString(2, perguntaTexto);  // Passa o texto da pergunta
            pstmt.executeUpdate();  // Executa o comando

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar pergunta: " + e.getMessage());
        }
    }

    // Método para listar todas as perguntas feitas por um usuário específico
    public List<Pergunta> listarPerguntasPorUsuario(int idUsuario) {
        List<Pergunta> perguntas = new ArrayList<>();
        String sql = "SELECT * FROM Perguntas WHERE id_usuario = ?";

        try (Connection conn = Database.connect(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String pergunta = rs.getString("pergunta");
                String resposta = rs.getString("resposta");
                String dataCriacao = rs.getString("data_criacao");
                String dataResposta = rs.getString("data_resposta");

                // Adiciona a pergunta à lista de retorno
                Pergunta p = new Pergunta(id, idUsuario, pergunta, resposta, dataCriacao, dataResposta);
                perguntas.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar perguntas: " + e.getMessage());
        }

        return perguntas;  // Retorna a lista de perguntas do usuário
    }
}
