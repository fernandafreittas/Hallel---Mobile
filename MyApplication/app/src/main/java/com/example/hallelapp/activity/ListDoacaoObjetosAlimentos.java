package com.example.hallelapp.activity;

import android.app.AlertDialog;
import android.content.Context;
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
import com.example.hallelapp.payload.resposta.DoacaoDinheiroEventoResponse;
import com.example.hallelapp.payload.resposta.DoacaoObjetosEventosResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListDoacaoObjetosAlimentos extends AppCompatActivity {

    Context context = this;
    List<String> listaDeNomes;
    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_doacao_objetos_alimentos);

        AuthenticationResponse authenticationResponse = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");
        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");

        HttpAdm requisicao = new HttpAdm();

        TableLayout tableLayout = findViewById(R.id.tableLayoutDoacao);
        Button btnAZ = findViewById(R.id.btnAz);
        Button btnZA = findViewById(R.id.btnZa);
        Button btnOrdemCronologica = findViewById(R.id.btnOrdemCronologica);


        showLoadingDialog();
        requisicao.ListDoacaoObjetos(evento.getId(), authenticationResponse, new HttpAdm.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                hideLoadingDialog();
                Type listType = new TypeToken<List<DoacaoObjetosEventosResponse>>() {}.getType();
                List<DoacaoObjetosEventosResponse> responsedoacao = new Gson().fromJson(response, listType);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        populateTable(tableLayout, responsedoacao);

                        btnAZ.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Collections.sort(responsedoacao, new Comparator<DoacaoObjetosEventosResponse>() {
                                    @Override
                                    public int compare(DoacaoObjetosEventosResponse d1, DoacaoObjetosEventosResponse d2) {
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
                                Collections.sort(responsedoacao, new Comparator<DoacaoObjetosEventosResponse>() {
                                    @Override
                                    public int compare(DoacaoObjetosEventosResponse d1, DoacaoObjetosEventosResponse d2) {
                                        return d2.getNomeDoador().compareTo(d1.getNomeDoador());
                                    }
                                });
                                tableLayout.removeAllViews();
                                populateTable(tableLayout, responsedoacao);
                            }
                        });



                        btnOrdemCronologica.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Ordenar por data de doação do mais novo para o mais antigo
                                Collections.sort(responsedoacao, new Comparator<DoacaoObjetosEventosResponse>() {
                                    @Override
                                    public int compare(DoacaoObjetosEventosResponse d1, DoacaoObjetosEventosResponse d2) {
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

    private void populateTable(TableLayout tableLayout, List<DoacaoObjetosEventosResponse> doacoes) {

        // Adiciona a linha de cabeçalho
        TableRow headerRow = new TableRow(this);

        TextView headerNome = new TextView(this);
        headerNome.setText("Doador      ");
        headerNome.setPadding(8, 8, 8, 8);
        headerNome.setTextColor(getResources().getColor(R.color.cortexto2));
        headerNome.setTypeface(ResourcesCompat.getFont(this, R.font.inter), Typeface.BOLD);
        headerNome.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        TextView headerObejto = new TextView(this);
        headerObejto.setText("Objeto           ");
        headerObejto.setPadding(8, 8, 8, 8);
        headerObejto.setTextColor(getResources().getColor(R.color.cortexto2));
        headerObejto.setTypeface(ResourcesCompat.getFont(this, R.font.inter), Typeface.BOLD);
        headerObejto.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        TextView headerQuantidade = new TextView(this);
        headerQuantidade.setText("Quanti...");
        headerQuantidade.setPadding(8, 8, 8, 8);
        headerQuantidade.setTextColor(getResources().getColor(R.color.cortexto2));
        headerQuantidade.setTypeface(ResourcesCompat.getFont(this, R.font.inter), Typeface.BOLD);
        headerQuantidade.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);


        TextView headerFoiRecebido = new TextView(this);
        headerFoiRecebido.setText("Quanti...");
        headerFoiRecebido.setPadding(8, 8, 8, 8);
        headerFoiRecebido.setTextColor(getResources().getColor(R.color.cortexto2));
        headerFoiRecebido.setTypeface(ResourcesCompat.getFont(this, R.font.inter), Typeface.BOLD);
        headerFoiRecebido.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        headerRow.addView(headerNome);
        headerRow.addView(headerObejto);
        headerRow.addView(headerFoiRecebido);
        headerRow.addView(headerQuantidade);

        tableLayout.addView(headerRow);


        // Adiciona as linhas de doações
        for (DoacaoObjetosEventosResponse doacao : doacoes) {
            TableRow tableRow = new TableRow(this);

            TextView nomeDoador = new TextView(this);
            nomeDoador.setText(doacao.getNomeDoador());
            nomeDoador.setPadding(8, 8, 8, 8);
            nomeDoador.setTextColor(getResources().getColor(R.color.cordetextohallel));
            nomeDoador.setTypeface(ResourcesCompat.getFont(this, R.font.inter_semibold));

            TextView objetoDoado = new TextView(this);
            objetoDoado.setText( doacao.getNomeDoObjeto());
            objetoDoado.setPadding(8, 8, 8, 8);
            objetoDoado.setTextColor(getResources().getColor(R.color.cordetextohallel));
            objetoDoado.setTypeface(ResourcesCompat.getFont(this, R.font.inter_semibold));

            TextView quantidade = new TextView(this);
            quantidade.setText(String.valueOf(doacao.getQuantidade()));
            quantidade.setPadding(8, 8, 8, 8);
            quantidade.setTextColor(getResources().getColor(R.color.cordetextohallel));
            quantidade.setTypeface(ResourcesCompat.getFont(this, R.font.inter_semibold));


            TextView Recebido = new TextView(this);

            if(doacao.isRecebido()) {
                Recebido.setText("sim");
                Recebido.setPadding(8, 8, 8, 8);
                Recebido.setTextColor(getResources().getColor(R.color.cordetextohallel));
                Recebido.setTypeface(ResourcesCompat.getFont(this, R.font.inter_semibold));
            }else {
                Recebido.setText("não");
                Recebido.setPadding(8, 8, 8, 8);
                Recebido.setTextColor(getResources().getColor(R.color.cordetextohallel));
                Recebido.setTypeface(ResourcesCompat.getFont(this, R.font.inter_semibold));
            }

            tableRow.addView(nomeDoador);
            tableRow.addView(objetoDoado);
            tableRow.addView(quantidade);
            tableRow.addView(Recebido);

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