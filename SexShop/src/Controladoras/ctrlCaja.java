/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladoras;

import DAO.BDCaja;
import Modelos.Caja;
import Modelos.DetCaja;
import Modelos.Usuario;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Valtanders
 */
public class ctrlCaja {
    
    BDCaja bdcaja = BDCaja.getIntance();
    
    public Caja cajaAbierta() throws SQLException{
        return bdcaja.cajaAbierta();
    }
    
    public int abrirCaja(Date ahora, Usuario user) throws SQLException {
        return bdcaja.abrirCaja(ahora,user);
    }
    
    public DetCaja traeUltimoDetalleAper() throws SQLException {
        return bdcaja.traeUltimoDetalleAper();
    }
    
    public int insertarDetalle(DetCaja detCaja) throws SQLException{
        return bdcaja.insertarDetalleCaja(detCaja);
    }
    
    public void modificaDetalle(DetCaja detcaja) throws SQLException {
        bdcaja.modificarDetalle(detcaja);
    }
    
    public void eliminarDetalle(int id) throws SQLException{
        bdcaja.eliminarDetalle(id);
    }
    
    public void cerrarCaja(Date ahora) throws SQLException {
        bdcaja.cerrarCaja(ahora);
    }
    
    public Caja traeUltimoCierre() throws SQLException{
        return bdcaja.traerUltimoCierre();
    }
    
    public ConcurrentHashMap traerDetallePorCaja(int id) throws SQLException {
        return bdcaja.traeDetallePorCaja(id);
    }
}
