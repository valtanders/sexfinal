/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelos.Alquiler;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Valtanders
 */
public class BDAlquiler {
    
    private static BDAlquiler instance;

    public static BDAlquiler getIntance() {
        if (instance == null) {
            instance = new BDAlquiler();
        }
        return instance;
    }
    
    public int cargarPrecio(Alquiler alquiler) throws SQLException {
        Conexion oCon = new Conexion();
        oCon.getConexion();
        java.sql.Date sqldate = new java.sql.Date(alquiler.getFecha().getTime());
        ResultSet rs = null;
        int llave = 0;
        String insert = "insert into alquiler(precio, fecha, activo) values(?,?,?)";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            sentencia.setFloat(1, alquiler.getPrecio());
            sentencia.setDate(2, sqldate);
            sentencia.setBoolean(3, true);
            sentencia.execute();
            rs = sentencia.getGeneratedKeys();
            if (rs != null && rs.next()) {
                llave = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return llave;
        }
    }
    
    public void updateUltimoPrecio() throws SQLException {
        Conexion oCon = new Conexion();
        oCon.getConexion();
        String update = "update alquiler set activo = false where activo = true";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(update);
            sentencia.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
        }
    }
    
    public Alquiler traerUltimoActivo() throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        Alquiler alquiler = null;
        oCon.getConexion();
        String select = "Select * from alquiler where activo = true";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                alquiler = new Alquiler(rs.getInt(1), rs.getFloat(2), rs.getDate(3), rs.getBoolean(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return alquiler;
        }
    }
    
    public ConcurrentHashMap traerTodos() throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        ConcurrentHashMap lista = new ConcurrentHashMap();
        Alquiler alquiler = null;
        oCon.getConexion();
        String select = "Select * from alquiler order by fecha desc";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                alquiler = new Alquiler(rs.getInt(1), rs.getFloat(2), rs.getDate(3), rs.getBoolean(4));
                lista.put(alquiler.getId(), alquiler);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return lista;
        }
    }
    
}
