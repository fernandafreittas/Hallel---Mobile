package com.example.hallelapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.payload.requerimento.DoacaoDinheiroEventoReq;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;

import java.io.IOException;

public class PagamentoPixActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento_pix);

        // Recuperar o objeto evento da intent
        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");
        DoacaoDinheiroEventoReq doacaoDinheiroEventoReq = (DoacaoDinheiroEventoReq) getIntent().getSerializableExtra("doacao");


        HttpMain requisicao = new HttpMain();
        Button btnCopiarPix = findViewById(R.id.btncopiarPix);

        doacaoDinheiroEventoReq.setCartaoCredito(null);
        doacaoDinheiroEventoReq.setFormaDePagamento("PIX");

        requisicao.DoarDinheiroEventoPixBoleto(evento.getId(), doacaoDinheiroEventoReq, new HttpMain.HttpCallback() {
            @Override
            public void onSuccess(String response) {


                System.out.println(doacaoDinheiroEventoReq.toString());
                System.out.println("deu certo");

            }

            @Override
            public void onFailure(IOException e) {
                showDialogErro();

            }
        });



        btnCopiarPix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        Toast.makeText(PagamentoPixActivity.this, "Pix copiado para sua area de transferencia!", Toast.LENGTH_LONG).show();



            }
        });



    }

    private void showDialogErro(){

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_erropagamento,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        Button btnContinuar = dialog.findViewById(R.id.buttonErrPa);
        if (btnContinuar == null) {
            Log.e("PagamentoCartaoActivity", "Botão 'buttonErrPa' não encontrado no layout.");
        } else {
            btnContinuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();



    }




}
