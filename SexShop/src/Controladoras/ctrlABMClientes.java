/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladoras;

import DAO.BDClientes;
import Modelos.Cliente;
import Modelos.DescuentoCli;
import Modelos.Estado;
import java.sql.Date;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Valtanders
 */
public class ctrlABMClientes {
    BDClientes bdclientes = BDClientes.getIntance();
    
    
    public int AgregarCliente(int desccli, String notas, String nom, String ape, String direc, String mail, String tel, String dni,Date fecha, int est) throws SQLException{
         return bdclientes.agregar(new Cliente(new DescuentoCli(desccli), notas, nom, ape, direc, mail, tel , dni, fecha,new Estado(est)));
    }
    
    public Cliente ModificarCliente(int id,DescuentoCli descli,String notas,String nom,String ape,String dire,String email,String telefono,String dni,Date fechanac ,Estado est) throws SQLException{
        return bdclientes.modificar(new Cliente(id,descli,notas,nom,ape,dire,email,telefono,dni, fechanac,est));
    }
    
    public void EliminarCliente(int id) throws SQLException {
        bdclientes.eliminar(id);
    }
    
    public ConcurrentHashMap TraerTodos() throws SQLException {        
        return bdclientes.traerTodos();
    }
        
    public DescuentoCli traerDescuento(int id) throws SQLException{
        return bdclientes.traerDecuento(id);
    }
    
    
    public Cliente traerPorID(int id) throws SQLException{
        return bdclientes.traeclienteporid(id);
    }
    
    public ConcurrentHashMap traerDescuentos() throws SQLException{
        return bdclientes.traerDescuentos();
    }
    
    public Estado traeEstado (int id) throws SQLException{
        return bdclientes.TraeEstado(id);
    }
    
    public ConcurrentHashMap traeCumpleanieros() throws SQLException {
        return bdclientes.traeCumpleanieros();
    }

    public ConcurrentHashMap traeHistorico() throws SQLException{
        return bdclientes.traeHistorico();
    }
    
    public ConcurrentHashMap traeHistoricoPorCliente(int id) throws SQLException {
        return bdclientes.traeHistoricoPorCliente(id);
    }
    
    public ConcurrentHashMap traePendientePorCliente(int id) throws SQLException {
        return bdclientes.traePendientePorCliente(id);
    }
}
