package com.example.hallelapp.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
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
    private AlertDialog loadingDialog;

    private ImageView imageEvento;
    private ImageView imageEndereco;
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

    private List<AllEventosListResponse> responseEventos;
    private AllEventosListResponse evento;
    private ValoresEventoResponse valoresEventoResponse;

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
        imageEndereco = findViewById(R.id.imageView12);

        HttpMain requisicao = new HttpMain();

        showLoadingDialog();

        requisicao.ListAllEventos(new HttpMain.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    Type listType = new TypeToken<List<AllEventosListResponse>>() {}.getType();
                    responseEventos = new Gson().fromJson(response, listType);

                    int posicao = (int) getIntent().getSerializableExtra("position");
                    evento = responseEventos.get(posicao);

                    loadEventDetails();

                    requisicao.ListValoresEvento(evento.getId(), new HttpMain.HttpCallback() {
                        @Override
                        public void onSuccess(String response) {
                            runOnUiThread(() -> {
                                valoresEventoResponse = new Gson().fromJson(response, ValoresEventoResponse.class);

                                if (valoresEventoResponse != null && valoresEventoResponse.getValorEvento() != null && valoresEventoResponse.getValorDescontoMembro() != null && valoresEventoResponse.getValorDescontoAssociado() != null) {
                                    txtValorSemDesconto.setText(String.valueOf(valoresEventoResponse.getValorEvento()));
                                    txtValorDescontoMembro.setText(String.valueOf(valoresEventoResponse.getValorEvento() - valoresEventoResponse.getValorDescontoMembro()));
                                    txtValoreDescontoAssociado.setText(String.valueOf(valoresEventoResponse.getValorEvento() - valoresEventoResponse.getValorDescontoAssociado()));
                                } else {
                                    Toast.makeText(MoreInfosActivity.this, "Falha ao carregar valores do evento.", Toast.LENGTH_SHORT).show();
                                }

                                hideLoadingDialog();
                            });
                        }

                        @Override
                        public void onFailure(IOException e) {
                            runOnUiThread(() -> {
                                Toast.makeText(MoreInfosActivity.this, "Falha ao carregar detalhes do evento.", Toast.LENGTH_SHORT).show();
                                hideLoadingDialog();
                            });
                        }
                    });
                });
            }

            @Override
            public void onFailure(IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(MoreInfosActivity.this, "Falha ao carregar eventos.", Toast.LENGTH_SHORT).show();
                    hideLoadingDialog();
                });
            }
        });

        Oncliclicks();
    }

    private void Oncliclicks() {
        enderecoEvento.setOnClickListener(v -> {
            if (evento != null && evento.getLocalEvento() != null) {
                String endereco = evento.getLocalEvento().getLocalizacao();
                abrirMapa(endereco);
            } else {
                Toast.makeText(MoreInfosActivity.this, "Endereço não disponível.", Toast.LENGTH_SHORT).show();
            }
        });

        imageEndereco.setOnClickListener(v -> {
            if (evento != null && evento.getLocalEvento() != null) {
                String endereco = evento.getLocalEvento().getLocalizacao();
                abrirMapa(endereco);
            } else {
                Toast.makeText(MoreInfosActivity.this, "Endereço não disponível.", Toast.LENGTH_SHORT).show();
            }
        });



        button4.setOnClickListener(v -> {
            Intent intent = new Intent(MoreInfosActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnParticparEvento.setOnClickListener(v -> {
            if (evento != null) {
                Intent intent = new Intent(MoreInfosActivity.this, ParticiparDeEventos.class);
                intent.putExtra("evento", evento);
                startActivity(intent);
            } else {
                Toast.makeText(MoreInfosActivity.this, "Evento não disponível. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
            }
        });

        buttonVoluntario.setOnClickListener(v -> {
            if (evento != null) {
                Intent intent = new Intent(MoreInfosActivity.this, FormVoluntarioActivity.class);
                intent.putExtra("evento", evento);
                startActivity(intent);
            } else {
                Toast.makeText(MoreInfosActivity.this, "Evento não disponível. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
            }
        });

        btnDoar.setOnClickListener(v -> {
            if (evento != null) {
                Intent intent = new Intent(MoreInfosActivity.this, FormDoacoesActivity.class);
                intent.putExtra("evento", evento);
                startActivity(intent);
            } else {
                Toast.makeText(MoreInfosActivity.this, "Evento não disponível. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadEventDetails() {
        runOnUiThread(() -> {
            if (evento != null) {
                String StringBase64 = evento.getImagem();
                if (StringBase64 != null && !StringBase64.isEmpty()) {
                    String[] partes = StringBase64.split(",");
                    if (partes.length > 1) {
                        String dadosBase64 = partes[1];
                        byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        imageEvento.setImageBitmap(decodedByte);
                    } else {
                        Toast.makeText(MoreInfosActivity.this, "Erro ao processar imagem do evento.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MoreInfosActivity.this, "Imagem do evento não disponível.", Toast.LENGTH_SHORT).show();
                }

                tituloEvento.setText(evento.getTitulo());
                descricaoEvento.setText(evento.getDescricao());
                LocalEvento localEvento = evento.getLocalEvento();
                enderecoEvento.setText(localEvento.getLocalizacao());

                Date data = evento.getDate();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                String dataString = formato.format(data);
                dataEvento.setText(dataString);
                horarioEvento.setText(evento.getHorario());

                List<String> palestrantes = evento.getPalestrantes();
                StringBuilder palestrantesString = new StringBuilder();

                if (palestrantes != null) {
                    for (String palestrante : palestrantes) {
                        palestrantesString.append(palestrante).append(", ");
                    }

                    if (palestrantesString.length() > 0) {
                        palestrantesString.delete(palestrantesString.length() - 2, palestrantesString.length());
                    }

                    palestrantesEvento.setText(palestrantesString.toString());
                } else {
                    palestrantesEvento.setText("Nenhum palestrante disponível");
                }

                palestrantesEvento.setText(palestrantesString.toString());

            } else {
                Toast.makeText(MoreInfosActivity.this, "Evento não disponível. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void abrirMapa(String endereco) {
        try {
            // Tentar primeiro abrir com o esquema "geo:"
            Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode(endereco));
            Intent geoIntent = new Intent(Intent.ACTION_VIEW, geoUri);

            try {
                startActivity(geoIntent);
            } catch (Exception e) {
                // Caso o esquema "geo:" falhe, tentar abrir com uma URL no navegador
                Uri mapsUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(endereco));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, mapsUri);
                startActivity(webIntent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao abrir o mapa ou navegador.", Toast.LENGTH_SHORT).show();
        }
    }




    private void showLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.loading_screen, null);
        builder.setView(dialogView);
        builder.setCancelable(false);

        loadingDialog = builder.create();
        loadingDialog.show();
    }

    private void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }


}
