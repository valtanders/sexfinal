/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelos.Caja;
import Modelos.DetCaja;
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
    
    public void insertarDetalleCaja(DetCaja detCaja) throws SQLException{
        Conexion oCon = new Conexion();
        oCon.getConexion();
        String insert = "insert into detallecaja(concepto, fk_idCabVenta, fk_idCaja,monto) values(?,?,?,?)";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            sentencia.setString(1, detCaja.getConcepto());
            if(detCaja.getDetalleOperacion() != null)
                sentencia.setInt(2, detCaja.getDetalleOperacion().getId());
            else
                sentencia.setNull(2, java.sql.Types.INTEGER);
            sentencia.setInt(3, detCaja.getCaja().getIdCaja());
            sentencia.setFloat(4, detCaja.getMonto());
            sentencia.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
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

    public int abrirCaja(Date ahora) throws SQLException {
        Conexion oCon = new Conexion();
        oCon.getConexion();
        java.sql.Date sqldate = new java.sql.Date(ahora.getTime());
        int llave = 0;
        String insert = "insert into caja(fechaapertura, fechacierre, abierta) values(?,null,1)";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            sentencia.setDate(1, sqldate);
            sentencia.execute();
            ResultSet rs = sentencia.getGeneratedKeys();
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
}
