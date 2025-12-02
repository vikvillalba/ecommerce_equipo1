package entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad de persistencia que representa una Categoría de producto en la base
 * de datos. Esta clase se mapea a la tabla "categorias".
 *
 * @author Alici
 */
@Entity
@Table(name = "categorias")
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único de la categoría. Es la clave primaria y se genera
     * automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Nombre de la categoría. Es obligatorio, tiene una longitud máxima de 100
     * caracteres, y debe ser único.
     */
    @Column(nullable = false, length = 100, unique = true)
    private String nombre;

    /**
     * Estado de actividad de la categoría. Indica si la categoría está
     * disponible para su uso.
     */
    @Column(nullable = false)
    private boolean activa;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Categoria() {
    }

    /**
     * Constructor para inicializar una categoría con todos sus atributos.
     *
     * @param id El identificador de la categoría.
     * @param nombre El nombre de la categoría.
     * @param activa El estado de actividad de la categoría.
     */
    public Categoria(Long id, String nombre, boolean activa) {
        this.id = id;
        this.nombre = nombre;
        this.activa = activa;
    }

    /**
     * Obtiene el identificador de la categoría.
     *
     * @return El identificador de la categoría.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la categoría.
     *
     * @param id El nuevo identificador de la categoría.
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return El estado de actividad de la categoría.
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

    /**
     * Devuelve una representación en cadena de la categoría, utilizando su
     * nombre. Esto es útil para la visualización en interfaces de usuario o
     * logs.
     *
     * @return El nombre de la categoría.
     */
    @Override
    public String toString() {
        return nombre;
    }

}
