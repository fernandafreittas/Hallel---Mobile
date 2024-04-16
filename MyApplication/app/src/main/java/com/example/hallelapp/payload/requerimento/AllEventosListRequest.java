package com.example.hallelapp.payload.requerimento;

import com.example.hallelapp.model.LocalEvento;
import com.example.hallelapp.model.PagamentoEntradaEvento;

import java.util.Date;
import java.util.List;

public class AllEventosListRequest {

    private String id;
    private String titulo;
    private String descricao;
    private Date date;
    private String imagem;
    private LocalEvento localEvento;
    private Boolean destaque;
    private String horario;
    private List<String> palestrantes;
    private List<PagamentoEntradaEvento> pagamentoEntradaEventoList;



}
