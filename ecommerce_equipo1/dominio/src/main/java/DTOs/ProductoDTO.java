package DTOs;

import enums.Tallas;

/**
 * Clase de transporte para un producto
 * @author victoria
 */
public class ProductoDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private String especificaciones;
    private double precio;
    private int existencias;
    private String imagen;
    private String colorHex;
    private boolean disponibilidad;
    private Long categoriaId;
    private String categoriaNombre;
    private Tallas talla;

    public ProductoDTO() {
    }

    // Constructor Completo
    public ProductoDTO(Integer id, String nombre, String descripcion, String especificaciones, double precio, int existencias, String imagen, String colorHex, boolean disponibilidad, Long categoriaId, Tallas talla) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.especificaciones = especificaciones;
        this.precio = precio;
        this.existencias = existencias;
        this.imagen = imagen;
        this.colorHex = colorHex;
        this.disponibilidad = disponibilidad;
        this.categoriaId = categoriaId;
        this.talla = talla;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public Tallas getTalla() {
        return talla;
    }

    public void setTalla(Tallas talla) {
        this.talla = talla;
    }
}
