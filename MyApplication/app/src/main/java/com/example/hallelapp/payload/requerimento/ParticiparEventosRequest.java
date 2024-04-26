package com.example.hallelapp.payload.requerimento;

import com.example.hallelapp.model.CartaoCredito;

import java.util.Date;

public class ParticiparEventosRequest {

    private String idEvento;
    private String id;
    private String email;
    private String cpf;
    private String nome;
    private Integer idade;
    private int numMetodoPagamento;
    private CartaoCredito cartaoCredito;
    private boolean membro;
    private boolean associado;
    private Date dataInscricao;
    private Double ValorPago;


    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public int getNumMetodoPagamento() {
        return numMetodoPagamento;
    }

    public void setNumMetodoPagamento(int numMetodoPagamento) {
        this.numMetodoPagamento = numMetodoPagamento;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public boolean isMembro() {
        return membro;
    }

    public void setMembro(boolean membro) {
        this.membro = membro;
    }

    public boolean isAssociado() {
        return associado;
    }

    public void setAssociado(boolean associado) {
        this.associado = associado;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public Double getValorPago() {
        return ValorPago;
    }

    public void setValorPago(Double valorPago) {
        ValorPago = valorPago;
    }

    @Override
    public String toString() {
        return "ParticiparEventosRequest{" +
                "idEvento='" + idEvento + '\'' +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", numMetodoPagamento=" + numMetodoPagamento +
                ", cartaoCredito=" + cartaoCredito +
                ", membro=" + membro +
                ", associado=" + associado +
                ", dataInscricao=" + dataInscricao +
                ", ValorPago=" + ValorPago +
                '}';
    }
}
