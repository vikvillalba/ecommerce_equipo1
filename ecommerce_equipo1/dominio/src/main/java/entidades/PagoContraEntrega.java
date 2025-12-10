package entidades;

import interfaces.MetodoPago;

/* *
 * Clase que representa el método de pago Contra Entrega.
 * No requiere persistencia en base de datos ya que no almacena datos de pago.
 *
 * @author Alici
 */
public class PagoContraEntrega implements MetodoPago {

    // Este método de pago no requiere campos adicionales.
    public PagoContraEntrega() {
    }
}
