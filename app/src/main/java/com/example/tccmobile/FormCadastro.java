package com.example.tccmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

public class FormCadastro extends AppCompatActivity {

    EditText editNome, editEmail, editSenha;
    Button btn_cadastrar;

    private TextView text_tenho_cad;

    // Expressão regular para validar a senha
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         // Pelo menos um número
                    "(?=\\S+$)" +           // Sem espaços em branco
                    ".{8,}" +               // Pelo menos 8 caracteres
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btn_cadastrar = findViewById(R.id.btn_cadastrar);

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validar os campos antes de prosseguir
                if (!validarEmail() || !validarSenha()) {
                    return;  // Não prosseguir se os dados estiverem inválidos
                }

                // Criando objeto Usuario
                Usuario user = new Usuario(
                        editNome.getText().toString(),
                        editEmail.getText().toString(),
                        editSenha.getText().toString()
                );

                // Inserindo usuário no banco de dados
                int res = UsuarioCrud.InserirUsuario(user, getBaseContext());
                if (res <= 0) {
                    Snackbar.make(btn_cadastrar, "Dados não inseridos", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(btn_cadastrar, "Dados cadastrados com sucesso", Snackbar.LENGTH_LONG).show();
                    startActivity(new Intent(FormCadastro.this, FormLogin.class));
                }
            }
        });

        ImageButton botaohome = findViewById(R.id.botaohome);
        botaohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormCadastro.this, TelaInicial.class);
                startActivity(intent);
            }
        });

        TextView text_tenho_cad = findViewById(R.id.text_tenho_cad);
        text_tenho_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormCadastro.this, FormLogin.class);
                startActivity(intent);
            }
        });
    }

    // Método para validar o formato do email
    private boolean validarEmail() {
        String emailInput = editEmail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            editEmail.setError("Campo obrigatório");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            editEmail.setError("Formato de email inválido");
            return false;
        } else {
            editEmail.setError(null);
            return true;
        }
    }

    // Método para validar o formato da senha
    private boolean validarSenha() {
        String senhaInput = editSenha.getText().toString().trim();
        if (senhaInput.isEmpty()) {
            editSenha.setError("Campo obrigatório");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(senhaInput).matches()) {
            editSenha.setError("A senha deve ter pelo menos 8 caracteres, incluindo números!");
            return false;
        } else {
            editSenha.setError(null);
            return true;
        }
    }
}