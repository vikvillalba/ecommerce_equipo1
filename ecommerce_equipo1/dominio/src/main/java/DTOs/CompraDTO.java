/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 * Objeto de Transferencia de Datos (DTO) que representa la información
 * de una compra ya procesada y lista para ser enviada a la capa de vista.
 * 
 * @author pablo
 */
public class CompraDTO {
    /** Monto total de la compra realizada. */
    private double total;

    /** Nombre del usuario/cliente que realizó la compra. */
    private String usuario;

    /** Fecha en que se realizó la compra, ya formateada para mostrar. */
    private String fecha;

    /** Número de pedido en formato string, por ejemplo "#00001234". */
    private String numeroPedido;

    /** Nombre del método de pago usado (Tarjeta, Transferencia, etc.). */
    private String metodoPago;

    /**
     * Constructor vacío requerido para frameworks y herramientas que necesitan
     * instanciar el objeto sin valores iniciales.
     */
    public CompraDTO() {
    }

    /**
     * Constructor completo para inicializar el DTO con todos sus campos.
     * 
     * @param total Monto total de la compra
     * @param usuario Nombre del cliente
     * @param fecha Fecha formateada de la compra
     * @param numeroPedido Número de pedido formateado
     * @param metodoPago Método de pago como texto
     */
    public CompraDTO(double total, String usuario, String fecha, String numeroPedido, String metodoPago) {
        this.total = total;
        this.usuario = usuario;
        this.fecha = fecha;
        this.numeroPedido = numeroPedido;
        this.metodoPago = metodoPago;
    }

    /** @return Monto total de la compra */
    public double getTotal() {
        return total;
    }

    /** @param total Monto total de la compra */
    public void setTotal(double total) {
        this.total = total;
    }

    /** @return Nombre del cliente */
    public String getUsuario() {
        return usuario;
    }

    /** @param usuario Nombre del cliente */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /** @return Fecha formateada de la compra */
    public String getFecha() {
        return fecha;
    }

    /** @param fecha Fecha formateada de la compra */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /** @return Número de pedido formateado */
    public String getNumeroPedido() {
        return numeroPedido;
    }

    /** @param numeroPedido Número de pedido formateado */
    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    /** @return Método de pago como texto legible */
    public String getMetodoPago() {
        return metodoPago;
    }

    /** @param metodoPago Método de pago formateado */
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}
