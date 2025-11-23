/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import entidades.Compra;
import entidades.Direccion;
import entidades.Pedido;
import enums.EstadoPedido;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pablo
 */
public class PedidoDAO {
    public List<Pedido> obtenerPedidosAdmin() {
        List<Pedido> lista = new ArrayList<>();

        Pedido p1 = new Pedido();
        p1.setNumeroPedido(12345678);
        
        Direccion dir = new Direccion();
        dir.setCalle("itson");
        dir.setCodigoPostal("12345");
        dir.setColonia("Villa itson");
        dir.setNumero("6543");
        p1.setDireccion(dir);

        Compra compra = new Compra();
        LocalDateTime fecha = LocalDateTime.now();
        compra.setFecha(fecha);
        compra.setTotal(500.00);
        p1.setPago(compra);

        p1.setEstado(EstadoPedido.PENDIENTE);
        
        lista.add(p1);

        return lista;
    }

    public void actualizarEstado(Integer numPedido, EstadoPedido estado) {
        // Nomás imprime porque no hay BD
        System.out.println("Actualizando pedido " + numPedido + " a estado: " + estado);
    }
}
