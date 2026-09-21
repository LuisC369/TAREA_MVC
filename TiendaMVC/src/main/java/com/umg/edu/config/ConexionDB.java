package com.umg.edu.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase de conexión a MySQL mediante JDBC (código compartido en clase).
 *
 * Mantiene UNA sola conexión estática que se reutiliza; el constructor es
 * privado para que nadie pueda hacer "new ConexionDB()".
 *
 * @author Luis
 */
public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/tienda";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "admin";

    private static Connection conexion = null;

    private ConexionDB() {
    }

    public static Connection IniciarConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("La conexion se creo correctamente");
            }
        } catch (SQLException e) {
            System.err.println("Error al momento de crear la conexion: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }

    // Para INSERT / UPDATE / DELETE
    public static boolean ejecutarInstruccion(String sql) {
        try {
            Connection conn = IniciarConexion();
            if (conn == null) {
                return false;
            }
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("La instruccion se ejecuto correctamente");
            return true;
        } catch (SQLException e) {
            System.err.println("Error al momento de ejecutar la instruccion: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Para SELETC
    public static ResultSet ejecutarConsulta(String sql) {
        try {
            Connection conn = IniciarConexion();
            if (conn == null) {
                return null;
            }
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Error al momento de ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("La conexion se cerro correctamente");
            }
        } catch (SQLException e) {
            System.err.println("Error al momento de cerrar la conexion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
