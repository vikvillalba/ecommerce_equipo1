/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import entidades.Compra;
import entidades.Direccion;
import entidades.Pedido;
import enums.EstadoPedido;
import interfaces.MetodoPago;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        fecha.format(DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm:ss"));
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
    
    private static final List<Pedido> pedidosMock = new ArrayList<>();

    static {

        Pedido p1 = new Pedido();
        p1.setNumeroPedido(982678);

        Direccion d1 = new Direccion();
        d1.setCalle("Av. Reforma");
        d1.setNumero("120");
        d1.setColonia("colonia 5");
        d1.setCodigoPostal("03300");
        p1.setDireccion(d1);

        Compra c1 = new Compra();
        c1.setId(1);
        c1.setFecha(LocalDateTime.of(2023, 10, 12, 14, 30));
        c1.setTotal(120.00);
        c1.setCarrito(null);
        p1.setPago(c1);

        p1.setEstado(EstadoPedido.ENTREGADO);


        Pedido p2 = new Pedido();
        p2.setNumeroPedido(846490);

        Direccion d2 = new Direccion();
        d2.setCalle("Calle Central");
        d2.setNumero("10");
        d2.setColonia("colonia 2");
        d2.setCodigoPostal("44100");
        p2.setDireccion(d2);

        Compra c2 = new Compra();
        c2.setId(2);
        c2.setFecha(LocalDateTime.of(2023, 10, 11, 16, 45));
        c2.setTotal(345.00);
        c2.setCarrito(null);
        p2.setPago(c2);

        p2.setEstado(EstadoPedido.ENTREGADO);


        Pedido p3 = new Pedido();
        p3.setNumeroPedido(743012);

        Direccion d3 = new Direccion();
        d3.setCalle("Av. Insurgentes");
        d3.setNumero("900");
        d3.setColonia("colonia 3");
        d3.setCodigoPostal("03100");
        p3.setDireccion(d3);

        Compra c3 = new Compra();
        c3.setId(3);
        c3.setFecha(LocalDateTime.of(2023, 8, 24, 11, 10));
        c3.setTotal(230.00);
        c3.setCarrito(null);
        p3.setPago(c3);

        p3.setEstado(EstadoPedido.ENTREGADO);


        Pedido p4 = new Pedido();
        p4.setNumeroPedido(546120);

        Direccion d4 = new Direccion();
        d4.setCalle("Calle 5");
        d4.setNumero("55");
        d4.setColonia("colonia 1");
        d4.setCodigoPostal("72000");
        p4.setDireccion(d4);

        Compra c4 = new Compra();
        c4.setId(4);
        c4.setFecha(LocalDateTime.of(2023, 8, 12, 9, 5));
        c4.setTotal(45.00);
        c4.setCarrito(null);
        p4.setPago(c4);

        p4.setEstado(EstadoPedido.ENTREGADO);


        pedidosMock.add(p1);
        pedidosMock.add(p2);
        pedidosMock.add(p3);
        pedidosMock.add(p4);
    }
     public List<Pedido> obtenerPedidosUsuario(int idUsuario) {
        return pedidosMock; // mock
    }
     
     
}
