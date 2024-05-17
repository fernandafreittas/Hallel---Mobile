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

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private ImageButton btnPerfil;
    private Button btnVerEventos;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    private static final int idSair = R.id.sairButton;

    List<AllEventosListResponse> responseEventos;

    InformacoesDaSessao informacoesDeLogin;

    ObterInformacoesDaSecao obterInformacoesDaSecao;

    int indexArray = 0;

    PerfilResponse perfilResponse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        //decalração dos componentes

        NavigationView navigationView = findViewById(R.id.navigation_bar);

        navigationView.setNavigationItemSelectedListener(this);
        btnPerfil = findViewById(R.id.btnperfil);
        navigationView.setVisibility(View.GONE);
        btnVerEventos = findViewById(R.id.btnvertodos);

        ImageView imagemEventos = findViewById(R.id.imgevento);
        ImageButton botaoAvancaEvento = findViewById(R.id.imageButton3);
        ImageButton botaoRetrocederEvento = findViewById(R.id.imageButton2);
        Button login = findViewById(R.id.buttonDoacao);
        Button loginADM = findViewById(R.id.button);




        obterInformacoesDaSecao = new ObterInformacoesDaSecao(this);


        try {
            informacoesDeLogin = obterInformacoesDaSecao.obterDadosSalvos();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(informacoesDeLogin.toString());

       //metodo que verifica se o lembre de Mim == false

      if (informacoesDeLogin.getLembreDeMin()==false){

          SharedPreferences sharedPref = getSharedPreferences(
                  getString(R.string.preference_file_key), Context.MODE_PRIVATE);

          SharedPreferences.Editor editor = sharedPref.edit();
          editor.putString("id", null);
          editor.putString("token", null);
          editor.putString("informacao1",null);
          editor.putString("informacao2",null);
          editor.apply();

       }


      //objetos para consumir rotas da api

        HttpMain requisicao = new HttpMain();
        HttpMembro requisicaoMembro = new HttpMembro();


        //renova o token

        if(informacoesDeLogin.getInformacao1() != null && informacoesDeLogin.getInformacao2() != null){

            LoginRequest loginRequest = new LoginRequest(informacoesDeLogin.getInformacao1(),informacoesDeLogin.getInformacao2());
           System.out.println( loginRequest.toString());

            requisicao.login(loginRequest, new HttpMain.HttpCallback() {
                @Override
                public void onSuccess(String response) {
                    LoginResponse loginResponse = new Gson().fromJson(response, LoginResponse.class);

                    SharedPreferences sharedPref = getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);

                    System.out.println( "renovação do toke"+ loginResponse.getToken());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("token",loginResponse.getToken());
                    editor.apply();

                    informacoesDeLogin.setToken(loginResponse.getToken());

                }

                @Override
                public void onFailure(IOException e) {

                }
            });


        }






        //requisição para api" ela pega os eventos que estão na api"

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


        //requisição para api "pega as informações do membro"
        if(informacoesDeLogin.getToken() != null){

            requisicaoMembro.InformacoesDePerfil(informacoesDeLogin , new HttpMain.HttpCallback() {
                @Override
                public void onSuccess(String response) {
                    Gson gson = new Gson();
                    PerfilResponse perfilResponse2 = gson.fromJson(response, PerfilResponse.class);
                    perfilResponse = perfilResponse2;
                    System.out.println(perfilResponse.toString());

                }

                @Override
                public void onFailure(IOException e) {

                }
            });



        }








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




        drawerLayout = findViewById(R.id.drawer_layout);
        View headerView = navigationView.getHeaderView(0);


        AppCompatImageButton sairButton = headerView.findViewById(R.id.sairButton);
        AppCompatButton verperfil = headerView.findViewById(R.id.buttonVerPerfil);

        MenuItem deslogar = headerView.findViewById(R.id.nav_logout);





        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();







        sairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        verperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.closeDrawers();
                Intent intent = new Intent(MainActivity.this, PerfilActivity.class);

                if(perfilResponse != null){

                intent.putExtra("informacoesPerfil", perfilResponse);

                }
                startActivity(intent);
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
                // Abrir o navigation drawer
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });





        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        loginADM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginAdmActiviy.class);
                startActivity(intent);
            }
        });

        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
          //  Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            //return insets;
        //});


    }





    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.sairButton){

            navigationView.setVisibility(View.GONE);

        }

        return false;

    }





    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}