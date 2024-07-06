package com.example.hallelapp.model;

public class Administrador extends Membro{

    private String senhaAcesso;
    private String cargo;

    public String getSenhaAcesso() {
        return senhaAcesso;
    }

    public void setSenhaAcesso(String senhaAcesso) {
        this.senhaAcesso = senhaAcesso;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "senhaAcesso='" + senhaAcesso + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }
}
