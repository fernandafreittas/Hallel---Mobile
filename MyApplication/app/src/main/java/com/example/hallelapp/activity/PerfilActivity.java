package com.example.hallelapp.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpAssociado;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.htpp.HttpMembro;
import com.example.hallelapp.model.InformacoesDaSessao;
import com.example.hallelapp.model.StatusMembro;
import com.example.hallelapp.payload.requerimento.BuscarIdAssociadoReq;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.payload.resposta.PagamentoAssociadoPerfilResponse;
import com.example.hallelapp.payload.resposta.PerfilResponse;
import com.example.hallelapp.tools.ObterInformacoesDaSecao;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PerfilActivity extends AppCompatActivity {


    InformacoesDaSessao informacoesDeLogin;

    ObterInformacoesDaSecao obterInformacoesDaSecao;

    String idAssociado;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        HttpMembro requestMembro = new HttpMembro();

        HttpAssociado requestAssociado = new HttpAssociado();

        obterInformacoesDaSecao = new ObterInformacoesDaSecao(this);



        try {
            informacoesDeLogin = obterInformacoesDaSecao.obterDadosSalvos();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        PerfilResponse perfilResponse = (PerfilResponse) getIntent().getSerializableExtra("informacoesPerfil");


        ImageView imagemPerfil = findViewById(R.id.imageView18);

        TextView nomeUsuario = findViewById(R.id.nome_usuarioMain);

        TextView email = findViewById(R.id.emailuser);

        TextView cpf = findViewById(R.id.cpfUser);

        TextView telefone = findViewById(R.id.telefoneuser);

        TextView statusExpirado = findViewById(R.id.statusExpirado);

        TextView statusAtivo = findViewById(R.id.statusAtivo);

        TextView statusPendente = findViewById(R.id.statusPendente);

        EditText data = findViewById(R.id.editTextDate);

        TextView nomePagador = findViewById(R.id.nomePagador);

        TextView dataPagamento = findViewById(R.id.textView36);

        TextView formaDePagamento = findViewById(R.id.textView40);


// Obter o mês e o ano atuais
        Calendar calendar = Calendar.getInstance();
        int mesAtual = calendar.get(Calendar.MONTH) + 1; // Adicionando 1 porque os meses começam em 0
        int anoAtual = calendar.get(Calendar.YEAR);

// Formatar o texto para o formato desejado (por exemplo, MM/yyyy)
        String textoData = String.format("%02d/%d", mesAtual, anoAtual);

// Definir o texto no EditText
        data.setText(textoData);


    if(perfilResponse.getImage()!=null) {

        String suaStringBase64 = perfilResponse.getImage();

        // Obter a parte da string que contém os dados em base64
        String[] partes = suaStringBase64.split(",");
        String dadosBase64 = partes[1];

        // Decodificar a string base64 em uma imagem Bitmap
        byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        imagemPerfil.setImageBitmap(decodedByte);
    }

        nomeUsuario.setText(perfilResponse.getNome());
        email.setText(perfilResponse.getEmail());
        cpf.setText(perfilResponse.getCpf());
        telefone.setText(perfilResponse.getTelefone());

       if(StatusMembro.ATIVO.equals(perfilResponse.getStatus())){

           statusAtivo.setVisibility(View.VISIBLE);
           statusExpirado.setVisibility(View.INVISIBLE);
           statusPendente.setVisibility(View.INVISIBLE);

       }else if(StatusMembro.INATIVO.equals(perfilResponse.getStatus())){

           statusAtivo.setVisibility(View.INVISIBLE);
           statusExpirado.setVisibility(View.VISIBLE);
           statusPendente.setVisibility(View.INVISIBLE);

       }else if(StatusMembro.PENDENTE.equals(perfilResponse.getStatus())) {

           statusAtivo.setVisibility(View.INVISIBLE);
           statusExpirado.setVisibility(View.INVISIBLE);
           statusPendente.setVisibility(View.VISIBLE);

       }

        BuscarIdAssociadoReq buscarIdAssociadoReq = new BuscarIdAssociadoReq(perfilResponse.getEmail());

       requestMembro.BuscarIdAssociado(buscarIdAssociadoReq, informacoesDeLogin, new HttpMain.HttpCallback() {
           @Override
           public void onSuccess(String response) {
               System.out.println("response : " + response);
               idAssociado = response;

               if (idAssociado != null || idAssociado.equals("")){

                   String mes = data.getText().toString().substring(0, 2);
               String ano = data.getText().toString().substring(3);

               requestAssociado.listarPagamentoAssociadoPerfilByMesAndAno(idAssociado, mes, ano, informacoesDeLogin, new HttpMain.HttpCallback() {
                   @Override
                   public void onSuccess(String response) {

                       System.out.println(response);

                       PagamentoAssociadoPerfilResponse pagamentoAssociadoPerfilResponse = new Gson().fromJson(response, PagamentoAssociadoPerfilResponse.class);

                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {


                               if (pagamentoAssociadoPerfilResponse != null) {

                                   nomePagador.setText(perfilResponse.getNome());
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

                   requestAssociado.listarPagamentoAssociadoPerfilByMesAndAno(idAssociado, mes, ano, informacoesDeLogin, new HttpMain.HttpCallback() {
                       @Override
                       public void onSuccess(String response) {

                           System.out.println(response);

                           PagamentoAssociadoPerfilResponse pagamentoAssociadoPerfilResponse = new Gson().fromJson(response,PagamentoAssociadoPerfilResponse.class);

                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {

                                   if (pagamentoAssociadoPerfilResponse != null) {

                                       nomePagador.setText(perfilResponse.getNome());
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
