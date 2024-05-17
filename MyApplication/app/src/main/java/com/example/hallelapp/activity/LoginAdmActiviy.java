package com.example.hallelapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpAdm;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.model.InformacoesDaSessao;
import com.example.hallelapp.model.Membro;
import com.example.hallelapp.payload.requerimento.AdministradorLoginRequest;
import com.example.hallelapp.payload.requerimento.LoginRequest;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.example.hallelapp.payload.resposta.LoginResponse;
import com.example.hallelapp.tools.AESExample;
import com.google.gson.Gson;

import java.io.IOException;

public class LoginAdmActiviy extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_adm_activiy);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button buttonlogin = findViewById(R.id.BtnCriarConta);
        EditText txtEmail = findViewById(R.id.TxtEmail);
        EditText textSenha = findViewById(R.id.TxtSenha);
        ImageButton imageButton = findViewById(R.id.mostraSenha);




        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(textSenha);
            }
        });


        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String senha = textSenha.getText().toString();

                if (email != null && senha != null) {
                    AdministradorLoginRequest admLoginRequest = new AdministradorLoginRequest(email, senha);
                    HttpAdm httpAdm = new HttpAdm();
                    httpAdm.RealizarLogin(admLoginRequest, new HttpAdm.HttpCallback() {

                    @Override
                        public void onSuccess(String response) {
                            System.out.println(response);

                          AuthenticationResponse authenticationResponse = new Gson().fromJson(response, AuthenticationResponse.class);


                            Log.d("JSON_RESPONSE", response);


                            System.out.println(authenticationResponse.toString());



                        Intent intent = new Intent(LoginAdmActiviy.this, MainAdmActivity.class);
                        intent.putExtra("informaçõesADM", authenticationResponse);
                        startActivity(intent);


                        }

                        @Override
                        public void onFailure(IOException e) {

                        }
                    });

                }

            }
        });



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



}
