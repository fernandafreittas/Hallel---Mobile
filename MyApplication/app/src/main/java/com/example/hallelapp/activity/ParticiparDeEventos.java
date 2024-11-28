package com.example.hallelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallelapp.MainActivity;
import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.htpp.HttpMembro;
import com.example.hallelapp.model.CartaoCredito;
import com.example.hallelapp.model.InformacoesDaSessao;
import com.example.hallelapp.payload.requerimento.ParticiparEventosRequest;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.tools.ObterInformacoesDaSecao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParticiparDeEventos extends AppCompatActivity {


    InformacoesDaSessao informacoesDeLogin;

    ObterInformacoesDaSecao obterInformacoesDaSecao;

    private android.app.AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_participar_de_eventos);



        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");

        HttpMain requisicao = new HttpMain();
        HttpMembro requisicaoMembro = new HttpMembro();



        obterInformacoesDaSecao = new ObterInformacoesDaSecao(this);


        try {
            informacoesDeLogin = obterInformacoesDaSecao.obterDadosSalvos();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        EditText txtnome = findViewById(R.id.inputNome);
        EditText txtEmail = findViewById(R.id.inputEmail);
        EditText txtCpf = findViewById(R.id.inputCPF);
        EditText txtIdade = findViewById(R.id.inputIdade);
        //cartao
        EditText txtNomeTitular = findViewById(R.id.nome_titular);
        EditText txtNumeroCartao = findViewById(R.id.numero_cartao);
        EditText txtValidade = findViewById(R.id.input_validade);
        EditText txtCVC = findViewById(R.id.inputCVC);
        EditText txtEndereco = findViewById(R.id.inputEndereço);
        Button btnParticipar = findViewById(R.id.button5);


        ParticiparEventosRequest participarEventosRequest = new ParticiparEventosRequest();



        //realiza a inscrição

        btnParticipar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showLoadingDialog();
                participarEventosRequest.setIdEvento(evento.getId());
                participarEventosRequest.setId(informacoesDeLogin.getId());
                participarEventosRequest.setNome(txtnome.getText().toString());
                participarEventosRequest.setEmail(txtEmail.getText().toString());
                participarEventosRequest.setCpf(txtCpf.getText().toString());
                participarEventosRequest.setIdade(Integer.parseInt(txtIdade.getText().toString()));

                CartaoCredito cartaoCredito = new CartaoCredito();
                cartaoCredito.setNomeTitular(txtNomeTitular.getText().toString());
                cartaoCredito.setNumeroCartao(txtNumeroCartao.getText().toString());



                // Supondo que validade.getText().toString() retorna a data no formato mm/yy
                String validade2 = txtValidade.getText().toString(); // Exemplo: "05/24"

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


                cartaoCredito.setEndereco(txtEndereco.getText().toString());
                participarEventosRequest.setCartaoCredito(cartaoCredito);




                System.out.println("tokeeeeeeen : "+informacoesDeLogin.getToken());
                System.out.println(participarEventosRequest.toString());


                    requisicao.ParticiparDeEvento(participarEventosRequest, new HttpMain.HttpCallback() {


                        @Override
                        public void onSuccess(String response) {
                            hideLoadingDialog();
                            showSuccessDialog();

                        }

                        @Override
                        public void onFailure(IOException e) {
                            hideLoadingDialog();
                            showErrorParticiparDialog();
                        }
                    });





            }
        });





        // Adiciona formatação ao campo de CPF
        txtCpf.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Não precisa fazer nada aqui
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String digitsOnly = s.toString().replaceAll("[^\\d]", ""); // Remove qualquer coisa que não seja dígito
                String formatted = "";

                // Aplica a formatação do CPF
                if (digitsOnly.length() <= 3) {
                    formatted = digitsOnly;
                } else if (digitsOnly.length() <= 6) {
                    formatted = digitsOnly.substring(0, 3) + "." + digitsOnly.substring(3);
                } else if (digitsOnly.length() <= 9) {
                    formatted = digitsOnly.substring(0, 3) + "." + digitsOnly.substring(3, 6) + "." + digitsOnly.substring(6);
                } else if (digitsOnly.length() <= 11) {
                    formatted = digitsOnly.substring(0, 3) + "." + digitsOnly.substring(3, 6) + "." + digitsOnly.substring(6, 9) + "-" + digitsOnly.substring(9);
                }

                isUpdating = true;
                txtCpf.setText(formatted);
                txtCpf.setSelection(formatted.length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Não precisa fazer nada aqui
            }
        });




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

    private void showErrorParticiparDialog() {
        // Inflate o layout do diálogo de erro
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_erro_deletarlocal, null);

        // Cria o dialog a partir do layout inflado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Clique no botão de continuar para fechar o diálogo
        Button btnContinuar = dialogView.findViewById(R.id.buttonErrDEvntloc);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showSuccessDialog() {
        // Inflate o layout do diálogo de sucesso
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_particevento_concluida, null);

        // Cria o dialog a partir do layout inflado
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setView(dialogView);

        androidx.appcompat.app.AlertDialog dialog = builder.create();

        // Clique no botão de continuar para redirecionar à página de login ou outra ação
        Button btnContinuar = dialogView.findViewById(R.id.buttonParE);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(ParticiparDeEventos.this, MainActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }


}