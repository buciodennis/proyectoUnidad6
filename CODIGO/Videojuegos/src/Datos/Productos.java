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
public class Productos {

    Connection con;
    Statement st;
    ResultSet rs;

    public Connection Conectar() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendajuegos?serverTimezone=UTC", "root", "root");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error " + e);
        }

        return con;
    }

    public DefaultTableModel MostrarProductos() {

        Connection con = Conectar();
        String[] nombresColumnas = {"ID Producto", "Nombre", "Descripcion","Precio",
            "Existencia", "ID Categoria", "ID Proveedor"};
        String[] registros = new String[7];

        DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);;
        try {
            st = con.createStatement();
            rs = st.executeQuery("Select * from productos");

            while (rs.next()) {
                registros[0] = rs.getString("id_producto");
                registros[1] = rs.getString("Nombre");
                registros[2] = rs.getString("descripcion");
                registros[3] = rs.getString("precio");
                registros[4] = rs.getString("exitencias");
                registros[5] = rs.getString("id_categoria");
                registros[6] = rs.getString("id_provedor");
                modelo.addRow(registros);
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);

        }

        return modelo;

    }
//metodo de busqueda

    public String obtenerProducto(String id) {
        Connection con = Conectar();
        String[] registros = new String[2];
        try {
            st = con.createStatement();
            rs = st.executeQuery("Select * from productos where id_producto='" + id + "'");

            while (rs.next()) {
                registros[0] = rs.getString("id_producto");
                registros[1] = rs.getString("Nombre");
                registros[2] = rs.getString("descripcion");
                registros[3] = rs.getString("precio");
                registros[4] = rs.getString("exitencias");
                registros[5] = rs.getString("id_categoria");
                registros[6] = rs.getString("id_provedor");

            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
        return registros[0] + " " + registros[1] + " " + registros[2] + " " + registros[3] + " "
                + registros[4] + " " + registros[5] + " " + registros[6];

    }

    public void insertarProducto(String id, String nombre, String descripcion, String precio,
            String existencia, String idCategoria, String idProveedor) {
        Connection con = Conectar();
        try {
            PreparedStatement pr = con.prepareStatement("INSERT INTO productos values (?,?,?,?,?,?,?)");
            pr.setString(1, id);
            pr.setString(2, nombre);
            pr.setString(3, descripcion);
            pr.setString(4, precio);
            pr.setString(5, existencia);
            pr.setString(6, idCategoria);
            pr.setString(7, idProveedor);

            pr.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }

    }

    public void modificarProducto(String id, String nombre, String descripcion, String precio,
            String existencia, String idCategoria, String idProveedor) {
        Connection con = Conectar();
        try {
            String sql = "UPDATE productos SET id_producto='" + id + "',Nombre='" + nombre
                    + "',descripcion='" + descripcion + "',precio='" + precio
                    + "',exitencias='" + existencia + "',id_categoria='" + idCategoria
                    + "',id_provedor='" + idProveedor + "' WHERE id_producto='" + id + "'";

            PreparedStatement pr = con.prepareStatement(sql);
            int respuesta = pr.executeUpdate(sql);
            if (respuesta > 0) {
                JOptionPane.showMessageDialog(null, "Categoria Modificada");
            } else {
                JOptionPane.showMessageDialog(null, "Categoria No Modificada");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }

    }

    public void eliminarProducto(String id) {
        Connection con = Conectar();
        try {
            PreparedStatement pr = con.prepareStatement("delete from productos where id_producto='" + id + "'");
            pr.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }

    }
}
