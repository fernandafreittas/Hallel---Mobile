package com.example.hallelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallelapp.MainActivity;
import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.htpp.HttpMembro;
import com.example.hallelapp.model.CartaoCredito;
import com.example.hallelapp.model.InformacoesDaSessao;
import com.example.hallelapp.payload.requerimento.ParticiparEventosRequest;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.tools.ObterInformacoesDaSecao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParticiparDeEventos extends AppCompatActivity {


    InformacoesDaSessao informacoesDeLogin;

    ObterInformacoesDaSecao obterInformacoesDaSecao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_participar_de_eventos);



        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");

        HttpMain requisicao = new HttpMain();
        HttpMembro requisicaoMembro = new HttpMembro();



        obterInformacoesDaSecao = new ObterInformacoesDaSecao(this);


        informacoesDeLogin = obterInformacoesDaSecao.obterDadosSalvos();


        EditText txtnome = findViewById(R.id.inputNome);
        EditText txtEmail = findViewById(R.id.inputEmail);
        EditText txtCpf = findViewById(R.id.inputCPF);
        EditText txtIdade = findViewById(R.id.inputIdade);
        //cartao
        EditText txtNomeTitular = findViewById(R.id.nome_titular);
        EditText txtNumeroCartao = findViewById(R.id.numero_cartao);
        EditText txtValidade = findViewById(R.id.input_validade);
        EditText txtCVC = findViewById(R.id.inputCVC);
        EditText txtEndereco = findViewById(R.id.inputEndereço);
        Button btnParticipar = findViewById(R.id.button5);


        ParticiparEventosRequest participarEventosRequest = new ParticiparEventosRequest();



        //realiza a inscrição

        btnParticipar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                participarEventosRequest.setIdEvento(evento.getId());
                participarEventosRequest.setId(informacoesDeLogin.getId());
                participarEventosRequest.setNome(txtnome.getText().toString());
                participarEventosRequest.setEmail(txtEmail.getText().toString());
                participarEventosRequest.setCpf(txtCpf.getText().toString());
                participarEventosRequest.setIdade(Integer.parseInt(txtIdade.getText().toString()));

                CartaoCredito cartaoCredito = new CartaoCredito();
                cartaoCredito.setNomeTitular(txtNomeTitular.getText().toString());
                cartaoCredito.setNumeroCartao(txtNumeroCartao.getText().toString());

                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");

                // Obtém a string da data de validade do seu texto
                String dataValidadeString = txtValidade.getText().toString();

                // Tenta analisar a string de data em um objeto Date
                try {
                    Date dataValidade = dateFormat.parse(dataValidadeString);
                    // Define a data de validade no objeto cartaoCredito
                    cartaoCredito.setDataValidadeCartao(dataValidade);

                } catch (ParseException e) {
                    // Se houver um erro ao analisar a data, trate-o adequadamente
                    e.printStackTrace();
                }
                cartaoCredito.setEndereco(txtEndereco.getText().toString());
                participarEventosRequest.setCartaoCredito(cartaoCredito);




                System.out.println("tokeeeeeeen : "+informacoesDeLogin.getToken());
                System.out.println(participarEventosRequest.toString());

                if(informacoesDeLogin.getToken()!=""){
                    requisicaoMembro.participarDeEvento(participarEventosRequest, informacoesDeLogin, new HttpMembro.HttpCallback() {
                        @Override
                        public void onSuccess(String response) {

                            System.out.println("deu certo membro !");

                            Intent intent = new Intent(ParticiparDeEventos.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(IOException e) {
                            System.out.println("deu errado membro !");
                        }
                    });
                }else {
                    requisicao.ParticiparDeEvento(participarEventosRequest, new HttpMain.HttpCallback() {
                        @Override
                        public void onSuccess(String response) {
                            System.out.println("deu certo !");

                            Intent intent = new Intent(ParticiparDeEventos.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(IOException e) {
                            System.out.println("deu errado  !");
                        }
                    });
                }




            }
        });





    }
}