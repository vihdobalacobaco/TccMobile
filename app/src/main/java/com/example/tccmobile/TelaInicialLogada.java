 package com.example.tccmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

 public class TelaInicialLogada extends AppCompatActivity {

     private static final String TAG = "TelaInicialLogada";
     RecyclerView recyclerViewNoticiasSemana;
     RecyclerView recyclerViewNoticiasMaisLidas;
     ImageAdapter imageAdapter;
     ImageAdapter maisLidasAdapter;  // Adapter para as notícias mais lidas
     List<Noticia> listaNoticias;
     List<Noticia> listaNoticiasMaisLidas;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_tela_inicial_logada);



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

         // Chama os métodos para carregar as notícias
         carregarNoticiasSemana();
         carregarNoticiasMaisLidas();

         imageAdapter.setOnItemClickListener((view, position) -> {
             Noticia noticia = listaNoticias.get(position);
             Intent intent = new Intent(TelaInicialLogada.this, NoticiaAberta.class);
             intent.putExtra("manchete", noticia.getManchete());
             intent.putExtra("conteudo", noticia.getConteudo());
             startActivity(intent);
         });

         maisLidasAdapter.setOnItemClickListener((view, position) -> {
             Noticia noticia = listaNoticiasMaisLidas.get(position);
             Intent intent = new Intent(TelaInicialLogada.this, NoticiaAberta.class);
             intent.putExtra("manchete", noticia.getManchete());
             intent.putExtra("conteudo", noticia.getConteudo());
             startActivity(intent);
         });

         ImageButton buttonsair = findViewById(R.id.buttonsair);
         buttonsair.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(TelaInicialLogada.this, TelaInicial.class);
                 startActivity(intent);
             }
         });

         ImageButton butperfil = findViewById(R.id.buttonperfil);
         butperfil.setOnClickListener(v -> {
             Intent intent = new Intent(TelaInicialLogada.this, FormLogin.class);
             startActivity(intent);
         });

     }

     @Override
     protected void onResume() {
         super.onResume();
         carregarNoticiasSemana();
         carregarNoticiasMaisLidas();
     }

     private void carregarNoticiasSemana() {
         carregarNoticiasSemana(this, listaNoticias, imageAdapter);
     }

     private void carregarNoticiasMaisLidas() {
         carregarNoticiasMaisLidas(this, listaNoticiasMaisLidas, maisLidasAdapter);
     }

     public static void carregarNoticiasSemana(Context context, List<Noticia> listaNoticias, ImageAdapter imageAdapter) {
         new CarregarNoticiasTask(context, listaNoticias, imageAdapter).execute();
     }

     public static void carregarNoticiasMaisLidas(Context context, List<Noticia> listaNoticiasMaisLidas, ImageAdapter maisLidasAdapter) {
         new CarregarNoticiasMaisLidasTask(context, listaNoticiasMaisLidas, maisLidasAdapter).execute();
     }

     private static class CarregarNoticiasTask extends AsyncTask<Void, Void, Void> {
         private final Context context;
         private final List<Noticia> listaNoticias;
         private final ImageAdapter imageAdapter;

         CarregarNoticiasTask(Context context, List<Noticia> listaNoticias, ImageAdapter imageAdapter) {
             this.context = context;
             this.listaNoticias = listaNoticias;
             this.imageAdapter = imageAdapter;
         }

         @Override
         protected Void doInBackground(Void... voids) {
             Connection conn = BancoDeDados.conectar(context);
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
             imageAdapter.notifyDataSetChanged();
         }
     }

     private static class CarregarNoticiasMaisLidasTask extends AsyncTask<Void, Void, Void> {
         private final Context context;
         private final List<Noticia> listaNoticiasMaisLidas;
         private final ImageAdapter maisLidasAdapter;

         CarregarNoticiasMaisLidasTask(Context context, List<Noticia> listaNoticiasMaisLidas, ImageAdapter maisLidasAdapter) {
             this.context = context;
             this.listaNoticiasMaisLidas = listaNoticiasMaisLidas;
             this.maisLidasAdapter = maisLidasAdapter;
         }

         @Override
         protected Void doInBackground(Void... voids) {
             Connection conn = BancoDeDados.conectar(context);
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
         }
     }


 }