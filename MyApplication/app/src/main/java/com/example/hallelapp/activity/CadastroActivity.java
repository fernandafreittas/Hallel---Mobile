package com.example.hallelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallelapp.MainActivity;
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
        ImageButton mostrasenha = findViewById(R.id.mostraSenha);
        ImageButton mostraConfirmaSenha = findViewById(R.id.mostraConfirmarSenha);
        TextView fazerLogin = findViewById(R.id.fazerLogin);

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
                        Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                        startActivity(intent);
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



        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = txtNome.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String senha = txtSenha.getText().toString();
                String confirmaSenha = txtConfirmaSenha.getText().toString();

                // Verificar se todos os campos estão preenchidos
                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmaSenha.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Todos os campos devem ser preenchidos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar se o e-mail está em um formato válido
                if (!isValidEmail(email)) {
                    Toast.makeText(CadastroActivity.this, "Insira um e-mail válido.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar se as senhas correspondem
                if (!senha.equals(confirmaSenha)) {
                    Toast.makeText(CadastroActivity.this, "Os campos de senha estão diferentes.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Prosegue com o cadastro
                CadastroRequest cadastroRequest = new CadastroRequest(nome, email, senha);
                HttpMain httpMain = new HttpMain();
                httpMain.cadastrar(cadastroRequest, new HttpMain.HttpCallback() {
                    @Override
                    public void onSuccess(String response) {
                        runOnUiThread(()->{
                            showSuccessDialog();
                        });

                    }

                    @Override
                    public void onFailure(IOException e) {

                        runOnUiThread(()->{
                            showErrorDialog();
                        });


                    }
                });
            }
        });

        fazerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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


    private void showErrorDialog() {
        // Inflate o layout do diálogo de erro
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_errocadastro, null);

        // Cria o dialog a partir do layout inflado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Clique no botão de continuar para fechar o diálogo
        Button btnContinuar = dialogView.findViewById(R.id.buttonErrc);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showSuccessDialog() {
        // Inflate o layout do diálogo de sucesso
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_cadastrosucesso, null);

        // Cria o dialog a partir do layout inflado
       AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Clique no botão de continuar para redirecionar à página de login ou outra ação
        Button btnContinuar = dialogView.findViewById(R.id.button3bolgerados);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // Redireciona para a tela de login, por exemplo
                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }



}
