/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import entidades.Producto;
import entidades.Resena;
import entidades.Cliente;
import enums.Categoria;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erika
 */
public class ResenaDAO {

    private static final List<Producto> productos = new ArrayList<>();

    static {
        //datos mock
        Cliente c1 = new Cliente();
        c1.setId(1);
        c1.setNombre("Samantha D.");

        Cliente c2 = new Cliente();
        c2.setId(2);
        c2.setNombre("Alex M.");

        Cliente c3 = new Cliente();
        c3.setId(3);
        c3.setNombre("Ethan R.");

        Cliente c4 = new Cliente();
        c4.setId(4);
        c4.setNombre("Olivia P.");

        // Reseñas mock 
        Resena r1 = new Resena();
        r1.setId(1);
        r1.setComentario("Esta reseña ha sido moderada por un administrador");
        r1.setCalificacion(4);
        r1.setCliente(c1);

        Resena r2 = new Resena();
        r2.setId(2);
        r2.setComentario("El vestido superó mis expectativas, excelente calidad.");
        r2.setCalificacion(5);
        r2.setCliente(c2);

        Resena r3 = new Resena();
        r3.setId(3);
        r3.setComentario("Muy bonito y con buenos acabados.");
        r3.setCalificacion(4);
        r3.setCliente(c3);

        Resena r4 = new Resena();
        r4.setId(4);
        r4.setComentario("Colores vibrantes y gran diseño.");
        r4.setCalificacion(5);
        r4.setCliente(c4);

        List<Resena> resenasProducto1 = new ArrayList<>();
        resenasProducto1.add(r1);
        resenasProducto1.add(r2);
        resenasProducto1.add(r3);
        resenasProducto1.add(r4);

        // Producto Mock 1
        Producto p1 = new Producto();
        p1.setId(1);
        p1.setNombre("Vestido Sweet Lolita 100% algodón");
        p1.setCategoria(Categoria.VESTIDOS);
        p1.setDescripcion("Vestido estilo lolita de algodón suave y detalles en encaje.");
        p1.setEspecificaciones("Lavado a mano, 100% algodón, fabricación artesanal.");
        p1.setImagen("img/vestido1.jpg");
        p1.setExistencias(100);
        p1.setDisponibilidad(true);
        p1.setPrecio(260.00);
        p1.setResenas(resenasProducto1);

        productos.add(p1);
    }

    public List<Producto> listar() {
        return productos;
    }

    public Producto obtenerPorId(Integer id) {
        return productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}
