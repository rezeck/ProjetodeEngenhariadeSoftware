package com.example.mateuspalhares.projetodeengenhariadesoftware;

/**
 * Created by mateuspalhares on 28/10/15.
 */
public class QuestionModel {

    private long id;
    private String pergunta;

    private int correta;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public int getCorreta() {
        return correta;
    }

    public void setCorreta(int correta) {
        this.correta = correta;
    }
}
