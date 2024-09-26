package com.example.tccmobile;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoDeDados {

    public static Connection conectar () {
        Connection conn = null;
        try {
            StrictMode.ThreadPolicy politica;
            politica = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://172.19.1.250"+
                    "databaseName=Banco_Android; user=sa;password=@ITB123456;");
             return conn;
        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
