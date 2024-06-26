package com.example.hallelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hallelapp.MainActivity;
import com.example.hallelapp.R;

public class HeaderMenuActivity extends AppCompatActivity {

    private ImageButton btnSair;

    protected void onCreate (Bundle savedInstanceDate){

        super.onCreate(savedInstanceDate);
        setContentView(R.layout.header_menu);

        btnSair = findViewById(R.id.sairButton);

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
