package Modelos;

import DAOs.ProductoDAO;
import DTOs.ProductoDTO;
import Exceptions.ModeloException;
import Exceptions.PersistenciaException;
import Interfaces.IProductoDAO;
import entidades.Producto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import mappers.ProductoMapper;

/**
 * Clase de Lógica de Negocio (BO) para manejar operaciones relacionadas con
 * Productos. Agrega validaciones de negocio e integridad a las operaciones de
 * persistencia.
 *
 * @author victoria
 */
public class ProductoBO {

    private static final Logger LOG = Logger.getLogger(ProductoBO.class.getName());
    // Instancia DAO necesaria para la validación de referencia
    IProductoDAO productoDAO = ProductoDAO.getInstancia();

    /**
     * Valida que una cadena no sea nula, vacía o consista solo en espacios en
     * blanco.
     *
     * @param cadena El valor de la cadena a validar.
     * @param nombreCampo El nombre del campo para el mensaje de error.
     * @throws ModeloException Si la cadena es inválida.
     */
    private void validarCadena(String cadena, String nombreCampo) throws ModeloException {
        if (cadena == null || cadena.trim().isEmpty()) {
            throw new ModeloException("El campo " + nombreCampo + " no puede ser nulo o vacío.");
        }
    }

    /**
     * Obtiene todos los productos registrados en la base de datos
     *
     *
     * @return un listado con todos los productos regisrados en la base de
     * datos.
     * @throws ModeloException si ocurrió un error al momento de recuperar los
     * registros.
     */
    public List<ProductoDTO> obtenerProductos() throws ModeloException {
        try {
            List<Producto> lista = productoDAO.obtenerProductos();
            List<ProductoDTO> productos = new ArrayList<>();

            for (Producto producto : lista) {
                ProductoDTO dto = ProductoMapper.toDTO(producto);
                productos.add(dto);

            }

            return productos;

        } catch (PersistenciaException ex) {
            throw new ModeloException(ex.getMessage());
        }
    }

    public boolean actualizarProducto(ProductoDTO producto) throws ModeloException {
        validarCadena(producto.getNombre(), "Nombre del producto");

        Producto productoActualizado = ProductoMapper.toEntity(producto);
        try {
            return productoDAO.actualizarProducto(productoActualizado);
        } catch (PersistenciaException ex) {
            ex.printStackTrace();
            throw new ModeloException(ex.getMessage());

        }

    }

    public boolean eliminarProducto(ProductoDTO producto) throws ModeloException {
        Producto productoEliminado = ProductoMapper.toEntity(producto);
        try {
            return productoDAO.eliminarProducto(productoEliminado);
        } catch (PersistenciaException ex) {
            throw new ModeloException(ex.getMessage());

        }
    }

    public boolean agregarProducto(ProductoDTO producto) throws ModeloException {
        validarCadena(producto.getNombre(), "Nombre del producto");
        Producto nuevoProducto = ProductoMapper.toEntity(producto);
        try {
            return productoDAO.agregarProducto(nuevoProducto);
        } catch (PersistenciaException ex) {
            throw new ModeloException(ex.getMessage());
        }
    }

    public ProductoDTO obtenerPorId(Integer id) {
        Producto p = productoDAO.obtenerPorId(id);
        return ProductoMapper.toDTO(p);
    }
}
