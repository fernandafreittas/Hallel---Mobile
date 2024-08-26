package com.example.hallelapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.hallelapp.activity.DirecionamentoDoacaoUser;
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

    // Declaração de componentes da interface
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

    int logadoRecentemente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inicialização dos componentes da interface
        navigationView = findViewById(R.id.navigation_bar);
        navigationView.setNavigationItemSelectedListener(this);
        btnPerfil = findViewById(R.id.btnperfil);
        navigationView.setVisibility(View.GONE);
        btnVerEventos = findViewById(R.id.btnvertodos);
        nomeEvento = findViewById(R.id.nomeevento);
        ImageView imagemEventos = findViewById(R.id.imgevento);
        ImageButton botaoAvancaEvento = findViewById(R.id.imageButton3);
        ImageButton botaoRetrocederEvento = findViewById(R.id.imageButton2);


        // Verifica se o extra "logadoRecentemente" não é nulo antes de fazer o cast
        if (getIntent().getSerializableExtra("logadoRecentemente") != null) {
            logadoRecentemente = (int) getIntent().getSerializableExtra("logadoRecentemente");
            // Use logadoRecentemente conforme necessário
        } else {
            // Trate o caso onde "logadoRecentemente" é nulo
            Log.e("MainActivity", "logadoRecentemente é nulo!");
            // Defina um valor padrão ou tome outra ação adequada
            logadoRecentemente = 0;
        }


        // Configuração do DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);
        View headerView = navigationView.getHeaderView(0);


        //inicialização do objeto obterInformacoesDaSecao
        obterInformacoesDaSecao = new ObterInformacoesDaSecao(this);

        // Verifica e obtém dados de login salvos
        try {
            informacoesDeLogin = obterInformacoesDaSecao.obterDadosSalvos();
        } catch (Exception e) {
            informacoesDeLogin = new InformacoesDaSessao();
        }

        // Configuração dos botões
        btnVerEventos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MoreInfosActivity.class);
            startActivity(intent);
        });

        btnPerfil.setOnClickListener(v -> {
            if (navigationView.getVisibility() == View.VISIBLE) {
                navigationView.setVisibility(View.GONE);
            } else {
                navigationView.setVisibility(View.VISIBLE);
            }
        });

        // Verifica se "Lembre de Mim" é falso e limpa os dados de login
        if (informacoesDeLogin != null && informacoesDeLogin.getLembreDeMin() != null && !informacoesDeLogin.getLembreDeMin() && logadoRecentemente != 1) {
            limparDados();
            informacoesDeLogin.setInformacao1(null);
            informacoesDeLogin.setInformacao2(null);
            System.out.println("limpeza");
        }

        // Objetos para consumir rotas da API
        HttpMain requisicao = new HttpMain();
        HttpMembro requisicaoMembro = new HttpMembro();

        // Renova o token
        if (informacoesDeLogin.getInformacao1() != null && informacoesDeLogin.getInformacao2() != null) {
            LoginRequest loginRequest = new LoginRequest(informacoesDeLogin.getInformacao1(), informacoesDeLogin.getInformacao2());
            requisicao.login(loginRequest, new HttpMain.HttpCallback() {
                @Override
                public void onSuccess(String response) {
                    System.out.println("deu certo informações de login");

                    LoginResponse loginResponse = new Gson().fromJson(response, LoginResponse.class);
                    SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("token", loginResponse.getToken());
                    editor.apply();
                    informacoesDeLogin.setToken(loginResponse.getToken());

                    // Atualiza a interface com as informações de perfil
                    AppCompatTextView txtNome = headerView.findViewById(R.id.textView);
                    AppCompatTextView txtEmail = headerView.findViewById(R.id.textView3);
                    AppCompatImageView imgPerfil = headerView.findViewById(R.id.imageView5);

                    if (perfilResponse != null) {
                        txtNome.setText(perfilResponse.getNome());
                        txtEmail.setText(perfilResponse.getEmail());
                        if (perfilResponse.getImage() != null) {
                            String suaStringBase64 = perfilResponse.getImage();
                            String[] partes = suaStringBase64.split(",");
                            String dadosBase64 = partes[1];
                            byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imgPerfil.setImageBitmap(decodedByte);
                        }
                    }
                }

                @Override
                public void onFailure(IOException e) {

                    atualizarInterfaceDeslogado();


                }
            });
        } else {
            atualizarInterfaceDeslogado();
        }

        // Requisição para API - Pega os eventos que estão na API
        requisicao.ListAllEventos(new HttpMain.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                new Thread(() -> {
                    Type listType = new TypeToken<List<AllEventosListResponse>>() {
                    }.getType();
                    responseEventos = new Gson().fromJson(response, listType);

                    if (responseEventos != null && !responseEventos.isEmpty()) {
                        AllEventosListResponse evento = responseEventos.get(0);

                        runOnUiThread(() -> {
                            String StringBase64 = evento.getImagem();
                            if (StringBase64 != null && !StringBase64.isEmpty()) {
                                String[] partes = StringBase64.split(",");
                                if (partes.length > 1) {
                                    String dadosBase64 = partes[1];
                                    byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    imagemEventos.setImageBitmap(decodedByte);
                                    nomeEvento.setText(evento.getTitulo());
                                } else {
                                    // Trate o caso onde a String não está no formato esperado
                                    imagemEventos.setImageBitmap(null);
                                    nomeEvento.setText(evento.getTitulo());
                                }
                            } else {
                                // Trate o caso onde a imagem é nula ou vazia
                                imagemEventos.setImageBitmap(null);
                                nomeEvento.setText(evento.getTitulo());
                            }
                        });
                    } else {
                        runOnUiThread(() -> {
                            // Trate o caso em que não há eventos
                            nomeEvento.setText("Nenhum evento disponível");
                        });
                    }
                }).start();
            }

            @Override
            public void onFailure(IOException e) {
                System.out.println("Erro na requisição de eventos");
            }
        });


        // Requisição para API - Pega as informações do membro
        if (informacoesDeLogin.getToken() != null) {





            System.out.println(informacoesDeLogin.getToken());
            System.out.println(informacoesDeLogin.toString());
            requisicaoMembro.InformacoesDePerfil(informacoesDeLogin, new HttpMain.HttpCallback() {
                @Override
                public void onSuccess(String response) {
                    System.out.println("deu certo informações de perfil");
                    Gson gson = new Gson();
                    perfilResponse = gson.fromJson(response, PerfilResponse.class);
                }

                @Override
                public void onFailure(IOException e) {
                    // Lida com falhas na obtenção do perfil
                }
            });

    }

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
                    if (StringBase64 != null && !StringBase64.isEmpty()) {
                        String[] partes = StringBase64.split(",");
                        if (partes.length > 1) {
                            String dadosBase64 = partes[1];
                            byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imagemEventos.setImageBitmap(decodedByte);
                            nomeEvento.setText(evento.getTitulo());
                        } else {
                            // Trate o caso onde a String não está no formato esperado
                            imagemEventos.setImageBitmap(null);
                            nomeEvento.setText(evento.getTitulo());
                        }
                    } else {
                        // Trate o caso onde a imagem é nula ou vazia
                        imagemEventos.setImageBitmap(null);
                        nomeEvento.setText(evento.getTitulo());
                    }
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
                    if (StringBase64 != null && !StringBase64.isEmpty()) {
                        String[] partes = StringBase64.split(",");
                        if (partes.length > 1) {
                            String dadosBase64 = partes[1];
                            byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imagemEventos.setImageBitmap(decodedByte);
                            nomeEvento.setText(evento.getTitulo());
                        } else {
                            // Trate o caso onde a String não está no formato esperado
                            imagemEventos.setImageBitmap(null);
                            nomeEvento.setText(evento.getTitulo());
                        }
                    } else {
                        // Trate o caso onde a imagem é nula ou vazia
                        imagemEventos.setImageBitmap(null);
                        nomeEvento.setText(evento.getTitulo());
                    }
                });
            }
        });



        // Configura o botão de sair e visualizar perfil no drawer
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


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sairButton) {
            navigationView.setVisibility(View.GONE);
        }
        if (id == R.id.nav_logout) {
            logout();
        }
        if (id == R.id.nav_login) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Remove todas as atividades anteriores
            startActivity(intent);
            finish(); // Finaliza a MainActivity
        }

        if (id == R.id.nav_home11) {
            Intent intent = new Intent(MainActivity.this, LoginAdmActiviy.class);
            startActivity(intent);
        }

        if (id == R.id.nav_home13) {
            drawerLayout.closeDrawers();
            Intent intent = new Intent(MainActivity.this, DirecionamentoDoacaoUser.class);
            if (perfilResponse != null) {
                intent.putExtra("informacoesPerfil", perfilResponse);
            }
            startActivity(intent);
        }


        return false;
    }

    private void logout() {
        // Limpa as informações de login salvas
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear(); // Remove todas as informações salvas
        editor.apply();

        // Volta para a tela de login
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Remove todas as atividades anteriores
        startActivity(intent);
        finish(); // Finaliza a MainActivity
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void limparDados(){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id", null);
        editor.putString("token", null);
        editor.putString("informacao1", null);
        editor.putString("informacao2", null);
        editor.apply();
    }



    private void atualizarInterfaceDeslogado() {
        navigationView.removeHeaderView(navigationView.getHeaderView(0));
        navigationView.inflateHeaderView(R.layout.header_menu_off);

        // Configure aqui os componentes da header para o estado deslogado
        View headerView = navigationView.getHeaderView(0);
        Button btnLogin = headerView.findViewById(R.id.buttonFazerLogin);
        ImageButton btnSair = headerView.findViewById(R.id.sairButtonOff);
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Remove todas as atividades anteriores
            startActivity(intent);
            finish(); // Finaliza a MainActivity
        });
        btnSair.setOnClickListener(v -> drawerLayout.closeDrawers());

    }



}

