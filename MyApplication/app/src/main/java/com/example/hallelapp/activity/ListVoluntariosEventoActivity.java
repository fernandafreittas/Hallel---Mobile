package com.example.hallelapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpAdm;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.example.hallelapp.payload.resposta.MembroResponse;
import com.example.hallelapp.payload.resposta.SeVoluntariarEventoResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListVoluntariosEventoActivity extends AppCompatActivity {


    Context context = this;

    List<String> listaDeNomes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_voluntarios_evento);




        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");
        AuthenticationResponse authenticationResponse = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");


        HttpAdm requisicao = new HttpAdm();


        TableLayout tableLayout = findViewById(R.id.tableLayout);
        Button btnAZ = findViewById(R.id.buttonAz);
        Button btnZA = findViewById(R.id.button11);


        requisicao.ListVoluntarios(evento.getId(),authenticationResponse, new HttpAdm.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                Type listType = new TypeToken<List<SeVoluntariarEventoResponse>>() {}.getType();
                List<SeVoluntariarEventoResponse> responseMembro = new Gson().fromJson(response, listType);


                List<String> nomesDosMembros = new ArrayList<>();

                for (SeVoluntariarEventoResponse membro : responseMembro) {
                    nomesDosMembros.add(membro.getNome());
                }

                listaDeNomes = nomesDosMembros;

                // Agora você tem uma lista de nomes dos Voluntarios
                System.out.println("Nomes dos Voluntarios:");
                for (String nome : nomesDosMembros) {
                    System.out.println(nome);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (String membro : nomesDosMembros) {
                            TextView textView = new TextView(context);
                            textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                            textView.setText(membro);
                            textView.setTextColor(getResources().getColor(R.color.cordetextohallel)); // Defina a cor do texto conforme necessário
                            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Defina o tamanho do texto conforme necessário
                            textView.setPadding(8, 8, 8, 8); // Defina o preenchimento conforme necessário
                            textView.setTypeface(ResourcesCompat.getFont(context, R.font.inter_semibold)); // Defina o tipo de fonte conforme necessário
                            tableLayout.addView(textView);
                        }
                    }
                });

            }

            @Override
            public void onFailure(IOException e) {

            }
        });


        btnAZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Limpar todas as visualizações
                tableLayout.removeAllViews();

                // Adicionar novamente o TextView estático
                TextView staticTextView = new TextView(ListVoluntariosEventoActivity.this);
                staticTextView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                staticTextView.setText("Nome dos Voluntarios");
                staticTextView.setTextColor(getResources().getColor(R.color.cortexto2)); // Defina a cor do texto conforme necessário
                staticTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Defina o tamanho do texto conforme necessário
                staticTextView.setPadding(8, 8, 8, 8); // Defina o preenchimento conforme necessário
                staticTextView.setTypeface(ResourcesCompat.getFont(context, R.font.inter_extrabold)); // Defina o tipo de fonte conforme necessário
                tableLayout.addView(staticTextView);

                Collections.sort(listaDeNomes);


                for (String membro : listaDeNomes) {
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    textView.setText(membro);
                    textView.setTextColor(getResources().getColor(R.color.cordetextohallel)); // Defina a cor do texto conforme necessário
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Defina o tamanho do texto conforme necessário
                    textView.setPadding(8, 8, 8, 8); // Defina o preenchimento conforme necessário
                    textView.setTypeface(ResourcesCompat.getFont(context, R.font.inter_semibold)); // Defina o tipo de fonte conforme necessário
                    tableLayout.addView(textView);
                }


            }
        });

        btnZA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Limpar todas as visualizações
                tableLayout.removeAllViews();

                // Adicionar novamente o TextView estático
                TextView staticTextView = new TextView(ListVoluntariosEventoActivity.this);
                staticTextView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                staticTextView.setText("Nome dos Voluntarios");
                staticTextView.setTextColor(getResources().getColor(R.color.cortexto2)); // Defina a cor do texto conforme necessário
                staticTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Defina o tamanho do texto conforme necessário
                staticTextView.setPadding(8, 8, 8, 8); // Defina o preenchimento conforme necessário
                staticTextView.setTypeface(ResourcesCompat.getFont(context, R.font.inter_extrabold)); // Defina o tipo de fonte conforme necessário
                tableLayout.addView(staticTextView);


                Collections.sort(listaDeNomes);
                Collections.sort(listaDeNomes, Collections.reverseOrder());


                for (String membro : listaDeNomes) {
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    textView.setText(membro);
                    textView.setTextColor(getResources().getColor(R.color.cordetextohallel)); // Defina a cor do texto conforme necessário
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Defina o tamanho do texto conforme necessário
                    textView.setPadding(8, 8, 8, 8); // Defina o preenchimento conforme necessário
                    textView.setTypeface(ResourcesCompat.getFont(context, R.font.inter_semibold)); // Defina o tipo de fonte conforme necessário
                    tableLayout.addView(textView);
                }


            }
        });



    }
}