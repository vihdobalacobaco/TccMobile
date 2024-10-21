package com.example.tccmobile;

import android.content.Context;
import android.util.Base64;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.transform.Result;

public class UsuarioCrud {

    // Método para inserir o usuário com senha criptografada
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

                // Codificando a senha em base64
                String senhaCodificada = codificarBase64(usuario.getSenha());

                // Definindo os valores dos parâmetros
                pst.setString(1, usuario.getNome());
                pst.setString(2, usuario.getEmail());
                pst.setString(3, senhaCodificada); // Senha codificada em base64
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
                // Codificar a senha fornecida pelo usuário em base64
                String senhaCodificada = codificarBase64(senha);

                // Preparando a instrução SQL para verificação de login
                pst = conn.prepareStatement(
                        "SELECT * FROM Usuario WHERE email = ? AND senha = ? AND statusUsuario = 'ATIVO'"
                );
                pst.setString(1, email);
                pst.setString(2, senhaCodificada); // Comparar a senha codificada

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

    // Método para codificar a senha em base64
    private static String codificarBase64(String senha) {
        // Converter a senha em bytes
        byte[] senhaBytes = senha.getBytes();
        // Codificar os bytes em base64
        return Base64.encodeToString(senhaBytes, Base64.DEFAULT).trim();
    }
}