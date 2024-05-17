package com.example.hallelapp.model;

import com.example.hallelapp.payload.requerimento.EventosRequest;

import java.util.Date;

public class ContribuicaoEvento {

    private String id;
    private String nome;
    private String emailPagador;
    private String tipoContribuicao;
    private Integer quantidade;
    private EventosRequest evento;
    private Date data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmailPagador() {
        return emailPagador;
    }

    public void setEmailPagador(String emailPagador) {
        this.emailPagador = emailPagador;
    }

    public String getTipoContribuicao() {
        return tipoContribuicao;
    }

    public void setTipoContribuicao(String tipoContribuicao) {
        this.tipoContribuicao = tipoContribuicao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public EventosRequest getEvento() {
        return evento;
    }

    public void setEvento(EventosRequest evento) {
        this.evento = evento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
