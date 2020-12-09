/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.sql.CallableStatement;
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
public class Categorias {

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

    public DefaultTableModel MostrarCategorias() {

        Connection con = Conectar();
        String[] nombresColumnas = {"ID", "Tipo De Categoria"};
        String[] registros = new String[2];

        DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);;
        try {
            st = con.createStatement();
            rs = st.executeQuery("Select * from categorias");

            while (rs.next()) {
                registros[0] = rs.getString("id_categoria");

                registros[1] = rs.getString("tipo");
                modelo.addRow(registros);
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error " + e);

        }

        return modelo;

    }
//metodo de busqueda

    public String obtenerCategoria(String id) {
        Connection con = Conectar();
        String[] registros = new String[2];
        try {
            st = con.createStatement();
            rs = st.executeQuery("Select * from categorias where id_categoria='" + id + "'");

            while (rs.next()) {
                registros[0] = rs.getString("id_categoria");

                registros[1] = rs.getString("tipo");

            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);

        }
        return registros[0] + " " + registros[1];

    }
//Metodo Insertar
    public void insertarCategoriaProcedimiento(String id, String tipo) {
        Connection con = Conectar();
        try {
            CallableStatement pr = con.prepareCall("{call agregarCategoria (?,?)}");
            pr.setString(1, id);
            pr.setString(2, tipo);

            pr.executeUpdate();

        } catch (Exception e) {

        }

    }
//Metodo Actualizar
    public void actualizarCategoriaProcedimiento(String id, String tipo) {
        Connection con = Conectar();
        try {
            CallableStatement pr = con.prepareCall("{call actualizarCategoria (?,?)}");
            pr.setString(1, id);
            pr.setString(2, tipo);

            pr.executeUpdate();

        } catch (Exception e) {

        }

    }
//Metodo Eliminar
    public void eliminarCategoriaProcedimiento(String id) {
        Connection con = Conectar();
        try {
            CallableStatement pr = con.prepareCall("{call eliminarCategoria (?)}");
            pr.setString(1, id);
            pr.executeUpdate();

            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
