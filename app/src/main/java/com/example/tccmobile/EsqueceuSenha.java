package com.example.tccmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EsqueceuSenha extends AppCompatActivity {

    private TextInputEditText editTextEmail, editTextNovaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_senha);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextNovaSenha = findViewById(R.id.ediTxtNovaSenha);

        ImageButton backtologin = findViewById(R.id.VoltarTela);
        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EsqueceuSenha.this, FormLogin.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.ButtonRedefinir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String novaSenha = editTextNovaSenha.getText().toString().trim();

                if (email.isEmpty() || novaSenha.isEmpty()) {
                    Toast.makeText(EsqueceuSenha.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Iniciar a redefinição de senha
                    new RedefinirSenhaTask(email, novaSenha).execute();
                }
            }
        });
    }

    private class RedefinirSenhaTask extends AsyncTask<Void, Void, Boolean> {

        private String email;
        private String novaSenha;

        RedefinirSenhaTask(String email, String novaSenha) {
            this.email = email;
            this.novaSenha = novaSenha;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Connection conn = BancoDeDados.conectar(EsqueceuSenha.this);
            if (conn != null) {
                try {
                    // Verifica se o e-mail está cadastrado
                    String queryCheckEmail = "SELECT * FROM Usuario WHERE email = ?";
                    PreparedStatement stmtCheckEmail = conn.prepareStatement(queryCheckEmail);
                    stmtCheckEmail.setString(1, email);
                    ResultSet rs = stmtCheckEmail.executeQuery();

                    if (rs.next()) {
                        // Codificar a nova senha em Base64
                        String senhaCodificada = codificarBase64(novaSenha);

                        // Se o e-mail existir, atualiza a senha
                        String queryUpdateSenha = "UPDATE Usuario SET senha = ? WHERE email = ?";
                        PreparedStatement stmtUpdateSenha = conn.prepareStatement(queryUpdateSenha);
                        stmtUpdateSenha.setString(1, senhaCodificada); // Senha codificada
                        stmtUpdateSenha.setString(2, email);
                        int rowsUpdated = stmtUpdateSenha.executeUpdate();

                        return rowsUpdated > 0;  // Retorna true se a senha foi atualizada
                    } else {
                        return false;  // O e-mail não foi encontrado
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(EsqueceuSenha.this, "Senha redefinida com sucesso!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EsqueceuSenha.this, FormLogin.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(EsqueceuSenha.this, "Erro ao redefinir a senha. Verifique o e-mail.", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Método para codificar a senha em Base64
    private String codificarBase64(String senha) {
        // Converter a senha em bytes
        byte[] senhaBytes = senha.getBytes();
        // Codificar os bytes em Base64
        return Base64.encodeToString(senhaBytes, Base64.DEFAULT).trim();
    }
}