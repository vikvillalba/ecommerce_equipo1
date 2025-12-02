package mappers;

import DTOs.PedidoDTO;
import entidades.Pedido;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Clase de mapeo (Mapper) que se encarga de la conversión de datos entre la
 * entidad Pedido y su Data Transfer Object (DTO) correspondiente.
 *
 *
 * @author Alicia
 */
public class PedidoMapper {

    /**
     * Convierte una lista de entidades Pedido a una lista de objetos PedidoDTO.
     *
     * @param pedidos La lista de entidades Pedido de origen.
     * @return Una lista de objetos PedidoDTO.
     */
    public static List<PedidoDTO> toDTOList(List<Pedido> pedidos) {
        List<PedidoDTO> pedidosDTO = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            pedidosDTO.add(toDTO(pedido));
        }
        return pedidosDTO;
    }

    /**
     * Convierte un objeto de entidad Pedido a un objeto PedidoDTO.
     *
     * @param pedido La entidad Pedido de origen.
     * @return Un nuevo objeto PedidoDTO con los datos mapeados y formateados.
     */
    public static PedidoDTO toDTO(Pedido pedido) {
        PedidoDTO pedidoDTO = new PedidoDTO();

        String numeroPedido = formatearNumeroPedido(pedido.getNumeroPedido());
        pedidoDTO.setNumeroPedido(numeroPedido);
        pedidoDTO.setUsuario(pedido.getPago().getCarrito().getCliente().getNombre());
        pedidoDTO.setFecha(formatearFecha(pedido.getPago().getFecha()));
        pedidoDTO.setTotal(pedido.getPago().getTotal());
        pedidoDTO.setEstado(pedido.getEstado());

        return pedidoDTO;
    }

    /**
     * Formatea un número entero de pedido en una cadena con el prefijo "#" y
     * relleno de ceros (por ejemplo, 1 se convierte en "#00000001").
     *
     * @param numero El número entero del pedido.
     * @return La cadena del número de pedido formateada.
     */
    private static String formatearNumeroPedido(int numero) {
        DecimalFormat df = new DecimalFormat("00000000");
        return "#" + df.format(numero);
    }

    /**
     * Formatea un objeto Calendar en una cadena de fecha y hora (por ejemplo,
     * "dd MMMM, yyyy HH:mm").
     *
     * @param calendar El objeto Calendar que contiene la fecha y hora.
     * @return La cadena de la fecha y hora formateada.
     */
    public static String formatearFecha(Calendar calendar) {
        String patron = "dd MMMM, yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(patron, new Locale("es", "ES"));
        Date fechaDate = calendar.getTime();
        return sdf.format(fechaDate);
    }
}
