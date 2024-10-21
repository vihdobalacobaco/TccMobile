package com.example.tccmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class FaleConosco extends AppCompatActivity {


    private TextInputEditText editTextNome;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextTexto;
    private Button btnEnviarMensagem;
    private Spinner spinnerTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fale_conosco);

        // Referenciando os campos da interface
        editTextNome = findViewById(R.id.editButtonNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextTexto = findViewById(R.id.editTextTexto);
        btnEnviarMensagem = findViewById(R.id.inputButtonEnviar);
        spinnerTipo = findViewById(R.id.spinnerTipo);

        // Criando um ArrayAdapter para o Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_mensagem, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapter);

        // Definindo a ação do botão de enviar mensagem
        btnEnviarMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarMensagem();
            }
        });

        ImageButton botaohome = findViewById(R.id.botaohome);
        botaohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FaleConosco.this, TelaInicial.class);
                startActivity(intent);
            }
        });
    }

    private void enviarMensagem() {
        String nome = editTextNome.getText() != null ? editTextNome.getText().toString() : "";
        String email = editTextEmail.getText() != null ? editTextEmail.getText().toString() : "";
        String texto = editTextTexto.getText() != null ? editTextTexto.getText().toString() : "";
        String tipo = spinnerTipo.getSelectedItem().toString();

        // Validação simples
        if (nome.isEmpty() || email.isEmpty() || texto.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos obrigatórios.", Toast.LENGTH_LONG).show();
            return;
        }

        // Validação do formato de e-mail
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor, insira um e-mail válido.", Toast.LENGTH_LONG).show();
            return;
        }

        // Criando um objeto Mensagem com o tipo correto
        Mensagem mensagem = new Mensagem(nome, nome, email, texto, tipo);

        // Enviando a mensagem para o banco de dados
        int resultado = MensagemCrud.InserirMensagem(mensagem, this);

        // Verificando se a mensagem foi inserida com sucesso
        if (resultado > 0) {
            Toast.makeText(this, "Mensagem enviada com sucesso!", Toast.LENGTH_LONG).show();
            limparCampos();
        } else {
            Toast.makeText(this, "Falha ao enviar mensagem.", Toast.LENGTH_LONG).show();
        }
    }

    // Método para limpar os campos após o envio
    private void limparCampos() {
        editTextNome.setText("");
        editTextEmail.setText("");
        editTextTexto.setText("");
    }
}