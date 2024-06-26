package com.example.hallelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.MainActivity;
import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.model.CartaoCredito;
import com.example.hallelapp.payload.requerimento.DoacaoDinheiroEventoReq;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class PagamentoCartaoActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento_cartao);

        // Recuperar o objeto evento da intent
        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");
        DoacaoDinheiroEventoReq doacaoDinheiroEventoReq = (DoacaoDinheiroEventoReq) getIntent().getSerializableExtra("doacao");


        HttpMain requisicao = new HttpMain();


        RadioButton RbtnCartaoCredito = findViewById(R.id.radiocartaocredt);
        RadioButton RbtncartaoDebito = findViewById(R.id.radiocartaodebt);
        Switch renovacao = findViewById(R.id.switchautomatico);
        EditText nomeTitula = findViewById(R.id.nometitularcartao);
        EditText numeroCartao = findViewById(R.id.numerocart);
        EditText validade = findViewById(R.id.input_validade);
        EditText CVC = findViewById(R.id.input_cvc);
        EditText endereco = findViewById(R.id.input_endereco);
        Button continuar = findViewById(R.id.btncontinuarform);




        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doacaoDinheiroEventoReq.setMensalmente(renovacao.isChecked());

                if(RbtncartaoDebito.isChecked()){
                    doacaoDinheiroEventoReq.setFormaDePagamento("cartão de debito");

                }
                if(RbtnCartaoCredito.isChecked()){
                    doacaoDinheiroEventoReq.setFormaDePagamento("cartao de credito");
                }

                CartaoCredito cartaoCredito = new CartaoCredito();

                cartaoCredito.setNomeTitular(nomeTitula.getText().toString());
                cartaoCredito.setNumeroCartao(numeroCartao.getText().toString());



                // Supondo que validade.getText().toString() retorna a data no formato mm/yy
                String validade2 = validade.getText().toString(); // Exemplo: "05/24"

// Adiciona o dia "01" no início da string
                String dataString = "01/" + validade2;

// Separa os componentes da data
                String[] dataParts = dataString.split("/");

// Formata a data no formato dd/mm/yyyy
                dataString = dataParts[0] + "/" + dataParts[1] + "/20" + dataParts[2];

                System.out.println(dataString );


                dataString = dataString.replace("/","-");
                System.out.println("dataString: " + dataString);



                cartaoCredito.setDataValidadeCartao(dataString);

                // Teste de saída
                System.out.println(cartaoCredito.getDataValidadeCartao());


                cartaoCredito.setCvc(Integer.valueOf(CVC.getText().toString()));

                doacaoDinheiroEventoReq.setCartaoCredito(cartaoCredito);

                Date dataAtual = new Date();

                // Define o formato da data desejado
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

                // Converte a data para string
                String dataComoString = formato.format(dataAtual);

                doacaoDinheiroEventoReq.setDia(dataComoString);



                requisicao.DoarDinheiroEvento(evento.getId(), doacaoDinheiroEventoReq, new HttpMain.HttpCallback() {
                    @Override
                    public void onSuccess(String response) {
                        System.out.println(doacaoDinheiroEventoReq.toString());
                        System.out.println("deu certo");
                        Intent intent = new Intent(PagamentoCartaoActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                });


            }
        });






    }






}



