/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelos.Cliente;
import Modelos.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Valtanders
 */
public class BDUsuario implements Interfaz {
    
    private static BDUsuario instance;

    public static BDUsuario getIntance() {
        if (instance == null) {
            instance = new BDUsuario();
        }
        return instance;
    }


    @Override
    public int agregar(Object valor) throws SQLException {
        Usuario user = (Usuario) valor;
        Conexion oCon = new Conexion();
        oCon.getConexion();
        int llave = 0;
        String insert = "INSERT INTO usuario (nombre,password,fk_idRoles) Values (?,?,?)";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, user.getNombre());
            sentencia.setString(2, user.getPassword());
            sentencia.setInt(3, user.getRol());
            sentencia.execute();
            ResultSet rs = sentencia.getGeneratedKeys();
            if (rs != null && rs.next()) {
                llave = rs.getInt(1);
            }
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return llave;
        }
    }

    @Override
    public Cliente modificar(Object valor) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ConcurrentHashMap traerTodos() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Usuario traerLogueado(String nombre, String pass) throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        Usuario user = null;
        oCon.getConexion();
        String insert = "SELECT * FROM usuario where nombre = ? and password = ?";
        try{
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            sentencia.setString(1, nombre);
            sentencia.setString(2, pass);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                user = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            }
            rs.close();
            sentencia.close();
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return user;
        }
    }
    
}
