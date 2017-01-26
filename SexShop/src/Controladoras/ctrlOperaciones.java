/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladoras;

import DAO.BDOperaciones;
import Modelos.CabOperacion;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Valtanders
 */
public class ctrlOperaciones {
    BDOperaciones bdoperaciones = BDOperaciones.getIntance();
    
    public void cargaOperacion(CabOperacion cabOp, ArrayList detalle, int keyCaja) throws SQLException{
        bdoperaciones.cargaOperacion(cabOp, detalle, keyCaja);
    }
}
