/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Interfaces.IResenaDAO;
import entidades.Producto;
import entidades.Resena;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.EntityTransaction;

/**
 * DAO encargado de gestionar las reseñas utilizando JPA y Base de Datos.
 *
 * @author erika
 */
public class ResenaDAO implements IResenaDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Ecommerce");

    public ResenaDAO() {
    }

    /**
     * Obtiene un EntityManager para realizar operaciones.
     */
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Obtiene todas las reseñas registradas en la base de datos.
     *
     * @return Lista de todas las reseñas.
     */
    @Override
    public List<Resena> obtenerTodasLasResenas() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT r FROM Resena r JOIN FETCH r.producto JOIN FETCH r.cliente";
            Query query = em.createQuery(jpql, Resena.class);
            return query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Elimina una reseña de la base de datos por su ID.
     *
     * @param idResena El ID de la reseña a eliminar.
     */
    @Override
    public void eliminarResena(Integer idResena) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Resena resena = em.find(Resena.class, idResena);

            if (resena != null) {

                em.remove(resena);

            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Modifica el comentario de una reseña existente.
     *
     * @param idResena ID de la reseña.
     * @param mensaje Nuevo texto del comentario.
     */
    @Override
    public void moderarResena(Integer idResena, String mensaje) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Resena resena = em.find(Resena.class, idResena);

            if (resena != null) {

                resena.setComentario(mensaje);

            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Resena> listarTodas() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT r FROM Resena r", Resena.class).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Guarda una nueva reseña en la base de datos.
     *
     * @param resena La reseña a persistir.
     * @return true si se guardó exitosamente, false en caso de error.
     */
    @Override
    public boolean crearResena(Resena resena) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            em.persist(resena);

            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al crear reseña:");
            e.printStackTrace();
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
