package com.example.hallelapp.activity;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpAdm;
import com.example.hallelapp.model.LocalEvento;
import com.example.hallelapp.payload.requerimento.EventosRequest;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CreateEventosActivity extends AppCompatActivity {


    List<LocalEvento> locais;
    String[] localizacoesArray;

    int indice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_eventos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        HttpAdm requisicao = new HttpAdm();
        AuthenticationResponse authenticationResponse = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");
        EventosRequest eventosRequest = new EventosRequest();


        Button adicionaFoto = findViewById(R.id.button2);
        EditText txtNomeEvento = findViewById(R.id.inputNome);
        EditText txtDescricao = findViewById(R.id.inputDescricao);
        Switch destacarEvento = findViewById(R.id.destaqueEvento);
        EditText txtData = findViewById(R.id.inputDate);
        EditText txtHorario = findViewById(R.id.inputTime);
        AutoCompleteTextView txtEndereco = findViewById(R.id.txtEndereco);
        EditText colaboradores = findViewById(R.id.editText4);
        Button adicionarColaborador = findViewById(R.id.button7);
        Button excluirColaborador = findViewById(R.id.button9);



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
                Type listType = new TypeToken<List<LocalEvento>>() {
                }.getType();
                List<LocalEvento> responseEventos2 = new Gson().fromJson(response, listType);
                locais = responseEventos2;

                localizacoesArray = new String[locais.size()];

                // Popula o array de strings com as localizações
                for (int i = 0; i < locais.size(); i++) {
                    localizacoesArray[i] = locais.get(i).getLocalizacao();

                    System.out.println(localizacoesArray[i]);
                }

                // Atualiza a interface do usuário na thread principal
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateEventosActivity.this, android.R.layout.simple_dropdown_item_1line, localizacoesArray);
                        txtEndereco.setAdapter(adapter);


                        // Configura o OnItemClickListener
                        txtEndereco.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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


                            }
                        });
                    }


                });
            }

            @Override
                    public void onFailure(IOException e) {
                        // Lida com a falha
                    }
                });


                eventosRequest.setTitulo(txtNomeEvento.getText().toString());
                eventosRequest.setDescricao(txtDescricao.getText().toString());
                eventosRequest.setDestaque(destacarEvento.isChecked());

                String dataString = txtData.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date data = null;
                try {
                    data = sdf.parse(dataString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Agora, você pode definir a data no objeto eventosRequest
                eventosRequest.setDate(data);
                eventosRequest.setHorario(txtHorario.getText().toString());
                eventosRequest.setLocalEvento(locais.get(indice));






    }





    public String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


}




