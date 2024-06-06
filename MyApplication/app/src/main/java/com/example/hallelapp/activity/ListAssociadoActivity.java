package com.example.hallelapp.activity;

import android.content.Context;
import android.content.Intent;
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

import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpAdm;
import com.example.hallelapp.model.Associado;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListAssociadoActivity extends AppCompatActivity {

    Context context = this;

    List<Associado> listaDeAssociados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_associado);

        AuthenticationResponse authenticationResponse = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");

        HttpAdm requisicao = new HttpAdm();

        TableLayout tableLayout = findViewById(R.id.tableLayoutAssociados);
        Button btnAZ = findViewById(R.id.buttonAzAssociados);
        Button btnZA = findViewById(R.id.button11);

        requisicao.ListAssociados(authenticationResponse, new HttpAdm.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                Type listType = new TypeToken<List<Associado>>() {}.getType();
                List<Associado> responseAssociado = new Gson().fromJson(response, listType);

                listaDeAssociados = responseAssociado;

                // Agora você tem uma lista de associados
                System.out.println("Nomes dos associados:");
                for (Associado associado : responseAssociado) {
                    System.out.println(associado.getEmail());
                    System.out.println(associado.getNome());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (Associado associado : responseAssociado) {
                            TextView textView = new TextView(context);
                            textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                            textView.setText(associado.getNome());
                            textView.setTextColor(getResources().getColor(R.color.cordetextohallel)); // Defina a cor do texto conforme necessário
                            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Defina o tamanho do texto conforme necessário
                            textView.setPadding(8, 8, 8, 8); // Defina o preenchimento conforme necessário
                            textView.setTypeface(ResourcesCompat.getFont(context, R.font.inter_semibold)); // Defina o tipo de fonte conforme necessário

                            // Adicionar o OnClickListener para abrir a nova Activity com detalhes do associado
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ListAssociadoActivity.this, DadosAssociadoActivity.class);
                                    System.out.println("associado"+associado.getNome()+"   "+ associado.toString());

                                    intent.putExtra("associado", associado);
                                    intent.putExtra("informaçõesADM", authenticationResponse);
                                    startActivity(intent);
                                }
                            });

                            tableLayout.addView(textView);
                        }
                    }
                });
            }

            @Override
            public void onFailure(IOException e) {
                // Tratamento de falha
            }
        });

        btnAZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Limpar todas as visualizações
                tableLayout.removeAllViews();

                // Adicionar novamente o TextView estático
                TextView staticTextView = new TextView(ListAssociadoActivity.this);
                staticTextView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                staticTextView.setText("Nome do associado");
                staticTextView.setTextColor(getResources().getColor(R.color.cortexto2)); // Defina a cor do texto conforme necessário
                staticTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Defina o tamanho do texto conforme necessário
                staticTextView.setPadding(8, 8, 8, 8); // Defina o preenchimento conforme necessário
                staticTextView.setTypeface(ResourcesCompat.getFont(context, R.font.inter_extrabold)); // Defina o tipo de fonte conforme necessário
                tableLayout.addView(staticTextView);

                Collections.sort(listaDeAssociados, (a1, a2) -> a1.getNome().compareTo(a2.getNome()));

                for (Associado associado : listaDeAssociados) {
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    textView.setText(associado.getNome());
                    textView.setTextColor(getResources().getColor(R.color.cordetextohallel)); // Defina a cor do texto conforme necessário
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Defina o tamanho do texto conforme necessário
                    textView.setPadding(8, 8, 8, 8); // Defina o preenchimento conforme necessário
                    textView.setTypeface(ResourcesCompat.getFont(context, R.font.inter_semibold)); // Defina o tipo de fonte conforme necessário

                    // Adicionar o OnClickListener para abrir a nova Activity com detalhes do associado
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ListAssociadoActivity.this, DadosAssociadoActivity.class);
                            intent.putExtra("associado", associado);
                            intent.putExtra("informaçõesADM", authenticationResponse);

                            startActivity(intent);
                        }
                    });

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
                TextView staticTextView = new TextView(ListAssociadoActivity.this);
                staticTextView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                staticTextView.setText("Nome do Associado");
                staticTextView.setTextColor(getResources().getColor(R.color.cortexto2)); // Defina a cor do texto conforme necessário
                staticTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Defina o tamanho do texto conforme necessário
                staticTextView.setPadding(8, 8, 8, 8); // Defina o preenchimento conforme necessário
                staticTextView.setTypeface(ResourcesCompat.getFont(context, R.font.inter_extrabold)); // Defina o tipo de fonte conforme necessário
                tableLayout.addView(staticTextView);

                Collections.sort(listaDeAssociados, (a1, a2) -> a2.getNome().compareTo(a1.getNome()));

                for (Associado associado : listaDeAssociados) {
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    textView.setText(associado.getNome());
                    textView.setTextColor(getResources().getColor(R.color.cordetextohallel)); // Defina a cor do texto conforme necessário
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Defina o tamanho do texto conforme necessário
                    textView.setPadding(8, 8, 8, 8); // Defina o preenchimento conforme necessário
                    textView.setTypeface(ResourcesCompat.getFont(context, R.font.inter_semibold)); // Defina o tipo de fonte conforme necessário

                    // Adicionar o OnClickListener para abrir a nova Activity com detalhes do associado
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ListAssociadoActivity.this, DadosAssociadoActivity.class);
                            intent.putExtra("associado", associado);
                            intent.putExtra("informaçõesADM", authenticationResponse);
                            startActivity(intent);
                        }
                    });

                    tableLayout.addView(textView);
                }
            }
        });
    }
}
