package com.example.hallelapp.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hallelapp.R;
import com.example.hallelapp.payload.resposta.AllEventosListResponse;
import com.example.hallelapp.recyclers.DoacaoObjRecycle;

public class DoacaoDeObjetosAlimentosActivity extends AppCompatActivity {


    private DoacaoObjRecycle adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao_obj_ali);

        // Recuperar o objeto evento da intent
        AllEventosListResponse evento = (AllEventosListResponse) getIntent().getSerializableExtra("evento");


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DoacaoObjRecycle();
        recyclerView.setAdapter(adapter);



    }

    }
