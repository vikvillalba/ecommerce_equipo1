/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author Alici
 */
public class PedidoBO {

    private PedidoDAO pedidoDAO = PedidoDAO.getInstancia();

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
