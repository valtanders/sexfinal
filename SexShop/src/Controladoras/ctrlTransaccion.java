/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladoras;

import DAO.BDTransaccion;
import java.sql.Date;
import java.sql.SQLException;

/**
 *
 * @author Valtanders
 */
public class ctrlTransaccion {
    BDTransaccion bdTransaccion = BDTransaccion.getIntance();
    
    public int traeUltimoNumero(int tipo) throws SQLException {
        return bdTransaccion.traeUltimoNumero(tipo);
    }
    
    public String traeMediodePago(int id, Date fecha) throws SQLException {
        return bdTransaccion.traeMediodePago(id,fecha);
    }
}
