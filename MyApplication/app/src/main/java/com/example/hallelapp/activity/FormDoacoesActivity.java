package com.example.hallelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallelapp.R;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;

public class FormDoacoesActivity extends AppCompatActivity {


    private RadioGroup radioGroupSexo;
    private RadioButton radioMasculino, radioFeminino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_doacoes);


        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");


        Button continuar = findViewById(R.id.btncontinuarform);
        EditText txtnome = findViewById(R.id.inputNomeform);
        EditText txtemail = findViewById(R.id.inputEmailform);
        EditText txtCPF = findViewById(R.id.inputCPFform);
        EditText txtNumeroDeTelefone = findViewById(R.id.inputIdadeform);


        // Inicializando o RadioGroup e RadioButtons
        radioGroupSexo = findViewById(R.id.radioGroupSexo);
        radioMasculino = findViewById(R.id.radioMasculinoform);
        radioFeminino = findViewById(R.id.radioFemininoform);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = txtnome.getText().toString().trim();
                String email = txtemail.getText().toString().trim();
                String cpf = txtCPF.getText().toString().trim();
                String telefone = txtNumeroDeTelefone.getText().toString().trim();
                int selectedSexoId = radioGroupSexo.getCheckedRadioButtonId();

                // Verifica se todos os campos foram preenchidos
                if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || selectedSexoId == -1) {
                    // Exibe um Toast se algum campo estiver vazio
                    Toast.makeText(FormDoacoesActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return; // Interrompe a execução se algum campo estiver vazio
                }

                // Validação de e-mail usando expressão regular
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(FormDoacoesActivity.this, "E-mail inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Se tudo estiver correto, prossegue para a próxima activity
                Intent intent = new Intent(FormDoacoesActivity.this, DirecionamentoDoacaoActivity.class);
                intent.putExtra("evento", evento); // Adiciona o objeto evento como um extra
                intent.putExtra("nome", nome);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

// Adiciona formatação ao campo de telefone
        txtNumeroDeTelefone.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;
            private String oldString = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Não precisa fazer nada aqui
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String digitsOnly = s.toString().replaceAll("[^\\d]", ""); // Remove qualquer coisa que não seja dígito
                String formatted = "";

                if (digitsOnly.length() <= 2) {
                    formatted = "(" + digitsOnly;
                } else if (digitsOnly.length() <= 7) {
                    formatted = "(" + digitsOnly.substring(0, 2) + ")" + digitsOnly.substring(2);
                } else if (digitsOnly.length() <= 11) {
                    formatted = "(" + digitsOnly.substring(0, 2) + ")" + digitsOnly.substring(2, 7) + "-" + digitsOnly.substring(7);
                } else {
                    formatted = "(" + digitsOnly.substring(0, 2) + ")" + digitsOnly.substring(2, 7) + "-" + digitsOnly.substring(7, 11);
                }

                isUpdating = true;
                txtNumeroDeTelefone.setText(formatted);
                txtNumeroDeTelefone.setSelection(formatted.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Adiciona formatação ao campo de CPF
        txtCPF.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Não precisa fazer nada aqui
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String digitsOnly = s.toString().replaceAll("[^\\d]", ""); // Remove qualquer coisa que não seja dígito
                String formatted = "";

                // Aplica a formatação do CPF
                if (digitsOnly.length() <= 3) {
                    formatted = digitsOnly;
                } else if (digitsOnly.length() <= 6) {
                    formatted = digitsOnly.substring(0, 3) + "." + digitsOnly.substring(3);
                } else if (digitsOnly.length() <= 9) {
                    formatted = digitsOnly.substring(0, 3) + "." + digitsOnly.substring(3, 6) + "." + digitsOnly.substring(6);
                } else if (digitsOnly.length() <= 11) {
                    formatted = digitsOnly.substring(0, 3) + "." + digitsOnly.substring(3, 6) + "." + digitsOnly.substring(6, 9) + "-" + digitsOnly.substring(9);
                }

                isUpdating = true;
                txtCPF.setText(formatted);
                txtCPF.setSelection(formatted.length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Não precisa fazer nada aqui
            }
        });




    }



}