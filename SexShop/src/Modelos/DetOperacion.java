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
public class DetOperacion {

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the articulo
     */
    public Articulo getArticulo() {
        return articulo;
    }

    /**
     * @param articulo the articulo to set
     */
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
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
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    private int id;
    private Articulo articulo;
    private float precio;
    private int cantidad;
    
    public DetOperacion() {}
    
    public DetOperacion(int id, Articulo articulo, float precio, int cantidad) {
        this.id = id;
        this.articulo = articulo;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    
    public DetOperacion(Articulo articulo, float precio, int cantidad) {
        this.articulo = articulo;
        this.precio = precio;
        this.cantidad = cantidad;
    }
}
