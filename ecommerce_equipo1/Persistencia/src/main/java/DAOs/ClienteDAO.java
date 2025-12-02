/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.ConexionJPA;
import entidades.Cliente;
import entidades.Direccion;
import enums.TipoUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * Data Access Object (DAO) para la entidad Cliente. Implementa el patrón
 * Singleton para asegurar una única instancia de la clase a lo largo de la
 * aplicación.
 *
 * @author Maryr
 */
public class ClienteDAO {

    /**
     * Única instancia de la clase ClienteDAO (Singleton).
     */
    private static ClienteDAO instancia;

    private ConexionJPA conexion;

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private ClienteDAO() {
        this.conexion = ConexionJPA.getInstance();
    }

    /**
     * Retorna la instancia única de ClienteDAO.
     *
     * @return La instancia de ClienteDAO.
     */
    public static ClienteDAO getInstancia() {
        if (instancia == null) {
            instancia = new ClienteDAO();
        }
        return instancia;
    }

    /**
     * Obtiene una instancia de EntityManager
     *
     * @return EntityManager
     */
    private EntityManager getEntityManager() {
        return conexion.getEntityManager();
    }

    /**
     * Autentica un cliente buscando por correo y contraseña.
     *
     * * @param correo El correo del cliente (propiedad heredada de Usuario).
     * @param correo
     * @param contrasena La contraseña del cliente (propiedad heredada de
     * Usuario).
     * @return El objeto Cliente autenticado o null si no se encuentra.
     */
    public Cliente autenticar(String correo, String contrasena) {
        EntityManager em = getEntityManager();
        try {
            // CORREGIDO: Acceso directo a c.correo y c.contrasena por herencia.
            TypedQuery<Cliente> query = em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.correo = :correo "
                    + "AND c.contrasena = :pass AND c.estado = true", Cliente.class);
            query.setParameter("correo", correo);
            query.setParameter("pass", contrasena);

            List<Cliente> resultados = query.getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);

        } finally {
            em.close();
        }
    }

    public boolean registrar(Cliente cliente) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(cliente);
            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;

        } finally {
            em.close();
        }
    }

    public boolean existeCorreo(String correo) {
        EntityManager em = getEntityManager();
        try {
            // CORREGIDO: Acceso directo a c.correo por herencia.
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(c) FROM Cliente c WHERE c.correo = :correo", Long.class);
            query.setParameter("correo", correo);
            return query.getSingleResult() > 0;

        } finally {
            em.close();
        }
    }

    public Cliente obtenerPorId(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public Cliente obtenerPorCorreo(String correo) {
        EntityManager em = getEntityManager();
        try {
            // CORREGIDO: Acceso directo a c.correo por herencia.
            TypedQuery<Cliente> query = em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.correo = :correo", Cliente.class);
            query.setParameter("correo", correo);

            List<Cliente> resultados = query.getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);

        } finally {
            em.close();
        }
    }

    /**
     * Obtiene un cliente usando el ID heredado de la entidad Usuario.
     *
     * * @param usuarioId El ID del cliente/usuario.
     * @param usuarioId
     * @return El Cliente encontrado.
     */
    public Cliente obtenerPorUsuarioId(Integer usuarioId) {
        EntityManager em = getEntityManager();
        try {
            // CORREGIDO: Acceso directo a c.id (el ID del Usuario) por herencia.
            TypedQuery<Cliente> query = em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.id = :idUser", Cliente.class);
            query.setParameter("idUser", usuarioId);

            List<Cliente> resultados = query.getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);

        } finally {
            em.close();
        }
    }

    public boolean actualizar(Cliente cliente) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(cliente);
            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;

        } finally {
            em.close();
        }
    }

    public List<Cliente> listar() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Cliente c", Cliente.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ------------------------------------------------------------------------
    // --- METODOS ADMIN ---
    // ------------------------------------------------------------------------
    public boolean eliminar(Integer id) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Cliente c = em.find(Cliente.class, id);
            if (c == null) {
                return false;
            }

            tx.begin();
            em.remove(c);
            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;

        } finally {
            em.close();
        }
    }

    public boolean desactivar(Integer id) {
        Cliente c = obtenerPorId(id);
        if (c == null) {
            return false;
        }

        c.setEstado(false);
        return actualizar(c);
    }

    public boolean activar(Integer id) {
        Cliente c = obtenerPorId(id);
        if (c == null) {
            return false;
        }

        c.setEstado(true);
        return actualizar(c);
    }
}
