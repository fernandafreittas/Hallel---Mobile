package com.example.hallelapp.activity;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ser_voluntario);

        HttpMain requisição = new HttpMain();

        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");


        EditText txtNome = findViewById(R.id.inputNome);
        EditText txtEmail = findViewById(R.id.inputEmail);
        EditText txtCpf = findViewById(R.id.inputCPF);
        EditText txtTelefone = findViewById(R.id.inputIdade);
        EditText txtPreferencia = findViewById(R.id.editText);
        RadioButton rbtnPreferencia = findViewById(R.id.radioButton);
        Button btnconfimar = findViewById(R.id.button5);

        SeVoluntariarEventoReq seVoluntariarEventoReq = new SeVoluntariarEventoReq();


        btnconfimar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                seVoluntariarEventoReq.setIdEvento(evento.getId());
                seVoluntariarEventoReq.setNome(txtNome.getText().toString());
                seVoluntariarEventoReq.setEmail(txtEmail.getText().toString());
                seVoluntariarEventoReq.setCpf(txtCpf.getText().toString());
                seVoluntariarEventoReq.setNumeroDeTelefone(txtTelefone.getText().toString());
                seVoluntariarEventoReq.setPreferencia(txtPreferencia.getText().toString());


                if(rbtnPreferencia.isChecked()){
                    seVoluntariarEventoReq.setPreferencia("Não tenho preferência");
                }

                requisição.SeVoluntariarEmEvento(seVoluntariarEventoReq, new HttpMembro.HttpCallback() {
                    @Override
                    public void onSuccess(String response) {
                        System.out.println( seVoluntariarEventoReq.toString());

                        System.out.println("deu certo !");

                        Intent intent = new Intent(FormVoluntarioActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(IOException e) {
                        System.out.println("deu errado  !");
                    }
                });



            }
        });



    }








}
