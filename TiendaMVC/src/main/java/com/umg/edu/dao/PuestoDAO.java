package com.umg.edu.dao;

import com.umg.edu.config.ConexionDB;
import com.umg.edu.modelo.Puesto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de la tabla puestos. Maneja nombre y salario base.
 *
 * @author Luis
 */
public class PuestoDAO implements CrudDAO<Puesto> {

    @Override
    public boolean insertar(Puesto puesto) {
        String sql = "INSERT INTO puestos (nombre, salario_base) VALUES (?, ?)";
        try {
            Connection conn = ConexionDB.IniciarConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            //Agregar parametros a la consulta
            ps.setString(1, puesto.getNombre());
            ps.setDouble(2, puesto.getSalarioBase());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar el puesto: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean actualizar(Puesto puesto) {
        String sql = "UPDATE puestos SET nombre = ?, salario_base = ? WHERE id_puesto = ?";
        try {
            Connection conn = ConexionDB.IniciarConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            //Agregar parametros a la consulta
            ps.setString(1, puesto.getNombre());
            ps.setDouble(2, puesto.getSalarioBase());
            ps.setInt(3, puesto.getIdPuesto());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar el puesto: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean eliminar(Puesto puesto) {
        String sql = "DELETE FROM puestos WHERE id_puesto = ?";
        try {
            Connection conn = ConexionDB.IniciarConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, puesto.getIdPuesto());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar el puesto: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Puesto buscarPorId(int id) {
        String sql = "SELECT id_puesto, nombre, salario_base FROM puestos WHERE id_puesto = ?";
        try {
            Connection conn = ConexionDB.IniciarConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            //Agregar parametros a la consulta
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return mapearPuesto(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el puesto: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Puesto> listarTodos() {
        List<Puesto> puestos = new ArrayList<>();
        String sql = "SELECT id_puesto, nombre, salario_base FROM puestos";
        try {
            Connection conn = ConexionDB.IniciarConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                puestos.add(mapearPuesto(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los puestos: " + e.getMessage());
        }
        return puestos;
    }

    // Convierte una fila del ResultSet en un objeto Puesto
    private Puesto mapearPuesto(ResultSet rs) {
        try {
            return new Puesto(
                    rs.getInt("id_puesto"),
                    rs.getString("nombre"),
                    rs.getDouble("salario_base")
            );
        } catch (SQLException e) {
            System.out.println("Error al momento de leer el puesto: " + e.getMessage());
        }
        return null;
    }
}
