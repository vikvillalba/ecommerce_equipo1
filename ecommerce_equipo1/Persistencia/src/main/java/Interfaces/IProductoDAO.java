/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Exceptions.PersistenciaException;
import entidades.Producto;
import java.util.List;

/**
 *
 * @author Alici
 */
public interface IProductoDAO {

    /**
     * Cuenta el número de productos que están asociados a una categoría
     * específica.
     *
     * @param categoriaId El ID de la categoría a contar.
     * @return El número de productos asociados.
     * @throws PersistenciaException Si ocurre un error al ejecutar la consulta.
     */
    public int contarProductosPorCategoria(Long categoriaId) throws PersistenciaException;

    /**
     * Obtiene una lista con todos los productos almacenados en la base de
     * datos.
     *
     * @return Una lista de objetos Producto.
     * @throws PersistenciaException Si ocurre un error al consultar los
     * productos.
     */
    public List<Producto> listar() throws PersistenciaException;

    /**
     * Obtiene un producto de la base de datos buscando por su ID.
     *
     * @param id El ID del producto a buscar.
     * @return El objeto Producto encontrado, o null si no se encuentra.
     */
    public Producto obtenerPorId(Integer id);

    /**
     * Persiste un nuevo Producto en la base de datos.
     *
     * @param producto El objeto producto a ser guardado.
     * @return True si la inserción fue exitosa y el producto obtuvo un ID.
     * @throws PersistenciaException Si ocurre un error durante la transacción
     * de registro.
     */
    public boolean agregarProducto(Producto producto) throws PersistenciaException;

    /**
     * Actualiza el estado de un producto existente en la base de datos.
     *
     * @param producto El objeto producto con los datos actualizados.
     * @return true si la actualización fue exitosa.
     * @throws PersistenciaException Si ocurre un error durante la transacción
     * de actualización.
     */
    public boolean actualizarProducto(Producto producto) throws PersistenciaException;

    /**
     * Elimina un producto de la base de datos.
     *
     * @param producto El objeto Producto a eliminar (debe contener el ID).
     * @return True si la eliminación fue exitosa.
     * @throws PersistenciaException Si el objeto o su ID son nulos o si ocurre
     * un error de transacción.
     */
    public boolean eliminarProducto(Producto producto) throws PersistenciaException;
    
    /**
     * Obtiene una lista con todos los productos almacenados en la base de
     * datos.
     *
     * @return Una lista de objetos Producto.
     * @throws PersistenciaException Si ocurre un error al consultar los
     * productos.
     */
    public List<Producto> obtenerProductos() throws PersistenciaException;
}
