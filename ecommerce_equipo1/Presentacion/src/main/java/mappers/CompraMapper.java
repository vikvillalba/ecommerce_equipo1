/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import DTOs.CompraDTO;
import entidades.Pedido;
import enums.TipoMetodoPago;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Clase utilidad encargada de convertir entidades persistentes (Pedido y Compra)
 * en objetos DTO listos para ser enviados a la capa de presentación.
 * 
 * Se encarga del formateo de fecha, número de pedido y método de pago.
 * 
 * @author pablo
 */
public class CompraMapper {

    /**
     * Convierte una entidad Pedido en un objeto CompraDTO.
     * 
     * @param pedido Entidad Pedido obtenida desde la base de datos
     * @return CompraDTO con información lista para mostrar en la vista
     */
    public static CompraDTO toDTO(Pedido pedido) {
        CompraDTO pago = new CompraDTO();
        
        // Formatea el número de pedido (#00000000)
        String numeroPedido = formatearNumeroPedido(pedido.getNumeroPedido());
        pago.setNumeroPedido(numeroPedido);
        
        // Asigna el nombre del usuario/cliente asociado a la compra
        pago.setUsuario(pedido.getCompra().getCliente().getNombre());
        
        // Convierte la fecha Calendar a una cadena legible en español
        pago.setFecha(formatearFecha(pedido.getCompra().getFecha()));
        
        // Monto total de la compra
        pago.setTotal(pedido.getCompra().getTotal());
        
        // Convierte el enum TipoMetodoPago a un texto amigable
        pago.setMetodoPago(formatearMetodoPago(pedido.getCompra().getTipoMetodoPago()));

        return pago;
    }
    
    /**
     * Construye y formatea el número de pedido con ceros a la izquierda,
     * devolviéndolo en formato "#00000000".
     * 
     * @param numero Número de pedido real (entero)
     * @return Número de pedido formateado como cadena
     */
    private static String formatearNumeroPedido(int numero) {
        DecimalFormat df = new DecimalFormat("00000000");
        return "#" + df.format(numero);
    }
    
    /**
     * Convierte un objeto Calendar a una cadena de texto con el formato:
     * "dd MMMM, yyyy HH:mm"
     * 
     * @param calendar Objeto Calendar con la fecha original
     * @return Cadena con la fecha formateada
     */
    public static String formatearFecha(Calendar calendar) {
        String patron = "dd MMMM, yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(patron, new Locale("es", "ES"));
        
        Date fechaDate = calendar.getTime();
        return sdf.format(fechaDate);
    }
    
    /**
     * Convierte el enum TipoMetodoPago a una descripción amigable para mostrar
     * en la interfaz de usuario.
     * 
     * @param tipo Tipo de método de pago (enum)
     * @return Cadena descriptiva del método de pago
     */
    private static String formatearMetodoPago(TipoMetodoPago tipo) {
        switch (tipo) {
            case TARJETA:
                return "Tarjeta";
            case TRANSFERENCIA:
                return "Transferencia bancaria";
            case CONTRA_ENTREGA:
                return "Pago contra entrega";
            default:
                return tipo.toString();
        }
    }
}
