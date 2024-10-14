package com.example.tccmobile;

import android.content.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.transform.Result;

public class UsuarioCrud {

    public static int InserirUsuario(Usuario usuario, Context ctx) {
        int resposta = 0;
        try {
            // Conectando ao banco de dados
            Connection conn = BancoDeDados.conectar(ctx);
            if (conn != null) {
                // Preparando a instrução SQL para inserção
                PreparedStatement pst = conn.prepareStatement(
                        "INSERT INTO Usuario (nome, email, senha, dataCadastro, statusUsuario) VALUES (?, ?, ?, ?, ?)"
                );

                // Definindo os valores dos parâmetros
                pst.setString(1, usuario.getNome());
                pst.setString(2, usuario.getEmail());
                pst.setString(3, usuario.getSenha());
                pst.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
                pst.setString(5, "ATIVO"); // Definindo o status como 'ATIVO'

                // Executando a inserção
                resposta = pst.executeUpdate();

                // Fechando o PreparedStatement e a conexão
                pst.close();
                conn.close();
            } else {
                System.out.println("Falha na conexão com o banco de dados!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resposta;
    }

    // Método para verificar o login do usuário
    public static boolean verificarLogin(String email, String senha, Context baseContext) {
        boolean autenticacao = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // Conectando ao banco de dados
            conn = BancoDeDados.conectar(baseContext);

            if (conn != null) {
                // Preparando a instrução SQL para verificação de login
                pst = conn.prepareStatement(
                        "SELECT * FROM Usuario WHERE email = ? AND senha = ? AND statusUsuario = 'ATIVO'"
                );
                pst.setString(1, email);
                pst.setString(2, senha);

                // Executando a consulta
                rs = pst.executeQuery();

                // Verificando se o usuário foi encontrado
                if (rs.next()) {
                    autenticacao = true;  // Usuário autenticado
                }
            } else {
                System.out.println("Falha na conexão com o banco de dados!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Fechando os recursos abertos
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return autenticacao;
    }
}