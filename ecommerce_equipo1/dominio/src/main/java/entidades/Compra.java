/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import interfaces.MetodoPago;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
//    @Column(nullable = false)
//    private MetodoPago metodoPago;
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

//    public MetodoPago getMetodoPago() {
//        return metodoPago;
//    }
//
//    public void setMetodoPago(MetodoPago metodoPago) {
//        this.metodoPago = metodoPago;
//    }

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

}
