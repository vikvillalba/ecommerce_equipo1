/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.ConexionJPA;
import Exceptions.PersistenciaException;
import Interfaces.IProductoDAO;
import entidades.Producto;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

/**
 * Data Access Object (DAO) para la entidad Producto. Implementa el patrón
 * Singleton para asegurar una única instancia de la clase a lo largo de la
 * aplicación. Es responsable de todas las operaciones de persistencia (CRUD)
 * relacionadas con la entidad Producto utilizando JPA.
 *
 * @author erika
 */
public class ProductoDAO implements IProductoDAO {

    /**
     * Única instancia de la clase ProductoDAO (Singleton).
     */
    private static ProductoDAO instancia;

    /**
     * Conexión JPA para obtener el EntityManager. DEBES TENER UNA CLASE
     * 'ConexionJPA' Y 'PersistenciaException' DEFINIDAS.
     */
    private ConexionJPA conexion = ConexionJPA.getInstance();

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private ProductoDAO() {
    }

    /**
     * Retorna la instancia única de ProductoDAO.
     *
     * @return La instancia de ProductoDAO.
     */
    public static ProductoDAO getInstancia() {
        if (instancia == null) {
            instancia = new ProductoDAO();
        }
        return instancia;
    }

    /**
     * Cuenta el número de productos que están asociados a una categoría
     * específica.
     *
     * @param categoriaId El ID de la categoría a contar.
     * @return El número de productos asociados.
     * @throws PersistenciaException Si ocurre un error al ejecutar la consulta.
     */
    @Override
    public int contarProductosPorCategoria(Long categoriaId) throws PersistenciaException {
        EntityManager em = conexion.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(p) FROM Producto p WHERE p.categoria.id = :idCategoria", Long.class);
            query.setParameter("idCategoria", categoriaId);

            Long count = query.getSingleResult();
            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            throw new PersistenciaException("Error al contar productos por categoría", e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene una lista con todos los productos almacenados en la base de
     * datos.
     *
     * @return Una lista de objetos Producto.
     * @throws PersistenciaException Si ocurre un error al consultar los
     * productos.
     */
    @Override
    public List<Producto> listar() throws PersistenciaException {
        EntityManager em = conexion.getEntityManager();
        try {

            return em.createQuery(
                    "SELECT DISTINCT p FROM Producto p "
                    + "LEFT JOIN FETCH p.categoria "
                    + "LEFT JOIN FETCH p.resenas", Producto.class)
                    .getResultList();

        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar la lista de productos", e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene un producto de la base de datos buscando por su ID.
     *
     * @param id El ID del producto a buscar.
     * @return El objeto Producto encontrado, o null si no se encuentra.
     */
    @Override
    public Producto obtenerPorId(Integer id) {
        EntityManager em = conexion.getEntityManager();
        try {
            TypedQuery<Producto> query = em.createQuery(
                    "SELECT p FROM Producto p LEFT JOIN FETCH p.categoria WHERE p.id = :id",
                    Producto.class
            );
            query.setParameter("id", id);
            Producto producto = query.getSingleResult();

            // Si necesitas las reseñas, se cargan después con una consulta separada
            if (producto != null) {
                producto.getResenas().size(); 
            }

            return producto;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Persiste un nuevo Producto en la base de datos.
     *
     * @param producto El objeto producto a ser guardado.
     * @return True si la inserción fue exitosa y el producto obtuvo un ID.
     * @throws PersistenciaException Si ocurre un error durante la transacción
     * de registro.
     */
    @Override
    public boolean agregarProducto(Producto producto) throws PersistenciaException {
        EntityManager em = conexion.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();
            return producto.getId() != null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Error al registrar producto", e);
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza el estado de un producto existente en la base de datos.
     *
     * @param producto El objeto producto con los datos actualizados.
     * @return true si la actualización fue exitosa.
     * @throws PersistenciaException Si ocurre un error durante la transacción
     * de actualización.
     */
    @Override
    public boolean actualizarProducto(Producto producto) throws PersistenciaException {
        EntityManager em = conexion.getEntityManager();
        try {
            em.getTransaction().begin();
            // merge() adjunta la entidad al contexto y persiste los cambios
            em.merge(producto);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Error al actualizar el producto", e);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un producto de la base de datos.
     *
     * @param producto El objeto Producto a eliminar (debe contener el ID).
     * @return True si la eliminación fue exitosa.
     * @throws PersistenciaException Si el objeto o su ID son nulos o si ocurre
     * un error de transacción.
     */
    @Override
    public boolean eliminarProducto(Producto producto) throws PersistenciaException {
        EntityManager em = conexion.getEntityManager();
        if (producto == null || producto.getId() == null) {
            throw new PersistenciaException("El objeto producto o su ID no pueden ser nulos para la eliminación");
        }
        try {
            em.getTransaction().begin();

            // Se necesita obtener una referencia gestionada para poder eliminar
            Producto productoPersistencia = em.find(Producto.class, producto.getId());

            if (productoPersistencia == null) {
                em.getTransaction().rollback();
                throw new PersistenciaException("No se encontró el producto con ID: " + producto.getId() + " para eliminar");
            }

            em.remove(productoPersistencia);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al eliminar el producto", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Producto> obtenerProductos() throws PersistenciaException {
        EntityManager em = conexion.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Producto p LEFT JOIN FETCH p.categoria ORDER BY p.id",
                    Producto.class)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar la lista de productos", e);
        } finally {
            em.close();
        }
    }
}
