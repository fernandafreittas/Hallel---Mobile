package com.example.hallelapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallelapp.activity.LoginActivity;
import com.example.hallelapp.activity.MoreInfosActivity;
import com.example.hallelapp.activity.VizualizaEventosActivity;
import com.example.hallelapp.databinding.ActivityVizualizaEventosBinding;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.payload.resposta.LoginResponse;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private ImageButton btnPerfil;
    private Button btnVerEventos;
    private NavigationView navigationView;

    private static final int idSair = R.id.sairButton;

    List<AllEventosListResponse> responseEventos;

    LoginResponse informacoesDeLogin;

    int indexArray = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.navigation_bar);
        navigationView.setNavigationItemSelectedListener(this);
        btnPerfil = findViewById(R.id.btnperfil);
        navigationView.setVisibility(View.GONE);
        btnVerEventos = findViewById(R.id.btnvertodos);
        ImageView imagemEventos = findViewById(R.id.imgevento);
        ImageButton botaoAvancaEvento = findViewById(R.id.imageButton3);
        ImageButton botaoRetrocederEvento = findViewById(R.id.imageButton2);
        Button login = findViewById(R.id.buttonDoacao);


       informacoesDeLogin = obterDadosSalvos();


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
                        AllEventosListResponse evento = responseEventos.get(0);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String StringBase64 = evento.getImagem();

                                // Obter a parte da string que contém os dados em base64
                                String[] partes = StringBase64.split(",");
                                String dadosBase64 = partes[1];

                                // Decodificar a string base64 em uma imagem Bitmap
                                byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                                imagemEventos.setImageBitmap(decodedByte);


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


        botaoAvancaEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(indexArray<responseEventos.size()-1){
                    indexArray++;

                }else {
                    indexArray = 0;

                }



                AllEventosListResponse evento = responseEventos.get(indexArray);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String StringBase64 = evento.getImagem();

                        // Obter a parte da string que contém os dados em base64
                        String[] partes = StringBase64.split(",");
                        String dadosBase64 = partes[1];

                        // Decodificar a string base64 em uma imagem Bitmap
                        byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        imagemEventos.setImageBitmap(decodedByte);


                    }
                });

            }
        });

        botaoRetrocederEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(indexArray>0){
                    indexArray--;
                }else {
                    indexArray = responseEventos.size()-1;
                }




                AllEventosListResponse evento = responseEventos.get(indexArray);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String StringBase64 = evento.getImagem();

                        // Obter a parte da string que contém os dados em base64
                        String[] partes = StringBase64.split(",");
                        String dadosBase64 = partes[1];

                        // Decodificar a string base64 em uma imagem Bitmap
                        byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        imagemEventos.setImageBitmap(decodedByte);


                    }
                });



            }
        });





        btnVerEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VizualizaEventosActivity.class);
                startActivity(intent);
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navigationView.getVisibility() == View.VISIBLE) {
                    navigationView.setVisibility(View.GONE);
                } else {
                    navigationView.setVisibility(View.VISIBLE);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.sairButton){

            navigationView.setVisibility(View.GONE);

        }

        return false;

    }

    private LoginResponse obterDadosSalvos() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        String savedResponse = sharedPref.getString("dados de login", null);

        if (savedResponse != null) {
            // Aqui você precisa converter a string de volta para um objeto LoginResponse
            Gson gson = new Gson(); // Certifique-se de ter Gson adicionado ao seu projeto
            return gson.fromJson(savedResponse, LoginResponse.class);
        }

        return null;
    }

}