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
public class Alquiler {
    private int id;
    private float precio;
    private Date fecha;
    private boolean activo;
    
    public Alquiler() {}
    
    public Alquiler(int id, float precio, Date fecha, boolean activo) {
        this.id = id;
        this.precio = precio;
        this.fecha = fecha;
        this.activo = activo;
    }
    
    public Alquiler(float precio, Date fecha, boolean activo) {
        this.precio = precio;
        this.fecha = fecha;
        this.activo = activo;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the precio
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
