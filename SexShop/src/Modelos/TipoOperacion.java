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

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    private int id;
    private String concepto;
    
    public TipoOperacion() {}
    
    public TipoOperacion(int id, String concepto) {
        this.id = id;
        this.concepto = concepto;
    }
}
