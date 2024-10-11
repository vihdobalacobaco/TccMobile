package com.example.tccmobile;

import android.content.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioCrud {

    public static int InserirUsuario(Usuario usuario, Context ctx) {
        int resposta = 0;
        try {
            Connection conn = BancoDeDados.conectar(ctx);
            if (conn != null) {
                PreparedStatement pst = conn.prepareStatement(
                        "INSERT INTO Usuario (nome, email, senha, dataCadastro, statusUsuario) VALUES (?, ?, ?, ?, ?)");

                pst.setString(1, usuario.getNome());
                pst.setString(2, usuario.getEmail());
                pst.setString(3, usuario.getSenha());
                pst.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
                pst.setString(5, "ATIVO");

                resposta = pst.executeUpdate();
            } else {
                System.out.println("Falha na conexão com o banco de dados!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resposta;
    }
}
