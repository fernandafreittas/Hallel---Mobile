package com.example.hallelapp.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.MainActivity;
import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.htpp.HttpMembro;
import com.example.hallelapp.payload.requerimento.SeVoluntariarEventoReq;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;

import java.io.IOException;

public class FormVoluntarioActivity extends AppCompatActivity {

    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_servoluntario);

        HttpMain requisicao = new HttpMain();

        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");

        EditText txtNome = findViewById(R.id.inputNome);
        EditText txtEmail = findViewById(R.id.inputEmail);
        EditText txtCpf = findViewById(R.id.inputCPF);
        EditText txtTelefone = findViewById(R.id.inputIdade);
        EditText txtPreferencia = findViewById(R.id.editText);
        RadioButton rbtnPreferencia = findViewById(R.id.radioButton);
        Button btnConfirmar = findViewById(R.id.btncontinuarcartao);

        SeVoluntariarEventoReq seVoluntariarEventoReq = new SeVoluntariarEventoReq();

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                seVoluntariarEventoReq.setIdEvento(evento.getId());
                seVoluntariarEventoReq.setNome(txtNome.getText().toString());
                seVoluntariarEventoReq.setEmail(txtEmail.getText().toString());
                seVoluntariarEventoReq.setCpf(txtCpf.getText().toString());
                seVoluntariarEventoReq.setNumeroDeTelefone(txtTelefone.getText().toString());
                seVoluntariarEventoReq.setPreferencia(txtPreferencia.getText().toString());

                if (rbtnPreferencia.isChecked()) {
                    seVoluntariarEventoReq.setPreferencia("Não tenho preferência");
                }

                requisicao.SeVoluntariarEmEvento(seVoluntariarEventoReq, new HttpMembro.HttpCallback() {
                    @Override
                    public void onSuccess(String response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println(seVoluntariarEventoReq.toString());
                                System.out.println("deu certo !");
                                hideLoadingDialog();
                                showSuccessDialog();
                            }
                        });
                    }

                    @Override
                    public void onFailure(IOException e) {
                        System.out.println("deu errado !");
                        hideLoadingDialog();
                    }
                });
            }
        });

        // Adiciona formatação ao campo de telefone
        txtTelefone.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;
            private String oldString = "";

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

                if (digitsOnly.length() <= 2) {
                    formatted = "(" + digitsOnly;
                } else if (digitsOnly.length() <= 7) {
                    formatted = "(" + digitsOnly.substring(0, 2) + ")" + digitsOnly.substring(2);
                } else if (digitsOnly.length() <= 11) {
                    formatted = "(" + digitsOnly.substring(0, 2) + ")" + digitsOnly.substring(2, 7) + "-" + digitsOnly.substring(7);
                } else {
                    formatted = "(" + digitsOnly.substring(0, 2) + ")" + digitsOnly.substring(2, 7) + "-" + digitsOnly.substring(7, 11);
                }

                isUpdating = true;
                txtTelefone.setText(formatted);
                txtTelefone.setSelection(formatted.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    private void showSuccessDialog() {
        // Inflate o layout do diálogo de sucesso
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_pedido_servoluntario, null);

        // Cria o dialog a partir do layout inflado
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setView(dialogView);

        androidx.appcompat.app.AlertDialog dialog = builder.create();

        // Clique no botão de continuar para redirecionar à página de login ou outra ação
        Button btnContinuar = dialogView.findViewById(R.id.buttonS);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(FormVoluntarioActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dialog.show();
    }


}
