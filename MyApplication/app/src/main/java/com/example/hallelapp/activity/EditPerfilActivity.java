package com.example.hallelapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.Manifest;

import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import com.example.hallelapp.MainActivity;
import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.htpp.HttpMembro;
import com.example.hallelapp.model.InformacoesDaSessao;
import com.example.hallelapp.payload.requerimento.EditPerfilRequest;
import com.example.hallelapp.payload.resposta.PerfilResponse;
import com.example.hallelapp.tools.ObterInformacoesDaSecao;

public class EditPerfilActivity extends AppCompatActivity {

    private EditText txtNomeUsuario, txtEmailUser, txtTelefoneUser, txtCpfUser;
    private Button btnEditNome, btnEditEmail, btnEditTelefone, btnEditCPF, btnEditFoto,btnSalvar;
    private ImageView Foto;

    InformacoesDaSessao informacoesDeLogin;
    ObterInformacoesDaSecao obterInformacoesDaSecao;

    EditPerfilRequest editPerfilRequest;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_GALLERY_PICK = 2;
    private static final int REQUEST_PERMISSIONS = 100;
    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil);
        EdgeToEdge.enable(this);

        // Inicialização das variáveis
        informacoesDeLogin = new InformacoesDaSessao();
        obterInformacoesDaSecao = new ObterInformacoesDaSecao(this);
        try {
            informacoesDeLogin = obterInformacoesDaSecao.obterDadosSalvos();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        PerfilResponse perfilResponse = (PerfilResponse) getIntent().getSerializableExtra("informacoesPerfil");
        HttpMembro httpMembro = new HttpMembro();
        editPerfilRequest = new EditPerfilRequest();

        // Inicialização dos EditTexts
        txtNomeUsuario = findViewById(R.id.nome_usuarioMain);
        txtEmailUser = findViewById(R.id.emailuser);
        txtTelefoneUser = findViewById(R.id.telefoneuser);
        txtCpfUser = findViewById(R.id.cpfUser);

        // Desativando os EditTexts inicialmente
        txtNomeUsuario.setEnabled(false);
        txtEmailUser.setEnabled(false);
        txtTelefoneUser.setEnabled(false);
        txtCpfUser.setEnabled(false);

        // Inicialização dos Buttons
        btnEditNome = findViewById(R.id.button1);
        btnEditEmail = findViewById(R.id.button12);
        btnEditTelefone = findViewById(R.id.button13);
        btnEditCPF = findViewById(R.id.button14);
        btnEditFoto = findViewById(R.id.button10);
        btnSalvar = findViewById(R.id.button11);

        // Inicializa o ImageView
        Foto = findViewById(R.id.imageView26);

        // Preenche os campos com os dados do perfil
        if (perfilResponse.getImage() != null) {
            String suaStringBase64 = perfilResponse.getImage();
            String[] partes = suaStringBase64.split(",");
            String dadosBase64 = partes[1];
            byte[] decodedString = Base64.decode(dadosBase64, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            Foto.setImageBitmap(decodedByte);
        }

        txtNomeUsuario.setText(perfilResponse.getNome());
        txtEmailUser.setText(perfilResponse.getEmail());
        txtCpfUser.setText(perfilResponse.getCpf());
        txtTelefoneUser.setText(perfilResponse.getTelefone());

        // Botão Salvar
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = Foto.getDrawable();
                if (drawable != null && drawable instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    String imageBase64 = "data:image/jpeg;base64," + bitmapToBase64(bitmap);
                    editPerfilRequest.setImage(imageBase64);
                } else {
                    // Trate o caso onde o drawable é nulo
                    Log.e("EditPerfilActivity", "Imagem não foi selecionada");
                }


                editPerfilRequest.setNome(txtNomeUsuario.getText().toString());
                editPerfilRequest.setEmail(txtEmailUser.getText().toString());
                editPerfilRequest.setCpf(txtCpfUser.getText().toString());
                editPerfilRequest.setTelefone(txtTelefoneUser.getText().toString());
                editPerfilRequest.toString();

                httpMembro.EditPerfilUsuario(editPerfilRequest, informacoesDeLogin.getId(), informacoesDeLogin, new HttpMain.HttpCallback() {
                    @Override
                    public void onSuccess(String response) {
                        System.out.println("deu super certo");
                        Intent intent = new Intent(EditPerfilActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                });




            }
        });

        // Configura os Listeners para os botões de edição
        btnEditNome.setOnClickListener(v -> txtNomeUsuario.setEnabled(true));
        btnEditEmail.setOnClickListener(v -> txtEmailUser.setEnabled(true));
        btnEditTelefone.setOnClickListener(v -> txtTelefoneUser.setEnabled(true));
        btnEditCPF.setOnClickListener(v -> txtCpfUser.setEnabled(true));

        btnEditFoto.setOnClickListener(v -> {
            if (checkPermissions()) {
                showImagePickerDialog();
            } else {
                requestPermissions();
            }
        });
    }


    // Verifica se as permissões necessárias estão concedidas
    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    // Solicita as permissões necessárias
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, REQUEST_PERMISSIONS);
    }

    // Exibe o diálogo para escolher entre Galeria ou Câmera
    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha uma opção")
                .setItems(new String[]{"Galeria", "Câmera"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            openGallery();
                        } else if (which == 1) {
                            openCamera();
                        }
                    }
                });
        builder.show();
    }

    // Abre a galeria para selecionar uma foto
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_GALLERY_PICK);
    }

    // Abre a câmera para tirar uma foto
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this, "com.seuapp.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    // Cria um arquivo para armazenar a foto tirada pela câmera
    private File createImageFile() throws IOException {
        String imageFileName = "JPEG_" + System.currentTimeMillis();
        File storageDir = getExternalFilesDir(null);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_PICK && data != null) {
                Uri selectedImageUri = data.getData();
                Foto.setImageURI(selectedImageUri);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Foto.setImageURI(photoURI);
            }
        }
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}
