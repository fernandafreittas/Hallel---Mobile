package com.example.hallelapp.payload.resposta;

import com.example.hallelapp.model.MetodosPagamentosFinanceiro;

import java.util.Date;

public class PagamentoAssociadoPerfilResponse {


    private Date date;
    private MetodosPagamentosFinanceiro metodoPag;
    private double valor;

    private Date dateToPagar;

    public PagamentoAssociadoPerfilResponse(Date date, MetodosPagamentosFinanceiro metodoPag, double valor, Date dateToPagar) {
        this.date = date;
        this.metodoPag = metodoPag;
        this.valor = valor;
        this.dateToPagar = dateToPagar;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MetodosPagamentosFinanceiro getMetodoPag() {
        return metodoPag;
    }

    public void setMetodoPag(MetodosPagamentosFinanceiro metodoPag) {
        this.metodoPag = metodoPag;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDateToPagar() {
        return dateToPagar;
    }

    public void setDateToPagar(Date dateToPagar) {
        this.dateToPagar = dateToPagar;
    }

    @Override
    public String toString() {
        return "PagamentoAssociadoPerfilResponse{" +
                "date=" + date +
                ", metodoPag=" + metodoPag +
                ", valor=" + valor +
                ", dateToPagar=" + dateToPagar +
                '}';
    }
}
