package com.example.hallelapp.model;

public class DespesaEvento {

    private Integer id;
    private String nome;
    private String descricao;
    private TipoDespesa tipoDespesa;

    // Em caso do tipo da despesa for igual a dinheiro
    private double valor;

    // Em caso do tipo da despesa for igual aos demais tipos
    private Integer quantidade;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoDespesa getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(TipoDespesa tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "DespesaEvento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", tipoDespesa=" + tipoDespesa +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                '}';
    }
}
