package com.example.hallelapp.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.R;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;

public class DoarParaEventoActivity extends AppCompatActivity {

    private Button btnContinuarCartao, btn20, btn30, btn50, btn80, btn100;
    private EditText editTextNumber, inptValor;
    private RadioButton rdioPontualmente, rdioMensalmente, rdioAnualmente;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doar_pevento);

        // Recuperar o objeto evento da intent
        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");

        // Inicialização dos componentes da UI
        btnContinuarCartao = findViewById(R.id.btncontinuarcartao);
        btn20 = findViewById(R.id.btn20);
        btn30 = findViewById(R.id.btn30);
        btn50 = findViewById(R.id.btn50);
        btn80 = findViewById(R.id.btn80);
        btn100 = findViewById(R.id.btn100);
        editTextNumber = findViewById(R.id.editTextNumber);
        inptValor = findViewById(R.id.inptValor);
        rdioPontualmente = findViewById(R.id.rdioPontualmente);
        rdioMensalmente = findViewById(R.id.rdioMensalmente);
        rdioAnualmente = findViewById(R.id.rdioAnualmente);




    }

}
