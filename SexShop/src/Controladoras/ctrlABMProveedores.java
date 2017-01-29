/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladoras;

import DAO.BDProveedores;
import Modelos.Proveedor;
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
    
    public int agregar(Proveedor proveedor) throws SQLException{
        return bdproveedores.agregar(proveedor);
    }
    
    public Proveedor modificar(Proveedor proveedor) throws SQLException{
        return bdproveedores.modificar(proveedor);
    }
    
    public Proveedor traerPorId(int id) throws SQLException {
        return bdproveedores.traerProveedorPorId(id);
    }
}
