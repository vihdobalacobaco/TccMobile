package com.example.tccmobile;

import android.content.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioCrud {

    public static int InserirUsuario (Usuario usuario, Context ctx){

        int resposta = 0;

        try {

            PreparedStatement pst = BancoDeDados.conectar().prepareStatement(
                    "Insert Into Usuario (nome, email, senha)" + "values (?,?,?)");

            pst.setString(1, Usuario.getNome());
            pst.setString(2, Usuario.getEmail());
            pst.setString(3, Usuario.getSenha());

            resposta = pst.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        }
        return resposta;
    }

    }

