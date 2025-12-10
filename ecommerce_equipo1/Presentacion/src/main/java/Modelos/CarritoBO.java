package Modelos;

import DTOs.ProductoCarritoDTO;
import enums.Tallas;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase de Lógica de Negocio para manejar el carrito de compras, persistente
 * solo durante la sesión HTTP.
 *
 * @author Alici
 */
public class CarritoBO {

    // Nombre constante del atributo del carrito en la sesión
    private static final String CARRITO = "productosCarrito";

    /**
     * Obtiene el carrito del cliente desde la sesión. Si no existe, crea uno
     * nuevo.
     *
     * @param session Sesión HTTP actual.
     * @return Lista de productos en el carrito.
     */
    public List<ProductoCarritoDTO> obtenerProductosCarrito(HttpSession session) {
        Object carritoObj = session.getAttribute(CARRITO);

        if (carritoObj instanceof List) {
            return (List<ProductoCarritoDTO>) carritoObj;
        } else {
            List<ProductoCarritoDTO> nuevoCarrito = new ArrayList<>();
            session.setAttribute(CARRITO, nuevoCarrito);
            return nuevoCarrito;
        }
    }

    /**
     * Agrega un nuevo producto o incrementa la cantidad si ya existe. El
     * producto se identifica por su nombre y talla.
     *
     * @param session Sesión HTTP actual.
     * @param nuevoProductoDTO DTO con la información del producto a agregar.
     */
    public void agregarProducto(HttpSession session, ProductoCarritoDTO nuevoProductoDTO) {
        List<ProductoCarritoDTO> carrito = obtenerProductosCarrito(session);

        Optional<ProductoCarritoDTO> productoExistenteOpt = carrito.stream()
                .filter(p -> p.getNombreProducto().equals(nuevoProductoDTO.getNombreProducto())
                && p.getTalla().equals(nuevoProductoDTO.getTalla()))
                .findFirst();

        if (productoExistenteOpt.isPresent()) {
            ProductoCarritoDTO existente = productoExistenteOpt.get();
            existente.setCantidad(existente.getCantidad() + nuevoProductoDTO.getCantidad());
        } else {
            carrito.add(nuevoProductoDTO);
        }
    }

    /**
     * Actualiza la cantidad de un producto existente en el carrito. El producto
     * se identifica por su nombre y talla.
     *
     * @param session Sesión HTTP actual.
     * @param nombreProducto Nombre del producto.
     * @param talla Talla del producto (Enum Tallas).
     * @param nuevaCantidad Nueva cantidad deseada.
     */
    public void actualizarCantidadProducto(HttpSession session, String nombreProducto, Tallas talla, int nuevaCantidad) {
        List<ProductoCarritoDTO> carrito = obtenerProductosCarrito(session);

        Optional<ProductoCarritoDTO> productoOpt = carrito.stream()
                .filter(p -> p.getNombreProducto().equals(nombreProducto) && p.getTalla().equals(talla.name()))
                .findFirst();

        if (productoOpt.isPresent()) {
            ProductoCarritoDTO producto = productoOpt.get();
            producto.setCantidad(nuevaCantidad);
        }

    }

    /**
     * Elimina un producto del carrito. El producto se identifica por su nombre
     * y talla.
     *
     * @param session Sesión HTTP actual.
     * @param nombreProducto Nombre del producto.
     * @param talla Talla del producto (Enum Tallas).
     */
    public void eliminarProducto(HttpSession session, String nombreProducto, Tallas talla) {
        List<ProductoCarritoDTO> carrito = obtenerProductosCarrito(session);

        carrito.removeIf(p -> p.getNombreProducto().equals(nombreProducto) && p.getTalla().equals(talla.name()));

    }

}
