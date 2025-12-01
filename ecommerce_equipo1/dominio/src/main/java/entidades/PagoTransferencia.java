/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import interfaces.MetodoPago;

/**
 *
 * @author erika
 */
public class PagoTransferencia implements MetodoPago {

    private byte[] comprobante;

    public PagoTransferencia() {
    }

    public byte[] getComprobante() {
        return comprobante;
    }

    public void setComprobante(byte[] comprobante) {
        this.comprobante = comprobante;
    }

}
