package com.example.mateuspalhares.projetodeengenhariadesoftware;

/**
 * Created by mateuspalhares on 25/11/15.
 */
public class Rank {
    private String nome;
    private int rank;

    public Rank() {
    }

    public Rank(String nome, int rank) {
        this.nome = nome;
        this.rank = rank;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
