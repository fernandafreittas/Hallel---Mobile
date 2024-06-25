package com.example.hallelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.R;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;

public class DirecionamentoDoacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direcionamento_doacao);

        // Recuperar o objeto evento da intent
        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");
        String nome = (String) getIntent().getSerializableExtra("nome");
        String email = (String) getIntent().getSerializableExtra("email");

        Button btnDoacoaDinheiro = findViewById(R.id.btnDoarDinheito);
        Button btnDoacoaObjeto = findViewById(R.id.btnDoarObj);


        btnDoacoaDinheiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DirecionamentoDoacaoActivity.this,DoarParaEventoActivity.class);
                intent.putExtra("evento", evento); // Adiciona o objeto evento como um extra
                intent.putExtra("nome", nome);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        btnDoacoaObjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DirecionamentoDoacaoActivity.this,DoacaoDeObjetosAlimentosActivity.class);
                intent.putExtra("evento", evento); // Adiciona o objeto evento como um extra
                intent.putExtra("nome", nome);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });




    }

    }
