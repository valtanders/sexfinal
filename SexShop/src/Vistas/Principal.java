/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Controladoras.ctrlABMArticulos;
import Controladoras.ctrlABMCategorias;
import Controladoras.ctrlABMClientes;
import Controladoras.ctrlABMProveedores;
import Controladoras.ctrlABMUsuarios;
import Modelos.Articulo;
import Modelos.Categoria;
import Modelos.Cliente;
import Modelos.DescuentoCli;
import Modelos.Estado;
import Modelos.Proveedor;
import Modelos.Usuario;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author gggia
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    private ConcurrentHashMap listaClientes;
    private ConcurrentHashMap listaArticulos;
    private ConcurrentHashMap listaProveedores;
    private ConcurrentHashMap listaCategorias;
    private ConcurrentHashMap listaUsuarios;
    private ctrlABMClientes ctrlclientes = new ctrlABMClientes();
    private ctrlABMUsuarios ctrlusuarios = new ctrlABMUsuarios();
    private ctrlABMArticulos ctrlarticulos = new ctrlABMArticulos();
    private ctrlABMProveedores ctrlproveedores = new ctrlABMProveedores();
    private ctrlABMCategorias ctrlcategorias = new ctrlABMCategorias();
    
    private int idCliente;
    private int idArticulo;
    private int filaSeleccionadaCliente;
    private int filaSeleccionadaArticulo;
    private TableRowSorter trsFiltro;
    
    public Principal() {}
    
    public Principal(Usuario user) {
        this.getContentPane().setBackground(Color.WHITE);
        initComponents();
        cbxArticulosProveedores.removeAllItems();
        cbxArticulosCategorias.removeAllItems();
        btnClientesFiltro.add(rdbClientesCodigo);
        btnClientesFiltro.add(rdbClientesApellido);
        rdbClientesCodigo.setSelected(true);
        btnArticulosFiltro.add(rdbAriculosCodigo);
        btnArticulosFiltro.add(rdbArticulosDescripcion);
        rdbAriculosCodigo.setSelected(true);
        lblClienteEstado.setHorizontalAlignment(SwingConstants.CENTER);
        lblArticulosActivo.setHorizontalAlignment(SwingConstants.CENTER);
        Calendar calendar = new GregorianCalendar();
        jdcClientesFechaNac.setCalendar(calendar);
        idCliente = 0;
        idArticulo = 0;
        try {
            listaProveedores = ctrlproveedores.traerTodos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
        }
        if (listaProveedores != null) {
            for (Iterator it = listaProveedores.entrySet().iterator(); it.hasNext();) {
                ConcurrentHashMap.Entry<?, ?> entry = (ConcurrentHashMap.Entry<?, ?>) it.next();
                cbxArticulosProveedores.addItem(((Proveedor)entry.getValue()));
            }
        }
        try {
            listaCategorias = ctrlcategorias.traerTodos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
        }
        if (listaCategorias != null) {
            for (Iterator it = listaCategorias.entrySet().iterator(); it.hasNext();) {
                ConcurrentHashMap.Entry<?, ?> entry = (ConcurrentHashMap.Entry<?, ?>) it.next();
                cbxArticulosCategorias.addItem(((Categoria)entry.getValue()));
            }
        }
        try {
            listaArticulos = ctrlarticulos.traerTodos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
        }
        if (listaArticulos != null){
            DefaultTableModel dtmarti = new DefaultTableModel(new Object [] {"Codigo","Descripcion","Precio"}, 0) {public boolean isCellEditable(int rowIndex,int columnIndex){return false;}};
            for (Iterator it = listaArticulos.entrySet().iterator(); it.hasNext();) {
                ConcurrentHashMap.Entry<?, ?> entry = (ConcurrentHashMap.Entry<?, ?>) it.next();
                dtmarti.addRow(new Object [] {entry.getKey(), ((Articulo) entry.getValue()).getDescripcion().toUpperCase(), ((Articulo) entry.getValue()).getPrecio()});
            }
            tblArticulosTodos.setModel(dtmarti);
            tblArticulosTodos.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
            
                    SimpleDateFormat fechaformat = new SimpleDateFormat("dd/MM/yyyy");
                    int fila = tblArticulosTodos.rowAtPoint(e.getPoint());
                    int columna = tblArticulosTodos.columnAtPoint(e.getPoint());
                    if ((fila > -1) && (columna > -1)){
                        filaSeleccionadaArticulo = fila;
                        Articulo arti = (Articulo)listaArticulos.get(tblArticulosTodos.getModel().getValueAt(fila, 0));
                        lblArticulosCodigo.setText(String.valueOf(arti.getId()));
                        if(arti.getEstado().getDescripcion().equals("activo")){
                            lblArticulosActivo.setForeground(Color.green);
                            lblArticulosActivo.setText(arti.getEstado().getDescripcion());
                        } else {
                            lblArticulosActivo.setForeground(Color.red);
                            lblArticulosActivo.setText(arti.getEstado().getDescripcion());
                        }
                        idArticulo = arti.getId();
                        lblArticulosDescripcion.setText(arti.getDescripcion());
                        lblArticulosPrecio.setText(String.valueOf(arti.getPrecio()));
                        lblArticulosCosto.setText(String.valueOf(arti.getCosto()));
                        lblArticulosFechaCompra.setText(fechaformat.format(arti.getFechaCompra()));
                        lblArticulosProveedor.setText(arti.getProveedor().getRazonsocial());
                    }
                }
            });
        }
        if(user.getRol() == 1){
            try { 
                listaClientes = ctrlclientes.TraerTodos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }
            if (listaClientes != null){
                DefaultTableModel dtm = new DefaultTableModel(new Object[] { "Codigo", "Apellido","Nombre", "Direccion" }, 0) {public boolean isCellEditable(int rowIndex,int columnIndex){return false;}};
                for (Iterator it = listaClientes.entrySet().iterator(); it.hasNext();) {
                    ConcurrentHashMap.Entry<?,?> entry = (ConcurrentHashMap.Entry<?,?>) it.next();
                    dtm.addRow(new Object[] {entry.getKey(), ((Cliente)entry.getValue()).getApellido().toUpperCase(), ((Cliente)entry.getValue()).getNombre(),((Cliente)entry.getValue()).getDireccion()});
                    
                }
                tblClientestodos.setModel(dtm);
                //tblClientestodos.getColumn("id").setPreferredWidth(0);
                //tblClientestodos.getColumn("id").setMinWidth(0);
                //tblClientestodos.getColumn("id").setWidth(0);
                //tblClientestodos.getColumn("id").setMaxWidth(0);
                tblClientestodos.addMouseListener(new MouseAdapter(){
                    public void mouseClicked(MouseEvent e) {
                        SimpleDateFormat fechaformat = new SimpleDateFormat("dd/MM/yyyy");
                        int fila = tblClientestodos.rowAtPoint(e.getPoint());
                        int columna = tblClientestodos.columnAtPoint(e.getPoint());
                        if ((fila > -1) && (columna > -1)){
                            filaSeleccionadaCliente = fila;
                            Cliente nombre = (Cliente)listaClientes.get(tblClientestodos.getModel().getValueAt(fila, 0));
                            lblClientesCodigo.setText(String.valueOf(nombre.getIdCliente()));
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
            try {
                listaUsuarios = ctrlusuarios.traerTodo();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }
            if (listaUsuarios != null) {
                DefaultTableModel dtmuser = new DefaultTableModel(new Object[] { "Codigo", "Nombre","Rol"}, 0) {public boolean isCellEditable(int rowIndex,int columnIndex){return false;}};
                String Rol;
                for (Iterator it = listaUsuarios.entrySet().iterator(); it.hasNext();) {
                    ConcurrentHashMap.Entry<?,?> entry = (ConcurrentHashMap.Entry<?,?>) it.next();
                    if (((Usuario)entry.getValue()).getRol() == 1)
                        Rol = "Administrador";
                    else
                        Rol = "Operador";
                    dtmuser.addRow(new Object[] {entry.getKey(), ((Usuario)entry.getValue()).getNombre(), Rol});
                    
                }
                tblUsuariosTodos.setModel(dtmuser);
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

        btnClientesFiltro = new javax.swing.ButtonGroup();
        btnArticulosFiltro = new javax.swing.ButtonGroup();
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
        jLabel24 = new javax.swing.JLabel();
        txtClientesBuscar = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        rdbClientesCodigo = new javax.swing.JRadioButton();
        rdbClientesApellido = new javax.swing.JRadioButton();
        btnClientesBuscar = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
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
        jScrollPane7 = new javax.swing.JScrollPane();
        tblUsuariosTodos = new javax.swing.JTable();
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
        txtArticulosDescripcion = new javax.swing.JTextArea();
        btnArticulosAceptar = new java.awt.Button();
        jLabel25 = new javax.swing.JLabel();
        cbxArticulosProveedores = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        cbxArticulosCategorias = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblArticulosTodos = new javax.swing.JTable();
        lblArticulosDescripcion = new javax.swing.JLabel();
        lblArticulosCodigo = new javax.swing.JLabel();
        lblArticulosPrecio = new javax.swing.JLabel();
        lblArticulosCosto = new javax.swing.JLabel();
        lblArticulosFechaCompra = new javax.swing.JLabel();
        lblArticulosProveedor = new javax.swing.JLabel();
        btnArticulosModificar = new javax.swing.JButton();
        btnArticulosElminar = new javax.swing.JButton();
        btnArticulosBuscar = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        txtArticulosBuscar = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        rdbAriculosCodigo = new javax.swing.JRadioButton();
        rdbArticulosDescripcion = new javax.swing.JRadioButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        lblArticulosActivo = new javax.swing.JLabel();
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
        jPanel8 = new javax.swing.JPanel();
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
                                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jLabel24.setText("Buscar:");

        rdbClientesCodigo.setText("Por Codigo");

        rdbClientesApellido.setText("Por Apellido");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdbClientesCodigo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbClientesApellido)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbClientesCodigo)
                    .addComponent(rdbClientesApellido))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnClientesBuscar.setText("Buscar");
        btnClientesBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesBuscarActionPerformed(evt);
            }
        });

        jLabel34.setText("Codigo: ");

        jLabel35.setText("Estado: ");

        jLabel36.setText("Nombre: ");

        jLabel37.setText("Diección:");

        jLabel38.setText("Telefono: ");

        jLabel39.setText("DNI:");

        jLabel40.setText("Fecha de nacimiento: ");

        jLabel41.setText("Mail:");

        jLabel42.setText("Notas: ");

        javax.swing.GroupLayout tabClientesLayout = new javax.swing.GroupLayout(tabClientes);
        tabClientes.setLayout(tabClientesLayout);
        tabClientesLayout.setHorizontalGroup(
            tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(tabClientesLayout.createSequentialGroup()
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(tabClientesLayout.createSequentialGroup()
                                        .addComponent(btnClintesModificar)
                                        .addGap(374, 374, 374)
                                        .addComponent(btnClientesElminar))
                                    .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(tabClientesLayout.createSequentialGroup()
                                            .addComponent(jLabel42)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lblClientesNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(tabClientesLayout.createSequentialGroup()
                                            .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(tabClientesLayout.createSequentialGroup()
                                                    .addComponent(jLabel40)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(lblClientesFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(jLabel38)
                                                .addGroup(tabClientesLayout.createSequentialGroup()
                                                    .addComponent(jLabel34)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(lblClientesCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(tabClientesLayout.createSequentialGroup()
                                                    .addComponent(jLabel36)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblClientesTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblClientesNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel39)
                                                .addGroup(tabClientesLayout.createSequentialGroup()
                                                    .addComponent(jLabel35)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(lblClienteEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(tabClientesLayout.createSequentialGroup()
                                                    .addComponent(jLabel37)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblClientesDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblClientesDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(tabClientesLayout.createSequentialGroup()
                                                    .addComponent(jLabel41)
                                                    .addGap(31, 31, 31)
                                                    .addComponent(lblClientesMail, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblClientesId, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtClientesBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnClientesBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tabClientesLayout.setVerticalGroup(
            tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                    .addGroup(tabClientesLayout.createSequentialGroup()
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnClientesBuscar, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtClientesBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(tabClientesLayout.createSequentialGroup()
                                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(tabClientesLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel34)
                                                    .addComponent(lblClientesCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(jLabel23)
                                            .addComponent(lblClientesId, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel36)
                                            .addComponent(lblClientesNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel38)
                                            .addComponent(lblClientesTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(tabClientesLayout.createSequentialGroup()
                                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(tabClientesLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel35))
                                            .addGroup(tabClientesLayout.createSequentialGroup()
                                                .addComponent(lblClienteEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(3, 3, 3)))
                                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblClientesDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel37))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel39)
                                            .addComponent(lblClientesDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel40)
                                        .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(lblClientesMail, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblClientesFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblClientesNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnClintesModificar)
                            .addComponent(btnClientesElminar))
                        .addGap(19, 19, 19)))
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

        tblUsuariosTodos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(tblUsuariosTodos);

        javax.swing.GroupLayout tabUsuariosLayout = new javax.swing.GroupLayout(tabUsuarios);
        tabUsuarios.setLayout(tabUsuariosLayout);
        tabUsuariosLayout.setHorizontalGroup(
            tabUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabUsuariosLayout.setVerticalGroup(
            tabUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                    .addGroup(tabUsuariosLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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

        SpinArticulosPrecio.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 1.0f));
        SpinArticulosPrecio.setEditor(new javax.swing.JSpinner.NumberEditor(SpinArticulosPrecio, "0.00"));

        spinArticulosCosto.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 1.0f));
        spinArticulosCosto.setEditor(new javax.swing.JSpinner.NumberEditor(spinArticulosCosto, "0.00"));

        txtArticulosDescripcion.setColumns(20);
        txtArticulosDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtArticulosDescripcion);

        btnArticulosAceptar.setLabel("Aceptar");
        btnArticulosAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArticulosAceptarActionPerformed(evt);
            }
        });

        jLabel25.setText("Proveedor:");

        cbxArticulosProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxArticulosProveedoresActionPerformed(evt);
            }
        });

        jLabel26.setText("Categoria:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(btnArticulosAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cbxArticulosProveedores, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbxArticulosCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtArticulosCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinArticulosCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SpinArticulosPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(25, Short.MAX_VALUE))
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
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(cbxArticulosProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(cbxArticulosCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 282, Short.MAX_VALUE)
                .addComponent(btnArticulosAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tblArticulosTodos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(tblArticulosTodos);

        btnArticulosModificar.setText("Modificar");
        btnArticulosModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArticulosModificarActionPerformed(evt);
            }
        });

        btnArticulosElminar.setText("Eliminar");
        btnArticulosElminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArticulosElminarActionPerformed(evt);
            }
        });

        btnArticulosBuscar.setText("Buscar");
        btnArticulosBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArticulosBuscarActionPerformed(evt);
            }
        });

        jLabel27.setText("Buscar:");

        rdbAriculosCodigo.setText("Por Codigo");

        rdbArticulosDescripcion.setText("Por Apellido");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdbAriculosCodigo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbArticulosDescripcion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbAriculosCodigo)
                    .addComponent(rdbArticulosDescripcion))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel28.setText("Codigo:");

        jLabel29.setText("Descripción:");

        jLabel30.setText("Precio:");

        jLabel31.setText("Costo:");

        jLabel32.setText("Fecha de compra:");

        jLabel33.setText("Provvedor:");

        lblArticulosActivo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        javax.swing.GroupLayout tabArticulosLayout = new javax.swing.GroupLayout(tabArticulos);
        tabArticulos.setLayout(tabArticulosLayout);
        tabArticulosLayout.setHorizontalGroup(
            tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabArticulosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabArticulosLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnArticulosModificar)
                        .addGap(337, 337, 337)
                        .addComponent(btnArticulosElminar)
                        .addContainerGap())
                    .addGroup(tabArticulosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabArticulosLayout.createSequentialGroup()
                                .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(tabArticulosLayout.createSequentialGroup()
                                        .addComponent(jLabel28)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblArticulosCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(43, 43, 43))
                                    .addGroup(tabArticulosLayout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblArticulosDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel30)))
                                .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(tabArticulosLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblArticulosPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel31)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblArticulosCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(350, 350, 350))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabArticulosLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblArticulosActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(408, 408, 408))))
                            .addGroup(tabArticulosLayout.createSequentialGroup()
                                .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(tabArticulosLayout.createSequentialGroup()
                                        .addComponent(jLabel32)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblArticulosFechaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel33)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblArticulosProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(tabArticulosLayout.createSequentialGroup()
                                        .addComponent(jLabel27)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtArticulosBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnArticulosBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        tabArticulosLayout.setVerticalGroup(
            tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabArticulosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabArticulosLayout.createSequentialGroup()
                        .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnArticulosBuscar, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtArticulosBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                        .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabArticulosLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblArticulosActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblArticulosPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblArticulosCosto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(34, 34, 34))
                            .addGroup(tabArticulosLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblArticulosCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel29)
                                    .addComponent(lblArticulosDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblArticulosFechaCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblArticulosProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(44, 44, 44)
                        .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnArticulosModificar)
                            .addComponent(btnArticulosElminar)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE))
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
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                    .addComponent(jScrollPane5))
                .addContainerGap())
        );

        TabContent.addTab("PROVEEDORES", tabProveedores);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1002, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 614, Short.MAX_VALUE)
        );

        TabContent.addTab("PELICULAS", jPanel8);

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        int key = 0;
        if(txtArticulosCodigo.getText().equals("") || txtArticulosDescripcion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "codigo o descripcion de articulo vacio, complete los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                if(ctrlarticulos.buscaCodigo(txtArticulosCodigo.getText())){
                    JOptionPane.showMessageDialog(null, "codigo Ingesado ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                } else{
                    java.util.Date fechaact = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(fechaact.getTime());
                    key = ctrlarticulos.agregarArticulo(txtArticulosDescripcion.getText(),(float)spinArticulosCosto.getValue(),(float)SpinArticulosPrecio.getValue(),((Proveedor)cbxArticulosProveedores.getSelectedItem()).getId(),((Categoria)cbxArticulosCategorias.getSelectedItem()).getId(),txtArticulosCodigo.getText());
                    listaArticulos.put(key, new Articulo(key,txtArticulosDescripcion.getText(),(float)spinArticulosCosto.getValue(),(float)SpinArticulosPrecio.getValue(),sqlDate,new Proveedor(((Proveedor)cbxArticulosProveedores.getSelectedItem()).getId(),((Proveedor)cbxArticulosProveedores.getSelectedItem()).getRazonsocial(),((Proveedor)cbxArticulosProveedores.getSelectedItem()).getDireccion(),((Proveedor)cbxArticulosProveedores.getSelectedItem()).getTelefono(),((Proveedor)cbxArticulosProveedores.getSelectedItem()).getMail(),((Proveedor)cbxArticulosProveedores.getSelectedItem()).getCodigo(),null),new Categoria(((Categoria)cbxArticulosCategorias.getSelectedItem()).getId(),((Categoria)cbxArticulosCategorias.getSelectedItem()).getDescripcion()),txtArticulosCodigo.getText(),new Estado(1,"activo")));
                    DefaultTableModel dtmarti = (DefaultTableModel) tblArticulosTodos.getModel();
                    dtmarti.addRow(new Object[] {key, txtArticulosCodigo.getText(), txtArticulosDescripcion.getText(), (float)SpinArticulosPrecio.getValue()});
                    txtArticulosCodigo.setText("");
                    txtArticulosDescripcion.setText("");
                    SpinArticulosPrecio.setValue(0);
                    spinArticulosCosto.setValue(0);
                    cbxArticulosProveedores.setSelectedIndex(0);
                    cbxArticulosCategorias.setSelectedIndex(0);
                }
                    
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }
        }
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
                        listaClientes.put(key, new Cliente(key,new DescuentoCli(1),txtClientesNotas.getText(),txtClientesNombre.getText(),txtClientesApellido.getText(),txtClientesDireccion.getText(),txtClientesEmail.getText(),txtClientesTelefono.getText(),Integer.valueOf(txtClientesDni.getText()),sqlDate, txtClientesCodigo.getText(),ctrlclientes.traeEstado(1)));
                        DefaultTableModel dtm = (DefaultTableModel)tblClientestodos.getModel();
                        dtm.addRow(new Object[] {key, txtClientesCodigo.getText(), txtClientesApellido.getText(),txtClientesNombre.getText(),txtClientesDireccion.getText()});
                        limpiarDatosClientes();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
                    }
                }
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
                    ((Cliente)listaClientes.get(modificado.getIdCliente())).setEstado(modificado.getEstado());
                    dtm.removeRow(filaSeleccionadaCliente);
                    dtm.addRow(new Object[] {modificado.getIdCliente(), modificado.getNombre()+", "+modificado.getApellido(),modificado.getDireccion()});
                    limpiarInfoClientes();
                }
            });
            modcli.show();
        }else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnClintesModificarActionPerformed

    private void btnClientesElminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesElminarActionPerformed
        int resp = JOptionPane.showConfirmDialog(null,"¿Desea eliminar el cliente seleccionado?","Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (JOptionPane.YES_OPTION == resp){
            try {
                ctrlclientes.EliminarCliente(idCliente);
                DefaultTableModel dtm = (DefaultTableModel)tblClientestodos.getModel();
                dtm.removeRow(filaSeleccionadaCliente);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnClientesElminarActionPerformed

    private void btnClientesBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesBuscarActionPerformed
        int columnaABuscar = 0;
        if(rdbClientesCodigo.isSelected())
            columnaABuscar = 0;
        else
            columnaABuscar = 1;
        
        trsFiltro = new TableRowSorter(tblClientestodos.getModel());
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtClientesBuscar.getText().toUpperCase(), columnaABuscar));
        tblClientestodos.setRowSorter(trsFiltro);
        
    }//GEN-LAST:event_btnClientesBuscarActionPerformed

    private void cbxArticulosProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxArticulosProveedoresActionPerformed

    }//GEN-LAST:event_cbxArticulosProveedoresActionPerformed

    private void btnArticulosModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArticulosModificarActionPerformed
        if(idArticulo != 0){
            ModificaArticulo modart = new ModificaArticulo(this, true,idArticulo);
            modart.setLocationRelativeTo(this);
            modart.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    Articulo modificado;
                    DefaultTableModel dtm = (DefaultTableModel)tblArticulosTodos.getModel();
                    modificado = modart.getModificado();
                    ((Articulo)listaArticulos.get(modificado.getId())).setEstado(modificado.getEstado());
                    dtm.removeRow(filaSeleccionadaArticulo);
                    limpiarnfoArtciulos();
                    dtm.addRow(new Object[] {modificado.getId(), modificado.getDescripcion(),modificado.getPrecio()});
                    limpiarDatosClientes();
                }
            });
            modart.show();
        }else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnArticulosModificarActionPerformed

    private void btnArticulosElminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArticulosElminarActionPerformed
        int resp = JOptionPane.showConfirmDialog(null,"¿Desea eliminar el cliente seleccionado?","Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (JOptionPane.YES_OPTION == resp){
            try {
                ctrlclientes.EliminarCliente(idArticulo);
                DefaultTableModel dtm = (DefaultTableModel)tblArticulosTodos.getModel();
                dtm.removeRow(filaSeleccionadaArticulo);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnArticulosElminarActionPerformed

    private void limpiarDatosClientes(){
        txtClientesCodigo.setText("");
        txtClientesNombre.setText("");
        txtClientesApellido.setText("");
        txtClientesDni.setText("");
        txtClientesDireccion.setText("");
        txtClientesTelefono.setText("");
        txtClientesEmail.setText("");
        txtClientesNotas.setText("");
    }
    
    private void limpiarInfoClientes(){
        lblClientesCodigo.setText("");
        lblClienteEstado.setText("");
        lblClientesDNI.setText("");
        lblClientesDireccion.setText("");
        lblClientesFechaNac.setText("");
        lblClientesMail.setText("");
        lblClientesNombre.setText("");
        lblClientesNotas.setText("");
        lblClientesTelefono.setText("");
    }
    
    private void limpiarnfoArtciulos(){
        lblArticulosActivo.setText("");
        lblArticulosCodigo.setText("");
        lblArticulosCosto.setText("");
        lblArticulosDescripcion.setText("");
        lblArticulosFechaCompra.setText("");
        lblArticulosPrecio.setText("");
        lblArticulosProveedor.setText("");
    }
    
    private void btnArticulosBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArticulosBuscarActionPerformed
        int columnaABuscar = 0;
        if(rdbAriculosCodigo.isSelected())
            columnaABuscar = 0;
        else
            columnaABuscar = 1;
        trsFiltro = new TableRowSorter(tblArticulosTodos.getModel());
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtArticulosBuscar.getText().toUpperCase(), columnaABuscar));
        tblArticulosTodos.setRowSorter(trsFiltro);
    }//GEN-LAST:event_btnArticulosBuscarActionPerformed

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
    private javax.swing.JButton btnArticulosBuscar;
    private javax.swing.JButton btnArticulosElminar;
    private javax.swing.ButtonGroup btnArticulosFiltro;
    private javax.swing.JButton btnArticulosModificar;
    private java.awt.Button btnClientesAceptar;
    private javax.swing.JButton btnClientesBuscar;
    private javax.swing.JButton btnClientesElminar;
    private javax.swing.ButtonGroup btnClientesFiltro;
    private javax.swing.JButton btnClintesModificar;
    private java.awt.Button btnProveedoresAceptar;
    private java.awt.Button btnUsuariosAceptar;
    private javax.swing.JComboBox<Modelos.Categoria> cbxArticulosCategorias;
    private javax.swing.JComboBox<Modelos.Proveedor> cbxArticulosProveedores;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private com.toedter.calendar.JDateChooser jdcClientesFechaNac;
    private java.awt.Label labelContraseña;
    private java.awt.Label labelContraseñaR;
    private java.awt.Label labelNombre;
    private javax.swing.JLabel lblArticulosActivo;
    private javax.swing.JLabel lblArticulosCodigo;
    private javax.swing.JLabel lblArticulosCosto;
    private javax.swing.JLabel lblArticulosDescripcion;
    private javax.swing.JLabel lblArticulosFechaCompra;
    private javax.swing.JLabel lblArticulosPrecio;
    private javax.swing.JLabel lblArticulosProveedor;
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
    private javax.swing.JRadioButton rdbAriculosCodigo;
    private javax.swing.JRadioButton rdbArticulosDescripcion;
    private javax.swing.JRadioButton rdbClientesApellido;
    private javax.swing.JRadioButton rdbClientesCodigo;
    private javax.swing.JSpinner spinArticulosCosto;
    private javax.swing.JPanel tabAlquiler;
    private javax.swing.JPanel tabArticulos;
    private javax.swing.JPanel tabCaja;
    private javax.swing.JPanel tabClientes;
    private javax.swing.JPanel tabProveedores;
    private javax.swing.JPanel tabUsuarios;
    private javax.swing.JPanel tabVenta;
    private javax.swing.JTable tblArticulosTodos;
    private javax.swing.JTable tblClientestodos;
    private javax.swing.JTable tblProveedoresTodos;
    private javax.swing.JTable tblUsuariosTodos;
    private javax.swing.JTextField txtArticulosBuscar;
    private javax.swing.JTextField txtArticulosCodigo;
    private javax.swing.JTextArea txtArticulosDescripcion;
    private javax.swing.JTextField txtClientesApellido;
    private javax.swing.JTextField txtClientesBuscar;
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
