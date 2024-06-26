package com.example.hallelapp.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.payload.requerimento.DoacaoDinheiroEventoReq;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;

public class PagamentoPixActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento_boleto);

        // Recuperar o objeto evento da intent
        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");
        DoacaoDinheiroEventoReq doacaoDinheiroEventoReq = (DoacaoDinheiroEventoReq) getIntent().getSerializableExtra("doacao");


        HttpMain requisicao = new HttpMain();



    }



    }
