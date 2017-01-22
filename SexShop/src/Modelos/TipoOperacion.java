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
public class TipoOperacion {
    private int id;
    private String concepto;
    
    public TipoOperacion() {}
    
    public TipoOperacion(int id, String concepto) {
        this.id = id;
        this.concepto = concepto;
    }
}
