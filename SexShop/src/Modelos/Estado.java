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
public class Estado {
    private int id;
    private String descripcion;
    
    public Estado() {}
    
    public Estado(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    
    public Estado(int id) {
        this.id = id;
    }
    
    public Estado(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
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
    
}
