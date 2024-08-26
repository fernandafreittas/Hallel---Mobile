package com.example.hallelapp.model;

import java.util.Date;
import java.util.List;

public class Eventos {

    private String id;
    private String titulo;
    private String descricao;
    private Date date;
    private String imagem;
    private LocalEvento localEvento;
    private Boolean destaque;
    private String horario;
    private List<String> palestrantes;
    private List<PagamentoEntradaEvento> pagamentoEntradaEventoList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public LocalEvento getLocalEvento() {
        return localEvento;
    }

    public void setLocalEvento(LocalEvento localEvento) {
        this.localEvento = localEvento;
    }

    public Boolean getDestaque() {
        return destaque;
    }

    public void setDestaque(Boolean destaque) {
        this.destaque = destaque;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public List<String> getPalestrantes() {
        return palestrantes;
    }

    public void setPalestrantes(List<String> palestrantes) {
        this.palestrantes = palestrantes;
    }

    public List<PagamentoEntradaEvento> getPagamentoEntradaEventoList() {
        return pagamentoEntradaEventoList;
    }

    public void setPagamentoEntradaEventoList(List<PagamentoEntradaEvento> pagamentoEntradaEventoList) {
        this.pagamentoEntradaEventoList = pagamentoEntradaEventoList;
    }
}
