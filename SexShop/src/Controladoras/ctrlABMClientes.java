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
    
    
<<<<<<< HEAD
    public int AgregarCliente(int desccli, String notas, String nom, String ape, String direc, String mail, String tel, int dni, Date fecha, String codcli, int estado) throws SQLException{
         return bdclientes.agregar(new Cliente(new DescuentoCli(desccli), notas, nom, ape, direc, mail, tel , dni, fecha, codcli, estado));
=======
    public int AgregarCliente(int desccli, String notas, String nom, String ape, String direc, String mail, String tel, int dni, String codigo,Date fecha) throws SQLException{
         return bdclientes.agregar(new Cliente(new DescuentoCli(desccli), notas, nom, ape, direc, mail, tel , dni, fecha, codigo ,new Estado(1)));
>>>>>>> Bruno
    };
    
    public ConcurrentHashMap TraerTodos() throws SQLException
    {        
        return bdclientes.traerTodos();
    }
        
    public DescuentoCli traerDescuento(int id) throws SQLException{
        return bdclientes.traerDecuento(id);
    }

}
