/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.util.Date;

/**
 *
 * @author Valtanders
 */
public class Caja {
    private int idCaja;
    private Date fechaAper;
    private Date fechaCierre;
    private boolean abierta;
    
    public Caja () {}
    
    public Caja (int id) {
        this.idCaja = id;
    }
    
    public Caja (int id, Date fechaA, Date fechaC, boolean abierta) {
        this.idCaja = id;
        this.fechaAper = fechaA;
        this.fechaCierre = fechaC;
        this.abierta = abierta;
    }

    /**
     * @return the idCaja
     */
    public int getIdCaja() {
        return idCaja;
    }

    /**
     * @return the fechaAper
     */
    public Date getFechaAper() {
        return fechaAper;
    }

    /**
     * @param fechaAper the fechaAper to set
     */
    public void setFechaAper(Date fechaAper) {
        this.fechaAper = fechaAper;
    }

    /**
     * @return the fechaCierre
     */
    public Date getFechaCierre() {
        return fechaCierre;
    }

    /**
     * @param fechaCierre the fechaCierre to set
     */
    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    /**
     * @return the abierta
     */
    public boolean isAbierta() {
        return abierta;
    }

    /**
     * @param abierta the abierta to set
     */
    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }
}

