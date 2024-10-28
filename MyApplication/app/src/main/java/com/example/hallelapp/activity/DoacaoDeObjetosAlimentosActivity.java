// DoacaoDeObjetosAlimentosActivity.java
package com.example.hallelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hallelapp.MainActivity;
import com.example.hallelapp.R;
import com.example.hallelapp.htpp.HttpMain;
import com.example.hallelapp.payload.requerimento.DoacaoObjetosEventosReq;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.recyclers.DoacaoObjRecycle;
import com.example.hallelapp.recyclers.DoacaoObjRecycle.DoacaoItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DoacaoDeObjetosAlimentosActivity extends AppCompatActivity {

    private DoacaoObjRecycle adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao_obj_ali);

        HttpMain requisicao = new HttpMain();

        // Recuperar o objeto evento da intent
        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");
        String nome = (String) getIntent().getSerializableExtra("nome");
        String email = (String) getIntent().getSerializableExtra("email");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DoacaoObjRecycle();
        recyclerView.setAdapter(adapter);

        Button continuar = findViewById(R.id.btnContinuar);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DoacaoObjetosEventosReq> listaDoacoes = new ArrayList<>();
                List<DoacaoItem> doacaoItems = adapter.getDoacaoItems();

                for (DoacaoItem item : doacaoItems) {
                    String quantityStr = item.getQuantity().trim();
                    if (quantityStr.isEmpty()) {
                        // Tratar o caso de quantidade vazia aqui
                        Toast.makeText(DoacaoDeObjetosAlimentosActivity.this, "Por favor, insira uma quantidade para o item: " + item.getItem(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        int quantity = Integer.parseInt(quantityStr);
                        DoacaoObjetosEventosReq req = new DoacaoObjetosEventosReq();
                        req.setNomeDoObjeto(item.getItem());
                        req.setQuantidade(quantity);
                        req.setNomeDoador(nome);
                        req.setEmailDoador(email);
                        listaDoacoes.add(req);
                    } catch (NumberFormatException e) {
                        // Tratar o caso de formato inválido aqui
                        Toast.makeText(DoacaoDeObjetosAlimentosActivity.this, "Quantidade inválida para o item: " + item.getItem(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                requisicao.DoarObjetoEvento(evento.getId(), listaDoacoes, new HttpMain.HttpCallback() {
                    @Override
                    public void onSuccess(String response) {
                        System.out.println("Deu certo a doacao");
                        DoacaoSucess();

                    }

                    @Override
                    public void onFailure(IOException e) {

                       showErrorDialog();
                    }
                });
            }
        });
    }

    private void DoacaoSucess() {
        // Inflate o layout do diálogo de erro
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_doacao_sucess, null);

        // Cria o dialog a partir do layout inflado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Clique no botão de continuar para fechar o diálogo
        Button btnContinuar = dialogView.findViewById(R.id.buttonDoacaosu);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(DoacaoDeObjetosAlimentosActivity.this,MainActivity.class);

                startActivity(intent);

            }
        });

        dialog.show();
    }


    private void showErrorDialog() {
        // Inflate o layout do diálogo de erro
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_erro_doacao, null);

        // Cria o dialog a partir do layout inflado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Clique no botão de continuar para fechar o diálogo
        Button btnContinuar = dialogView.findViewById(R.id.buttonErrdoa);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



}
