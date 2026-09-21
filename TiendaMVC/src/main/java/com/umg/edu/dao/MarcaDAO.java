package com.umg.edu.dao;

import com.umg.edu.config.ConexionDB;
import com.umg.edu.modelo.Marca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de la tabla marcas. Mismo patron que ClienteDAO pero con los campos
 * que le tocan a esta tabla (solo nombre).
 *
 * @author Luis
 */
public class MarcaDAO implements CrudDAO<Marca> {

    @Override
    public boolean insertar(Marca marca) {
        String sql = "INSERT INTO marcas (nombre) VALUES (?)";
        try {
            Connection conn = ConexionDB.IniciarConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            //Agregar parametros a la consulta
            ps.setString(1, marca.getNombre());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar la marca: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean actualizar(Marca marca) {
        String sql = "UPDATE marcas SET nombre = ? WHERE id_marca = ?";
        try {
            Connection conn = ConexionDB.IniciarConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            //Agregar parametros a la consulta
            ps.setString(1, marca.getNombre());
            ps.setInt(2, marca.getIdMarca());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar la marca: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean eliminar(Marca marca) {
        String sql = "DELETE FROM marcas WHERE id_marca = ?";
        try {
            Connection conn = ConexionDB.IniciarConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, marca.getIdMarca());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar la marca: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Marca buscarPorId(int id) {
        String sql = "SELECT id_marca, nombre FROM marcas WHERE id_marca = ?";
        try {
            Connection conn = ConexionDB.IniciarConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            //Agregar parametros a la consulta
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return mapearMarca(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la marca: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Marca> listarTodos() {
        List<Marca> marcas = new ArrayList<>();
        String sql = "SELECT id_marca, nombre FROM marcas";
        try {
            Connection conn = ConexionDB.IniciarConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                marcas.add(mapearMarca(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar las marcas: " + e.getMessage());
        }
        return marcas;
    }

    // Convierte una fila del ResultSet en un objeto Marca
    private Marca mapearMarca(ResultSet rs) {
        try {
            return new Marca(
                    rs.getInt("id_marca"),
                    rs.getString("nombre")
            );
        } catch (SQLException e) {
            System.out.println("Error al momento de leer la marca: " + e.getMessage());
        }
        return null;
    }
}
