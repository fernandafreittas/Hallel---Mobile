package com.example.hallelapp.payload.requerimento;

import com.example.hallelapp.model.CartaoCredito;

import java.util.Date;

public class ParticiparEventosRequest {

    private String idEvento;
    private String id;
    private String email;
    private String cpf;
    private String nome;
    private Integer idade;
    private int numMetodoPagamento;
    private CartaoCredito cartaoCredito;
    private boolean membro;
    private boolean associado;
    private Date dataInscricao;
    private Double ValorPago;

}
