package com.umg.edu.modelo;

/**
 * Clase ABSTRACTA: agrupa los atributos que las tablas clientes y empleados
 * tienen en común (id, nombre y apellidos).
 * No se puede hacer "new Persona()", solo sirve para que Cliente y Empleado
 * hereden de ella.
 *
 * @author Luis
 */
public abstract class Persona {

    private int id;
    private String nombre;
    private String apellidos;

    // Constructor vacio: Spring lo usa para armar el objeto desde el formulario
    public Persona() {
    }

    public Persona(int id, String nombre, String apellidos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    // Este método lo heredan Cliente y Empleado sin tener que escribirlo
    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }
}
