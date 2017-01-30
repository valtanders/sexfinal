/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author valtanders
 */
public class DetCaja {
    private int id;
    private float monto;
    private String concepto;
    private Caja caja;
    private DetOperacion detalleOperacion;
    private String tipoOperacion;
    
    public DetCaja() {}
    
    public DetCaja(int id, float monto, String concepto, Caja caja, DetOperacion detOperacion, String tipoOperacion) {
        this.id = id;
        this.monto = monto;
        this.concepto = concepto;
        this.caja = caja;
        this.detalleOperacion = detOperacion;
        this.tipoOperacion = tipoOperacion;
    }
    
    public DetCaja(int id, float monto, String concepto) {
        this.id = id;
        this.monto = monto;
        this.concepto = concepto;
    }
    
    public DetCaja(float monto, String concepto, Caja caja, DetOperacion detOperacion, String tipoOperacion) {
        this.monto = monto;
        this.concepto = concepto;
        this.caja = caja;
        this.detalleOperacion = detOperacion;
        this.tipoOperacion = tipoOperacion;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the monto
     */
    public float getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(float monto) {
        this.monto = monto;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * @return the caja
     */
    public Caja getCaja() {
        return caja;
    }

    /**
     * @param caja the caja to set
     */
    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    /**
     * @return the detalleOperacion
     */
    public DetOperacion getDetalleOperacion() {
        return detalleOperacion;
    }

    /**
     * @param detalleOperacion the detalleOperacion to set
     */
    public void setDetalleOperacion(DetOperacion detalleOperacion) {
        this.detalleOperacion = detalleOperacion;
    }

    /**
     * @return the tipoOperacion
     */
    public String getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * @param tipoOperacion the tipoOperacion to set
     */
    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }
}
