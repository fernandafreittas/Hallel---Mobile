package com.example.hallelapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Curso implements Serializable {

    private String id;
    private String nome;
    private String image;
    private String descricao;

    private ArrayList<String> requisitos;
    private ArrayList<String> tags;
    private ArrayList<String> aprendizado;
    private ArrayList<String> conteudo;

    private ArrayList<AtividadesCurso> atividades;
    private ArrayList<ModulosCurso> modulos;
    private ArrayList<Associado> participantes;
    private String desempenhoDoCurso;
    private Boolean cursoCompleted;


}
