package com.example.hallelapp.payload.resposta;

import com.example.hallelapp.model.LocalEvento;
import com.example.hallelapp.model.PagamentoEntradaEvento;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AllEventosListResponse implements Serializable {

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

    public AllEventosListResponse(String id, String titulo, String descricao, Date date, String imagem, LocalEvento localEvento, Boolean destaque, String horario, List<String> palestrantes, List<PagamentoEntradaEvento> pagamentoEntradaEventoList) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.date = date;
        this.imagem = imagem;
        this.localEvento = localEvento;
        this.destaque = destaque;
        this.horario = horario;
        this.palestrantes = palestrantes;
        this.pagamentoEntradaEventoList = pagamentoEntradaEventoList;
    }

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

    @Override
    public String toString() {
        return "AllEventosListResponse{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", date=" + date +
                ", imagem='" + imagem + '\'' +
                ", localEvento=" + localEvento +
                ", destaque=" + destaque +
                ", horario='" + horario + '\'' +
                ", palestrantes=" + palestrantes +
                ", pagamentoEntradaEventoList=" + pagamentoEntradaEventoList +
                '}';
    }
}
