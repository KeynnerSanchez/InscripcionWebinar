package com.mycompany.inscripcionwebinar.Modelo.Persistencia;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConexionBD {

    private static String url = "jdbc:mysql://localhost:3306/my_db";
    private static String user = "root";
    private static String password = "051015_sanchez";
    public static Connection con = null;

    public static Connection MysConnection() throws SQLException {
        return getConnection(url, user, password);
    }

    private static Connection getConnection(String url, String user, String password) {
        try {
            con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                DatabaseMetaData meta = con.getMetaData();
                System.out.println("Base de datos conectada " + meta.getDriverName());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return con;
    }
}
