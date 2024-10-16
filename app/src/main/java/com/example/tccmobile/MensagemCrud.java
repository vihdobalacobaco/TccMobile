package com.example.tccmobile;

import android.content.Context;
import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MensagemCrud {
    public static int InserirMensagem(Mensagem mensagem, Context ctx) {
        int resposta = 0;
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            // Conectando ao banco de dados
            conn = BancoDeDados.conectar(ctx);
            if (conn != null) {
                // Preparando a instrução SQL para inserção
                pst = conn.prepareStatement(
                        "INSERT INTO Mensagem (dataMensagem, emissor, tipo, email, texto, statusMensagem) VALUES (?, ?, ?, ?, ?, ?)"
                );

                // Definindo os valores dos parâmetros
                pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis())); // dataMensagem
                pst.setString(2, mensagem.getNome()); // emissor
                pst.setString(3, "DÚVIDA"); // tipo (exemplo de valor, ajuste conforme necessário)
                pst.setString(4, mensagem.getEmail()); // email
                pst.setString(5, mensagem.getTexto()); // texto
                pst.setString(6, "ATIVO"); // statusMensagem

                // Executando a inserção
                resposta = pst.executeUpdate();

                // Verifica se a inserção foi bem-sucedida
                if (resposta > 0) {
                    Log.d("MensagemCrud", "Mensagem inserida com sucesso.");
                } else {
                    Log.d("MensagemCrud", "Nenhuma linha inserida.");
                }
            } else {
                Log.e("MensagemCrud", "Falha na conexão com o banco de dados!");
            }
        } catch (SQLException e) {
            Log.e("MensagemCrud", "Erro ao inserir mensagem: " + e.getMessage());
        } finally {
            // Fechando recursos
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                Log.e("MensagemCrud", "Erro ao fechar recursos: " + e.getMessage());
            }
        }
        return resposta;
    }
}