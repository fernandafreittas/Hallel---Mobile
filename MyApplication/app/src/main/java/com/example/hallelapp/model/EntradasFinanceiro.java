package com.example.hallelapp.model;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class EntradasFinanceiro implements Comparable<EntradasFinanceiro>{
    private String id;
    private CodigoEntradaFinanceiro codigo;
    private Date date;
    private Double valor;
    private MetodosPagamentosFinanceiro metodoPagamento;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CodigoEntradaFinanceiro getCodigo() {
        return codigo;
    }

    public void setCodigo(CodigoEntradaFinanceiro codigo) {
        this.codigo = codigo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public MetodosPagamentosFinanceiro getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodosPagamentosFinanceiro metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    @Override
    public int compareTo(@NotNull EntradasFinanceiro o) {
        if(this.date != null && o.getDate()!=null) {
            return o.getDate().compareTo(this.getDate());
        }else{
            return -1;
        }
    }
}
