/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.Tallas;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * **Clase Entidad Producto**
 *
 * Representa un producto en el sistema de inventario o comercio electrónico.
 * Esta clase mapea a la tabla "productos" en la base de datos y define
 * todas las características y relaciones de un producto.
 *
 * @author erika
 */
@Entity
@Table(name = "productos")
public class Producto {

    /**
     * Identificador único del producto.
     * Es la llave primaria y se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del producto. Campo obligatorio.
     */
    @Column(nullable = false, length = 200)
    private String nombre;

    /**
     * Categoría a la que pertenece el producto.
     * Establece una relación Many-to-One con la entidad Categoria.
     * La columna de unión en la base de datos es "categoria_id".
     */
    @ManyToOne()
    @JoinColumn(name = "categoria_id", nullable = true)
    private Categoria categoria;

    /**
     * Especificaciones técnicas detalladas del producto.
     * Campo obligatorio.
     */
    @Column(nullable = false, length = 1000)
    private String especificaciones;

    /**
     * Descripción general del producto.
     * Campo obligatorio.
     */
    @Column(nullable = false, length = 1000)
    private String descripcion;

    /**
     * URL o ruta de la imagen principal del producto.
     * Campo obligatorio.
     */
    @Column(nullable = false)
    private String imagen;

    /**
     * Código hexadecimal que representa el color del producto (ej. #FF0000 para rojo).
     * Opcional (nullable = true).
     */
    @Column(name = "color_hex", length = 7, nullable = true)
    private String colorHex;

    /**
     * Talla del producto, usando el enumerador Tallas (XS, S, M, L, XL, UNICA).
     * Se almacena como String en la base de datos (EnumType.STRING).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "talla", nullable = false)
    private Tallas talla;

    /**
     * Cantidad de unidades de este producto disponibles en inventario.
     * Campo obligatorio.
     */
    @Column(nullable = false)
    private int existencias;

    /**
     * Indica si el producto está disponible para la venta (true) o no (false).
     * Campo obligatorio.
     */
    @Column(nullable = false)
    private boolean disponibilidad;

    /**
     * Precio de venta unitario del producto.
     * Campo obligatorio.
     */
    @Column(nullable = false)
    private double precio;

    /**
     * Lista de reseñas asociadas a este producto.
     * Establece una relación One-to-Many con la entidad Resena.
     * Las operaciones de eliminación, fusión y persistencia se propagan
     * a las reseñas asociadas (CascadeType.REMOVE, MERGE, PERSIST).
     */
    @OneToMany(mappedBy = "producto", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<Resena> resenas;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Producto() {
    }

    /**
     * Obtiene el identificador del producto.
     * @return El ID del producto.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador del producto.
     * @param id El nuevo ID del producto.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del producto.
     * @return El nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     * @param nombre El nuevo nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la categoría a la que pertenece el producto.
     * @return La entidad Categoria asociada.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría a la que pertenece el producto.
     * @param categoria La entidad Categoria a asociar.
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtiene las especificaciones del producto.
     * @return Las especificaciones en formato String.
     */
    public String getEspecificaciones() {
        return especificaciones;
    }

    /**
     * Establece las especificaciones del producto.
     * @param especificaciones Las nuevas especificaciones.
     */
    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    /**
     * Obtiene la descripción del producto.
     * @return La descripción en formato String.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     * @param descripcion La nueva descripción.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la URL/ruta de la imagen del producto.
     * @return La ruta de la imagen.
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Establece la URL/ruta de la imagen del producto.
     * @param imagen La nueva ruta de la imagen.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Obtiene el código de color hexadecimal.
     * @return El código de color (ej. "#FFFFFF").
     */
    public String getColorHex() {
        return colorHex;
    }

    /**
     * Establece el código de color hexadecimal.
     * @param colorHex El nuevo código de color.
     */
    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    /**
     * Obtiene la talla del producto.
     * @return La talla (Enum Tallas).
     */
    public Tallas getTalla() {
        return talla;
    }

    /**
     * Establece la talla del producto.
     * @param talla La nueva talla (Enum Tallas).
     */
    public void setTalla(Tallas talla) {
        this.talla = talla;
    }

    /**
     * Obtiene la cantidad de existencias.
     * @return La cantidad de existencias.
     */
    public int getExistencias() {
        return existencias;
    }

    /**
     * Establece la cantidad de existencias.
     * @param existencias La nueva cantidad de existencias.
     */
    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    /**
     * Verifica la disponibilidad del producto.
     * @return true si está disponible, false en caso contrario.
     */
    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    /**
     * Establece la disponibilidad del producto.
     * @param disponibilidad La nueva disponibilidad.
     */
    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    /**
     * Obtiene el precio del producto.
     * @return El precio del producto.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     * @param precio El nuevo precio.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la lista de reseñas asociadas al producto.
     * @return Una lista de objetos Resena.
     */
    public List<Resena> getResenas() {
        return resenas;
    }

    /**
     * Establece la lista de reseñas asociadas al producto.
     * @param resenas La nueva lista de reseñas.
     */
    public void setResenas(List<Resena> resenas) {
        this.resenas = resenas;
    }

}