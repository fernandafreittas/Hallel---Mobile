package com.example.hallelapp.payload.resposta;

import java.io.Serializable;
import java.util.Date;

public class DoacaoDinheiroEventoResponse implements Serializable {

    private String id;
    private String formaDePagamento;
    private Double valorDoado;
    private String nomeDoador;
    private Date dataDoacao;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(String formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public Double getValorDoado() {
        return valorDoado;
    }

    public void setValorDoado(Double valorDoado) {
        this.valorDoado = valorDoado;
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

    @Override
    public String toString() {
        return "DoacaoDinheiroEventoResponse{" +
                "id='" + id + '\'' +
                ", formaDePagamento='" + formaDePagamento + '\'' +
                ", valorDoado=" + valorDoado +
                ", nomeDoador='" + nomeDoador + '\'' +
                ", dataDoacao=" + dataDoacao +
                '}';
    }
}
