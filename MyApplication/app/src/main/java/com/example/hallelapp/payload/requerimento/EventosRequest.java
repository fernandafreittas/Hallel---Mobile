package com.example.hallelapp.payload.requerimento;

import com.example.hallelapp.model.ContribuicaoEvento;
import com.example.hallelapp.model.LocalEvento;
import com.example.hallelapp.model.LocalEventoLocalizacaoRequest;
import com.example.hallelapp.model.PagamentoEntradaEvento;

import java.util.Date;
import java.util.List;

public class EventosRequest {

    private String descricao;
    private String titulo;

    private Date date;

    private LocalEventoLocalizacaoRequest localEventoRequest;
    private LocalEvento localEvento;
    private String horario;
    private String imagem;
    private List<String> palestrantes;
    private List<PagamentoEntradaEvento> pagamentoEntradaEventoList;
    private Boolean destaque;
    private List<ContribuicaoEvento> contribuicaoEventosList;
    private Double valorDoEvento;
    private Double valorDescontoMembro;
    private Double valorDescontoAssociado;




    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalEventoLocalizacaoRequest getLocalEventoRequest() {
        return localEventoRequest;
    }

    public void setLocalEventoRequest(LocalEventoLocalizacaoRequest localEventoRequest) {
        this.localEventoRequest = localEventoRequest;
    }

    public LocalEvento getLocalEvento() {
        return localEvento;
    }

    public void setLocalEvento(LocalEvento localEvento) {
        this.localEvento = localEvento;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
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

    public Boolean getDestaque() {
        return destaque;
    }

    public void setDestaque(Boolean destaque) {
        this.destaque = destaque;
    }

    public List<ContribuicaoEvento> getContribuicaoEventosList() {
        return contribuicaoEventosList;
    }

    public void setContribuicaoEventosList(List<ContribuicaoEvento> contribuicaoEventosList) {
        this.contribuicaoEventosList = contribuicaoEventosList;
    }

    public Double getValorDoEvento() {
        return valorDoEvento;
    }

    public void setValorDoEvento(Double valorDoEvento) {
        this.valorDoEvento = valorDoEvento;
    }

    public Double getValorDescontoMembro() {
        return valorDescontoMembro;
    }

    public void setValorDescontoMembro(Double valorDescontoMembro) {
        this.valorDescontoMembro = valorDescontoMembro;
    }

    public Double getValorDescontoAssociado() {
        return valorDescontoAssociado;
    }

    public void setValorDescontoAssociado(Double valorDescontoAssociado) {
        this.valorDescontoAssociado = valorDescontoAssociado;
    }

    @Override
    public String toString() {
        return "EventosRequest{" +
                "descricao='" + descricao + '\'' +
                ", titulo='" + titulo + '\'' +
                ", date=" + date +
                ", localEventoRequest=" + localEventoRequest +
                ", localEvento=" + localEvento +
                ", horario='" + horario + '\'' +
                ", imagem='" + imagem + '\'' +
                ", palestrantes=" + palestrantes +
                ", pagamentoEntradaEventoList=" + pagamentoEntradaEventoList +
                ", destaque=" + destaque +
                ", contribuicaoEventosList=" + contribuicaoEventosList +
                ", valorDoEvento=" + valorDoEvento +
                ", ValorDescontoMembro=" + valorDescontoMembro +
                ", ValorDescontoAssociado=" + valorDescontoAssociado +
                '}';
    }
}
