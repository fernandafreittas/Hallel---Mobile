package com.example.hallelapp.payload.requerimento;

import java.text.SimpleDateFormat;

public class LocalEventoReq {

    private SimpleDateFormat simpleDateFormat;

    private String localizacao;
    private String imagem;

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
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

    @Override
    public String toString() {
        return "LocalEventoReq{" +
                "simpleDateFormat=" + simpleDateFormat +
                ", localizacao='" + localizacao + '\'' +
                ", imagem='" + imagem + '\'' +
                '}';
    }
}
