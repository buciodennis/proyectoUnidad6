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
public class Empleados {
    Connection con;
    Statement st;
    ResultSet rs;
    
   public Connection  Conectar(){
       
    try{
             
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendajuegos?serverTimezone=UTC", "root", "root");
            
             
         }catch(Exception e){
             System.out.println("error "+ e);
         }
       
    return con;
   }
    
   
   public DefaultTableModel  MostrarEmpleados(){
         Connection con=Conectar();
        
        
        String []  nombresColumnas = {"ID","Nombre","Primer Apellido","Segundo apellido",
        "Puesto","Dirección","Ciudad","Telefono","UsuarioLogin"};
        String [] registros = new String[9];
        
        DefaultTableModel modelo =  new DefaultTableModel(null, nombresColumnas);;
        try{
            st=con.createStatement();
            rs=st.executeQuery("Select * from empleados");
          
            while(rs.next()){
                registros[0] = rs.getString("id_empleado");
                registros[1] = rs.getString("Nombre");
                registros[2] = rs.getString("PrimerApellido");
                registros[3] = rs.getString("SegundoApellido");
                registros[4] = rs.getString("Puesto");
                registros[5] = rs.getString("Direccion");
                registros[6] = rs.getString("Ciudad");
                registros[7] = rs.getString("Telefono");
                registros[8] = rs.getString("UsuarioLogin");
               
                modelo.addRow(registros);
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
        } 
        
        return modelo;
    
    }
   
   public String obtenerEmpleado(String id){
        Connection con=Conectar();
        String [] registros = new String[10];
         try{
            st=con.createStatement();
            rs=st.executeQuery("Select * from empleados where id_empleado='"+id+"'");
          
            while(rs.next()){
               registros[0] = rs.getString("id_empleado");
               registros[1] = rs.getString("Nombre");
               registros[2] = rs.getString("PrimerApellido");
               registros[3] = rs.getString("SegundoApellido");
               registros[4] = rs.getString("Puesto");
               registros[5] = rs.getString("Direccion");
               registros[6] = rs.getString("Ciudad");
               registros[7] = rs.getString("Telefono");
               registros[8] = rs.getString("UsuarioLogin");
               registros[9] = rs.getString("ContraseñaLogin");
            }
            con.close();
        }catch(Exception e){
            System.out.println(e);
            
        } 
       return registros[0]+" "+registros[1]+" "+registros[2]+" "+registros[3]+" "+registros[4]
               +" "+registros[5]+" "+registros[6]+" "+registros[7]+" "+registros[8]+" "+registros[9];
       
   }
   
   public void insertarEmpleado(String id,String nombre, String primerAp, String segundoAp, String puesto,
           String direccion, String ciudad, String telefono, String usuarioLogin, String contrasenaLogin){
       Connection con=Conectar();
       try{
            PreparedStatement pr= con.prepareStatement("Insert into empleados values (?,?,?,?,?,?,?,?,?,sha1(?))");
            pr.setString(1, id);
            pr.setString(2, nombre);
            pr.setString(3, primerAp);
            pr.setString(4, segundoAp);
            pr.setString(5, puesto);
            pr.setString(6, direccion);
            pr.setString(7, ciudad);
            pr.setString(8, telefono);
            pr.setString(9, usuarioLogin);
            pr.setString(10, contrasenaLogin);  
            pr.executeUpdate();
            
       }catch(Exception e){
           System.out.println(e);
       } 
       
       
   }
   
   public void modificarEmpleado(String id,String nombre, String primerAp, String segundoAp, String puesto,
           String direccion, String ciudad, String telefono, String usuarioLogin, String contrasenaLogin) {
        Connection con = Conectar();
        try {
            String sql = "Update empleados SET id_empleado='"+id+"', Nombre='"+nombre+"', PrimerApellido='"+ primerAp+"', SegundoApellido='"+segundoAp+"',Puesto='"+puesto+"',Direccion='"+direccion+"',ciudad='"+ciudad+"',Telefono='"+telefono+"',UsuarioLogin='"+usuarioLogin+"',Contraseña=sha1('"+contrasenaLogin+"') WHERE id_empleado='"+id+"'" ;
            PreparedStatement pr = con.prepareStatement(sql);
            int respuesta = pr.executeUpdate(sql);
            if (respuesta>0) {
                JOptionPane.showMessageDialog(null, "Empleado Modificado");
            }else{
                JOptionPane.showMessageDialog(null, "Empleado No Modificado");
            }

        } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
        }

    }
   
   
   
   
   public void eliminarEmpleado(String id){
       Connection con=Conectar();
       try{
            PreparedStatement pr= con.prepareStatement("delete from empleados where id_empleado='"+id+"'");
              
            pr.executeUpdate();
            
       }catch(Exception e){
           System.out.println(e);
       } 
       
       
   }
   
   
   
   public void ingresra(String usuario, String contraseña){
       Connection con=Conectar();
       String [] registros = new String[2];
       try{
            st=con.createStatement();
            rs=st.executeQuery("Select * from login where usuario='"+usuario+"'");
          
            while(rs.next()){
                 
                registros[0]=rs.getString("usuario");
                registros[1]=rs.getString("contraseña");
                
                
            }
            rs=st.executeQuery("Select sha1('"+contraseña+"')");
            String contraseñaComp="";
       
            while(rs.next()){

                     contraseñaComp=rs.getString(1);


             }
            
           System.out.println(registros[0]);
           System.out.println(registros [1]);
           System.out.println(contraseñaComp);       
            
           if(registros[0].equals(usuario) && registros[1].equals(contraseñaComp)){
               System.out.println("INGRESO");
           }else{
               System.out.println("FALSEEE");
           }
            
        }catch(Exception e){
                System.out.println(e);
        }
   
   
   } 
}
