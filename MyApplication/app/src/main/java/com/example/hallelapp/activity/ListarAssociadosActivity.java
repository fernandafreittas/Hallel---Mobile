package com.example.hallelapp.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpAssociado;
import com.example.hallelapp.model.InformacoesDaSessao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class ListarAssociadosActivity extends AppCompatActivity {

    private AlertDialog loadingDialog;
    private TableLayout tableLayoutAssociados;
    private InformacoesDaSessao informacoesDaSessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associado_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainListAssociados), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tableLayoutAssociados = findViewById(R.id.tableLayoutAssociados);
        informacoesDaSessao = (InformacoesDaSessao) getIntent().getSerializableExtra("informacoesSessao");

        listarTodosAssociados();
    }

    private void listarTodosAssociados() {
        showLoadingDialog();

        HttpAssociado httpAssociado = new HttpAssociado();
        httpAssociado.listarTodosAssociados(informacoesDaSessao, new HttpAssociado.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    hideLoadingDialog();
                    try {
                        JSONArray associadosArray = new JSONArray(response);
                        for (int i = 0; i < associadosArray.length(); i++) {
                            JSONObject associado = associadosArray.getJSONObject(i);
                            String nome = associado.getString("nome");

                            TableRow row = new TableRow(ListarAssociadosActivity.this);
                            TextView textView = new TextView(ListarAssociadosActivity.this);
                            textView.setText(nome);
                            textView.setPadding(8, 8, 8, 8);
                            row.addView(textView);

                            tableLayoutAssociados.addView(row);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onFailure(IOException e) {
                runOnUiThread(() -> {
                    hideLoadingDialog();
                    e.printStackTrace();
                    // Tratar o erro adequadamente
                });
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
}
