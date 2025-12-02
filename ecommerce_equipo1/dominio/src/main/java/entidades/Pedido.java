package entidades;

import enums.EstadoPedido;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Representa la entidad de Pedido en la base de datos, mapeada a la tabla
 * "pedidos".
 *
 * @author Alicia
 */
@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {

    /**
     * Número de versión para la serialización.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del pedido, generado automáticamente. Es la clave
     * primaria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer numeroPedido;

    /**
     * Dirección de envío del pedido. Relación muchos a uno. Puede ser nula.
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "direccion_id", nullable = true)
    private Direccion direccion;

    /**
     * La entidad Compra asociada al pedido, que contiene la información de
     * pago. Relación uno a uno.
     */
    @OneToOne()
    @JoinColumn(name = "compra_id", nullable = false)
    private Compra pago;

    /**
     * Estado actual del pedido (por ejemplo, PENDIENTE, EN_CAMINO, ENTREGADO).
     * Se almacena como una cadena en la base de datos.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado;

    /**
     * Constructor por defecto de la clase Pedido.
     */
    public Pedido() {
    }

    /**
     * Obtiene el número de identificación del pedido.
     *
     * @return El número del pedido.
     */
    public Integer getNumeroPedido() {
        return numeroPedido;
    }

    /**
     * Establece el número de identificación del pedido.
     *
     * @param numeroPedido El número del pedido.
     */
    public void setNumeroPedido(Integer numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    /**
     * Obtiene la dirección de envío asociada al pedido.
     *
     * @return La dirección del pedido.
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección de envío del pedido.
     *
     * @param direccion La nueva dirección del pedido.
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene la compra (pago) asociada a este pedido.
     *
     * @return La entidad Compra (pago) del pedido.
     */
    public Compra getPago() {
        return pago;
    }

    /**
     * Establece la compra (pago) asociada a este pedido.
     *
     * @param pago La entidad Compra (pago) a asociar.
     */
    public void setPago(Compra pago) {
        this.pago = pago;
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
