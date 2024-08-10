package com.example.hallelapp.payload.resposta;

import java.io.Serializable;
import java.util.Date;

public class DoacaoObjetosEventosResponse implements Serializable {


    private String id;
    private String nomeDoObjeto;
    private Integer quantidade;
    private String nomeDoador;
    private Date dataDoacao;
    private boolean recebido;
    private String emailDoador;

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
        return recebido;
    }

    public void setRecebido(boolean recebido) {
        this.recebido = recebido;
    }

    public String getEmailDoador() {
        return emailDoador;
    }

    public void setEmailDoador(String emailDoador) {
        this.emailDoador = emailDoador;
    }

    @Override
    public String toString() {
        return "DoacaoObjetosEventosResponse{" +
                "id='" + id + '\'' +
                ", nomeDoObjeto='" + nomeDoObjeto + '\'' +
                ", quantidade=" + quantidade +
                ", nomeDoador='" + nomeDoador + '\'' +
                ", dataDoacao=" + dataDoacao +
                ", isRecebido=" + recebido +
                ", emailDoador='" + emailDoador + '\'' +
                '}';
    }
}
