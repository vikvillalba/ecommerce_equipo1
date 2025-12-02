package DAOs;

import Conexion.ConexionJPA;
import Exceptions.PersistenciaException;
import entidades.Pedido;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Data Access Object (DAO) para la entidad Pedido. Implementa el patrón
 * Singleton. Esta clase se encarga de manejar todas las operaciones de
 * persistencia (CRUD) relacionadas con los pedidos en la base de datos
 * utilizando JPA.
 *
 * @author Alici
 */
public class PedidoDAO {

    /**
     * Única instancia de la clase PedidoDAO para el patrón Singleton.
     */
    private static PedidoDAO instancia;

    /**
     * Referencia a la conexión JPA para obtener el EntityManager.
     */
    private ConexionJPA conexion = ConexionJPA.getInstance();

    /**
     * Constructor privado para prevenir la instanciación externa (patrón
     * Singleton).
     */
    private PedidoDAO() {
    }

    /**
     * Proporciona el punto de acceso global para obtener la instancia única de
     * PedidoDAO.
     *
     * @return La instancia única de PedidoDAO.
     */
    public static PedidoDAO getInstancia() {
        if (instancia == null) {
            instancia = new PedidoDAO();
        }
        return instancia;
    }

    /**
     * Obtiene todos los pedidos asociados a un cliente específico a partir de
     * su correo electrónico.
     *
     * @param correoElectronico El correo electrónico del cliente cuyos pedidos
     * se desean consultar.
     * @return Una lista de objetos pedido.
     * @throws PersistenciaException Si ocurre un error durante la ejecución de
     * la consulta.
     */
    public List<Pedido> obtenerPedidosUsuario(String correoElectronico) throws PersistenciaException {
        EntityManager em = conexion.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Pedido p JOIN p.pago c JOIN c.carrito ca JOIN ca.cliente cli WHERE cli.correo = :correoElectronico", Pedido.class)
                    .setParameter("correoElectronico", correoElectronico)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar los pedidos del cliente con el correo proporcionado", e);
        } finally {
            em.close();
        }
    }

    /**
     * Recupera una lista de todos los pedidos registrados en la base de datos.
     *
     * @return Una lista de todos los objetos pedido.
     * @throws PersistenciaException Si ocurre un error durante la ejecución de
     * la consulta.
     */
    public List<Pedido> obtenerPedidos() throws PersistenciaException {
        EntityManager em = conexion.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Pedido p", Pedido.class)
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar los pedidos", e);
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza la información de un pedido existente en la base de datos (por
     * ejemplo, su estado).
     *
     * @param pedido El objeto pedido con el ID y los datos a actualizar.
     * @return true si la actualización fue exitosa.
     * @throws PersistenciaException Si ocurre un error durante la transacción,
     * provocando un rollback.
     */
    public boolean actualizarEstadoPedido(Pedido pedido) throws PersistenciaException {
        EntityManager em = conexion.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(pedido);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Error al actualizar el estado del pedido", e);
        } finally {
            em.close();
        }
    }

    /**
     * Persiste un nuevo pedido en la base de datos.
     *
     * @param pedido El objeto pedido a guardar.
     * @return true si el pedido fue registrado y se le asignó un identificador.
     * @throws PersistenciaException Si ocurre un error durante la transacción
     * de registro, provocando un rollback.
     */
    public boolean registrarPedido(Pedido pedido) throws PersistenciaException {
        EntityManager em = conexion.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(pedido);
            em.getTransaction().commit();
            return pedido.getNumeroPedido() != null;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Error al registrar pedido", e);
        } finally {
            em.close();
        }
    }

    /**
     * Busca un pedido específico utilizando su número de pedido (identificador
     * único).
     *
     * @param numeroPedido El identificador del pedido a buscar.
     * @return El objeto pedido encontrado.
     * @throws PersistenciaException Si ocurre un error al intentar recuperar el
     * pedido.
     */
    public Pedido obtenerPedidoPorId(Integer numeroPedido) throws PersistenciaException {
        EntityManager em = conexion.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Pedido p WHERE p.numeroPedido = :numeroPedido", Pedido.class)
                    .setParameter("numeroPedido", numeroPedido)
                    .getSingleResult();
        } catch (Exception e) {
            throw new PersistenciaException("Error al recuperar el pedido por su numero de pedido", e);
        } finally {
            em.close();
        }
    }

}
