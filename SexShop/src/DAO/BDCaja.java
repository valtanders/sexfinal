/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelos.Caja;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Valtanders
 */
public class BDCaja {

    private static BDCaja instance;

    public static BDCaja getIntance() {
        if (instance == null) {
            instance = new BDCaja();
        }
        return instance;
    }

    public Caja cajaAbierta() throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        boolean abierta = false;
        Caja caja = null;
        oCon.getConexion();
        String select = "Select * from caja where abierta = true";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                caja = new Caja(rs.getInt(1),rs.getDate(2),null,rs.getBoolean(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return caja;
        }
    }
    
    
    public void cerrarCaja(Date ahora) throws SQLException{
        Conexion oCon = new Conexion();
        oCon.getConexion();
        java.sql.Date sqldate = new java.sql.Date(ahora.getTime());
        String insert = "update caja set fechacierre = ?,abierta = false where abierta = true";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            sentencia.setDate(1, sqldate);
            sentencia.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
        }
    }

    public void abrirCaja(Date ahora) throws SQLException {
        Conexion oCon = new Conexion();
        oCon.getConexion();
        java.sql.Date sqldate = new java.sql.Date(ahora.getTime());
        String insert = "insert into caja(fechaapertura, fechacierre, abierta) values(?,null,1)";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            sentencia.setDate(1, sqldate);
            sentencia.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
        }
    }
}
