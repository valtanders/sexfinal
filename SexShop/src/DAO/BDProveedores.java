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
        Proveedor proveedor = (Proveedor) valor;
        Conexion oCon = new Conexion();
        oCon.getConexion();
        ResultSet rs = null;
        int llave = 0;
        String insert = "insert into proveedores(razonsocial, direccion, telefono, mail, fk_idEstados) values(?,?,?,?,?)";
        try {
            com.mysql.jdbc.PreparedStatement sentencia = (com.mysql.jdbc.PreparedStatement) oCon.getConexion().prepareStatement(insert, com.mysql.jdbc.PreparedStatement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, proveedor.getRazonsocial());
            sentencia.setString(2, proveedor.getDireccion());
            sentencia.setString(3, proveedor.getTelefono());
            sentencia.setString(4, proveedor.getMail());
            sentencia.setInt(5, proveedor.getEstado().getId());
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

    @Override
    public Proveedor modificar(Object valor) throws SQLException {
        Conexion oCon = new Conexion();
        Proveedor proveedor = (Proveedor)valor;
        oCon.getConexion();
        String update = "update proveedores set razonsocial = ?,direccion = ?,telefono = ?,mail = ?,fk_idEstados = ? where idProveedores = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(update);
            sentencia.setString(1, proveedor.getRazonsocial());
            sentencia.setString(2, proveedor.getDireccion());
            sentencia.setString(3, proveedor.getTelefono());
            sentencia.setString(4, proveedor.getMail());
            sentencia.setInt(5,proveedor.getEstado().getId());
            sentencia.setInt(6,proveedor.getId());
            sentencia.execute();
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();            
        } finally {
            oCon.close();
            return proveedor;
        }
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
        String insert = "select P.idProveedores, P.razonsocial, P.direccion, P.telefono, P.mail, P.fk_idEstados, E.descripcion from Proveedores P\n" +
                        "inner join Estados E on E.idEstados = P.fk_idEstados\n" +
                        "where P.fk_idEstados <> 3";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            rs = sentencia.executeQuery();

            Proveedor proveedor;
            Estado est;
            while (rs.next()) {
                est = new Estado(rs.getInt(6),rs.getString(7));
                proveedor = new Proveedor(rs.getInt(1),rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5), est);
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
    
    public Proveedor traerProveedorPorId(int id) throws SQLException{
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        Proveedor proveedor = null;
        Estado est = null;
        oCon.getConexion();
        String select = "select p.idProveedores, p.razonsocial, p.direccion, p.telefono, p.mail, p.fk_idEstados,E.idEstados,E.Descripcion from proveedores p\n" +
                        "join estados e on p.fk_idEstados = e.idestados\n" +
                        "where p.idProveedores = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            sentencia.setInt(1, id);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                est = new Estado(rs.getInt(7),rs.getString(8));
                proveedor = new Proveedor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),est);
            }
            rs.close();
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return proveedor;
        }
        
    }
    
}
