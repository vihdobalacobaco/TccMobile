package com.example.tccmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelaInicial extends AppCompatActivity {

    RecyclerView recyclerViewNoticiasSemana, recyclerViewNoticiasMaisLidas;

    ImageAdapter image;

    List<com.example.tccmobile.Noticia> listaImage;

    @Override
    protected void onResume() {
        super.onResume();
        carregarNoticiasSemana();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        listaImage = new ArrayList<>();
        image = new ImageAdapter(listaImage);

        recyclerViewNoticiasSemana = findViewById(R.id.NoticiasSemana);
        recyclerViewNoticiasSemana.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewNoticiasMaisLidas = findViewById(R.id.NoticiasMaisLidas);
        recyclerViewNoticiasMaisLidas.setLayoutManager(new LinearLayoutManager(this));

        carregarNoticiasSemana();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void carregarNoticiasSemana() {
        Connection conn = BancoDeDados.conectar(this);
        if (conn != null) {
            try {
                String query = "SELECT * FROM Noticia WHERE statusNoticia = 'ATIVO' ORDER BY id DESC";
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    com.example.tccmobile.Noticia produto = new com.example.tccmobile.Noticia();
                    produto.setFoto(rs.getBytes("foto"));

                    listaImage.add(image);
                }

                image.notifyDataSetChanged();

            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(this, "Erro ao carregar noticias", Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}