/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelos.Articulo;
import Modelos.Cliente;
import Modelos.Devolucion;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Valtanders
 */
public class BDDevoluciones {

    private static BDDevoluciones instance;

    public static BDDevoluciones getIntance() {
        if (instance == null) {
            instance = new BDDevoluciones();
        }
        return instance;
    }

    public ConcurrentHashMap traerDevolucionesPend() throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        ConcurrentHashMap lista = new ConcurrentHashMap();
        Articulo articulo = null;
        Cliente cliente = null;
        Devolucion devolucion = null;
        oCon.getConexion();
        String select = "select d.iddevoluciones, d.fk_idcliente, d.fk_idarticulos, d.fecha_alqui, d.fecha_devol, c.nombre, c.apellido, c.direccion, c.mail, c.dni, c.fechanac, a.descripccion, a.costo, a.precio \n"
                + "from devoluciones d\n"
                + "join cliente c on d.fk_idcliente = c.idcliente\n"
                + "join articulos a on d.fk_idarticulos = a.idarticulos\n"
                + "where d.fecha_devol = ''";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                articulo = new Articulo(rs.getInt(3));
                articulo.setDescripcion(rs.getString(12));
                articulo.setCosto(rs.getFloat(13));
                articulo.setPrecio(rs.getFloat(14));
                cliente = new Cliente(rs.getInt(2));
                cliente.setNombre(rs.getString(6));
                cliente.setApellido(rs.getString(7));
                cliente.setDireccion(rs.getString(8));
                cliente.setMail(rs.getString(9));
                cliente.setDni(rs.getString(10));
                cliente.setFechanac(rs.getDate(11));
                devolucion = new Devolucion(rs.getInt(1), cliente, articulo, rs.getDate(4), null);
                lista.put(devolucion.getId(), devolucion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return lista;
        }
    }

    public ConcurrentHashMap traeUltima(int idcli, Date fecha) throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        ConcurrentHashMap lista = new ConcurrentHashMap();
        Articulo articulo = null;
        Cliente cliente = null;
        Devolucion devolucion = null;
        oCon.getConexion();
        String select = "select d.iddevoluciones, d.fk_idcliente, d.fk_idarticulos, d.fecha_alqui, d.fecha_devol, c.nombre, c.apellido, c.direccion, c.mail, c.dni, c.fechanac, a.descripccion, a.costo, a.precio \n"
                + "from devoluciones d\n"
                + "join cliente c on d.fk_idcliente = c.idcliente\n"
                + "join articulos a on d.fk_idarticulos = a.idarticulos\n"
                + "where d.fecha_devol is null and d.fk_idcliente = ? and d.fecha_alqui = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            sentencia.setInt(1, idcli);
            sentencia.setDate(2, fecha);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                articulo = new Articulo(rs.getInt(3));
                articulo.setDescripcion(rs.getString(12));
                articulo.setCosto(rs.getFloat(13));
                articulo.setPrecio(rs.getFloat(14));
                cliente = new Cliente(rs.getInt(2));
                cliente.setNombre(rs.getString(6));
                cliente.setApellido(rs.getString(7));
                cliente.setDireccion(rs.getString(8));
                cliente.setMail(rs.getString(9));
                cliente.setDni(rs.getString(10));
                cliente.setFechanac(rs.getDate(11));
                devolucion = new Devolucion(rs.getInt(1), cliente, articulo, rs.getDate(4), null);
                lista.put(devolucion.getId(), devolucion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return lista;
        }
    }

    public void procesarDevolucion(Devolucion devol, int idcaja, float monto, Date fecha) throws SQLException {
        Conexion oCon = new Conexion();
        oCon.getConexion();
        Connection con = oCon.getConexion();
        java.sql.Date sqldate = new Date(fecha.getTime());
        String update = "update devoluciones set fecha_devol = ? where fecha_alqui = ? and fk_idcliente = ?";
        try {
            con.setAutoCommit(false);
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(update);
            sentencia.setDate(1, sqldate);
            sentencia.setDate(2, new java.sql.Date(devol.getFechaAlqui().getTime()));
            sentencia.setInt(3, devol.getCliente().getIdCliente());
            sentencia.execute();
            sentencia = null;
            update = "insert into detallecaja(concepto, fk_idCabVenta, fk_idCaja,monto, tipoOperacion) values(?,null,?,?,?)";
            sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(update);
            sentencia.setString(1, "Devolucion "+sqldate);
            sentencia.setInt(2, idcaja);
            sentencia.setFloat(3, monto);
            sentencia.setString(4, "I");
            sentencia.execute();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
        } finally {
            oCon.close();
        }
    }
}
