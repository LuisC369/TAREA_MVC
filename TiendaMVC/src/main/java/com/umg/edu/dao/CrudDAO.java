package com.umg.edu.dao;

import java.util.List;

/**
 * Interfaz generica del patron DAO. Define las 5 operaciones que toda tabla
 * necesita, sin importar de que entidad se trate.
 *
 * @author Luis
 * @param <T> Parametro generico (Cliente, Marca, Puesto, etc.)
 */
public interface CrudDAO<T> {

    boolean insertar(T objeto);

    boolean actualizar(T objeto);

    boolean eliminar(T objeto);

    T buscarPorId(int id);

    List<T> listarTodos();
}
