/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author Valtanders
 */
public class Transaccion {
    private int id;
    private String medioDePago;
    private int pv;
    private int numero;
    private CabOperacion cabOperacion;
    
    public Transaccion() {}
    
    public Transaccion(int id, String medioDePago, int pv, int numero, CabOperacion cabOperacion) {
        this.id = id;
        this.medioDePago = medioDePago;
        this.pv = pv;
        this.numero = numero;
        this.cabOperacion = cabOperacion;    
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the medioDePago
     */
    public String getMedioDePago() {
        return medioDePago;
    }

    /**
     * @param medioDePago the medioDePago to set
     */
    public void setMedioDePago(String medioDePago) {
        this.medioDePago = medioDePago;
    }

    /**
     * @return the pv
     */
    public int getPv() {
        return pv;
    }

    /**
     * @param pv the pv to set
     */
    public void setPv(int pv) {
        this.pv = pv;
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the cabOperacion
     */
    public CabOperacion getCabOperacion() {
        return cabOperacion;
    }

    /**
     * @param cabOperacion the cabOperacion to set
     */
    public void setCabOperacion(CabOperacion cabOperacion) {
        this.cabOperacion = cabOperacion;
    }
}
