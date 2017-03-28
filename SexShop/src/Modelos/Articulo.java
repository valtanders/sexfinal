/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.sql.Date;

/**
 *
 * @author Valtanders
 */
public class Articulo {
    private int id;
    private String descripcion;
    private float costo;
    private float precio;
    private int cantidad;
    private Date fechaCompra;
    private Proveedor proveedor;
    private Categoria categoria;
    private Estado estado;
    
    public Articulo () {}
    
    public Articulo (int id) {this.id=id;}
    
    public Articulo (int id, String descripcion, float costo, float precio, int cantidad, Date fechacompra, Proveedor proveedor, Categoria categoria, Estado estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.costo = costo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.fechaCompra = fechacompra;
        this.proveedor = proveedor;
        this.categoria = categoria;
        this.estado = estado;
    }
    
    public Articulo (int id, String descripcion, float costo, float precio, int cantidad, Date fechacompra, Proveedor proveedor, Estado estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.costo = costo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.fechaCompra = fechacompra;
        this.proveedor = proveedor;
        this.estado = estado;
    }
    
    public Articulo (int id, String descripcion, float costo, float precio, int cantidad, Proveedor proveedor, Estado estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.costo = costo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.proveedor = proveedor;
        this.estado = estado;
    }
    
    
    public Articulo (String descripcion, float costo, float precio, int cantidad, Date fechacompra, Proveedor proveedor, Categoria categoria, Estado estado) {
        this.descripcion = descripcion;
        this.costo = costo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.fechaCompra = fechacompra;
        this.proveedor = proveedor;
        this.categoria = categoria;
        this.estado = estado;
    }
    
    public Articulo (int id,String descripcion, float costo, float precio, int cantidad, Proveedor proveedor, Categoria categoria, Estado estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.costo = costo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.proveedor = proveedor;
        this.categoria = categoria;
        this.estado = estado;
    }

    public Articulo(String descripcion, float costo, float precio, int cantidad, Proveedor proveedor, Categoria categoria, Estado estado) {
        this.descripcion = descripcion;
        this.costo = costo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.proveedor = proveedor;
        this.categoria = categoria;
        this.estado = estado;
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

    /**
     * @return the costo
     */
    public float getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(float costo) {
        this.costo = costo;
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
     * @return the fechaCompra
     */
    public Date getFechaCompra() {
        return fechaCompra;
    }

    /**
     * @param fechaCompra the fechaCompra to set
     */
    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    /**
     * @return the proveedor
     */
    public Proveedor getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * @return the categoria
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    

    /**
     * @return the estado
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
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

    /**
     * @return the codigo
     */
}
