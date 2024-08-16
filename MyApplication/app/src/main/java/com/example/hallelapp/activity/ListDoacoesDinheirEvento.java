package com.example.hallelapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpAdm;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.example.hallelapp.payload.resposta.DoacaoDinheiroEventoResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.graphics.Typeface;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListDoacoesDinheirEvento extends AppCompatActivity {

    Context context = this;
    List<String> listaDeNomes;
    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adm_doacaodinheiro);

        AuthenticationResponse authenticationResponse = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");
        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");

        HttpAdm requisicao = new HttpAdm();

        TableLayout tableLayout = findViewById(R.id.tableLayoutDoacaoDinheiro);
        Button btnAZ = findViewById(R.id.btnAzDinheiro);
        Button btnZA = findViewById(R.id.btnZaDinheiro);
        Button btnValor = findViewById(R.id.btnValor);
        Button btnOrdemCronologica = findViewById(R.id.btnOrdemCronologicaDinheiro);


        showLoadingDialog();
        requisicao.ListDoacaoDinheiro(evento.getId(), authenticationResponse, new HttpAdm.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                hideLoadingDialog();
                Type listType = new TypeToken<List<DoacaoDinheiroEventoResponse>>() {}.getType();
                List<DoacaoDinheiroEventoResponse> responsedoacao = new Gson().fromJson(response, listType);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        populateTable(tableLayout, responsedoacao);

                        btnAZ.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Collections.sort(responsedoacao, new Comparator<DoacaoDinheiroEventoResponse>() {
                                    @Override
                                    public int compare(DoacaoDinheiroEventoResponse d1, DoacaoDinheiroEventoResponse d2) {
                                        return d1.getNomeDoador().compareTo(d2.getNomeDoador());
                                    }
                                });
                                tableLayout.removeAllViews();
                                populateTable(tableLayout, responsedoacao);
                            }
                        });

                        btnZA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Collections.sort(responsedoacao, new Comparator<DoacaoDinheiroEventoResponse>() {
                                    @Override
                                    public int compare(DoacaoDinheiroEventoResponse d1, DoacaoDinheiroEventoResponse d2) {
                                        return d2.getNomeDoador().compareTo(d1.getNomeDoador());
                                    }
                                });
                                tableLayout.removeAllViews();
                                populateTable(tableLayout, responsedoacao);
                            }
                        });



                        btnValor.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Ordenar por valor doado em ordem decrescente
                                Collections.sort(responsedoacao, new Comparator<DoacaoDinheiroEventoResponse>() {
                                    @Override
                                    public int compare(DoacaoDinheiroEventoResponse d1, DoacaoDinheiroEventoResponse d2) {
                                        return d2.getValorDoado().compareTo(d1.getValorDoado());
                                    }
                                });

                                tableLayout.removeAllViews();
                                // Repopular o TableLayout
                                populateTable(tableLayout, responsedoacao);
                            }
                        });

                        btnOrdemCronologica.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Ordenar por data de doação do mais novo para o mais antigo
                                Collections.sort(responsedoacao, new Comparator<DoacaoDinheiroEventoResponse>() {
                                    @Override
                                    public int compare(DoacaoDinheiroEventoResponse d1, DoacaoDinheiroEventoResponse d2) {
                                        return d2.getDataDoacao().compareTo(d1.getDataDoacao());
                                    }
                                });

                                tableLayout.removeAllViews();
                                // Repopular o TableLayout
                                populateTable(tableLayout, responsedoacao);
                            }
                        });



                    }
                });
            }

            @Override
            public void onFailure(IOException e) {
                hideLoadingDialog();
                e.printStackTrace();
            }
        });
    }

    private void populateTable(TableLayout tableLayout, List<DoacaoDinheiroEventoResponse> doacoes) {

        // Adiciona a linha de cabeçalho
        TableRow headerRow = new TableRow(this);

        TextView headerNome = new TextView(this);
        headerNome.setText("Nome do membro      ");
        headerNome.setPadding(8, 8, 8, 8);
        headerNome.setTextColor(getResources().getColor(R.color.cortexto2));
        headerNome.setTypeface(ResourcesCompat.getFont(this, R.font.inter), Typeface.BOLD);
        headerNome.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        TextView headerValor = new TextView(this);
        headerValor.setText("Valor           ");
        headerValor.setPadding(8, 8, 8, 8);
        headerValor.setTextColor(getResources().getColor(R.color.cortexto2));
        headerValor.setTypeface(ResourcesCompat.getFont(this, R.font.inter), Typeface.BOLD);
        headerValor.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        TextView headerFormaPagamento = new TextView(this);
        headerFormaPagamento.setText("Forma de pagamento");
        headerFormaPagamento.setPadding(8, 8, 8, 8);
        headerFormaPagamento.setTextColor(getResources().getColor(R.color.cortexto2));
        headerFormaPagamento.setTypeface(ResourcesCompat.getFont(this, R.font.inter), Typeface.BOLD);
        headerFormaPagamento.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        headerRow.addView(headerNome);
        headerRow.addView(headerValor);
        headerRow.addView(headerFormaPagamento);

        tableLayout.addView(headerRow);


        // Adiciona as linhas de doações
        for (DoacaoDinheiroEventoResponse doacao : doacoes) {
            TableRow tableRow = new TableRow(this);

            TextView nomeDoador = new TextView(this);
            nomeDoador.setText(doacao.getNomeDoador());
            nomeDoador.setPadding(8, 8, 8, 8);
            nomeDoador.setTextColor(getResources().getColor(R.color.cordetextohallel));
            nomeDoador.setTypeface(ResourcesCompat.getFont(this, R.font.inter_semibold));

            TextView valorDoado = new TextView(this);
            valorDoado.setText(String.format("R$ %.2f", doacao.getValorDoado()));
            valorDoado.setPadding(8, 8, 8, 8);
            valorDoado.setTextColor(getResources().getColor(R.color.cordetextohallel));
            valorDoado.setTypeface(ResourcesCompat.getFont(this, R.font.inter_semibold));

            TextView formaDePagamento = new TextView(this);
            formaDePagamento.setText(doacao.getFormaDePagamento());
            formaDePagamento.setPadding(8, 8, 8, 8);
            formaDePagamento.setTextColor(getResources().getColor(R.color.cordetextohallel));
            formaDePagamento.setTypeface(ResourcesCompat.getFont(this, R.font.inter_semibold));

            valorDoado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ListDoacoesDinheirEvento.this, DadosDoacaoDinheiroActivity.class);
                    intent.putExtra("doacaoDinheiro", doacao);
                    startActivity(intent);
                }
            });



            tableRow.addView(nomeDoador);
            tableRow.addView(valorDoado);
            tableRow.addView(formaDePagamento);

            tableLayout.addView(tableRow);
        }




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
