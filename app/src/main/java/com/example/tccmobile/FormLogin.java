package com.example.tccmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class FormLogin extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button butEntrar;
    private TextView text_tela_cadastro, text_esqsenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        // Vinculando os campos
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        butEntrar = findViewById(R.id.butEntrar);

        // Ação do botão "Entrar"
        butEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();

                // Verifica se os campos estão vazios
                if (email.isEmpty() || senha.isEmpty()) {
                    Snackbar.make(v, "Preencha todos os campos!", Snackbar.LENGTH_LONG).show();
                } else {
                    // Chama o método de verificação no banco de dados
                    boolean isValid = UsuarioCrud.verificarLogin(email, senha, getBaseContext());

                    if (isValid) {
                        // Redireciona para a tela inicial ou outra tela
                        Intent intent = new Intent(FormLogin.this, TelaInicialLogada.class);
                        startActivity(intent);
                    } else {
                        Snackbar.make(v, "Email ou senha incorretos!", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        // Ação para abrir a tela de cadastro
        text_tela_cadastro = findViewById(R.id.text_tela_cadastro);
        text_tela_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormLogin.this, FormCadastro.class);
                startActivity(intent);
            }
        });

        // Ação para abrir a tela de "Esqueceu Senha"
        text_esqsenha = findViewById(R.id.text_esqsenha);
        text_esqsenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormLogin.this, EsqueceuSenha.class);
                startActivity(intent);
            }
        });

        // Ação do botão "Home"
        ImageButton buthome = findViewById(R.id.botaohome);
        buthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormLogin.this, TelaInicial.class);
                startActivity(intent);
            }
        });
    }
}