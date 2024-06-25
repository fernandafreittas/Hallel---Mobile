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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.MainActivity;
import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.model.LocalEvento;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.payload.resposta.ValoresEventoResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public class MoreInfosActivity extends AppCompatActivity {

    private Button button4;
    private Button buttonVoluntario;

    private ImageView imageEvento;
    private TextView tituloEvento;
    private TextView descricaoEvento;
    private TextView enderecoEvento;
    private TextView dataEvento;
    private TextView horarioEvento;
    private TextView palestrantesEvento;
    private TextView txtValorSemDesconto;
    private TextView txtValorDescontoMembro;
    private TextView txtValoreDescontoAssociado;
    private Button btnParticparEvento;
    private Button btnDoar;


    List<AllEventosListResponse> responseEventos;
    AllEventosListResponse evento;

    ValoresEventoResponse valoresEventoResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_infos);

        button4 = findViewById(R.id.button4);
        buttonVoluntario = findViewById(R.id.buttonVoluntario);
        imageEvento = findViewById(R.id.imageView7);
        tituloEvento = findViewById(R.id.textView5);
        descricaoEvento = findViewById(R.id.textView6);
        enderecoEvento = findViewById(R.id.textView8);
        dataEvento = findViewById(R.id.textView9);
        horarioEvento = findViewById(R.id.textView10);
        palestrantesEvento = findViewById(R.id.textView12);
        btnParticparEvento = findViewById(R.id.button3);
        txtValorSemDesconto = findViewById(R.id.textView22);
        txtValorDescontoMembro = findViewById(R.id.textView24);
        txtValoreDescontoAssociado = findViewById(R.id.textView26);
        btnDoar = findViewById(R.id.buttonDoar);

        HttpMain requisicao = new HttpMain();

        requisicao.ListAllEventos(new HttpMain.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                // Processar a resposta em um thread de fundo
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Type listType = new TypeToken<List<AllEventosListResponse>>() {}.getType();
                        List<AllEventosListResponse> responseEventos2 = new Gson().fromJson(response, listType);
                        responseEventos = responseEventos2;

                        int posicao = (int) getIntent().getSerializableExtra("position");
                        // Recuperar o objeto evento da lista
                        evento = responseEventos.get(posicao);

                        // Após obter o evento, carregue os detalhes na UI
                        loadEventDetails();
                    }
                });
            }

            @Override
            public void onFailure(IOException e) {
                // Lida com a falha na requisição
                // Por exemplo, você pode exibir uma mensagem de erro para o usuário
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MoreInfosActivity.this, "Falha ao carregar detalhes do evento.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

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
                if (evento != null) {
                    Intent intent = new Intent(MoreInfosActivity.this, ParticiparDeEventos.class);
                    intent.putExtra("evento", evento); // Adiciona o objeto evento como um extra
                    startActivity(intent);
                } else {
                    Toast.makeText(MoreInfosActivity.this, "Evento não disponível. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonVoluntario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evento != null) {
                    Intent intent = new Intent(MoreInfosActivity.this, FormVoluntarioActivity.class);
                    intent.putExtra("evento", evento); // Adiciona o objeto evento como um extra
                    startActivity(intent);
                } else {
                    Toast.makeText(MoreInfosActivity.this, "Evento não disponível. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDoar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evento != null) {
                    Intent intent = new Intent(MoreInfosActivity.this, FormDoacoesActivity.class);
                    intent.putExtra("evento", evento); // Adiciona o objeto evento como um extra
                    startActivity(intent);
                } else {
                    Toast.makeText(MoreInfosActivity.this, "Evento não disponível. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadEventDetails() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (evento != null) {
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
                        palestrantesString.append(palestrante).append(", ");
                    }

                    if (palestrantesString.length() > 0) {
                        palestrantesString.delete(palestrantesString.length() - 2, palestrantesString.length());
                    }

                    String palestrantesConcatenados = palestrantesString.toString();

                    palestrantesEvento.setText(palestrantesConcatenados);

                } else {
                    Toast.makeText(MoreInfosActivity.this, "Evento não disponível. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

