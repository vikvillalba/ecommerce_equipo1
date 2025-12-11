/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import enums.Tallas;

/**
 *
 * @author Alici
 */
public class ProductoCarritoDTO {

    private Integer idProducto;
    private String nombreProducto;
    private Tallas talla;
    private String colorHex;
    private int cantidad;
    private double subtotal;
    private double precio;
    private String direccionImagen;

    public ProductoCarritoDTO() {
        this.subtotal = 0.0; 
    }

    public ProductoCarritoDTO(Integer idProducto, String nombreProducto, Tallas talla, String colorHex, int cantidad, double subtotal, double precio, String direccionImagen) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.talla = talla;
        this.colorHex = colorHex;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = precio * cantidad;
        this.direccionImagen = direccionImagen;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Tallas getTalla() {
        return talla;
    }

    public void setTalla(Tallas talla) {
        this.talla = talla;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = this.precio * this.cantidad; // Calcula el subtotal
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
        this.subtotal = this.precio * this.cantidad; // Recalcular si el precio cambia
    }

    public String getDireccionImagen() {
        return direccionImagen;
    }

    public void setDireccionImagen(String direccionImagen) {
        this.direccionImagen = direccionImagen;
    }

}