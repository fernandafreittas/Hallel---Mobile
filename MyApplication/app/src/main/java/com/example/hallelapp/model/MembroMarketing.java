package com.example.hallelapp.model;

public class MembroMarketing {

    private String senhaAcesso;

    public String getSenhaAcesso() {
        return senhaAcesso;
    }

    public void setSenhaAcesso(String senhaAcesso) {
        this.senhaAcesso = senhaAcesso;
    }

    @Override
    public String toString() {
        return "MembroMarketing{" +
                "senhaAcesso='" + senhaAcesso + '\'' +
                '}';
    }
}
