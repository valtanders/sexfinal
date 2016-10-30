/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Valtanders
 */
public class Conexion {
    private Connection conexion;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/sexshop";

    static final String USER = "root";
    static final String PASS = "";

    public Connection getConexion() {
        this.conexion = null;

        try {
            Class.forName(JDBC_DRIVER);
            conexion = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (Exception ex){
            ex.printStackTrace();
        }
            return conexion;
    }

    public void close() throws SQLException {
        if (!conexion.isClosed()) {
            conexion.close();
        }
    }
}
