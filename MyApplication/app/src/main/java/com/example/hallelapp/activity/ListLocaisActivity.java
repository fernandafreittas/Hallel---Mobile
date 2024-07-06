package com.example.hallelapp.activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallelapp.R;
import com.example.hallelapp.databinding.ActivityListLocaisBinding;
import com.example.hallelapp.htpp.HttpAdm;
import com.example.hallelapp.model.LocalEvento;
import com.example.hallelapp.payload.resposta.AuthenticationResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ListLocaisActivity extends AppCompatActivity {

    AuthenticationResponse authenticationResponse;
    ActivityListLocaisBinding binding;
    private AlertDialog loadingDialog;
    HttpAdm requisicao;
    List<LocalEvento> locais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityListLocaisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        authenticationResponse = (AuthenticationResponse) getIntent().getSerializableExtra("informaçõesADM");
        requisicao = new HttpAdm();
        showLoadingDialog();

        requisicao.ListLocaisEventos(authenticationResponse, new HttpAdm.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                // Deserializa a resposta e prepara os dados
                Type listType = new TypeToken<List<LocalEvento>>() {}.getType();
                locais = new Gson().fromJson(response, listType);

                // Popula a TableLayout com os locais
                runOnUiThread(() -> populateTableLayout());
            }

            @Override
            public void onFailure(IOException e) {
                // Lida com a falha
            }
        });
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

    private void populateTableLayout() {
        TableLayout tableLayout = findViewById(R.id.tableLayoutLocais);

        for (LocalEvento local : locais) {
            TableRow tableRow = new TableRow(this);

            // Configurando o TextView
            TextView textView = new TextView(this);
            textView.setText(local.getLocalizacao());
            textView.setLayoutParams(new TableRow.LayoutParams(
                    0, // width
                    TableRow.LayoutParams.WRAP_CONTENT, // height
                    1 // weight
            ));
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setMaxLines(1);
            textView.setPadding(8, 8, 8, 8);
            textView.setTextColor(getResources().getColor(R.color.cordetextohallel));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setTypeface(ResourcesCompat.getFont(this, R.font.inter_semibold), Typeface.BOLD);
            tableRow.addView(textView);

            // Configurando o botão Editar
            Button editButton = new Button(this);
            editButton.setText("Editar");
            editButton.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    dpToPx(30)
            ));
            editButton.setTextColor(getResources().getColor(R.color.cortexto2));
            editButton.setBackground(getResources().getDrawable(R.drawable.fundo_edit_local));
            editButton.setOnClickListener(v -> editLocal(local));
            tableRow.addView(editButton);

            // Configurando o botão Deletar
            Button deleteButton = new Button(this);
            deleteButton.setText("Deletar");
            deleteButton.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    dpToPx(40)
            ));
            deleteButton.setTextColor(getResources().getColor(R.color.cortexto2));
            deleteButton.setBackground(getResources().getDrawable(R.drawable.button_crash));
            deleteButton.setOnClickListener(v -> deleteLocal(local));
            tableRow.addView(deleteButton);

            tableLayout.addView(tableRow);
        }

        hideLoadingDialog();
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void editLocal(LocalEvento local) {
        // Implementa a lógica de edição do local
    }

    private void deleteLocal(LocalEvento local) {
        // Implementa a lógica de deleção do local
    }
}
