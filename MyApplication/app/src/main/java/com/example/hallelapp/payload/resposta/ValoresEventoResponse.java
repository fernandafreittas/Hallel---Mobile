package com.example.hallelapp.payload.resposta;

public class ValoresEventoResponse {


    Double valorEvento;
    Double valorDescontoMembro;
    Double valorDescontoAssociado;

    public Double getValorEvento() {
        return valorEvento;
    }

    public void setValorEvento(Double valorEvento) {
        this.valorEvento = valorEvento;
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
        return "ValoresEventoResponse{" +
                "valorEvento=" + valorEvento +
                ", valorDescontoMembro=" + valorDescontoMembro +
                ", valorDescontoAssociado=" + valorDescontoAssociado +
                '}';
    }
}
