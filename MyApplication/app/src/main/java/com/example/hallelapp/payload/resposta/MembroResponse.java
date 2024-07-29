package com.example.hallelapp.payload.resposta;

import com.example.hallelapp.model.Roles;
import com.example.hallelapp.model.StatusMembro;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class MembroResponse implements Serializable {


    private String id;
    private String nome;
    private String email;
    private StatusMembro statusMembro;
    private Set<Roles> roles;
    private String cpf;
    private Integer idade;
    private String imagem;

    private String telefone;

    private Date dataNascimento;

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return "MembroResponse{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", statusMembro=" + statusMembro +
                ", roles=" + roles +
                ", cpf='" + cpf + '\'' +
                ", idade=" + idade +
                ", imagem='" + imagem + '\'' +
                ", telefone='" + telefone + '\'' +
                ", dataNascimento=" + dataNascimento +
                '}';
    }
}
