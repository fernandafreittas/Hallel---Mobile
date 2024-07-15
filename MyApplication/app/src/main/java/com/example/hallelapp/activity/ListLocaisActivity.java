package com.example.hallelapp.activity;

import android.content.Context;
import android.content.Intent;
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
import com.example.hallelapp.databinding.ActivityListLocaisBinding;
import com.example.hallelapp.htpp.HttpAdm;
import com.example.hallelapp.model.LocalEvento;
import com.example.hallelapp.payload.requerimento.LocalEventoReq;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.example.hallelapp.recyclers.LocalRecycle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListLocaisActivity extends AppCompatActivity implements LocalRecycle.OnItemClickListener {

    AuthenticationResponse authenticationResponse;
    ActivityListLocaisBinding binding;
    private AlertDialog loadingDialog;
    HttpAdm requisicao;
    List<LocalEvento> locais;

    private RecyclerView recyclerView;
    private LocalRecycle adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityListLocaisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Context context = this;

        authenticationResponse = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");
        requisicao = new HttpAdm();
        showLoadingDialog();

        EditText txtEndereco = findViewById(R.id.inputEndereco);
        Button btnAddLocal = findViewById(R.id.buttonAddLocal);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        locais = new ArrayList<>();
        adapter = new LocalRecycle(this, locais, this);
        recyclerView.setAdapter(adapter);

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

        btnAddLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();

                LocalEventoReq localEventoReq = new LocalEventoReq();
                localEventoReq.setLocalizacao(txtEndereco.getText().toString());

                requisicao.CreateLocaisEventos(localEventoReq, authenticationResponse, new HttpAdm.HttpCallback() {
                    @Override
                    public void onSuccess(String response) {
                        hideLoadingDialog();
                        runOnUiThread(() -> {
                            Toast.makeText(context, "Local criado com sucesso", Toast.LENGTH_SHORT).show();
                            finish(); // Talvez queira recarregar os dados ao invés de finalizar a atividade
                        });
                    }

                    @Override
                    public void onFailure(IOException e) {
                        runOnUiThread(() -> {
                            hideLoadingDialog();
                            Toast.makeText(context, "Erro ao criar o local", Toast.LENGTH_SHORT).show();
                        });
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
    public void onEditClick(int position) {
        // Handle edit action
        LocalEvento local = locais.get(position);


        showLoadingDialog();
        Intent intent = new Intent(ListLocaisActivity.this, EditLocalActivity.class);
        intent.putExtra("informaçõesADM", authenticationResponse);
        intent.putExtra("local", local);
        startActivity(intent);
        hideLoadingDialog();


    }

    @Override
    public void onDeleteClick(int position) {
        // Handle delete action

        LocalEvento local = locais.get(position);

        requisicao.DeleteLocaisEventos(local, authenticationResponse, new HttpAdm.HttpCallback() {
            @Override
            public void onSuccess(String response) {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        locais.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                });
            }

            @Override
            public void onFailure(IOException e) {

            }
        });



    }
}
