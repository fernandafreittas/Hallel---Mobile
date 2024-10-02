package com.example.hallelapp.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpAdm;
import com.example.hallelapp.model.Membro;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.example.hallelapp.payload.resposta.SeVoluntariarEventoResponse;
import com.example.hallelapp.payload.resposta.ValoresEventoResponse;
import com.google.gson.Gson;

import java.io.IOException;

public class DadosParticipanteActivity extends AppCompatActivity {

    // Declaração dos TextViews
    private TextView nomeParticipante;
    private TextView numeroTelefone;
    private TextView emailParticipante;
    private TextView cpfParticipante;
    private TextView idadeParticipante;
    private TextView txtVoluntario;
    private SeVoluntariarEventoResponse voluntario;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dados_participante);

        // Inicialização dos TextViews
        nomeParticipante = findViewById(R.id.nomeParticipante);
        numeroTelefone = findViewById(R.id.numeroTelefone);
        emailParticipante = findViewById(R.id.emailParticipante);
        cpfParticipante = findViewById(R.id.cpfParticipante);
        idadeParticipante = findViewById(R.id.idadeParticipante);
        txtVoluntario = findViewById(R.id.voluntario);


        HttpAdm requisicao = new HttpAdm();


        AuthenticationResponse authenticationResponse = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");
        Membro membro = (Membro) getIntent().getSerializableExtra("membro");
        String id = (String) getIntent().getSerializableExtra("id");


        if (membro != null) {
            nomeParticipante.setText(membro.getNome());
            numeroTelefone.setText(membro.getTelefone());
            emailParticipante.setText(membro.getEmail());
            cpfParticipante.setText(membro.getCpf());
            idadeParticipante.setText(String.valueOf(membro.getIdade()));


            requisicao.ListVoluntarioByEmail(id, membro.getEmail(), authenticationResponse, new HttpAdm.HttpCallback() {
                @Override
                public void onSuccess(String response) {
                    voluntario = new Gson().fromJson(response, SeVoluntariarEventoResponse.class);
                    runOnUiThread(() -> {


                        if(voluntario != null) {
                            txtVoluntario.setText(voluntario.getPreferencia());
                        }
                    });
                }

                @Override
                public void onFailure(IOException e) {

                }
            });



        }





    }



    }
