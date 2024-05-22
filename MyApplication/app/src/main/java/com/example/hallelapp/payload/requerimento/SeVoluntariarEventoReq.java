package com.example.hallelapp.payload.requerimento;

import java.util.Date;

public class SeVoluntariarEventoReq {

    private String idEvento;
    private String nome;
    private String email;
    private String Sexo;
    private Date dataNascimento;
    private String numeroDeTelefone;
    private String cpf;
    private String preferencia;

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNumeroDeTelefone() {
        return numeroDeTelefone;
    }

    public void setNumeroDeTelefone(String numeroDeTelefone) {
        this.numeroDeTelefone = numeroDeTelefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPreferencia() {
        return preferencia;
    }

    public void setPreferencia(String preferencia) {
        this.preferencia = preferencia;
    }

    @Override
    public String toString() {
        return "SeVoluntariarEventoReq{" +
                "id='" + idEvento + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", Sexo='" + Sexo + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", numeroDeTelefone='" + numeroDeTelefone + '\'' +
                ", cpf='" + cpf + '\'' +
                ", preferencia='" + preferencia + '\'' +
                '}';
    }
}
