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
}
