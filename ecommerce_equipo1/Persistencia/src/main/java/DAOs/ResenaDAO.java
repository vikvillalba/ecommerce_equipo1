/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import entidades.Producto;
import entidades.Resena;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityTransaction;

/**
 * DAO encargado de gestionar las reseñas utilizando JPA y Base de Datos.
 *
 * @author erika
 */
public class ResenaDAO {

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
    public void eliminarResena(Integer idResena) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // 1. Buscamos la reseña
            Resena resena = em.find(Resena.class, idResena);

            if (resena != null) {
                // 2. Para mantener la coherencia, a veces es necesario removerla de la lista del producto
                // antes de borrarla, aunque JPA suele manejar el borrado directo si está configurado.
                // Opción directa:
                em.remove(resena);

                // Opción segura si tienes caché de segundo nivel o relaciones bidireccionales complejas:
                // Producto p = resena.getProducto();
                // p.getResenas().remove(resena);
                // em.merge(p);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            // Aquí podrías lanzar una excepción personalizada si lo deseas
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
    public void moderarResena(Integer idResena, String mensaje) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // 1. Buscamos la reseña por ID
            Resena resena = em.find(Resena.class, idResena);

            if (resena != null) {
                // 2. Modificamos el estado del objeto. 
                // Al hacer commit, JPA detecta el cambio y hace el UPDATE automáticamente.
                resena.setComentario(mensaje);

                // Opcional: Si tienes un campo de estado, podrías actualizarlo también
                // resena.setEstado("MODERADO");
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

    // Método auxiliar por si necesitas listar todas las reseñas sueltas (opcional)
    public List<Resena> listarTodas() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT r FROM Resena r", Resena.class).getResultList();
        } finally {
            em.close();
        }
    }
}
