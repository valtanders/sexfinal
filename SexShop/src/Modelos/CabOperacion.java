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
public class CabOperacion {
    
    private int id;
    private TipoOperacion tipoOperacion;
    private Cliente cliente;
    private Usuario usuario;
    private Date fecha;
    private int idVendedor;
    private float total;
    
    public CabOperacion() {}
    
    public CabOperacion(int id, TipoOperacion tipoOperacion, Cliente cliente, Usuario usuario, Date fecha, int idVendedor, float total) {
        this.id = id;
        this.tipoOperacion = tipoOperacion;
        this.cliente = cliente;
        this.usuario = usuario;
        this.fecha = fecha;
        this.idVendedor = idVendedor;
        this.total = total;
    }
    
    public CabOperacion(TipoOperacion tipoOperacion, Cliente cliente, Usuario usuario, Date fecha, int idVendedor, float total) {
        this.tipoOperacion = tipoOperacion;
        this.cliente = cliente;
        this.usuario = usuario;
        this.fecha = fecha;
        this.idVendedor = idVendedor;
        this.total = total;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the tipoOperacion
     */
    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * @param tipoOperacion the tipoOperacion to set
     */
    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
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
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
     * @return the idVendedor
     */
    public int getIdVendedor() {
        return idVendedor;
    }

    /**
     * @param idVendedor the idVendedor to set
     */
    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    /**
     * @return the total
     */
    public float getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(float total) {
        this.total = total;
    }
}
