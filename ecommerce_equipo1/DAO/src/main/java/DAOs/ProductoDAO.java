/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import entidades.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erika
 */
public class ProductoDAO {

    private static List<Producto> productos = new ArrayList<>();

    public List<Producto> listar() {
        Producto p1 = new Producto();
        p1.setId(1);
        p1.setNombre("Vestido Sweet Lolita 100% algodón");
        p1.setDescripcion("Vestido elegante estilo lolita, hecho 100% de algodón.");
        p1.setEspecificaciones("Lavado a mano. 100% algodón");
        p1.setImagen("img/rr1.jpg");
        p1.setExistencias(100);
        p1.setDisponibilidad(true);
        p1.setPrecio(260.0);

        Producto p2 = new Producto();
        p2.setId(2);
        p2.setNombre("Vestido Dark Lolita 100% algodón");
        p2.setDescripcion("Camisa de rayas verticales estilo clásico.");
        p2.setEspecificaciones("Vestido elegante estilo lolita, hecho 100% de algodón.");
        p2.setImagen("img/rr2.jpg");
        p2.setExistencias(0);
        p2.setDisponibilidad(false);
        p2.setPrecio(212.0);

        productos.add(p1);
        productos.add(p2);
        return productos;
    }

    public Producto obtenerPorId(Integer id) {
        return productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
