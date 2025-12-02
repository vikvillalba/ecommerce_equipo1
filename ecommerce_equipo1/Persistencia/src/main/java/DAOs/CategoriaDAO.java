package DAOs;

import Conexion.ConexionJPA;
import Exceptions.PersistenciaException;
import entidades.Categoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author Alici
 */
public class CategoriaDAO {

    private static CategoriaDAO instancia;
    private ConexionJPA conexion = ConexionJPA.getInstance();

    private CategoriaDAO() {
    }

    public static CategoriaDAO getInstancia() {
        if (instancia == null) {
            instancia = new CategoriaDAO();
        }
        return instancia;
    }

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
