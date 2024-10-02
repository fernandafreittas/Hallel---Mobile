package com.example.hallelapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Membro implements Serializable {


    private String id;

    private String nome;
    private String senha;
    private String email;
    private String cpf;
    private Date dataNascimento;
    private StatusMembro statusMembro;
    private Integer idade;
    private String telefone;
    private String image;
    private String funcao;
    private Boolean doador;
    private Integer quantidadeDoacoes;
    private List<String> eventosParticipando;
    private CartaoCredito cartaoMembro;
    private Map<String, Object> atributtes;

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public StatusMembro getStatusMembro() {
        return statusMembro;
    }

    public void setStatusMembro(StatusMembro statusMembro) {
        this.statusMembro = statusMembro;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public Boolean getDoador() {
        return doador;
    }

    public void setDoador(Boolean doador) {
        this.doador = doador;
    }

    public Integer getQuantidadeDoacoes() {
        return quantidadeDoacoes;
    }

    public void setQuantidadeDoacoes(Integer quantidadeDoacoes) {
        this.quantidadeDoacoes = quantidadeDoacoes;
    }

    public List<String> getEventosParticipando() {
        return eventosParticipando;
    }

    public void setEventosParticipando(List<String> eventosParticipando) {
        this.eventosParticipando = eventosParticipando;
    }

    public CartaoCredito getCartaoMembro() {
        return cartaoMembro;
    }

    public void setCartaoMembro(CartaoCredito cartaoMembro) {
        this.cartaoMembro = cartaoMembro;
    }

    public Map<String, Object> getAtributtes() {
        return atributtes;
    }

    public void setAtributtes(Map<String, Object> atributtes) {
        this.atributtes = atributtes;
    }

    @Override
    public String toString() {
        return "Membro{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", statusMembro=" + statusMembro +
                ", idade=" + idade +
                ", telefone='" + telefone + '\'' +
                ", funcao='" + funcao + '\'' +
                ", doador=" + doador +
                ", quantidadeDoacoes=" + quantidadeDoacoes +
                ", eventosParticipando=" + eventosParticipando +
                ", cartaoMembro=" + cartaoMembro +
                ", atributtes=" + atributtes +
                '}';
    }
}
