/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dennis
 */

public class Proveedores {
    Connection con;
    Statement st;
    ResultSet rs;
    
   public Connection  Conectar(){
       
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendajuegos?serverTimezone=UTC", "root", "root");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error " + e);
        }

        return con;
   }
    
   
      public DefaultTableModel MostrarProveedores(){
       
        Connection con=Conectar();
        String []  nombresColumnas = {"id_provedor","nombre_compania","nombre_gerente","direccion","cuidad","telefono"};
        String [] registros = new String[6];
        
        DefaultTableModel modelo =  new DefaultTableModel(null,nombresColumnas);;
        try{
            st=con.createStatement();
            rs=st.executeQuery("Select * from empresa_proveedor");
          
            while(rs.next()){
                 registros[0] = rs.getString("id_provedor");
                 registros[1] = rs.getString("nombre_compania");
                 registros[2] = rs.getString("nombre_gerente");
                 registros[3] = rs.getString("direccion"); 
                 registros[4] = rs.getString("cuidad"); 
                 registros[5] = rs.getString("telefono");
                 
                modelo.addRow(registros);
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);  
        } 
        return modelo;
    }
   
   public String obtenerProveedores(String id){
        Connection con=Conectar();
        String [] registros = new String[2];
         try{
            st=con.createStatement();
            rs=st.executeQuery("Select * from empresa_proveedor where id_provedor='"+id+"'");
          
            while(rs.next()){
                 registros[0] = rs.getString("id_provedor");
                 registros[1] = rs.getString("nombre_compania");
                 registros[2] = rs.getString("nombre_gerente");
                 registros[3] = rs.getString("direccion"); 
                 registros[4] = rs.getString("cuidad"); 
                 registros[5] = rs.getString("telefono");
                
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
            
        } 
       return registros[0]+" "+registros[1]+" "+registros[2]+" "+registros[3]+" "+ registros[4] + " " + registros[5];
       
   }
   
   public void insertarProveedores(String id_provedor,String nombre_compania,String nombre_gerente,
           String direccion,String cuidad,String telefono){
       
       Connection con=Conectar();
       try{
            PreparedStatement pr= con.prepareStatement("Insert into empresa_proveedor values (?,?,?,?,?,?)");
            pr.setString(1, id_provedor);
            pr.setString(2, nombre_compania);
            pr.setString(3, nombre_gerente);
            pr.setString(4, direccion);
            pr.setString(5, cuidad);
            pr.setString(6, telefono);
              
            pr.executeUpdate();
            
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, "Error " + e);
       } 
       
       
   }
      public void modificarProveedor(String id_provedor,String nombre_compania,String nombre_gerente,
           String direccion,String cuidad,String telefono) {
        Connection con = Conectar();
        try {
            String sql = "UPDATE empresa_proveedor SET id_provedor='" + id_provedor + "',nombre_compania='" + nombre_compania
                    + "',nombre_gerente='" + nombre_gerente + "',direccion='" + direccion
                    + "',cuidad='" + cuidad + "',telefono='" + telefono
                    + "' WHERE id_provedor='" + id_provedor + "'";

            PreparedStatement pr = con.prepareStatement(sql);
            int respuesta = pr.executeUpdate(sql);
            if (respuesta > 0) {
                JOptionPane.showMessageDialog(null, "Proveedor Modificado con exito");
            } else {
                JOptionPane.showMessageDialog(null, "Proveedor No Modificado");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }

    }
   public void eliminarProveedores(String id_provedor){
       Connection con=Conectar();
       try{
            PreparedStatement pr= con.prepareStatement("delete from empresa_proveedor where id_provedor='"+id_provedor+"'");
              
            pr.executeUpdate();
            
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, "Error " + e);
       } 
       
       
   }
   
}
