package com.umg.edu.modelo;

/**
 * Tabla puestos.
 *
 * @author Luis
 */
public class Puesto {

    private int idPuesto;
    private String nombre;
    private double salarioBase;

    // Constructor vacio: Spring hace new Puesto() y luego llama a los setters
    public Puesto() {
    }

    public Puesto(int idPuesto, String nombre, double salarioBase) {
        this.idPuesto = idPuesto;
        this.nombre = nombre;
        this.salarioBase = salarioBase;
    }

    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    @Override
    public String toString() {
        return "Puesto " + idPuesto + ": " + nombre + " | Salario base: " + salarioBase;
    }
}
