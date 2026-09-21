package com.umg.edu.dao;

import com.umg.edu.config.ConexionDB;
import com.umg.edu.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de la tabla clientes. Aqui vive TODO el SQL de clientes, asi el
 * formulario no sabe nada de la base de datos.
 *
 * @author Luis
 */
public class ClienteDAO implements CrudDAO<Cliente> {

    @Override
    public boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nit, nombre, apellidos, email) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = ConexionDB.IniciarConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            //Agregar parametros a la consulta
            ps.setString(1, cliente.getNit());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellidos());
            ps.setString(4, cliente.getEmail());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar el cliente: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nit = ?, nombre = ?, apellidos = ?, email = ? WHERE id_cliente = ?";
        try {
            Connection conn = ConexionDB.IniciarConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            //Agregar parametros a la consulta
            ps.setString(1, cliente.getNit());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellidos());
            ps.setString(4, cliente.getEmail());
            ps.setInt(5, cliente.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar el cliente: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean eliminar(Cliente cliente) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";
        try {
            Connection conn = ConexionDB.IniciarConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar el cliente: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Cliente buscarPorId(int id) {
        String sql = "SELECT id_cliente, nit, nombre, apellidos, email FROM clientes WHERE id_cliente = ?";
        try {
            Connection conn = ConexionDB.IniciarConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            //Agregar parametros a la consulta
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return mapearCliente(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el cliente: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nit, nombre, apellidos, email FROM clientes";
        try {
            Connection conn = ConexionDB.IniciarConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los clientes: " + e.getMessage());
        }
        return clientes;
    }

    // Convierte una fila del ResultSet en un objeto Cliente
    private Cliente mapearCliente(ResultSet rs) {
        try {
            return new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nit"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("email")
            );
        } catch (SQLException e) {
            System.out.println("Error al momento de leer el cliente: " + e.getMessage());
        }
        return null;
    }
}
