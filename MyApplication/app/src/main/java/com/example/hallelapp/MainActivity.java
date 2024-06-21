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
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.hallelapp.activity.LoginActivity;
import com.example.hallelapp.activity.LoginAdmActiviy;
import com.example.hallelapp.activity.MoreInfosActivity;
import com.example.hallelapp.activity.VizualizaEventosActivity;
import com.example.hallelapp.activity.PerfilActivity;
import com.example.hallelapp.databinding.ActivityVizualizaEventosBinding;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.htpp.HttpMembro;
import com.example.hallelapp.model.InformacoesDaSessao;
import com.example.hallelapp.payload.requerimento.LoginRequest;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.payload.resposta.LoginResponse;
import com.example.hallelapp.payload.resposta.PerfilResponse;
import com.example.hallelapp.payload.resposta.ValoresEventoResponse;
import com.example.hallelapp.tools.ObterInformacoesDaSecao;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageButton btnPerfil;
    private Button btnVerEventos;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private static final int idSair = R.id.sairButton;

    List<AllEventosListResponse> responseEventos;

    InformacoesDaSessao informacoesDeLogin;

    ObterInformacoesDaSecao obterInformacoesDaSecao;

    int indexArray = 0;

    private TextView nomeEvento;


    PerfilResponse perfilResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // declaração dos componentes

        navigationView = findViewById(R.id.navigation_bar);
        navigationView.setNavigationItemSelectedListener(this);
        btnPerfil = findViewById(R.id.btnperfil);
        navigationView.setVisibility(View.GONE);
        btnVerEventos = findViewById(R.id.btnvertodos);
        nomeEvento = findViewById(R.id.nomeevento);
        ImageView imagemEventos = findViewById(R.id.imgevento);
        ImageButton botaoAvancaEvento = findViewById(R.id.imageButton3);
        ImageButton botaoRetrocederEvento = findViewById(R.id.imageButton2);
        Button login = findViewById(R.id.buttonDoacao);
        Button loginADM = findViewById(R.id.button);

        obterInformacoesDaSecao = new ObterInformacoesDaSecao(this);


        // Verifica se há dados criptografados antes de tentar descriptografar
        try {
            informacoesDeLogin = obterInformacoesDaSecao.obterDadosSalvos();
        } catch (Exception e) {
            informacoesDeLogin = new InformacoesDaSessao();

        btnVerEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MoreInfosActivity.class);
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
    }



        System.out.println(informacoesDeLogin.toString());

        // Verifica se o "Lembre de Mim" é falso
        if (informacoesDeLogin != null && informacoesDeLogin.getLembreDeMin() != null && !informacoesDeLogin.getLembreDeMin()) {
            SharedPreferences sharedPref = getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("id", null);
            editor.putString("token", null);
            editor.putString("informacao1", null);
            editor.putString("informacao2", null);
            editor.apply();
        }

        // Objetos para consumir rotas da API
        HttpMain requisicao = new HttpMain();
        HttpMembro requisicaoMembro = new HttpMembro();

        // Renova o token
        if (informacoesDeLogin.getInformacao1() != null && informacoesDeLogin.getInformacao2() != null) {
            LoginRequest loginRequest = new LoginRequest(informacoesDeLogin.getInformacao1(), informacoesDeLogin.getInformacao2());
            System.out.println(loginRequest.toString());

            requisicao.login(loginRequest, new HttpMain.HttpCallback() {
                @Override
                public void onSuccess(String response) {
                    LoginResponse loginResponse = new Gson().fromJson(response, LoginResponse.class);

                    SharedPreferences sharedPref = getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    System.out.println("Renovação do token: " + loginResponse.getToken());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("token", loginResponse.getToken());
                    editor.apply();

                    informacoesDeLogin.setToken(loginResponse.getToken());
                }

                @Override
                public void onFailure(IOException e) {
                    // Handle failure
                }
            });
        }

        // Requisição para API - Pega os eventos que estão na API
        requisicao.ListAllEventos(new HttpMain.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                new Thread(() -> {
                    Type listType = new TypeToken<List<AllEventosListResponse>>() {}.getType();
                    List<AllEventosListResponse> responseEventos2 = new Gson().fromJson(response, listType);
                    responseEventos = responseEventos2;
                    AllEventosListResponse evento = responseEventos.get(0);

                    runOnUiThread(() -> {
                        String StringBase64 = evento.getImagem();
                        String[] partes = StringBase64.split(",");
                        String dadosBase64 = partes[1];

                        byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        imagemEventos.setImageBitmap(decodedByte);
                        nomeEvento.setText(evento.getTitulo());
                    });
                }).start();
            }

            @Override
            public void onFailure(IOException e) {
                System.out.println("Erro na requisição de eventos");
            }

        });

        // Requisição para API - Pega as informações do membro
        if (informacoesDeLogin.getToken() != null) {
            requisicaoMembro.InformacoesDePerfil(informacoesDeLogin, new HttpMain.HttpCallback() {
                @Override
                public void onSuccess(String response) {
                    Gson gson = new Gson();
                    perfilResponse = gson.fromJson(response, PerfilResponse.class);
                    System.out.println(perfilResponse.toString());
                }

                @Override
                public void onFailure(IOException e) {
                    // Handle failure
                }
            });
        }

        // Configura os botões de navegação de eventos
        botaoAvancaEvento.setOnClickListener(v -> {
            if (responseEventos != null && !responseEventos.isEmpty()) {
                if (indexArray < responseEventos.size() - 1) {
                    indexArray++;
                } else {
                    indexArray = 0;
                }

                AllEventosListResponse evento = responseEventos.get(indexArray);
                runOnUiThread(() -> {
                    String StringBase64 = evento.getImagem();
                    String[] partes = StringBase64.split(",");
                    String dadosBase64 = partes.length > 1 ? partes[1] : partes[0];


                    byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    imagemEventos.setImageBitmap(decodedByte);
                    nomeEvento.setText(evento.getTitulo());
                });
            }
        });

        botaoRetrocederEvento.setOnClickListener(v -> {
            if (responseEventos != null && !responseEventos.isEmpty()) {
                if (indexArray > 0) {
                    indexArray--;
                } else {
                    indexArray = responseEventos.size() - 1;
                }

                AllEventosListResponse evento = responseEventos.get(indexArray);
                runOnUiThread(() -> {
                    String StringBase64 = evento.getImagem();
                    String[] partes = StringBase64.split(",");
                    String dadosBase64 = partes.length > 1 ? partes[1] : partes[0];

                    byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    imagemEventos.setImageBitmap(decodedByte);
                    nomeEvento.setText(evento.getTitulo());
                });
            }
        });

        // Configura a navegação do drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        View headerView = navigationView.getHeaderView(0);

        AppCompatImageButton sairButton = headerView.findViewById(R.id.sairButton);
        AppCompatButton verPerfil = headerView.findViewById(R.id.buttonVerPerfil);
        MenuItem deslogar = headerView.findViewById(R.id.nav_logout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        sairButton.setOnClickListener(v -> drawerLayout.closeDrawers());

        verPerfil.setOnClickListener(v -> {
            drawerLayout.closeDrawers();
            Intent intent = new Intent(MainActivity.this, PerfilActivity.class);
            if (perfilResponse != null) {
                intent.putExtra("informacoesPerfil", perfilResponse);
            }
            startActivity(intent);
        });

        btnVerEventos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VizualizaEventosActivity.class);
            startActivity(intent);
        });

        btnPerfil.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.END));

        login.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        loginADM.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginAdmActiviy.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sairButton) {
            navigationView.setVisibility(View.GONE);
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
