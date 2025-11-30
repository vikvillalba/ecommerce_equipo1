/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.ConexionJPA;
import entidades.Usuario;
import enums.TipoUsuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Clase DAO para gestionar operaciones CRUD de la entidad Usuario en MySQL
 * usando JPA
 *
 * @author Jack Murrieta
 */
public class UsuarioDAO {

    private ConexionJPA conexion;

    /**
     * Constructor que obtiene la conexión JPA singleton
     */
    public UsuarioDAO() {
        this.conexion = ConexionJPA.getInstance();
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
     * Guarda un nuevo usuario en la base de datos
     *
     * @param usuario Usuario a guardar
     * @return true si se guardó exitosamente, false en caso contrario
     */
    public boolean crear(Usuario usuario) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(usuario);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza un usuario existente en la base de datos
     *
     * @param usuario Usuario con los datos actualizados
     * @return true si se actualizó exitosamente, false en caso contrario
     */
    public boolean actualizar(Usuario usuario) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(usuario);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un usuario de la base de datos por su ID
     *
     * @param id ID del usuario a eliminar
     * @return true si se eliminó exitosamente, false en caso contrario
     */
    public boolean eliminar(Integer id) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) {
                em.remove(usuario);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Busca un usuario por su ID
     *
     * @param id ID del usuario
     * @return Usuario encontrado o null si no existe
     */
    public Usuario obtenerPorId(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Busca un usuario por su correo electrónico
     *
     * @param correo Correo electrónico del usuario
     * @return Usuario encontrado o null si no existe
     */
    public Usuario obtenerPorCorreo(String correo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.correo = :correo", Usuario.class);
            query.setParameter("correo", correo);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Autentica un usuario con correo y contraseña
     *
     * @param correo Correo del usuario
     * @param contrasena Contraseña del usuario
     * @return Usuario si las credenciales son correctas, null en caso contrario
     */
    public Usuario autenticar(String correo, String contrasena) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.correo = :correo AND u.contrasena = :contrasena",
                    Usuario.class);
            query.setParameter("correo", correo);
            query.setParameter("contrasena", contrasena);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Verifica si existe un usuario con el correo dado
     *
     * @param correo Correo a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existeCorreo(String correo) {
        return obtenerPorCorreo(correo) != null;
    }

    /**
     * Obtiene todos los usuarios del sistema
     *
     * @return Lista de todos los usuarios
     */
    public List<Usuario> listarTodos() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u", Usuario.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todos los usuarios de un tipo específico
     *
     * @param tipo Tipo de usuario (ADMINISTRADOR o CLIENTE)
     * @return Lista de usuarios del tipo especificado
     */
    public List<Usuario> listarPorTipo(TipoUsuario tipo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.tipoUsuario = :tipo", Usuario.class);
            query.setParameter("tipo", tipo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todos los administradores del sistema
     *
     * @return Lista de usuarios administradores
     */
    public List<Usuario> listarAdministradores() {
        return listarPorTipo(TipoUsuario.ADMINISTRADOR);
    }

    /**
     * Obtiene todos los clientes del sistema
     *
     * @return Lista de usuarios clientes
     */
    public List<Usuario> listarClientes() {
        return listarPorTipo(TipoUsuario.CLIENTE);
    }
}
