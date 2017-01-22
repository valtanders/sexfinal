/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladoras;

import DAO.BDCaja;
import Modelos.Caja;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Valtanders
 */
public class ctrlCaja {
    
    BDCaja bdcaja = BDCaja.getIntance();
    
    public Caja cajaAbierta() throws SQLException{
        return bdcaja.cajaAbierta();
    }
    
    public void abrirCaja(Date ahora) throws SQLException {
        bdcaja.abrirCaja(ahora);
    }
    
    public void cerrarCaja(Date ahora) throws SQLException {
        bdcaja.cerrarCaja(ahora);
    }
}
