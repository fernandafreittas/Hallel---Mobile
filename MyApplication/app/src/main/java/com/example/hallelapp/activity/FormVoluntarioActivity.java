package com.example.hallelapp.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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
                        System.out.println(seVoluntariarEventoReq.toString());
                        System.out.println("deu certo !");
                        hideLoadingDialog();
                        Intent intent = new Intent(FormVoluntarioActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(IOException e) {
                        System.out.println("deu errado !");
                        hideLoadingDialog();
                    }
                });
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
}
