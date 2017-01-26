/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladoras;

import DAO.BDAlquiler;
import Modelos.Alquiler;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Valtanders
 */
public class ctrlAlquiler {
    BDAlquiler bdalquiler = BDAlquiler.getIntance();
    
    public int cargarPrecio(Alquiler alquiler) throws SQLException {
        return bdalquiler.cargarPrecio(alquiler);
    }
    
    public void updateUltimoPrecio() throws SQLException {
        bdalquiler.updateUltimoPrecio();
    }
    
    public void traerUltimoActivo() throws SQLException {
        bdalquiler.traerUltimoActivo();
    }
    
    public ConcurrentHashMap traerTodos() throws SQLException {
        return bdalquiler.traerTodos();
    }
    
}
