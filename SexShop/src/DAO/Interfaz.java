/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import Modelos.Cliente;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 * @author Valtanders
 */
public interface Interfaz <T> {
    public int agregar(T valor)throws SQLException;
    public Cliente modificar(T valor)throws SQLException;
    public void eliminar(T valor)throws SQLException;
    public ConcurrentHashMap traerTodos()throws SQLException;

}
