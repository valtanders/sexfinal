/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladoras;

import DAO.BDProveedores;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Valtanders
 */
public class ctrlABMProveedores {
    
    BDProveedores bdproveedores = BDProveedores.getIntance();
    
    public ConcurrentHashMap traerTodos () throws SQLException {
        return bdproveedores.traerTodos();
    }
}
