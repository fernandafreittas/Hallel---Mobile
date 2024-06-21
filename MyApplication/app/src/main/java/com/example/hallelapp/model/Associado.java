package com.example.hallelapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Associado extends Membro {

    private Eventos eventoParticipando;
    private AssociadoStatus isAssociado;
    private Date dataExpiroAssociacao;
    private List<Recompensa> recompensas;
    private String dataNascimentoAssociado;
    private Double desempenhoTotalCursos;
    private ArrayList<Curso> historicoCurso;
    private CartaoCredito cartaoCredito;
    private HashMap<AtividadesCurso, Boolean> associadoAtividadesCurso;
    private ArrayList<Curso> cursosFavoritos;
    private ArrayList<Curso> cursosInscritos;
    private ArrayList<ModulosCurso> modulosCursosCompletos;
    private Boolean mensalidadePaga;
    private List<Date> mesesPagos;


    private List<PagamentosAssociado> pagamentosAssociados;


    public Eventos getEventoParticipando() {
        return eventoParticipando;
    }

    public void setEventoParticipando(Eventos eventoParticipando) {
        this.eventoParticipando = eventoParticipando;
    }

    public AssociadoStatus getIsAssociado() {
        return isAssociado;
    }

    public void setIsAssociado(AssociadoStatus isAssociado) {
        this.isAssociado = isAssociado;
    }

    public Date getDataExpiroAssociacao() {
        return dataExpiroAssociacao;
    }

    public void setDataExpiroAssociacao(Date dataExpiroAssociacao) {
        this.dataExpiroAssociacao = dataExpiroAssociacao;
    }

    public List<Recompensa> getRecompensas() {
        return recompensas;
    }

    public void setRecompensas(List<Recompensa> recompensas) {
        this.recompensas = recompensas;
    }

    public String getDataNascimentoAssociado() {
        return dataNascimentoAssociado;
    }

    public void setDataNascimentoAssociado(String dataNascimentoAssociado) {
        this.dataNascimentoAssociado = dataNascimentoAssociado;
    }

    public Double getDesempenhoTotalCursos() {
        return desempenhoTotalCursos;
    }

    public void setDesempenhoTotalCursos(Double desempenhoTotalCursos) {
        this.desempenhoTotalCursos = desempenhoTotalCursos;
    }

    public ArrayList<Curso> getHistoricoCurso() {
        return historicoCurso;
    }

    public void setHistoricoCurso(ArrayList<Curso> historicoCurso) {
        this.historicoCurso = historicoCurso;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public HashMap<AtividadesCurso, Boolean> getAssociadoAtividadesCurso() {
        return associadoAtividadesCurso;
    }

    public void setAssociadoAtividadesCurso(HashMap<AtividadesCurso, Boolean> associadoAtividadesCurso) {
        this.associadoAtividadesCurso = associadoAtividadesCurso;
    }

    public ArrayList<Curso> getCursosFavoritos() {
        return cursosFavoritos;
    }

    public void setCursosFavoritos(ArrayList<Curso> cursosFavoritos) {
        this.cursosFavoritos = cursosFavoritos;
    }

    public ArrayList<Curso> getCursosInscritos() {
        return cursosInscritos;
    }

    public void setCursosInscritos(ArrayList<Curso> cursosInscritos) {
        this.cursosInscritos = cursosInscritos;
    }

    public ArrayList<ModulosCurso> getModulosCursosCompletos() {
        return modulosCursosCompletos;
    }

    public void setModulosCursosCompletos(ArrayList<ModulosCurso> modulosCursosCompletos) {
        this.modulosCursosCompletos = modulosCursosCompletos;
    }

    public Boolean getMensalidadePaga() {
        return mensalidadePaga;
    }

    public void setMensalidadePaga(Boolean mensalidadePaga) {
        this.mensalidadePaga = mensalidadePaga;
    }

    public List<Date> getMesesPagos() {
        return mesesPagos;
    }

    public void setMesesPagos(List<Date> mesesPagos) {
        this.mesesPagos = mesesPagos;
    }

    public List<PagamentosAssociado> getPagamentosAssociados() {
        return pagamentosAssociados;
    }

    public void setPagamentosAssociados(List<PagamentosAssociado> pagamentosAssociados) {
        this.pagamentosAssociados = pagamentosAssociados;
    }

    @Override
    public String toString() {
        return "Associado{" +
                "eventoParticipando=" + eventoParticipando +
                ", isAssociado=" + isAssociado +
                ", dataExpiroAssociacao=" + dataExpiroAssociacao +
                ", recompensas=" + recompensas +
                ", dataNascimentoAssociado='" + dataNascimentoAssociado + '\'' +
                ", desempenhoTotalCursos=" + desempenhoTotalCursos +
                ", historicoCurso=" + historicoCurso +
                ", cartaoCredito=" + cartaoCredito +
                ", associadoAtividadesCurso=" + associadoAtividadesCurso +
                ", cursosFavoritos=" + cursosFavoritos +
                ", cursosInscritos=" + cursosInscritos +
                ", modulosCursosCompletos=" + modulosCursosCompletos +
                ", mensalidadePaga=" + mensalidadePaga +
                ", mesesPagos=" + mesesPagos +
                ", pagamentosAssociados=" + pagamentosAssociados +
                '}';
    }
}