package Modelos;

import DAOs.PedidoDAO;
import DTOs.PedidoDTO;
import Exceptions.ModeloException;
import Exceptions.PersistenciaException;
import Interfaces.IPedidoDAO;
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
    private IPedidoDAO pedidoDAO = PedidoDAO.getInstancia();

    /**
     * Actualiza el estado de un pedido específico, aplicando validaciones de
     * formato.
     *
     *
     * @param numero El número de pedido en formato de cadena (Ej. P0001).
     * @param nuevoEstado El nuevo estado a asignar al pedido.
     * @return true si el estado fue actualizado exitosamente, false en caso
     * contrario.
     * @throws ModeloException Si el número de pedido es inválido, no está
     * registrado.
     */
    public boolean actualizarEstado(String numero, EstadoPedido nuevoEstado) throws ModeloException {
        int numeroPedido = obtenerIdPedido(numero);
        if (numeroPedido == -1) {
            throw new ModeloException("Número de pedido en formato inválido. El formato esperado es una letra seguida de números.");
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
            throw new ModeloException("Error en persistencia al intentar actualizar el estado del pedido: " + ex.getMessage());
        }
    }

    /**
     * Convierte una cadena formateada de pedido a su valor numérico entero.
     *
     * @param cadenaFormateada La cadena con el formato de pedido.
     * @return El ID numérico del pedido, o -1 si la conversión falla.
     */
    private static int obtenerIdPedido(String cadenaFormateada) {
        // Validación: No debe ser nula, debe tener al menos 2 caracteres y debe comenzar con '#'
        if (cadenaFormateada == null || cadenaFormateada.trim().length() < 2 || cadenaFormateada.charAt(0) != '#') {
            return -1;
        }

        String parteNumericaConCeros = cadenaFormateada.substring(1).trim();
        try {
            // Intentamos parsear el resto de la cadena como entero
            int valorNumerico = Integer.parseInt(parteNumericaConCeros);
            return valorNumerico;
        } catch (NumberFormatException e) {
            // Si la parte numérica contiene letras u otros caracteres inválidos
            System.err.println("La cadena no contiene un formato numérico válido: " + e.getMessage());
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
            throw new ModeloException("Error al obtener la lista de pedidos: " + ex.getMessage());
        }
        return pedidos;
    }
}
