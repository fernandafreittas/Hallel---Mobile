package com.example.hallelapp.payload.requerimento;

public class DoacaoObjetosEventosReq {


    private String nomeDoObjeto;
    private Integer quantidade;
    private String emailDoador;
    private String nomeDoador;


    public String getNomeDoObjeto() {
        return nomeDoObjeto;
    }

    public void setNomeDoObjeto(String nomeDoObjeto) {
        this.nomeDoObjeto = nomeDoObjeto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getEmailDoador() {
        return emailDoador;
    }

    public void setEmailDoador(String emailDoador) {
        this.emailDoador = emailDoador;
    }

    public String getNomeDoador() {
        return nomeDoador;
    }

    public void setNomeDoador(String nomeDoador) {
        this.nomeDoador = nomeDoador;
    }

    @Override
    public String toString() {
        return "DoacaoObjetosEventosReq{" +
                "nomeDoObjeto='" + nomeDoObjeto + '\'' +
                ", quantidade=" + quantidade +
                ", emailDoador='" + emailDoador + '\'' +
                ", nomeDoador='" + nomeDoador + '\'' +
                '}';
    }
}
