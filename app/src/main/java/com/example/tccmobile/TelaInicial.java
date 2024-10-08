package com.example.tccmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TelaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        ImageButton butfaleconosco = findViewById(R.id.butfaleconosco);
        butfaleconosco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaInicial.this,  FaleConosco.class);
                startActivity(intent);
            }
        });

        ImageButton buttonperfil = findViewById(R.id.buttonperfil);
        buttonperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaInicial.this, FormLogin.class);
                startActivity(intent);
            }
        });
    }
}