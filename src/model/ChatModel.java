package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dao.PerguntaDAO;

import com.google.gson.JsonArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ChatModel {

    private PerguntaDAO perguntaDAO;

    public ChatModel() {
        this.perguntaDAO = new PerguntaDAO();
    }

    public void enviarPerguntasParaAPI(String question, ModelCallback callback) {
        try {
            // Obtém todas as perguntas e respostas
            List<Pergunta> perguntas = perguntaDAO.listarTodasPerguntas();

            // Converte a lista de perguntas para o formato esperado pela API
            JsonArray dataArray = new JsonArray();
            for (Pergunta pergunta : perguntas) {
                JsonObject perguntaJson = new JsonObject();
                perguntaJson.addProperty("id", pergunta.getId());
                perguntaJson.addProperty("usuario_id", pergunta.getIdUsuario());
                perguntaJson.addProperty("pergunta", pergunta.getPergunta());
                perguntaJson.addProperty("resposta", pergunta.getResposta() != null ? pergunta.getResposta() : "Sem Resposta");
                perguntaJson.addProperty("data_criacao", pergunta.getDataCriacao());
                perguntaJson.addProperty("data_resposta", pergunta.getDataResposta() != null ? pergunta.getDataResposta() : "N/A");

                dataArray.add(perguntaJson);
            }

            // Cria o JSON final com a estrutura correta
            JsonObject finalJson = new JsonObject();
            finalJson.add("data", dataArray);
            finalJson.addProperty("question", question); // A pergunta do usuário

            // conexão com a API
            URL url = new URL("http://35.247.253.161:8000/ask-for-your-assistant");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(50000);  // Tempo de conexão
            connection.setReadTimeout(50000);   // Tempo de leitura aumentado para 10 segundos

            // Envia o JSON para a API
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = new Gson().toJson(finalJson).getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Verifica a resposta da API
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lê a resposta da API
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Converte a resposta em formato JSON
                JsonObject jsonResponse = new Gson().fromJson(response.toString(), JsonObject.class);

                // Extrai a resposta da chave "response"
                String apiResponse = jsonResponse.get("response").getAsString();

                // Chama o callback com a resposta da API
                callback.onResponse(apiResponse);

            } else {
                callback.onError("Erro ao enviar perguntas: Código de resposta " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            callback.onError("Erro ao enviar perguntas para a API: " + e.getMessage());
        }
    }

    // Interface para receber a resposta da API ou erro
    public interface ModelCallback {
        void onResponse(String response);
        void onError(String error);
    }
}
