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
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        txtVentasCbte = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txtVentasPV = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        txtVentasNumero = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        txtVentasVendedor = new javax.swing.JTextField();
        cbxVentasVendedores = new javax.swing.JComboBox<>();
        jLabel66 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        txtVentasSaldoCC = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        txtVentasFecha = new javax.swing.JFormattedTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        txtVentasCodigoCli = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        txtVentasNombreCli = new javax.swing.JTextField();
        btnVentasBuscarCli = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        txtVentasCodArti = new javax.swing.JTextField();
        btnVentasBucarArti = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblVentasTablaVentas = new javax.swing.JTable();
        jLabel51 = new javax.swing.JLabel();
        txtVentasDevoluciones = new javax.swing.JTextField();
        txtVentasRecargos = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        txtVentasTotal = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        txtVentasProcesar = new javax.swing.JButton();
        tabAlquiler = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        txtAlquilerCbte = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        txtAlquilerPV = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        txtAlquilerNumero = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        txtAlquilerVendedor = new javax.swing.JTextField();
        cbxAlquilerVendedores = new javax.swing.JComboBox<>();
        jPanel16 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        txtAlquilerSaldoCC = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        txtAlquilerFecha = new javax.swing.JFormattedTextField();
        jPanel17 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        txtAlquilerCodigoCli = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        txtAlquilerNombreCli = new javax.swing.JTextField();
        btnVentasBuscarCli1 = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblAlquilerTablaAlqui = new javax.swing.JTable();
        jLabel62 = new javax.swing.JLabel();
        txtAlquilerDevoluciones = new javax.swing.JTextField();
        txtAlquilerRecargos = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        txtAlquilerTotal = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        txtAlquilerProcesar = new javax.swing.JButton();
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
        jTable1 = new javax.swing.JTable();
        tblUsuariosTodos = new javax.swing.JTable();
        tabArticulos = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
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
        jPanel20 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        SpineliculasPrecio = new javax.swing.JSpinner();
        spinPeliculasCosto = new javax.swing.JSpinner();
        jScrollPane10 = new javax.swing.JScrollPane();
        txtPeliculasDescripcion = new javax.swing.JTextArea();
        btnPeliculasAceptar = new java.awt.Button();
        jLabel69 = new javax.swing.JLabel();
        cbxPeliculasProveedores = new javax.swing.JComboBox<>();
        txtPeliculasBuscar = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        rdbPeliculasTitulo = new javax.swing.JRadioButton();
        rdbPeliculasCodigo = new javax.swing.JRadioButton();
        btnPeliculasBuscar = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblPeliculasTodos = new javax.swing.JTable();
        jLabel71 = new javax.swing.JLabel();
        lblPeliculasCodgo = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        lblPeliculasDescripcion = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        lblPeliculasPrecio = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        lblPeliculasCosto = new javax.swing.JLabel();
        lblPelculasProveedor = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        lblPeliculasFechaCompra = new javax.swing.JLabel();
        btnPeliculasModificar = new javax.swing.JButton();
        btnPeliculasElminar = new javax.swing.JButton();
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

        jLabel43.setText("Cbte:");

        txtVentasCbte.setText("00");

        jLabel44.setText("Tcket");

        txtVentasPV.setText("0000");

        jLabel45.setText("-");

        txtVentasNumero.setText("00000000");

        jLabel46.setText("Vendedor:");

        txtVentasVendedor.setText("00");

        cbxVentasVendedores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel66.setText("Clientes:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVentasCbte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVentasPV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVentasNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVentasVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxVentasVendedores, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel66)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(txtVentasCbte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44)
                    .addComponent(txtVentasPV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45)
                    .addComponent(txtVentasNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46)
                    .addComponent(txtVentasVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxVentasVendedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jLabel66))
        );

        jLabel47.setText("Sado CC");

        txtVentasSaldoCC.setText("0000");

        jLabel48.setText("Fecha:");

        txtVentasFecha.setText("__/__/____");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVentasSaldoCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 261, Short.MAX_VALUE)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVentasFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(txtVentasSaldoCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVentasFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel49.setText("Codigo:");

        txtVentasCodigoCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVentasCodigoCliActionPerformed(evt);
            }
        });

        jLabel50.setText("Nombre:");

        btnVentasBuscarCli.setText("Buscar");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtVentasCodigoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVentasNombreCli)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVentasBuscarCli)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel49)
                            .addComponent(txtVentasCodigoCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50)
                            .addComponent(txtVentasNombreCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(btnVentasBuscarCli)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel65.setText("Codigo Articulo: ");

        btnVentasBucarArti.setText("Buscar");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel65)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVentasCodArti, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVentasBucarArti)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(0, 46, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(txtVentasCodArti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVentasBucarArti)))
        );

        tblVentasTablaVentas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(tblVentasTablaVentas);

        jLabel51.setText("Devoluciones");
        jLabel51.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel52.setText("Recargos");
        jLabel52.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel53.setText("TOTAL A COBRAR");

        txtVentasProcesar.setText("Procesar");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jScrollPane8)
                        .addContainerGap())
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtVentasDevoluciones)
                            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtVentasRecargos, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtVentasTotal)
                            .addComponent(jLabel53))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVentasProcesar, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel51)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVentasDevoluciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel52)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVentasRecargos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVentasTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtVentasProcesar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tabVentaLayout = new javax.swing.GroupLayout(tabVenta);
        tabVenta.setLayout(tabVentaLayout);
        tabVentaLayout.setHorizontalGroup(
            tabVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabVentaLayout.setVerticalGroup(
            tabVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabContent.addTab("VENTA", tabVenta);

        jLabel54.setText("Cbte:");

        txtAlquilerCbte.setText("00");

        jLabel55.setText("Tcket");

        txtAlquilerPV.setText("0000");

        jLabel56.setText("-");

        txtAlquilerNumero.setText("00000000");

        jLabel57.setText("Vendedor:");

        txtAlquilerVendedor.setText("00");

        cbxAlquilerVendedores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAlquilerCbte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAlquilerPV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel56)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAlquilerNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAlquilerVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxAlquilerVendedores, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(txtAlquilerCbte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55)
                    .addComponent(txtAlquilerPV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56)
                    .addComponent(txtAlquilerNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57)
                    .addComponent(txtAlquilerVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxAlquilerVendedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jLabel58.setText("Sado CC");

        txtAlquilerSaldoCC.setText("0000");

        jLabel59.setText("Fecha:");

        txtAlquilerFecha.setText("__/__/____");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel58)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAlquilerSaldoCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 261, Short.MAX_VALUE)
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAlquilerFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(txtAlquilerSaldoCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAlquilerFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel60.setText("Codigo:");

        txtAlquilerCodigoCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlquilerCodigoCliActionPerformed(evt);
            }
        });

        jLabel61.setText("Nombre:");

        btnVentasBuscarCli1.setText("Buscar");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAlquilerCodigoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel61)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAlquilerNombreCli)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVentasBuscarCli1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel60)
                            .addComponent(txtAlquilerCodigoCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel61)
                            .addComponent(txtAlquilerNombreCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(btnVentasBuscarCli1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        tblAlquilerTablaAlqui.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane9.setViewportView(tblAlquilerTablaAlqui);

        jLabel62.setText("Devoluciones");
        jLabel62.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel63.setText("Recargos");
        jLabel63.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel64.setText("TOTAL A COBRAR");

        txtAlquilerProcesar.setText("Procesar");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jScrollPane9)
                        .addContainerGap())
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtAlquilerDevoluciones)
                            .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtAlquilerRecargos, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtAlquilerTotal)
                            .addComponent(jLabel64))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAlquilerProcesar, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel62)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAlquilerDevoluciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel63)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAlquilerRecargos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel64)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAlquilerTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtAlquilerProcesar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tabAlquilerLayout = new javax.swing.GroupLayout(tabAlquiler);
        tabAlquiler.setLayout(tabAlquilerLayout);
        tabAlquilerLayout.setHorizontalGroup(
            tabAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabAlquilerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabAlquilerLayout.setVerticalGroup(
            tabAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabAlquilerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(jTable1);
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
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
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel25))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxArticulosProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinArticulosCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SpinArticulosPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(48, 48, 48)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 329, Short.MAX_VALUE)
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

        rdbArticulosDescripcion.setText("Por Descripcion");

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

        jLabel33.setText("Proveedor:");

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
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE))
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

        jPanel20.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel20.setPreferredSize(new java.awt.Dimension(365, 574));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Alta Pelicula");

        jLabel26.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel26.setText("Descripción:");

        jLabel67.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel67.setText("Costo:");

        jLabel68.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel68.setText("Precio:");

        SpineliculasPrecio.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 1.0f));
        SpineliculasPrecio.setEditor(new javax.swing.JSpinner.NumberEditor(SpineliculasPrecio, "0.00"));

        spinPeliculasCosto.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 1.0f));
        spinPeliculasCosto.setEditor(new javax.swing.JSpinner.NumberEditor(spinPeliculasCosto, "0.00"));

        txtPeliculasDescripcion.setColumns(20);
        txtPeliculasDescripcion.setRows(5);
        jScrollPane10.setViewportView(txtPeliculasDescripcion);

        btnPeliculasAceptar.setLabel("Aceptar");
        btnPeliculasAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeliculasAceptarActionPerformed(evt);
            }
        });

        jLabel69.setText("Proveedor:");

        cbxPeliculasProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxPeliculasProveedoresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(btnPeliculasAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel67)
                            .addComponent(jLabel68)
                            .addComponent(jLabel26)
                            .addComponent(jLabel69))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxPeliculasProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spinPeliculasCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SpineliculasPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(48, 48, 48)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel67)
                    .addComponent(spinPeliculasCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(SpineliculasPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69)
                    .addComponent(cbxPeliculasProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 341, Short.MAX_VALUE)
                .addComponent(btnPeliculasAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel70.setText("Buscar:");

        rdbPeliculasTitulo.setText("Por Titulo");

        rdbPeliculasCodigo.setText("Por Codigo");
        rdbPeliculasCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbPeliculasCodigoActionPerformed(evt);
            }
        });

        btnPeliculasBuscar.setText("Buscar");
        btnPeliculasBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeliculasBuscarActionPerformed(evt);
            }
        });

        tblPeliculasTodos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane11.setViewportView(tblPeliculasTodos);

        jLabel71.setText("Codigo:");

        jLabel72.setText("Descripción:");

        jLabel73.setText("Precio:");

        jLabel74.setText("Costo:");

        jLabel75.setText("Proveedor:");

        jLabel76.setText("Fecha de compra:");

        btnPeliculasModificar.setText("Modificar");
        btnPeliculasModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeliculasModificarActionPerformed(evt);
            }
        });

        btnPeliculasElminar.setText("Eliminar");
        btnPeliculasElminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeliculasElminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel70)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPeliculasBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdbPeliculasCodigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdbPeliculasTitulo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPeliculasBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel71)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblPeliculasCodgo, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(43, 43, 43))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel72)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblPeliculasDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel73)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPeliculasPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel74)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPeliculasCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel76)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPeliculasFechaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel75)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPelculasProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addContainerGap())
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(btnPeliculasModificar)
                        .addGap(337, 337, 337)
                        .addComponent(btnPeliculasElminar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPeliculasBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdbPeliculasCodigo)
                    .addComponent(rdbPeliculasTitulo)
                    .addComponent(btnPeliculasBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPeliculasPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPeliculasCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPeliculasCodgo, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel72)
                            .addComponent(lblPeliculasDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPeliculasFechaCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPelculasProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPeliculasModificar)
                    .addComponent(btnPeliculasElminar))
                .addContainerGap())
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
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TabContent, javax.swing.GroupLayout.PREFERRED_SIZE, 1004, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        if(txtArticulosDescripcion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "codigo o descripcion de articulo vacio, complete los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                    java.util.Date fechaact = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(fechaact.getTime());
            
                key = ctrlarticulos.agregarArticulo(txtArticulosDescripcion.getText(),(float)spinArticulosCosto.getValue(),(float)SpinArticulosPrecio.getValue(),((Proveedor)cbxArticulosProveedores.getSelectedItem()).getId(),2);
            
                    listaArticulos.put(key, new Articulo(key,txtArticulosDescripcion.getText(),(float)spinArticulosCosto.getValue(),(float)SpinArticulosPrecio.getValue(),sqlDate,new Proveedor(((Proveedor)cbxArticulosProveedores.getSelectedItem()).getId(),((Proveedor)cbxArticulosProveedores.getSelectedItem()).getRazonsocial(),((Proveedor)cbxArticulosProveedores.getSelectedItem()).getDireccion(),((Proveedor)cbxArticulosProveedores.getSelectedItem()).getTelefono(),((Proveedor)cbxArticulosProveedores.getSelectedItem()).getMail(),((Proveedor)cbxArticulosProveedores.getSelectedItem()).getCodigo(),null),new Categoria(2,"Venta"),new Estado(1,"activo")));
                    DefaultTableModel dtmarti = (DefaultTableModel) tblArticulosTodos.getModel();
                    dtmarti.addRow(new Object[] {key, key, txtArticulosDescripcion.getText().toUpperCase(), (float)SpinArticulosPrecio.getValue()});
                    txtArticulosDescripcion.setText("");
                    SpinArticulosPrecio.setValue(0);
                    spinArticulosCosto.setValue(0);
                    cbxArticulosProveedores.setSelectedIndex(0);
                    } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "MySql.", "Error", JOptionPane.ERROR_MESSAGE);
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
                    dtm.addRow(new Object[] {modificado.getIdCliente(), modificado.getApellido().toUpperCase(), modificado.getNombre(),modificado.getDireccion()});
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
                    dtm.addRow(new Object[] {modificado.getId(), modificado.getDescripcion().toUpperCase(),modificado.getPrecio()});
                    limpiarDatosClientes();
                }
            });
            modart.show();
        }else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un articulo", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void txtVentasCodigoCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVentasCodigoCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVentasCodigoCliActionPerformed

    private void txtAlquilerCodigoCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlquilerCodigoCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlquilerCodigoCliActionPerformed

    private void btnPeliculasAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeliculasAceptarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPeliculasAceptarActionPerformed

    private void cbxPeliculasProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxPeliculasProveedoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxPeliculasProveedoresActionPerformed

    private void btnPeliculasBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeliculasBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPeliculasBuscarActionPerformed

    private void rdbPeliculasCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbPeliculasCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdbPeliculasCodigoActionPerformed

    private void btnPeliculasModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeliculasModificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPeliculasModificarActionPerformed

    private void btnPeliculasElminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeliculasElminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPeliculasElminarActionPerformed

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
    private javax.swing.JSpinner SpineliculasPrecio;
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
    private java.awt.Button btnPeliculasAceptar;
    private javax.swing.JButton btnPeliculasBuscar;
    private javax.swing.JButton btnPeliculasElminar;
    private javax.swing.JButton btnPeliculasModificar;
    private java.awt.Button btnProveedoresAceptar;
    private java.awt.Button btnUsuariosAceptar;
    private javax.swing.JButton btnVentasBucarArti;
    private javax.swing.JButton btnVentasBuscarCli;
    private javax.swing.JButton btnVentasBuscarCli1;
    private javax.swing.JComboBox<String> cbxAlquilerVendedores;
    private javax.swing.JComboBox<Modelos.Proveedor> cbxArticulosProveedores;
    private javax.swing.JComboBox<Modelos.Proveedor> cbxPeliculasProveedores;
    private javax.swing.JComboBox<String> cbxVentasVendedores;
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
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable1;
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
    private javax.swing.JLabel lblPelculasProveedor;
    private javax.swing.JLabel lblPeliculasCodgo;
    private javax.swing.JLabel lblPeliculasCosto;
    private javax.swing.JLabel lblPeliculasDescripcion;
    private javax.swing.JLabel lblPeliculasFechaCompra;
    private javax.swing.JLabel lblPeliculasPrecio;
    private javax.swing.JMenu menuAlquileres;
    private javax.swing.JRadioButton rdbAriculosCodigo;
    private javax.swing.JRadioButton rdbArticulosDescripcion;
    private javax.swing.JRadioButton rdbClientesApellido;
    private javax.swing.JRadioButton rdbClientesCodigo;
    private javax.swing.JRadioButton rdbPeliculasCodigo;
    private javax.swing.JRadioButton rdbPeliculasTitulo;
    private javax.swing.JSpinner spinArticulosCosto;
    private javax.swing.JSpinner spinPeliculasCosto;
    private javax.swing.JPanel tabAlquiler;
    private javax.swing.JPanel tabArticulos;
    private javax.swing.JPanel tabCaja;
    private javax.swing.JPanel tabClientes;
    private javax.swing.JPanel tabProveedores;
    private javax.swing.JPanel tabUsuarios;
    private javax.swing.JPanel tabVenta;
    private javax.swing.JTable tblAlquilerTablaAlqui;
    private javax.swing.JTable tblArticulosTodos;
    private javax.swing.JTable tblClientestodos;
    private javax.swing.JTable tblPeliculasTodos;
    private javax.swing.JTable tblProveedoresTodos;
    private javax.swing.JTable tblVentasTablaVentas;
    private javax.swing.JTextField txtAlquilerCbte;
    private javax.swing.JTextField txtAlquilerCodigoCli;
    private javax.swing.JTextField txtAlquilerDevoluciones;
    private javax.swing.JFormattedTextField txtAlquilerFecha;
    private javax.swing.JTextField txtAlquilerNombreCli;
    private javax.swing.JTextField txtAlquilerNumero;
    private javax.swing.JTextField txtAlquilerPV;
    private javax.swing.JButton txtAlquilerProcesar;
    private javax.swing.JTextField txtAlquilerRecargos;
    private javax.swing.JTextField txtAlquilerSaldoCC;
    private javax.swing.JTextField txtAlquilerTotal;
    private javax.swing.JTextField txtAlquilerVendedor;
    private javax.swing.JTextField txtArticulosBuscar;
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
    private javax.swing.JTextField txtPeliculasBuscar;
    private javax.swing.JTextArea txtPeliculasDescripcion;
    private javax.swing.JTextField txtProveedoresDireccion;
    private javax.swing.JTextField txtProveedoresDireccion1;
    private javax.swing.JTextField txtProveedoresDireccion2;
    private javax.swing.JTextField txtProveedoresNombre;
    private javax.swing.JTextArea txtProveedoresNotas;
    private java.awt.Label txtTituloAltaUsuario;
    private javax.swing.JPasswordField txtUsuariosContraseña;
    private javax.swing.JTextField txtUsuariosNombre;
    private javax.swing.JPasswordField txtUsuariosRepContraseña;
    private javax.swing.JTextField txtVentasCbte;
    private javax.swing.JTextField txtVentasCodArti;
    private javax.swing.JTextField txtVentasCodigoCli;
    private javax.swing.JTextField txtVentasDevoluciones;
    private javax.swing.JFormattedTextField txtVentasFecha;
    private javax.swing.JTextField txtVentasNombreCli;
    private javax.swing.JTextField txtVentasNumero;
    private javax.swing.JTextField txtVentasPV;
    private javax.swing.JButton txtVentasProcesar;
    private javax.swing.JTextField txtVentasRecargos;
    private javax.swing.JTextField txtVentasSaldoCC;
    private javax.swing.JTextField txtVentasTotal;
    private javax.swing.JTextField txtVentasVendedor;
    // End of variables declaration//GEN-END:variables
}
