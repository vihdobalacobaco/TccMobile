package com.example.tccmobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NoticiaAberta extends AppCompatActivity {

    ImageView fotoNoticia;
    TextView mancheteNoticia, conteudoNoticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_aberta);

        mancheteNoticia = findViewById(R.id.mancheteNoticia);
        fotoNoticia = findViewById(R.id.fotoNoticia);
        conteudoNoticia = findViewById(R.id.conteudoNoticia);

        Intent intent = getIntent();
        if (intent != null) {
            byte[] imageBytes = intent.getByteArrayExtra("foto");
            String manchete = intent.getStringExtra("manchete");
            CharSequence conteudo = intent.getCharSequenceExtra("conteudo");

            if (imageBytes != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                fotoNoticia.setImageBitmap(bitmap);
            } else {
                fotoNoticia.setImageResource(R.drawable.placeholder);
            }

            mancheteNoticia.setText(manchete);
            conteudoNoticia.setText(conteudo);
        }


        ImageButton butComentar = findViewById(R.id.butcomentar);
        butComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NoticiaAberta.this, NoticiaComentario.class);
                startActivity(intent);
            }
        });

    }
}