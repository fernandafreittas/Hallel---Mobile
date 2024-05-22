package com.example.hallelapp.model;

import java.util.Date;

public class VoluntarioEvento {

    private String id;
    private String nome;
    private String email;
    private String Sexo;
    private Date dataNascimento;
    private String numeroDeTelefone;
    private String cpf;
    private String preferencia;

    public VoluntarioEvento(String id, String nome, String email, String sexo, Date dataNascimento, String numeroDeTelefone, String cpf, String preferencia) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        Sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.numeroDeTelefone = numeroDeTelefone;
        this.cpf = cpf;
        this.preferencia = preferencia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "VoluntarioEvento{" +
                "id='" + id + '\'' +
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
