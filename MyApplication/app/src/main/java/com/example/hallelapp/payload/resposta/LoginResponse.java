package com.example.hallelapp.payload.resposta;

import com.example.hallelapp.model.Roles;

import java.util.Set;

public class LoginResponse {
    private String token;
    private String id;
    private String nome;
    private String email;
    private Set<Roles> roles;
    private String cpf;
    private Integer idade;
    private String imagem;

    private String statusMembro;

    private Boolean lembreDeMim;

    public LoginResponse(String token, String id, String nome, String email, Set<Roles> roles, String cpf, Integer idade, String imagem, String statusMembro, Boolean lembreDeMim) {
        this.token = token;
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.roles = roles;
        this.cpf = cpf;
        this.idade = idade;
        this.imagem = imagem;
        this.statusMembro = statusMembro;
        this.lembreDeMim = lembreDeMim;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", cpf='" + cpf + '\'' +
                ", idade=" + idade +
                ", imagem='" + imagem + '\'' +
                ", statusMembro='" + statusMembro + '\'' +
                ", lembreDeMim=" + lembreDeMim +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getStatusMembro() {
        return statusMembro;
    }

    public void setStatusMembro(String statusMembro) {
        this.statusMembro = statusMembro;
    }

    public Boolean getLembreDeMim() {
        return lembreDeMim;
    }

    public void setLembreDeMim(Boolean lembreDeMim) {
        this.lembreDeMim = lembreDeMim;
    }
}
