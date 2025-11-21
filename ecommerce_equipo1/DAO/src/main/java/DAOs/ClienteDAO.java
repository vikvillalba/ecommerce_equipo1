/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import entidades.Cliente;
import entidades.Direccion;
import enums.TipoUsuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maryr
 */
public class ClienteDAO {

    private static List<Cliente> clientes = new ArrayList<>();
    private static int contadorId = 1;

    static {
        Direccion dir1 = new Direccion();
        dir1.setId(1);
        dir1.setCalle("Narnia");
        dir1.setNumero("123");
        dir1.setColonia("Centro");
        dir1.setCodigoPostal("85000");

        Cliente c1 = new Cliente();
        c1.setId(1);
        c1.setNombre("Jesus en moto");
        c1.setCorreo("moto@ejemplo.com");
        c1.setContrasena("123456");
        c1.setTelefono("6441234567");
        c1.setEstado(true);
        c1.setTipoUsuario(TipoUsuario.CLIENTE);
        c1.setDireccion(dir1);

        clientes.add(c1);
        contadorId = 2;
    }

    public Cliente autenticar(String correo, String contrasena) {
        for (Cliente c : clientes) {
            if (c.getCorreo().equals(correo)
                    && c.getContrasena().equals(contrasena)
                    && c.isEstado()) {
                return c;
            }
        }
        return null;
    }

    public boolean registrar(Cliente cliente) {
        try {
            cliente.setId(contadorId++);

            if (cliente.getDireccion() != null) {
                cliente.getDireccion().setId(cliente.getId());
            }

            clientes.add(cliente);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean existeCorreo(String correo) {
        for (Cliente c : clientes) {
            if (c.getCorreo().equalsIgnoreCase(correo)) {
                return true;
            }
        }
        return false;
    }

    public Cliente obtenerPorId(Integer id) {
        for (Cliente c : clientes) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public Cliente obtenerPorCorreo(String correo) {
        for (Cliente c : clientes) {
            if (c.getCorreo().equalsIgnoreCase(correo)) {
                return c;
            }
        }
        return null;
    }

    public boolean actualizar(Cliente cliente) {
        Cliente existente = obtenerPorId(cliente.getId());

        if (existente != null) {
            clientes.remove(existente);
            clientes.add(cliente);
            return true;
        }
        return false;
    }

    public List<Cliente> listar() {
        return new ArrayList<>(clientes);
    }
}
