package com.example.hallelapp.model;

import java.util.Date;
import java.util.List;

public class EventoArquivado {

    private String id;
    private List<Associado> associadoParticipando;
    private String descricao;
    private Long quantidadeMembros;
    private Long maxMembros;
    private String titulo;
    private List<Membro> integrantes;
    private MembroMarketing membroMarketing;
    private Administrador administrador;
    private Date date;
    private LocalEvento localEvento;
    private String horario;
    private String imagem;
    private Long participantesEspeciais;
    private Boolean destaque;

    private List<DespesaEvento> despesas;
    private List<String> palestrantes;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Associado> getAssociadoParticipando() {
        return associadoParticipando;
    }

    public void setAssociadoParticipando(List<Associado> associadoParticipando) {
        this.associadoParticipando = associadoParticipando;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getQuantidadeMembros() {
        return quantidadeMembros;
    }

    public void setQuantidadeMembros(Long quantidadeMembros) {
        this.quantidadeMembros = quantidadeMembros;
    }

    public Long getMaxMembros() {
        return maxMembros;
    }

    public void setMaxMembros(Long maxMembros) {
        this.maxMembros = maxMembros;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Membro> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<Membro> integrantes) {
        this.integrantes = integrantes;
    }

    public MembroMarketing getMembroMarketing() {
        return membroMarketing;
    }

    public void setMembroMarketing(MembroMarketing membroMarketing) {
        this.membroMarketing = membroMarketing;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalEvento getLocalEvento() {
        return localEvento;
    }

    public void setLocalEvento(LocalEvento localEvento) {
        this.localEvento = localEvento;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Long getParticipantesEspeciais() {
        return participantesEspeciais;
    }

    public void setParticipantesEspeciais(Long participantesEspeciais) {
        this.participantesEspeciais = participantesEspeciais;
    }

    public Boolean getDestaque() {
        return destaque;
    }

    public void setDestaque(Boolean destaque) {
        this.destaque = destaque;
    }

    public List<DespesaEvento> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<DespesaEvento> despesas) {
        this.despesas = despesas;
    }

    public List<String> getPalestrantes() {
        return palestrantes;
    }

    public void setPalestrantes(List<String> palestrantes) {
        this.palestrantes = palestrantes;
    }

    @Override
    public String toString() {
        return "EventoArquivado{" +
                "id='" + id + '\'' +
                ", associadoParticipando=" + associadoParticipando +
                ", descricao='" + descricao + '\'' +
                ", quantidadeMembros=" + quantidadeMembros +
                ", maxMembros=" + maxMembros +
                ", titulo='" + titulo + '\'' +
                ", integrantes=" + integrantes +
                ", membroMarketing=" + membroMarketing +
                ", administrador=" + administrador +
                ", date=" + date +
                ", localEvento=" + localEvento +
                ", horario='" + horario + '\'' +
                ", imagem='" + imagem + '\'' +
                ", participantesEspeciais=" + participantesEspeciais +
                ", destaque=" + destaque +
                ", despesas=" + despesas +
                ", palestrantes=" + palestrantes +
                '}';
    }
}
