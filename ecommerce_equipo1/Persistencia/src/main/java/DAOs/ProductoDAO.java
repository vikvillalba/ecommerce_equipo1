/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import entidades.Cliente;
import entidades.Producto;
import entidades.Resena;
import entidades.Categoria;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erika
 */
public class ProductoDAO {

    private static List<Producto> productos = new ArrayList<>();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    public List<Producto> listar() {


        if (!productos.isEmpty()) {
            return productos;
        }

        // CLIENTES MOCK
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

        // RESEÑAS MOCK PRODUCTO 1
        List<Resena> resenas1 = new ArrayList<>();

        Resena r1 = new Resena();
        r1.setId(1);
        r1.setComentario("Hermoso vestido, igual que en la foto. Tela suave y cómoda.");
        r1.setCalificacion(5);
        r1.setCliente(c1);

        Resena r2 = new Resena();
        r2.setId(2);
        r2.setComentario("El envío fue rápido y la calidad es excelente.");
        r2.setCalificacion(4);
        r2.setCliente(c2);

        resenas1.add(r1);
        resenas1.add(r2);

        // RESEÑAS MOCK PRODUCTO 2
        List<Resena> resenas2 = new ArrayList<>();

        Resena r3 = new Resena();
        r3.setId(3);
        r3.setComentario("Muy bonito, pero llegó una talla más pequeña.");
        r3.setCalificacion(3);
        r3.setCliente(c3);

        Resena r4 = new Resena();
        r4.setId(4);
        r4.setComentario("El diseño es espectacular. Lo compraría de nuevo.");
        r4.setCalificacion(5);
        r4.setCliente(c4);

        resenas2.add(r3);
        resenas2.add(r4);


        // PRODUCTO 1
        Producto p1 = new Producto();
        p1.setId(1);
        p1.setNombre("Vestido Sweet Lolita 100% algodón");
        p1.setDescripcion("Vestido elegante estilo lolita, hecho 100% de algodón.");
        p1.setEspecificaciones("Lavado a mano. 100% algodón");
        p1.setImagen("img/rr1.jpg");
        p1.setExistencias(100);
        p1.setDisponibilidad(true);
        p1.setPrecio(260.0);
//        p1.setCategoria(categoriaDAO.obtenerPorNombre("ACCESORIOS"));
        p1.setResenas(resenas1);


        // PRODUCTO 2
        Producto p2 = new Producto();
        p2.setId(2);
        p2.setNombre("Vestido Dark Lolita 100% algodón");
        p2.setDescripcion("Camisa de rayas verticales estilo clásico.");
        p2.setEspecificaciones("Vestido elegante estilo lolita, hecho 100% de algodón.");
        p2.setImagen("img/rr2.jpg");
        p2.setExistencias(0);
        p2.setDisponibilidad(false);
        p2.setPrecio(212.0);
//        p2.setCategoria(categoriaDAO.obtenerPorNombre("VESTIDOS"));
        p2.setResenas(resenas2);


        productos.add(p1);
        productos.add(p2);

        return productos;
    }


    public Producto obtenerPorId(Integer id) {
        if (productos.isEmpty()) {
            listar(); 
        }

        return productos.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    
}
