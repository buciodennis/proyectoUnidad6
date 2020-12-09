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
public class Clientes {
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

    public DefaultTableModel MostrarClientes() {

        Connection con = Conectar();
        String[] nombresColumnas = {"ID Cliente", "Nombre", "Apellidos","Fecha Nacimiento",
            "Direccion", "Ciudad", "Telefono"};
        String[] registros = new String[7];

        DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);;
        try {
            st = con.createStatement();
            rs = st.executeQuery("Select * from Clientes");

            while (rs.next()) {
                registros[0] = rs.getString("id_cliente");
                registros[1] = rs.getString("Nombre");
                registros[2] = rs.getString("Apellidos");
                registros[3] = rs.getString("fechaNacimiento");
                registros[4] = rs.getString("Direccion");
                registros[5] = rs.getString("Cuidad");
                registros[6] = rs.getString("Telefono");
                modelo.addRow(registros);
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);

        }

        return modelo;

    }


    public void insertarCliente(String id, String nombre, String apellidos, String fechaNac,
            String direccion, String ciudad, String telefono) {
        Connection con = Conectar();
        try {
            PreparedStatement pr = con.prepareStatement("INSERT INTO Clientes values (?,?,?,?,?,?,?)");
            pr.setString(1, id);
            pr.setString(2, nombre);
            pr.setString(3, apellidos);
            pr.setString(4, fechaNac);
            pr.setString(5, direccion);
            pr.setString(6, ciudad);
            pr.setString(7, telefono);

            pr.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }

    }

    public void modificarCliente(String id, String nombre, String apellidos, String fechaNac,
            String direccion, String ciudad, String telefono) {
        Connection con = Conectar();
        try {
            String sql = "UPDATE clientes SET id_cliente='" + id + "',Nombre='" + nombre
                    + "',apellidos='" + apellidos + "',fechaNacimiento='" + fechaNac
                    + "',direccion='" + direccion + "',cuidad='" + ciudad
                    + "',telefono='" + telefono + "' WHERE id_cliente='" + id + "'";

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

    public void eliminarCliente(String id) {
        Connection con = Conectar();
        try {
            PreparedStatement pr = con.prepareStatement("delete from clientes where id_cliente='" + id + "'");

            pr.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }

    }
}
