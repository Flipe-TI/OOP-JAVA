package model;

public class Pergunta {
    private int id;
    private int idUsuario;
    private String pergunta;
    private String resposta;
    private String dataCriacao;
    private String dataResposta;

    public Pergunta(int id, int idUsuario, String pergunta, String resposta, String dataCriacao, String dataResposta) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.dataCriacao = dataCriacao;
        this.dataResposta = dataResposta;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public String getDataResposta() {
        return dataResposta;
    }
}
