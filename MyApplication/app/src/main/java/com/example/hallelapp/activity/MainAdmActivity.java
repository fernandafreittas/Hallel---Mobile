package com.example.hallelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallelapp.R;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;

public class MainAdmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_adm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AuthenticationResponse authenticationResponse = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");


        Button btnEventos = findViewById(R.id.btnEventos);

        Button btnMembros  = findViewById(R.id.btnmembros);




        btnEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdmActivity.this, EventosAdmActivity.class);
                intent.putExtra("informaçõesADM", authenticationResponse);
                startActivity(intent);

            }
        });

        btnMembros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdmActivity.this, ListMemberActivity.class);
                intent.putExtra("informaçõesADM", authenticationResponse);
                startActivity(intent);
            }
        });





    }
}