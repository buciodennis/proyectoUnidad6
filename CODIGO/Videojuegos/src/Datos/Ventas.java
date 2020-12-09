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
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dennis
 */
public class Ventas {
     Connection con;
    Statement st;
    ResultSet rs;
    
    
   //Metodo para hacer la conexion a la base de datos
   public Connection  Conectar(){
       
    try{
             
             Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendajuegos?serverTimezone=UTC", "root", "root");
            
             
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
       
    return con;
   }
   
   
   //Metodo para obtener los datos de las ventas de la base de datos
   // retorna un DefaultTableModel para asignar a una tabla y visializar los datos
   public DefaultTableModel  MostrarVentas(){
         Connection con=Conectar();
        
        
        String []  nombresColumnas = {"ID","Fecha","IdEmpleado","IdCliente","Monto"};
        String [] registros = new String[5];
        
        DefaultTableModel modelo =  new DefaultTableModel(null,nombresColumnas);;
        try{
            st=con.createStatement();
            rs=st.executeQuery("Select * from ventas");
          
            while(rs.next()){
                 registros[0] = rs.getString("id_venta");
                registros[1] = rs.getString("fecha");
                registros[2] = rs.getString("id_empleado");
                registros[3] = rs.getString("id_cliente");
                registros[4] = rs.getString("monto_total");
                modelo.addRow(registros);
            }
            con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        } 
        
        return modelo;
    
    }
   
   //agregarVenta utiliza una transaccion para agregar una venta y descontar los productos a la tabla productos que se 
   //agregan a la venta, tambien crea los detalles de venta por cada producto que se agrega
   
    public void agregarVenta(ArrayList <ArrayList<String> > productos, String id, String empleado, String cliente, String monto){
      Connection con=Conectar();
     
       try{
           con.setAutoCommit(false);
            PreparedStatement pr= con.prepareStatement("Insert into ventas values (?,current_date(),?,?,?)");
            
            pr.setString(1, id);
            pr.setString(2, empleado);
            pr.setString(3, cliente);
            pr.setString(4, monto);
            pr.executeUpdate();
            
            
          for(int i=0; i<productos.size(); i++){
           String pId=productos.get(i).get(0);
           String cantidad=productos.get(i).get(1);
           String importe=productos.get(i).get(2);
           pr= con.prepareStatement("Update productos  set exitencias=exitencias-'"+cantidad+"' where id_producto='"+pId+"'"); 
           pr.executeUpdate();
           
           pr= con.prepareStatement("Insert into detalles_venta values (?,?,current_date(),?,?)");
                pr.setString(1, pId);
                    pr.setString(2, id);
                pr.setString(3, cantidad);
                pr.setString(4, importe);
                pr.executeUpdate();
              
           
           
           
             
          } 
           con.commit();
            
            
       }catch(Exception e){
           try{
           con.rollback();
           }catch(Exception ex){
               JOptionPane.showMessageDialog(null, e);
           }
         JOptionPane.showMessageDialog(null, e);
       } 
       
       
   } 
    //Metodo para actualizar un registro de venta, recibe el id de la venta que se va a modificar y todos los datos de una venta
    public void modificarVenta(String id, String idN, String empleado, String cliente, String monto) {
        Connection con = Conectar();
        try {
            String sql = "Update ventas SET id_venta='"+idN+ "', id_empleado='"+empleado+"', id_cliente='"+cliente+"', monto_total='"+ monto+"' WHERE id_venta='"+id+"'" ;
            PreparedStatement pr = con.prepareStatement(sql);
            int respuesta = pr.executeUpdate(sql);
            if (respuesta>0) {
                JOptionPane.showMessageDialog(null, "Venta Modificada");
            }else{
                JOptionPane.showMessageDialog(null, "Venta No Modificada");
            }

        } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
        }

    }
    
    
    //Metodo para eliminar, recibe el id de la venta que se desea eliminar
    public void eliminarVenta(String id){
       Connection con=Conectar();
       try{
            PreparedStatement pr= con.prepareStatement("delete from ventas where id_venta='"+id+"'");
              
            pr.executeUpdate();
            
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
        
       } 
       
         
   }
    
    
    
    
       
    
    
   
}
