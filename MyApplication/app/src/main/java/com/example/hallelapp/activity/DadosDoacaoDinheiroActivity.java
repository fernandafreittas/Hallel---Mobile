package com.example.hallelapp.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpAdm;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.model.DoacaoObjetosEventos;
import com.example.hallelapp.payload.requerimento.AlteraRecebimentoRequest;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.example.hallelapp.payload.resposta.DoacaoDinheiroEventoResponse;
import com.example.hallelapp.payload.resposta.DoacaoObjetosEventosResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class DadosDoacaoDinheiroActivity extends AppCompatActivity {


    private TextView inputNomeDoador2, inputNumeroCelular2, inputSexoDoador2, inputEmailDoador2;
    private TextView inputItemDoado2, inputInstituicao,inputFormaDePagamento, inputData;


    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dados_doacao_dinheiro);

        DoacaoDinheiroEventoResponse doacaoDinehiro = (DoacaoDinheiroEventoResponse) getIntent().getSerializableExtra("doacaoDinheiro");



        System.out.println(doacaoDinehiro.toString());


        // Inicialização dos TextViews com "input" no ID
        inputNomeDoador2 = findViewById(R.id.inputNomeDoador2);
        inputNumeroCelular2 = findViewById(R.id.inputNumeroCelular2);
        inputSexoDoador2 = findViewById(R.id.inputSexoDoador2);
        inputEmailDoador2 = findViewById(R.id.inputEmailDoador2);
        inputItemDoado2 = findViewById(R.id.inputItemDoado2);
        inputInstituicao = findViewById(R.id.inputInstituicao);
        inputFormaDePagamento = findViewById(R.id.textView962);
        inputData = findViewById(R.id.textView88);

        inputNomeDoador2.setText(doacaoDinehiro.getNomeDoador());
        inputFormaDePagamento.setText(doacaoDinehiro.getFormaDePagamento());
        inputItemDoado2.setText(String.valueOf(doacaoDinehiro.getValorDoado()));

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        // Converta a data para String
        String dataString = formato.format(doacaoDinehiro.getDataDoacao());

        inputData.setText(dataString);










    }




}
