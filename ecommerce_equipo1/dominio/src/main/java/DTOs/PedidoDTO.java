package DTOs;

import enums.EstadoPedido;

/**
 * Data Transfer Object (DTO) para la entidad Pedido. Utilizado para la
 * transferencia de datos de Pedido entre las capas de la aplicación,
 * encapsulando la información esencial del pedido para su visualización y
 * gestión.
 *
 * @author Alici
 */
public class PedidoDTO {

    /**
     * Identificador único del pedido.
     */
    private String numeroPedido;

    /**
     * Correo o nombre del usuario al que pertenece el pedido.
     */
    private String usuario;

    /**
     * Fecha en que se realizó el pedido.
     */
    private String fecha;

    /**
     * Monto total del pedido.
     */
    private double total;

    /**
     * Estado actual del pedido (Pendiente, Enviado, Entregado, etc.).
     */
    private EstadoPedido estado;

    /**
     * Constructor por defecto.
     */
    public PedidoDTO() {
    }

    /**
     * Constructor que inicializa todos los atributos del DTO.
     *
     * @param numeroPedido El identificador del pedido.
     * @param usuario El identificador del usuario.
     * @param fecha La fecha de realización del pedido.
     * @param total El monto total del pedido.
     * @param estado El estado actual del pedido.
     */
    public PedidoDTO(String numeroPedido, String usuario, String fecha, double total, EstadoPedido estado) {
        this.numeroPedido = numeroPedido;
        this.usuario = usuario;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
    }

    /**
     * Obtiene el número de pedido.
     *
     * @return El número de pedido.
     */
    public String getNumeroPedido() {
        return numeroPedido;
    }

    /**
     * Establece el número de pedido.
     *
     * @param numeroPedido El nuevo número de pedido.
     */
    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    /**
     * Obtiene el identificador del usuario.
     *
     * @return El identificador del usuario.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece el identificador del usuario.
     *
     * @param usuario El nuevo identificador del usuario.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la fecha del pedido.
     *
     * @return La fecha del pedido.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha del pedido.
     *
     * @param fecha La nueva fecha del pedido.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el total del pedido.
     *
     * @return El total del pedido.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Establece el total del pedido.
     *
     * @param total El nuevo total del pedido.
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Obtiene el estado actual del pedido.
     *
     * @return El estado del pedido.
     */
    public EstadoPedido getEstado() {
        return estado;
    }

    /**
     * Establece el estado actual del pedido.
     *
     * @param estado El nuevo estado del pedido.
     */
    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

}
