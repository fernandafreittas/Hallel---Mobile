package com.example.hallelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PagamentoCartaoActivity  extends AppCompatActivity {


    private android.app.AlertDialog loadingDialog;

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



                if (nomeTitula.getText().toString().isEmpty() ||
                        numeroCartao.getText().toString().isEmpty() ||
                        validade.getText().toString().isEmpty() ||
                        CVC.getText().toString().isEmpty() ||
                        endereco.getText().toString().isEmpty() ||
                        (!RbtnCartaoCredito.isChecked() && !RbtncartaoDebito.isChecked())) {

                    // Exibir um Toast avisando para preencher todos os campos
                    Toast.makeText(PagamentoCartaoActivity.this, "Por favor, preencha todos os campos e selecione uma opção de pagamento", Toast.LENGTH_SHORT).show();
                    return; // Interrompe o processamento se algum campo estiver vazio ou nenhum RadioButton estiver selecionado
                }

                // Verificação da validade do cartão
                String expiry = validade.getText().toString();
                if (expiry.length() == 5) {
                    String[] parts = expiry.split("/");
                    int month = Integer.parseInt(parts[0]);
                    int year = Integer.parseInt(parts[1]) + 2000;

                    // Verificar se o mês está entre 1 e 12
                    if (month < 1 || month > 12) {
                        Toast.makeText(PagamentoCartaoActivity.this, "Data inválida. Por favor, insira um mês entre 01 e 12.", Toast.LENGTH_SHORT).show();

                        return;
                    }

                    // Verificar se a data é futura
                    Calendar today = Calendar.getInstance();
                    Calendar expiryDate = Calendar.getInstance();
                    expiryDate.set(Calendar.MONTH, month - 1);
                    expiryDate.set(Calendar.YEAR, year);

                    if (expiryDate.before(today)) {
                        Toast.makeText(PagamentoCartaoActivity.this, "Data inválida. O cartão expirou.", Toast.LENGTH_SHORT).show();

                        return;
                    }
                } else {
                    // Caso o formato não seja correto
                    Toast.makeText(PagamentoCartaoActivity.this, "Data inválida. Formato esperado: MM/YY.", Toast.LENGTH_SHORT).show();

                    return;
                }


                showLoadingDialog();



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

                        runOnUiThread(()->{
                            hideLoadingDialog();
                           showDialogSucesso();

                        });


                    }

                    @Override
                    public void onFailure(IOException e) {

                        System.out.println("erro na hora da doação ");

                        runOnUiThread(()->{
                            hideLoadingDialog();
                            showDialogErro();
                        });


                    }
                });


            }
        });


        validade.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private Calendar cal = Calendar.getInstance();
            private boolean isUpdating = false; // Variável de controle para evitar múltiplos toasts

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) return; // Ignora a atualização se já estiver atualizando

                String clean = s.toString().replaceAll("[^\\d]", "");

                if (clean.length() < 4) {
                    // Adiciona apenas o '/' quando houver pelo menos 2 dígitos
                    if (clean.length() >= 2) {
                        clean = clean.substring(0, 2) + "/" + clean.substring(2);
                    }
                } else {
                    // Formata o texto em MM/YY
                    String month = clean.substring(0, 2);
                    String year = clean.substring(2, 4);
                    clean = month + "/" + year;
                }

                // Atualiza o texto apenas se houve alteração
                if (!clean.equals(current)) {
                    isUpdating = true; // Marca como atualizando
                    current = clean;
                    validade.setText(current);
                    validade.setSelection(current.length());
                    isUpdating = false; // Marca como não atualizando
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String expiry = validade.getText().toString();
                if (expiry.length() == 5) {
                    try {
                        String[] parts = expiry.split("/");
                        int month = Integer.parseInt(parts[0]);
                        int year = Integer.parseInt(parts[1]) + 2000;

                        // Verificar se o mês está entre 1 e 12
                        if (month < 1 || month > 12) {
                            Toast.makeText(PagamentoCartaoActivity.this, "Data inválida. Por favor, insira um mês entre 01 e 12.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Verificar se a data é futura
                            Calendar today = Calendar.getInstance();
                            Calendar expiryDate = Calendar.getInstance();
                            expiryDate.set(Calendar.MONTH, month - 1);
                            expiryDate.set(Calendar.YEAR, year);

                            if (expiryDate.before(today)) {
                                Toast.makeText(PagamentoCartaoActivity.this, "Data inválida. O cartão expirou.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(PagamentoCartaoActivity.this, "Data inválida. Formato esperado: MM/YY.", Toast.LENGTH_SHORT).show();
                    }
                }
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


    private void showDialogSucesso(){

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_pagamentosucesso, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        Button btnContinuar = dialogView.findViewById(R.id.buttonEPG);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(PagamentoCartaoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();



    }

    private void showLoadingDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.loading_screen, null);
        builder.setView(dialogView);
        builder.setCancelable(false);

        loadingDialog = builder.create();
        loadingDialog.show();
    }

    private void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }





}



