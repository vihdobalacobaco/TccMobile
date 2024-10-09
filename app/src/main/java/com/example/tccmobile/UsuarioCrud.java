package com.example.tccmobile;

import android.content.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioCrud {

    public static int InserirUsuario (Usuario usuario, Context ctx){

        int resposta = 0;

        try {
            PreparedStatement pst = BancoDeDados.conectar((TesteConexaoBD) ctx).prepareStatement(
                    "Insert Into Usuario (nome, email, senha)" + "values (?,?,?)");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resposta;
    }
}