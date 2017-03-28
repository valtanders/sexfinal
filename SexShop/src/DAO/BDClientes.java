/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelos.Articulo;
import Modelos.Cliente;
import Modelos.DescuentoCli;
import Modelos.Devolucion;
import Modelos.Estado;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Valtanders
 */
public class BDClientes implements Interfaz {

    private static BDClientes instance;

    public static BDClientes getIntance() {
        if (instance == null) {
            instance = new BDClientes();
        }
        return instance;
    }

    @Override
    public int agregar(Object valor) throws SQLException {
        Cliente cliente = (Cliente) valor;
        Conexion oCon = new Conexion();
        oCon.getConexion();
        int llave = 0;
        String insert = "INSERT INTO cliente (fk_idDescuentoCli,notas,nombre,apellido,direccion,mail,telefono,dni,fechanac,fk_idEstados) Values (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            sentencia.setInt(1, cliente.getDescuento().getIdDescuentoCli());
            sentencia.setString(2, cliente.getNotas());
            sentencia.setString(3, cliente.getNombre());
            sentencia.setString(4, cliente.getApellido());
            sentencia.setString(5, cliente.getDireccion());
            sentencia.setString(6, cliente.getMail());
            sentencia.setString(7, cliente.getTelefono());
            sentencia.setString(8, cliente.getDni());
            sentencia.setDate(9, cliente.getFechanac());
            sentencia.setInt(10, cliente.getEstado().getId());
            sentencia.execute();
            ResultSet rs = sentencia.getGeneratedKeys();
            if (rs != null && rs.next()) {
                llave = rs.getInt(1);
            }
            sentencia.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            oCon.close();
            return llave;
        }
    }

    @Override
    public Cliente modificar(Object valor) throws SQLException {
        Conexion oCon = new Conexion();
        Cliente cliente = (Cliente) valor;
        oCon.getConexion();
        String update = "update cliente set fk_idDescuentoCli = ?,notas = ?,nombre = ?,apellido = ?,direccion = ?,mail = ?,telefono = ?,dni = ?,fechanac = ?,fk_idEstados = ? where idCliente = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(update);
            sentencia.setInt(1, cliente.getDescuento().getIdDescuentoCli());
            sentencia.setString(2, cliente.getNotas());
            sentencia.setString(3, cliente.getNombre());
            sentencia.setString(4, cliente.getApellido());
            sentencia.setString(5, cliente.getDireccion());
            sentencia.setString(6, cliente.getMail());
            sentencia.setString(7, cliente.getTelefono());
            sentencia.setString(8, cliente.getDni());
            sentencia.setDate(9, cliente.getFechanac());
            sentencia.setInt(10, cliente.getEstado().getId());
            sentencia.setInt(11, cliente.getIdCliente());
            sentencia.execute();
            sentencia.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            oCon.close();
            return cliente;
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        Conexion oCon = new Conexion();
        oCon.getConexion();
        String eliminar = "update cliente set fk_idestados = 3 where idcliente = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(eliminar);
            sentencia.setInt(1, id);
            sentencia.execute();
            sentencia.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            oCon.close();
        }
    }

    @Override
    public ConcurrentHashMap traerTodos() throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        ConcurrentHashMap resp = new ConcurrentHashMap<String, String>();
        oCon.getConexion();
        String insert = "SELECT C.idcliente,C.notas,C.nombre,C.apellido,C.direccion,C.mail,C.telefono,C.dni,C.fechanac, DC.idDescuentoCli, DC.descripcion, DC.porcentaje, DC.importe,E.idEstados,E.Descripcion  FROM cliente C INNER JOIN descuentocli DC ON idDescuentoCli = C.fk_idDescuentoCli INNER JOIN estados E on idEstados = C.fk_idEstados where C.fk_idEstados <> 3";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            rs = sentencia.executeQuery();

            Cliente cliente;
            DescuentoCli desc;
            Estado est;
            while (rs.next()) {
                est = new Estado(rs.getInt(14), rs.getString(15));
                desc = new DescuentoCli(rs.getInt(10), rs.getString(11), rs.getInt(12), rs.getFloat(13));
                desc = null;
                cliente = new Cliente(rs.getInt(1), desc, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), est);
                resp.put(cliente.getIdCliente(), cliente);
            }
            rs.close();
            sentencia.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            oCon.close();
            return resp;
        }
    }

    public DescuentoCli traerDecuento(int id) throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        DescuentoCli desccli = null;
        oCon.getConexion();
        String insert = "SELECT * FROM DescuentoCli where idDescuentoCli = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            sentencia.setInt(1, id);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                desccli = new DescuentoCli(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getFloat(4));
            }
            rs.close();
            sentencia.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            oCon.close();
            return desccli;
        }

    }

    public Cliente traeclienteporid(int id) throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        Cliente cliente = null;
        Estado est = null;
        DescuentoCli desc = null;
        oCon.getConexion();
        String select = "SELECT C.idcliente,C.notas,C.nombre,C.apellido,C.direccion,C.mail,C.telefono,C.dni,C.fechanac, DC.idDescuentoCli, DC.descripcion, DC.porcentaje, DC.importe,E.idEstados,E.Descripcion  FROM cliente C INNER JOIN descuentocli DC ON idDescuentoCli = C.fk_idDescuentoCli INNER JOIN estados E on idEstados = C.fk_idEstados where C.idcliente = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            sentencia.setInt(1, id);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                est = new Estado(rs.getInt(14), rs.getString(15));
                desc = new DescuentoCli(rs.getInt(10), rs.getString(11), rs.getInt(12), rs.getFloat(13));
                cliente = new Cliente(rs.getInt(1), desc, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), est);
            }
            rs.close();
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return cliente;
        }

    }

    public ConcurrentHashMap traerDescuentos() throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        DescuentoCli descli = null;
        ConcurrentHashMap lista = new ConcurrentHashMap();
        oCon.getConexion();
        String select = "Select * from descuentocli";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                descli = new DescuentoCli(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getFloat(4));
                lista.put(descli.getIdDescuentoCli(), descli);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            oCon.close();
            return lista;
        }
    }

    public Estado TraeEstado(int id) throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        Estado estado = null;
        oCon.getConexion();
        String select = "Select * from estados where idestados = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            sentencia.setInt(1, id);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                estado = new Estado(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            oCon.close();
            return estado;
        }
    }

    public ConcurrentHashMap traeCumpleanieros() throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        ConcurrentHashMap resp = new ConcurrentHashMap<String, String>();
        oCon.getConexion();
        String insert = "SELECT C.idcliente,C.notas,C.nombre,C.apellido,C.direccion,C.mail,C.telefono,C.dni,C.fechanac, DC.idDescuentoCli, DC.descripcion, DC.porcentaje, DC.importe,E.idEstados,E.Descripcion  FROM cliente C \n"
                + "INNER JOIN descuentocli DC ON idDescuentoCli = C.fk_idDescuentoCli \n"
                + "INNER JOIN estados E on idEstados = C.fk_idEstados \n"
                + "where C.fk_idEstados <> 3 and month(fechanac) = month(curdate()) and day(fechanac) = day(curdate())";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            rs = sentencia.executeQuery();

            Cliente cliente;
            DescuentoCli desc;
            Estado est;
            while (rs.next()) {
                est = new Estado(rs.getInt(14), rs.getString(15));
                desc = new DescuentoCli(rs.getInt(10), rs.getString(11), rs.getInt(12), rs.getFloat(13));
                cliente = new Cliente(rs.getInt(1), desc, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), est);
                resp.put(cliente.getIdCliente(), cliente);
            }
            rs.close();
            sentencia.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            oCon.close();
            return resp;
        }
    }

    public ConcurrentHashMap traeHistorico() throws SQLException {
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
                + "where d.fecha_devol != ''";
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
            throw e;
        } finally {
            oCon.close();
            return lista;
        }
    }

    public ConcurrentHashMap traeHistoricoPorCliente(int id) throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        ConcurrentHashMap lista = new ConcurrentHashMap();
        Articulo articulo = null;
        Cliente cliente = null;
        Date devol = null;
        String devolAux = "";
        Devolucion devolucion = null;
        oCon.getConexion();
        String select = "select d.iddevoluciones, d.fk_idarticulos, d.fecha_alqui, d.fecha_devol, c.notas, a.descripccion \n"
                + "from devoluciones d\n"
                + "join cliente c on d.fk_idcliente = c.idcliente\n"
                + "join articulos a on d.fk_idarticulos = a.idarticulos \n"
                + "where c.idcliente = ?\n"
                + "order by d.fecha_devol asc";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            sentencia.setInt(1, id);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                devol = rs.getDate("fecha_devol") != null ? rs.getDate("fecha_devol") : null;
                articulo = new Articulo(rs.getInt("fk_idarticulos"));
                articulo.setDescripcion(rs.getString("descripccion"));
                cliente = new Cliente(id);
                devolucion = new Devolucion(rs.getInt("iddevoluciones"), cliente, articulo, rs.getDate("fecha_alqui"), devol);
                lista.put(devolucion.getId(), devolucion);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            oCon.close();
            return lista;
        }
    }
}
