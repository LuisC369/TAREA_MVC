package com.umg.edu.modelo;

/**
 * Tabla clientes. Hereda de Persona (id, nombre, apellidos) y agrega
 * sus atributos específicos: nit y email.
 *
 * @author Luis
 */
public class Cliente extends Persona {

    private String nit;
    private String email;

    // Constructor vacio: Spring hace new Cliente() y luego llama a los setters
    public Cliente() {
    }

    public Cliente(int idCliente, String nit, String nombre, String apellidos, String email) {
        super(idCliente, nombre, apellidos); // lo común lo guarda Persona
        this.nit = nit;
        this.email = email;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente " + getId() + ": " + getNombreCompleto()
                + " | NIT: " + nit + " | Email: " + email;
    }
}
