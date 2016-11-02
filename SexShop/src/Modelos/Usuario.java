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
public class Usuario {
    
    
    private int id;
    private String nombre;
    private String password;
    private int rol;

    public Usuario () {}
    
    public Usuario (int id, String nom, String pass, int rol) {
        this.id = id;
        this.nombre = nom;
        this.password = pass;
        this.rol = rol;
    }
    
    public Usuario (String nom, String pass, int rol) {
        this.nombre = nom;
        this.password = pass;
        this.rol = rol;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
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
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the rol
     */
    public int getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(int rol) {
        this.rol = rol;
    }
    
    
}
