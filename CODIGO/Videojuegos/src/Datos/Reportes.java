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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dennis
 */
public class Reportes {
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
   
   //Genera un reporte sobre los empleados en un año y mes especifico
  public DefaultTableModel  ventasEmpleadoMes(String mes, String año){
         Connection con=Conectar();
        
        
        String []  nombresColumnas = {"ID","Puesto","Nombre","Cantida de ventas","Monto total"};
        String [] registros = new String[5];
        
        DefaultTableModel modelo =  new DefaultTableModel(null,nombresColumnas);;
        try{
            st=con.createStatement();
            rs=st.executeQuery("select id_empleado, puesto, concat(nombre,' ', primerapellido,' ', segundoapellido) as nombre,\n" +
            "(\n" +
            "select count(v.id_venta) from ventas v  where v.id_venta=id_venta and month(fecha)='"+mes+"' and year(fecha)='"+año+"' \n" +
            ") as cantidadVentas,\n" +
            "(\n" +
            "select sum(v.monto_total) from ventas v  where v.id_venta=id_venta and month(fecha)='"+mes+"' and year(fecha)='"+año+"' \n" +
            ") as montoTotal\n" +
            "from empleados order by montoTotal desc");
          
            while(rs.next()){
                 registros[0] = rs.getString("id_empleado");
                registros[1] = rs.getString("puesto");
                registros[2] = rs.getString("nombre");
                registros[3] = rs.getString("cantidadVentas");
                registros[4] = rs.getString("montoTotal");
                modelo.addRow(registros);
            }
            con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        } 
        
        return modelo;
    
    } 
    //Genera un reporte sobre los productos de una cayegoria en un periodo de tiempo
   public DefaultTableModel  cantidadProductoCategoria(String id,String inicio, String termino){
         Connection con=Conectar();
        
        
        String []  nombresColumnas = {"ID","Nombre","Precio","Unidades en A.","Tipo","Cantidad venida", "Monto total"};
        String [] registros = new String[7];
        
        DefaultTableModel modelo =  new DefaultTableModel(null,nombresColumnas);;
        try{
            st=con.createStatement();
            rs=st.executeQuery("select p.id_producto, p.nombre, p.precio, p.exitencias, c.tipo,\n" +
            "(\n" +
            "select sum(dv.cantidad) from detalles_venta dv where dv.id_producto=p.id_producto \n" +
            "and dv.fecha between '"+inicio+"' and '"+termino+"'\n" +
            ") as caantidadProductos,\n" +
            "(\n" +
            "select sum(dv.cantidad*p.precio) from detalles_venta dv where dv.id_producto=p.id_producto \n" +
            "and dv.fecha between '"+inicio+"' and '"+termino+"'\n" +
            ") as montoTotal\n" +
            "from productos p natural join categorias c where p.id_categoria='"+id+"'\n" +
            "order by montoTotal desc");
          
            while(rs.next()){
                 registros[0] = rs.getString("p.id_producto");
                registros[1] = rs.getString("p.nombre");
                registros[2] = rs.getString("p.precio");
                registros[3] = rs.getString("p.exitencias");
                registros[4] = rs.getString("c.tipo");
                registros[5] = rs.getString("caantidadProductos");
                registros[6] = rs.getString("montoTotal");
                
                modelo.addRow(registros);
            }
            con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        } 
        
        return modelo;
    
    } 
   
   //Gener un reporte sobre las ventas en un periodo de tiempo
   public DefaultTableModel  ventasPorPeriodo(String inicio, String fin){
         Connection con=Conectar();
        
        
        String []  nombresColumnas = {"ID","Fecha","Monto","Nombre del empleado"};
        String [] registros = new String[4];
        
        DefaultTableModel modelo =  new DefaultTableModel(null,nombresColumnas);;
        try{
            st=con.createStatement();
            rs=st.executeQuery("select v.id_venta, v.fecha, v.monto_total, concat(e.nombre,' ',e.primerapellido,' ',e.segundoapellido) \n" +
            "as nombreEmpleado\n" +
            "from empleados e natural join ventas v where fecha between '"+inicio+"' and '"+fin+"'");
          
            while(rs.next()){
                 registros[0] = rs.getString("id_venta");
                registros[1] = rs.getString("fecha");
                registros[2] = rs.getString("monto_total");
                registros[3] = rs.getString("nombreEmpleado");
                modelo.addRow(registros);
            }
            con.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        } 
        
        return modelo;
    
    } 
   
}
