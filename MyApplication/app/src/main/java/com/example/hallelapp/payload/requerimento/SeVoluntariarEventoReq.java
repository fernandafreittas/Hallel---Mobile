package com.example.hallelapp.payload.requerimento;

public class SeVoluntariarEventoReq {

    private String nome;
    private String email;
    private String Sexo;

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

    @Override
    public String toString() {
        return "SeVoluntariarEventoReq{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", Sexo='" + Sexo + '\'' +
                '}';
    }
}
