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
    RecyclerView recyclerViewNoticiasMaisLidas;
    ImageAdapter imageAdapter;
    ImageAdapter maisLidasAdapter;  // Adapter para as notícias mais lidas
    List<Noticia> listaNoticias;
    List<Noticia> listaNoticiasMaisLidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        listaNoticias = new ArrayList<>();
        listaNoticiasMaisLidas = new ArrayList<>();

        imageAdapter = new ImageAdapter(this, listaNoticias, true);  // Exibe imagens para "Notícias da Semana"
        maisLidasAdapter = new ImageAdapter(this, listaNoticiasMaisLidas, false);  // Oculta imagens para "Notícias Mais Lidas"

        recyclerViewNoticiasSemana = findViewById(R.id.NoticiasSemana);
        recyclerViewNoticiasMaisLidas = findViewById(R.id.NoticiasMaisLidas);

        recyclerViewNoticiasSemana.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewNoticiasMaisLidas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyclerViewNoticiasSemana.setAdapter(imageAdapter);  // Configura o adapter para as notícias da semana
        recyclerViewNoticiasMaisLidas.setAdapter(maisLidasAdapter);  // Configura o adapter para as mais lidas

        carregarNoticiasSemana();
        carregarNoticiasMaisLidas();

        imageAdapter.setOnItemClickListener((view, position) -> {
            Noticia noticia = listaNoticias.get(position);
            Intent intent = new Intent(TelaInicial.this, NoticiaAberta.class);
            intent.putExtra("manchete", noticia.getManchete());
            intent.putExtra("conteudo", noticia.getConteudo());
            startActivity(intent);
        });

        maisLidasAdapter.setOnItemClickListener((view, position) -> {
            Noticia noticia = listaNoticiasMaisLidas.get(position);
            Intent intent = new Intent(TelaInicial.this, NoticiaAberta.class);
            intent.putExtra("manchete", noticia.getManchete());
            intent.putExtra("conteudo", noticia.getConteudo());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarNoticiasSemana();
        carregarNoticiasMaisLidas();
    }

    public void carregarNoticiasSemana() {
        new CarregarNoticiasTask().execute();
    }

    public void carregarNoticiasMaisLidas() {
        new CarregarNoticiasMaisLidasTask().execute();
    }

    private class CarregarNoticiasTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Connection conn = BancoDeDados.conectar(TelaInicial.this);
            if (conn != null) {
                try {
                    String query = "SELECT * FROM Noticia WHERE statusNoticia = 'PUBLICADA' ORDER BY dataPublicacao DESC";

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
            imageAdapter.notifyDataSetChanged();
            if (listaNoticias.isEmpty()) {
                Toast.makeText(TelaInicial.this, "Nenhuma notícia encontrada.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class CarregarNoticiasMaisLidasTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Connection conn = BancoDeDados.conectar(TelaInicial.this);
            if (conn != null) {
                try {
                    String query = "SELECT * FROM Noticia WHERE statusNoticia = 'PUBLICADA' ORDER BY NEWID()"; // Aleatoriza as notícias
                    PreparedStatement stmt = conn.prepareStatement(query);
                    ResultSet rs = stmt.executeQuery();

                    listaNoticiasMaisLidas.clear();

                    while (rs.next()) {
                        Noticia noticia = new Noticia();
                        noticia.setId(rs.getInt("id"));
                        noticia.setManchete(rs.getString("manchete"));
                        noticia.setConteudo(rs.getString("conteudo"));
                        noticia.setFoto(rs.getBytes("foto"));
                        noticia.setStatusNoticia(rs.getString("statusNoticia"));
                        listaNoticiasMaisLidas.add(noticia);
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
            maisLidasAdapter.notifyDataSetChanged();
            if (listaNoticiasMaisLidas.isEmpty()) {
                Toast.makeText(TelaInicial.this, "Nenhuma notícia encontrada.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}