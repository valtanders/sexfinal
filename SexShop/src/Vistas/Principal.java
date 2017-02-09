/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Controladoras.ctrlABMArticulos;
import Controladoras.ctrlABMClientes;
import Controladoras.ctrlABMProveedores;
import Controladoras.ctrlABMUsuarios;
import Controladoras.ctrlAlquiler;
import Controladoras.ctrlCaja;
import Controladoras.ctrlOperaciones;
import Controladoras.ctrlTransaccion;
import Modelos.Articulo;
import Modelos.Alquiler;
import Modelos.Caja;
import Modelos.Categoria;
import Modelos.Cliente;
import Modelos.DescuentoCli;
import Modelos.DetCaja;
import Modelos.Estado;
import Modelos.Proveedor;
import Modelos.TipoOperacion;
import Modelos.Usuario;
import Modelos.CabOperacion;
import Modelos.DetOperacion;
import Modelos.FormatoTabla;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
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
    private ConcurrentHashMap listaUsuarios;
    private ConcurrentHashMap listaPreciosAlqui;
    private ConcurrentHashMap listaClientesCumple;
    private ConcurrentHashMap listaingregre;
    private ArrayList listaDetalleVenta;
    private ArrayList listaDetalleAlqui;
    private ctrlABMClientes ctrlclientes = new ctrlABMClientes();
    private ctrlABMUsuarios ctrlusuarios = new ctrlABMUsuarios();
    private ctrlABMArticulos ctrlarticulos = new ctrlABMArticulos();
    private ctrlABMProveedores ctrlproveedores = new ctrlABMProveedores();
    private ctrlCaja ctrlcaja = new ctrlCaja();
    private ctrlAlquiler ctrlalquiler = new ctrlAlquiler();
    private ctrlOperaciones ctrloperaciones = new ctrlOperaciones();
    private ctrlTransaccion ctrltransaccion = new ctrlTransaccion();

    private int idCliente;
    private int idArticulo;
    private int idArticuloStock;
    private int idPelicula;
    private int idProveedor;
    private int filaSeleccionadaCliente;
    private int filaSeleccionadaArticulo;
    private int filaSeleccionadaArticuloStock;
    private int filaSeleccionadaPelicula;
    private int filaSeleccionadaProveedor;
    private int filaSeleccionadaInreso;
    private int filaSeleccionadaEgreso;
    private int keyCajaAper;
    private int iddetalle;
    private int numVenta;
    private int numAlquiler;
    private float totalVentas;
    private float totalAlquiler;
    private float totalCaja;
    private float precioAlqui;
    private float valorAnteriorIngre;
    private float valorAnteriorEgre;
    private TableRowSorter trsFiltro;
    private Cliente clienteVenta;
    private Cliente clienteAlqui;
    private Caja caja;
    private Caja cajaCerrada;
    private DetCaja detCaja;
    private Usuario user;
    private CumplesF cumples;

    public Principal() {
    }

    public Principal(Usuario user) throws SQLException {
        this.getContentPane().setBackground(Color.WHITE);
        this.setTitle("Salesforce - 3N Consulting");
        initComponents();
        //this.setIconImage(new ImageIcon(getClass().getResource("../imagenes/3n_ico.png")).getImage());
        this.setSize(800, 600);
        this.user = user;
        cbxArticulosProveedores.removeAllItems();
        cbxPeliculasProveedores.removeAllItems();
        cbxAlquilerVendedores.removeAllItems();
        cbxVentasVendedores.removeAllItems();
        btnClientesFiltro.add(rdbClientesCodigo);
        btnClientesFiltro.add(rdbClientesApellido);
        rdbClientesCodigo.setSelected(true);
        btnArticulosFiltro.add(rdbAriculosCodigo);
        btnArticulosFiltro.add(rdbArticulosDescripcion);
        rdbAriculosCodigo.setSelected(true);
        btnPeliculasFitro.add(rdbPeliculasCodigo);
        btnPeliculasFitro.add(rdbPeliculasTitulo);
        rdbPeliculasCodigo.setSelected(true);
        btnVentaPago.add(rdbVentaContado);
        btnVentaPago.add(rdbVentaCtaCte);
        rdbVentaContado.setSelected(true);
        btnAlquilerPago.add(rdbAlquilerContado);
        btnAlquilerPago.add(rdbAlquilerCtaCte);
        rdbAlquilerCtaCte.setSelected(true);
        lblClienteEstado.setHorizontalAlignment(SwingConstants.CENTER);
        lblArticulosActivo.setHorizontalAlignment(SwingConstants.CENTER);
        SimpleDateFormat fechaformat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = new GregorianCalendar();
        jdcClientesFechaNac.setCalendar(calendar);
        Date ahora = new Date();
        txtVentasFecha.setText(fechaformat.format(ahora));
        txtAlquilerFecha.setText(fechaformat.format(ahora));
        idCliente = 0;
        idArticulo = 0;
        idArticuloStock = 0;
        idPelicula = 0;
        idProveedor = 0;
        totalVentas = 0;
        totalAlquiler = 0;
        iddetalle = 0;
        valorAnteriorIngre = 0;
        valorAnteriorEgre = 0;
        listaDetalleVenta = new ArrayList();
        listaDetalleAlqui = new ArrayList();
        clienteVenta = null;
        txtCajaConceptoIngr.setEnabled(false);
        txtCajaConceptoEgre.setEnabled(false);
        jMenu3.setVisible(false);
        jMenu3.setEnabled(false);
        try {
            caja = ctrlcaja.cajaAbierta();
            cajaCerrada = ctrlcaja.traeUltimoCierre();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
        }
        if (caja == null) {
            JOptionPane.showMessageDialog(null, "Debe realizar una apertura de caja antes de continuar", "Error", JOptionPane.ERROR_MESSAGE);
            TabDevoluciones.setEnabledAt(TabDevoluciones.indexOfTab("VENTA"), false);
            TabDevoluciones.setEnabledAt(TabDevoluciones.indexOfTab("ALQUILER"), false);
            TabDevoluciones.setSelectedIndex(10);
            btnCajaCierre.setEnabled(false);
            txtCajaMontoCierre.setEnabled(false);
            txtCajaMontoIngr.setEnabled(false);
            txtCajaMontoEgre.setEnabled(false);
            lblCajaEstado.setText("Caja Cerrada");
            lblCajaEstado.setForeground(Color.red);
            txtCajaConceptoIngr.setEnabled(false);
            txtCajaMontoIngr.setEnabled(false);
            txtCajaConceptoEgre.setEnabled(false);
            txtCajaMontoEgre.setEnabled(false);
            btnCajaAnadirIngre.setEnabled(false);
            btnCajaModificarIngre.setEnabled(false);
            btnCajaEliminarIngre.setEnabled(false);
            btnCajaanadirEgre.setEnabled(false);
            btnCajaModificarEgre.setEnabled(false);
            btnCajaEliminarEgre.setEnabled(false);
            if (cajaCerrada != null) {
                if (cajaCerrada.getUsuario().getId() < 10) {
                    lblCajaCodigoVendedorCierre.setText("0" + cajaCerrada.getUsuario().getId());
                } else {
                    lblCajaCodigoVendedorCierre.setText(String.valueOf(cajaCerrada.getUsuario().getId()));
                }
                lblCajaNomVendedorCierre.setText(cajaCerrada.getUsuario().getNombre());
                Date cierre = cajaCerrada.getFechaAper();
                lblCajaFechaCierre.setText(fechaformat.format(cierre));
            }
        } else {
            try {
                detCaja = ctrlcaja.traeUltimoDetalleAper();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }
            if (detCaja != null) {
                java.util.Date date = new Date();
                totalCaja = detCaja.getMonto();
                keyCajaAper = caja.getIdCaja();
                btnCajaAbre.setEnabled(false);
                txtCajaMontoAper.setEnabled(false);
                txtCajaMontoIngr.setEnabled(true);
                txtCajaMontoEgre.setEnabled(true);
                txtCajaMontoAper.setText(String.valueOf(totalCaja));
                lblCajaEstado.setText("Caja Abierta");
                lblCajaEstado.setForeground(Color.green);
                txtCajaConceptoIngr.setEnabled(true);
                txtCajaMontoIngr.setEnabled(true);
                txtCajaConceptoEgre.setEnabled(true);
                txtCajaMontoEgre.setEnabled(true);
                btnCajaAnadirIngre.setEnabled(true);
                btnCajaModificarIngre.setEnabled(true);
                btnCajaEliminarIngre.setEnabled(true);
                btnCajaanadirEgre.setEnabled(true);
                btnCajaModificarEgre.setEnabled(true);
                btnCajaEliminarEgre.setEnabled(true);
                if (caja.getUsuario().getId() < 10) {
                    lblCajaCodigoVendedor.setText("0" + caja.getUsuario().getId());
                } else {
                    lblCajaCodigoVendedor.setText(String.valueOf(caja.getUsuario().getId()));
                }
                lblCajaNomVendedor.setText(caja.getUsuario().getNombre());
                Date aper = caja.getFechaAper();
                lblCajaFechaAper.setText(fechaformat.format(aper));
                if (!aper.equals(date))
                    JOptionPane.showMessageDialog(null, "Existe una apertura del dia "+aper, "Apertura de caja", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        numAlquiler = ctrltransaccion.traeUltimoNumero(1);
        numVenta = ctrltransaccion.traeUltimoNumero(2);
        txtVentasPV.setText("01");
        txtAlquilerPV.setText("01");
        if (numAlquiler > 0) {
            txtAlquilerNumero.setText(String.format("%010d", numAlquiler + 1));
        } else {
            txtAlquilerNumero.setText(String.format("%010d", 1));
        }
        if (numVenta > 0) {
            txtVentasNumero.setText(String.format("%010d", numVenta + 1));
        } else {
            txtVentasNumero.setText(String.format("%010d", 1));
        }
        try {
            listaClientesCumple = ctrlclientes.traeCumpleanieros();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
        }
        if (listaClientesCumple.size() > 0) {
            cumples = new CumplesF(listaClientesCumple);
            cumples.setVisible(true);
        }
        DefaultTableModel dtdevoluciones = new DefaultTableModel(new Object[]{"Cliente", "Titulo", "Fecha de alquiler", "Fecha de devolucion"}, 0) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        tblDevoluciones.setModel(dtdevoluciones);
        DefaultTableModel dtventas = new DefaultTableModel(new Object[]{"Codigo", "Descripcion", "Precio", "Cantidad", "total"}, 0) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        dtventas.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tme) {
                if (tme.getType() == TableModelEvent.INSERT || tme.getType() == TableModelEvent.UPDATE || tme.getType() == TableModelEvent.DELETE) {
                    txtVentasTotal.setText(String.valueOf(totalVentas));
                }
            }
        });
        DefaultTableModel dtalqui = new DefaultTableModel(new Object[]{"Codigo", "Descripcion", "Precio", "Cantidad"}, 0) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        dtalqui.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tme) {
                if (tme.getType() == TableModelEvent.INSERT || tme.getType() == TableModelEvent.UPDATE || tme.getType() == TableModelEvent.DELETE) {
                    txtAlquilerTotal.setText(String.valueOf(totalAlquiler));
                }
            }
        });
        tblVentasTablaVentas.setModel(dtventas);
        tblAlquilerTablaAlqui.setModel(dtalqui);
        DefaultTableModel dtmingre = new DefaultTableModel(new Object[]{"cod", "Descripcion Ingreso", "Monto"}, 0) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        dtventas.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tme) {
                if (tme.getType() == TableModelEvent.INSERT || tme.getType() == TableModelEvent.UPDATE || tme.getType() == TableModelEvent.DELETE) {
                    txtCajaTotalIngre.setText(String.valueOf(totalVentas));
                }
            }
        });
        DefaultTableModel dtmegre = new DefaultTableModel(new Object[]{"cod", "Descripcion Ingreso", "Monto"}, 0) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        dtventas.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tme) {
                if (tme.getType() == TableModelEvent.INSERT || tme.getType() == TableModelEvent.UPDATE || tme.getType() == TableModelEvent.DELETE) {
                    txtCajaTotalEgre.setText(String.valueOf(totalVentas));
                }
            }
        });
        txtCajaTotalIngre.setText("0");
        txtCajaTotalEgre.setText("0");
        if (listaingregre == null) {
            listaingregre = new ConcurrentHashMap();
        }
        if (caja != null) {
            float totalingre = 0;
            float totalegre = 0;
            listaingregre = ctrlcaja.traerDetallePorCaja(caja.getIdCaja());
            for (Iterator it = listaingregre.entrySet().iterator(); it.hasNext();) {
                ConcurrentHashMap.Entry<?, ?> entry = (ConcurrentHashMap.Entry<?, ?>) it.next();
                if (((DetCaja) entry.getValue()).getTipoOperacion().equals("I")) {
                    dtmingre.addRow(new Object[]{entry.getKey(), ((DetCaja) entry.getValue()).getConcepto(), ((DetCaja) entry.getValue()).getMonto()});
                    totalingre += ((DetCaja) entry.getValue()).getMonto();
                } else {
                    dtmegre.addRow(new Object[]{entry.getKey(), ((DetCaja) entry.getValue()).getConcepto(), ((DetCaja) entry.getValue()).getMonto()});
                    totalegre += ((DetCaja) entry.getValue()).getMonto();
                }
            }
            if (totalingre > 0) {
                txtCajaTotalIngre.setText(String.valueOf(totalingre));
            }
            if (totalegre > 0) {
                txtCajaTotalEgre.setText(String.valueOf(totalegre));
            }
        }
        tblCajaIngresos.setModel(dtmingre);
        tblCajaIngresos.getColumnModel().getColumn(0).setMaxWidth(0);
        tblCajaIngresos.getColumnModel().getColumn(0).setMinWidth(0);
        tblCajaIngresos.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        tblCajaIngresos.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        tblCajaIngresos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tblCajaIngresos.rowAtPoint(e.getPoint());
                int columna = tblCajaIngresos.columnAtPoint(e.getPoint());
                if ((fila > -1) && (columna > -1)) {
                    filaSeleccionadaInreso = fila;
                    DetCaja detcaja = (DetCaja) listaingregre.get(tblCajaIngresos.getModel().getValueAt(fila, 0));
                    if (!detcaja.getConcepto().contains("Apertura")) {
                        txtCajaConceptoIngr.setText(detcaja.getConcepto());
                        txtCajaMontoIngr.setValue(detcaja.getMonto());
                        valorAnteriorIngre = detcaja.getMonto();
                        iddetalle = detcaja.getId();
                    } else {
                        JOptionPane.showMessageDialog(null, "No selecciono ingreso o es Apertura", "Invalido", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        });
        tblCajaEgresos.setModel(dtmegre);
        tblCajaEgresos.getColumnModel().getColumn(0).setMaxWidth(0);
        tblCajaEgresos.getColumnModel().getColumn(0).setMinWidth(0);
        tblCajaEgresos.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        tblCajaEgresos.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        tblCajaEgresos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tblCajaEgresos.rowAtPoint(e.getPoint());
                int columna = tblCajaEgresos.columnAtPoint(e.getPoint());
                if ((fila > -1) && (columna > -1)) {
                    filaSeleccionadaEgreso = fila;
                    DetCaja detcaja = (DetCaja) listaingregre.get(tblCajaEgresos.getModel().getValueAt(fila, 0));
                    if (!detcaja.getConcepto().contains("Cierre")) {
                        txtCajaConceptoEgre.setText(detcaja.getConcepto());
                        txtCajaMontoEgre.setValue(detcaja.getMonto());
                        valorAnteriorEgre = detcaja.getMonto();
                        iddetalle = detcaja.getId();
                    } else {
                        JOptionPane.showMessageDialog(null, "No selecciono ingreso o es Cierre", "Invalido", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        });
        try {
            listaProveedores = ctrlproveedores.traerTodos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
        }
        if (listaProveedores
                != null) {
            for (Iterator it = listaProveedores.entrySet().iterator(); it.hasNext();) {
                ConcurrentHashMap.Entry<?, ?> entry = (ConcurrentHashMap.Entry<?, ?>) it.next();
                cbxArticulosProveedores.addItem(((Proveedor) entry.getValue()));
                cbxPeliculasProveedores.addItem(((Proveedor) entry.getValue()));
            }
        }

        try {
            listaArticulos = ctrlarticulos.traerTodos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
        }
        if (listaArticulos
                != null) {
            DefaultTableModel dtmarti = new DefaultTableModel(new Object[]{"Codigo", "Descripcion", "Precio"}, 0) {
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            DefaultTableModel dtmpeli = new DefaultTableModel(new Object[]{"Codigo", "Descripcion", "Precio"}, 0) {
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }

                public Class getColumnClass(int c) {
                    return getValueAt(0, c).getClass();
                }
            };
            for (Iterator it = listaArticulos.entrySet().iterator(); it.hasNext();) {
                ConcurrentHashMap.Entry<?, ?> entry = (ConcurrentHashMap.Entry<?, ?>) it.next();
                if (((Articulo) entry.getValue()).getCategoria().getId() == 2) {
                    dtmarti.addRow(new Object[]{entry.getKey(), ((Articulo) entry.getValue()).getDescripcion().toUpperCase(), ((Articulo) entry.getValue()).getPrecio()});
                } else if (((Articulo) entry.getValue()).getCategoria().getId() == 1) {
                    dtmpeli.addRow(new Object[]{entry.getKey(), ((Articulo) entry.getValue()).getDescripcion().toUpperCase(), ((Articulo) entry.getValue()).getPrecio()});
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

            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dtmpeli);
            RowSorter<TableModel> sorterart = new TableRowSorter<TableModel>(dtmarti);
            tblPeliculasTodos.setRowSorter(sorter);
            tblArticulosTodos.setRowSorter(sorterart);
            tblArticulosTodos.setModel(dtmarti);
            tblPeliculasTodos.setModel(dtmpeli);
            tblPeliculasTodos.getColumnModel().getColumn(0).setCellRenderer(render);
            tblPeliculasTodos.getRowSorter().toggleSortOrder(0);
            tblArticulosTodos.getColumnModel().getColumn(0).setCellRenderer(render);
            tblArticulosTodos.getRowSorter().toggleSortOrder(0);
            tblArticulosTodos.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    SimpleDateFormat fechaformat = new SimpleDateFormat("dd/MM/yyyy");
                    int fila = tblArticulosTodos.rowAtPoint(e.getPoint());
                    int columna = tblArticulosTodos.columnAtPoint(e.getPoint());
                    if ((fila > -1) && (columna > -1)) {
                        filaSeleccionadaArticulo = fila;
                        Articulo arti = (Articulo) listaArticulos.get(tblArticulosTodos.getModel().getValueAt(fila, 0));
                        lblArticulosCodigo.setText(String.valueOf(arti.getId()));
                        if (arti.getEstado().getDescripcion().equals("ACTIVO")) {
                            lblArticulosActivo.setForeground(Color.green);
                            lblArticulosActivo.setText(arti.getEstado().getDescripcion());
                        } else {
                            lblArticulosActivo.setForeground(Color.red);
                            lblArticulosActivo.setText(arti.getEstado().getDescripcion());
                        }
                        idArticulo = arti.getId();
                        lblArticulosDescripcion.setText(arti.getDescripcion());
                        lblArticulosPrecio.setText("$ " + String.valueOf(arti.getPrecio()));
                        lblArticulosCosto.setText("$ " + String.valueOf(arti.getCosto()));
                        lblArticulosFechaCompra.setText(fechaformat.format(arti.getFechaCompra()));
                        lblArticulosProveedor.setText(arti.getProveedor().getRazonsocial());
                    }
                }
            });
            tblPeliculasTodos.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {

                    SimpleDateFormat fechaformat = new SimpleDateFormat("dd/MM/yyyy");
                    int fila = tblPeliculasTodos.rowAtPoint(e.getPoint());
                    int columna = tblPeliculasTodos.columnAtPoint(e.getPoint());
                    if ((fila > -1) && (columna > -1)) {
                        filaSeleccionadaPelicula = fila;
                        Articulo arti = (Articulo) listaArticulos.get(tblPeliculasTodos.getModel().getValueAt(fila, 0));
                        lblPeliculasCodigo.setText(String.valueOf(arti.getId()));
                        if (arti.getEstado().getDescripcion().equals("ACTIVO")) {
                            lblPeliculasActivo.setForeground(Color.green);
                            lblPeliculasActivo.setText(arti.getEstado().getDescripcion());
                        } else {
                            lblPeliculasActivo.setForeground(Color.red);
                            lblPeliculasActivo.setText(arti.getEstado().getDescripcion());
                        }
                        idPelicula = arti.getId();
                        lblPeliculasDescripcion.setText(arti.getDescripcion());
                        lblPeliculasPrecio.setText("$ " + String.valueOf(arti.getPrecio()));
                        lblPeliculasCosto.setText("$ " + String.valueOf(arti.getCosto()));
                        lblPeliculasFechaCompra.setText(fechaformat.format(arti.getFechaCompra()));
                        if (arti.getProveedor() != null) {
                            lblPeliculasProveedor.setText(arti.getProveedor().getRazonsocial());
                        }
                    }
                }
            });
        }

        try {
            listaUsuarios = ctrlusuarios.traerTodo();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
        }
        if (listaUsuarios
                != null) {
            for (Iterator it = listaUsuarios.entrySet().iterator(); it.hasNext();) {
                ConcurrentHashMap.Entry<?, ?> entry = (ConcurrentHashMap.Entry<?, ?>) it.next();
                cbxAlquilerVendedores.addItem(((Usuario) entry.getValue()));
                cbxVentasVendedores.addItem(((Usuario) entry.getValue()));
            }
        }
        Usuario item = null;
        int i = 0;
        boolean esta = false;

        while (i < cbxAlquilerVendedores.getItemCount()
                && !esta) {
            item = (Usuario) cbxAlquilerVendedores.getItemAt(i);
            if (item.getId() == user.getId()) {
                cbxAlquilerVendedores.setSelectedIndex(i);
                cbxVentasVendedores.setSelectedIndex(i);
                esta = true;
            }
            i++;
        }

        if (item.getId()
                < 10) {
            txtAlquilerVendedor.setText("0" + String.valueOf(user.getId()));
            txtVentasVendedor.setText("0" + String.valueOf(user.getId()));
        } else {
            txtAlquilerVendedor.setText(String.valueOf(user.getId()));
            txtVentasVendedor.setText(String.valueOf(user.getId()));
        }

        if (user.getRol()
                == 1) {
            DefaultTableModel dtmProvee = new DefaultTableModel(new Object[]{"Codigo", "Nombre", "Direccion", "Telefono"}, 0) {
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            for (Iterator it = listaProveedores.entrySet().iterator(); it.hasNext();) {
                ConcurrentHashMap.Entry<?, ?> entry = (ConcurrentHashMap.Entry<?, ?>) it.next();
                dtmProvee.addRow(new Object[]{entry.getKey(), ((Proveedor) entry.getValue()).getRazonsocial(), ((Proveedor) entry.getValue()).getDireccion(), ((Proveedor) entry.getValue()).getTelefono()});
            }
            tblProveedoresTodos.setModel(dtmProvee);
            tblProveedoresTodos.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int fila = tblProveedoresTodos.rowAtPoint(e.getPoint());
                    int columna = tblProveedoresTodos.columnAtPoint(e.getPoint());
                    if ((fila > -1) && (columna > -1)) {
                        filaSeleccionadaProveedor = fila;
                        Proveedor proveedor = (Proveedor) listaProveedores.get(tblProveedoresTodos.getModel().getValueAt(fila, 0));
                        lblProveedorID.setText(String.valueOf(proveedor.getId()));
                        if (proveedor.getEstado().getDescripcion().equals("ACTIVO")) {
                            lblProveedorEstado.setForeground(Color.green);
                            lblProveedorEstado.setText(proveedor.getEstado().getDescripcion());
                        } else {
                            lblProveedorEstado.setForeground(Color.red);
                            lblProveedorEstado.setText(proveedor.getEstado().getDescripcion());
                        }
                        idProveedor = proveedor.getId();
                        lblProveedorNombre.setText(proveedor.getRazonsocial());
                        lblProveedorDireccion.setText(proveedor.getDireccion());
                        lblProveedorTelefono.setText(proveedor.getTelefono());
                        lblProveedorEmail.setText(proveedor.getMail());
                    }
                }
            });
            try {
                listaPreciosAlqui = ctrlalquiler.traerTodos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }
            if (listaPreciosAlqui != null) {
                DefaultTableModel dtmPrcioAlqui = new DefaultTableModel(new Object[]{"Precio", "Fecha", "Condicion"}, 0) {
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return false;
                    }
                };
                for (Iterator it = listaPreciosAlqui.entrySet().iterator(); it.hasNext();) {
                    ConcurrentHashMap.Entry<?, ?> entry = (ConcurrentHashMap.Entry<?, ?>) it.next();
                    if (((Alquiler) entry.getValue()).isActivo()) {
                        precioAlqui = ((Alquiler) entry.getValue()).getPrecio();
                        dtmPrcioAlqui.addRow(new Object[]{"$ " + ((Alquiler) entry.getValue()).getPrecio(), fechaformat.format(((Alquiler) entry.getValue()).getFecha()), "Activo"});
                    } else {
                        dtmPrcioAlqui.addRow(new Object[]{"$ " + ((Alquiler) entry.getValue()).getPrecio(), fechaformat.format(((Alquiler) entry.getValue()).getFecha()), "No Activo"});
                    }
                }
                tblPreciosAlqui.setModel(dtmPrcioAlqui);
                FormatoTabla ft = new FormatoTabla();
                tblPreciosAlqui.setDefaultRenderer(Object.class, ft);
            }
            if (listaArticulos != null) {
                stock();
                tabStock.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent me) {
                        stock();
                    }
                });
            }

            try {
                listaClientes = ctrlclientes.TraerTodos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }
            if (listaClientes != null) {
                DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Codigo", "Apellido", "Nombre", "Direccion"}, 0) {
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return false;
                    }
                };
                for (Iterator it = listaClientes.entrySet().iterator(); it.hasNext();) {
                    ConcurrentHashMap.Entry<?, ?> entry = (ConcurrentHashMap.Entry<?, ?>) it.next();
                    if (((Cliente) entry.getValue()).getApellido() != null) {
                        dtm.addRow(new Object[]{entry.getKey(), ((Cliente) entry.getValue()).getApellido().toUpperCase(), ((Cliente) entry.getValue()).getNombre(), ((Cliente) entry.getValue()).getDireccion()});
                    } else {
                        dtm.addRow(new Object[]{entry.getKey(), ((Cliente) entry.getValue()).getApellido(), ((Cliente) entry.getValue()).getNombre(), ((Cliente) entry.getValue()).getDireccion()});
                    }
                }
                tblClientestodos.setModel(dtm);
                tblClientestodos.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        SimpleDateFormat fechaformat = new SimpleDateFormat("dd/MM/yyyy");
                        int fila = tblClientestodos.rowAtPoint(e.getPoint());
                        int columna = tblClientestodos.columnAtPoint(e.getPoint());
                        if ((fila > -1) && (columna > -1)) {
                            filaSeleccionadaCliente = fila;
                            Cliente nombre = (Cliente) listaClientes.get(tblClientestodos.getModel().getValueAt(fila, 0));
                            lblClientesCodigo.setText(String.valueOf(nombre.getIdCliente()));
                            if (nombre.getEstado().getDescripcion().equals("ACTIVO")) {
                                lblClienteEstado.setForeground(Color.green);
                                lblClienteEstado.setText(nombre.getEstado().getDescripcion());
                            } else {
                                lblClienteEstado.setForeground(Color.red);
                                lblClienteEstado.setText(nombre.getEstado().getDescripcion());
                            }
                            idCliente = nombre.getIdCliente();
                            if (nombre.getApellido() != null) {
                                lblClientesNombre.setText(nombre.getApellido() + ", " + nombre.getNombre());
                            }
                            lblClientesDireccion.setText(nombre.getDireccion());
                            lblClientesDNI.setText(nombre.getDni());
                            lblClientesTelefono.setText(nombre.getTelefono());
                            if (nombre.getFechanac() != null) {
                                lblClientesFechaNac.setText(fechaformat.format(nombre.getFechanac()));
                            }
                            lblClientesMail.setText(nombre.getMail());
                            lblClientesNotas.setText(nombre.getNotas());
                            if (listaClientesCumple.containsKey(nombre.getIdCliente())) {
                                lblClientesCumple.setText("Hoy es su cumpleaños");
                                lblClientesCumple.setForeground(Color.blue);
                            } else {
                                lblClientesCumple.setText("");
                            }
                        }
                    }
                });
            }
            if (listaUsuarios != null) {
                DefaultTableModel dtmuser = new DefaultTableModel(new Object[]{"Codigo", "Nombre", "Rol"}, 0) {
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return false;
                    }
                };
                String Rol;
                for (Iterator it = listaUsuarios.entrySet().iterator(); it.hasNext();) {
                    ConcurrentHashMap.Entry<?, ?> entry = (ConcurrentHashMap.Entry<?, ?>) it.next();
                    if (((Usuario) entry.getValue()).getRol() == 1) {
                        Rol = "Administrador";
                    } else {
                        Rol = "Operador";
                    }
                    dtmuser.addRow(new Object[]{entry.getKey(), ((Usuario) entry.getValue()).getNombre(), Rol});

                }
                tblUsuariosTodos.setModel(dtmuser);
            }
        } else {
            TabDevoluciones.setEnabledAt(TabDevoluciones.indexOfTab("USUARIOS"), false);
            TabDevoluciones.setEnabledAt(TabDevoluciones.indexOfTab("PROVEEDORES"), false);
            TabDevoluciones.setEnabledAt(TabDevoluciones.indexOfTab("CLIENTES"), false);
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
        btnPeliculasFitro = new javax.swing.ButtonGroup();
        btnAlquilerPago = new javax.swing.ButtonGroup();
        btnVentaPago = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        TabDevoluciones = new javax.swing.JTabbedPane();
        tabVenta = new javax.swing.JPanel();
        panelVentas = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
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
        btnVentasBuscarCli = new javax.swing.JButton();
        lblNombreCliente = new javax.swing.JLabel();
        lblCodigoCliente = new javax.swing.JLabel();
        lblCodigoClientellenar = new javax.swing.JLabel();
        lblNombreClientellenar = new javax.swing.JLabel();
        lblVentasCumple = new javax.swing.JLabel();
        rdbVentaContado = new javax.swing.JRadioButton();
        rdbVentaCtaCte = new javax.swing.JRadioButton();
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
        btnVentasProcesar = new javax.swing.JButton();
        tabAlquiler = new javax.swing.JPanel();
        PanelAlquiler = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        txtAlquilerPV = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        txtAlquilerNumero = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        txtAlquilerVendedor = new javax.swing.JTextField();
        cbxAlquilerVendedores = new javax.swing.JComboBox<>();
        jLabel77 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        txtAlquilerSaldoCC = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        txtAlquilerFecha = new javax.swing.JFormattedTextField();
        jPanel17 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        txtAlquilerCodigoCli = new javax.swing.JTextField();
        btnAlquilerBuscarCli = new javax.swing.JButton();
        lblCodigoClientealq = new javax.swing.JLabel();
        lblNombreClientealq = new javax.swing.JLabel();
        lblCodigoClientellenaralq = new javax.swing.JLabel();
        lblNombreClientellenaralq = new javax.swing.JLabel();
        lblAlquilerCumple = new javax.swing.JLabel();
        rdbAlquilerCtaCte = new javax.swing.JRadioButton();
        rdbAlquilerContado = new javax.swing.JRadioButton();
        jPanel18 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        txtAlquilerCodArti = new javax.swing.JTextField();
        btnAlquilerBucarArti = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblAlquilerTablaAlqui = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        txtAlquilerDevoluciones = new javax.swing.JTextField();
        txtAlquilerRecargos = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        txtAlquilerTotal = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        btnAlquilerProcesar = new javax.swing.JButton();
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
        lblClientesCumple = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblClientestodos = new javax.swing.JTable();
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
        jLabel95 = new javax.swing.JLabel();
        SpinArticulosCantidad = new javax.swing.JSpinner();
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
        jScrollBar1 = new javax.swing.JScrollBar();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblArticulosTodos = new javax.swing.JTable();
        tabPeliculas = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        SpinPeliculasPrecio = new javax.swing.JSpinner();
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
        lblPeliculasCodigo = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        lblPeliculasDescripcion = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        lblPeliculasPrecio = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        lblPeliculasCosto = new javax.swing.JLabel();
        lblPeliculasProveedor = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        lblPeliculasFechaCompra = new javax.swing.JLabel();
        btnPeliculasModificar = new javax.swing.JButton();
        btnPeliculasElminar = new javax.swing.JButton();
        lblPeliculasActivo = new javax.swing.JLabel();
        tabProveedores = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtProveedoresNombre = new javax.swing.JTextField();
        txtProveedoresDireccion = new javax.swing.JTextField();
        txtProveedoresTelefono = new javax.swing.JTextField();
        txtProveedoresEmail = new javax.swing.JTextField();
        btnProveedoresAceptar = new java.awt.Button();
        jLabel43 = new javax.swing.JLabel();
        lblProveedorNombre = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        lblProveedorDireccion = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        lblProveedorTelefono = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        lblProveedorEmail = new javax.swing.JLabel();
        lblProveedorEstado = new javax.swing.JLabel();
        btnProveedorModificar = new javax.swing.JButton();
        btnProveedorEliminar = new javax.swing.JButton();
        jLabel54 = new javax.swing.JLabel();
        lblProveedorID = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProveedoresTodos = new javax.swing.JTable();
        tabStock = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        txtStockCodArti = new javax.swing.JTextField();
        btnStockBuscar = new javax.swing.JButton();
        jLabel94 = new javax.swing.JLabel();
        spinStockCantidad = new javax.swing.JSpinner();
        btnStockProcesar = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tblStock = new javax.swing.JTable();
        tabPrecioAlquiler = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        spinPrecioalqui = new javax.swing.JSpinner();
        btnCambiarPrecio = new javax.swing.JButton();
        jLabel91 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tblPreciosAlqui = new javax.swing.JTable();
        jLabel92 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDevoluciones = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        tabCaja = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        lblCajaFechaAper = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        lblCajaCodigoVendedor = new javax.swing.JLabel();
        lblCajaNomVendedor = new javax.swing.JLabel();
        lblCajaEstado = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        txtCajaMontoAper = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblCajaEgresos = new javax.swing.JTable();
        jLabel98 = new javax.swing.JLabel();
        txtCajaMontoEgre = new javax.swing.JSpinner();
        txtCajaConceptoEgre = new javax.swing.JTextField();
        jLabel100 = new javax.swing.JLabel();
        btnCajaModificarEgre = new javax.swing.JButton();
        btnCajaanadirEgre = new javax.swing.JButton();
        btnCajaEliminarEgre = new javax.swing.JButton();
        jLabel83 = new javax.swing.JLabel();
        txtCajaTotalEgre = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblCajaIngresos = new javax.swing.JTable();
        btnCajaAnadirIngre = new javax.swing.JButton();
        btnCajaModificarIngre = new javax.swing.JButton();
        btnCajaEliminarIngre = new javax.swing.JButton();
        jLabel82 = new javax.swing.JLabel();
        txtCajaTotalIngre = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        lblCajaFechaCierre = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        lblCajaCodigoVendedorCierre = new javax.swing.JLabel();
        lblCajaNomVendedorCierre = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        txtCajaRetiro = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        txtCajaMontoCierre = new javax.swing.JTextField();
        btnCajaCierre = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        txtCajaConceptoIngr = new javax.swing.JTextField();
        txtCajaMontoIngr = new javax.swing.JSpinner();
        btnCajaAbre = new javax.swing.JButton();
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
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/SalesForce.png"))); // NOI18N

        jLabel44.setText("Ticket");

        txtVentasPV.setEditable(false);
        txtVentasPV.setText("0000");

        jLabel45.setText("-");

        txtVentasNumero.setEditable(false);
        txtVentasNumero.setText("00000000");

        jLabel46.setText("Vendedor:");

        txtVentasVendedor.setText("00");

        cbxVentasVendedores.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxVentasVendedoresItemStateChanged(evt);
            }
        });

        jLabel66.setText("Clientes:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel66)
                    .addGroup(jPanel9Layout.createSequentialGroup()
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
                        .addComponent(cbxVentasVendedores, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 81, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txtVentasPV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45)
                    .addComponent(txtVentasNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46)
                    .addComponent(txtVentasVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxVentasVendedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel66))
        );

        jLabel47.setText("Saldo CC");

        txtVentasSaldoCC.setEditable(false);
        txtVentasSaldoCC.setText("0000");

        jLabel48.setText("Fecha:");

        txtVentasFecha.setEditable(false);
        txtVentasFecha.setText("__/__/____");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtVentasSaldoCC, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jLabel49.setText("Codigo:");

        txtVentasCodigoCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVentasCodigoCliActionPerformed(evt);
            }
        });
        txtVentasCodigoCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtVentasCodigoCliKeyTyped(evt);
            }
        });

        btnVentasBuscarCli.setText("Buscar Cliente");
        btnVentasBuscarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasBuscarCliActionPerformed(evt);
            }
        });

        rdbVentaContado.setText("Contado");

        rdbVentaCtaCte.setText("Cuenta Corriente");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtVentasCodigoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVentasBuscarCli))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(rdbVentaContado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdbVentaCtaCte)))
                .addGap(42, 42, 42)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(lblNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNombreClientellenar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(lblCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCodigoClientellenar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblVentasCumple, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCodigoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                            .addComponent(lblCodigoClientellenar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblVentasCumple, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNombreClientellenar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel49)
                            .addComponent(txtVentasCodigoCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnVentasBuscarCli))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdbVentaCtaCte)
                            .addComponent(rdbVentaContado))
                        .addContainerGap())))
        );

        jLabel65.setText("Codigo Articulo: ");

        txtVentasCodArti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtVentasCodArtiKeyTyped(evt);
            }
        });

        btnVentasBucarArti.setText("Buscar Articulo");
        btnVentasBucarArti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasBucarArtiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel65)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVentasCodArti, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(btnVentasBucarArti)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
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

        txtVentasTotal.setEditable(false);

        jLabel53.setText("TOTAL A COBRAR");

        btnVentasProcesar.setText("Procesar");
        btnVentasProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasProcesarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtVentasDevoluciones)
                            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtVentasRecargos, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 494, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtVentasTotal)
                            .addComponent(jLabel53))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVentasProcesar, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(btnVentasProcesar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelVentasLayout = new javax.swing.GroupLayout(panelVentas);
        panelVentas.setLayout(panelVentasLayout);
        panelVentasLayout.setHorizontalGroup(
            panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVentasLayout.createSequentialGroup()
                .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVentasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelVentasLayout.createSequentialGroup()
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelVentasLayout.setVerticalGroup(
            panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(51, 51, 51))
        );

        javax.swing.GroupLayout tabVentaLayout = new javax.swing.GroupLayout(tabVenta);
        tabVenta.setLayout(tabVentaLayout);
        tabVentaLayout.setHorizontalGroup(
            tabVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabVentaLayout.setVerticalGroup(
            tabVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabDevoluciones.addTab("VENTA", tabVenta);

        jLabel55.setText("Ticket");

        txtAlquilerPV.setEditable(false);
        txtAlquilerPV.setText("0000");

        jLabel56.setText("-");

        txtAlquilerNumero.setEditable(false);
        txtAlquilerNumero.setText("00000000");

        jLabel57.setText("Vendedor:");

        txtAlquilerVendedor.setText("00");

        cbxAlquilerVendedores.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxAlquilerVendedoresItemStateChanged(evt);
            }
        });

        jLabel77.setText("Clientes:");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel77)
                    .addGroup(jPanel15Layout.createSequentialGroup()
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
                        .addComponent(cbxAlquilerVendedores, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(txtAlquilerPV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56)
                    .addComponent(txtAlquilerNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57)
                    .addComponent(txtAlquilerVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxAlquilerVendedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel77))
        );

        jLabel58.setText("Saldo CC");

        txtAlquilerSaldoCC.setEditable(false);
        txtAlquilerSaldoCC.setText("0000");

        jLabel59.setText("Fecha:");

        txtAlquilerFecha.setEditable(false);
        txtAlquilerFecha.setText("__/__/____");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel58)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtAlquilerSaldoCC, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jLabel60.setText("Codigo:");

        txtAlquilerCodigoCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlquilerCodigoCliActionPerformed(evt);
            }
        });
        txtAlquilerCodigoCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAlquilerCodigoCliKeyTyped(evt);
            }
        });

        btnAlquilerBuscarCli.setText("Buscar Cliente");
        btnAlquilerBuscarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlquilerBuscarCliActionPerformed(evt);
            }
        });

        rdbAlquilerCtaCte.setText("Cuenta Corriente");

        rdbAlquilerContado.setText("Contado");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(rdbAlquilerContado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdbAlquilerCtaCte))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAlquilerCodigoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAlquilerBuscarCli)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(lblNombreClientealq, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNombreClientellenaralq, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(lblCodigoClientealq, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCodigoClientellenaralq, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAlquilerCumple, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel60)
                            .addComponent(txtAlquilerCodigoCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAlquilerBuscarCli))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(rdbAlquilerCtaCte))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(rdbAlquilerContado))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCodigoClientealq, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCodigoClientellenaralq, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lblAlquilerCumple, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreClientealq, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNombreClientellenaralq, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(4, 4, 4))
        );

        jLabel78.setText("Codigo Articulo: ");

        txtAlquilerCodArti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAlquilerCodArtiKeyTyped(evt);
            }
        });

        btnAlquilerBucarArti.setText("Buscar Pelicula");
        btnAlquilerBucarArti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlquilerBucarArtiActionPerformed(evt);
            }
        });

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

        txtAlquilerTotal.setEditable(false);

        jLabel64.setText("TOTAL A COBRAR");

        btnAlquilerProcesar.setText("Procesar");
        btnAlquilerProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlquilerProcesarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtAlquilerDevoluciones)
                    .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtAlquilerRecargos, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 500, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtAlquilerTotal)
                    .addComponent(jLabel64))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAlquilerProcesar, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(btnAlquilerProcesar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel78)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAlquilerCodArti, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(btnAlquilerBucarArti)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane9)
            .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78)
                    .addComponent(txtAlquilerCodArti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAlquilerBucarArti))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout PanelAlquilerLayout = new javax.swing.GroupLayout(PanelAlquiler);
        PanelAlquiler.setLayout(PanelAlquilerLayout);
        PanelAlquilerLayout.setHorizontalGroup(
            PanelAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(PanelAlquilerLayout.createSequentialGroup()
                .addGroup(PanelAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAlquilerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelAlquilerLayout.setVerticalGroup(
            PanelAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAlquilerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tabAlquilerLayout = new javax.swing.GroupLayout(tabAlquiler);
        tabAlquiler.setLayout(tabAlquilerLayout);
        tabAlquilerLayout.setHorizontalGroup(
            tabAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabAlquilerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelAlquiler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabAlquilerLayout.setVerticalGroup(
            tabAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabAlquilerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelAlquiler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabDevoluciones.addTab("ALQUILER", tabAlquiler);

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2))
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
                                    .addComponent(jdcClientesFechaNac, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                .addGap(75, 75, 75)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtClientesEmail)
                                    .addComponent(txtClientesDireccion)
                                    .addComponent(txtClientesTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(50, 50, 50))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(btnClientesAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(33, 33, 33)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                .addComponent(btnClientesAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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

        btnClientesBuscar.setText("Buscar Cliente");
        btnClientesBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdbClientesCodigo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbClientesApellido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClientesBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbClientesCodigo)
                    .addComponent(rdbClientesApellido)
                    .addComponent(btnClientesBuscar))
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jLabel34.setText("Codigo: ");

        jLabel35.setText("Estado: ");

        jLabel36.setText("Nombre: ");

        jLabel37.setText("Diección:");

        jLabel38.setText("Telefono: ");

        jLabel39.setText("DNI:");

        jLabel40.setText("Fecha de nacimiento: ");

        jLabel41.setText("Mail:");

        jLabel42.setText("Notas: ");

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
        jScrollPane5.setViewportView(tblClientestodos);

        javax.swing.GroupLayout tabClientesLayout = new javax.swing.GroupLayout(tabClientes);
        tabClientes.setLayout(tabClientesLayout);
        tabClientesLayout.setHorizontalGroup(
            tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabClientesLayout.createSequentialGroup()
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabClientesLayout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(btnClintesModificar)
                                .addGap(336, 336, 336)
                                .addComponent(btnClientesElminar)
                                .addGap(26, 26, 26))
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tabClientesLayout.createSequentialGroup()
                                        .addComponent(jLabel42)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblClientesNotas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                                .addComponent(lblClientesNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                                        .addComponent(lblClientesMail, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblClientesCumple, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblClientesId, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabClientesLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtClientesBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tabClientesLayout.setVerticalGroup(
            tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabClientesLayout.createSequentialGroup()
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtClientesBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabClientesLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(lblClientesId, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabClientesLayout.createSequentialGroup()
                                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(tabClientesLayout.createSequentialGroup()
                                        .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(tabClientesLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel34)
                                                    .addComponent(lblClientesCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabClientesLayout.createSequentialGroup()
                                        .addGap(84, 84, 84)
                                        .addComponent(lblClientesFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel42)
                                    .addComponent(lblClientesNotas, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                    .addComponent(lblClientesCumple, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(tabClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnClintesModificar)
                                    .addComponent(btnClientesElminar)))))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
                .addGap(88, 88, 88))
        );

        TabDevoluciones.addTab("CLIENTES", tabClientes);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 303, Short.MAX_VALUE)
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        TabDevoluciones.addTab("USUARIOS", tabUsuarios);

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

        jLabel95.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel95.setText("Cantidad:");

        SpinArticulosCantidad.setModel(new javax.swing.SpinnerNumberModel());
        SpinArticulosCantidad.setEditor(new javax.swing.JSpinner.NumberEditor(SpinArticulosCantidad, "0.00"));

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
                            .addComponent(jLabel25)
                            .addComponent(jLabel95))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SpinArticulosCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxArticulosProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGap(48, 48, 48)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(spinArticulosCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(SpinArticulosPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel95)
                    .addComponent(SpinArticulosCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(cbxArticulosProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnArticulosAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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

        btnArticulosBuscar.setText("Buscar Articulo");
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

        javax.swing.GroupLayout tabArticulosLayout = new javax.swing.GroupLayout(tabArticulos);
        tabArticulos.setLayout(tabArticulosLayout);
        tabArticulosLayout.setHorizontalGroup(
            tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabArticulosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabArticulosLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(btnArticulosModificar)
                        .addGap(332, 332, 332)
                        .addComponent(btnArticulosElminar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(tabArticulosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabArticulosLayout.createSequentialGroup()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(368, 368, 368))
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
                                            .addComponent(lblArticulosFechaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                            .addComponent(btnArticulosBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(0, 0, Short.MAX_VALUE)))))))
        );
        tabArticulosLayout.setVerticalGroup(
            tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabArticulosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabArticulosLayout.createSequentialGroup()
                        .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtArticulosBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabArticulosLayout.createSequentialGroup()
                                .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnArticulosBuscar, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(4, 4, 4)))
                        .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabArticulosLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabArticulosLayout.createSequentialGroup()
                                .addComponent(lblArticulosActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblArticulosPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblArticulosCosto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(4, 4, 4))
                            .addGroup(tabArticulosLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblArticulosCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel29)
                                    .addComponent(lblArticulosDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblArticulosFechaCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblArticulosProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnArticulosModificar)
                            .addComponent(btnArticulosElminar))
                        .addGap(1, 1, 1))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE))
                .addGap(78, 78, 78))
        );

        TabDevoluciones.addTab("ARTICULOS", tabArticulos);

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

        SpinPeliculasPrecio.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 1.0f));
        SpinPeliculasPrecio.setEditor(new javax.swing.JSpinner.NumberEditor(SpinPeliculasPrecio, "0.00"));

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
                            .addComponent(SpinPeliculasPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(25, Short.MAX_VALUE))
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
                    .addComponent(SpinPeliculasPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69)
                    .addComponent(cbxPeliculasProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 295, Short.MAX_VALUE)
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

        btnPeliculasBuscar.setText("Buscar Pelicula");
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

        lblPeliculasActivo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        javax.swing.GroupLayout tabPeliculasLayout = new javax.swing.GroupLayout(tabPeliculas);
        tabPeliculas.setLayout(tabPeliculasLayout);
        tabPeliculasLayout.setHorizontalGroup(
            tabPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPeliculasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabPeliculasLayout.createSequentialGroup()
                        .addComponent(jLabel71)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPeliculasCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPeliculasActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(tabPeliculasLayout.createSequentialGroup()
                        .addGroup(tabPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabPeliculasLayout.createSequentialGroup()
                                .addComponent(jLabel70)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPeliculasBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdbPeliculasCodigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdbPeliculasTitulo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPeliculasBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(tabPeliculasLayout.createSequentialGroup()
                        .addGroup(tabPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(tabPeliculasLayout.createSequentialGroup()
                                .addComponent(jLabel76)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPeliculasFechaCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(tabPeliculasLayout.createSequentialGroup()
                                .addComponent(jLabel72)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPeliculasDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(tabPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabPeliculasLayout.createSequentialGroup()
                                .addComponent(jLabel73)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPeliculasPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel74)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPeliculasCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(tabPeliculasLayout.createSequentialGroup()
                                .addComponent(jLabel75)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPeliculasProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(tabPeliculasLayout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(btnPeliculasModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPeliculasElminar)
                        .addGap(88, 88, 88))))
        );
        tabPeliculasLayout.setVerticalGroup(
            tabPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPeliculasLayout.createSequentialGroup()
                .addGroup(tabPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tabPeliculasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(tabPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPeliculasBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdbPeliculasCodigo)
                            .addComponent(rdbPeliculasTitulo)
                            .addComponent(btnPeliculasBuscar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPeliculasCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblPeliculasActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(tabPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPeliculasPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPeliculasCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(tabPeliculasLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(tabPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel72)
                                    .addComponent(lblPeliculasDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPeliculasFechaCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPeliculasProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabPeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPeliculasModificar)
                            .addComponent(btnPeliculasElminar))))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        TabDevoluciones.addTab("PELICULAS", tabPeliculas);

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
                                    .addComponent(txtProveedoresEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProveedoresDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProveedoresNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProveedoresTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(btnProveedoresAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                    .addComponent(txtProveedoresTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtProveedoresEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 335, Short.MAX_VALUE)
                .addComponent(btnProveedoresAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel43.setText("Nombre:");

        jLabel90.setText("Direccion: ");

        jLabel97.setText("Telefono");

        jLabel99.setText("E-mail:");

        lblProveedorEstado.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        btnProveedorModificar.setText("Modificar Proveedor");
        btnProveedorModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorModificarActionPerformed(evt);
            }
        });

        btnProveedorEliminar.setText("Eliminar Proveedor");

        jLabel54.setText("Codigo:");

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
        jScrollPane3.setViewportView(tblProveedoresTodos);

        javax.swing.GroupLayout tabProveedoresLayout = new javax.swing.GroupLayout(tabProveedores);
        tabProveedores.setLayout(tabProveedoresLayout);
        tabProveedoresLayout.setHorizontalGroup(
            tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabProveedoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabProveedoresLayout.createSequentialGroup()
                        .addGroup(tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(tabProveedoresLayout.createSequentialGroup()
                                .addComponent(jLabel90)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblProveedorDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(tabProveedoresLayout.createSequentialGroup()
                                .addComponent(jLabel97)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnProveedorModificar)
                                    .addComponent(lblProveedorTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(tabProveedoresLayout.createSequentialGroup()
                                .addComponent(jLabel54)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblProveedorID, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel43)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblProveedorNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabProveedoresLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addComponent(btnProveedorEliminar)
                                .addGap(133, 133, 133))
                            .addGroup(tabProveedoresLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblProveedorEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(tabProveedoresLayout.createSequentialGroup()
                                        .addComponent(jLabel99)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblProveedorEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(tabProveedoresLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        tabProveedoresLayout.setVerticalGroup(
            tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabProveedoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tabProveedoresLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblProveedorNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel99)
                            .addGroup(tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblProveedorID, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblProveedorEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabProveedoresLayout.createSequentialGroup()
                                .addGroup(tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel90)
                                    .addComponent(lblProveedorDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel97)
                                    .addComponent(lblProveedorTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblProveedorEstado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnProveedorModificar)
                            .addComponent(btnProveedorEliminar))))
                .addContainerGap(83, Short.MAX_VALUE))
        );

        TabDevoluciones.addTab("PROVEEDORES", tabProveedores);

        jLabel93.setText("Codigo Articulo:");

        btnStockBuscar.setText("Buscar Articulo");

        jLabel94.setText("Cargar Cantidad actual:");

        btnStockProcesar.setText("Procesar");
        btnStockProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStockProcesarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel93)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStockCodArti, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(btnStockBuscar))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel94)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinStockCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(btnStockProcesar)))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel93)
                    .addComponent(txtStockCodArti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStockBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel94)
                    .addComponent(spinStockCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(btnStockProcesar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblStock.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane15.setViewportView(tblStock);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tabStockLayout = new javax.swing.GroupLayout(tabStock);
        tabStock.setLayout(tabStockLayout);
        tabStockLayout.setHorizontalGroup(
            tabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabStockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabStockLayout.setVerticalGroup(
            tabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabStockLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        TabDevoluciones.addTab("STOCK", tabStock);

        jLabel80.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel80.setText("Precio de Alquileres");

        jLabel89.setText("Precio a Cambiar:");

        spinPrecioalqui.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 1.0f));

        btnCambiarPrecio.setText("Cambiar");
        btnCambiarPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarPrecioActionPerformed(evt);
            }
        });

        jLabel91.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel89)
                                .addGap(18, 18, 18)
                                .addComponent(spinPrecioalqui, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(btnCambiarPrecio))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 26, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel91)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel89)
                    .addComponent(spinPrecioalqui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCambiarPrecio)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblPreciosAlqui.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane14.setViewportView(tblPreciosAlqui);

        jLabel92.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel92.setText("Historico de precios");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jLabel92, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tabPrecioAlquilerLayout = new javax.swing.GroupLayout(tabPrecioAlquiler);
        tabPrecioAlquiler.setLayout(tabPrecioAlquilerLayout);
        tabPrecioAlquilerLayout.setHorizontalGroup(
            tabPrecioAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPrecioAlquilerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        tabPrecioAlquilerLayout.setVerticalGroup(
            tabPrecioAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabPrecioAlquilerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabPrecioAlquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        TabDevoluciones.addTab("PRECIO ALQUILER", tabPrecioAlquiler);

        tblDevoluciones.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblDevoluciones);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 239, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap(175, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabDevoluciones.addTab("DEVOLUCIONES", jPanel24);

        jLabel50.setText("Datos Apertura");

        jLabel61.setText("Fecha");

        jLabel79.setText("Operador");

        lblCajaEstado.setBackground(new java.awt.Color(0, 0, 0));
        lblCajaEstado.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        jLabel81.setText("Apertura de caja:");

        tblCajaEgresos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane13.setViewportView(tblCajaEgresos);

        jLabel98.setText("Monto:");

        txtCajaMontoEgre.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 1.0f));
        txtCajaMontoEgre.setEditor(new javax.swing.JSpinner.NumberEditor(txtCajaMontoEgre, "0.00"));

        jLabel100.setText("Concepto:");

        btnCajaModificarEgre.setText("Modificar");
        btnCajaModificarEgre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCajaModificarEgreActionPerformed(evt);
            }
        });

        btnCajaanadirEgre.setText("Añadir");
        btnCajaanadirEgre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCajaanadirEgreActionPerformed(evt);
            }
        });

        btnCajaEliminarEgre.setText("Eliminar");
        btnCajaEliminarEgre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCajaEliminarEgreActionPerformed(evt);
            }
        });

        jLabel83.setText("Total");

        txtCajaTotalEgre.setEditable(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCajaanadirEgre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCajaModificarEgre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCajaEliminarEgre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel83)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCajaTotalEgre, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel98)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCajaMontoEgre, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel100)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCajaConceptoEgre, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel98)
                    .addComponent(jLabel100)
                    .addComponent(txtCajaConceptoEgre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCajaMontoEgre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCajaanadirEgre)
                    .addComponent(btnCajaModificarEgre)
                    .addComponent(btnCajaEliminarEgre)
                    .addComponent(jLabel83)
                    .addComponent(txtCajaTotalEgre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tblCajaIngresos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane12.setViewportView(tblCajaIngresos);

        btnCajaAnadirIngre.setText("Añadir");
        btnCajaAnadirIngre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCajaAnadirIngreActionPerformed(evt);
            }
        });

        btnCajaModificarIngre.setText("Modificar");
        btnCajaModificarIngre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCajaModificarIngreActionPerformed(evt);
            }
        });

        btnCajaEliminarIngre.setText("Eliminar");
        btnCajaEliminarIngre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCajaEliminarIngreActionPerformed(evt);
            }
        });

        jLabel82.setText("Total");

        txtCajaTotalIngre.setEditable(false);

        jLabel86.setText("Datos Cierre");

        jLabel84.setText("Fecha");

        jLabel85.setText("Operador");

        jLabel87.setText("Retira");

        jLabel88.setText("Cierre de caja:");

        txtCajaMontoCierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCajaMontoCierreActionPerformed(evt);
            }
        });

        btnCajaCierre.setText("Cerrar Caja");
        btnCajaCierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCajaCierreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel84)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCajaFechaCierre, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel85)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCajaCodigoVendedorCierre, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCajaNomVendedorCierre, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jLabel87)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCajaRetiro, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                .addComponent(jLabel88)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCajaMontoCierre, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCajaCierre))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel88)
                            .addComponent(txtCajaMontoCierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCajaCierre)))
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblCajaFechaCierre, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel84)
                            .addComponent(jLabel86))
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCajaRetiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel87))
                        .addComponent(jLabel85)
                        .addComponent(lblCajaNomVendedorCierre, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblCajaCodigoVendedorCierre, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 30, Short.MAX_VALUE))
        );

        jLabel21.setText("Monto:");

        jLabel96.setText("Concepto:");

        txtCajaMontoIngr.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 1.0f));
        txtCajaMontoIngr.setEditor(new javax.swing.JSpinner.NumberEditor(txtCajaMontoIngr, "0.00"));

        btnCajaAbre.setText("Abrir Caja");
        btnCajaAbre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCajaAbreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabCajaLayout = new javax.swing.GroupLayout(tabCaja);
        tabCaja.setLayout(tabCajaLayout);
        tabCajaLayout.setHorizontalGroup(
            tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabCajaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabCajaLayout.createSequentialGroup()
                        .addGroup(tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(tabCajaLayout.createSequentialGroup()
                                    .addComponent(btnCajaAnadirIngre)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCajaModificarIngre)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCajaEliminarIngre)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel82)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCajaTotalIngre, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(50, 50, 50))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabCajaLayout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(jLabel21)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCajaMontoIngr, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel96)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCajaConceptoIngr, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(tabCajaLayout.createSequentialGroup()
                                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel61)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCajaFechaAper, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabCajaLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblCajaEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabCajaLayout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))
                    .addGroup(tabCajaLayout.createSequentialGroup()
                        .addGroup(tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabCajaLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel79)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCajaCodigoVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCajaNomVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(270, 270, 270)
                                .addComponent(jLabel81)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCajaMontoAper, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCajaAbre)))
                        .addContainerGap())))
        );
        tabCajaLayout.setVerticalGroup(
            tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabCajaLayout.createSequentialGroup()
                .addGroup(tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabCajaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCajaFechaAper, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel61)))
                    .addComponent(lblCajaEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCajaAbre, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel81)
                        .addComponent(txtCajaMontoAper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel79)
                            .addComponent(lblCajaCodigoVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblCajaNomVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tabCajaLayout.createSequentialGroup()
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jLabel96)
                            .addComponent(txtCajaConceptoIngr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCajaMontoIngr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addGroup(tabCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCajaAnadirIngre)
                            .addComponent(btnCajaModificarIngre)
                            .addComponent(btnCajaEliminarIngre)
                            .addComponent(txtCajaTotalIngre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel82))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        TabDevoluciones.addTab("CAJA", tabCaja);

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
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(TabDevoluciones, javax.swing.GroupLayout.PREFERRED_SIZE, 1004, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TabDevoluciones, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnPeliculasElminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeliculasElminarActionPerformed
        if (idPelicula != 0) {
            int resp = JOptionPane.showConfirmDialog(null, "¿Desea eliminar la pelicula " + ((Articulo) listaClientes.get(idCliente)).getDescripcion() + "?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (JOptionPane.YES_OPTION == resp) {
                try {
                    ctrlarticulos.eliminarArticulo(idPelicula);
                    DefaultTableModel dtm = (DefaultTableModel) tblPeliculasTodos.getModel();
                    dtm.removeRow(filaSeleccionadaPelicula);
                    idPelicula = 0;
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un articulo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPeliculasElminarActionPerformed

    private void btnPeliculasModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeliculasModificarActionPerformed
        if (idPelicula != 0) {
            ModificaArticulo modart = new ModificaArticulo(this, true, idPelicula);
            modart.setLocationRelativeTo(this);
            modart.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    Articulo modificado;
                    DefaultTableModel dtm = (DefaultTableModel) tblPeliculasTodos.getModel();
                    modificado = modart.getModificado();
                    if (modificado != null) {
                        listaArticulos.put(idPelicula, modificado);
                        //((Articulo) listaArticulos.get(modificado.getId())).setEstado(modificado.getEstado());
                        dtm.removeRow(filaSeleccionadaPelicula);
                        limpiarnfoArtciulos();
                        dtm.addRow(new Object[]{modificado.getId(), modificado.getDescripcion().toUpperCase(), modificado.getPrecio()});
                        limpiarDatosPeliculas();
                        limpiarnfoPeliculas();
                    }
                    idPelicula = 0;
                }
            });
            modart.show();
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un articulo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPeliculasModificarActionPerformed

    private void btnPeliculasBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeliculasBuscarActionPerformed
        int columnaABuscar = 0;
        if (rdbPeliculasCodigo.isSelected()) {
            columnaABuscar = 0;
        } else {
            columnaABuscar = 1;
        }
        trsFiltro = new TableRowSorter(tblPeliculasTodos.getModel());
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtPeliculasBuscar.getText().toUpperCase(), columnaABuscar));
        tblPeliculasTodos.setRowSorter(trsFiltro);
    }//GEN-LAST:event_btnPeliculasBuscarActionPerformed

    private void rdbPeliculasCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbPeliculasCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdbPeliculasCodigoActionPerformed

    private void cbxPeliculasProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxPeliculasProveedoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxPeliculasProveedoresActionPerformed

    private void btnPeliculasAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeliculasAceptarActionPerformed
        if (txtPeliculasDescripcion.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "codigo o descripcion de articulo vacio, complete los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            agregararticulo(1);
        }
    }//GEN-LAST:event_btnPeliculasAceptarActionPerformed

    private void btnUsuariosAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosAceptarActionPerformed
        int rol = 0;
        if (!txtUsuariosNombre.getText().equals("") && !txtUsuariosContraseña.getText().equals("")
                && !txtUsuariosRepContraseña.getText().equals("")) {
            if (!txtUsuariosContraseña.getText().equals(txtUsuariosRepContraseña.getText())) {
                JOptionPane.showMessageDialog(null, "Verifique las Contraseñas", "Security", JOptionPane.ERROR_MESSAGE);
            } else {
                if (chkUsuariosAdmin.isSelected()) {
                    rol = 1;
                } else {
                    rol = 2;
                }
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

        } else {
            JOptionPane.showMessageDialog(null, "Complete los Campos.", "Security", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUsuariosAceptarActionPerformed

    private void btnClientesElminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesElminarActionPerformed
        if (idCliente != 0) {
            int resp = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el cliente " + ((Cliente) listaClientes.get(idCliente)).getApellido() + ", " + ((Cliente) listaClientes.get(idCliente)).getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (JOptionPane.YES_OPTION == resp) {
                try {
                    ctrlclientes.EliminarCliente(idCliente);
                    DefaultTableModel dtm = (DefaultTableModel) tblClientestodos.getModel();
                    dtm.removeRow(filaSeleccionadaCliente);
                    idCliente = 0;
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnClientesElminarActionPerformed

    private void btnClintesModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClintesModificarActionPerformed
        if (idCliente != 0) {
            ModificaCliente modcli = new ModificaCliente(this, true, idCliente);
            modcli.setLocationRelativeTo(this);
            modcli.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    Cliente modificado;
                    DefaultTableModel dtm = (DefaultTableModel) tblClientestodos.getModel();
                    modificado = modcli.getModificado();
                    listaClientes.put(idCliente, modificado);
                    //((Cliente) listaClientes.get(modificado.getIdCliente())).setEstado(modificado.getEstado());
                    dtm.removeRow(filaSeleccionadaCliente);
                    dtm.addRow(new Object[]{modificado.getIdCliente(), modificado.getApellido().toUpperCase(), modificado.getNombre(), modificado.getDireccion()});
                    limpiarInfoClientes();
                    idCliente = 0;
                }
            });
            modcli.show();
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnClintesModificarActionPerformed

    private void btnAlquilerBucarArtiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlquilerBucarArtiActionPerformed
        BuscarArticulos buscaArticulos = new BuscarArticulos(this, true, listaArticulos, "alquiler");
        buscaArticulos.setLocationRelativeTo(this);
        buscaArticulos.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                boolean esta = false;
                if (buscaArticulos.getArticulo() != null) {
                    Articulo buscado = buscaArticulos.getArticulo();
                    if (listaDetalleAlqui.size() > 0) {
                        int i = 0;
                        while (i < listaDetalleAlqui.size()) {
                            if (((DetOperacion) listaDetalleAlqui.get(i)).getArticulo().getId() == buscado.getId()) {
                                i = listaDetalleAlqui.size();
                                esta = true;
                            }
                        }
                    }
                    if (!esta) {
                        DefaultTableModel dtm = (DefaultTableModel) tblAlquilerTablaAlqui.getModel();
                        listaDetalleAlqui.add(new DetOperacion(buscado, precioAlqui, 1));
                        totalAlquiler += precioAlqui * buscaArticulos.getCantidad();
                        dtm.addRow(new Object[]{buscado.getId(), buscado.getDescripcion().toUpperCase(), precioAlqui, buscaArticulos.getCantidad()});
                    } else {
                        JOptionPane.showMessageDialog(null, "El codigo de la pelicula ya fue ingresado", "Codigo repetido", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buscaArticulos.show();
    }//GEN-LAST:event_btnAlquilerBucarArtiActionPerformed

    private void txtAlquilerCodArtiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlquilerCodArtiKeyTyped
        boolean esta = false;
        char cTeclaPresionada = evt.getKeyChar();
        if (cTeclaPresionada == KeyEvent.VK_ENTER || cTeclaPresionada == KeyEvent.VK_TAB) {
            if (isNumeric(txtAlquilerCodArti.getText())) {
                Articulo agregar = (Articulo) listaArticulos.get(Integer.parseInt(txtAlquilerCodArti.getText()));
                if (agregar != null && agregar.getEstado().getId() != 2) {
                    if (agregar.getCategoria().getId() == 1) {
                        if (listaDetalleAlqui.size() > 0) {
                            int i = 0;
                            while (i < listaDetalleAlqui.size()) {
                                if (((DetOperacion) listaDetalleAlqui.get(i)).getArticulo().getId() == agregar.getId()) {
                                    i = listaDetalleAlqui.size();
                                    esta = true;
                                }
                                i++;
                            }
                        }
                        if (!esta) {
                            DefaultTableModel dtm = (DefaultTableModel) tblAlquilerTablaAlqui.getModel();
                            listaDetalleAlqui.add(new DetOperacion(agregar, precioAlqui, 1));
                            totalAlquiler += precioAlqui;
                            dtm.addRow(new Object[]{agregar.getId(), agregar.getDescripcion().toUpperCase(), agregar.getPrecio(), 1});
                            txtAlquilerCodArti.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "El codigo de la pelicula ya fue ingresado", "Codigo repetido", JOptionPane.ERROR_MESSAGE);
                            txtAlquilerCodArti.selectAll();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El codigo ingresado no pertenece a una pelicula", "Codigo no valido", JOptionPane.ERROR_MESSAGE);
                        txtAlquilerCodArti.selectAll();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ingrese un codigo de pelicula valido", "Pelicula no encontrada o Inactiva", JOptionPane.ERROR_MESSAGE);
                    txtAlquilerCodArti.selectAll();
                }
            } else {
                txtAlquilerCodArti.setText("");
            }
        }
    }//GEN-LAST:event_txtAlquilerCodArtiKeyTyped

    private void btnAlquilerBuscarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlquilerBuscarCliActionPerformed
        BuscarCliente buscacliente = new BuscarCliente(this, true, listaClientes);
        buscacliente.setLocationRelativeTo(this);
        buscacliente.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                Cliente buscado = buscacliente.getCliente();
                lblCodigoClientealq.setText("Codigo");
                lblNombreClientealq.setText("Nombre");
                if (buscado != null) {
                    lblNombreClientellenaralq.setText(buscado.getApellido() + ", " + buscado.getNombre());
                    lblCodigoClientellenaralq.setText(String.valueOf(buscado.getIdCliente()));
                    if (listaClientesCumple.containsKey(buscado.getIdCliente())) {
                        lblAlquilerCumple.setText("Hoy es su cumpleaños");
                        lblAlquilerCumple.setForeground(Color.blue);
                    } else {
                        lblAlquilerCumple.setText("");
                    }
                    clienteAlqui = buscado;
                }
            }
        });
        buscacliente.show();
    }//GEN-LAST:event_btnAlquilerBuscarCliActionPerformed

    private void txtAlquilerCodigoCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlquilerCodigoCliKeyTyped
        char cTeclaPresionada = evt.getKeyChar();
        Cliente aux = clienteAlqui;
        clienteAlqui = null;
        if (cTeclaPresionada == KeyEvent.VK_ENTER || cTeclaPresionada == KeyEvent.VK_TAB) {
            if (isNumeric(txtAlquilerCodigoCli.getText())) {
                clienteAlqui = (Cliente) listaClientes.get(Integer.parseInt(txtAlquilerCodigoCli.getText()));
                if (clienteAlqui != null && clienteAlqui.getEstado().getId() != 2) {
                    lblCodigoClientealq.setText("Codigo");
                    lblNombreClientealq.setText("Nombre");
                    lblNombreClientellenaralq.setText(clienteAlqui.getApellido() + ", " + clienteAlqui.getNombre());
                    lblCodigoClientellenaralq.setText(String.valueOf(clienteAlqui.getIdCliente()));
                    if (listaClientesCumple.containsKey(clienteAlqui.getIdCliente())) {
                        lblAlquilerCumple.setText("Hoy es su cumpleaños");
                        lblAlquilerCumple.setForeground(Color.blue);
                    } else {
                        lblAlquilerCumple.setText("");
                    }
                    txtAlquilerCodigoCli.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "ingrese un codigo de cliente valido", "Cliente no encontrado o Inactivo", JOptionPane.ERROR_MESSAGE);
                    clienteAlqui = aux;
                    txtAlquilerCodigoCli.selectAll();
                }
            } else {
                txtAlquilerCodigoCli.setText("");
            }
        }
    }//GEN-LAST:event_txtAlquilerCodigoCliKeyTyped

    private void txtAlquilerCodigoCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlquilerCodigoCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlquilerCodigoCliActionPerformed

    private void cbxAlquilerVendedoresItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxAlquilerVendedoresItemStateChanged
        Usuario seleccionado = (Usuario) cbxAlquilerVendedores.getSelectedItem();
        if (seleccionado.getId() < 10) {
            txtAlquilerVendedor.setText("0" + String.valueOf(seleccionado.getId()));
        } else {
            txtAlquilerVendedor.setText(String.valueOf(seleccionado.getId()));
        }
    }//GEN-LAST:event_cbxAlquilerVendedoresItemStateChanged

    private void btnVentasProcesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasProcesarActionPerformed
        String mp = "";
        if (clienteVenta == null) {
            JOptionPane.showMessageDialog(null, "seleccione un cliente antes de continuar", "Clente no encontrado", JOptionPane.ERROR_MESSAGE);
        } else {
            if (listaDetalleVenta.size() == 0) {
                JOptionPane.showMessageDialog(null, "Debe cargar al menos un articulo para continuar", "Sin articulos", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    // procesa ok
                    if (rdbVentaContado.isSelected()) {
                        mp = "Contado";
                    } else {
                        mp = "CtaCte";
                    }
                    ctrloperaciones.cargaOperacion(new CabOperacion(new TipoOperacion(2, "Venta"), clienteVenta, user, new java.util.Date(), ((Usuario) cbxVentasVendedores.getSelectedItem()).getId(), totalVentas), listaDetalleVenta, keyCajaAper, mp, Integer.valueOf(txtVentasNumero.getText()));
                    DefaultTableModel dtventas = new DefaultTableModel(new Object[]{"Codigo", "Descripcion", "Precio", "Cantidad", "total"}, 0) {
                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return false;
                        }
                    };
                    dtventas.addTableModelListener(new TableModelListener() {
                        @Override
                        public void tableChanged(TableModelEvent tme) {
                            if (tme.getType() == TableModelEvent.INSERT || tme.getType() == TableModelEvent.UPDATE || tme.getType() == TableModelEvent.DELETE) {
                                txtVentasTotal.setText(String.valueOf(totalVentas));
                            }
                        }
                    });
                    tblVentasTablaVentas.setModel(dtventas);
                    listaDetalleVenta.clear();
                    clienteVenta = null;
                    totalVentas = 0;
                    lblNombreCliente.setText("");
                    lblNombreClientellenar.setText("");
                    lblCodigoCliente.setText("");
                    lblCodigoClientellenar.setText("");
                    txtVentasTotal.setText("");
                    int num = Integer.valueOf(txtVentasNumero.getText());
                    txtVentasNumero.setText("%010d" + num + 1);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnVentasProcesarActionPerformed

    private void btnVentasBucarArtiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasBucarArtiActionPerformed
        BuscarArticulos buscaArticulos = new BuscarArticulos(this, true, listaArticulos, "venta");
        buscaArticulos.setLocationRelativeTo(this);
        buscaArticulos.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                if (buscaArticulos.getArticulo() != null) {
                    Articulo buscado = buscaArticulos.getArticulo();
                    listaDetalleVenta.add(new DetOperacion(buscado, buscado.getPrecio(), buscaArticulos.getCantidad()));
                    DefaultTableModel dtm = (DefaultTableModel) tblVentasTablaVentas.getModel();
                    totalVentas += buscado.getPrecio() * buscaArticulos.getCantidad();
                    dtm.addRow(new Object[]{buscado.getId(), buscado.getDescripcion().toUpperCase(), buscado.getPrecio(), buscaArticulos.getCantidad(), buscado.getPrecio() * buscaArticulos.getCantidad()});
                    buscado.setCantidad(buscado.getCantidad() - buscaArticulos.getCantidad());
                    listaArticulos.put(buscado.getId(), buscado);
                }
            }
        });
        buscaArticulos.show();
    }//GEN-LAST:event_btnVentasBucarArtiActionPerformed

    private void txtVentasCodArtiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVentasCodArtiKeyTyped
        char cTeclaPresionada = evt.getKeyChar();
        if (cTeclaPresionada == KeyEvent.VK_ENTER || cTeclaPresionada == KeyEvent.VK_TAB) {
            if (isNumeric(txtVentasCodArti.getText())) {
                Articulo agregar = (Articulo) listaArticulos.get(Integer.parseInt(txtVentasCodArti.getText()));
                if (agregar != null && agregar.getEstado().getId() != 2) {
                    if (agregar.getCantidad() >= 1) {
                        listaDetalleVenta.add(new DetOperacion(agregar, agregar.getPrecio(), 1));
                        DefaultTableModel dtm = (DefaultTableModel) tblVentasTablaVentas.getModel();
                        totalVentas += agregar.getPrecio();
                        dtm.addRow(new Object[]{agregar.getId(), agregar.getDescripcion().toUpperCase(), agregar.getPrecio(), 1, agregar.getPrecio()});
                        txtVentasCodArti.setText("");
                        agregar.setCantidad(agregar.getCantidad() - 1);
                        listaArticulos.put(agregar.getId(), agregar);
                    } else {
                        int resp = JOptionPane.showConfirmDialog(null, "Cantidad ingresada supera la existencia, ¿Continua?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (resp == JOptionPane.YES_OPTION) {
                            listaDetalleVenta.add(agregar);
                            DefaultTableModel dtm = (DefaultTableModel) tblVentasTablaVentas.getModel();
                            totalVentas += agregar.getPrecio();
                            dtm.addRow(new Object[]{agregar.getId(), agregar.getDescripcion().toUpperCase(), agregar.getPrecio(), 1, agregar.getPrecio()});
                            txtVentasCodArti.setText("");
                            agregar.setCantidad(agregar.getCantidad() - 1);
                            listaArticulos.put(agregar.getId(), agregar);
                        } else {
                            txtVentasCodArti.setText("");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ingrese un codigo de articulo valido", "Articulo no encontrado o Inactivo", JOptionPane.ERROR_MESSAGE);
                    txtVentasCodArti.selectAll();
                }
            } else {
                txtVentasCodArti.setText("");
            }
        }
    }//GEN-LAST:event_txtVentasCodArtiKeyTyped

    private void cbxVentasVendedoresItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxVentasVendedoresItemStateChanged
        Usuario seleccionado = (Usuario) cbxVentasVendedores.getSelectedItem();
        if (seleccionado.getId() < 10) {
            txtVentasVendedor.setText("0" + String.valueOf(seleccionado.getId()));
        } else {
            txtVentasVendedor.setText(String.valueOf(seleccionado.getId()));
        }
    }//GEN-LAST:event_cbxVentasVendedoresItemStateChanged

    private void btnCajaAbreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCajaAbreActionPerformed
        Date ahora = new Date();
        SimpleDateFormat fechaformat = new SimpleDateFormat("dd/MM/yyyy");

        float monto = 0;
        boolean abre = false;
        if (txtCajaMontoAper.getText().equals("")) {
            monto = 0;
            int resp = JOptionPane.showConfirmDialog(null, "¿Seguro que desea abri la caja en 0 pesos?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (resp == JOptionPane.YES_OPTION) {
                abre = true;
            }
        } else {
            if (isNumeric(txtCajaMontoAper.getText())) {
                monto = Float.valueOf(txtCajaMontoAper.getText());
                abre = true;
            } else {
                JOptionPane.showMessageDialog(null, "ingrese solo numeros para el monto de apertura", "Datos IncorrectoS", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (abre) {
            try {
                keyCajaAper = ctrlcaja.abrirCaja(ahora, user);
                DetCaja det = new DetCaja(monto, "Apertura " + fechaformat.format(ahora), new Caja(keyCajaAper), null, "I");
                int key = ctrlcaja.insertarDetalle(det);
                DefaultTableModel dtm = (DefaultTableModel) tblCajaIngresos.getModel();
                dtm.addRow(new Object[]{key, "Apertura " + fechaformat.format(ahora), monto});
                listaingregre.put(key, det);
                totalCaja = monto;
                btnCajaCierre.setEnabled(true);
                txtCajaMontoCierre.setEnabled(true);
                btnCajaAbre.setEnabled(false);
                txtCajaMontoAper.setEnabled(false);
                txtCajaMontoAper.setText("");
                txtCajaConceptoIngr.setEnabled(true);
                txtCajaMontoIngr.setEnabled(true);
                txtCajaConceptoEgre.setEnabled(true);
                txtCajaMontoEgre.setEnabled(true);
                btnCajaAnadirIngre.setEnabled(true);
                btnCajaModificarIngre.setEnabled(true);
                btnCajaEliminarIngre.setEnabled(true);
                btnCajaanadirEgre.setEnabled(true);
                btnCajaModificarEgre.setEnabled(true);
                btnCajaEliminarEgre.setEnabled(true);
                lblCajaEstado.setText("Caja Abierta");
                lblCajaEstado.setForeground(Color.green);
                TabDevoluciones.setEnabledAt(TabDevoluciones.indexOfTab("VENTA"), true);
                TabDevoluciones.setEnabledAt(TabDevoluciones.indexOfTab("ALQUILER"), true);
                lblCajaFechaAper.setText(fechaformat.format(ahora));
                if (user.getId() < 10) {
                    lblCajaCodigoVendedor.setText("0" + user.getId());
                } else {
                    lblCajaCodigoVendedor.setText(String.valueOf(user.getId()));
                }
                lblCajaNomVendedor.setText(user.getNombre());
                lblCajaFechaCierre.setText("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCajaAbreActionPerformed

    private void btnCajaCierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCajaCierreActionPerformed
        Date ahora = new Date();
        SimpleDateFormat fechaformat = new SimpleDateFormat("dd/MM/yyyy");
        float monto = 0;
        if (txtCajaMontoCierre.getText().equals("")) {
            monto = 0;
        } else {
            if (isNumeric(txtCajaMontoAper.getText())) {
                monto = Float.valueOf(txtCajaMontoAper.getText());
            } else {
                JOptionPane.showMessageDialog(null, "ingrese solo numeros para el monto de Cierre", "Datos IncorrectoS", JOptionPane.ERROR_MESSAGE);
            }
        }
        try {
            ctrlcaja.cerrarCaja(ahora);
            ctrlcaja.insertarDetalle(new DetCaja(monto, "Cierre " + fechaformat.format(ahora), new Caja(keyCajaAper), null, "Ë"));
            btnCajaAbre.setEnabled(true);
            txtCajaMontoAper.setEnabled(true);
            btnCajaCierre.setEnabled(false);
            txtCajaMontoCierre.setEnabled(false);
            lblCajaEstado.setText("Caja Cerrada");
            lblCajaEstado.setForeground(Color.red);
            txtCajaConceptoIngr.setEnabled(false);
            txtCajaMontoIngr.setEnabled(false);
            txtCajaConceptoEgre.setEnabled(false);
            txtCajaMontoEgre.setEnabled(false);
            btnCajaAnadirIngre.setEnabled(false);
            btnCajaModificarIngre.setEnabled(false);
            btnCajaEliminarIngre.setEnabled(false);
            btnCajaanadirEgre.setEnabled(false);
            btnCajaModificarEgre.setEnabled(false);
            btnCajaEliminarEgre.setEnabled(false);
            TabDevoluciones.setEnabledAt(TabDevoluciones.indexOfTab("VENTA"), false);
            TabDevoluciones.setEnabledAt(TabDevoluciones.indexOfTab("ALQUILER"), false);
            lblCajaFechaCierre.setText(fechaformat.format(ahora));
            if (user.getId() < 10) {
                lblCajaCodigoVendedorCierre.setText("0" + user.getId());
            } else {
                lblCajaCodigoVendedorCierre.setText(String.valueOf(user.getId()));
            }
            lblCajaNomVendedorCierre.setText(user.getNombre());
            lblCajaFechaCierre.setText(fechaformat.format(ahora));
            lblCajaFechaAper.setText("");
            listaingregre.clear();
            DefaultTableModel dtnIngre = (DefaultTableModel) tblCajaIngresos.getModel();
            DefaultTableModel dtnEgre = (DefaultTableModel) tblCajaEgresos.getModel();
            dtnIngre.getDataVector().removeAllElements();
            dtnIngre.fireTableDataChanged();
            dtnEgre.getDataVector().removeAllElements();
            dtnEgre.fireTableDataChanged();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCajaCierreActionPerformed

    private void btnCambiarPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarPrecioActionPerformed
        if ((float) spinPrecioalqui.getValue() == 0) {
            int resp = JOptionPane.showConfirmDialog(null, "Esta a punto de dejar el precio en 0, ¿continuar?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (resp == JOptionPane.YES_OPTION) {
                actualizaPrecioAlqui();
            }
        } else {
            actualizaPrecioAlqui();
        }
    }//GEN-LAST:event_btnCambiarPrecioActionPerformed

    private void btnStockProcesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStockProcesarActionPerformed
        Articulo arti = null;
        DefaultTableModel dtm = null;
        if (idArticuloStock != 0) {
            arti = (Articulo) listaArticulos.get(tblStock.getModel().getValueAt(filaSeleccionadaArticuloStock, 0));
            arti.setCantidad((Integer) spinStockCantidad.getValue());
            dtm = (DefaultTableModel) tblStock.getModel();
            try {
                ctrlarticulos.cargaStock(arti);
                ((Articulo) listaArticulos.get(arti.getId())).setCantidad(arti.getCantidad());
                dtm.removeRow(filaSeleccionadaArticuloStock);
                limpiarnfoArtciulos();
                dtm.addRow(new Object[]{arti.getId(), arti.getDescripcion().toUpperCase(), arti.getCantidad()});
                spinStockCantidad.setValue((Integer) 0);
                txtStockCodArti.setText("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "debe seleccionar un articulo", "Atencion", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnStockProcesarActionPerformed

    private void btnAlquilerProcesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlquilerProcesarActionPerformed
        String mp;
        if (clienteAlqui == null) {
            JOptionPane.showMessageDialog(null, "seleccione un cliente antes de continuar", "Clente no encontrado", JOptionPane.ERROR_MESSAGE);
        } else {
            if (listaDetalleAlqui.size() == 0) {
                JOptionPane.showMessageDialog(null, "Debe cargar al menos una pelicula para continuar", "Sin Pelicula", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    // procesa ok
                    if (rdbAlquilerContado.isSelected()) {
                        mp = "Contado";
                    } else {
                        mp = "CtaCte";
                    }
                    ctrloperaciones.cargaOperacion(new CabOperacion(new TipoOperacion(1, "Alquiler"), clienteAlqui, user, new java.util.Date(), ((Usuario) cbxVentasVendedores.getSelectedItem()).getId(), totalAlquiler), listaDetalleAlqui, keyCajaAper, mp, Integer.valueOf(txtAlquilerNumero.getText()));
                    DefaultTableModel dtalqui = new DefaultTableModel(new Object[]{"Codigo", "Descripcion", "Precio", "Cantidad"}, 0) {
                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return false;
                        }
                    };
                    dtalqui.addTableModelListener(new TableModelListener() {
                        @Override
                        public void tableChanged(TableModelEvent tme) {
                            if (tme.getType() == TableModelEvent.INSERT || tme.getType() == TableModelEvent.UPDATE || tme.getType() == TableModelEvent.DELETE) {
                                txtAlquilerTotal.setText(String.valueOf(totalAlquiler));
                            }
                        }
                    });
                    tblAlquilerTablaAlqui.setModel(dtalqui);
                    listaDetalleAlqui.clear();
                    clienteAlqui = null;
                    totalAlquiler = 0;
                    lblNombreClientealq.setText("");
                    lblNombreClientellenaralq.setText("");
                    lblCodigoClientealq.setText("");
                    lblCodigoClientellenaralq.setText("");
                    txtAlquilerTotal.setText("");
                    int num = Integer.valueOf(txtAlquilerNumero.getText());
                    txtAlquilerNumero.setText("%010d" + num + 1);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnAlquilerProcesarActionPerformed

    private void btnProveedoresAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedoresAceptarActionPerformed
        int key = 0;
        if (!txtProveedoresNombre.getText().equals("")) {
            try {
                key = ctrlproveedores.agregar(new Proveedor(txtProveedoresNombre.getText(), txtProveedoresDireccion.getText(), txtProveedoresTelefono.getText(), txtProveedoresEmail.getText(), new Estado(1, "ACTIVO")));
                listaProveedores.put(key, new Proveedor(txtProveedoresNombre.getText(), txtProveedoresDireccion.getText(), txtProveedoresTelefono.getText(), txtProveedoresEmail.getText(), new Estado(1, "ACTIVO")));
                DefaultTableModel dtm = (DefaultTableModel) tblProveedoresTodos.getModel();
                dtm.addRow(new Object[]{key, txtProveedoresNombre.getText().toUpperCase(), txtProveedoresDireccion.getText(), txtProveedoresTelefono.getText()});
                limpiarDatosProveedores();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Razon social vacia, cargue una para continuar", "Datos incompletos", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnProveedoresAceptarActionPerformed

    private void btnProveedorModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorModificarActionPerformed
        if (idProveedor != 0) {
            ModificaProveedor modprov = new ModificaProveedor(this, true, idProveedor);
            modprov.setLocationRelativeTo(this);
            modprov.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    Proveedor modificado;
                    DefaultTableModel dtm = (DefaultTableModel) tblProveedoresTodos.getModel();
                    modificado = modprov.getModificado();
                    listaProveedores.put(modificado.getId(), modificado);
                    //((Proveedor) listaProveedores.get(modificado.getId())).setEstado(modificado.getEstado());
                    dtm.removeRow(filaSeleccionadaProveedor);
                    dtm.addRow(new Object[]{modificado.getId(), modificado.getRazonsocial().toUpperCase(), modificado.getDireccion(), modificado.getTelefono()});
                    limpiaInfoProveedores();
                    idProveedor = 0;
                }
            });
            modprov.show();
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un proveedor", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnProveedorModificarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (cumples != null) {
            cumples.toFront();
        }
    }//GEN-LAST:event_formWindowOpened

    private void btnVentasBuscarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasBuscarCliActionPerformed
        BuscarCliente buscacliente = new BuscarCliente(this, true, listaClientes);
        buscacliente.setLocationRelativeTo(this);
        buscacliente.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                Cliente buscado = buscacliente.getCliente();
                lblCodigoCliente.setText("Codigo");
                lblNombreCliente.setText("Nombre");
                if (buscado != null) {
                    lblNombreClientellenar.setText(buscado.getApellido() + ", " + buscado.getNombre());
                    lblCodigoClientellenar.setText(String.valueOf(buscado.getIdCliente()));
                    if (listaClientesCumple.containsKey(buscado.getIdCliente())) {
                        lblVentasCumple.setText("Hoy es su cumpleaños");
                        lblVentasCumple.setForeground(Color.blue);
                    } else {
                        lblVentasCumple.setText("");
                    }
                    clienteVenta = buscado;
                }
            }
        });
        buscacliente.show();
    }//GEN-LAST:event_btnVentasBuscarCliActionPerformed

    private void txtVentasCodigoCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVentasCodigoCliKeyTyped
        char cTeclaPresionada = evt.getKeyChar();
        Cliente aux = clienteVenta;
        clienteVenta = null;
        if (cTeclaPresionada == KeyEvent.VK_ENTER || cTeclaPresionada == KeyEvent.VK_TAB) {
            if (isNumeric(txtVentasCodigoCli.getText())) {
                clienteVenta = (Cliente) listaClientes.get(Integer.parseInt(txtVentasCodigoCli.getText()));
                if (clienteVenta != null && clienteVenta.getEstado().getId() != 2) {
                    lblCodigoCliente.setText("Codigo");
                    lblNombreCliente.setText("Nombre");
                    lblNombreClientellenar.setText(clienteVenta.getApellido() + ", " + clienteVenta.getNombre());
                    lblCodigoClientellenar.setText(String.valueOf(clienteVenta.getIdCliente()));
                    if (listaClientesCumple.containsKey(clienteVenta.getIdCliente())) {
                        lblVentasCumple.setText("Hoy es su cumpleaños");
                        lblVentasCumple.setForeground(Color.blue);
                    } else {
                        lblVentasCumple.setText("");
                    }
                    txtVentasCodigoCli.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "ingrese un codigo de cliente valido", "Clente no encontrado o Inactivo", JOptionPane.ERROR_MESSAGE);
                    clienteVenta = aux;
                    txtVentasCodigoCli.selectAll();
                }
            } else {
                txtVentasCodigoCli.setText("");
            }
        }
        /*else {
            txtVentasCodigoCli.setText("");
        }*/
    }//GEN-LAST:event_txtVentasCodigoCliKeyTyped

    private void txtVentasCodigoCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVentasCodigoCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVentasCodigoCliActionPerformed

    private void txtCajaMontoCierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCajaMontoCierreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCajaMontoCierreActionPerformed

    private void btnCajaAnadirIngreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCajaAnadirIngreActionPerformed
        iddetalle = 0;
        float aux;
        if ((float) txtCajaMontoIngr.getValue() == 0 && txtCajaConceptoIngr.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Monto o Concepto vacio", "Complete los campos", JOptionPane.ERROR_MESSAGE);
        } else {
            if (isNumeric(String.valueOf(txtCajaMontoIngr.getValue()))) {
                try {
                    iddetalle = ctrlcaja.insertarDetalle(new DetCaja((float) txtCajaMontoIngr.getValue(), txtCajaConceptoIngr.getText(), caja, null, "I"));
                    DefaultTableModel dtm = (DefaultTableModel) tblCajaIngresos.getModel();
                    dtm.addRow(new Object[]{iddetalle, txtCajaConceptoIngr.getText(), txtCajaMontoIngr.getValue()});
                    listaingregre.put(iddetalle, new DetCaja((float) txtCajaMontoIngr.getValue(), txtCajaConceptoIngr.getText(), caja, null, "I"));
                    aux = Float.valueOf(txtCajaTotalIngre.getText());
                    aux += (Float) txtCajaMontoIngr.getValue();
                    txtCajaTotalIngre.setText(String.valueOf(aux));
                    txtCajaMontoIngr.setValue(0);
                    txtCajaConceptoIngr.setText("");
                    iddetalle = 0;
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese solo valores numericos", "Datos invalidos", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCajaAnadirIngreActionPerformed

    private void btnCajaModificarIngreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCajaModificarIngreActionPerformed
        float aux;
        float valorAmodificar;
        if ((float) txtCajaMontoIngr.getValue() == 0 && txtCajaConceptoIngr.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Monto o Concepto vacio", "Complete los campos", JOptionPane.ERROR_MESSAGE);
        } else {
            if (isNumeric(String.valueOf(txtCajaMontoIngr.getValue()))) {
                if (iddetalle >= 0 && filaSeleccionadaInreso >= 0) {
                    try {
                        ctrlcaja.modificaDetalle(new DetCaja(iddetalle, (float) txtCajaMontoIngr.getValue(), txtCajaConceptoIngr.getText()));
                        ((DetCaja) listaingregre.get(iddetalle)).setConcepto(txtCajaConceptoIngr.getText());
                        ((DetCaja) listaingregre.get(iddetalle)).setMonto((float) txtCajaMontoIngr.getValue());
                        DefaultTableModel dtm = (DefaultTableModel) tblCajaIngresos.getModel();
                        dtm.removeRow(filaSeleccionadaInreso);
                        dtm.addRow(new Object[]{iddetalle, txtCajaConceptoIngr.getText(), (float) txtCajaMontoIngr.getValue()});
                        aux = Float.valueOf(txtCajaTotalIngre.getText());
                        valorAmodificar = (float) txtCajaMontoIngr.getValue();
                        if (valorAmodificar != valorAnteriorIngre) {
                            aux = aux - valorAnteriorIngre + valorAmodificar;
                            txtCajaTotalIngre.setText(String.valueOf(aux));
                        } else {
                            JOptionPane.showMessageDialog(null, "Sin cambios", "Atencion", JOptionPane.INFORMATION_MESSAGE);
                        }
                        iddetalle = 0;
                        txtCajaConceptoIngr.setText("");
                        txtCajaMontoIngr.setValue(0);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un Ingreso de la tabla", "sin detalle", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese solo valores numericos", "Datos invalidos", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCajaModificarIngreActionPerformed

    private void btnCajaEliminarIngreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCajaEliminarIngreActionPerformed
        float aux;
        if (iddetalle >= 0 && filaSeleccionadaInreso >= 0) {
            try {
                ctrlcaja.eliminarDetalle(iddetalle);
                aux = Float.valueOf(txtCajaTotalIngre.getText());
                aux -= (float) ((DetCaja) listaingregre.get(iddetalle)).getMonto();
                txtCajaTotalIngre.setText(String.valueOf(aux));
                listaingregre.remove(iddetalle);
                DefaultTableModel dtm = (DefaultTableModel) tblCajaIngresos.getModel();
                dtm.removeRow(filaSeleccionadaInreso);
                txtCajaConceptoIngr.setText("");
                txtCajaMontoIngr.setValue(0);
                iddetalle = 0;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un Ingreso de la tabla", "sin detalle", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCajaEliminarIngreActionPerformed

    private void btnCajaanadirEgreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCajaanadirEgreActionPerformed
        float aux;
        iddetalle = 0;
        if ((float) txtCajaMontoEgre.getValue() == 0 && txtCajaConceptoEgre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Monto o Concepto vacio", "Complete los campos", JOptionPane.ERROR_MESSAGE);
        } else {
            if (isNumeric(String.valueOf(txtCajaMontoEgre.getValue()))) {
                try {
                    iddetalle = ctrlcaja.insertarDetalle(new DetCaja((float) txtCajaMontoEgre.getValue(), txtCajaConceptoEgre.getText(), caja, null, "E"));
                    DefaultTableModel dtm = (DefaultTableModel) tblCajaEgresos.getModel();
                    dtm.addRow(new Object[]{iddetalle, txtCajaConceptoEgre.getText(), txtCajaMontoEgre.getValue()});
                    listaingregre.put(iddetalle, new DetCaja((float) txtCajaMontoEgre.getValue(), txtCajaConceptoEgre.getText(), caja, null, "E"));
                    aux = Float.valueOf(txtCajaTotalEgre.getText());
                    aux += (Float) txtCajaMontoEgre.getValue();
                    txtCajaTotalEgre.setText(String.valueOf(aux));
                    txtCajaMontoEgre.setValue(0);
                    txtCajaConceptoEgre.setText("");
                    iddetalle = 0;
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese solo valores numericos", "Datos invalidos", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCajaanadirEgreActionPerformed

    private void btnCajaModificarEgreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCajaModificarEgreActionPerformed
        float aux = 0;
        float valorAmodificar;
        if ((float) txtCajaMontoEgre.getValue() == 0 && txtCajaConceptoEgre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Monto o Concepto vacio", "Complete los campos", JOptionPane.ERROR_MESSAGE);
        } else {
            if (isNumeric(String.valueOf(txtCajaMontoEgre.getValue()))) {
                if (iddetalle >= 0 && filaSeleccionadaEgreso >= 0) {
                    try {
                        ctrlcaja.modificaDetalle(new DetCaja(iddetalle, (float) txtCajaMontoEgre.getValue(), txtCajaConceptoEgre.getText()));
                        ((DetCaja) listaingregre.get(iddetalle)).setConcepto(txtCajaConceptoEgre.getText());
                        ((DetCaja) listaingregre.get(iddetalle)).setMonto((float) txtCajaMontoEgre.getValue());
                        DefaultTableModel dtm = (DefaultTableModel) tblCajaEgresos.getModel();
                        dtm.removeRow(filaSeleccionadaEgreso);
                        dtm.addRow(new Object[]{iddetalle, txtCajaConceptoEgre.getText(), (float) txtCajaMontoEgre.getValue()});
                        aux = Float.valueOf(txtCajaTotalEgre.getText());
                        valorAmodificar = (float) txtCajaMontoEgre.getValue();
                        if (valorAnteriorEgre != valorAmodificar) {
                            aux = aux - valorAnteriorEgre + valorAmodificar;
                            txtCajaTotalEgre.setText(String.valueOf(aux));
                        } else {
                            JOptionPane.showMessageDialog(null, "Sin cambios", "Atencion", JOptionPane.INFORMATION_MESSAGE);
                        }
                        txtCajaConceptoEgre.setText("");
                        txtCajaMontoEgre.setValue(0);
                        iddetalle = 0;
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un Ingreso de la tabla", "sin detalle", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese solo valores numericos", "Datos invalidos", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCajaModificarEgreActionPerformed

    private void btnCajaEliminarEgreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCajaEliminarEgreActionPerformed
        float aux;
        if (iddetalle >= 0 && filaSeleccionadaEgreso >= 0) {
            try {
                ctrlcaja.eliminarDetalle(iddetalle);
                aux = Float.valueOf(txtCajaTotalEgre.getText());
                aux -= (float) ((DetCaja) listaingregre.get(iddetalle)).getMonto();
                txtCajaTotalEgre.setText(String.valueOf(aux));
                listaingregre.remove(iddetalle);
                DefaultTableModel dtm = (DefaultTableModel) tblCajaEgresos.getModel();
                dtm.removeRow(filaSeleccionadaEgreso);
                txtCajaConceptoEgre.setText("");
                txtCajaMontoEgre.setValue(0);
                iddetalle = 0;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un Ingreso de la tabla", "sin detalle", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCajaEliminarEgreActionPerformed

    private void btnClientesAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesAceptarActionPerformed
        int key = 0;
        java.util.Date utilDate = (java.util.Date) jdcClientesFechaNac.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        if (txtClientesNombre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "nombre de usuario vacio, complete los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                key = ctrlclientes.AgregarCliente(1, txtClientesNotas.getText(), txtClientesNombre.getText(), txtClientesApellido.getText(), txtClientesDireccion.getText(), txtClientesEmail.getText(), txtClientesTelefono.getText(), txtClientesDni.getText(), sqlDate, 1);
                listaClientes.put(key, new Cliente(key, new DescuentoCli(1), txtClientesNotas.getText(), txtClientesNombre.getText(), txtClientesApellido.getText(), txtClientesDireccion.getText(), txtClientesEmail.getText(), txtClientesTelefono.getText(), txtClientesDni.getText(), sqlDate, ctrlclientes.traeEstado(1)));
                DefaultTableModel dtm = (DefaultTableModel) tblClientestodos.getModel();
                dtm.addRow(new Object[]{key, txtClientesApellido.getText(), txtClientesNombre.getText(), txtClientesDireccion.getText()});
                limpiarDatosClientes();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnClientesAceptarActionPerformed

    private void btnArticulosBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArticulosBuscarActionPerformed
        int columnaABuscar = 0;
        if (rdbAriculosCodigo.isSelected()) {
            columnaABuscar = 0;
        } else {
            columnaABuscar = 1;
        }
        trsFiltro = new TableRowSorter(tblArticulosTodos.getModel());
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtArticulosBuscar.getText().toUpperCase(), columnaABuscar));
        tblArticulosTodos.setRowSorter(trsFiltro);
    }//GEN-LAST:event_btnArticulosBuscarActionPerformed

    private void btnArticulosElminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArticulosElminarActionPerformed
        if (idArticulo != 0) {
            int resp = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el articulo " + ((Articulo) listaClientes.get(idCliente)).getDescripcion() + "?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (JOptionPane.YES_OPTION == resp) {
                try {
                    ctrlarticulos.eliminarArticulo(idArticulo);
                    DefaultTableModel dtm = (DefaultTableModel) tblArticulosTodos.getModel();
                    dtm.removeRow(filaSeleccionadaArticulo);
                    idArticulo = 0;
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un articulo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnArticulosElminarActionPerformed

    private void btnArticulosModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArticulosModificarActionPerformed
        if (idArticulo != 0) {
            ModificaArticulo modart = new ModificaArticulo(this, true, idArticulo);
            modart.setLocationRelativeTo(this);
            modart.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    Articulo modificado;
                    DefaultTableModel dtm = (DefaultTableModel) tblArticulosTodos.getModel();
                    modificado = modart.getModificado();
                    listaArticulos.put(idArticulo, modificado);
                    //((Articulo) listaArticulos.get(modificado.getId())).setEstado(modificado.getEstado());
                    dtm.removeRow(filaSeleccionadaArticulo);
                    limpiarnfoArtciulos();
                    dtm.addRow(new Object[]{modificado.getId(), modificado.getDescripcion().toUpperCase(), modificado.getPrecio()});
                    limpiarDatosArticulos();
                    idArticulo = 0;
                }
            });
            modart.show();
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un articulo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnArticulosModificarActionPerformed

    private void cbxArticulosProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxArticulosProveedoresActionPerformed

    }//GEN-LAST:event_cbxArticulosProveedoresActionPerformed

    private void btnArticulosAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArticulosAceptarActionPerformed
        if (txtArticulosDescripcion.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "codigo o descripcion de articulo vacio, complete los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            agregararticulo(2);
        }
    }//GEN-LAST:event_btnArticulosAceptarActionPerformed

    private void btnClientesBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesBuscarActionPerformed
        int columnaABuscar = 0;
        if (rdbClientesCodigo.isSelected()) {
            columnaABuscar = 0;
        } else {
            columnaABuscar = 1;
        }

        trsFiltro = new TableRowSorter(tblClientestodos.getModel());
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtClientesBuscar.getText().toUpperCase(), columnaABuscar));
        tblClientestodos.setRowSorter(trsFiltro);
    }//GEN-LAST:event_btnClientesBuscarActionPerformed

    private void actualizaPrecioAlqui() {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        DefaultTableModel dtmPrcioAlqui = (DefaultTableModel) tblPreciosAlqui.getModel();
        int key = 0;
        try {
            ctrlalquiler.updateUltimoPrecio();
            key = ctrlalquiler.cargarPrecio(new Alquiler((float) spinPrecioalqui.getValue(), date, true));
            listaPreciosAlqui.put(key, new Alquiler((float) spinPrecioalqui.getValue(), date, true));
            if (dtmPrcioAlqui.getRowCount() > 0) {
                dtmPrcioAlqui.setValueAt("No Activo", tblPreciosAlqui.getRowCount() - 1, tblPreciosAlqui.getColumnCount() - 1);
            }
            dtmPrcioAlqui.addRow(new Object[]{(float) spinPrecioalqui.getValue(), formato.format(date), "Activo"});
            tblPreciosAlqui.repaint();
            precioAlqui = (float) spinPrecioalqui.getValue();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "MySql", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregararticulo(int cod) {
        int key = 0;

        try {
            java.util.Date fechaact = new Date();
            java.sql.Date sqlDate = new java.sql.Date(fechaact.getTime());
            if (cod == 2) {
                key = ctrlarticulos.agregarArticulo(txtArticulosDescripcion.getText(), (float) spinArticulosCosto.getValue(), (float) SpinArticulosPrecio.getValue(), (Integer) SpinArticulosCantidad.getValue(), ((Proveedor) cbxArticulosProveedores.getSelectedItem()).getId(), 2);

                listaArticulos.put(key, new Articulo(key, txtArticulosDescripcion.getText(), (float) spinArticulosCosto.getValue(), (float) SpinArticulosPrecio.getValue(), (Integer) SpinArticulosCantidad.getValue(), sqlDate, new Proveedor(((Proveedor) cbxArticulosProveedores.getSelectedItem()).getId(), ((Proveedor) cbxArticulosProveedores.getSelectedItem()).getRazonsocial(), ((Proveedor) cbxArticulosProveedores.getSelectedItem()).getDireccion(), ((Proveedor) cbxArticulosProveedores.getSelectedItem()).getTelefono(), ((Proveedor) cbxArticulosProveedores.getSelectedItem()).getMail(), null), new Categoria(2, "Venta"), new Estado(1, "ACTIVO")));
                DefaultTableModel dtmarti = (DefaultTableModel) tblArticulosTodos.getModel();
                dtmarti.addRow(new Object[]{key, txtArticulosDescripcion.getText().toUpperCase(), (float) SpinArticulosPrecio.getValue()});
                txtArticulosDescripcion.setText("");
                SpinArticulosPrecio.setValue(0);
                spinArticulosCosto.setValue(0);
                cbxArticulosProveedores.setSelectedIndex(0);
            } else if (cod == 1) {
                key = ctrlarticulos.agregarArticulo(txtPeliculasDescripcion.getText(), (float) spinPeliculasCosto.getValue(), (float) SpinPeliculasPrecio.getValue(), (Integer) SpinArticulosCantidad.getValue(), ((Proveedor) cbxPeliculasProveedores.getSelectedItem()).getId(), 1);

                listaArticulos.put(key, new Articulo(key, txtPeliculasDescripcion.getText(), (float) spinPeliculasCosto.getValue(), (float) SpinPeliculasPrecio.getValue(), (Integer) SpinArticulosCantidad.getValue(), sqlDate, new Proveedor(((Proveedor) cbxArticulosProveedores.getSelectedItem()).getId(), ((Proveedor) cbxPeliculasProveedores.getSelectedItem()).getRazonsocial(), ((Proveedor) cbxPeliculasProveedores.getSelectedItem()).getDireccion(), ((Proveedor) cbxPeliculasProveedores.getSelectedItem()).getTelefono(), ((Proveedor) cbxPeliculasProveedores.getSelectedItem()).getMail(), null), new Categoria(1, "Alquiler"), new Estado(1, "ACTIVO")));
                DefaultTableModel dtmarti = (DefaultTableModel) tblPeliculasTodos.getModel();
                dtmarti.addRow(new Object[]{key, txtPeliculasDescripcion.getText().toUpperCase(), (float) SpinPeliculasPrecio.getValue()});
                txtPeliculasDescripcion.setText("");
                SpinPeliculasPrecio.setValue(0);
                spinPeliculasCosto.setValue(0);
                cbxPeliculasProveedores.setSelectedIndex(0);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "MySql.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarDatosClientes() {
        txtClientesNombre.setText("");
        txtClientesApellido.setText("");
        txtClientesDni.setText("");
        txtClientesDireccion.setText("");
        txtClientesTelefono.setText("");
        txtClientesEmail.setText("");
        txtClientesNotas.setText("");
    }

    private void limpiarDatosProveedores() {
        txtProveedoresNombre.setText("");
        txtProveedoresDireccion.setText("");
        txtProveedoresTelefono.setText("");
        txtProveedoresEmail.setText("");
    }

    private void limpiaInfoProveedores() {
        lblProveedorNombre.setText("");
        lblProveedorDireccion.setText("");
        lblProveedorTelefono.setText("");
        lblProveedorEmail.setText("");
        lblProveedorEstado.setText("");
    }

    private void limpiarInfoClientes() {
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

    private void limpiarDatosArticulos() {
        txtArticulosDescripcion.setText("");
        SpinArticulosPrecio.setValue(0);
        spinArticulosCosto.setValue(0);
        cbxArticulosProveedores.setSelectedIndex(0);
    }

    private void limpiarDatosPeliculas() {
        txtPeliculasDescripcion.setText("");
        SpinPeliculasPrecio.setValue(0);
        spinPeliculasCosto.setValue(0);
        cbxPeliculasProveedores.setSelectedIndex(0);
    }

    private void limpiarnfoPeliculas() {
        lblPeliculasActivo.setText("");
        lblPeliculasCodigo.setText("");
        lblPeliculasCosto.setText("");
        lblPeliculasDescripcion.setText("");
        lblPeliculasFechaCompra.setText("");
        lblPeliculasPrecio.setText("");
        lblPeliculasProveedor.setText("");
    }

    private void limpiarnfoArtciulos() {
        lblArticulosActivo.setText("");
        lblArticulosCodigo.setText("");
        lblArticulosCosto.setText("");
        lblArticulosDescripcion.setText("");
        lblArticulosFechaCompra.setText("");
        lblArticulosPrecio.setText("");
        lblArticulosProveedor.setText("");
    }

    public static boolean isNumeric(String str) {
        return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("") == false);
    }

    public void stock() {
        DefaultTableModel dtmStock = new DefaultTableModel(new Object[]{"Codigo", "Descripcion", "Cantidad"}, 0) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        for (Iterator it = listaArticulos.entrySet().iterator(); it.hasNext();) {
            ConcurrentHashMap.Entry<?, ?> entry = (ConcurrentHashMap.Entry<?, ?>) it.next();
            dtmStock.addRow(new Object[]{((Articulo) entry.getValue()).getId(), ((Articulo) entry.getValue()).getDescripcion().toUpperCase(), ((Articulo) entry.getValue()).getCantidad()});
        }
        tblStock.setModel(dtmStock);

        tblStock.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tblStock.rowAtPoint(e.getPoint());
                int columna = tblStock.columnAtPoint(e.getPoint());
                if ((fila > -1) && (columna > -1)) {
                    filaSeleccionadaArticuloStock = fila;
                    Articulo arti = (Articulo) listaArticulos.get(tblStock.getModel().getValueAt(fila, 0));
                    idArticuloStock = arti.getId();
                    txtStockCodArti.setText(String.valueOf(arti.getId()));
                }
            }
        });
    }

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
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JPanel PanelAlquiler;
    private javax.swing.JSpinner SpinArticulosCantidad;
    private javax.swing.JSpinner SpinArticulosPrecio;
    private javax.swing.JSpinner SpinPeliculasPrecio;
    private javax.swing.JTabbedPane TabDevoluciones;
    private javax.swing.JButton btnAlquilerBucarArti;
    private javax.swing.JButton btnAlquilerBuscarCli;
    private javax.swing.ButtonGroup btnAlquilerPago;
    private javax.swing.JButton btnAlquilerProcesar;
    private java.awt.Button btnArticulosAceptar;
    private javax.swing.JButton btnArticulosBuscar;
    private javax.swing.JButton btnArticulosElminar;
    private javax.swing.ButtonGroup btnArticulosFiltro;
    private javax.swing.JButton btnArticulosModificar;
    private javax.swing.JButton btnCajaAbre;
    private javax.swing.JButton btnCajaAnadirIngre;
    private javax.swing.JButton btnCajaCierre;
    private javax.swing.JButton btnCajaEliminarEgre;
    private javax.swing.JButton btnCajaEliminarIngre;
    private javax.swing.JButton btnCajaModificarEgre;
    private javax.swing.JButton btnCajaModificarIngre;
    private javax.swing.JButton btnCajaanadirEgre;
    private javax.swing.JButton btnCambiarPrecio;
    private java.awt.Button btnClientesAceptar;
    private javax.swing.JButton btnClientesBuscar;
    private javax.swing.JButton btnClientesElminar;
    private javax.swing.ButtonGroup btnClientesFiltro;
    private javax.swing.JButton btnClintesModificar;
    private java.awt.Button btnPeliculasAceptar;
    private javax.swing.JButton btnPeliculasBuscar;
    private javax.swing.JButton btnPeliculasElminar;
    private javax.swing.ButtonGroup btnPeliculasFitro;
    private javax.swing.JButton btnPeliculasModificar;
    private javax.swing.JButton btnProveedorEliminar;
    private javax.swing.JButton btnProveedorModificar;
    private java.awt.Button btnProveedoresAceptar;
    private javax.swing.JButton btnStockBuscar;
    private javax.swing.JButton btnStockProcesar;
    private java.awt.Button btnUsuariosAceptar;
    private javax.swing.ButtonGroup btnVentaPago;
    private javax.swing.JButton btnVentasBucarArti;
    private javax.swing.JButton btnVentasBuscarCli;
    private javax.swing.JButton btnVentasProcesar;
    private javax.swing.JComboBox<Modelos.Usuario> cbxAlquilerVendedores;
    private javax.swing.JComboBox<Modelos.Proveedor> cbxArticulosProveedores;
    private javax.swing.JComboBox<Modelos.Proveedor> cbxPeliculasProveedores;
    private javax.swing.JComboBox<Modelos.Usuario> cbxVentasVendedores;
    private javax.swing.JCheckBox chkUsuariosAdmin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
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
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
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
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private com.toedter.calendar.JDateChooser jdcClientesFechaNac;
    private java.awt.Label labelContraseña;
    private java.awt.Label labelContraseñaR;
    private java.awt.Label labelNombre;
    private javax.swing.JLabel lblAlquilerCumple;
    private javax.swing.JLabel lblArticulosActivo;
    private javax.swing.JLabel lblArticulosCodigo;
    private javax.swing.JLabel lblArticulosCosto;
    private javax.swing.JLabel lblArticulosDescripcion;
    private javax.swing.JLabel lblArticulosFechaCompra;
    private javax.swing.JLabel lblArticulosPrecio;
    private javax.swing.JLabel lblArticulosProveedor;
    private javax.swing.JLabel lblCajaCodigoVendedor;
    private javax.swing.JLabel lblCajaCodigoVendedorCierre;
    private javax.swing.JLabel lblCajaEstado;
    private javax.swing.JLabel lblCajaFechaAper;
    private javax.swing.JLabel lblCajaFechaCierre;
    private javax.swing.JLabel lblCajaNomVendedor;
    private javax.swing.JLabel lblCajaNomVendedorCierre;
    private javax.swing.JLabel lblClienteEstado;
    private javax.swing.JLabel lblClientesCodigo;
    private javax.swing.JLabel lblClientesCumple;
    private javax.swing.JLabel lblClientesDNI;
    private javax.swing.JLabel lblClientesDireccion;
    private javax.swing.JLabel lblClientesFechaNac;
    private javax.swing.JLabel lblClientesId;
    private javax.swing.JLabel lblClientesMail;
    private javax.swing.JLabel lblClientesNombre;
    private javax.swing.JLabel lblClientesNotas;
    private javax.swing.JLabel lblClientesTelefono;
    private javax.swing.JLabel lblCodigoCliente;
    private javax.swing.JLabel lblCodigoClientealq;
    private javax.swing.JLabel lblCodigoClientellenar;
    private javax.swing.JLabel lblCodigoClientellenaralq;
    private javax.swing.JLabel lblNombreCliente;
    private javax.swing.JLabel lblNombreClientealq;
    private javax.swing.JLabel lblNombreClientellenar;
    private javax.swing.JLabel lblNombreClientellenaralq;
    private javax.swing.JLabel lblPeliculasActivo;
    private javax.swing.JLabel lblPeliculasCodigo;
    private javax.swing.JLabel lblPeliculasCosto;
    private javax.swing.JLabel lblPeliculasDescripcion;
    private javax.swing.JLabel lblPeliculasFechaCompra;
    private javax.swing.JLabel lblPeliculasPrecio;
    private javax.swing.JLabel lblPeliculasProveedor;
    private javax.swing.JLabel lblProveedorDireccion;
    private javax.swing.JLabel lblProveedorEmail;
    private javax.swing.JLabel lblProveedorEstado;
    private javax.swing.JLabel lblProveedorID;
    private javax.swing.JLabel lblProveedorNombre;
    private javax.swing.JLabel lblProveedorTelefono;
    private javax.swing.JLabel lblVentasCumple;
    private javax.swing.JMenu menuAlquileres;
    private javax.swing.JPanel panelVentas;
    private javax.swing.JRadioButton rdbAlquilerContado;
    private javax.swing.JRadioButton rdbAlquilerCtaCte;
    private javax.swing.JRadioButton rdbAriculosCodigo;
    private javax.swing.JRadioButton rdbArticulosDescripcion;
    private javax.swing.JRadioButton rdbClientesApellido;
    private javax.swing.JRadioButton rdbClientesCodigo;
    private javax.swing.JRadioButton rdbPeliculasCodigo;
    private javax.swing.JRadioButton rdbPeliculasTitulo;
    private javax.swing.JRadioButton rdbVentaContado;
    private javax.swing.JRadioButton rdbVentaCtaCte;
    private javax.swing.JSpinner spinArticulosCosto;
    private javax.swing.JSpinner spinPeliculasCosto;
    private javax.swing.JSpinner spinPrecioalqui;
    private javax.swing.JSpinner spinStockCantidad;
    private javax.swing.JPanel tabAlquiler;
    private javax.swing.JPanel tabArticulos;
    private javax.swing.JPanel tabCaja;
    private javax.swing.JPanel tabClientes;
    private javax.swing.JPanel tabPeliculas;
    private javax.swing.JPanel tabPrecioAlquiler;
    private javax.swing.JPanel tabProveedores;
    private javax.swing.JPanel tabStock;
    private javax.swing.JPanel tabUsuarios;
    private javax.swing.JPanel tabVenta;
    private javax.swing.JTable tblAlquilerTablaAlqui;
    private javax.swing.JTable tblArticulosTodos;
    private javax.swing.JTable tblCajaEgresos;
    private javax.swing.JTable tblCajaIngresos;
    private javax.swing.JTable tblClientestodos;
    private javax.swing.JTable tblDevoluciones;
    private javax.swing.JTable tblPeliculasTodos;
    private javax.swing.JTable tblPreciosAlqui;
    private javax.swing.JTable tblProveedoresTodos;
    private javax.swing.JTable tblStock;
    private javax.swing.JTable tblUsuariosTodos;
    private javax.swing.JTable tblVentasTablaVentas;
    private javax.swing.JTextField txtAlquilerCodArti;
    private javax.swing.JTextField txtAlquilerCodigoCli;
    private javax.swing.JTextField txtAlquilerDevoluciones;
    private javax.swing.JFormattedTextField txtAlquilerFecha;
    private javax.swing.JTextField txtAlquilerNumero;
    private javax.swing.JTextField txtAlquilerPV;
    private javax.swing.JTextField txtAlquilerRecargos;
    private javax.swing.JTextField txtAlquilerSaldoCC;
    private javax.swing.JTextField txtAlquilerTotal;
    private javax.swing.JTextField txtAlquilerVendedor;
    private javax.swing.JTextField txtArticulosBuscar;
    private javax.swing.JTextArea txtArticulosDescripcion;
    private javax.swing.JTextField txtCajaConceptoEgre;
    private javax.swing.JTextField txtCajaConceptoIngr;
    private javax.swing.JTextField txtCajaMontoAper;
    private javax.swing.JTextField txtCajaMontoCierre;
    private javax.swing.JSpinner txtCajaMontoEgre;
    private javax.swing.JSpinner txtCajaMontoIngr;
    private javax.swing.JTextField txtCajaRetiro;
    private javax.swing.JTextField txtCajaTotalEgre;
    private javax.swing.JTextField txtCajaTotalIngre;
    private javax.swing.JTextField txtClientesApellido;
    private javax.swing.JTextField txtClientesBuscar;
    private javax.swing.JTextField txtClientesDireccion;
    private javax.swing.JTextField txtClientesDni;
    private javax.swing.JTextField txtClientesEmail;
    private javax.swing.JTextField txtClientesNombre;
    private javax.swing.JTextArea txtClientesNotas;
    private javax.swing.JTextField txtClientesTelefono;
    private javax.swing.JTextField txtPeliculasBuscar;
    private javax.swing.JTextArea txtPeliculasDescripcion;
    private javax.swing.JTextField txtProveedoresDireccion;
    private javax.swing.JTextField txtProveedoresEmail;
    private javax.swing.JTextField txtProveedoresNombre;
    private javax.swing.JTextField txtProveedoresTelefono;
    private javax.swing.JTextField txtStockCodArti;
    private java.awt.Label txtTituloAltaUsuario;
    private javax.swing.JPasswordField txtUsuariosContraseña;
    private javax.swing.JTextField txtUsuariosNombre;
    private javax.swing.JPasswordField txtUsuariosRepContraseña;
    private javax.swing.JTextField txtVentasCodArti;
    private javax.swing.JTextField txtVentasCodigoCli;
    private javax.swing.JTextField txtVentasDevoluciones;
    private javax.swing.JFormattedTextField txtVentasFecha;
    private javax.swing.JTextField txtVentasNumero;
    private javax.swing.JTextField txtVentasPV;
    private javax.swing.JTextField txtVentasRecargos;
    private javax.swing.JTextField txtVentasSaldoCC;
    private javax.swing.JTextField txtVentasTotal;
    private javax.swing.JTextField txtVentasVendedor;
    // End of variables declaration//GEN-END:variables
}
