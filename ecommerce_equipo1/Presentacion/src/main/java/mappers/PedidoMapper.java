/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author Alici
 */
public class PedidoMapper {
    
    public static List<PedidoDTO> toDTOList(List<Pedido> pedidos) {
        List<PedidoDTO> pedidosDTO = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            pedidosDTO.add(toDTO(pedido));
        }
        return pedidosDTO;
    }
    
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
    
    private static String formatearNumeroPedido(int numero) {
        DecimalFormat df = new DecimalFormat("00000000");
        return "#" + df.format(numero);
    }
    
    public static String formatearFecha(Calendar calendar) {
        String patron = "dd MMMM, yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(patron, new Locale("es", "ES"));
        Date fechaDate = calendar.getTime();
        return sdf.format(fechaDate);
    }
}
