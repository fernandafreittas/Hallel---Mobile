package com.example.hallelapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
                    // Remover o evento desarquivado da lista
                    responseEventos.remove(eventoArquivado);
                    binding.recyclerView.getAdapter().notifyDataSetChanged(); // Notifica o adaptador sobre a mudança

                    showSuccessDesarquivarDialog();
                    hideLoadingDialog();
                });
            }

            @Override
            public void onFailure(IOException e) {
                runOnUiThread(() -> {
                    hideLoadingDialog();
                    showErrorDesarquivarDialog();
                });
            }
        });
    }


    @Override
    public void onDeleteClick(EventoArquivado eventoArquivado) {
        showCertezaDialog(eventoArquivado);
    }

    private void showCertezaDialog(EventoArquivado eventoArquivado) {
        // Inflate o layout do diálogo de sucesso
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_certeza_deletarevento, null);

        // Cria o dialog a partir do layout inflado
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setView(dialogView);

        androidx.appcompat.app.AlertDialog dialog = builder.create();

        // Clique no botão de não continuar
        Button btnNaoContinuar = dialogView.findViewById(R.id.buttonCertDel);
        btnNaoContinuar.setOnClickListener(v -> dialog.dismiss());

        Button btnContinuar = dialogView.findViewById(R.id.buttonCertDelsim);
        btnContinuar.setOnClickListener(v -> {
            dialog.dismiss();

            requisicao = new HttpAdm();
            showLoadingDialog();
            requisicao.DeletarEvento(eventoArquivado.getId(), authenticationResponse, new HttpMain.HttpCallback() {
                @Override
                public void onSuccess(String response) {
                    runOnUiThread(() -> {
                        hideLoadingDialog();
                        // Atualizar a lista removendo o evento deletado
                        responseEventos.remove(eventoArquivado);
                        binding.recyclerView.getAdapter().notifyDataSetChanged(); // Notifica o adaptador sobre a mudança
                        showSuccessDeleteDialog();
                    });
                }

                @Override
                public void onFailure(IOException e) {
                    System.out.println("deu errado ao deletar");
                    hideLoadingDialog();
                    showErrorDeleteDialog();
                }
            });
        });

        dialog.show();
    }

    private void showErrorDeleteDialog() {
        // Inflate o layout do diálogo de erro
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_erro_deletarevento, null);

        // Cria o dialog a partir do layout inflado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Clique no botão de continuar para fechar o diálogo
        Button btnContinuar = dialogView.findViewById(R.id.buttonErrDEvnt);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showErrorDesarquivarDialog() {
        // Inflate o layout do diálogo de erro
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_erro_desarqevento, null);

        // Cria o dialog a partir do layout inflado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Clique no botão de continuar para fechar o diálogo
        Button btnContinuar = dialogView.findViewById(R.id.buttonErrDesEvntloc);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showSuccessDeleteDialog() {
        // Inflate o layout do diálogo de sucesso
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_eventodeletado_sucesso, null);

        // Cria o dialog a partir do layout inflado
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setView(dialogView);

        androidx.appcompat.app.AlertDialog dialog = builder.create();

        // Clique no botão de continuar para redirecionar à página de login ou outra ação
        Button btnContinuar = dialogView.findViewById(R.id.buttonEd);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showSuccessDesarquivarDialog() {
        // Inflate o layout do diálogo de sucesso
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_eventodesarquivado, null);

        // Cria o dialog a partir do layout inflado
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setView(dialogView);

        androidx.appcompat.app.AlertDialog dialog = builder.create();

        // Clique no botão de continuar para redirecionar à página de login ou outra ação
        Button btnContinuar = dialogView.findViewById(R.id.buttonEdz);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }








}
