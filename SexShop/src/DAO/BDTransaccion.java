/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Valtanders
 */
public class BDTransaccion {
    
    private static BDTransaccion instance;

    public static BDTransaccion getIntance() {
        if (instance == null) {
            instance = new BDTransaccion();
        }
        return instance;
    }
    
    public int traeUltimoNumero(int tipo) throws SQLException {
        Conexion oCon = new Conexion();
        Connection con = oCon.getConexion();
        ResultSet rs = null;
        int num = 0;
        String select = "select max(numero) as numero from transaccion t\n" +
                          "join operacion o on t.fk_idcabventa = o.idcabventa\n" +
                          "where o.fk_idtipoperacion = 1";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                num = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
        } finally {
            con.close();
            return num;
        }
    }
}
