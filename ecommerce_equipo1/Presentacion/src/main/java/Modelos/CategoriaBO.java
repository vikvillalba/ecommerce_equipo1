package Modelos;

import DAOs.CategoriaDAO;
import DAOs.ProductoDAO;
import DTOs.CategoriaDTO;
import Exceptions.ModeloException;
import Exceptions.PersistenciaException;
import Interfaces.ICategoriaDAO;
import Interfaces.IProductoDAO;
import entidades.Categoria;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappers.CategoriaMapper;

/**
 * Clase de Lógica de Negocio (BO) para manejar operaciones relacionadas con
 * Categorias. Agrega validaciones de negocio e integridad a las operaciones de
 * persistencia.
 *
 * @author Alici
 */
public class CategoriaBO {

    private static final Logger LOG = Logger.getLogger(CategoriaBO.class.getName());
    ICategoriaDAO categoriaDAO = CategoriaDAO.getInstancia();
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
     * Valida que el nombre de la categoría solo contenga caracteres
     * alfanuméricos y espacios.
     *
     * @param nombre El nombre a validar.
     * @throws ModeloException Si el formato no es válido.
     */
    private void validarFormatoNombre(String nombre) throws ModeloException {
        if (!nombre.matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]{1,100}$")) {
            throw new ModeloException("El nombre de la categoría contiene caracteres inválidos. Solo se permiten letras y números sin espacios.");
        }
    }

    /**
     * Obtiene una lista de todas las categorías mapeadas a DTOs.
     *
     * @return Lista de CategoriaDTO.
     */
    public List<CategoriaDTO> obtenerCategorias() {
        List<CategoriaDTO> categorias = new ArrayList<>();
        try {
            List<Categoria> categoriasPersistencia = categoriaDAO.obtenerCategorias();
            categorias = CategoriaMapper.toDTOList(categoriasPersistencia);
        } catch (PersistenciaException ex) {
            LOG.log(Level.SEVERE, "Error al obtener categorías de la persistencia", ex);
        }
        return categorias;
    }

    /**
     * Elimina una categoría por su nombre, aplicando validaciones de existencia
     * y de referencia (no debe tener productos asociados).
     *
     * @param nombreCategoria El nombre de la categoría a eliminar.
     * @return true si se eliminó, false en caso contrario.
     * @throws ModeloException Si el nombre es inválido, la categoría no existe
     * o si tiene productos asociados.
     */
    public boolean eliminarCategoria(String nombreCategoria) throws ModeloException {
        validarCadena(nombreCategoria, "Nombre de Categoría");

        try {
            Categoria categoria = categoriaDAO.obtenerCategoriaPorNombre(nombreCategoria);
            if (categoria == null) {
                throw new ModeloException("No se encontró la categoría con el nombre: " + nombreCategoria);
            }

            int productosAsociados = productoDAO.contarProductosPorCategoria(categoria.getId());
            if (productosAsociados > 0) {
                throw new ModeloException("No se puede eliminar la categoría " + nombreCategoria
                        + " porque tiene " + productosAsociados + " producto(s) asociado(s).");
            }

            return categoriaDAO.eliminarCategoria(categoria);
        } catch (PersistenciaException ex) {
            LOG.log(Level.SEVERE, "Error en persistencia al intentar eliminar la categoría.", ex);
            throw new ModeloException("Error al intentar eliminar la categoría: " + ex.getMessage());
        }
    }

    /**
     * Agrega una nueva categoría, aplicando validaciones de existencia,
     * longitud, y formato.
     *
     * @param categoriaDTO El DTO de la categoría a agregar.
     * @return true si se agregó, false en caso contrario.
     * @throws ModeloException Si el DTO o su nombre son inválidos, si la
     * categoría ya existe, o si el nombre excede el límite de 100 caracteres.
     */
    public boolean agregarCategoria(CategoriaDTO categoriaDTO) throws ModeloException {
        if (categoriaDTO == null) {
            throw new ModeloException("El objeto CategoriaDTO no puede ser nulo.");
        }

        validarCadena(categoriaDTO.getNombre(), "Nombre de Categoría");
        validarFormatoNombre(categoriaDTO.getNombre());

        String nombreLimpio = categoriaDTO.getNombre().trim();
        categoriaDTO.setNombre(nombreLimpio);

        try {
            Categoria existe = categoriaDAO.obtenerCategoriaPorNombre(categoriaDTO.getNombre());
            if (existe != null) {
                throw new ModeloException("Ya existe una categoría con el nombre: " + categoriaDTO.getNombre());
            }
            if (categoriaDTO.getNombre().length() > 100) {
                throw new ModeloException("El nombre supera los 100 caracteres");
            }
            return categoriaDAO.agregarCategoria(CategoriaMapper.toEntity(categoriaDTO));
        } catch (PersistenciaException ex) {
            LOG.log(Level.SEVERE, "Error en persistencia al intentar agregar la categoría.", ex);
            throw new ModeloException("Error al intentar agregar la categoría: " + ex.getMessage());
        }
    }

    /**
     * Desactiva una categoría por su nombre, aplicando validaciones.
     *
     * @param nombreCategoria El nombre de la categoría a desactivar.
     * @return true si se actualizó, false en caso contrario.
     * @throws ModeloException Si el nombre es inválido, la categoría no existe
     * o si ya se encuentra inactiva.
     */
    public boolean desactivarCategoria(String nombreCategoria) throws ModeloException {
        validarCadena(nombreCategoria, "Nombre de Categoría");
        try {
            Categoria categoria = categoriaDAO.obtenerCategoriaPorNombre(nombreCategoria);
            if (categoria == null) {
                throw new ModeloException("No se encontró la categoría con el nombre: " + nombreCategoria);
            }
            if (!categoria.isActiva()) {
                throw new ModeloException("La categoría " + nombreCategoria + " ya se encuentra inactiva.");
            }
            categoria.setActiva(false);
            return categoriaDAO.actualizarCategoria(categoria);
        } catch (PersistenciaException ex) {
            LOG.log(Level.SEVERE, "Error en persistencia al intentar desactivar la categoría.", ex);
            throw new ModeloException("Error al intentar desactivar la categoría: " + ex.getMessage());
        }
    }

    /**
     * Activa una categoría por su nombre, aplicando validaciones.
     *
     * @param nombreCategoria El nombre de la categoría a activar.
     * @return true si se actualizó, false en caso contrario.
     * @throws ModeloException Si el nombre es inválido, la categoría no existe
     * o si ya se encuentra activa.
     */
    public boolean activarCategoria(String nombreCategoria) throws ModeloException {
        validarCadena(nombreCategoria, "Nombre de Categoría");
        try {
            Categoria categoria = categoriaDAO.obtenerCategoriaPorNombre(nombreCategoria);
            if (categoria == null) {
                throw new ModeloException("No se encontró la categoría con el nombre: " + nombreCategoria);
            }
            if (categoria.isActiva()) {
                throw new ModeloException("La categoría " + nombreCategoria + " ya se encuentra activa.");
            }
            categoria.setActiva(true);
            return categoriaDAO.actualizarCategoria(categoria);
        } catch (PersistenciaException ex) {
            LOG.log(Level.SEVERE, "Error en persistencia al intentar activar la categoría.", ex);
            throw new ModeloException("Error al intentar activar la categoría: " + ex.getMessage());
        }
    }
}
