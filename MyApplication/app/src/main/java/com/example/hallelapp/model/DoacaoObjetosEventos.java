package com.example.hallelapp.model;

import java.util.Date;

public class DoacaoObjetosEventos {

    private String id;
    private String nomeDoObjeto;
    private Integer quantidade;
    private String emailDoador;
    private Boolean recebido;
    private String dataRecebida;
    private Date dataDoacao;
    private String nomeDoador;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeDoObjeto() {
        return nomeDoObjeto;
    }

    public void setNomeDoObjeto(String nomeDoObjeto) {
        this.nomeDoObjeto = nomeDoObjeto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getEmailDoador() {
        return emailDoador;
    }

    public void setEmailDoador(String emailDoador) {
        this.emailDoador = emailDoador;
    }

    public boolean isRecebido() {
        return recebido;
    }

    public void setRecebido(boolean recebido) {
        recebido = recebido;
    }

    public String getDataRecebida() {
        return dataRecebida;
    }

    public void setDataRecebida(String dataRecebida) {
        this.dataRecebida = dataRecebida;
    }

    public Date getDataDoacao() {
        return dataDoacao;
    }

    public void setDataDoacao(Date dataDoacao) {
        this.dataDoacao = dataDoacao;
    }

    public String getNomeDoador() {
        return nomeDoador;
    }

    public void setNomeDoador(String nomeDoador) {
        this.nomeDoador = nomeDoador;
    }

    @Override
    public String toString() {
        return "DoacaoObjetosEventos{" +
                "id='" + id + '\'' +
                ", nomeDoObjeto='" + nomeDoObjeto + '\'' +
                ", quantidade=" + quantidade +
                ", emailDoador='" + emailDoador + '\'' +
                ", isRecebido=" + recebido +
                ", dataRecebida='" + dataRecebida + '\'' +
                ", dataDoacao=" + dataDoacao +
                ", nomeDoador='" + nomeDoador + '\'' +
                '}';
    }
}
