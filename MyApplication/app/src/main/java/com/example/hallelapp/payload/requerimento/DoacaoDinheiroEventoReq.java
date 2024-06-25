package com.example.hallelapp.payload.requerimento;

import com.example.hallelapp.model.CartaoCredito;

import java.io.Serializable;

public class DoacaoDinheiroEventoReq implements Serializable {

    private String emailDoador;
    private Double valorDoado;
    private String formaDePagamento;
    private String nomeDoador;

    private CartaoCredito cartaoCredito;

    private Boolean Mensalmente;
    private Boolean Anualmente;


    private String dia;

    public String getEmailDoador() {
        return emailDoador;
    }

    public void setEmailDoador(String emailDoador) {
        this.emailDoador = emailDoador;
    }

    public Double getValorDoado() {
        return valorDoado;
    }

    public void setValorDoado(Double valorDoado) {
        this.valorDoado = valorDoado;
    }

    public String getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(String formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public String getNomeDoador() {
        return nomeDoador;
    }

    public void setNomeDoador(String nomeDoador) {
        this.nomeDoador = nomeDoador;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public Boolean getMensalmente() {
        return Mensalmente;
    }

    public void setMensalmente(Boolean mensalmente) {
        Mensalmente = mensalmente;
    }

    public Boolean getAnualmente() {
        return Anualmente;
    }

    public void setAnualmente(Boolean anualmente) {
        Anualmente = anualmente;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    @Override
    public String toString() {
        return "DoacaoDinheiroEventoReq{" +
                "emailDoador='" + emailDoador + '\'' +
                ", valorDoado=" + valorDoado +
                ", formaDePagamento='" + formaDePagamento + '\'' +
                ", nomeDoador='" + nomeDoador + '\'' +
                ", cartaoCredito=" + cartaoCredito.toString() +
                ", Mensalmente=" + Mensalmente +
                ", Anualmente=" + Anualmente +
                ", dia='" + dia + '\'' +
                '}';
    }
}
