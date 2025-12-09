package Interfaces;

import Exceptions.PersistenciaException;
import entidades.Pedido;
import java.util.List;

/**
 *
 * @author Alici
 */
public interface IPedidoDAO {

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
    public List<Pedido> obtenerPedidosUsuario(String correoElectronico) throws PersistenciaException;

    /**
     * Recupera una lista de todos los pedidos registrados en la base de datos.
     *
     * @return Una lista de todos los objetos pedido.
     * @throws PersistenciaException Si ocurre un error durante la ejecución de
     * la consulta.
     */
    public List<Pedido> obtenerPedidos() throws PersistenciaException;

    /**
     * Actualiza la información de un pedido existente en la base de datos (por
     * ejemplo, su estado).
     *
     * @param pedido El objeto pedido con el ID y los datos a actualizar.
     * @return true si la actualización fue exitosa.
     * @throws PersistenciaException Si ocurre un error durante la transacción,
     * provocando un rollback.
     */
    public boolean actualizarEstadoPedido(Pedido pedido) throws PersistenciaException;

    /**
     * Persiste un nuevo pedido en la base de datos.
     *
     * @param pedido El objeto pedido a guardar.
     * @return true si el pedido fue registrado y se le asignó un identificador.
     * @throws PersistenciaException Si ocurre un error durante la transacción
     * de registro, provocando un rollback.
     */
    public boolean registrarPedido(Pedido pedido) throws PersistenciaException;

    /**
     * Busca un pedido específico utilizando su número de pedido (identificador
     * único).
     *
     * @param numeroPedido El identificador del pedido a buscar.
     * @return El objeto pedido encontrado.
     * @throws PersistenciaException Si ocurre un error al intentar recuperar el
     * pedido.
     */
    public Pedido obtenerPedidoPorId(Integer numeroPedido) throws PersistenciaException;
}
