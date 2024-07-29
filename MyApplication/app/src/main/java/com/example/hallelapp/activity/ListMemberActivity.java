package com.example.hallelapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ListMemberActivity extends AppCompatActivity {

    Context context = this;

    List<MembroResponse> listaDeMembros;
    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_member);

        AuthenticationResponse authenticationResponse = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");
        HttpAdm requisicao = new HttpAdm();

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        Button btnAZ = findViewById(R.id.buttonAz);
        Button btnZA = findViewById(R.id.buttonZa);

        showLoadingDialog();
        requisicao.ListMembros(authenticationResponse, new HttpAdm.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                hideLoadingDialog();
                Type listType = new TypeToken<List<MembroResponse>>() {}.getType();
                listaDeMembros = new Gson().fromJson(response, listType);

                List<String> nomesDosMembros = new ArrayList<>();
                for (MembroResponse membro : listaDeMembros) {
                    nomesDosMembros.add(membro.getNome());
                }

                // Agora você tem uma lista de objetos MembroResponse e uma lista de nomes dos membros
                System.out.println("Nomes dos membros:");
                for (String nome : nomesDosMembros) {
                    System.out.println(nome);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (String nome : nomesDosMembros) {
                            TextView textView = new TextView(context);
                            textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                            textView.setText(nome);
                            textView.setTextColor(getResources().getColor(R.color.cordetextohallel));
                            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                            textView.setPadding(8, 8, 8, 8);
                            textView.setTypeface(ResourcesCompat.getFont(context, R.font.inter_semibold));

                            // Adicione o OnClickListener
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    for (MembroResponse membro : listaDeMembros) {
                                        if (membro.getNome().equals(nome)) {
                                            Intent intent = new Intent(ListMemberActivity.this, InformacoesDoMembroActivity.class);
                                            intent.putExtra("membro", membro); // Passa o objeto membro
                                            startActivity(intent);
                                            break;
                                        }
                                    }
                                }
                            });

                            tableLayout.addView(textView);
                        }
                    }
                });
            }

            @Override
            public void onFailure(IOException e) {
                hideLoadingDialog();
                // Tratar falhas de rede aqui
            }
        });

        btnAZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Limpar todas as visualizações
                tableLayout.removeAllViews();

                // Adicionar novamente o TextView estático
                TextView staticTextView = new TextView(ListMemberActivity.this);
                staticTextView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                staticTextView.setText("Nome do membro");
                staticTextView.setTextColor(getResources().getColor(R.color.cortexto2));
                staticTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                staticTextView.setPadding(8, 8, 8, 8);
                staticTextView.setTypeface(ResourcesCompat.getFont(context, R.font.inter_extrabold));
                tableLayout.addView(staticTextView);

                Collections.sort(listaDeMembros, (a, b) -> a.getNome().compareTo(b.getNome()));

                for (MembroResponse membro : listaDeMembros) {
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    textView.setText(membro.getNome());
                    textView.setTextColor(getResources().getColor(R.color.cordetextohallel));
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    textView.setPadding(8, 8, 8, 8);
                    textView.setTypeface(ResourcesCompat.getFont(context, R.font.inter_semibold));

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ListMemberActivity.this, InformacoesDoMembroActivity.class);
                            intent.putExtra("membro", membro); // Passa o objeto membro
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
                TextView staticTextView = new TextView(ListMemberActivity.this);
                staticTextView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                staticTextView.setText("Nome do membro");
                staticTextView.setTextColor(getResources().getColor(R.color.cortexto2));
                staticTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                staticTextView.setPadding(8, 8, 8, 8);
                staticTextView.setTypeface(ResourcesCompat.getFont(context, R.font.inter_extrabold));
                tableLayout.addView(staticTextView);

                Collections.sort(listaDeMembros, (a, b) -> b.getNome().compareTo(a.getNome()));

                for (MembroResponse membro : listaDeMembros) {
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    textView.setText(membro.getNome());
                    textView.setTextColor(getResources().getColor(R.color.cordetextohallel));
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    textView.setPadding(8, 8, 8, 8);
                    textView.setTypeface(ResourcesCompat.getFont(context, R.font.inter_semibold));

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ListMemberActivity.this, InformacoesDoMembroActivity.class);
                            intent.putExtra("membro", membro); // Passa o objeto membro
                            startActivity(intent);
                        }
                    });

                    tableLayout.addView(textView);
                }
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

