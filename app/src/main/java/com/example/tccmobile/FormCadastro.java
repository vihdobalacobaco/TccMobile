package com.example.tccmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class FormCadastro extends AppCompatActivity {

    EditText editNome, editEmail, editSenha;
    Button btn_cadastrar;

    private TextView text_tenho_cad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        editNome = (EditText) findViewById(R.id.editNome);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editSenha = (EditText) findViewById(R.id.editSenha);
        btn_cadastrar = (Button) findViewById(R.id.btn_cadastrar);

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v){
                Usuario user = new Usuario(
                        editNome.getText().toString(),
                        editEmail.getText().toString(),
                        editSenha.getText().toString()
                );

                int res = UsuarioCrud.inserirUsuario(user, getBaseContext());
                if (res <=0){
                    Snackbar.make(btn_cadastrar, "Dados não inseridos!", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(btn_cadastrar, "Dados Cadastrados com sucesso", Snackbar.LENGTH_LONG).show();
                }
            }
        });


        IniciarComponentes();
        text_tenho_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FormCadastro.this, FormLogin.class);
                startActivity(intent);
            }
        });


    }
    private void IniciarComponentes(){
        text_tenho_cad = findViewById(R.id.text_tenho_cad);
    }
    }
