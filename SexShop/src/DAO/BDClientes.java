/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import Modelos.Cliente;
import Modelos.DescuentoCli;
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
        String insert = "INSERT INTO cliente (fk_idDescuentoCli,notas,nombre,apellido,direccion,mail,telefono,dni,fechanac) Values (?,?,?,?,?,?,?,?,?)";
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
    public void modificar(Object valor) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        String insert = "SELECT * FROM cliente INNER JOIN descuentocli ON idDescuentoCli = fk_idDescuentoCli";
        try {
            PreparedStatement sentencia = (PreparedStatement) oCon.getConexion().prepareStatement(insert);
            rs = sentencia.executeQuery();

            Cliente cliente;
            DescuentoCli desc;
            while (rs.next()) {
                desc = new DescuentoCli(rs.getInt(11), rs.getString(12), rs.getInt(13), rs.getFloat(14));
                cliente = new Cliente(rs.getInt(1), desc, rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getDate(10));
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
}
