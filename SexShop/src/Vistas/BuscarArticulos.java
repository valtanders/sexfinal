/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Modelos.Articulo;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Valtanders
 */
public class BuscarArticulos extends javax.swing.JDialog {

    /**
     * Creates new form BuscarArticulos
     */
    ConcurrentHashMap listaArticulos;
    private Articulo articulo;
    JDialog esto;
    private int cantidad;

    public BuscarArticulos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //this.setIconImage(new ImageIcon(getClass().getResource("../Imagenes/3n_ico.png")).getImage());
    }

    public BuscarArticulos(java.awt.Frame parent, boolean modal, ConcurrentHashMap listaarti, String cat) {
        super(parent, modal);
        this.setTitle("Buscar Articulos");
        initComponents();
        cantidad = 1;
        jspCantidad.setValue(1);
        this.listaArticulos = listaarti;
        esto = this;
        DefaultTableModel dtmarti = new DefaultTableModel(new Object[]{"Codigo", "Descripcion", "Precio"}, 0) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
            public Class getColumnClass(int c) {
                if (getValueAt(0, c) != null)
                    return getValueAt(0, c).getClass();
                else
                    return Object.class;
            }
        };
        for (Iterator it = listaArticulos.entrySet().iterator(); it.hasNext();) {
            ConcurrentHashMap.Entry<?, ?> entry = (ConcurrentHashMap.Entry<?, ?>) it.next();
            if (cat.equals("venta") && ((Articulo) entry.getValue()).getEstado().getId() == 1) {
                dtmarti.addRow(new Object[]{entry.getKey(), ((Articulo) entry.getValue()).getDescripcion().toUpperCase(), ((Articulo) entry.getValue()).getPrecio()});
            } else {
                if (((Articulo) entry.getValue()).getCategoria().getId() == 1 && ((Articulo) entry.getValue()).getEstado().getId() == 1) {
                    dtmarti.addRow(new Object[]{entry.getKey(), ((Articulo) entry.getValue()).getDescripcion().toUpperCase(), ((Articulo) entry.getValue()).getPrecio()});
                }
            }
        }
        TableCellRenderer render = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                l.setHorizontalAlignment(SwingConstants.LEFT);
                return l;
            }
        };
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dtmarti);
        tblArticulos.setRowSorter(sorter);
        tblArticulos.setModel(dtmarti);
        tblArticulos.getColumnModel().getColumn(0).setCellRenderer(render);
        tblArticulos.getRowSorter().toggleSortOrder(0);
        tblArticulos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tblArticulos.rowAtPoint(e.getPoint());
                int columna = tblArticulos.columnAtPoint(e.getPoint());
                if ((fila > -1) && (columna > -1)) {
                    articulo = (Articulo) listaArticulos.get(tblArticulos.getModel().getValueAt(fila, 0));
                    cantidad = (Integer) jspCantidad.getValue();
                }
                if (cat.equals("venta")) {
                    if (articulo.getCantidad() < cantidad) {
                        int resp = JOptionPane.showConfirmDialog(null, "Cantidad ingresada supera la existencia, ¿Continua?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (resp == JOptionPane.YES_OPTION) {
                            esto.dispose();
                        } else {
                            articulo = null;
                            cantidad = 1;
                        }
                    } else {
                        esto.dispose();
                    }
                } else
                    esto.dispose();
            }

        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBuscaArti = new javax.swing.JTextField();
        rbCodigo = new javax.swing.JRadioButton();
        rbDescripcion = new javax.swing.JRadioButton();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblArticulos = new javax.swing.JTable();
        jspCantidad = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        rbCodigo.setText("Codigo");
        rbCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCodigoActionPerformed(evt);
            }
        });

        rbDescripcion.setText("Descripción");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tblArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblArticulos);

        jLabel1.setText("Cantidad");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtBuscaArti, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbCodigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbDescripcion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jspCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscaArti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbCodigo)
                    .addComponent(rbDescripcion)
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jspCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbCodigoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        int columnaABuscar = 0;
        if (rbCodigo.isSelected()) {
            columnaABuscar = 0;
        } else {
            columnaABuscar = 1;
        }

        TableRowSorter trsFiltro = new TableRowSorter(tblArticulos.getModel());
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtBuscaArti.getText().toUpperCase(), columnaABuscar));
        tblArticulos.setRowSorter(trsFiltro);
    }//GEN-LAST:event_btnBuscarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuscarArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuscarArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuscarArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuscarArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BuscarArticulos dialog = new BuscarArticulos(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jspCantidad;
    private javax.swing.JRadioButton rbCodigo;
    private javax.swing.JRadioButton rbDescripcion;
    private javax.swing.JTable tblArticulos;
    private javax.swing.JTextField txtBuscaArti;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the articulo
     */
    public Articulo getArticulo() {
        return articulo;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }
}
