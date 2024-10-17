package com.example.tccmobile;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoDeDados {
    private static final String TAG = "BancoDeDados";

    public static Connection conectar(Context context) {
        Connection conn = null;
        try {
            StrictMode.ThreadPolicy politica;
            politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://172.19.2.127;" +
                    "databaseName=bdPortal360;user=sa;password=@ITB123456;");
        } catch (SQLException e) {
            Log.e(TAG, "Erro de SQL: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Erro ao carregar o driver: " + e.getMessage());
        }
        return conn;
    }
}