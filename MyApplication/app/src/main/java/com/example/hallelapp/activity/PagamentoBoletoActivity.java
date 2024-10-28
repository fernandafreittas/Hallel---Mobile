package com.example.hallelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
                             dialogBoletoGerado();
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
                        showErrorCopiarDialog();
                    }
                });


            }
        });



    }

    private void dialogBoletoGerado() {
        // Inflate o layout do diálogo de erro
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_boletosucesso, null);

        // Cria o dialog a partir do layout inflado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Clique no botão de continuar para fechar o diálogo
        Button btnContinuar = dialogView.findViewById(R.id.button3bolgerados);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void showErrorDialog() {
        // Inflate o layout do diálogo de erro
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_erro_doacao, null);

        // Cria o dialog a partir do layout inflado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Clique no botão de continuar para fechar o diálogo
        Button btnContinuar = dialogView.findViewById(R.id.buttonErrdoa);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showErrorCopiarDialog() {
        // Inflate o layout do diálogo de erro
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_errocodigo_barras, null);

        // Cria o dialog a partir do layout inflado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Clique no botão de continuar para fechar o diálogo
        Button btnContinuar = dialogView.findViewById(R.id.buttonErrco);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }





}
