package org.itson.dao;

import DAOs.CategoriaDAO;
import DAOs.ClienteDAO;
import DAOs.ProductoDAO;
import DAOs.ResenaDAO;
import Exceptions.PersistenciaException;
import entidades.Categoria;
import entidades.Cliente;
import entidades.Direccion;
import entidades.Producto;
import entidades.Resena;
import enums.Tallas;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author victoria
 */
public class DatosInsert {

    public static void cargarDatos() {
        // inserts de categorías
        CategoriaDAO dao = CategoriaDAO.getInstancia();
        ProductoDAO prodDao = ProductoDAO.getInstancia();

        Categoria cat1 = new Categoria("PANTALONES", true);
        Categoria cat2 = new Categoria("FALDAS", true);
        Categoria cat3 = new Categoria("SHORTS", true);
        Categoria cat4 = new Categoria("BLUSAS", true);
        Categoria cat5 = new Categoria("CAMISAS", true);
        Categoria cat6 = new Categoria("VESTIDOS", true);
        Categoria cat7 = new Categoria("ZAPATOS", true);
        Categoria cat8 = new Categoria("CHAMARRAS", true);
        Categoria cat9 = new Categoria("ACCESORIOS", true);

        try {

            dao.agregarCategoria(cat1);
            dao.agregarCategoria(cat2);
            dao.agregarCategoria(cat3);
            dao.agregarCategoria(cat4);
            dao.agregarCategoria(cat5);
            dao.agregarCategoria(cat6);
            dao.agregarCategoria(cat7);
            dao.agregarCategoria(cat8);
            dao.agregarCategoria(cat9);
        } catch (PersistenciaException ex) {
            System.out.println("Error al agregar categorías");
            ex.printStackTrace();
        }

        List<Producto> lista = new ArrayList();

        Producto p1 = new Producto();
        p1.setNombre("Vestido Sweet Lolita 100% algodón");
        p1.setEspecificaciones("100% Algodón, 180g");
        p1.setDescripcion("Vestido muy bonito");
        p1.setImagen("/img/vestido1.png");
        p1.setColorHex("#FFFFFF");
        p1.setTalla(Tallas.M);
        p1.setExistencias(50);
        p1.setDisponibilidad(true);
        p1.setPrecio(15.99);
        p1.setCategoria(cat6);
        lista.add(p1);

        Producto p2 = new Producto();
        p2.setNombre("Vestido chocolat");
        p2.setEspecificaciones("100% algodón");
        p2.setDescripcion("Corte moderno y cómodo.");
        p2.setImagen("/img/vestido2.png");
        p2.setColorHex("#4D2108");
        p2.setTalla(Tallas.L);
        p2.setExistencias(30);
        p2.setDisponibilidad(true);
        p2.setPrecio(45.50);
        p2.setCategoria(cat6);
        lista.add(p2);

        Producto p3 = new Producto();
        p3.setNombre("Vestido Dark Lolita");
        p3.setEspecificaciones("100% algodón");
        p3.setDescripcion("Perfecto para el frío.");
        p3.setImagen("/img/vestido3.png");
        p3.setColorHex("#808080");
        p3.setTalla(Tallas.XL);
        p3.setExistencias(20);
        p3.setDisponibilidad(true);
        p3.setPrecio(35.00);
        p3.setCategoria(cat6);
        lista.add(p3);

        Producto p4 = new Producto();
        p4.setNombre("Vestido Lolita Verano");
        p4.setEspecificaciones("Lino transpirable");
        p4.setDescripcion("Fresco y elegante.");
        p4.setImagen("/img/vestido4.png");
        p4.setColorHex("#FF0000");
        p4.setTalla(Tallas.S);
        p4.setExistencias(15);
        p4.setDisponibilidad(true);
        p4.setPrecio(29.99);
        p4.setCategoria(cat6);
        lista.add(p4);

        Producto p5 = new Producto();
        p5.setNombre("Vestido Lolita Festivo");
        p5.setEspecificaciones("100% algodón");
        p5.setDescripcion("Estilo clásico y festivo.");
        p5.setImagen("/img/vestido5.png");
        p5.setColorHex("#000000");
        p5.setTalla(Tallas.UNICA);
        p5.setExistencias(100);
        p5.setDisponibilidad(true);
        p5.setPrecio(12.00);
        p5.setCategoria(cat6);
        lista.add(p5);

        for (Producto producto : lista) {
            try {
                prodDao.agregarProducto(producto);
            } catch (PersistenciaException ex) {
                System.out.println("Error al cargar productos");
                ex.printStackTrace();
            }
        }

        ClienteDAO clienteDAO = ClienteDAO.getInstancia();

        Cliente cliente = new Cliente();
        cliente.setNombre("María bottle");
        cliente.setCorreo("maria@bottle.com");
        cliente.setContrasena("1234");

        // Crear la Dirección del Cliente
        Direccion direccion = new Direccion();
        direccion.setCalle("Narnia");
        direccion.setNumero("124");
        direccion.setColonia("Centro");
        direccion.setCodigoPostal("85000");

        // Crear el Cliente y asociar Usuario + Dirección
//            cliente.setUsuario(usuarioCliente);
        cliente.setTelefono("6443679815");
        cliente.setEstado(true);
        cliente.setDireccion(direccion);

        clienteDAO.registrar(cliente);

        ResenaDAO resenaDao = new ResenaDAO();
        List<Resena> listaResenas = new ArrayList<>();

        Resena r1 = new Resena();
        r1.setCalificacion(5);
        r1.setComentario("¡Me encantó! La tela es súper suave y no se encoge al lavarla. Llegó antes de tiempo.");
        r1.setProducto(p1);
        r1.setCliente(cliente);
        listaResenas.add(r1);

        Resena r2 = new Resena();
        r2.setCalificacion(4);
        r2.setComentario("El vestido es de buena calidad, pero la talla viene un poco reducida. Recomiendo pedir una talla más.");
        r2.setProducto(p1);
        r2.setCliente(cliente);
        listaResenas.add(r2);

        Resena r3 = new Resena();
        r3.setCalificacion(2);
        r3.setComentario("No me gustó. La tela se siente muy sintética y el color es más opaco que en la foto.");
        r3.setProducto(p2);
        r3.setCliente(cliente);
        listaResenas.add(r3);

        Resena r4 = new Resena();
        r4.setCalificacion(5);
        r4.setComentario("Simplemente hermoso. Lo usé para una fiesta y todos me preguntaron dónde lo compré. ¡Súper fresco!");
        r4.setProducto(p2);
        r4.setCliente(cliente);
        listaResenas.add(r4);
        
        Resena r5 = new Resena();
        r5.setCalificacion(1);
        r5.setComentario("Llegó totalmente aplastada porque la enviaron en una bolsa y no en caja. Pésimo servicio de envío.");
        r5.setProducto(p3);
        r5.setCliente(cliente);
        listaResenas.add(r5);

        Resena r6 = new Resena();
        r6.setCalificacion(3);
        r6.setComentario("Es bonita, pero la tela es muy delgada y se transparenta un poco. Necesitas usar algo debajo.");
        r6.setProducto(p3);
        r6.setCliente(cliente);
        listaResenas.add(r6);

        Resena r7 = new Resena();
        r7.setCalificacion(5);
        r7.setComentario("Calidad premium. Huele a cuero real y los cierres son muy resistentes. Vale cada centavo.");
        r7.setProducto(p3);
        r7.setCliente(cliente);
        listaResenas.add(r7);

        Resena r8 = new Resena();
        r8.setCalificacion(4);
        r8.setComentario("Muy calientita y suave, aunque suelta un poco de pelusa al principio. El color verde es precioso.");
        r8.setProducto(p4);
        r8.setCliente(cliente);
        listaResenas.add(r8);

        Resena r9 = new Resena();
        r9.setCalificacion(5);
        r9.setComentario("Justo lo que necesitaba para la oficina. Casi no se arruga y el corte es muy favorecedor.");
        r9.setProducto(p4);
        r9.setCliente(cliente);
        listaResenas.add(r9);

        Resena r10 = new Resena();
        r10.setCalificacion(2);
        r10.setComentario("Pedí talla S y parece talla de niño. Es imposible que esto le quede a un adulto promedio.");
        r10.setProducto(p5);
        r10.setCliente(cliente);
        listaResenas.add(r10);
        
        for (Resena listaResena : listaResenas) {
            resenaDao.crearResena(listaResena);
        }

    }

}
