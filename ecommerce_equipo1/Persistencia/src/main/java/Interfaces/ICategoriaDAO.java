/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Exceptions.PersistenciaException;
import entidades.Categoria;
import java.util.List;

/**
 *
 * @author Alici
 */
public interface ICategoriaDAO {

    /**
     * Obtiene una categoria de la base de datos buscando por su nombre.
     *
     * @param nombre El nombre de la categoría a buscar.
     * @return La categoría encontrada, o null si no se encuentra ninguna.
     * @throws PersistenciaException Si ocurre un error al ejecutar la consulta
     * en la base de datos.
     */
    public Categoria obtenerCategoriaPorNombre(String nombre) throws PersistenciaException;

    /**
     * Actualiza el estado de una categoria existente en la base de datos.
     *
     * @param categoria El objeto categoria con los datos actualizados.
     * @return true si la actualización fue exitosa.
     * @throws PersistenciaException Si ocurre un error durante la transacción
     * de actualización.
     */
    public boolean actualizarCategoria(Categoria categoria) throws PersistenciaException;

    /**
     * Obtiene una lista con todas las categorias almacenadas en la base de
     * datos.
     *
     * @return Una lista de categorias.
     * @throws PersistenciaException Si ocurre un error al consultar las
     * categorías.
     */
    public List<Categoria> obtenerCategorias() throws PersistenciaException;

    /**
     * Persiste una nueva Categoria en la base de datos.
     *
     * @param categoria El objeto categoria a ser guardado.
     * @return True si la inserción fue exitosa y la categoría obtuvo un ID.
     * @throws PersistenciaException Si ocurre un error durante la transacción
     * de registro.
     */
    public boolean agregarCategoria(Categoria categoria) throws PersistenciaException;

    /**
     * Elimina una categoria de la base de datos.
     *
     * @param categoria El objeto categoria a eliminar (debe contener el ID).
     * @return True si la eliminación fue exitosa.
     * @throws PersistenciaException Si el objeto o su ID son nulos, si la
     * categoría no se encuentra, o si ocurre un error de transacción.
     */
    public boolean eliminarCategoria(Categoria categoria) throws PersistenciaException;
}
