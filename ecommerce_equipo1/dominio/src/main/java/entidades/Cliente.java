/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.TipoUsuario;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Entidad que representa un cliente en el sistema. Un cliente tiene una
 * relación con Usuario y Dirección.
 *
 * @author erika
 */
@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Usuario implements Serializable {

    @Column(name = "telefono", length = 20, nullable = true)
    private String telefono;
    @Column(name = "estado", nullable = true)
    private boolean estado;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "direccion_id", referencedColumnName = "id", nullable = true)
    private Direccion direccion;

    public Cliente() {
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

}
