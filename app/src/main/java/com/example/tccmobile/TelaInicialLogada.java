 package com.example.tccmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.ArrayList;
import java.util.List;

 public class TelaInicialLogada extends AppCompatActivity {

     private static final String TAG = "TelaIniciaLogada";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial_logada);

        // Configurando o ImageButton para navegação
        ImageButton buttonsair = findViewById(R.id.buttonsair);
        buttonsair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaInicialLogada.this, TelaInicial.class);
                startActivity(intent);
            }
        });

    }
}
