package com.example.hallelapp.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.MainActivity;
import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.model.LocalEvento;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.payload.resposta.ValoresEventoResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MoreInfosActivity extends AppCompatActivity {

    private Button button4;
    private Button buttonVoluntario;
    private Button buttonDoar;

    ValoresEventoResponse valoresEventoResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_infos);

        button4 = findViewById(R.id.button4);
        ImageView imageEvento = findViewById(R.id.imageView7);
        TextView tituloEvento = findViewById(R.id.textView5);
        TextView descricaoEvento = findViewById(R.id.textView6);
        TextView enderecoEvento = findViewById(R.id.textView8);
        TextView dataEvento = findViewById(R.id.textView9);
        TextView horarioEvento = findViewById(R.id.textView10);
        TextView palestrantesEvento = findViewById(R.id.textView12);
        Button btnParticparEvento = findViewById(R.id.button3);
        TextView txtValorSemDesconto = findViewById(R.id.textView22);
        TextView txtValorDescontoMembro = findViewById(R.id.textView24);
        TextView txtValoreDescontoAssociado = findViewById(R.id.textView26);


        // Recuperar o objeto evento da intent
        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");

        HttpMain requisicao = new HttpMain();

        requisicao.ListValoresEvento(evento.getId(), new HttpMain.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                ValoresEventoResponse valoresEventoResponse2 = gson.fromJson(response, ValoresEventoResponse.class);
                valoresEventoResponse = valoresEventoResponse2;
                System.out.println(valoresEventoResponse.toString());

                // Coloque a lógica que depende de valoresEventoResponse aqui dentro
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Aqui você pode acessar valoresEventoResponse e realizar as operações necessárias
                        if (valoresEventoResponse.getValorEvento() != null && valoresEventoResponse.getValorDescontoMembro() != null
                        && valoresEventoResponse.getValorDescontoAssociado() != null) {
                            txtValorSemDesconto.setText(String.valueOf(valoresEventoResponse.getValorEvento()));
                            txtValorDescontoMembro.setText(String.valueOf(valoresEventoResponse.getValorEvento() - valoresEventoResponse.getValorDescontoMembro()));
                            txtValoreDescontoAssociado.setText(String.valueOf(valoresEventoResponse.getValorEvento() - valoresEventoResponse.getValorDescontoAssociado()));
                        } else {
                            // Trate o caso em que valoresEventoResponse é nulo
                        }
                    }
                });
            }

            @Override
            public void onFailure(IOException e) {

            }
        });



        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String StringBase64 = evento.getImagem();

                // Obter a parte da string que contém os dados em base64
                String[] partes = StringBase64.split(",");
                String dadosBase64 = partes[1];

                // Decodificar a string base64 em uma imagem Bitmap
                byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                imageEvento.setImageBitmap(decodedByte);
                tituloEvento.setText(evento.getTitulo());
                descricaoEvento.setText(evento.getDescricao());
                LocalEvento localEvento = evento.getLocalEvento();
                enderecoEvento.setText(localEvento.getLocalizacao());




                // Criando um objeto Date
                Date data = evento.getDate();

                // Criando um formato desejado para a data
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

                // Convertendo o objeto Date em uma string no formato especificado
                String dataString = formato.format(data);

                dataEvento.setText(dataString);
                horarioEvento.setText(evento.getHorario());

                List<String> palestrantes = evento.getPalestrantes();

                StringBuilder palestrantesString = new StringBuilder();

                for (String palestrante : palestrantes) {
                    //
                    palestrantesString.append(palestrante).append(", ");
                }


                if (palestrantesString.length() > 0) {
                    palestrantesString.delete(palestrantesString.length() - 2, palestrantesString.length());
                }

                String palestrantesConcatenados = palestrantesString.toString();

                palestrantesEvento.setText(palestrantesConcatenados);


            }
        });

        buttonVoluntario = findViewById(R.id.buttonVoluntario);


        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoreInfosActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        btnParticparEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoreInfosActivity.this, ParticiparDeEventos.class);
                intent.putExtra("evento", evento); // Adiciona o objeto evento como um extra
                startActivity(intent);
            }
        });


        buttonVoluntario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoreInfosActivity.this,FormVoluntarioActivity.class);
                intent.putExtra("evento", evento); // Adiciona o objeto evento como um extra
                startActivity(intent);
            }
        });


    }
}
