package entidades;

import enums.TipoMetodoPago;
import interfaces.MetodoPago;
import java.io.Serializable;
import java.util.Calendar;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author erika
 */
@Entity
@Table(name = "compras")
public class Compra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fecha;

    @Column(name = "metodo_pago_tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoMetodoPago tipoMetodoPago;

    // Relación opcional para Pago con Tarjeta (Será null si el pago es Transferencia o Contra Entrega)
    @OneToOne
    @JoinColumn(name = "pago_tarjeta_id", nullable = true)
    private PagoTarjeta pagoTarjeta;

    // Relación opcional para Pago con Transferencia (Será null si el pago es Tarjeta o Contra Entrega)
    @OneToOne
    @JoinColumn(name = "pago_transferencia_id", nullable = true)
    private PagoTransferencia pagoTransferencia;

    @Column(nullable = false)
    private double total;

    @ManyToOne()
    @JoinColumn(name = "carrito_id", nullable = false)
    private Carrito carrito;

    public Compra() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public TipoMetodoPago getTipoMetodoPago() {
        return tipoMetodoPago;
    }

    public void setTipoMetodoPago(TipoMetodoPago tipoMetodoPago) {
        this.tipoMetodoPago = tipoMetodoPago;
    }

    public PagoTarjeta getPagoTarjeta() {
        return pagoTarjeta;
    }

    public void setPagoTarjeta(PagoTarjeta pagoTarjeta) {
        this.pagoTarjeta = pagoTarjeta;
    }

    public PagoTransferencia getPagoTransferencia() {
        return pagoTransferencia;
    }

    public void setPagoTransferencia(PagoTransferencia pagoTransferencia) {
        this.pagoTransferencia = pagoTransferencia;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    /**
     * Devuelve el objeto MetodoPago concreto basado en las relaciones. Si es
     * ContraEntrega, crea una nueva instancia.
     *
     * @return El objeto MetodoPago (Tarjeta, Transferencia o ContraEntrega).
     */
    public MetodoPago obtenerMetodoPago() {
        if (TipoMetodoPago.TARJETA.equals(this.tipoMetodoPago) && this.pagoTarjeta != null) {
            return this.pagoTarjeta;
        } else if (TipoMetodoPago.TRANSFERENCIA.equals(this.tipoMetodoPago) && this.pagoTransferencia != null) {
            return this.pagoTransferencia;
        } else if (TipoMetodoPago.CONTRA_ENTREGA.equals(this.tipoMetodoPago)) {
            // No se persiste, solo se crea la instancia en tiempo de ejecución
            return new PagoContraEntrega();
        }
        return null;
    }

    /**
     * Establece el método de pago en la Compra, ajustando el tipo y las
     * relaciones de persistencia según el tipo de objeto MetodoPago
     * proporcionado.
     *
     * @param metodoPago Objeto que implementa la interfaz MetodoPago.
     */
    public void setMetodoPago(MetodoPago metodoPago) {
        this.pagoTarjeta = null;
        this.pagoTransferencia = null;
        this.tipoMetodoPago = null;

        if (metodoPago instanceof PagoTarjeta) {
            this.tipoMetodoPago = TipoMetodoPago.TARJETA;
            this.pagoTarjeta = (PagoTarjeta) metodoPago;
        } else if (metodoPago instanceof PagoTransferencia) {
            this.tipoMetodoPago = TipoMetodoPago.TRANSFERENCIA;
            this.pagoTransferencia = (PagoTransferencia) metodoPago;
        } else if (metodoPago instanceof PagoContraEntrega) {
            this.tipoMetodoPago = TipoMetodoPago.CONTRA_ENTREGA;
        }
    }
}
