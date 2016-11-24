/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladoras;

import DAO.BDCategorias;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Valtanders
 */
public class ctrlABMCategorias {
    BDCategorias bDCategorias = BDCategorias.getIntance();
    
    public ConcurrentHashMap traerTodos() throws SQLException{
        return bDCategorias.traerTodos();
    }
}
