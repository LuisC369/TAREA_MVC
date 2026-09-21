package com.umg.edu.modelo;

/**
 * Tabla marcas.
 *
 * @author Luis
 */
public class Marca {

    private int idMarca;
    private String nombre;

    // Constructor vacio: Spring hace new Marca() y luego llama a los setters
    public Marca() {
    }

    public Marca(int idMarca, String nombre) {
        this.idMarca = idMarca;
        this.nombre = nombre;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Marca " + idMarca + ": " + nombre;
    }
}
