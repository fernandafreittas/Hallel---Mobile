package com.example.hallelapp.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.R;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.example.hallelapp.payload.resposta.MembroResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InformacoesDoMembroActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dados_membro);


        MembroResponse membro = (MembroResponse) getIntent().getSerializableExtra("membro");

        TextView txtNome = findViewById(R.id.nomeCompletoMembro);
        TextView txtTelefone = findViewById(R.id.numeroTelMembro);
        TextView txtEmail = findViewById(R.id.emailMembro);
        TextView txtCPF = findViewById(R.id.cpfmembro);
        TextView txtDataNascimento = findViewById(R.id.dataNascMembro);

        System.out.println(membro.toString());


        txtNome.setText(membro.getNome());
        txtTelefone.setText(membro.getTelefone());
        txtEmail.setText(membro.getEmail());
        txtCPF.setText(membro.getCpf());

        if(membro.getDataNascimento()!=null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Você pode usar outro padrão se preferir

            Date dataNascimento = membro.getDataNascimento();


            String dataNascimentoFormatada = dateFormat.format(dataNascimento);

            txtDataNascimento.setText(dataNascimentoFormatada);
        }else txtDataNascimento.setText(null);



    }




    }
