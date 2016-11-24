/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelos.Categoria;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Valtanders
 */
public class BDCategorias implements Interfaz {
    
    private static BDCategorias instance;

    public static BDCategorias getIntance() {
        if (instance == null) {
            instance = new BDCategorias();
        }
        return instance;
    }
    

    @Override
    public int agregar(Object valor) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object modificar(Object valor) throws SQLException {
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
        String insert = "select * from categorias";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            rs = sentencia.executeQuery();

            Categoria categoria;
            while (rs.next()) {
                categoria = new Categoria(rs.getInt(1),rs.getString(2));
                resp.put(categoria.getId(), categoria);
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
