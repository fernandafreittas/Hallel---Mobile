package com.example.hallelapp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpAdm;
import com.example.hallelapp.htpp.HttpAssociado;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.model.Associado;
import com.example.hallelapp.model.AssociadoStatus;
import com.example.hallelapp.model.StatusMembro;
import com.example.hallelapp.payload.requerimento.BuscarIdAssociadoReq;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.example.hallelapp.payload.resposta.PagamentoAssociadoPerfilResponse;
import com.example.hallelapp.tools.ObterInformacoesDaSecao;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DadosAssociadoActivity extends AppCompatActivity {

    HttpAssociado requestAssociado = new HttpAssociado();

    HttpAdm request = new HttpAdm();

    String idAssociado;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dados_associado);


        AuthenticationResponse authenticationResponse = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");

        Associado associado =  (Associado) getIntent().getSerializableExtra("associado");

        TextView nome = findViewById(R.id.nomeCompletoAssociado);

        TextView telefone = findViewById(R.id.numeroTelAssociado);

        TextView email = findViewById(R.id.emailAssociado);

        TextView cpf = findViewById(R.id.cpfassociado);

        TextView dataNascimento = findViewById(R.id.dataNascAssociado);

        TextView statusExpirado = findViewById(R.id.statusExpiradoassociado);

        TextView statusAtivo = findViewById(R.id.statusAtivoassociado);

        TextView statusPendente = findViewById(R.id.statusPendenteassociado);

        EditText data = findViewById(R.id.editTextDateassociado);

        TextView nomePagador = findViewById(R.id.nomePagadorassociado);

        TextView dataPagamento = findViewById(R.id.textView36associado);

        TextView formaDePagamento = findViewById(R.id.textView40associado);


        // Obter o mês e o ano atuais
        Calendar calendar = Calendar.getInstance();
        int mesAtual = calendar.get(Calendar.MONTH) + 1; // Adicionando 1 porque os meses começam em 0
        int anoAtual = calendar.get(Calendar.YEAR);

// Formatar o texto para o formato desejado (por exemplo, MM/yyyy)
        String textoData = String.format("%02d/%d", mesAtual, anoAtual);

// Definir o texto no EditText
        data.setText(textoData);



        System.out.println(associado.toString());
        System.out.println(associado.getEmail());
        System.out.println(associado.getNome());

        nome.setText(associado.getNome());

        telefone.setText(associado.getTelefone());

        email.setText(associado.getEmail());

        cpf.setText(associado.getCpf());


        dataNascimento.setText(associado.getDataNascimentoAssociado());

        statusAtivo.setVisibility(View.INVISIBLE);
        statusExpirado.setVisibility(View.INVISIBLE);
        statusPendente.setVisibility(View.INVISIBLE);


        if(AssociadoStatus.PAGO.equals(associado.getIsAssociado())){

            statusAtivo.setVisibility(View.VISIBLE);
            statusExpirado.setVisibility(View.INVISIBLE);
            statusPendente.setVisibility(View.INVISIBLE);

        }else if(AssociadoStatus.NAO_PAGO.equals(associado.getIsAssociado())){

            statusAtivo.setVisibility(View.INVISIBLE);
            statusExpirado.setVisibility(View.VISIBLE);
            statusPendente.setVisibility(View.INVISIBLE);

        }else if(AssociadoStatus.PENDENTE.equals(associado.getIsAssociado())) {

            statusAtivo.setVisibility(View.INVISIBLE);
            statusExpirado.setVisibility(View.INVISIBLE);
            statusPendente.setVisibility(View.VISIBLE);

        }





        BuscarIdAssociadoReq buscarIdAssociadoReq = new BuscarIdAssociadoReq(associado.getEmail());

        request.BuscarIdAssociadoADM(buscarIdAssociadoReq, authenticationResponse, new HttpMain.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response : " + response);
                idAssociado = response;

                if (idAssociado != null || idAssociado.equals("")){

                    String mes = data.getText().toString().substring(0, 2);
                    String ano = data.getText().toString().substring(3);

                    request.listarPagamentoAssociadoPerfilByMesAndAnoADM(idAssociado, mes, ano, authenticationResponse, new HttpMain.HttpCallback() {
                        @Override
                        public void onSuccess(String response) {

                            System.out.println(response);

                            PagamentoAssociadoPerfilResponse pagamentoAssociadoPerfilResponse = new Gson().fromJson(response, PagamentoAssociadoPerfilResponse.class);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (pagamentoAssociadoPerfilResponse != null) {

                                        nomePagador.setText(associado.getNome());
                                        Date dataPagamentoDate = pagamentoAssociadoPerfilResponse.getDate();
                                        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
                                        String dataFormatada = formatoData.format(dataPagamentoDate);
                                        dataPagamento.setText(dataFormatada);
                                        formaDePagamento.setText(pagamentoAssociadoPerfilResponse.getMetodoPag().toString());

                                    }else {

                                        nomePagador.setText("nenhum pagamento realizado");
                                        dataPagamento.setText("");
                                        formaDePagamento.setText("");
                                    }



                                }
                            });


                        }

                        @Override
                        public void onFailure(IOException e) {

                        }
                    });

                }else{
                    nomePagador.setText("nenhum pagamento realizado");
                    dataPagamento.setText("");
                    formaDePagamento.setText("");

                }



            }

            @Override
            public void onFailure(IOException e) {

            }
        });






        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setText("");
            }
        });

        data.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if (input.length() == 7) {



                    String mes = data.getText().toString().substring(0, 2);
                    String ano = data.getText().toString().substring(3);

                    request.listarPagamentoAssociadoPerfilByMesAndAnoADM(idAssociado, mes, ano, authenticationResponse, new HttpMain.HttpCallback() {
                        @Override
                        public void onSuccess(String response) {

                            System.out.println(response);

                            PagamentoAssociadoPerfilResponse pagamentoAssociadoPerfilResponse = new Gson().fromJson(response,PagamentoAssociadoPerfilResponse.class);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (pagamentoAssociadoPerfilResponse != null) {

                                        nomePagador.setText(associado.getNome());
                                        Date dataPagamentoDate = pagamentoAssociadoPerfilResponse.getDate();
                                        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
                                        String dataFormatada = formatoData.format(dataPagamentoDate);
                                        dataPagamento.setText(dataFormatada);
                                        formaDePagamento.setText(pagamentoAssociadoPerfilResponse.getMetodoPag().toString());

                                    }else {

                                        nomePagador.setText("nenhum pagamento realizado");
                                        dataPagamento.setText("");
                                        formaDePagamento.setText("");
                                    }
                                }


                            });



                        }

                        @Override
                        public void onFailure(IOException e) {

                        }
                    });



                }
            }
        });








    }
}