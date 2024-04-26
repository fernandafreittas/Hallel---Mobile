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
import com.example.hallelapp.model.LocalEvento;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MoreInfosActivity extends AppCompatActivity {

    private Button button4;

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

        // Recuperar o objeto evento da intent
        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");



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

    }
}
