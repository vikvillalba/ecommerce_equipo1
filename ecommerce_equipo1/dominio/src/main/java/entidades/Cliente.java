/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author erika
 */
public class Cliente extends Usuario{
    private String telefono;
    private boolean estado;
    private Direccion direccion;
    private boolean estaAutenticado;

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

    public boolean isEstaAutenticado() {
        return estaAutenticado;
    }

    public void setEstaAutenticado(boolean estaAutenticado) {
        this.estaAutenticado = estaAutenticado;
    }
    
    
}
