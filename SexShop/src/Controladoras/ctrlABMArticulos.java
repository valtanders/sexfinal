/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladoras;

import DAO.BDArticulos;
import Modelos.Articulo;
import Modelos.Categoria;
import Modelos.Estado;
import Modelos.Proveedor;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Valtanders
 */
public class ctrlABMArticulos {
    
    BDArticulos bdarticulos = BDArticulos.getIntance();
    
    public int agregarArticulo(String descripcion, float costo, float precio, int idproveedor, int idcategoria) throws SQLException {
        java.util.Date fechaact = new Date();
        java.sql.Date sqlDate = new java.sql.Date(fechaact.getTime());
        return bdarticulos.agregar(new Articulo(descripcion, costo, precio, sqlDate , new Proveedor(idproveedor), new Categoria(idcategoria), new Estado(1)));
    }
    
    public Articulo modificarArticulo(int id, String descripcion, float costo, float precio, int idproveedor, int idcategoria, int estado, String desc) throws SQLException{
        return bdarticulos.modificar(new Articulo(id,descripcion, costo, precio , new Proveedor(idproveedor), new Categoria(idcategoria),new Estado(estado,desc)));
    }
    
    public void eliminarArticulo(int id) throws SQLException{
        bdarticulos.eliminar(id);
    }
    
    public ConcurrentHashMap traerTodos() throws SQLException {
        return bdarticulos.traerTodos();
    }
    
    public boolean buscaCodigo(String cod) throws SQLException {
        return bdarticulos.BuscaCodigo(cod);
    }
    
    public Articulo traerPorId(int id) throws SQLException {
        return bdarticulos.traerPorId(id);
    }
}
