package com.example.tccmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelaInicial extends AppCompatActivity {

    private static final String TAG = "TelaInicial";
    RecyclerView recyclerViewNoticiasSemana;
    ImageAdapter imageAdapter;
    List<Noticia> listaNoticias;

    @Override
    protected void onResume() {
        super.onResume();
        carregarNoticiasSemana();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);


        listaNoticias = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, listaNoticias);

        recyclerViewNoticiasSemana = findViewById(R.id.NoticiasSemana);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNoticiasSemana.setLayoutManager(layoutManager);

        carregarNoticiasSemana();

        imageAdapter.setOnItemClickListener((view, position) -> {
            Noticia noticia = listaNoticias.get(position);
            Intent intent = new Intent(TelaInicial.this, NoticiaAberta.class);
            intent.putExtra("foto", noticia.getFoto());
            intent.putExtra("manchete", noticia.getManchete());
            intent.putExtra("fonte", noticia.getFonte());
            intent.putExtra("dataPublicacao", noticia.getDataPublicacao());
            intent.putExtra("palavrasChave", noticia.getPalavrasChave());
            intent.putExtra("conteudo", noticia.getConteudo());
            startActivity(intent);
        });
    }

    private void carregarNoticiasSemana() {
        new CarregarNoticiasTask().execute();
    }

    private class CarregarNoticiasTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Connection conn = BancoDeDados.conectar(TelaInicial.this);
            if (conn != null) {
                try {
                    String query = "SELECT * FROM Noticia WHERE statusNoticia = 'PUBLICADA' ORDER BY id DESC";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    ResultSet rs = stmt.executeQuery();

                    listaNoticias.clear();

                    while (rs.next()) {
                        Noticia noticia = new Noticia();
                        noticia.setId(rs.getInt("id"));
                        noticia.setManchete(rs.getString("manchete"));
                        noticia.setConteudo(rs.getString("conteudo"));
                        noticia.setFoto(rs.getBytes("foto"));
                        noticia.setStatusNoticia(rs.getString("statusNoticia"));
                        listaNoticias.add(noticia);
                    }

                } catch (SQLException e) {
                    Log.e(TAG, "Erro ao carregar noticias: " + e.getMessage());
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        Log.e(TAG, "Erro ao fechar a conexão: " + e.getMessage());
                    }
                }
            } else {
                Log.e(TAG, "Conexão com o banco falhou");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            recyclerViewNoticiasSemana.setAdapter(imageAdapter);
            imageAdapter.notifyDataSetChanged();
            if (listaNoticias.isEmpty()) {
                Toast.makeText(TelaInicial.this, "Nenhuma notícia encontrada.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}