
package Modelos;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class FormatoTabla extends DefaultTableCellRenderer{
    
             
     public FormatoTabla() {
        super();
        this.setOpaque(true);
     }                 
      
    @Override
    public Component getTableCellRendererComponent(JTable jTable2, Object value, boolean selected, boolean focused, int row, int column) {
       
      String aux =   (String) jTable2.getValueAt(row, 2);    //enviado
      jTable2.setShowGrid(true);    
     
      
        if (aux.equals("No Activo")){  
            setBackground(new Color(250, 180, 190));  //color rojo en rgb
            setForeground(Color.BLACK);             
        }   
        
        else if (aux.equals("Activo")) {
            setBackground(new Color(190, 255, 190));  //color verde
            setForeground(Color.black);           
            
       } else{
            setBackground(Color.WHITE);              //color blanco
            setForeground(Color.black);  
             
           
       }
                
        super.getTableCellRendererComponent(jTable2, value, selected, focused, row, column);
        return this;
    }     
}
