package com.example.hallelapp.model;

import java.util.Set;

public class Membro {

    private String id;
    private String nome;
    private String email;
    private StatusMembro statusMembro;
    private Set<Roles> roles;
    private String cpf;
    private Integer idade;
    private String imagem;

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

    public StatusMembro getStatusMembro() {
        return statusMembro;
    }

    public void setStatusMembro(StatusMembro statusMembro) {
        this.statusMembro = statusMembro;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
