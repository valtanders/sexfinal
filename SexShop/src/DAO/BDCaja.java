/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelos.Caja;
import Modelos.DetCaja;
import Modelos.Usuario;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Valtanders
 */
public class BDCaja {

    private static BDCaja instance;

    public static BDCaja getIntance() {
        if (instance == null) {
            instance = new BDCaja();
        }
        return instance;
    }

    public Caja cajaAbierta() throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        Usuario usuario = null;
        Caja caja = null;
        oCon.getConexion();
        String select = "Select c.idCaja, c.fechaapertura, c.fechacierre, c.abierta, c.fk_idusuario, u.nombre, u.password, u.fk_idRoles, r.descripcion from caja c \n" +
                        "join usuario u on c.fk_idusuario = u.idusuario\n" +
                        "join roles r on u.fk_idroles = idroles \n" +
                        "where abierta = true";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                usuario = new Usuario(rs.getInt(5), rs.getString(6), rs.getString(7), rs.getInt(8));
                caja = new Caja(rs.getInt(1), rs.getDate(2), null, rs.getBoolean(4), usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return caja;
        }
    }

    public int insertarDetalleCaja(DetCaja detCaja) throws SQLException {
        Conexion oCon = new Conexion();
        oCon.getConexion();
        int key =0;
        ResultSet rs = null;
        String insert = "insert into detallecaja(concepto, fk_idCabVenta, fk_idCaja,monto, tipoOperacion) values(?,?,?,?,?)";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert,PreparedStatement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, detCaja.getConcepto());
            if (detCaja.getDetalleOperacion() != null) {
                sentencia.setInt(2, detCaja.getDetalleOperacion().getId());
            } else {
                sentencia.setNull(2, java.sql.Types.NULL);
            }
            sentencia.setInt(3, detCaja.getCaja().getIdCaja());
            sentencia.setFloat(4, detCaja.getMonto());
            sentencia.setString(5, detCaja.getTipoOperacion());
            sentencia.execute();
            rs = sentencia.getGeneratedKeys();
            if (rs != null && rs.next()) {
                key = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return key;
        }
    }
    
    public void modificarDetalle(DetCaja detCaja) throws SQLException {
        Conexion oCon = new Conexion();
        oCon.getConexion();
        String insert = "Update detallecaja set concepto = ?, monto = ? where iddetallecaja = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            sentencia.setString(1, detCaja.getConcepto());
            sentencia.setFloat(2, detCaja.getMonto());
            sentencia.setInt(3, detCaja.getId());
            sentencia.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            
        }
    }
    
    public void eliminarDetalle(int id) throws SQLException{
        Conexion oCon = new Conexion();
        oCon.getConexion();
        String eliminar = "delete from detallecaja where iddetallecaja = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(eliminar);
            sentencia.setInt(1, id);
            sentencia.execute();
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
        }
    }

    public DetCaja traeUltimoDetalleAper() throws SQLException {
        Conexion oCon = new Conexion();
        oCon.getConexion();
        ResultSet rs = null;
        DetCaja detCaja = null;
        String select = "select * from detallecaja d \n"
                + "join caja c on d.fk_idcaja = c.idcaja\n"
                + "where c.abierta = true\n"
                + "and concepto like '%Apertura%'";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                detCaja = new DetCaja(rs.getByte(1), rs.getFloat(4), rs.getString(3), new Caja(rs.getInt(2)), null, rs.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return detCaja;
        }

    }

    public void cerrarCaja(Date ahora) throws SQLException {
        Conexion oCon = new Conexion();
        oCon.getConexion();
        java.sql.Date sqldate = new java.sql.Date(ahora.getTime());
        String insert = "update caja set fechacierre = ?,abierta = false where abierta = true";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            sentencia.setDate(1, sqldate);
            sentencia.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
        }
    }

    public int abrirCaja(Date ahora, Usuario user) throws SQLException {
        Conexion oCon = new Conexion();
        oCon.getConexion();
        java.sql.Date sqldate = new java.sql.Date(ahora.getTime());
        int llave = 0;
        String insert = "insert into caja(fechaapertura, fechacierre, abierta, fk_idusuario) values(?,null,1,?)";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            sentencia.setDate(1, sqldate);
            sentencia.setInt(2, user.getId());
            sentencia.execute();
            ResultSet rs = sentencia.getGeneratedKeys();
            if (rs != null && rs.next()) {
                llave = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return llave;
        }
    }
    
    public Caja traerUltimoCierre() throws SQLException{
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        Usuario usuario = null;
        Caja caja = null;
        oCon.getConexion();
        String select = "Select c.idCaja, c.fechaapertura, c.fechacierre, c.abierta, c.fk_idusuario, u.nombre, u.password, u.fk_idRoles, r.descripcion from caja c \n" +
                        "join usuario u on c.fk_idusuario = u.idusuario\n" +
                        "join roles r on u.fk_idroles = idroles\n" +
                        "where abierta = false \n" +
                        "order by fechacierre desc\n" +
                        "limit 1";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                usuario = new Usuario(rs.getInt(5), rs.getString(6), rs.getString(7), rs.getInt(8));
                caja = new Caja(rs.getInt(1), rs.getDate(2), null, rs.getBoolean(4), usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return caja;
        }
    }
    
    public ConcurrentHashMap traeDetallePorCaja(int idcaja) throws SQLException {
        Conexion oCon = new Conexion();
        oCon.getConexion();
        ResultSet rs = null;
        ConcurrentHashMap detCaja = new ConcurrentHashMap();
        String select = "select * from detallecaja where fk_idcaja = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            sentencia.setInt(1, idcaja);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                detCaja.put(rs.getInt(1), new DetCaja(rs.getInt(1), rs.getFloat(4), rs.getString(3), new Caja(rs.getInt(2)), null, rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return detCaja;
        }
    }
}
