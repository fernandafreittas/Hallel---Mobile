package com.example.hallelapp.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallelapp.R;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;

public class EventosAdmActivity extends AppCompatActivity {

    private AlertDialog loadingDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eventos_adm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AuthenticationResponse authenticationResponse = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");

        Button btnAddEvento = findViewById(R.id.buttonAddEvento);
        Button btnEditEvento = findViewById(R.id.buttonEditEvento);
        Button btnEventosArquivados = findViewById(R.id.buttonEventosArquivados);
        Button btnEventosVoluntarios = findViewById(R.id.buttonEventosVoluntarios);
        Button btnLocais = findViewById(R.id.buttonEventosLocais);
        Button btnParticipantes = findViewById(R.id.buttonParticipantes);

        btnAddEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                Intent intent = new Intent(EventosAdmActivity.this, CreateEventosActivity.class);
                intent.putExtra("informaçõesADM", authenticationResponse);
                startActivity(intent);
                hideLoadingDialog();
            }
        });

        btnEditEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                Intent intent = new Intent(EventosAdmActivity.this, VizualizarEventosEditActivity.class);
                intent.putExtra("informaçõesADM", authenticationResponse);
                startActivity(intent);
                hideLoadingDialog();
            }
        });

        btnEventosArquivados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                Intent intent = new Intent(EventosAdmActivity.this, EventosArquivadosActivity.class);
                intent.putExtra("informaçõesADM", authenticationResponse);
                startActivity(intent);
                hideLoadingDialog();
            }
        });

        btnEventosVoluntarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                Intent intent = new Intent(EventosAdmActivity.this, VizualizarEventosVoluntario.class);
                intent.putExtra("informaçõesADM", authenticationResponse);
                startActivity(intent);
                hideLoadingDialog();
            }
        });

        btnLocais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                Intent intent = new Intent(EventosAdmActivity.this, ListLocaisActivity.class);
                intent.putExtra("informaçõesADM", authenticationResponse);
                startActivity(intent);
                hideLoadingDialog();
            }
        });

        btnParticipantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                Intent intent = new Intent(EventosAdmActivity.this, VizualizarEventosParticipantes.class);
                intent.putExtra("informaçõesADM", authenticationResponse);
                startActivity(intent);
                hideLoadingDialog();
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
