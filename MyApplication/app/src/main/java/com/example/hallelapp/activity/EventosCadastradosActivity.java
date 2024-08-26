package com.example.hallelapp.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hallelapp.R;
import com.example.hallelapp.databinding.ActivityVizualizaEventosBinding;
import com.example.hallelapp.htpp.HttpAdm;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.example.hallelapp.recyclers.EventoAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class EventosCadastradosActivity extends AppCompatActivity implements EventoAdapter.OnArquivarClickListener {

    ActivityVizualizaEventosBinding binding;
    List<AllEventosListResponse> responseEventos;
    private AlertDialog loadingDialog;
    private RecyclerView recyclerView;
    private EventoAdapter eventoAdapter;
    private AuthenticationResponse authenticationResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_visualizar_eventoscadastrados);

        recyclerView = findViewById(R.id.recyclerViewcad);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        HttpMain requisicao = new HttpMain();
        HttpAdm requisicaoADM = new HttpAdm();

     authenticationResponse = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");

        showLoadingDialog();

        requisicao.ListAllEventos(new HttpMain.HttpCallback() {

            @Override
            public void onSuccess(String response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingDialog();
                        Type listType = new TypeToken<List<AllEventosListResponse>>() {}.getType();
                        List<AllEventosListResponse> responseEventos2 = new Gson().fromJson(response, listType);
                        responseEventos = responseEventos2;

                        runOnUiThread(() -> {
                            eventoAdapter = new EventoAdapter(EventosCadastradosActivity.this, responseEventos, EventosCadastradosActivity.this);
                            recyclerView.setAdapter(eventoAdapter);
                        });
                    }
                }).start();
            }

            @Override
            public void onFailure(IOException e) {
                System.out.println("Falha na requisição");
                hideLoadingDialog();
            }
        });
    }

    @Override
    public void onArquivarClick(AllEventosListResponse evento) {
        HttpAdm requisicaoADM = new HttpAdm();

        requisicaoADM.ArquivarEvento(evento.getId(), authenticationResponse, new HttpAdm.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    // Remover o evento da lista
                    responseEventos.remove(evento);

                    // Notificar o adaptador sobre a remoção
                    eventoAdapter.notifyItemRemoved(responseEventos.indexOf(evento));
                    eventoAdapter.notifyDataSetChanged();

                    Toast.makeText(EventosCadastradosActivity.this, "Evento arquivado com sucesso", Toast.LENGTH_SHORT).show();
                    hideLoadingDialog();
                });
            }

            @Override
            public void onFailure(IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(EventosCadastradosActivity.this, "Erro ao arquivar evento", Toast.LENGTH_SHORT).show();
                });
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


