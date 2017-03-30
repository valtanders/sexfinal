/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelos.Articulo;
import Modelos.Categoria;
import Modelos.Estado;
import Modelos.Proveedor;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Valtanders
 */
public class BDArticulos implements Interfaz {

    private static BDArticulos instance;

    public static BDArticulos getIntance() {
        if (instance == null) {
            instance = new BDArticulos();
        }
        return instance;
    }

    @Override
    public int agregar(Object valor) throws SQLException {
        Articulo articulo = (Articulo) valor;
        Conexion oCon = new Conexion();
        oCon.getConexion();
        int llave = 0;
        String insert = "insert into articulos (descripccion, costo, precio, cantidad, fechaCompra, fk_idProveedores, fk_idCategorias, fk_idEstados)\n"
                + "values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, articulo.getDescripcion());
            sentencia.setFloat(2, articulo.getCosto());
            sentencia.setFloat(3, articulo.getPrecio());
            sentencia.setInt(4, articulo.getCantidad());
            sentencia.setDate(5, articulo.getFechaCompra());
            sentencia.setInt(6, articulo.getProveedor().getId());
            sentencia.setInt(7, articulo.getCategoria().getId());
            sentencia.setInt(8, articulo.getEstado().getId());
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
    public Articulo modificar(Object valor) throws SQLException {
        Conexion oCon = new Conexion();
        Articulo articulo = (Articulo) valor;
        oCon.getConexion();
        String update = "update Articulos set descripccion = ?, costo = ?, precio = ?, cantidad = ?, fk_idproveedores = ?, fk_idcategorias = ?, fk_idestados = ? where idarticulos = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(update);
            sentencia.setString(1, articulo.getDescripcion());
            sentencia.setFloat(2, articulo.getCosto());
            sentencia.setFloat(3, articulo.getPrecio());
            sentencia.setInt(4, articulo.getCantidad());
            sentencia.setInt(5, articulo.getProveedor().getId());
            sentencia.setInt(6, articulo.getCategoria().getId());
            sentencia.setInt(7, articulo.getEstado().getId());
            sentencia.setInt(8, articulo.getId());
            sentencia.execute();
            sentencia.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            oCon.close();
            return articulo;
        }

    }

    @Override
    public void eliminar(int id) throws SQLException {
        Conexion oCon = new Conexion();
        oCon.getConexion();
        String eliminar = "update articulos set fk_idestados = 3 where idarticulos = ?";
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
        String insert = "select A.idArticulos, A.descripccion, A.costo, A.precio, A.cantidad, A.fechaCompra, A.fk_idProveedores, A.fk_idCategorias, A.fk_idEstados, P.razonsocial, P.direccion, P.telefono, P.mail, P.fk_idEstados, C.descripcion, E.Descripcion from articulos A\n"
                + "left join proveedores P on P.idproveedores = A.fk_idproveedores\n"
                + "inner join categorias C on C.idcategorias = A.fk_idcategorias\n"
                + "inner join Estados E on E.idestados = A.fk_idestados\n"
                + "where A.fk_idEstados <> 3";

        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            rs = sentencia.executeQuery();

            Articulo articulo;
            Proveedor proveedor = null;
            Categoria categoria;
            Estado est;
            while (rs.next()) {
                est = new Estado(rs.getInt(9), rs.getString(16));
                categoria = new Categoria(rs.getInt(8), rs.getString(15));
                if (rs.getString(10) != null) {
                    proveedor = new Proveedor(rs.getInt(7), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), new Estado());
                }
                articulo = new Articulo(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getFloat(4), rs.getInt(5), rs.getDate(6), proveedor, categoria, est);
                resp.put(articulo.getId(), articulo);
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

    public boolean BuscaCodigo(String cod) throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        String codigo = "";
        boolean codrepetido = false;
        oCon.getConexion();
        String select = "Select codigo from articulos where codigo = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            sentencia.setString(1, cod);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                codigo = rs.getString(1);
            }
            if (!codigo.equals("")) {
                codrepetido = true;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            oCon.close();
            return codrepetido;
        }
    }

    public Articulo traerPorId(int id) throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        Articulo arti = null;
        Estado est = null;
        Proveedor prov = null;
        Categoria cat = null;
        int prove = 0;
        oCon.getConexion();
        String select = "select A.idArticulos, A.descripccion, A.costo, A.precio, A.cantidad, A.fechaCompra, A.fk_idProveedores, A.fk_idCategorias, A.fk_idEstados, P.razonsocial, P.direccion, P.telefono, P.mail, P.fk_idEstados, C.descripcion, E.Descripcion from articulos A\n"
                + "inner join proveedores P on P.idproveedores = A.fk_idproveedores\n"
                + "inner join categorias C on C.idcategorias = A.fk_idcategorias\n"
                + "inner join Estados E on E.idestados = A.fk_idestados\n"
                + "where A.idArticulos = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            sentencia.setInt(1, id);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                /*est = new Estado(rs.getInt(8),rs.getString(16));
                cat = new Categoria(rs.getInt(7),rs.getString(15));
                prov = new Proveedor(rs.getInt(6), rs.getString(9),rs.getString(10), rs.getString(11),rs.getString(12),rs.getString(13),new Estado());
                arti = new Articulo(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getFloat(4), rs.getDate(5), prov, cat,est);*/
                est = new Estado(rs.getInt(9), rs.getString(16));
                cat = new Categoria(rs.getInt(8), rs.getString(15));
                prove = rs.getString(7) != null ? rs.getInt(7) : 0;
                if (prove != 0) {
                    prov = new Proveedor(rs.getInt(7), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), new Estado());
                }
                arti = new Articulo(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getFloat(4), rs.getInt(5), rs.getDate(6), prov, cat, est);
            }
            rs.close();
            sentencia.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            oCon.close();
            return arti;
        }
    }

    public void cargaStock(Articulo articulo) throws SQLException {
        Conexion oCon = new Conexion();
        oCon.getConexion();
        String update = "update Articulos set cantidad = ? where idarticulos = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(update);
            sentencia.setInt(1, articulo.getCantidad());
            sentencia.setInt(2, articulo.getId());
            sentencia.execute();
            sentencia.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            oCon.close();
        }
    }

    public boolean traePeliYaVista(int idPeli, int idCli) throws SQLException {
        Conexion oCon = new Conexion();
        oCon.getConexion();
        ResultSet rs = null;
        boolean esta = false;
        int total = 0;
        String select = "select count(*) as total from articulos a\n"
                + "join devoluciones d on a.idarticulos = d.fk_idarticulos \n"
                + "where d.fk_idcliente = ?\n"
                + "and a.idarticulos = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            sentencia.setInt(1, idCli);
            sentencia.setInt(2, idPeli);
            rs = sentencia.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
                if (total != 0) {
                    esta = true;
                }
            }
            rs.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            oCon.close();
        }
        return esta;
    }

}
