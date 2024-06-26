package com.example.hallelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.R;
import com.example.hallelapp.payload.requerimento.DoacaoDinheiroEventoReq;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;

public class PagamentoEventoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento_evento);

        // Recuperar o objeto evento da intent
        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");
        DoacaoDinheiroEventoReq doacaoDinheiroEventoReq  = (DoacaoDinheiroEventoReq) getIntent().getSerializableExtra("doacao");

        Button alterarValor = findViewById(R.id.button5);
        Button btnCartao = findViewById(R.id.btnCartao);
        Button btnBoleto = findViewById(R.id.btnBoleto);
        Button btnPix = findViewById(R.id.btnPix);
        TextView doador = findViewById(R.id.textView31);
        TextView valor = findViewById(R.id.textView35);


        doador.setText(doacaoDinheiroEventoReq.getNomeDoador());
        valor.setText(String.valueOf(doacaoDinheiroEventoReq.getValorDoado()));




        alterarValor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PagamentoEventoActivity.this,PagamentoCartaoActivity.class);
                intent.putExtra("evento", evento); // Adiciona o objeto evento como um extra
                intent.putExtra("doacao", doacaoDinheiroEventoReq);
                startActivity(intent);
            }
        });

        btnBoleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PagamentoEventoActivity.this,PagamentoBoletoActivity.class);
                intent.putExtra("evento", evento); // Adiciona o objeto evento como um extra
                intent.putExtra("doacao", doacaoDinheiroEventoReq);
                startActivity(intent);
            }
        });


    }

}
