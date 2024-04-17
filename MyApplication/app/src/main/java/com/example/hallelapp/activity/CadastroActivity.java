package com.example.hallelapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.payload.requerimento.CadastroRequest;

import java.io.IOException;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });






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


        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("teste1");

                HttpMain requisicao = new HttpMain();
                requisicao.ListAllEventos(new HttpMain.HttpCallback() {

                    @Override
                    public void onSuccess(String response) {
                        System.out.println("teste2");
                        // Lida com a resposta bem-sucedida
                        // Por exemplo, você pode processar os eventos recebidos ou atualizar a interface do usuário
                    }

                    @Override
                    public void onFailure(IOException e) {
                        System.out.println("teste3");
                        // Lida com a falha na requisição
                        // Por exemplo, você pode exibir uma mensagem de erro para o usuário
                    }
                });


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
