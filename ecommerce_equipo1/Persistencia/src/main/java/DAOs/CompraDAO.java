package DAOs;

import Conexion.ConexionJPA;
import Interfaces.ICompraDAO;
import entidades.Compra;
import entidades.Pedido;
import entidades.Direccion;
import entidades.ProductoCompra;
import entidades.PagoTarjeta;
import entidades.PagoTransferencia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Data Access Object para la entidad Compra y la gestión de Pedidos. Implementa
 * la lógica de persistencia transaccional para el proceso de compra.
 *
 * @author Alici
 */
public class CompraDAO implements ICompraDAO{

    /**
     * Única instancia de la clase CompraDAO para el patrón Singleton.
     */
    private static CompraDAO instancia;

    /**
     * Referencia a la conexión JPA para obtener el EntityManager. Nota: Asume
     * la existencia de la clase Conexion.ConexionJPA
     */
    private ConexionJPA conexion = ConexionJPA.getInstance();

    /**
     * Constructor privado para prevenir la instanciación externa (patrón
     * Singleton).
     */
    private CompraDAO() {
    }

    /**
     * Proporciona el punto de acceso global para obtener la instancia única de
     * CompraDAO.
     *
     * @return La instancia única de CompraDAO.
     */
    public static CompraDAO getInstancia() {
        if (instancia == null) {
            instancia = new CompraDAO();
        }
        return instancia;
    }

    /**
     * Guarda la Compra, Pedido, la Dirección, la entidad de Pago (si aplica) y
     * todos los ProductosCompra asociados dentro de una única transacción.
     *
     * @param pedido El objeto Pedido que contiene la Compra, Dirección, y la
     * lista de ProductosCompra.
     * @return El objeto Pedido persistido con IDs generados (incluyendo
     * numeroPedido).
     * @throws Exception Si ocurre un error durante la transacción (ej.
     * problemas de conexión o datos).
     */
    @Override
    public Pedido generarPedido(Pedido pedido) throws Exception {
        EntityManager em = conexion.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Direccion direccion = pedido.getDireccion();
            Compra compra = pedido.getCompra();

            // Persistir la Dirección de envío
            em.persist(direccion);

            // Persistir la entidad de Pago específica si aplica (Tarjeta o Transferencia)
            PagoTarjeta pagoTarjeta = compra.getPagoTarjeta();
            PagoTransferencia pagoTransferencia = compra.getPagoTransferencia();

            if (pagoTarjeta != null) {
                // Si es PagoTarjeta, se persiste antes de la Compra
                em.persist(pagoTarjeta);
            } else if (pagoTransferencia != null) {
                // Si es PagoTransferencia, se persiste antes de la Compra
                em.persist(pagoTransferencia);
            }
            // Nota: PagoContraEntrega no se persiste ya que no tiene campos propios.

            // Persistir la Compra (que ya tiene referencias a Cliente, Pago y Total)
            em.persist(compra);

            //  Persistir los ProductosCompra
            for (ProductoCompra pc : compra.getProductos()) {
                em.persist(pc);
            }

            //  Persistir el Pedido
            em.persist(pedido);

            tx.commit();

            return pedido;
        } catch (Exception e) {
            // Si hay un error, deshacer la transacción
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            throw new Exception("Error fatal en la capa de persistencia al generar el pedido.", e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

}
