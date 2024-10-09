/* package com.example.tccmobile;

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

public class TelaInicialLogada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial_logada);

        // Configurando o RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("https://images.unsplash.com/photo-1720048171180-a8178a198fa8?w=700&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxmZWF0dXJlZC1waG90b3MtZmVlZHwxfHx8ZW58MHx8fHx8");
        arrayList.add("https://images.unsplash.com/photo-1719937206491-ed673f64be1f?w=700&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxmZWF0dXJlZC1waG90b3MtZmVlZHw2fHx8ZW58MHx8fHx8");
        arrayList.add("https://images.unsplash.com/photo-1719937051230-8798ae2ebe86?w=700&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxmZWF0dXJlZC1waG90b3MtZmVlZHwxMXx8fGVufDB8fHx8fA%3D%3D");

        ImageAdapter adapter = new ImageAdapter(TelaInicialLogada.this, arrayList);
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String url) {
                startActivity(new Intent(TelaInicialLogada.this, ImageViewActivity.class)
                                .putExtra("image", url),
                        ActivityOptions.makeSceneTransitionAnimation(TelaInicialLogada.this, imageView, "image").toBundle());
            }
        });
        recyclerView.setAdapter(adapter);

        TextView textViewLink1 = findViewById(R.id.textViewLink1);
        textViewLink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaInicialLogada.this, NoticiaAberta.class);
                startActivity(intent);
            }
        });

        // Configurando o ImageButton para navegação
        ImageButton butsair = findViewById(R.id.butsair);
        butsair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaInicialLogada.this, TelaInicial.class);
                startActivity(intent);
            }
        });

    }
}
*/