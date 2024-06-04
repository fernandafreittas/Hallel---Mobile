package com.example.hallelapp.model;

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

}