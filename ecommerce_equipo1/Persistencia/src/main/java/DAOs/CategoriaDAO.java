package DAOs;

import Conexion.ConexionJPA;
import Exceptions.PersistenciaException;
import entidades.Categoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 * Data Access Object (DAO) para la entidad Categoria. Implementa el patrón
 * Singleton para asegurar una única instancia de la clase a lo largo de la
 * aplicación. * Es responsable de todas las operaciones de persistencia (CRUD)
 * relacionadas con la entidad Categoria utilizando JPA.
 *
 * @author Alici
 */
public class CategoriaDAO {

    /**
     * Única instancia de la clase CategoriaDAO (Singleton).
     */
    private static CategoriaDAO instancia;

    /**
     * Conexión JPA para obtener el EntityManager.
     */
    private ConexionJPA conexion = ConexionJPA.getInstance();

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private CategoriaDAO() {
    }

    /**
     * Retorna la instancia única de CategoriaDAO.
     *
     * @return La instancia de CategoriaDAO.
     */
    public static CategoriaDAO getInstancia() {
        if (instancia == null) {
            instancia = new CategoriaDAO();
        }
        return instancia;
    }

    /**
     * Obtiene una categoria de la base de datos buscando por su nombre.
     *
     * @param nombre El nombre de la categoría a buscar.
     * @return La categoría encontrada, o null si no se encuentra ninguna.
     * @throws PersistenciaException Si ocurre un error al ejecutar la consulta
     * en la base de datos.
     */
    public Categoria obtenerCategoriaPorNombre(String nombre) throws PersistenciaException {
        EntityManager em = conexion.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Categoria c WHERE c.nombre = :nombre", Categoria.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new PersistenciaException("Error al recuperar la categoria con el nombre indicado", e);
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza el estado de una categoria existente en la base de datos.
     *
     * @param categoria El objeto categoria con los datos actualizados.
     * @return true si la actualización fue exitosa.
     * @throws PersistenciaException Si ocurre un error durante la transacción
     * de actualización.
     */
    public boolean actualizarCategoria(Categoria categoria) throws PersistenciaException {
        EntityManager em = conexion.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(categoria);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Error al actualizar el estado de la categoria", e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene una lista con todas las categorias almacenadas en la base de
     * datos.
     *
     * @return Una lista de categorias.
     * @throws PersistenciaException Si ocurre un error al consultar las
     * categorías.
     */
    public List<Categoria> obtenerCategorias() throws PersistenciaException {
        EntityManager em = conexion.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Categoria c", Categoria.class)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar las categorias", e);
        } finally {
            em.close();
        }
    }

    /**
     * Persiste una nueva Categoria en la base de datos.
     *
     * @param categoria El objeto categoria a ser guardado.
     * @return True si la inserción fue exitosa y la categoría obtuvo un ID.
     * @throws PersistenciaException Si ocurre un error durante la transacción
     * de registro.
     */
    public boolean agregarCategoria(Categoria categoria) throws PersistenciaException {
        EntityManager em = conexion.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(categoria);
            em.getTransaction().commit();
            return categoria.getId() != null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Error al registrar categoria", e);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina una categoria de la base de datos.
     *
     * @param categoria El objeto categoria a eliminar (debe contener el ID).
     * @return True si la eliminación fue exitosa.
     * @throws PersistenciaException Si el objeto o su ID son nulos, si la
     * categoría no se encuentra, o si ocurre un error de transacción.
     */
    public boolean eliminarCategoria(Categoria categoria) throws PersistenciaException {
        EntityManager em = conexion.getEntityManager();
        if (categoria == null || categoria.getId() == null) {
            throw new PersistenciaException("El objeto categoría o su ID no pueden ser nulos para la eliminación");
        }
        try {
            em.getTransaction().begin();
            Long categoriaId = categoria.getId();
            Categoria categoriaPersistencia = em.find(Categoria.class, categoriaId);

            if (categoriaPersistencia == null) {
                em.getTransaction().rollback();
                throw new PersistenciaException("No se encontró la categoría con el ID: " + categoriaId + " para eliminar");
            }

            em.remove(categoriaPersistencia);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction() != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al eliminar la categoria", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
