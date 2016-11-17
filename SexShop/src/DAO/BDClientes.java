/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import Modelos.Cliente;
import Modelos.DescuentoCli;
import Modelos.Estado;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.PreparedStatement;
import java.util.concurrent.ConcurrentHashMap;
/**
 *
 * @author Valtanders
 */
public class BDClientes implements Interfaz{
    
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
        String insert = "INSERT INTO cliente (fk_idDescuentoCli,notas,nombre,apellido,direccion,mail,telefono,dni,fechanac,codigoCliente,fk_idEstados) Values (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            sentencia.setInt(1, cliente.getDescuento().getIdDescuentoCli());
            sentencia.setString(2, cliente.getNotas());
            sentencia.setString(3, cliente.getNombre());
            sentencia.setString(4, cliente.getApellido());
            sentencia.setString(5, cliente.getDireccion());
            sentencia.setString(6, cliente.getMail());
            sentencia.setString(7, cliente.getTelefono());
            sentencia.setInt(8,cliente.getDni());
            sentencia.setDate(9, cliente.getFechanac());
            sentencia.setString(10, cliente.getCodigo());
            sentencia.setInt(11,cliente.getEstado().getId());
            sentencia.execute();
            ResultSet rs = sentencia.getGeneratedKeys();
            if (rs != null && rs.next()) {
                llave = rs.getInt(1);
            }
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return llave;
        }
    }

    @Override
    public Cliente modificar(Object valor) throws SQLException {
        Conexion oCon = new Conexion();
        Cliente cliente = (Cliente)valor;
        oCon.getConexion();
        String update = "update cliente set fk_idDescuentoCli = ?,notas = ?,nombre = ?,apellido = ?,direccion = ?,mail = ?,telefono = ?,dni = ?,fechanac = ?,fk_idEstados = ? where idCliente = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(update);
            sentencia.setInt(1,cliente.getDescuento().getIdDescuentoCli());
            sentencia.setString(2, cliente.getNotas());
            sentencia.setString(3, cliente.getNombre());
            sentencia.setString(4, cliente.getApellido());
            sentencia.setString(5, cliente.getDireccion());
            sentencia.setString(6, cliente.getMail());
            sentencia.setString(7, cliente.getTelefono());
            sentencia.setInt(8,cliente.getDni());
            sentencia.setDate(9, cliente.getFechanac());
            sentencia.setInt(10,cliente.getEstado().getId());
            sentencia.setInt(11,cliente.getIdCliente());
            sentencia.execute();
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();            
        } finally {
            oCon.close();
            return cliente;
        }
    }

    @Override
    public void eliminar(Object valor) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ConcurrentHashMap traerTodos() throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        ConcurrentHashMap resp = new ConcurrentHashMap<String, String>();
        oCon.getConexion();
        String insert = "SELECT C.idcliente,C.notas,C.nombre,C.apellido,C.direccion,C.mail,C.telefono,C.dni,C.fechanac,C.codigoCliente, DC.idDescuentoCli, DC.descripcion, DC.porcentaje, DC.importe,E.idEstados,E.Descripcion  FROM cliente C INNER JOIN descuentocli DC ON idDescuentoCli = C.fk_idDescuentoCli INNER JOIN estados E on idEstados = C.fk_idEstados";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            rs = sentencia.executeQuery();

            Cliente cliente;
            DescuentoCli desc;
            Estado est;
            while (rs.next()) {
                est = new Estado(rs.getInt(15),rs.getString(16));
                desc = new DescuentoCli(rs.getInt(11), rs.getString(12), rs.getInt(13), rs.getFloat(14));
                cliente = new Cliente(rs.getInt(1), desc, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getDate(9),rs.getString(10),est);
                resp.put(cliente.getIdCliente(), cliente);
            }
            rs.close();
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return resp;
        }
    }
    
    public DescuentoCli traerDecuento(int id) throws SQLException{
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        DescuentoCli desccli = null;
        oCon.getConexion();
        String insert = "SELECT * FROM DescuentoCli where idDescuentoCli = ?";
        try{
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            sentencia.setInt(1, id);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                desccli = new DescuentoCli(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getFloat(4));
            }
            rs.close();
            sentencia.close();
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return desccli;
        }
    
    }
    
    public boolean BuscaCodigo(String cod) throws SQLException {
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        String codigo = "";
        boolean codrepetido = false;
        oCon.getConexion();
        String select = "Select codigoCliente from cliente where codigoCliente = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            sentencia.setString(1, cod);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                codigo = rs.getString(1);
            }
            if (!codigo.equals(""))
                codrepetido = true;
        } catch (SQLException e) {
            e.printStackTrace();            
        } finally {
            oCon.close();
            return codrepetido;
        }
    }
    
    public Cliente traeclienteporid(int id) throws SQLException{
        Conexion oCon = new Conexion();
        ResultSet rs = null;
        Cliente cliente = null;
        Estado est = null;
        DescuentoCli desc = null;
        oCon.getConexion();
        String select = "SELECT C.idcliente,C.notas,C.nombre,C.apellido,C.direccion,C.mail,C.telefono,C.dni,C.fechanac,C.codigoCliente, DC.idDescuentoCli, DC.descripcion, DC.porcentaje, DC.importe,E.idEstados,E.Descripcion  FROM cliente C INNER JOIN descuentocli DC ON idDescuentoCli = C.fk_idDescuentoCli INNER JOIN estados E on idEstados = C.fk_idEstados where C.idcliente = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            sentencia.setInt(1, id);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                est = new Estado(rs.getInt(15),rs.getString(16));
                desc = new DescuentoCli(rs.getInt(11), rs.getString(12), rs.getInt(13), rs.getFloat(14));
                cliente = new Cliente(rs.getInt(1), desc, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getDate(9),rs.getString(10),est);
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
        try{
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(select);
            rs = sentencia.executeQuery();
            while (rs.next()){
                descli = new DescuentoCli(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getFloat(4));
                lista.put(descli.getIdDescuentoCli(), descli);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            oCon.close();
            return lista;
        }
    }
}
