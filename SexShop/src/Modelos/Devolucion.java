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
public class Devolucion {
    private int id;
    private Cliente cliente;
    private Articulo articulo;
    private Date fechaAlqui;
    private Date fechaDevol;
    
    public Devolucion() {}
    
    public Devolucion(int id, Cliente cliente, Articulo articulo, Date fechaAlqui, Date fechaDevol) {
        this.id = id;
        this.cliente = cliente;
        this.articulo = articulo;
        this.fechaAlqui = fechaAlqui;
        this.fechaDevol = fechaDevol;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
     * @return the fechaAlqui
     */
    public Date getFechaAlqui() {
        return fechaAlqui;
    }

    /**
     * @param fechaAlqui the fechaAlqui to set
     */
    public void setFechaAlqui(Date fechaAlqui) {
        this.fechaAlqui = fechaAlqui;
    }

    /**
     * @return the fechaDevol
     */
    public Date getFechaDevol() {
        return fechaDevol;
    }

    /**
     * @param fechaDevol the fechaDevol to set
     */
    public void setFechaDevol(Date fechaDevol) {
        this.fechaDevol = fechaDevol;
    }
}
