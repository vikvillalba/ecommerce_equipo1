/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import DAOs.PedidoDAO;
import DTOs.CompraDTO;
import Exceptions.ModeloException;
import Exceptions.PersistenciaException;
import Interfaces.IPedidoDAO;
import entidades.Pedido;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappers.CompraMapper;

/**
 * Clase de lógica de negocio (Business Object) encargada de obtener la información
 * de una compra asociada a un número de pedido. Se apoya en el DAO de Pedido 
 * y en el mapper para convertir entidades en DTO.
 * 
 * @author pablo
 */
public class CompraBO {
    // Instancia del DAO que permite consultar pedidos en la base de datos
    private IPedidoDAO pedidoDAO = PedidoDAO.getInstancia();
    
    /**
     * Obtiene un objeto CompraDTO a partir del número de pedido formateado 
     * (por ejemplo "#00001234").
     * 
     * @param numero Número de pedido en formato de cadena (incluye '#')
     * @return CompraDTO con la información lista para mostrar en la capa de vista
     * @throws ModeloException Si ocurre un error al recuperar el pedido desde la base de datos
     */
    public CompraDTO obtenerCompraPedido(String numero) throws ModeloException {
        
        // Se convierte el número formateado 
        int numeroPedido = obtenerIdPedido(numero);
        
        // Instancia que será llenada con la información convertida desde la entidad
        CompraDTO pago = new CompraDTO();
        
        try {
            // Se obtiene el pedido desde la capa de persistencia 
            Pedido pedidoPersistencia = pedidoDAO.obtenerPedidoPorId(numeroPedido);
            
            // Se transforma la entidad Pedido en un DTO listo para mostrar en la vista
            pago = CompraMapper.toDTO(pedidoPersistencia);
            
        } catch (PersistenciaException ex) {
            // Log para registrar el error en consola
            Logger.getLogger(PedidoBO.class.getName()).log(Level.SEVERE, null, ex);
            
            // Se relanza como excepción de modelo para manejarla en capas superiores
            throw new ModeloException("Error al obtener la compra: " + ex.getMessage());
        }
        
        return pago;
    }
    
    /**
     * Convierte un número de pedido formateado (por ejemplo "#00001234") a su
     * valor numérico entero (1234).
     * 
     * @param cadenaFormateada Cadena con el formato "#0000..."
     * @return entero del número de pedido, o -1 si el formato no es válido
     */
    private static int obtenerIdPedido(String cadenaFormateada) {
        
        // Validación: no debe ser nula y debe comenzar con '#'
        if (cadenaFormateada == null || cadenaFormateada.charAt(0) != '#') {
            return -1;
        }

        // Extraemos solo los dígitos, quitando el '#'
        String parteNumericaConCeros = cadenaFormateada.substring(1).trim();
        
        try {
            // Se convierte la parte numérica a entero
            int valorNumerico = Integer.parseInt(parteNumericaConCeros);
            return valorNumerico;
            
        } catch (NumberFormatException e) {
            // Si la cadena contiene caracteres inválidos (letras, símbolos, etc.)
            System.err.println("La cadena no contiene un formato numérico válido: " + e.getMessage());
        }
        
        // -1 indica error en el formato
        return -1;
    }
}
