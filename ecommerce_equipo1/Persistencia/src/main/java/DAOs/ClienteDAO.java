/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.ConexionJPA;
import Interfaces.IClienteDAO;
import entidades.Cliente;
import entidades.Direccion;
import enums.TipoUsuario;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

/**
 * Data Access Object (DAO) para la entidad Cliente. Implementa el patrón
 * Singleton para asegurar una única instancia de la clase a lo largo de la
 * aplicación.
 *
 * @author Maryr
 */
public class ClienteDAO implements IClienteDAO {

    /**
     * Única instancia de la clase ClienteDAO (Singleton).
     */
    private static ClienteDAO instancia;

    /**
     * Referencia a la clase de conexión JPA para obtener el EntityManager.
     */
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
     * Obtiene una instancia de EntityManager para interactuar con la base de
     * datos.
     *
     * @return EntityManager
     */
    private EntityManager getEntityManager() {
        return conexion.getEntityManager();
    }

    /**
     * Autentica un cliente buscando por correo, contraseña y verificando que su
     * estado sea activo.
     *
     * @param correo El correo del cliente (propiedad heredada de Usuario).
     * @param contrasena La contraseña del cliente (propiedad heredada de
     * Usuario).
     * @return El objeto Cliente autenticado o null si no se encuentra o está
     * inactivo.
     */
    @Override
    public Cliente autenticar(String correo, String contrasena) {
        EntityManager em = getEntityManager();
        try {
            // Consulta HQL que busca por correo, contraseña y estado activo (c.estado = true).
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

    /**
     * Persiste un nuevo cliente en la base de datos.
     *
     * @param cliente El objeto Cliente a registrar.
     * @return true si el registro fue exitoso, false en caso de error
     * (incluyendo rollback).
     */
    @Override
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
                tx.rollback(); // Deshacer la transacción en caso de excepción.
            }
            e.printStackTrace();
            return false;

        } finally {
            em.close();
        }
    }

    /**
     * Verifica si ya existe un cliente registrado con el correo electrónico
     * proporcionado.
     *
     * @param correo El correo electrónico a verificar.
     * @return true si el correo ya existe en la base de datos, false si no
     * existe.
     */
    @Override
    public boolean existeCorreo(String correo) {
        try (EntityManager em = getEntityManager()) {
            // Consulta que cuenta el número de clientes con el correo dado.
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(c) FROM Cliente c WHERE c.correo = :correo", Long.class);
            query.setParameter("correo", correo);

            // Si el conteo es mayor a 0, el correo existe.
            return query.getSingleResult() > 0;

        }
    }

    /**
     * Busca y obtiene un cliente por su identificador único (ID).
     *
     * @param id El ID del cliente a buscar.
     * @return El objeto Cliente si es encontrado, o null en caso contrario.
     */
    @Override
    public Cliente obtenerPorId(Integer id) {
        try (EntityManager em = getEntityManager()) {
            // Uso de em.find para buscar por la clave primaria.
            return em.find(Cliente.class, id);
        }
    }

    /**
     * Busca y obtiene un cliente por su correo electrónico.
     *
     * @param correo El correo electrónico del cliente a buscar.
     * @return El objeto Cliente si es encontrado, o null en caso contrario.
     */
    @Override
    public Cliente obtenerPorCorreo(String correo) {
        EntityManager em = getEntityManager();
        try {
            // Consulta HQL para buscar un cliente por su correo.
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
     * Obtiene un cliente usando el ID heredado de la entidad Usuario (alias de
     * obtenerPorId).
     *
     * @param usuarioId El ID del cliente/usuario.
     * @return El Cliente encontrado.
     */
    public Cliente obtenerPorUsuarioId(Integer usuarioId) {
        EntityManager em = getEntityManager();
        try {
            // Consulta que usa el ID del usuario para encontrar el cliente.
            TypedQuery<Cliente> query = em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.id = :idUser", Cliente.class);
            query.setParameter("idUser", usuarioId);

            List<Cliente> resultados = query.getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);

        } finally {
            em.close();
        }
    }

    /**
     * Actualiza la información de un cliente existente en la base de datos.
     *
     * @param cliente El objeto Cliente con los datos actualizados (debe tener
     * un ID válido).
     * @return true si la actualización fue exitosa, false en caso de error
     * (incluyendo rollback).
     */
    @Override
    public boolean actualizar(Cliente cliente) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(cliente); // Usa merge para actualizar la entidad.
            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback(); // Deshacer la transacción en caso de excepción.
            }
            e.printStackTrace();
            return false;

        } finally {
            em.close();
        }
    }

    /**
     * Recupera una lista de todos los clientes registrados en la base de datos.
     *
     * @return Una lista de objetos Cliente.
     */
    public List<Cliente> listar() {
        EntityManager em = getEntityManager();
        try {
            // Consulta HQL para obtener todos los clientes.
            return em.createQuery("SELECT c FROM Cliente c", Cliente.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    //  METODOS ADMIN
    /**
     * Elimina permanentemente un cliente de la base de datos.
     *
     * @param id El ID del cliente a eliminar.
     * @return true si la eliminación fue exitosa, false si el cliente no existe
     * o hubo un error.
     */
    @Override
    public boolean eliminar(Integer id) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            // Primero se busca el cliente por ID para asegurar que existe.
            Cliente c = em.find(Cliente.class, id);
            if (c == null) {
                return false;
            }

            tx.begin();
            em.remove(c); // Elimina la entidad.
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

    /**
     * Cambia el estado de un cliente a inactivo (`estado = false`).
     *
     * @param id El ID del cliente a desactivar.
     * @return true si el cliente fue encontrado y desactivado exitosamente,
     * false si no se encontró o hubo un error.
     */
    @Override
    public boolean desactivar(Integer id) {
        Cliente c = obtenerPorId(id);
        if (c == null) {
            return false; // No se encontró el cliente.
        }

        c.setEstado(false);
        return actualizar(c); // Utiliza el método actualizar para persistir el cambio de estado.
    }

    /**
     * Cambia el estado de un cliente a activo (`estado = true`).
     *
     * @param id El ID del cliente a activar.
     * @return true si el cliente fue encontrado y activado exitosamente, false
     * si no se encontró o hubo un error.
     */
    @Override
    public boolean activar(Integer id) {
        Cliente c = obtenerPorId(id);
        if (c == null) {
            return false; // No se encontró el cliente.
        }

        c.setEstado(true);
        return actualizar(c);
    }
}
