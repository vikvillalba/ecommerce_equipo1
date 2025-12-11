/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import entidades.Cliente;

/**
 *
 * @author erika
 */
public interface IClienteDAO {
    public Cliente autenticar(String correo, String contrasena);
    public boolean registrar(Cliente cliente);
    public boolean existeCorreo(String correo);
    public Cliente obtenerPorId(Integer id);
    public Cliente obtenerPorCorreo(String correo);
    public Cliente obtenerPorUsuarioId(Integer usuarioId);
    public boolean actualizar(Cliente cliente);
     public boolean eliminar(Integer id);
     public boolean desactivar(Integer id);
     public boolean activar(Integer id);
}
