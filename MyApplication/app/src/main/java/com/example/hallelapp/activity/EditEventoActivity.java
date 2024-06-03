package com.example.hallelapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpAdm;
import com.example.hallelapp.model.LocalEvento;
import com.example.hallelapp.model.LocalEventoLocalizacaoRequest;
import com.example.hallelapp.payload.requerimento.EventosRequest;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.example.hallelapp.recyclers.ColaboradorAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;




public class EditEventoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ColaboradorAdapter colaboradorAdapter;
    List<LocalEvento> locais;
    String[] localizacoesArray;
    int indice;

    Context context = this;

    HttpAdm requisicao = new HttpAdm();
    EventosRequest eventosRequest = new EventosRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_evento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Recuperar o objeto evento e authetication da intent
        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");
        AuthenticationResponse authenticationResponse = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");

        recyclerView = findViewById(R.id.recyclerViewColoborador);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        colaboradorAdapter = new ColaboradorAdapter(this);
        recyclerView.setAdapter(colaboradorAdapter);

        Button adicionaFoto = findViewById(R.id.button2);
        EditText txtNomeEvento = findViewById(R.id.inputNome);
        EditText txtDescricao = findViewById(R.id.inputDescricao);
        Switch destacarEvento = findViewById(R.id.destaqueEvento);
        EditText txtData = findViewById(R.id.inputDate);
        EditText txtHorario = findViewById(R.id.inputTime);
        AutoCompleteTextView txtEndereco = findViewById(R.id.txtEndereco);
        Button editNome = findViewById(R.id.editNome);
        Button editDesc = findViewById(R.id.editDesc);
        Button editData = findViewById(R.id.editDate);
        Button editHora = findViewById(R.id.editHour);
        Button editEnder = findViewById(R.id.editAddress);
        Button salvarButton = findViewById(R.id.button9);

        txtNomeEvento.setEnabled(false);
        txtDescricao.setEnabled(false);
        txtData.setEnabled(false);
        txtHorario.setEnabled(false);
        txtEndereco.setEnabled(false);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            adicionaFoto.setBackground(new BitmapDrawable(getResources(), bitmap));
                            adicionaFoto.setText("");

                            // Convertendo a imagem para base64
                            String imagemBase64 = bitmapToBase64(bitmap);
                            eventosRequest.setImagem(imagemBase64);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        adicionaFoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intent);
        });

        requisicao.ListLocaisEventos(authenticationResponse, new HttpAdm.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                // Deserializa a resposta e prepara os dados
                Type listType = new TypeToken<List<LocalEvento>>() {}.getType();
                List<LocalEvento> responseEventos2 = new Gson().fromJson(response, listType);
                locais = responseEventos2;
                localizacoesArray = new String[locais.size()];

                // Popula o array de strings com as localizações
                for (int i = 0; i < locais.size(); i++) {
                    localizacoesArray[i] = locais.get(i).getLocalizacao();
                    System.out.println(localizacoesArray[i]);
                }

                // Atualiza a interface do usuário na thread principal
                runOnUiThread(() -> {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(EditEventoActivity.this, android.R.layout.simple_dropdown_item_1line, localizacoesArray);
                    txtEndereco.setAdapter(adapter);

                    // Configura o OnItemClickListener
                    txtEndereco.setOnItemClickListener((parent, view, position, id) -> {
                        // Obtém o item selecionado e seu índice
                        String selectedItem = (String) parent.getItemAtPosition(position);
                        int selectedIndex = -1;
                        for (int i = 0; i < localizacoesArray.length; i++) {
                            if (localizacoesArray[i].equals(selectedItem)) {
                                selectedIndex = i;
                                indice = selectedIndex;
                                break;
                            }
                        }
                        // Exibe o índice selecionado
                        Log.d("SelectedIndex", "Index: " + selectedIndex);
                        // Ou você pode fazer outras ações com o índice aqui
                    });
                });
            }

            @Override
            public void onFailure(IOException e) {
                // Lida com a falha
            }
        });

        requisicao.ListInformacoesEventoById(evento.getId(), authenticationResponse, new HttpAdm.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                eventosRequest = gson.fromJson(response, EventosRequest.class);
                System.out.println(eventosRequest.toString());

                runOnUiThread(() -> {

                    if (eventosRequest.getImagem() != null) {
                        String StringBase64 = eventosRequest.getImagem();
                        String[] partes = StringBase64.split(",");
                        String dadosBase64 = partes[1];

                        byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        adicionaFoto.setText("");
                        adicionaFoto.setBackground(new BitmapDrawable(getResources(), decodedByte));
                    }

                    txtNomeEvento.setText(eventosRequest.getTitulo());
                    txtDescricao.setText(eventosRequest.getDescricao());

                    if (eventosRequest.getDestaque() != null) {
                        destacarEvento.setChecked(eventosRequest.getDestaque());
                    }

                    Date data = eventosRequest.getDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String dataFormatada = sdf.format(data);
                    txtData.setText(dataFormatada);
                    txtHorario.setText(eventosRequest.getHorario());
                    txtEndereco.setText(eventosRequest.getLocalEvento().getLocalizacao());

                    System.out.println("palestrantes " + eventosRequest.getPalestrantes());

                    if (eventosRequest.getPalestrantes() != null) {
                        colaboradorAdapter.adicionaColaboradores(eventosRequest.getPalestrantes());
                    }
                });

            }

            @Override
            public void onFailure(IOException e) {

            }
        });

        editNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNomeEvento.setEnabled(true);
            }
        });

        editDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDescricao.setEnabled(true);
            }
        });

        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtData.setEnabled(true);
            }
        });

        editHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtHorario.setEnabled(true);
            }
        });

        editEnder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEndereco.setEnabled(true);
            }
        });


        salvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para salvar os detalhes do evento
                eventosRequest.setTitulo(txtNomeEvento.getText().toString());
                eventosRequest.setDescricao(txtDescricao.getText().toString());
                eventosRequest.setDestaque(destacarEvento.isChecked());

                String dataString = txtData.getText().toString();
                dataString = dataString.replace("/","-");
                System.out.println("dataString: " + dataString);



                Date data = null;

                eventosRequest.setDate(data);








                eventosRequest.setHorario(txtHorario.getText().toString());

                LocalEvento local = locais.get(indice);
                eventosRequest.setLocalEvento(local);

                // Lógica para salvar colaboradores/palestrantes
                List<String> colaboradores = colaboradorAdapter.getColaboradores();
                eventosRequest.setPalestrantes(colaboradores);

                LocalEventoLocalizacaoRequest localEventoLocalizacaoRequest = new LocalEventoLocalizacaoRequest();
                localEventoLocalizacaoRequest.setLocalizacao(local.getLocalizacao());
                localEventoLocalizacaoRequest.setId(local.getId());

                eventosRequest.setLocalEventoRequest(localEventoLocalizacaoRequest);

                // Enviar a solicitação de edição do evento
                requisicao.EditarEvento(evento.getId(), dataString, eventosRequest, authenticationResponse, new HttpAdm.HttpCallback() {
                    @Override
                    public void onSuccess(String response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Evento criado com sucesso", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    }

                    @Override
                    public void onFailure(IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Erro ao criar evento", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });



    }

    public String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
