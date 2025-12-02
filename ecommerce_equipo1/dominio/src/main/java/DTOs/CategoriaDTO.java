package DTOs;

/**
 * Data Transfer Object (DTO) para la entidad Categoria. Utilizado para la
 * transferencia de datos de Categoría entre capas de la aplicación,
 * especialmente para exponer datos específicos a la interfaz de usuario o
 * servicios.
 *
 * @author Alici
 */
public class CategoriaDTO {

    /**
     * Nombre de la categoría. Es el identificador de negocio de la categoría.
     */
    private String nombre;

    /**
     * Indicador booleano que determina si la categoría se encuentra activa o
     * no.
     */
    private boolean activa;

    /**
     * Constructor por defecto.
     */
    public CategoriaDTO() {
    }

    /**
     * Obtiene el nombre de la categoría.
     *
     * @return El nombre de la categoría.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la categoría.
     *
     * @param nombre El nuevo nombre de la categoría.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Indica si la categoría está activa.
     *
     * @return true si la categoría está activa, false en caso contrario.
     */
    public boolean isActiva() {
        return activa;
    }

    /**
     * Establece el estado de actividad de la categoría.
     *
     * @param activa El nuevo estado de actividad.
     */
    public void setActiva(boolean activa) {
        this.activa = activa;
    }

}
