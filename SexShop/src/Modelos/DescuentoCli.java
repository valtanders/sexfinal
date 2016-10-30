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
public class DescuentoCli {
    
    private int idDescuentoCli;
    private String descripcion;
    private int porcentaje;
    private float importe;
    
    public DescuentoCli() {};
    
    public DescuentoCli(int id) {
        this.idDescuentoCli = id;
    };
    
    public DescuentoCli(int id, String desc, int porc, float imp) {
        this.idDescuentoCli = id;
        this.descripcion = desc;
        this.porcentaje = porc;
        this.importe = imp;
    };

    /**
     * @return the idDescuentoCli
     */
    public int getIdDescuentoCli() {
        return idDescuentoCli;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the porcentaje
     */
    public int getPorcentaje() {
        return porcentaje;
    }

    /**
     * @param porcentaje the porcentaje to set
     */
    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    /**
     * @return the importe
     */
    public float getImporte() {
        return importe;
    }

    /**
     * @param importe the importe to set
     */
    public void setImporte(float importe) {
        this.importe = importe;
    }
}
