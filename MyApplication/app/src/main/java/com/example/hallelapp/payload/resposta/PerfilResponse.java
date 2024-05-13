package com.example.hallelapp.payload.resposta;

import com.example.hallelapp.model.StatusMembro;

import java.io.Serializable;
import java.util.Date;

public class PerfilResponse implements Serializable {

    private String nome;
    private Date dataAniversario;
    private String email;
    private StatusMembro status;
    private Integer idade;
    private String image;
    private String cpf;
    private String Telefone;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataAniversario() {
        return dataAniversario;
    }

    public void setDataAniversario(Date dataAniversario) {
        this.dataAniversario = dataAniversario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StatusMembro getStatus() {
        return status;
    }

    public void setStatus(StatusMembro status) {
        this.status = status;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    @Override
    public String toString() {
        return "PerfilResponse{" +
                "nome='" + nome + '\'' +
                ", dataAniversario=" + dataAniversario +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", idade=" + idade +
                ", image='" + image + '\'' +
                ", cpf='" + cpf + '\'' +
                ", Telefone='" + Telefone + '\'' +
                '}';
    }
}
