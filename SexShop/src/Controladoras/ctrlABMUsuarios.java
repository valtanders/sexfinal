/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladoras;

import DAO.BDUsuario;
import Modelos.Usuario;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Valtanders
 */
public class ctrlABMUsuarios {
    
    BDUsuario bdusuario = BDUsuario.getIntance();
    
    public int agregarUsario(String nombre, String password, int rol) throws SQLException {
        return bdusuario.agregar(new Usuario(nombre,password,rol));
    }
    
    public Usuario traerogueado(String nombre, String pass) throws SQLException {
        return bdusuario.traerLogueado(nombre, pass);
    }
    
    public ConcurrentHashMap traerTodo() throws SQLException {
        return bdusuario.traerTodos();
    }
}
