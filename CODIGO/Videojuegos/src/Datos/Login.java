/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Dennis
 */
public class Login {
    Connection con;
    Statement st;
    ResultSet rs;
      
    
   //Metodo para hacer la conexion a la base de datos  
   public Connection  Conectar(){
       
    try{
             
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendajuegos?serverTimezone=UTC", "root", "root");
            
             
         }catch(Exception e){
             System.out.println("error "+ e);
         }
       
    return con;
   }
    
    
    
    //Recibe un usuario y una contraseña para verificarlas en la base de datos
   //Busca la contraseña encriptada correspondiente al usuario, y encripta la contraseña que el usuario ingreso
   // compara ambass contraseñas encriptadas
    public boolean ingresrar(String usuario, String contraseña){
       Connection con=Conectar();
       String [] registros = new String[2];
       try{
            st=con.createStatement();
            rs=st.executeQuery("Select usuarioLogin, contraseña from empleados where usuarioLogin='"+usuario+"'");
          
            while(rs.next()){
                 
                registros[0]=rs.getString("usuarioLogin");
                registros[1]=rs.getString("contraseña");
                
                
            }
            rs=st.executeQuery("Select sha1('"+contraseña+"')");
            String contraseñaComp="";
       
            while(rs.next()){

                     contraseñaComp=rs.getString(1);


             }
            
//           System.out.println(registros[0]);
//           System.out.println(registros [1]);
//           System.out.println(contraseñaComp);       
            
           if(registros[0].equals(usuario) && registros[1].equals(contraseñaComp)){
              return true;
           }else{
               JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
               return false;
           }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Usuario no encontrado");
            return false;
                
        }
   
   
   } 
    
}
