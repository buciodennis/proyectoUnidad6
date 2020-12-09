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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Dennis
 */
public class Detalle {
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
   
   //Metodo para mostrar los datos de la tabla detalles
   public DefaultTableModel  MostrarDetalles(){
         Connection con=Conectar();
        
        
        String []  nombresColumnas = {"ID Producto","ID Venta","Fecha","Cantidad","Importe"};
        String [] registros = new String[5];
        
        DefaultTableModel modelo =  new DefaultTableModel(null,nombresColumnas);
        try{
            st=con.createStatement();
            rs=st.executeQuery("Select * from detalles_venta");
          
            while(rs.next()){
                 registros[0] = rs.getString("id_producto");
                registros[1] = rs.getString("id_venta");
                registros[2] = rs.getString("Fecha");
                registros[3] = rs.getString("Cantidad");
                registros[4] = rs.getString("Importe");
                modelo.addRow(registros);
            }
            con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        } 
        
        return modelo;
    
    }
//  //Metodo para obtener los datos 
   public String obtenerDetalle(String idproducto) {
        Connection con = Conectar();
        String[] registros = new String[2];
        try {
            st = con.createStatement();
            rs = st.executeQuery("Select * from detalles_venta where id_producto='" + idproducto + "'");

            while (rs.next()) {
                registros[0] = rs.getString("id_producto");
                registros[1] = rs.getString("id_venta");
                registros[2] = rs.getString("fecha");
                registros[3] = rs.getString("cantidad");
                registros[4] = rs.getString("importe");
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
        return registros[0] + " " + registros[1] + " " + registros[2] + " " + registros[3] + " "
                + registros[4];

    }
   //Metodo para agregar los datos a la tabla detalles
    public void agregarDetalle(String idproducto, String idventa, String fecha,
            String cantidad,String importe){
      Connection con=Conectar();
     
       try{
           con.setAutoCommit(false);
            PreparedStatement pr= con.prepareStatement("Insert into detalles_venta values (?,current_date(),?,?)");
            
            pr.setString(1, idproducto);
            pr.setString(2, idventa);
            pr.setString(3, fecha);
            pr.setString(4, cantidad);
            pr.setString(5, importe);
            pr.executeUpdate();
            
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }         
       
   } 
    //Metodo para modificar los datos agregados a la tabla detalles y actualizarlos
    public void modificarDetalle(String idproducto, String idventa, String fecha,
            String cantidad,String importe) {
        Connection con = Conectar();
        try {
            String sql = "Update ventas SET id_productos='"+idproducto+ "', id_venta='"+idventa+"', fecha='"+fecha+"' WHERE id_producto='"+idproducto+"'" ;
            PreparedStatement pr = con.prepareStatement(sql);
            int respuesta = pr.executeUpdate(sql);
            if (respuesta>0) {
                JOptionPane.showMessageDialog(null, "Detalle Modificado");
            }else{
                JOptionPane.showMessageDialog(null, "Detalle No Modificado");
            }

        } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
        }

    }
    
    
    //Metodo para eliminar y 
    public void eliminarDetalle(String idproducto){
       Connection con=Conectar();
       try{
            PreparedStatement pr= con.prepareStatement("delete from detalles_venta where id_producto='"+idproducto+"'");
              
            pr.executeUpdate();
            
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
        
       } 
}
}
