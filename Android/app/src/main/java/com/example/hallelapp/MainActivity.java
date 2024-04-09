package com.example.hallelapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.databinding.ActivityLoginBinding;
import com.example.hallelapp.databinding.ActivityMainBinding;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.payload.requerimento.CadastroRequest;
import com.example.hallelapp.payload.requerimento.LoginRequest;
import com.example.hallelapp.payload.resposta.LoginResponse;
import com.google.gson.Gson;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);








        HttpMain requisicao = new HttpMain();

        //componentes
        Button cadastrar = findViewById(R.id.BtnCriarConta);
        EditText txtNome = findViewById(R.id.TxtNome);
        EditText txtEmail = findViewById(R.id.TxtEmail);
        EditText txtSenha = findViewById(R.id.PsSenha);
        EditText txtConfirmaSenha = findViewById(R.id.PsConfirmarSenha);
        TextView txtErro = findViewById(R.id.txtErro);
        ImageButton mostrasenha = findViewById(R.id.mostraSenha);
        ImageButton mostraConfirmaSenha = findViewById(R.id.mostraConfirmarSenha);



        //mostra a senha
        mostrasenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(txtSenha);
            }
        });

        mostraConfirmaSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(txtConfirmaSenha);
            }
        });





        //evento disparado quando clicar no botão de criar conta
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = txtNome.getText().toString();
                String email = txtEmail.getText().toString();

                System.out.println("oi!");
                if(txtSenha.getText().toString().equals( txtConfirmaSenha.getText().toString())){

                    String senha = txtSenha.getText().toString();

                    CadastroRequest cadastroRequest = new CadastroRequest(nome,email,senha);
                    HttpMain httpMain = new HttpMain();
                    httpMain.cadastrar(cadastroRequest, new HttpMain.HttpCallback() {
                        @Override
                        public void onSuccess(String response) {
                            // Lida com a resposta bem-sucedida
                        }

                        @Override
                        public void onFailure(IOException e) {
                            // Lida com a falha na requisição
                        }
                    });
                }else {
                    txtErro.setText("Erro senhas digitadas não são iguais");
                }




            }
        });

    }

 //metodo para mostrar a senha dos campos
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