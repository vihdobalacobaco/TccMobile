package com.example.tccmobile;

import android.content.Context;

import java.sql.PreparedStatement;

public class UsuarioCrud {

    public static int InserirUsuario (Usuario usuario, Context ctx){

        int resposta = 0;

        try {
            PreparedStatement pst = BancoDeDados.conectar(ctx).prepareStatement(
                    "Insert Into Usuario (nome, email, senha)" + "values (?,?,?)");

        }
    }
}
