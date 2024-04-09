package com.example.hallelapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallelapp.R;
import com.example.hallelapp.databinding.ActivityLoginBinding;
import com.example.hallelapp.databinding.ActivityMainBinding;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.payload.requerimento.LoginRequest;
import com.example.hallelapp.payload.resposta.LoginResponse;
import com.google.gson.Gson;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button buttonlogin = findViewById(R.id.BtnCriarConta);
        EditText txtEmail = findViewById(R.id.TxtEmail);
        EditText textSenha = findViewById(R.id.TxtSenha);
        CheckBox lembreDeMim = findViewById(R.id.LembreMe);


        binding.BtnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String senha = textSenha.getText().toString();

                if (email != null && senha != null) {
                    LoginRequest loginRequest = new LoginRequest(email, senha);
                    HttpMain httpMain = new HttpMain();
                    httpMain.login(loginRequest, new HttpMain.HttpCallback() {
                        @Override
                        public void onSuccess(String response) {
                            System.out.println(response);
                            LoginResponse loginResponse = new Gson().fromJson(response, LoginResponse.class);


                            if (lembreDeMim.isChecked()) {
                                loginResponse.setLembreDeMim(true);
                            } else {
                                loginResponse.setLembreDeMim(false);
                            }

                            System.out.println(loginResponse.toString());
                            SalvarDados(loginResponse);
                        }

                        @Override
                        public void onFailure(IOException e) {

                        }
                    });

                }

            }
        });


    }


    private void SalvarDados(LoginResponse loginResponse){
        String response = loginResponse.toString();

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("dados de login",response);
        editor.apply();

    }

    }