package com.example.hallelapp.payload.resposta;

import java.util.Date;

public class DoacaoObjetosEventosResponse {


    private String id;
    private String nomeDoObjeto;
    private Integer quantidade;
    private String nomeDoador;
    private Date dataDoacao;
    private boolean isRecebido;

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

    public String getNomeDoador() {
        return nomeDoador;
    }

    public void setNomeDoador(String nomeDoador) {
        this.nomeDoador = nomeDoador;
    }

    public Date getDataDoacao() {
        return dataDoacao;
    }

    public void setDataDoacao(Date dataDoacao) {
        this.dataDoacao = dataDoacao;
    }

    public boolean isRecebido() {
        return isRecebido;
    }

    public void setRecebido(boolean recebido) {
        isRecebido = recebido;
    }

    @Override
    public String toString() {
        return "DoacaoObjetosEventosResponse{" +
                "id='" + id + '\'' +
                ", nomeDoObjeto='" + nomeDoObjeto + '\'' +
                ", quantidade=" + quantidade +
                ", nomeDoador='" + nomeDoador + '\'' +
                ", dataDoacao=" + dataDoacao +
                ", isRecebido=" + isRecebido +
                '}';
    }
}
