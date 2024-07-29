package com.example.hallelapp.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.hallelapp.R;
import com.example.hallelapp.databinding.ActivityVizualizaEventosBinding;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.example.hallelapp.recyclers.EventosRecycle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class VizualizarEventosDoacao extends AppCompatActivity {


    ActivityVizualizaEventosBinding binding;
    private AlertDialog loadingDialog;
    List<AllEventosListResponse> responseEventos;
    AuthenticationResponse authenticationResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityVizualizaEventosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        System.out.println("teste1");


        AuthenticationResponse authenticationResponse2 = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");
        authenticationResponse = authenticationResponse2;

        HttpMain requisicao = new HttpMain();

        showLoadingDialog();
        requisicao.ListAllEventos(new HttpMain.HttpCallback() {

            @Override
            public void onSuccess(String response) {
                // Processar a resposta em um thread de fundo
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingDialog();
                        Type listType = new TypeToken<List<AllEventosListResponse>>() {
                        }.getType();
                        List<AllEventosListResponse> responseEventos2 = new Gson().fromJson(response, listType);
                        responseEventos = responseEventos2;

                        // Adicionando um log para verificar se a lista está preenchida

                        // Atualizar a UI no thread principal
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Chamar recicleView() apenas quando a lista estiver pronta
                                recicleView(responseEventos);
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void onFailure(IOException e) {
                System.out.println("teste3");
                // Lida com a falha na requisição
                hideLoadingDialog();
                // Por exemplo, você pode exibir uma mensagem de erro para o usuário
            }
        });


    }


    private void recicleView(List<AllEventosListResponse> responseEventos) {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        binding.recyclerView.setHasFixedSize(true);
        EventosRecycle recycle = new EventosRecycle();
        recycle.setEventos(responseEventos);
        binding.recyclerView.setAdapter(recycle);

        // Adicionando clique ao RecyclerView
        recycle.setOnItemClickListener(new EventosRecycle.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                System.out.println(position);
                AllEventosListResponse evento = responseEventos.get(position);
                Intent intent = new Intent(VizualizarEventosDoacao.this, ListDoacoesDinheirEvento.class);
                intent.putExtra("evento", evento); // Adiciona o objeto evento como um extra
                intent.putExtra("informaçõesADM", authenticationResponse);
                startActivity(intent);

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
