package com.example.hallelapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hallelapp.databinding.ActivityLoginBinding;
import com.example.hallelapp.databinding.ActivityMainBinding;
import com.example.hallelapp.databinding.ActivityVizualizaEventosBinding;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.payload.requerimento.CadastroRequest;
import com.example.hallelapp.payload.requerimento.LoginRequest;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.payload.resposta.LoginResponse;
import com.example.hallelapp.recyclers.EventosRecycle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityVizualizaEventosBinding binding;
    List<AllEventosListResponse> responseEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVizualizaEventosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        System.out.println("teste1");

        HttpMain requisicao = new HttpMain();

        requisicao.ListAllEventos(new HttpMain.HttpCallback() {

            @Override
            public void onSuccess(String response) {
                // Processar a resposta em um thread de fundo
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Type listType = new TypeToken<List<AllEventosListResponse>>() {}.getType();
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
    }
}