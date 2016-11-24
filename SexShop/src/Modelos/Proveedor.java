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
public class Proveedor {
    private int id;
    private String razonsocial;
    private String direccion;
    private String telefono;
    private String mail;
    private String codigo;
    private Estado estado;
    
    public Proveedor() {}
    
    public Proveedor(int id) {this.id = id;}
    
    public Proveedor(int id, String razonsocial, String direccion, String telefono, String mail, String codigo, Estado estado) {
        this.id = id;
        this.razonsocial = razonsocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.mail = mail;
        this.codigo = codigo;
        this.estado = estado;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the razonsocial
     */
    public String getRazonsocial() {
        return razonsocial;
    }

    /**
     * @param razonsocial the razonsocial to set
     */
    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
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
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
    
    @Override
    public String toString() {
        return this.razonsocial;
    }
}
