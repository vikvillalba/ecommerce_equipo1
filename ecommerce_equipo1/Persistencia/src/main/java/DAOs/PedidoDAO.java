package DAOs;

import Conexion.ConexionJPA;
import Exceptions.PersistenciaException;
import entidades.Pedido;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Alici
 */
public class PedidoDAO {

    private static PedidoDAO instancia;
    private ConexionJPA conexion = ConexionJPA.getInstance();

    private PedidoDAO() {
    }

    public static PedidoDAO getInstancia() {
        if (instancia == null) {
            instancia = new PedidoDAO();
        }
        return instancia;
    }

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
