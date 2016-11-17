/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Controladoras.ctrlABMClientes;
import Controladoras.ctrlABMUsuarios;
import Modelos.Cliente;
import Modelos.Estado;
import Modelos.Usuario;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gggia
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    private ConcurrentHashMap listaClientes = null;
    
    private ctrlABMClientes ctrlclientes = new ctrlABMClientes();
    private ctrlABMUsuarios ctrlusuarios = new ctrlABMUsuarios();
    private int idCliente;
    private int filaSeleccionada;
    
    public Principal() {}
    
    public Principal(Usuario user) {
        this.getContentPane().setBackground(Color.WHITE);
        initComponents();
        idCliente = 0;
        if(user.getRol() == 1){
            lblClienteEstado.setHorizontalAlignment(SwingConstants.CENTER);
            Calendar calendar = new GregorianCalendar();
            jdcClientesFechaNac.setCalendar(calendar);
            try { 
                listaClientes = ctrlclientes.TraerTodos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }
            if (listaClientes != null){
                DefaultTableModel dtm = new DefaultTableModel(new Object[] { "id","Codigo", "Nombre", "Direccion" }, 0) {public boolean isCellEditable(int rowIndex,int columnIndex){return false;}};
                for (Iterator it = listaClientes.entrySet().iterator(); it.hasNext();) {
                    ConcurrentHashMap.Entry<?,?> entry = (ConcurrentHashMap.Entry<?,?>) it.next();
                    dtm.addRow(new Object[] {entry.getKey(),((Cliente)entry.getValue()).getCodigo(), ((Cliente)entry.getValue()).getApellido()+", "+((Cliente)entry.getValue()).getNombre(),((Cliente)entry.getValue()).getDireccion()});
                    
                }
                tblClientestodos.setModel(dtm);
                tblClientestodos.getColumn("id").setPreferredWidth(0);
                tblClientestodos.getColumn("id").setMinWidth(0);
                tblClientestodos.getColumn("id").setWidth(0);
                tblClientestodos.getColumn("id").setMaxWidth(0);
                tblClientestodos.addMouseListener(new MouseAdapter(){
                    public void mouseClicked(MouseEvent e) {
                        SimpleDateFormat fechaformat = new SimpleDateFormat("dd/MM/yyyy");
                        int fila = tblClientestodos.rowAtPoint(e.getPoint());
                        int columna = tblClientestodos.columnAtPoint(e.getPoint());
                        if ((fila > -1) && (columna > -1)){
                            filaSeleccionada = fila;
                            Cliente nombre = (Cliente)listaClientes.get(tblClientestodos.getModel().getValueAt(fila, 0));
                            lblClientesCodigo.setText(nombre.getCodigo());
                            if(nombre.getEstado().getDescripcion().equals("activo")){
                                lblClienteEstado.setForeground(Color.green);
                                lblClienteEstado.setText(nombre.getEstado().getDescripcion());
                            } else {
                                lblClienteEstado.setForeground(Color.red);
                                lblClienteEstado.setText(nombre.getEstado().getDescripcion());
                            }
                            idCliente = nombre.getIdCliente();
                            lblClientesNombre.setText(nombre.getApellido()+", "+nombre.getNombre());
                            lblClientesDireccion.setText(nombre.getDireccion());
                            lblClientesDNI.setText(String.valueOf(nombre.getDni()));
                            lblClientesTelefono.setText(nombre.getTelefono());
                            lblClientesFechaNac.setText(fechaformat.format(nombre.getFechanac()));
                            lblClientesMail.setText(nombre.getMail());
                            lblClientesNotas.setText(nombre.getNotas());
                        }
                    }
                });
            }
        } else {
            TabContent.setEnabledAt(TabContent.indexOfTab("USUARIOS"), false);
            TabContent.setEnabledAt(TabContent.indexOfTab("PROVEEDORES"), false);
            TabContent.setEnabledAt(TabContent.indexOfTab("CLIENTES"), false);
        }
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        TabContent = new javax.swing.JTabbedPane();
        tabVenta = new javax.swing.JPanel();
        tabAlquiler = new javax.swing.JPanel();
        tabClientes = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtClientesApellido = new javax.swing.JTextField();
        txtClientesDireccion = new javax.swing.JTextField();
        txtClientesTelefono = new javax.swing.JTextField();
        txtClientesEmail = new javax.swing.JTextField();
        btnClientesAceptar = new java.awt.Button();
        jLabel17 = new javax.swing.JLabel();
        txtClientesNombre = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtClientesDni = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtClientesNotas = new javax.swing.JTextArea();
        jdcClientesFechaNac = new com.toedter.calendar.JDateChooser();
        jLabel22 = new javax.swing.JLabel();
        txtClientesCodigo = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblClientestodos = new javax.swing.JTable();
        lblClientesNombre = new javax.swing.JLabel();
        lblClientesDireccion = new javax.swing.JLabel();
        lblClientesDNI = new javax.swing.JLabel();
        lblClientesTelefono = new javax.swing.JLabel();
        lblClientesFechaNac = new javax.swing.JLabel();
        lblClientesMail = new javax.swing.JLabel();
        lblClientesNotas = new javax.swing.JLabel();
        lblClienteEstado = new javax.swing.JLabel();
        lblClientesCodigo = new javax.swing.JLabel();
        btnClintesModificar = new javax.swing.JButton();
        btnClientesElminar = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        lblClientesId = new javax.swing.JLabel();
        tabCaja = new javax.swing.JPanel();
        tabUsuarios = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtTituloAltaUsuario = new java.awt.Label();
        labelNombre = new java.awt.Label();
        labelContraseña = new java.awt.Label();
        labelContraseñaR = new java.awt.Label();
        btnUsuariosAceptar = new java.awt.Button();
        chkUsuariosAdmin = new javax.swing.JCheckBox();
        txtUsuariosNombre = new javax.swing.JTextField();
        txtUsuariosContraseña = new javax.swing.JPasswordField();
        txtUsuariosRepContraseña = new javax.swing.JPasswordField();
        tabArticulos = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtArticulosCodigo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        SpinArticulosPrecio = new javax.swing.JSpinner();
        spinArticulosCosto = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        btnArticulosAceptar = new java.awt.Button();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        tabProveedores = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtProveedoresNombre = new javax.swing.JTextField();
        txtProveedoresDireccion = new javax.swing.JTextField();
        txtProveedoresDireccion1 = new javax.swing.JTextField();
        txtProveedoresDireccion2 = new javax.swing.JTextField();
        btnProveedoresAceptar = new java.awt.Button();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtProveedoresNotas = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblProveedoresTodos = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menuAlquileres = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(9, 166, 216));
        setMinimumSize(new java.awt.Dimension(1024, 700));
        setName("JPrincipal"); // NOI18N
        setResizable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/SalesForce.png"))); // NOI18N

        javax.swing.GroupLayout tabVentaLayout = new javax.swing.GroupLayout(tabVenta);
        tabVenta.setLayout(tabVentaLayout);
        tabVentaLayout.setHorizontalGroup(
            tabVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 999, Short.MAX_VALUE)
        );
        tabVentaLayout.setVerticalGroup(
            tabVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 612, Short.MAX_VALUE)
        );

        TabContent.addTab("VENTA", tabVenta);

        javax.swing.GroupLayout tabAlquilerLayout = new javax.swing.GroupLayout(tabAlquiler);
        tabAlquiler.setLayout(tabAlquilerLayout);
        tabAlquilerLayout.setHorizontalGroup(
            tabAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 999, Short.MAX_VALUE)
        );
        tabAlquilerLayout.setVerticalGroup(
            tabAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 612, Short.MAX_VALUE)
        );

        TabContent.addTab("ALQUILER", tabAlquiler);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setPreferredSize(new java.awt.Dimension(365, 574));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setText("Alta Clientes");

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel13.setText("Nombre:");

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel14.setText("Direccion:");

        jLabel15.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel15.setText("Telefono:");

        jLabel16.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel16.setText("E-Mail:");

        btnClientesAceptar.setLabel("Aceptar");
        btnClientesAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesAceptarActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel17.setText("Apellido:");

        jLabel18.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel18.setText("D.N.I:");

        jLabel19.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel19.setText("Fecha de Nacimiento: ");

        jLabel20.setText("Notas:");

        txtClientesNotas.setColumns(20);
        txtClientesNotas.setRows(5);
        jScrollPane2.setViewportView(txtClientesNotas);

        jdcClientesFechaNac.setDateFormatString("dd/MM/yyyy");

        jLabel22.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel22.setText("Codigo");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel12))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addComponent(btnClientesAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2))
                            .addComponent(jLabel13)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel22)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel18)
                                            .addComponent(jLabel19))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtClientesNombre)
                                            .addComponent(txtClientesApellido)
                                            .addComponent(txtClientesDni)
                                            .addComponent(jdcClientesFechaNac, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                                            .addComponent(txtClientesCodigo, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(75, 75, 75)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtClientesEmail)
                                            .addComponent(txtClientesDireccion)
                                            .addComponent(txtClientesTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(50, 50, 50)))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(3, 3, 3)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtClientesCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtClientesNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtClientesApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtClientesDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdcClientesFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtClientesDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtClientesTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtClientesEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                .addComponent(btnClientesAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tblClientestodos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblClientestodos);

        lblClienteEstado.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        btnClintesModificar.setText("Modificar");
        btnClintesModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClintesModificarActionPerformed(evt);
            }
        });

        btnClientesElminar.setText("Eliminar");
        btnClientesElminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesElminarActionPerformed(evt);
            }
        });

        lblClientesId.setText("id");

        javax.swing.GroupLayout tabClientesLayout = new javax.swing.GroupLayout(tabClientes);
        tabClientes.setLayout(tabClientesLayout);
        tabClientesLayout.setHorizontalGroup(
            tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
                    .addGroup(tabClientesLayout.createSequentialGroup()
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addComponent(btnClintesModificar)
                                .addGap(374, 374, 374)
                                .addComponent(btnClientesElminar))
                            .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(tabClientesLayout.createSequentialGroup()
                                    .addComponent(lblClientesDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(lblClientesFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblClientesMail, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(lblClientesNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(tabClientesLayout.createSequentialGroup()
                                    .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblClientesNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblClientesCodigo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(tabClientesLayout.createSequentialGroup()
                                            .addComponent(lblClientesDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(lblClientesTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(lblClienteEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblClientesId, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tabClientesLayout.setVerticalGroup(
            tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                    .addGroup(tabClientesLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblClienteEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(tabClientesLayout.createSequentialGroup()
                                        .addGap(0, 11, Short.MAX_VALUE)
                                        .addComponent(lblClientesCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(tabClientesLayout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblClientesNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblClientesDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblClientesTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblClientesDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblClientesFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblClientesMail, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblClientesNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnClintesModificar)
                                    .addComponent(btnClientesElminar))
                                .addGap(19, 19, 19))
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addComponent(lblClientesId, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        TabContent.addTab("CLIENTES", tabClientes);

        javax.swing.GroupLayout tabCajaLayout = new javax.swing.GroupLayout(tabCaja);
        tabCaja.setLayout(tabCajaLayout);
        tabCajaLayout.setHorizontalGroup(
            tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 999, Short.MAX_VALUE)
        );
        tabCajaLayout.setVerticalGroup(
            tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 612, Short.MAX_VALUE)
        );

        TabContent.addTab("CAJA", tabCaja);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(365, 574));

        txtTituloAltaUsuario.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtTituloAltaUsuario.setText("Alta Usuarios:");

        labelNombre.setText("Nombre:");

        labelContraseña.setText("Contraseña:");

        labelContraseñaR.setText("Repetir Contraseña:");

        btnUsuariosAceptar.setLabel("Aceptar");
        btnUsuariosAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosAceptarActionPerformed(evt);
            }
        });

        chkUsuariosAdmin.setText("Administrador.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkUsuariosAdmin)
                            .addComponent(txtTituloAltaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelContraseñaR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtUsuariosContraseña)
                                    .addComponent(txtUsuariosRepContraseña)
                                    .addComponent(txtUsuariosNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(btnUsuariosAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(181, 181, 181))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTituloAltaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtUsuariosNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtUsuariosContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtUsuariosRepContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelContraseñaR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(chkUsuariosAdmin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 367, Short.MAX_VALUE)
                .addComponent(btnUsuariosAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout tabUsuariosLayout = new javax.swing.GroupLayout(tabUsuarios);
        tabUsuarios.setLayout(tabUsuariosLayout);
        tabUsuariosLayout.setHorizontalGroup(
            tabUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(624, Short.MAX_VALUE))
        );
        tabUsuariosLayout.setVerticalGroup(
            tabUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabContent.addTab("USUARIOS", tabUsuarios);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(365, 574));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Alta Articulos");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel3.setText("Codigo:");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel4.setText("Descripción:");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel5.setText("Costo:");

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel6.setText("Precio:");

        SpinArticulosPrecio.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));
        SpinArticulosPrecio.setEditor(new javax.swing.JSpinner.NumberEditor(SpinArticulosPrecio, "0.00"));

        spinArticulosCosto.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));
        spinArticulosCosto.setEditor(new javax.swing.JSpinner.NumberEditor(spinArticulosCosto, "0.00"));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        btnArticulosAceptar.setLabel("Aceptar");
        btnArticulosAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArticulosAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtArticulosCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinArticulosCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SpinArticulosPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(btnArticulosAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtArticulosCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(spinArticulosCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(SpinArticulosPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 275, Short.MAX_VALUE)
                .addComponent(btnArticulosAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(jTable1);

        javax.swing.GroupLayout tabArticulosLayout = new javax.swing.GroupLayout(tabArticulos);
        tabArticulos.setLayout(tabArticulosLayout);
        tabArticulosLayout.setHorizontalGroup(
            tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabArticulosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabArticulosLayout.setVerticalGroup(
            tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabArticulosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                    .addComponent(jScrollPane6))
                .addContainerGap())
        );

        TabContent.addTab("ARTICULOS", tabArticulos);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setPreferredSize(new java.awt.Dimension(365, 574));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setText("Alta Proveedores");

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel8.setText("Nombre:");

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel9.setText("Direccion:");

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel10.setText("Telefono:");

        jLabel11.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel11.setText("E-Mail:");

        btnProveedoresAceptar.setLabel("Aceptar");
        btnProveedoresAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedoresAceptarActionPerformed(evt);
            }
        });

        jLabel21.setText("Notas:");

        txtProveedoresNotas.setColumns(20);
        txtProveedoresNotas.setRows(5);
        jScrollPane3.setViewportView(txtProveedoresNotas);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                        .addGap(300, 300, 300))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtProveedoresDireccion2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProveedoresDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProveedoresNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProveedoresDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(btnProveedoresAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(41, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtProveedoresNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtProveedoresDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtProveedoresDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtProveedoresDireccion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 311, Short.MAX_VALUE)
                .addComponent(btnProveedoresAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tblProveedoresTodos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblProveedoresTodos);

        javax.swing.GroupLayout tabProveedoresLayout = new javax.swing.GroupLayout(tabProveedores);
        tabProveedores.setLayout(tabProveedoresLayout);
        tabProveedoresLayout.setHorizontalGroup(
            tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabProveedoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE))
        );
        tabProveedoresLayout.setVerticalGroup(
            tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabProveedoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                    .addComponent(jScrollPane5))
                .addContainerGap())
        );

        TabContent.addTab("PROVEEDORES", tabProveedores);

        jMenu2.setText("Archivo");

        jMenuItem1.setText("Cerrar Sesión.");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                none(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Reportes");

        menuAlquileres.setText("Ventas");
        jMenu3.add(menuAlquileres);

        jMenu4.setText("Alquileres");
        jMenu3.add(jMenu4);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TabContent, javax.swing.GroupLayout.PREFERRED_SIZE, 1004, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TabContent, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void none(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_none
        dispose();
        Login login = new Login();
        login.show();
    }//GEN-LAST:event_none

    private void btnProveedoresAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedoresAceptarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnProveedoresAceptarActionPerformed

    private void btnArticulosAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArticulosAceptarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnArticulosAceptarActionPerformed

    private void btnUsuariosAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosAceptarActionPerformed
        int rol = 0;
        if(!txtUsuariosNombre.getText().equals("") && !txtUsuariosContraseña.getText().equals("")
            && !txtUsuariosRepContraseña.getText().equals("")){
            if(!txtUsuariosContraseña.getText().equals(txtUsuariosRepContraseña.getText())){
                JOptionPane.showMessageDialog(null, "Verifique las Contraseñas", "Security", JOptionPane.ERROR_MESSAGE);
            } else {
                if (chkUsuariosAdmin.isSelected())
                    rol = 1;
                else
                    rol = 2;
                try {
                    ctrlusuarios.agregarUsario(txtUsuariosNombre.getText(), txtUsuariosContraseña.getText(), rol);
                    txtUsuariosNombre.setText("");
                    txtUsuariosContraseña.setText("");
                    txtUsuariosRepContraseña.setText("");
                    chkUsuariosAdmin.setSelected(false);
                    JOptionPane.showMessageDialog(null, "El usuario se genero correctamente.", "Succes", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        else{
            JOptionPane.showMessageDialog(null, "Complete los Campos.", "Security", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUsuariosAceptarActionPerformed

    private void btnClientesAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesAceptarActionPerformed
        int key = 0;
        int dni = 0;
        java.util.Date utilDate = (java.util.Date)jdcClientesFechaNac.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        if(!txtClientesDni.getText().equals("")){
            dni = Integer.valueOf(txtClientesDni.getText());
        }
        if(txtClientesCodigo.getText().equals("") || txtClientesNombre.getText().equals("")){
            JOptionPane.showMessageDialog(null, "codigo o nombre de usuario vacio, complete los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                if(ctrlclientes.BuscaCodigo(txtClientesCodigo.getText()))
                    JOptionPane.showMessageDialog(null, "codigo Ingesado ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                else {
                    try {
                        key = ctrlclientes.AgregarCliente(1, txtClientesNotas.getText(), txtClientesNombre.getText(), txtClientesApellido.getText(), txtClientesDireccion.getText(), txtClientesEmail.getText(), txtClientesTelefono.getText(), dni, txtClientesCodigo.getText(),sqlDate,txtClientesCodigo.getText(),1);
                        DefaultTableModel dtm = (DefaultTableModel)tblClientestodos.getModel();
                        dtm.addRow(new Object[] {key, txtClientesCodigo.getText(), txtClientesNombre.getText()+", "+txtClientesApellido.getText(),txtClientesDireccion.getText()});
                        txtClientesCodigo.setText("");
                        txtClientesNombre.setText("");
                        txtClientesApellido.setText("");
                        txtClientesDni.setText("");
                        txtClientesDireccion.setText("");
                        txtClientesTelefono.setText("");
                        txtClientesEmail.setText("");
                        txtClientesNotas.setText("");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }
            try {
                listaClientes.put(key, new Cliente(key,ctrlclientes.traerDescuento(1),txtClientesNotas.getText(), txtClientesNombre.getText(), txtClientesApellido.getText(), txtClientesDireccion.getText(), txtClientesEmail.getText(), txtClientesTelefono.getText(), dni,sqlDate,txtClientesCodigo.getText(), new Estado(1)));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_btnClientesAceptarActionPerformed

    private void btnClintesModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClintesModificarActionPerformed
        if(idCliente != 0){
            ModificaCliente modcli = new ModificaCliente(this, true,idCliente);
            modcli.setLocationRelativeTo(this);
            modcli.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    Cliente modificado;
                    DefaultTableModel dtm = (DefaultTableModel)tblClientestodos.getModel();
                    modificado = modcli.getModificado();
                    dtm.removeRow(filaSeleccionada);
                    dtm.addRow(new Object[] {modificado.getIdCliente(), modificado.getCodigo(), modificado.getNombre()+", "+modificado.getApellido(),modificado.getDireccion()});
                }
            });
            modcli.show();
        }else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnClintesModificarActionPerformed

    private void btnClientesElminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesElminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClientesElminarActionPerformed

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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner SpinArticulosPrecio;
    private javax.swing.JTabbedPane TabContent;
    private java.awt.Button btnArticulosAceptar;
    private java.awt.Button btnClientesAceptar;
    private javax.swing.JButton btnClientesElminar;
    private javax.swing.JButton btnClintesModificar;
    private java.awt.Button btnProveedoresAceptar;
    private java.awt.Button btnUsuariosAceptar;
    private javax.swing.JCheckBox chkUsuariosAdmin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private com.toedter.calendar.JDateChooser jdcClientesFechaNac;
    private java.awt.Label labelContraseña;
    private java.awt.Label labelContraseñaR;
    private java.awt.Label labelNombre;
    private javax.swing.JLabel lblClienteEstado;
    private javax.swing.JLabel lblClientesCodigo;
    private javax.swing.JLabel lblClientesDNI;
    private javax.swing.JLabel lblClientesDireccion;
    private javax.swing.JLabel lblClientesFechaNac;
    private javax.swing.JLabel lblClientesId;
    private javax.swing.JLabel lblClientesMail;
    private javax.swing.JLabel lblClientesNombre;
    private javax.swing.JLabel lblClientesNotas;
    private javax.swing.JLabel lblClientesTelefono;
    private javax.swing.JMenu menuAlquileres;
    private javax.swing.JSpinner spinArticulosCosto;
    private javax.swing.JPanel tabAlquiler;
    private javax.swing.JPanel tabArticulos;
    private javax.swing.JPanel tabCaja;
    private javax.swing.JPanel tabClientes;
    private javax.swing.JPanel tabProveedores;
    private javax.swing.JPanel tabUsuarios;
    private javax.swing.JPanel tabVenta;
    private javax.swing.JTable tblClientestodos;
    private javax.swing.JTable tblProveedoresTodos;
    private javax.swing.JTextField txtArticulosCodigo;
    private javax.swing.JTextField txtClientesApellido;
    private javax.swing.JTextField txtClientesCodigo;
    private javax.swing.JTextField txtClientesDireccion;
    private javax.swing.JTextField txtClientesDni;
    private javax.swing.JTextField txtClientesEmail;
    private javax.swing.JTextField txtClientesNombre;
    private javax.swing.JTextArea txtClientesNotas;
    private javax.swing.JTextField txtClientesTelefono;
    private javax.swing.JTextField txtProveedoresDireccion;
    private javax.swing.JTextField txtProveedoresDireccion1;
    private javax.swing.JTextField txtProveedoresDireccion2;
    private javax.swing.JTextField txtProveedoresNombre;
    private javax.swing.JTextArea txtProveedoresNotas;
    private java.awt.Label txtTituloAltaUsuario;
    private javax.swing.JPasswordField txtUsuariosContraseña;
    private javax.swing.JTextField txtUsuariosNombre;
    private javax.swing.JPasswordField txtUsuariosRepContraseña;
    // End of variables declaration//GEN-END:variables
}
