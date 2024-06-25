package com.example.hallelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallelapp.R;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;

public class FormDoacoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_doacoes);


        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");


        Button continuar = findViewById(R.id.btncontinuarform);
        EditText txtnome = findViewById(R.id.inputNomeform);
        EditText txtemail = findViewById(R.id.inputEmailform);


        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = txtnome.getText().toString();
                String email = txtemail.getText().toString();

                Intent intent = new Intent(FormDoacoesActivity.this,DirecionamentoDoacaoActivity.class);
                intent.putExtra("evento", evento); // Adiciona o objeto evento como um extra
                intent.putExtra("nome", nome);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });





    }
}