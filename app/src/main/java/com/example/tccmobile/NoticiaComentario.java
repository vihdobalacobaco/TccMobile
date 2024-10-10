package com.example.tccmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoticiaComentario extends AppCompatActivity {

    private TextInputEditText inputDescricao;
    private RecyclerView recyclerView;
    private ComentarioAdapter comentarioAdapter;
    private List<Comentario> comentarios; // Lista para armazenar os comentários

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_comentario);

        // Acessando o TextInputEditText corretamente
        inputDescricao = findViewById(R.id.textInputEditText);

        // Acessando o RecyclerView
        recyclerView = findViewById(R.id.recyclerViewComentarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializando a lista e o adapter
        comentarios = new ArrayList<>();
        comentarioAdapter = new ComentarioAdapter(comentarios);
        recyclerView.setAdapter(comentarioAdapter);

        // Botão de enviar comentário
        findViewById(R.id.inputButtonEnviar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comentario = inputDescricao.getText().toString();
                if (!comentario.isEmpty()) {
                    enviarComentario(comentario);
                } else {
                    Toast.makeText(NoticiaComentario.this, "Por favor, insira um comentário.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        carregarComentarios(); // Carregar comentários ao iniciar a atividade
    }

    private void enviarComentario(String comentario) {
        new EnviarComentarioTask(comentario).execute();
    }

    private class EnviarComentarioTask extends AsyncTask<Void, Void, Boolean> {
        private final String comentario;

        EnviarComentarioTask(String comentario) {
            this.comentario = comentario;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Connection conn = BancoDeDados.conectar(NoticiaComentario.this); // Passando o contexto corretamente
            if (conn != null) {
                try {
                    String query = "INSERT INTO ComentarioNoticia (texto, usuario_id, statusComentario) VALUES (?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, comentario);
                    stmt.setInt(2, 1); // Substitua pelo ID do usuário real
                    stmt.setString(3, "ATIVO");
                    int rowsAffected = stmt.executeUpdate();
                    Log.d("Database", "Rows affected: " + rowsAffected); // Log para verificação
                    return rowsAffected > 0; // Comentário enviado com sucesso

                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                    return false; // Conexão falhou
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (java.sql.SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(NoticiaComentario.this, "Comentário enviado com sucesso!", Toast.LENGTH_SHORT).show();
                inputDescricao.setText(""); // Limpa o campo de texto
                carregarComentarios(); // Atualiza a lista de comentários
            } else {
                Toast.makeText(NoticiaComentario.this, "Erro ao enviar comentário. Tente novamente.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void carregarComentarios() {
        new AsyncTask<Void, Void, List<Comentario>>() {
            @Override
            protected List<Comentario> doInBackground(Void... voids) {
                List<Comentario> comentarios = new ArrayList<>();
                Connection conn = BancoDeDados.conectar(NoticiaComentario.this);
                if (conn != null) {
                    try {
                        String query = "SELECT * FROM ComentarioNoticia WHERE statusComentario = 'ATIVO'";
                        PreparedStatement stmt = conn.prepareStatement(query);
                        ResultSet rs = stmt.executeQuery();
                        while (rs.next()) {
                            String texto = rs.getString("texto");
                            int usuarioId = rs.getInt("usuario_id");
                            // Adicione o comentário à lista
                            comentarios.add(new Comentario(texto, usuarioId));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (java.sql.SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return comentarios;
            }

            @Override
            protected void onPostExecute(List<Comentario> comentarios) {
                NoticiaComentario.this.comentarios.clear(); // Limpa a lista existente
                NoticiaComentario.this.comentarios.addAll(comentarios); // Adiciona os novos comentários
                comentarioAdapter.notifyDataSetChanged(); // Atualiza o adapter
            }
        }.execute();
    }
}