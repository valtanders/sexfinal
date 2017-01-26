/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladoras;

import DAO.BDCaja;
import Modelos.Caja;
import Modelos.DetCaja;
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
    
    public int abrirCaja(Date ahora) throws SQLException {
        return bdcaja.abrirCaja(ahora);
    }
    
    public DetCaja traeUltimoDetalleAper() throws SQLException {
        return bdcaja.traeUltimoDetalleAper();
    }
    
    public void insertarDetalle(DetCaja detCaja) throws SQLException{
        bdcaja.insertarDetalleCaja(detCaja);
    }
    
    public void cerrarCaja(Date ahora) throws SQLException {
        bdcaja.cerrarCaja(ahora);
    }
}
