/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelos.CabOperacion;
import Modelos.DetOperacion;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Valtanders
 */
public class BDOperaciones {

    private static BDOperaciones instance;

    public static BDOperaciones getIntance() {
        if (instance == null) {
            instance = new BDOperaciones();
        }
        return instance;
    }

    public void cargaOperacion(CabOperacion cabOp, ArrayList detalle, int keyCaja, String mp, int numero) throws SQLException {
        Conexion oCon = new Conexion();
        Connection con = oCon.getConexion();
        String insert = "insert into operacion(fk_idTipoperacion, fk_idcliente, fk_idUsuarioLogueado, fecha, idUsuarioVendedor, total) values(?,?,?,?,?,?)";
        int cabKey = 0;
        try {
            con.setAutoCommit(false);
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            sentencia.setInt(1, cabOp.getTipoOperacion().getId());
            sentencia.setInt(2, cabOp.getCliente().getIdCliente());
            sentencia.setInt(3, cabOp.getUsuario().getId());
            sentencia.setDate(4, new java.sql.Date(cabOp.getFecha().getTime()));
            sentencia.setInt(5, cabOp.getIdVendedor());
            sentencia.setFloat(6, cabOp.getTotal());
            sentencia.execute();
            ResultSet rs = sentencia.getGeneratedKeys();
            if (rs != null && rs.next()) {
                cabKey = rs.getInt(1);
            }
            insert = "insert into detalleoperacion(fk_idArticulos, fk_idOperacion, precio, cantidad) values(?," + cabKey + ",?,?)";
            for (int i = 0; detalle.size() > i; i++) {
                sentencia = null;
                sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
                sentencia.setInt(1, ((DetOperacion) detalle.get(i)).getArticulo().getId());
                sentencia.setFloat(2, ((DetOperacion) detalle.get(i)).getPrecio());
                sentencia.setInt(3, ((DetOperacion) detalle.get(i)).getCantidad());
                sentencia.execute();
            }
            insert = "insert into transaccion values(0,?,1,?," + cabKey + ")";
            sentencia = null;
            sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            sentencia.setString(1, mp);
            sentencia.setInt(2,numero);
            sentencia.execute();
            if (cabOp.getTipoOperacion().getId() == 2) {
                insert = "Update articulos set cantidad = cantidad - ? where idarticulos = ?";
                for (int i = 0; detalle.size() > i; i++) {
                    sentencia = null;
                    sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
                    sentencia.setInt(2, ((DetOperacion) detalle.get(i)).getArticulo().getId());
                    sentencia.setInt(1, ((DetOperacion) detalle.get(i)).getCantidad());
                    sentencia.execute();
                }
            } else {
                insert = "insert into devoluciones values(0,?,?,?,?)";
                java.util.Date date = new Date();
                Timestamp tsh = new Timestamp(date.getTime());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_YEAR, 2);
                Timestamp tsa = new Timestamp(calendar.getTimeInMillis());
                for (int i = 0; detalle.size() > i; i++) {
                    sentencia = null;
                    sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
                    sentencia.setInt(1, cabOp.getCliente().getIdCliente());
                    sentencia.setInt(2, ((DetOperacion) detalle.get(i)).getArticulo().getId());
                    sentencia.setTimestamp(3, tsh);
                    sentencia.setTimestamp(4, tsa);
                }
            }           
            insert = "insert into detallecaja(concepto, fk_idCabVenta, fk_idCaja,monto) values(?,?,?,?)";
            sentencia = null;
            sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            if (cabOp.getTipoOperacion().getId() == 2) {
                sentencia.setString(1, "Venta");
            } else {
                sentencia.setString(1, "Alquiler");
            }
            sentencia.setInt(2, cabKey);
            sentencia.setInt(3, keyCaja);
            sentencia.setFloat(4, cabOp.getTotal());
            sentencia.execute();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
        } finally {
            con.close();
        }
    }
}
