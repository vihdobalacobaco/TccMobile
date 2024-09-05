package com.example.tccmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TokenVerificacao extends AppCompatActivity {

    private TextView text_tenho_cad2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_verificacao);


        IniciarComponentes();
        text_tenho_cad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TokenVerificacao.this, FormLogin.class);
                startActivity(intent);
            }
        });
    }

    private void IniciarComponentes() {
    }
}