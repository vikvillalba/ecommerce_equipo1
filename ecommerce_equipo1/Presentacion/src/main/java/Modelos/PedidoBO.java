package Modelos;

import DAOs.PedidoDAO;
import DTOs.PedidoDTO;
import Exceptions.ModeloException;
import Exceptions.PersistenciaException;
import entidades.Pedido;
import enums.EstadoPedido;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappers.PedidoMapper;

/**
 * Clase de Objeto de Negocio (BO) que maneja la lógica de negocio relacionada
 * con la entidad Pedido.
 *
 * @author Alicia
 */
public class PedidoBO {

    /**
     * Instancia Singleton del Data Access Object (DAO) para Pedido.
     */
    private PedidoDAO pedidoDAO = PedidoDAO.getInstancia();

    /**
     * Actualiza el estado de un pedido específico.
     * <p>
     * Este método valida el formato del número de pedido, verifica si el pedido
     * existe en la base de datos y luego actualiza su estado.
     * </p>
     *
     * @param numero El número de pedido en formato de cadena.
     * @param nuevoEstado El nuevo estado a asignar al pedido.
     * @return true si el estado fue actualizado exitosamente, false en caso
     * contrario.
     * @throws ModeloException Si el número de pedido es inválido o no está
     * registrado.
     */
    public boolean actualizarEstado(String numero, EstadoPedido nuevoEstado) throws ModeloException {
        int numeroPedido = obtenerIdPedido(numero);
        if (numeroPedido == -1) {
            throw new ModeloException("Número de pedido en formato inválido");
        }
        try {
            Pedido pedido = pedidoDAO.obtenerPedidoPorId(numeroPedido);
            if (pedido == null) {
                throw new ModeloException("Número de pedido no registrado");
            }
            pedido.setEstado(nuevoEstado);
            return pedidoDAO.actualizarEstadoPedido(pedido);
        } catch (PersistenciaException ex) {
            Logger.getLogger(PedidoBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Convierte una cadena formateada de pedido a su valor numérico entero.
     * <p>
     * Se espera que la cadena comience con un carácter y el resto sea la parte
     * numérica.
     * </p>
     *
     * @param cadenaFormateada La cadena con el formato de pedido.
     * @return El ID numérico del pedido, o -1 si la conversión falla.
     */
    private static int obtenerIdPedido(String cadenaFormateada) {

        String parteNumericaConCeros = cadenaFormateada.substring(1);
        try {
            int valorNumerico = Integer.parseInt(parteNumericaConCeros);
            return valorNumerico;
        } catch (NumberFormatException e) {
            System.err.println("La cadena no contiene un formato numérico válido" + e.getMessage());
        }
        return -1;
    }

    /**
     * Recupera una lista de todos los pedidos registrados y los mapea a objetos
     * DTO.
     *
     * @return Una lista de objetos PedidoDTO. Si no hay pedidos, devuelve una
     * lista vacía.
     * @throws ModeloException Si ocurre un error durante la recuperación de
     * datos (delegado a PersistenciaException).
     */
    public List<PedidoDTO> obtenerPedidos() throws ModeloException {
        List<PedidoDTO> pedidos = new ArrayList<>();
        try {
            List<Pedido> pedidosPersistencia = pedidoDAO.obtenerPedidos();
            pedidos = PedidoMapper.toDTOList(pedidosPersistencia);
        } catch (PersistenciaException ex) {
            Logger.getLogger(PedidoBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pedidos;
    }
}
