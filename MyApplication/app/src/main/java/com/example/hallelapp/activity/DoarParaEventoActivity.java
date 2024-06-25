package com.example.hallelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.R;
import com.example.hallelapp.payload.requerimento.DoacaoDinheiroEventoReq;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;

import java.util.Locale;

public class DoarParaEventoActivity extends AppCompatActivity {

    private Button btnContinuarForm, btn20, btn30, btn50, btn80, btn100;
    private EditText editTextNumber, inptValor;
    private RadioButton rdioPontualmente, rdioMensalmente, rdioAnualmente;
    DoacaoDinheiroEventoReq doacaoDinheiroEventoReq;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doar_pevento);

        // Recuperar o objeto evento da intent
        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");
        String nome = (String) getIntent().getSerializableExtra("nome");
        String email = (String) getIntent().getSerializableExtra("email");


        // Inicialização dos componentes da UI
        btnContinuarForm = findViewById(R.id.btncontinuarform);
        btn20 = findViewById(R.id.btn20);
        btn30 = findViewById(R.id.btn30);
        btn50 = findViewById(R.id.btn50);
        btn80 = findViewById(R.id.btn80);
        btn100 = findViewById(R.id.btn100);
        inptValor = findViewById(R.id.inptValor);

       doacaoDinheiroEventoReq = new DoacaoDinheiroEventoReq();



// Configuração dos listeners para os botões
        btn20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValueToInput(20.00);
            }
        });

        btn30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValueToInput(30.00);
            }
        });

        btn50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValueToInput(50.00);
            }
        });

        btn80.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValueToInput(80.00);
            }
        });

        btn100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValueToInput(100.00);
            }
        });


        btnContinuarForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doacaoDinheiroEventoReq.setNomeDoador(nome);
                doacaoDinheiroEventoReq.setEmailDoador(email);
                doacaoDinheiroEventoReq.setAnualmente(false);
                doacaoDinheiroEventoReq.setMensalmente(false);
                setDonationValue();

                Intent intent = new Intent(DoarParaEventoActivity.this,PagamentoEventoActivity.class);
                intent.putExtra("evento", evento); // Adiciona o objeto evento como um extra
                intent.putExtra("doacao", doacaoDinheiroEventoReq);
                startActivity(intent);

            }
        });



    }


    // Método para definir o valor no EditText
    private void setValueToInput(double value) {
        inptValor.setText(String.format(Locale.getDefault(), "%.2f", value));
    }


    private void setDonationValue() {
        String valueString = inptValor.getText().toString().replace(",", ".");
        double value = Double.parseDouble(valueString);
        doacaoDinheiroEventoReq.setValorDoado(value);
    }

}
