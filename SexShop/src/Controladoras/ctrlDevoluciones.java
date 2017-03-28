/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladoras;

import DAO.BDDevoluciones;
import Modelos.Devolucion;
import java.sql.Date;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Valtanders
 */
public class ctrlDevoluciones {
    BDDevoluciones bddevoluciones = BDDevoluciones.getIntance();
    
    public ConcurrentHashMap traerDevolucionesPend() throws SQLException{
        return bddevoluciones.traerDevolucionesPend();
    }
    
    public ConcurrentHashMap traerDevolucionesPend(int id, Date fecha) throws SQLException{
        return bddevoluciones.traeUltima(id, fecha);
    }
    
    public void prosecaDevolucion(Devolucion devo, int idcaja, float monto, Date fecha) throws SQLException{
        bddevoluciones.procesarDevolucion(devo, idcaja, monto, fecha);
    }
}
