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
public class Cliente {

    /**
     * @return the codigoCliente
     */
    public String getCodigoCliente() {
        return codigoCliente;
    }

    /**
     * @param codigoCliente the codigoCliente to set
     */
    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }
    private int idCliente;
    private DescuentoCli descuento;
    private String notas;
    private String nombre;
    private String apellido;
    private String direccion;
    private String mail;
    private String telefono;
    private int dni;
    private Date fechanac;
    private String codigoCliente;
    private int estado;
    
    public Cliente() {};
    
    public Cliente(int id, DescuentoCli desccli, String notas, String nom, String ape, String direc, String mail, String tel, int dni, Date fecha, String codCli, int estado) {
        this.idCliente = id;
        this.descuento = desccli;
        this.notas = notas;
        this.nombre = nom;
        this.apellido = ape;
        this.direccion = direc;
        this.mail = mail;
        this.telefono = tel;
        this.dni = dni;
        this.fechanac = fecha;
        this.codigoCliente = codCli;
        this.estado = estado;
    };
    
    public Cliente(DescuentoCli desccli, String notas, String nom, String ape, String direc, String mail, String tel, int dni, Date fecha,String codCli, int estado) {
        this.descuento = desccli;
        this.notas = notas;
        this.nombre = nom;
        this.apellido = ape;
        this.direccion = direc;
        this.mail = mail;
        this.telefono = tel;
        this.dni = dni;
        this.fechanac = fecha;
        this.codigoCliente = codCli;
        this.estado = estado;
    };

    /**
     * @return the idCliente
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * @return the notas
     */
    public String getNotas() {
        return notas;
    }

    /**
     * @param notas the notas to set
     */
    public void setNotas(String notas) {
        this.notas = notas;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the dni
     */
    public int getDni() {
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(int dni) {
        this.dni = dni;
    }

    /**
     * @return the fechanac
     */
    public Date getFechanac() {
        return fechanac;
    }

    /**
     * @param fechanac the fechanac to set
     */
    public void setFechanac(Date fechanac) {
        this.fechanac = fechanac;
    }

    /**
     * @return the descuento
     */
    public DescuentoCli getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(DescuentoCli descuento) {
        this.descuento = descuento;
    }
    
    
}
