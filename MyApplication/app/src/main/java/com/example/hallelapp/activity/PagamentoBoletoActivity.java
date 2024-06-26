package com.example.hallelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.MainActivity;
import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.model.CartaoCredito;
import com.example.hallelapp.payload.requerimento.DoacaoDinheiroEventoReq;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;

import java.io.IOException;

public class PagamentoBoletoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento_boleto);

        // Recuperar o objeto evento da intent
        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");
        DoacaoDinheiroEventoReq doacaoDinheiroEventoReq = (DoacaoDinheiroEventoReq) getIntent().getSerializableExtra("doacao");


        HttpMain requisicao = new HttpMain();

        Button BtnGerarBoleto = findViewById(R.id.btnDoarDinheito);
        Button BtnCopiarCodigoBoleto = findViewById(R.id.btnDoarObj);


        BtnGerarBoleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doacaoDinheiroEventoReq.setCartaoCredito(null);
                doacaoDinheiroEventoReq.setFormaDePagamento("boleto");

                requisicao.DoarDinheiroEventoPixBoleto(evento.getId(), doacaoDinheiroEventoReq, new HttpMain.HttpCallback() {
                    @Override
                    public void onSuccess(String response) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PagamentoBoletoActivity.this, "Boleto baixado com sucesso!", Toast.LENGTH_LONG).show();
                            }
                        });

                        System.out.println(doacaoDinheiroEventoReq.toString());
                        System.out.println("deu certo");

                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                });


            }
        });


        BtnCopiarCodigoBoleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doacaoDinheiroEventoReq.setCartaoCredito(null);
                doacaoDinheiroEventoReq.setFormaDePagamento("boleto");

                requisicao.DoarDinheiroEventoPixBoleto(evento.getId(), doacaoDinheiroEventoReq, new HttpMain.HttpCallback() {
                    @Override
                    public void onSuccess(String response) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PagamentoBoletoActivity.this, "Boleto copiado para area de transferencia!!", Toast.LENGTH_LONG).show();
                            }
                        });

                        System.out.println(doacaoDinheiroEventoReq.toString());
                        System.out.println("deu certo");

                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                });


            }
        });



    }


    }
