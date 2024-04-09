package com.example.hallelapp.payload.requerimento;


public class CadastroRequest {

    String nome;
    String email;
    String senha;

    public CadastroRequest(String nomeCompleto, String email, String senha) {
        this.nome = nomeCompleto;
        this.email = email;
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "CadastroRequest{" +
                "nomeCompleto='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}