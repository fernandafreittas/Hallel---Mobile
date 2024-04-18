package com.example.hallelapp.model;

import java.io.Serializable;

public class LocalEvento implements Serializable {

    private String id;
    private String localizacao;
    private String imagem;
    private String dataCadastrada;

    public LocalEvento(String id, String localizacao, String imagem, String dataCadastrada) {
        this.id = id;
        this.localizacao = localizacao;
        this.imagem = imagem;
        this.dataCadastrada = dataCadastrada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getDataCadastrada() {
        return dataCadastrada;
    }

    public void setDataCadastrada(String dataCadastrada) {
        this.dataCadastrada = dataCadastrada;
    }

    @Override
    public String toString() {
        return "LocalEvento{" +
                "id='" + id + '\'' +
                ", localizacao='" + localizacao + '\'' +
                ", imagem='" + imagem + '\'' +
                ", dataCadastrada='" + dataCadastrada + '\'' +
                '}';
    }
}
