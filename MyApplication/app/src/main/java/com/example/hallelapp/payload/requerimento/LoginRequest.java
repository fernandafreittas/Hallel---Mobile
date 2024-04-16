package com.example.hallelapp.payload.requerimento;

public class LoginRequest {

    String email;
    String senha;

    public LoginRequest(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "Email='" + email + '\'' +
                ", Senha='" + senha + '\'' +
                '}';
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