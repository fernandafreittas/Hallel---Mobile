package com.example.hallelapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.MainActivity;
import com.example.hallelapp.R;

public class MoreInfosActivity extends AppCompatActivity {

    private Button button4;
    private Button buttonVoluntario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_infos);

        button4 = findViewById(R.id.button4);
        buttonVoluntario = findViewById(R.id.buttonVoluntario);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoreInfosActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonVoluntario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoreInfosActivity.this,FormVoluntarioActivity.class);
                startActivity(intent);
            }
        });
    }
}
