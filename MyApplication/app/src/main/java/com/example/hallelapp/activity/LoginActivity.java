package com.example.hallelapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallelapp.MainActivity;
import com.example.hallelapp.R;
import com.example.hallelapp.databinding.ActivityLoginBinding;
import com.example.hallelapp.databinding.ActivityMainBinding;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.model.InformacoesDaSessao;
import com.example.hallelapp.model.Membro;
import com.example.hallelapp.payload.requerimento.LoginRequest;
import com.example.hallelapp.payload.resposta.LoginResponse;
import com.example.hallelapp.tools.AESExample;
import com.google.gson.Gson;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    //	"email":"lolo93@gmail.com",
    //	"senha":"lolo123"

    private ActivityLoginBinding binding;
    private AlertDialog loadingDialog;

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
        ImageButton imageButton = findViewById(R.id.mostraSenha);
        TextView criarConta = findViewById(R.id.textCriarconta);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(textSenha);
            }
        });


        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                String email = txtEmail.getText().toString();
                String senha = textSenha.getText().toString();

                if (email != null && senha != null) {
                    LoginRequest loginRequest = new LoginRequest(email, senha);
                    HttpMain httpMain = new HttpMain();
                    httpMain.login(loginRequest, new HttpMain.HttpCallback() {
                        @Override
                        public void onSuccess(String response) {
                            System.out.println(response);
                            hideLoadingDialog();
                            LoginResponse loginResponse = new Gson().fromJson(response, LoginResponse.class);


                            Log.d("JSON_RESPONSE", response);




                                SalvarDados(loginResponse,lembreDeMim.isChecked(), senha);



                            InformacoesDaSessao informacoesDaSessao = new InformacoesDaSessao();
                            informacoesDaSessao.setToken(loginResponse.getToken());
                            Membro membro = loginResponse.getMembro();
                            informacoesDaSessao.setId(membro.getId());

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("logadoRecentemente", 1);
                            startActivity(intent);

                        }

                        @Override
                        public void onFailure(IOException e) {

                        }
                    });

                }

            }
        });


        criarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });


    }

    private void SalvarDados(LoginResponse loginResponse, Boolean lembreDeMin, String senhaLogin) {
        Membro membro = loginResponse.getMembro();

        String id = membro.getId();
        String token = loginResponse.getToken();
        String login = membro.getEmail();
        String senha = senhaLogin;



        try {
            // Criptografar email e senha
            String emailCriptografado = AESExample.criptografar(login);
            String senhaCriptografada = AESExample.criptografar(senha);

            SharedPreferences sharedPref = getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("id", id);
            editor.putString("token", token);
            editor.putBoolean("lembreDeMin", lembreDeMin);

            // Salvar as informações criptografadas
            editor.putString("informacao1", emailCriptografado);
            editor.putString("informacao2", senhaCriptografada);

            System.out.println("criptografado :"+senhaCriptografada);

            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
            // Lidar com erros de criptografia aqui
        }

    }


    private void togglePasswordVisibility(EditText editText) {
        int cursorPosition = editText.getSelectionEnd();

        if (editText.getInputType() == (android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

        editText.setSelection(cursorPosition);

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