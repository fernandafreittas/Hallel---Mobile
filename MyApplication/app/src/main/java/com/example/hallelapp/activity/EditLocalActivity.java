package com.example.hallelapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hallelapp.R;
import com.example.hallelapp.databinding.ActivityEditLocaisBinding;
import com.example.hallelapp.htpp.HttpAdm;
import com.example.hallelapp.model.LocalEvento;
import com.example.hallelapp.payload.requerimento.LocalEventoReq;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.example.hallelapp.recyclers.LocalEditRecycle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EditLocalActivity extends AppCompatActivity implements LocalEditRecycle.OnItemClickListener{

    AuthenticationResponse authenticationResponse;
    ActivityEditLocaisBinding binding;
    private AlertDialog loadingDialog;
    HttpAdm requisicao;
    List<LocalEvento> locais;

    LocalEvento localEdit;

    private RecyclerView recyclerView;
    private LocalEditRecycle adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityEditLocaisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Context context = this;

        Button btnAlterar = findViewById(R.id.buttonEditLocal);
        EditText txtLocalEdit = findViewById(R.id.inputEnderecoEdit);



        authenticationResponse = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");
        localEdit = (LocalEvento) getIntent().getSerializableExtra("local");
        requisicao = new HttpAdm();
        showLoadingDialog();


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        locais = new ArrayList<>();
        adapter = new LocalEditRecycle(this, locais, this);
        recyclerView.setAdapter(adapter);

        txtLocalEdit.setText(localEdit.getLocalizacao());


        requisicao.ListLocaisEventos(authenticationResponse, new HttpAdm.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                // Deserializa a resposta e prepara os dados
                Type listType = new TypeToken<List<LocalEvento>>() {}.getType();
                final List<LocalEvento> locaisResponse = new Gson().fromJson(response, listType);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        locais.clear();
                        locais.addAll(locaisResponse);
                        adapter.notifyDataSetChanged();
                        hideLoadingDialog();
                    }
                });

                btnAlterar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        LocalEventoReq localEventoReq = new LocalEventoReq();

                        localEventoReq.setLocalizacao(txtLocalEdit.getText().toString());
                        localEventoReq.setImagem(localEdit.getImagem());


                        requisicao.EditLocaisEventos(localEventoReq, localEdit.getId(), authenticationResponse, new HttpAdm.HttpCallback() {
                            @Override
                            public void onSuccess(String response) {
                                runOnUiThread(() -> {
                                    hideLoadingDialog();
                                    showSuccessEditDialog();
                                });

                                finish();

                            }

                            @Override
                            public void onFailure(IOException e) {

                                runOnUiThread(() -> {
                                    hideLoadingDialog();
                                   showErrorEditDialog();
                                });

                            }
                        });

                    }
                });


            }

            @Override
            public void onFailure(IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingDialog();
                        Toast.makeText(context, "Erro ao carregar os locais", Toast.LENGTH_SHORT).show();
                    }
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



    @Override
    public void onDeleteClick(int position) {
        // Handle delete action

        LocalEvento local = locais.get(position);
        CerterzaDialog(local,position);




    }
    private void showErrorDeletarDialog() {
        // Inflate o layout do diálogo de erro
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_erro_deletarlocal, null);

        // Cria o dialog a partir do layout inflado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Clique no botão de continuar para fechar o diálogo
        Button btnContinuar = dialogView.findViewById(R.id.buttonErrDEvntloc);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void CerterzaDialog(LocalEvento local,int position) {
        // Inflate o layout do diálogo de erro
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_certeza_deletarlocalevento, null);

        // Cria o dialog a partir do layout inflado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Clique no botão de continuar para fechar o diálogo
        Button btnContinuar = dialogView.findViewById(R.id.buttonCertDelsimloc);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                requisicao.DeleteLocaisEventos(local, authenticationResponse, new HttpAdm.HttpCallback() {
                    @Override
                    public void onSuccess(String response) {


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                locais.remove(position);
                                adapter.notifyItemRemoved(position);
                                showSuccessDeleteDialog();
                            }
                        });
                    }

                    @Override
                    public void onFailure(IOException e) {
                        showErrorDeletarDialog();
                    }
                });
            }
        });

        Button btnNaoContinuar = dialogView.findViewById(R.id.buttonCertDelloc);

        btnNaoContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void showErrorEditDialog() {
        // Inflate o layout do diálogo de erro
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_erro_editarlocal, null);

        // Cria o dialog a partir do layout inflado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Clique no botão de continuar para fechar o diálogo
        Button btnContinuar = dialogView.findViewById(R.id.buttonErrc);
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
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_localdeletado_sucesso, null);

        // Cria o dialog a partir do layout inflado
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setView(dialogView);

        androidx.appcompat.app.AlertDialog dialog = builder.create();

        // Clique no botão de continuar para redirecionar à página de login ou outra ação
        Button btnContinuar = dialogView.findViewById(R.id.buttonEld);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showSuccessEditDialog() {
        // Inflate o layout do diálogo de sucesso
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_localeditado_sucesso, null);

        // Cria o dialog a partir do layout inflado
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setView(dialogView);

        androidx.appcompat.app.AlertDialog dialog = builder.create();

        // Clique no botão de continuar para redirecionar à página de login ou outra ação
        Button btnContinuar = dialogView.findViewById(R.id.buttonEldt);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }




}
