package com.example.hallelapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.hallelapp.R;
import com.example.hallelapp.databinding.ActivityEventosArquivadosBinding;
import com.example.hallelapp.htpp.HttpAdm;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.model.EventoArquivado;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.example.hallelapp.recyclers.EventosArquivadosRecycle;
import com.example.hallelapp.recyclers.OnEventoArquivadoClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class EventosArquivadosActivity extends AppCompatActivity implements OnEventoArquivadoClickListener {

    AuthenticationResponse authenticationResponse;
    Context context = this;
    ActivityEventosArquivadosBinding binding;
    List<EventoArquivado> responseEventos;
    private AlertDialog loadingDialog;

    HttpAdm requisicao;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityEventosArquivadosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AuthenticationResponse authenticationResponse2 = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");
        authenticationResponse = authenticationResponse2;

        requisicao = new HttpAdm();
        showLoadingDialog();

        requisicao.ListEventosArquivados(authenticationResponse, new HttpMain.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingDialog();
                        Type listType = new TypeToken<List<EventoArquivado>>() {}.getType();
                        List<EventoArquivado> responseEventos2 = new Gson().fromJson(response, listType);
                        responseEventos = responseEventos2;
                        System.out.println("deu certo!");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recicleView(responseEventos);
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void onFailure(IOException e) {
                System.out.println("teste3");
                hideLoadingDialog();
            }
        });
    }





    private void recicleView(List<EventoArquivado> responseEventos) {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setHasFixedSize(true);
        EventosArquivadosRecycle eventosArquivadosRecycle = new EventosArquivadosRecycle(context, responseEventos, this);
        binding.recyclerView.setAdapter(eventosArquivadosRecycle);
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

    @Override
    public void onDesarquivarClick(EventoArquivado eventoArquivado) {


        requisicao = new HttpAdm();
        showLoadingDialog();
        requisicao.DesarquivaEvento(eventoArquivado.getId(), authenticationResponse, new HttpMain.HttpCallback() {
            @Override
            public void onSuccess(String response) {


                runOnUiThread(() -> {
                    Toast.makeText(context, "Evento desarquivado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                    hideLoadingDialog();
                });
            }

            @Override
            public void onFailure(IOException e) {

            }
        });





    }

    @Override
    public void onDeleteClick(EventoArquivado eventoArquivado) {



        requisicao = new HttpAdm();
        showLoadingDialog();
        requisicao.DesarquivaEvento(eventoArquivado.getId(), authenticationResponse, new HttpMain.HttpCallback() {
            @Override
            public void onSuccess(String response) {
              requisicao.DeletarEvento(eventoArquivado.getId(), authenticationResponse, new HttpMain.HttpCallback() {
                  @Override
                  public void onSuccess(String response) {
                      runOnUiThread(() -> {
                          Toast.makeText(context, "Evento deletado com sucesso", Toast.LENGTH_SHORT).show();
                          hideLoadingDialog();
                          finish();
                      });
                  }

                  @Override
                  public void onFailure(IOException e) {

                  }
              });


            }

            @Override
            public void onFailure(IOException e) {

            }
        });


    }
}
