package com.example.hallelapp.payload.resposta;

import java.util.Date;

public class SeVoluntariarEventoResponse {


    private String id;
    private String nome;
    private String email;
    private String Sexo;
    private Date dataNascimento;
    private String numeroDeTelefone;
    private String cpf;
    private String preferencia;

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
        return "SeVoluntariarEventoResponse{" +
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
