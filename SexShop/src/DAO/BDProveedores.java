/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelos.Estado;
import Modelos.Proveedor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Valtanders
 */
public class BDProveedores implements Interfaz {
    
    private static BDProveedores instance;

    public static BDProveedores getIntance() {
        if (instance == null) {
            instance = new BDProveedores();
        }
        return instance;
    }

    @Override
    public int agregar(Object valor) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Proveedor modificar(Object valor) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ConcurrentHashMap traerTodos() throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        ConcurrentHashMap resp = new ConcurrentHashMap<String, String>();
        oCon.getConexion();
        String insert = "select P.idProveedores, P.razonsocial, P.direccion, P.telefono, P.mail, P.codigoProveedor, P.fk_idEstados, E.descripcion from Proveedores P\n" +
                        "inner join Estados E on E.idEstados = P.fk_idEstados\n" +
                        "where P.fk_idEstados <> 3";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            rs = sentencia.executeQuery();

            Proveedor proveedor;
            Estado est;
            while (rs.next()) {
                est = new Estado(rs.getInt(7),rs.getString(8));
                proveedor = new Proveedor(rs.getInt(1),rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6), est);
                resp.put(proveedor.getId(), proveedor);
            }
            rs.close();
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return resp;
        }
    }
    
}