package com.example.tccmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MensagemSucesso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagem_sucesso);

        ImageButton botaohome = findViewById(R.id.botaohome);
        botaohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MensagemSucesso.this, TelaInicialLogada.class);
                startActivity(intent);
            }
        });

    }
}